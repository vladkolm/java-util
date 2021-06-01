package info.vladkolm.utils.singleton;

import java.util.function.Supplier;

public class LazySingleton<T> {
    private final Supplier<T> supplier;
    private volatile T object;

    public LazySingleton(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T createOrGet() {
        if(object != null) return object;
        return createOrGetWithLock();
    }

    private synchronized T createOrGetWithLock() {
        if(object == null) object = supplier.get();
        return object;
    }
}
