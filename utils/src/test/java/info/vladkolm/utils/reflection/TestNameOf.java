package info.vladkolm.utils.reflection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import info.vladkolm.utils.reflection.classes.ClassWithGenericMethod;

import static info.vladkolm.utils.reflection.MethodInfo.methodOf;
import static info.vladkolm.utils.reflection.MethodInfo.nameOf;

public class TestNameOf {
    @Test
    public void nameOf_usingMethodReference() {
        String name = nameOf(ClassWithGenericMethod::genFun);
        Assertions.assertEquals("genFun", name);
    }
    @Test
    public void nameOf_usingMethodReference2() {
        String name = nameOf(ClassWithGenericMethod::genFun2);
        Assertions.assertEquals("genFun2", name);
    }
    @Test
    public void nameOf_usingLambda() {
        String name = nameOf(() -> ClassWithGenericMethod.genFun(""));
        Assertions.assertEquals("genFun", name);
    }

    @Test
    public void testNameOfGenericMethodAsLambda() {
        String name = nameOf(() -> ClassWithGenericMethod.genFun(""));
        Assertions.assertEquals("genFun", name);
    }
}
