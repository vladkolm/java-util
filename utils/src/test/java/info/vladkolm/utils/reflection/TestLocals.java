package info.vladkolm.utils.reflection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class TestLocals {
    @Test
    public void testNameOfLocal() {
        String myMessage = "";
        Optional<String> name = Locals.nameOf(() -> myMessage);
        Assertions.assertTrue(name.isPresent());
        Assertions.assertEquals("myMessage", name.get());
    }
    @Test
    public void testNameOfFinalLocal() {
        final String myMsg = "";
        Optional<String> name = Locals.nameOf(() -> myMsg);
        // Currently, cannot extract the name of the final variable
        Assertions.assertFalse(name.isPresent());
    }

}
