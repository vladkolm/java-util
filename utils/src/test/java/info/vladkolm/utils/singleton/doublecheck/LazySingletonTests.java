package info.vladkolm.utils.singleton.doublecheck;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;


public class LazySingletonTests {
    static class TClass {
    }
    static class TClass2 {
        public final int index;
        public TClass2(int index) {
            this.index = index;
        }
    }

    @Test
    public void testSameInstance() {
        Supplier<TClass> singleton = LazySingleton.createProxy(TClass::new);
        TClass t1 = singleton.get();
        TClass t2 = singleton.get();
        Assertions.assertSame(t1, t2);
    }
    @Test
    public void testSameInstance_ConstructorWithParameters() {
        Supplier<TClass2> singleton = LazySingleton.createProxy(()->new TClass2(1));
        TClass2 t1 = singleton.get();
        Assertions.assertSame(1, t1.index);
        TClass2 t2 = singleton.get();
        Assertions.assertSame(t1, t2);
    }
}
