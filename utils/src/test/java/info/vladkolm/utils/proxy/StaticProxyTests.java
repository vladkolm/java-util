package info.vladkolm.utils.proxy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

interface Testable {
    int doSomething();
}

class ClassWithStaticMethods {
    public static int doSomething() {
        return 13;
    }
}

public class StaticProxyTests {
    @Test
    public void staticProxyTest() {
        Testable testable = StaticProxy.createProxy(Testable.class, ClassWithStaticMethods.class);
        int result = testable.doSomething();
        Assertions.assertEquals(13, result);
    }
}
