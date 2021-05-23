package info.vladkolm.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestClass {
    public TestClass() {
    }
}


public class LazySingletonTests {
    @Test
    public void testSameInstance() {
        LazySingleton<TestClass> singleton = new LazySingleton<>(TestClass::new);
        TestClass t1 = singleton.createOrGet();
        TestClass t2 = singleton.createOrGet();
        Assertions.assertSame(t1, t2);
    }
}
