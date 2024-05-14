package info.vladkolm.utils.reflection;

import org.objectweb.asm.Type;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ClassUtils {
    public static class ClassLoaderEx extends ClassLoader {
        private byte[] _bytes;

        public ClassLoaderEx() {
        }

        public Class<?> defineClass(String name) {
            return defineClass(name, _bytes,0, _bytes.length);
        }
        public void setBytes(byte[] _bytes) {
            this._bytes = _bytes;
        }
    }

    public static String point2Slash(String str) {
        return str.replace('.', '/');
    }
    public static String getName(Class<?> clazz) {
        return point2Slash(clazz.getName());
    }

    public static Class<?> duplicateClass(Class<?> prototype) {
        ClassLoaderEx classLoader = new ClassLoaderEx();
        return duplicateClass(classLoader, prototype);
    }

    public static Class<?> duplicateClass(ClassLoaderEx classLoader, Class<?> prototype) {
        try {
            Class<?>[] innerClasses = prototype.getDeclaredClasses();
            for(Class<?> innerClass : innerClasses) {
                duplicateClass(classLoader, innerClass);
            }

            InputStream stream = getClassAsStream(prototype);
            byte[] byteArray = stream.readAllBytes();
            classLoader.setBytes(byteArray);
            return classLoader.defineClass(null);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static InputStream getClassAsStream(Class<?> prototype) {
        return getClassAsStream(prototype.getName());
    }

    private static InputStream getClassAsStream(String className) {
        className = "/"+ point2Slash(className)+".class";
        return AsmUtils.class.getResourceAsStream(className);
    }

    static InputStream getClassStream(Class<?> clazz) {
        return getClassAsStream(clazz.getName());
    }

    private static void getAbstractMethods(Class<?> clazz, List<Method> methodList) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (Modifier.isAbstract(method.getModifiers())) {
                methodList.add(method);
            }
        }
        Class<?> superclass = clazz.getSuperclass();
        if (superclass != null && superclass != Object.class) {
            getAbstractMethod(superclass);
        }
    }

    public static Method getAbstractMethod(Class<?> clazz) {
        List<Method> methodList = new ArrayList<>();
        getAbstractMethods(clazz, methodList);
        if (methodList.isEmpty()) return null;
        if (methodList.size() != 1) throw new ReflectUtilsException("The class have more then one abstract method");
        return methodList.get(0);
    }

    public static Method getMethodFromInterfaceList(Class<?>[] classList) {
        List<Method> methodList = new ArrayList<>();
        for (Class<?> clazz : classList) {
            getAbstractMethods(clazz, methodList);
        }
        // Find Method which does not belong to Object class
        for (Method method : methodList) {
            try {
                Object.class.getMethod(method.getName(), method.getParameterTypes());
            } catch (NoSuchMethodException e) {
                return method;
            } catch (SecurityException e) {
                throw new ReflectUtilsException(e);
            }
        }
        return null;
    }

    public static Method getMethodFromFunctionalInterface(Class<?> clazz) {
        List<Method> methodList = new ArrayList<>();
        getAbstractMethods(clazz, methodList);
        for (Method method : methodList) {
            try {
                Object.class.getMethod(method.getName(), method.getParameterTypes());
            } catch (NoSuchMethodException | SecurityException e) {
                return method;
            }
        }
        return null;
    }

    public static Class<?> getClass(Type type) {
        String className = type.getClassName();
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ReflectUtilsException(e);
        }
    }

    public static Class<?>[] getClassList(Type[] types) {
        List<Class<?>> classList = new ArrayList<>();
        for (Type type : types) {
            Class<?> clazz = ClassUtils.getClass(type);
            classList.add(clazz);
        }
        return classList.toArray(new Class<?>[0]);
    }


    public static Class<?> getClass(String className) {
        Class<?> cls;
        try {
            cls = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new ReflectUtilsException(e);
        }
        return cls;
    }

    public static Object invokeMethod(Object object, Method method, Object... parame) {
        try {
            return method.invoke(object, parame);
        } catch (Exception e) {
            throw new ReflectUtilsException(e);
        }
    }

    public static Method getMethod(Class<?> clazz, String name) {
        return getMethod(clazz, name, new Class<?>[0]).orElseThrow(ReflectUtilsException::new);
    }

    public static Optional<Method> getMethod(Class<?> clazz, String name, Class<?>[] classList) {
        try {
            Method method = clazz.getDeclaredMethod(name, classList);
            method.setAccessible(true);
            return Optional.of(method);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public static Class<?> getArrayType(Class<?> elementClass) {
        try {
            return Array.newInstance(elementClass, 0).getClass();
        } catch (Exception e) {
            throw new ReflectUtilsException(e);
        }
    }

    public static Class<?> getClassFromStr(String implClass) {
        try {
            return Class.forName(implClass.replaceAll("/", "."));
        } catch (Exception e) {
            throw new ReflectUtilsException(e);
        }
    }

}
