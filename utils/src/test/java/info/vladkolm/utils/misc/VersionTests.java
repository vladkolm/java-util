package info.vladkolm.utils.misc;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class VersionTests {
    @Test
    public void testGetJavaVersion() {
        int version = Version.getJavaVersion();
        Assertions.assertTrue(version >=7);
    }
}
