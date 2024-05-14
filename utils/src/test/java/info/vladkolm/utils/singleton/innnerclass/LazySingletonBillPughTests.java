package info.vladkolm.utils.singleton.innnerclass;

import info.vladkolm.utils.singleton.innerclass.BillPughSupplierCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.Supplier;


public class LazySingletonBillPughTests {
    static class TClass {
        static int counter;
        TClass() {
            counter++;
        }
    }
    @BeforeEach
    void setUp() {
        TClass.counter = 0;
    }

    @Test
    public void testSameInstance_Direct() {
        Supplier<TClass> supplier = BillPughSupplierCreator.createProxy(TClass::new);
        // Proxy is created, but the object is not
        Assertions.assertEquals(0, TClass.counter);
        TClass object1 = supplier.get();
        // The object is now created
        Assertions.assertEquals(1, TClass.counter);
        TClass object2 = supplier.get();
        // The supplier.get() returns the same object, so the counter is the same
        Assertions.assertEquals(1, TClass.counter);
        Assertions.assertSame(object1, object2);
    }

    @Test
    public void testSameInstance_DifferentLoaders() {
        Supplier<TClass> supplier1 = BillPughSupplierCreator.createProxy(TClass::new);
        Supplier<TClass> supplier2 = BillPughSupplierCreator.createProxy(TClass::new);
        // Proxies are created, but the objects are not
        Assertions.assertEquals(0, TClass.counter);
        // Different proxies are different objects
        Assertions.assertNotSame(supplier1, supplier2);
        // The object1 is now created
        TClass object1 = supplier1.get();
        Assertions.assertEquals(1, TClass.counter);
        // The supplier1.get() returns the same object1, so the counter is the same
        TClass object2 = supplier1.get();
        Assertions.assertSame(object1, object2);
        TClass object3 = supplier2.get();
        // The object3 is now created, so there are two different objects
        Assertions.assertEquals(2, TClass.counter);
        // The supplier2.get() returns the same object3, so the counter is the same as it was
        TClass object4 = supplier2.get();
        Assertions.assertEquals(2, TClass.counter);
        Assertions.assertSame(object3, object4);
        // Different proxies create different objects
        Assertions.assertNotSame(object1, object3);
    }

}
