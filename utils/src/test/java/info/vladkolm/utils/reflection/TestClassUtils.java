package info.vladkolm.utils.reflection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Comparator;
import java.util.function.Function;


public class TestClassUtils {
	@Test
	public void testMethodFromFunctionalInterface_Function() {
		Method method = ClassUtils.getMethodFromFunctionalInterface(Function.class);
		Assertions.assertNotNull(method);
		Assertions.assertEquals("apply", method.getName());
	}

	@Test
	public void testMethodFromFunctionalInterface_Comparator() {
		Method method = ClassUtils.getMethodFromFunctionalInterface(Comparator.class);
		Assertions.assertNotNull(method);
		Assertions.assertEquals("compare", method.getName());
	}

}
