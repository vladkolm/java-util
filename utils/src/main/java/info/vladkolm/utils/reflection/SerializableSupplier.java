package info.vladkolm.utils.reflection;

import java.io.Serializable;
import java.util.function.Supplier;

public interface SerializableSupplier<T> extends Serializable, Supplier<T> {
}
