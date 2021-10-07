package info.vladkolm.utils.reflection;

import java.io.Serializable;

public class Fields {
    public static String nameOf(SerializableSupplier<?> lambda) {
        return internalNameOfField(lambda);
    }
    public static String nameOf(SerializableFunction<?,?> lambda) {
        return internalNameOfField(lambda);
    }
    private static String internalNameOfField(Serializable lambda) {
        MethodInfo methodInfo = new MethodInfo(lambda);
        return methodInfo.extractFieldNameFromLambda();
    }

}
