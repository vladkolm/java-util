package info.vladkolm.utils.singleton.innnerclass;

import info.vladkolm.utils.reflection.ClassUtils;
import info.vladkolm.utils.reflection.ClassUtils.ClassLoaderEx;
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

    // Asserts recursively, that all internal classes have the same ClassLoader
    void assertRecursive(ClassLoader classLoader, Class<?> clazz){
        Assertions.assertEquals(classLoader, clazz.getClassLoader());
        Class<?>[] innerClasses = clazz.getDeclaredClasses();
        for(Class<?> innerClass : innerClasses) {
            assertRecursive(classLoader, innerClass);
        }
    }

    @Test
    public void duplicateClass_ClassLoaderTest() {
        Class<?> clazz = ClassUtils.duplicateClass(BillPughSupplier.class);
        ClassLoader classLoader = clazz.getClassLoader();
        Assertions.assertSame(classLoader.getClass(), ClassLoaderEx.class);
        assertRecursive(classLoader, clazz);
    }
}