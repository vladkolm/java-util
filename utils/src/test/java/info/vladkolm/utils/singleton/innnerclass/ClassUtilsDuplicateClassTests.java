package info.vladkolm.utils.singleton.innnerclass;

import info.vladkolm.utils.reflection.ClassUtils;
import info.vladkolm.utils.singleton.innerclass.BillPughSupplier;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ClassUtilsDuplicateClassTests {
    @Test
    public void duplicateClassTest() {
        Class<?> clazz = ClassUtils.duplicateClass(BillPughSupplier.class);
        Assertions.assertNotNull(clazz);
        Assertions.assertNotEquals(BillPughSupplier.class, clazz);
    }
}