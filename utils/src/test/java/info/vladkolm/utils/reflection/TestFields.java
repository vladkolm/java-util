package info.vladkolm.utils.reflection;

import info.vladkolm.utils.reflection.classes.ClassWithMethods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestFields {
    @Test
    public void testNameOfStaticField() {
        String name = Fields.nameOf(() -> ClassWithMethods.msg);
        Assertions.assertEquals("msg", name);
    }

    @Test
    public void testNameOfField() {
        String name = Fields.nameOf((ClassWithMethods cwm) -> cwm.message);
        Assertions.assertEquals("message", name);
    }
}
