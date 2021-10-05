package info.vladkolm.utils.reflection;


import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandleProxies;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Function;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import info.vladkolm.utils.reflection.classes.ClassWithMethods;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


public class TestReflectionUtils
{
	ClassWithMethods obj;
	
	@BeforeEach
	public void before() {
		obj = new ClassWithMethods();
	}
	
	@Test
    public void testSimpleMethod() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method method = new ReflectUtils() {
			public void marker() { obj.method(null);}
		}.method();
		method.invoke(obj, "test method");
		
    	assertEquals("The text: test method", ClassWithMethods.msg);
	}


	
	@Test
    public void testSimpleProxy() {
		Function<String, Integer> proxy = new ReflectUtils() {
			public void marker() { 
				obj.method(null);
			}
		}.proxy(Function.class, obj);
		int result = proxy.apply("test");
    	assertEquals(1, result);
    	assertEquals("The text: test", ClassWithMethods.msg);
    	
    	// Extract reflection-method from proxy
    	Method method = ReflectUtils.getMethod(proxy);
		assertNotNull(method);
    	assertEquals("method", method.getName());
	}

	
	@Test
    public void testBoundedProxy1() throws Throwable {
		MethodHandle mh = new ReflectUtils()  {
			public void marker() { obj.method(null, null);}
		}.methodHandle();
		mh = mh.bindTo(obj);
		mh = MethodHandles.insertArguments(mh, 1, "world!");
		mh.invoke("Hello, ");

    	assertEquals("Hello, world!", ClassWithMethods.msg);
	}

	@Test
	public void testManualCreateByMethodHandleProxies() {
		MethodHandle mh = new ReflectUtils() {public void marker() { obj.method(null);}}.methodHandle();
		mh = mh.bindTo(obj);
		
		@SuppressWarnings("unchecked")
		Function<String, Object> function = MethodHandleProxies.asInterfaceInstance(Function.class, mh);
		function.apply("testManualCreateByMethodHandleProxies");
		assertEquals("The text: testManualCreateByMethodHandleProxies", ClassWithMethods.msg);
	}
	
	@Test
    public void testManualCreateByMethodHandleProxiesWithBinding() {
		MethodHandle mh  =  new ReflectUtils() {public void marker() { obj.method(null, null);}}.methodHandle();
		mh = mh.bindTo(obj);
		mh = MethodHandles.insertArguments(mh, 1, "Bye");
		mh = MethodHandles.insertArguments(mh, 0, "Good ");

		Runnable runnable = MethodHandleProxies.asInterfaceInstance(Runnable.class, mh);
		
		runnable.run();
    	assertEquals("Good Bye", ClassWithMethods.msg);
	}

	
	@Test
    public void testBoundedProxy2() {
		Runnable runnable =  new ReflectUtils() {
			public void marker() {
				obj.method(null, null);
			}
		} 
		.bindTo(obj)
		.bindArg(0, "Good ")
		.bindArg(1, "Bye")
		.proxy(Runnable.class);
		
		runnable.run();
    	assertEquals("Good Bye", ClassWithMethods.msg);
	}
	
	@Test
	public void testMethodWithPrimitiveParam() {
		Runnable runnable =  new ReflectUtils() {
			public void marker() { 
				obj.methodWithPrimitiveParam(null, 0);
			}
		}.bindTo(obj).bindArg(0, "test").bindArg(1, 13).proxy(Runnable.class);
		
		runnable.run();
    	assertEquals("The text: test13", ClassWithMethods.msg);
	}
	
	@Test
	public void testMethodWithArrayParams() {
		Runnable runnable =  new ReflectUtils() {
			public void marker() { 
				obj.methodWithArrayParam(null, null);
			}
		}.bindTo(obj).bindArg(0, "test").bindArg(1, new int[] {1,2,3,4}).proxy(Runnable.class);
		
		runnable.run();
    	assertEquals("The text: [1, 2, 3, 4]", ClassWithMethods.msg);
	}

	@Test
	public void testMethodWithArrayOfArraysParam() {
		Runnable runnable =  new ReflectUtils() {
		public void marker() { 
			obj.methodWithArrayOfArraysParam("", new int[0][0]);
		}
		{
			bindTo(obj); 
			bindArg(0, "test");
			bindArg(1, new int[][]{new int[]{1,2}, new int[]{3,4,5}});
		}
		}.proxy(Runnable.class);
		
		runnable.run();
    	assertEquals("Array of arrays: [[1, 2],[3, 4, 5]]", ClassWithMethods.msg);
	}

}
