package info.vladkolm.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class LazySingletonTests {
    static class TClass {
    }


    @Test
    public void testSameInstance() {
        LazySingleton<TClass> singleton = new LazySingleton<>(TClass::new);
        TClass t1 = singleton.createOrGet();
        TClass t2 = singleton.createOrGet();
        Assertions.assertSame(t1, t2);
    }
}
