package info.vladkolm.utils;

import org.junit.Assert;
import org.junit.Test;

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
        Assert.assertSame(t1, t2);
    }
}
