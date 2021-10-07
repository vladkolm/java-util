package info.vladkolm.utils.reflection;

public class Methods {
    public static String nameOf(SerializableRunnable lambda) {
        return MethodInfo.internalNameOf(lambda);
    }
    public static String nameOf(SerializableConsumer<?> lambda) {
        return MethodInfo.internalNameOf(lambda);
    }
    public static String nameOf(SerializableFun lambda) {
        return MethodInfo.internalNameOf(lambda);
    }

}
