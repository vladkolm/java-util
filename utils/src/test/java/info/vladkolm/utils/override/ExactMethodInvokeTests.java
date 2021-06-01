package info.vladkolm.utils.override;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

class A {}
class B {
    @Override
    public int hashCode() {
        return 1;
    }
}

public class ExactMethodInvokeTests {
    @Test
    public void testStandardHashCode() {
        B b = new B();
        Assertions.assertEquals(1, b.hashCode());
    }

    @Test
    public void testOriginalHashCode() throws Throwable {
        B b = new B();
        Method hashCodeMethod = Object.class.getMethod("hashCode");
        Object invoke = ExactMethodInvoke.invoke(Object.class, hashCodeMethod, b);
        Assertions.assertEquals(System.identityHashCode(b), invoke);
    }
}
