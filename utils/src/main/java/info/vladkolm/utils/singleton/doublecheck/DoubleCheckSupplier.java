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
        return createOrGetWithLock();
    }
    private synchronized T createOrGetWithLock() {
        if(object == null) object = supplier.get();
        return object;
    }

}
