package info.vladkolm.utils.singleton.doublecheck;

import java.util.function.Supplier;

class DoubleCheckSupplier<T> implements Supplier<T> {
    private final Supplier<T> supplier;
    private volatile T object;

    DoubleCheckSupplier(Supplier<T> supplier) {
        this.supplier = supplier;
    }
    @Override
    public T get() {
        if(object != null) return object;
        return getWithLock();
    }
    private synchronized T getWithLock() {
        if(object == null) object = supplier.get();
        return object;
    }

}
