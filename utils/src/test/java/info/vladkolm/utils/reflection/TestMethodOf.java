package info.vladkolm.utils.reflection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import info.vladkolm.utils.reflection.classes.ClassWithGenericMethod;
import info.vladkolm.utils.reflection.classes.ClassWithMethods;
import info.vladkolm.utils.reflection.classes.GenericClass;

import java.lang.reflect.Method;

import static info.vladkolm.utils.reflection.MethodInfo.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TestMethodOf {

	@Test
	public void testGenericMethod() throws Exception {
		String s = "Hello, world!";
		Method method = methodOf(ClassWithGenericMethod::genFun);
		String r = (String) method.invoke(null, s);
		Assertions.assertEquals(s, r);
	}

	@Test
	public void testGenericMethodAsLambda() throws Exception{
		String s = "Hello, world!";
		Method method = methodOf(() -> ClassWithGenericMethod.genFun(s));
		String r = (String) method.invoke(null, s);
		Assertions.assertEquals(s, r);
	}

	@Test
	public void testGenericClass() {
		Method method = methodOf((GenericClass<String> obj) -> obj.func(""));
		Assertions.assertEquals("func", method.getName());
	}

	@Test
	public void testSerializableMethodReference() {
		Method method = methodOf((ClassWithMethods obj) -> obj.method(""));
		Assertions.assertEquals("method", method.getName());
	}

	@Test
	public void testRunnableSerializable() throws Exception {
		ClassWithMethods obj = new ClassWithMethods();
		Method method = methodOf(() -> obj.method(null));
		Assertions.assertNotNull(method);
		method.invoke(obj, "Hello, lambda");
		Assertions.assertEquals("The text: Hello, lambda", ClassWithMethods.msg);
	}

	@Test
	public void testExpectException() {
		assertThrows(ReflectUtilsException.class, () -> methodOf(() -> {}));
	}

	@Test
	public void methodOf_incorrectLambda() {
		assertThrows(ReflectUtilsException.class,
				() -> methodOf((ClassWithMethods obj) -> {obj.method(""); obj.method("test");}));
	}


	@Test
	public void methodOf_usingMethodReference2() {
		Method method = methodOf(ClassWithGenericMethod::genFun2);
		Assertions.assertEquals("genFun2", method.getName());
	}

}
