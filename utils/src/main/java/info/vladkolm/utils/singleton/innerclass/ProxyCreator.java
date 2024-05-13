package info.vladkolm.utils.singleton.innerclass;

import info.vladkolm.utils.reflection.AsmUtils;

import java.lang.reflect.Constructor;
import java.util.function.Supplier;
// See "Bill Pugh Singleton Implementation"
// https://www.digitalocean.com/community/tutorials/java-singleton-design-pattern-best-practices-examples
public class ProxyCreator {
    @SuppressWarnings("unchecked")
    static public <T> Supplier<T> createProxy(Supplier<T> supplier) {
        try {
            Class<?> newClass = AsmUtils.duplicateClass(Proxy.class);
            Constructor<?> constructor = newClass.getConstructor(Supplier.class);
            Object obj = constructor.newInstance(supplier);
            return (Supplier<T>) obj;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
