package info.vladkolm.utils.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class StaticProxy implements InvocationHandler {
    private static Class<?> _clazz;
    private static StaticProxy _invocationHandler;

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(Class<T> interfaceClass, Class<?> objClass) {
        _clazz = objClass;
        _invocationHandler = new StaticProxy();
        return (T) Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[] { interfaceClass },
                _invocationHandler);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Method classMethod = _clazz.getDeclaredMethod(method.getName(), method.getParameterTypes());
        classMethod.setAccessible(true);
        return classMethod.invoke(null, args);
    }
}
