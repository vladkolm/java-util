package info.vladkolm.utils.reflection;

import info.vladkolm.utils.reflection.classes.ClassWithMethods;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static info.vladkolm.utils.reflection.Fields.nameOf;

public class TestFields {
    @Test
    public void testNameOfStaticField() {
        String name = nameOf(() -> ClassWithMethods.msg);
        Assertions.assertEquals("msg", name);
    }
    @Test
    public void testNameOfField() {
        String name = nameOf((ClassWithMethods cwm) -> cwm.message);
        Assertions.assertEquals("message", name);
    }

}
