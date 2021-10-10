package info.vladkolm.utils.reflection;

import info.vladkolm.utils.reflection.classes.ClassWithGenericMethod;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestMethods {
    @Test
    public void nameOf_usingMethodReference() {
        String name = Methods.nameOf(ClassWithGenericMethod::genFun);
        Assertions.assertEquals("genFun", name);
    }
    @Test
    public void nameOf_usingMethodReference2() {
        String name = Methods.nameOf(ClassWithGenericMethod::genFun2);
        Assertions.assertEquals("genFun2", name);
    }
    @Test
    public void nameOf_usingLambda() {
        String name = Methods.nameOf(() -> ClassWithGenericMethod.genFun(""));
        Assertions.assertEquals("genFun", name);
    }

    @Test
    public void testNameOfGenericMethodAsLambda() {
        String name = Methods.nameOf(() -> ClassWithGenericMethod.genFun(""));
        Assertions.assertEquals("genFun", name);
    }
}
