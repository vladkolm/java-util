package info.vladkolm.utils.singleton.doublecheck;

import java.util.function.Supplier;

public class LazySingleton<T> implements Supplier<T> {
    private final Supplier<T> supplier;
    private volatile T object;

    public static <T> Supplier<T> createProxy(Supplier<T> supplier) {
        return new LazySingleton<>(supplier);
    }

    private LazySingleton(Supplier<T> supplier) {
        this.supplier = supplier;
    }
    @Override
    public T get() {
        if(object != null) return object;
        return createOrGetWithLock();
    }
    private synchronized T createOrGetWithLock() {
        if(object == null) object = supplier.get();
        return object;
    }

}
