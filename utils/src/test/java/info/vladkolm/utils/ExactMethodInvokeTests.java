package info.vladkolm.utils;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
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
        Assert.assertEquals(1, b.hashCode());
    }

    @Test
    public void testOriginalHashCode() throws Throwable {
        B b = new B();
        Method hashCodeMethod = Object.class.getMethod("hashCode");
        Object invoke = ExactMethodInvoke.invoke(Object.class, hashCodeMethod, b);
        Assert.assertEquals(System.identityHashCode(b), invoke);
    }
}
