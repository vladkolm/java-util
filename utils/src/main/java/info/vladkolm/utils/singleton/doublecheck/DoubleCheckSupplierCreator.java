package info.vladkolm.utils.singleton.doublecheck;

import java.util.function.Supplier;

public class DoubleCheckSupplierCreator {
    public static <T> Supplier<T> createProxy(final Supplier<T> supplier) {
        return new DoubleCheckSupplier<>(supplier);
    }
}
