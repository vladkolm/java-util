package info.vladkolm.utils.reflection;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public abstract class ReflectUtils {
	SortedMap<Integer, Object> boundParams = new TreeMap<>(Collections.reverseOrder());
	abstract public void marker();
	Method method;
	MethodHandle methodHandle;
	
	
	protected ReflectUtils bindArg(int pos, Object value) {
		boundParams.put(pos, value);
		return this;
	}
	protected ReflectUtils bindTo(Object obj) {
		methodHandle = methodHandle().bindTo(obj);
		return this;
	}
	
	public MethodHandle methodHandle() {
		if(methodHandle == null) {
			MethodHandles.Lookup lookup = MethodHandles.lookup();
			try {
				methodHandle = lookup.unreflect(method());
			} catch (IllegalAccessException e) {
				throw new ReflectUtilsException(e);
			}
		}
		
		return methodHandle;
	}
	
	public Method method() {
		if(method == null) {
			method = extractMethod();
		}
		return method;
	}

	
	private Method extractMethod() {
		Method method = ClassUtils.getAbstractMethod(ReflectUtils.class);
		if(method == null) throw new ReflectUtilsException("Cannot gat method from the class");
		return getMethodFromClass(getClass(), method.getName());
	}

	public static Method getMethodFromClass(Class<?> clazz, String methodName) {
		return AsmUtils.getMethod(clazz, methodName);
	}
	
	public void bindParameters() {
		for(Map.Entry<Integer, Object> mapEntry: boundParams.entrySet()) {
			methodHandle = MethodHandles.insertArguments(methodHandle(), mapEntry.getKey(), mapEntry.getValue()); 
		}
		
		
	}
	
	public <T> T proxy(Class<?> clazz) {
		return proxy(clazz, null);
	}
	

	@SuppressWarnings("unchecked")
	public <T> T proxy(Class<?> clazz, Object obj) {
		if(!clazz.isInterface()) {
			throw new ReflectUtilsException("Proxied class should be an interface");
		}
		String methodName = getAbstractMethodName(clazz);
		if(methodName == null) {
			throw new ReflectUtilsException("Interface does not have acceptable methods");
		}
		
		if(obj != null) {
			methodHandle = methodHandle().bindTo(obj);
		}
		bindParameters();

		Handler handler = new Handler(method(), methodHandle(), methodName);
		return (T)Proxy.newProxyInstance(handler.getClass().getClassLoader(), new Class<?>[]{clazz}, handler);
	}
	
	
	public static Method getMethod(Object proxy) {
		Class<?> clazz = proxy.getClass();
		if(Proxy.isProxyClass(clazz)) {
			InvocationHandler invHandler = Proxy.getInvocationHandler(proxy);
			if(invHandler instanceof Handler) {
				Handler handler = (Handler)invHandler;
				return handler.getMethod();
			}
		}
		return null;
	}

	
	public static String getAbstractMethodName(Class<?> clazz) {
		for(Method method: clazz.getMethods()) {
			if(Modifier.isAbstract(method.getModifiers())) {
				return method.getName();
			}
		}
		return null;
	}

	
	
}
