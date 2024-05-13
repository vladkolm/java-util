package info.vladkolm.utils.singleton.innerclass;

import java.util.function.Supplier;

@SuppressWarnings({"unused", "unchecked"})
public class Proxy<T> implements Supplier<T> {
    private static Supplier<?> _supplier;

    public Proxy(Supplier<?> supplier)
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
