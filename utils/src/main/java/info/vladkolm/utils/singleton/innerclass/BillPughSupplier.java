package info.vladkolm.utils.singleton.innerclass;

import java.util.function.Supplier;

@SuppressWarnings("unchecked")
public class BillPughSupplier<T> implements Supplier<T> {
    private static Supplier<?> _supplier;

    public BillPughSupplier(Supplier<?> supplier)
    {
        _supplier = supplier;
    }

    private static class LazyHolder {
        public static final Object INSTANCE = _supplier.get();
    }

    @Override
    public T get() {
        return (T)LazyHolder.INSTANCE;
    }
}
