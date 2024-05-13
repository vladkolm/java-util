package info.vladkolm.utils.override;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static info.vladkolm.utils.reflection.MethodInfo.methodOf;

class A {
    public static String MESSAGE = "Hello";
    @SuppressWarnings("unused")
    public String getMessage(String arg) {
        return MESSAGE + arg;
    }
}
class B extends A {
    public static String DERIVED_MESSAGE = "Good Bye";

    @Override
    public int hashCode() {
        return 1;
    }

    public String getMessage(String arg) {
        return DERIVED_MESSAGE + arg;
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
        Method hashCodeMethod = methodOf(Object::hashCode);
        int invoke = (int) ExactMethodInvoke.invoke(Object.class, hashCodeMethod, b);
        Assertions.assertEquals(System.identityHashCode(b), invoke);
    }

    @Test
    public void testStandardDerivedObject() {
        B b = new B();
        Assertions.assertEquals(B.DERIVED_MESSAGE, b.getMessage(""));
    }

    @Test
    public void testOriginalMessageObject() throws Throwable {
        B b = new B();
        Method method = methodOf(() -> b.getMessage(""));
        String message = ExactMethodInvoke.invoke(A.class, method, b, "").toString();
        Assertions.assertEquals(B.MESSAGE, message);
    }
}
