package info.vladkolm.utils.override;

import org.objectweb.asm.Type;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ExactMethodInvoke {
    public static Object invoke(Class<?> clazz, Method method, Object obj, Object ...args) throws Throwable {
        Lookup lookup = getLookup(obj.getClass());
        String descriptor = Type.getMethodDescriptor(method);
        MethodType methodType = MethodType.fromMethodDescriptorString(descriptor, clazz.getClassLoader());
        MethodHandle methodHandle = lookup.findSpecial(clazz, method.getName(), methodType, obj.getClass());
        List<Object> argList = new ArrayList<>();
        argList.add(obj);
        argList.addAll(Arrays.asList(args));
        return methodHandle.invokeWithArguments(argList);
    }

    // see: https://dzone.com/articles/correct-reflective-access-to-interface-default-methods
    private static Lookup getLookup(Class<?> objClass) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException {
        Lookup rootLookup = MethodHandles.lookup();
        try {
            //This should work starting from JDK-9 and higher
            //call MethodHandles.privateLookupIn(objClass, rootLookup) through reflection
            Method privateLookupMethod = MethodHandles.class.getMethod("privateLookupIn", Class.class, Lookup.class);
            return (Lookup)privateLookupMethod.invoke(null, objClass, rootLookup);
        } catch (NoSuchMethodException e) {
            //This is a fallback for JDK-7/8
            Constructor<?> constructor = Lookup.class.getDeclaredConstructor(Class.class);
            constructor.setAccessible(true);
            return (Lookup) constructor.newInstance(objClass);
        }
    }


}
