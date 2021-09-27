package info.vladkolm.utils.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class StaticProxy {
    private static Map<MethodInfo, Method> methodMap = new HashMap<>();
    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Class<T> interfaceClass, Class<?> objClass) {
        createMethodMap(interfaceClass, objClass);
        return (T) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[] { interfaceClass, ProxyInfo.class },
                (proxy, method, args)-> {
                    if(checkMethod(method)) {
                        String checkedMethodName = (String) args[1];
                        Class<?> [] argTypes = (Class<?>[]) args[2];
                        MethodInfo methodInfo = new MethodInfo(checkedMethodName, argTypes);
                        return methodMap.containsKey(methodInfo);
                    } else {
                        MethodInfo methodInfo = new MethodInfo(method);
                        Method classMethod = methodMap.get(methodInfo);
                        if(classMethod == null) {
                            throw new NoSuchMethodException(method.getName());
                        }
                        return classMethod.invoke(null, args);
                    }
                });
    }

    private static <T> void createMethodMap(Class<T> interfaceClass, Class<?> objClass) {
        Method[] declaredMethods = interfaceClass.getDeclaredMethods();
        for(Method method: declaredMethods) {
            try {
                Method implMethod = objClass.getDeclaredMethod(method.getName(), method.getParameterTypes());
                implMethod.setAccessible(true);
                methodMap.put(new MethodInfo(method), implMethod);
            } catch (NoSuchMethodException ignore) {
            }
        }
    }

    private static boolean checkMethod(Method method) {
        Class<?>[] paramTypes = method.getParameterTypes();
        return method.getName().equals("contains") && paramTypes.length == 3 && paramTypes[0] == Class.class && paramTypes[1] == String.class;
    }

}
