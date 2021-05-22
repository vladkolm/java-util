package info.vladkolm.utils;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.lang.invoke.MethodType;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ExactMethodInvoke {

    public static Object invoke(Class<?> clazz, Method method, Object obj, Object ...args) throws Throwable {
        Lookup lookup = getLookup(obj.getClass());
        MethodHandle methodHandle = lookup.findSpecial(clazz, method.getName(), MethodType.methodType(int.class), obj.getClass());
        return methodHandle.invoke(obj);
    }

    private static Lookup getLookup(Class<?> objClass) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> constructor = Lookup.class.getDeclaredConstructor(Class.class);
        constructor.setAccessible(true);
        return (Lookup) constructor.newInstance(objClass);
    }

}
