package info.vladkolm.utils.singleton.innnerclass;

import info.vladkolm.utils.reflection.AsmUtils;
import info.vladkolm.utils.singleton.innerclass.Proxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AsmUtilsTests {
    @Test
    public void duplicateClassTests() {
        Class<?> clazz = AsmUtils.duplicateClass(Proxy.class);
        Assertions.assertNotNull(clazz);
        Assertions.assertNotEquals(Proxy.class, clazz);
    }
}