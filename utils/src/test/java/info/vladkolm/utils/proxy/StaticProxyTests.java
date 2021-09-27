package info.vladkolm.utils.proxy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    Testable testable;
    ProxyInfo proxyInfo;

    @BeforeEach
    void beforeEach() {
        testable = StaticProxy.createProxy(Testable.class, ClassWithStaticMethods.class);
        proxyInfo = (ProxyInfo)testable;
    }

    @Test
    public void proxyInfoTest() {
        boolean hesDoSomething = proxyInfo.contains(Testable.class, "doSomething", new Class[0]);
        Assertions.assertTrue(hesDoSomething);
    }


    @Test
    public void staticProxyTest() {
        int result = testable.doSomething();
        Assertions.assertEquals(13, result);
    }
}
