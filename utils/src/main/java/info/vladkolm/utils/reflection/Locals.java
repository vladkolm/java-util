package info.vladkolm.utils.reflection;

import java.io.Serializable;
import java.util.Optional;

public class Locals {
    public static Optional<String> nameOf(SerializableSupplier<?> lambda) {
        return internalNameOfLocal(lambda);
    }
    public static Optional<String> nameOf(SerializableFunction<?,?> lambda) {
        return internalNameOfLocal(lambda);
    }
    private static Optional<String> internalNameOfLocal(Serializable lambda) {
        MethodInfo methodInfo = new MethodInfo(lambda);
        return methodInfo.extractLocalNameFromLambda();
    }

}
