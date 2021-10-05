package info.vladkolm.utils.reflection;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

class Handler implements InvocationHandler {
	String methodName;
	MethodHandle methodHandle;
	Method method;

	public Handler(Method method, MethodHandle methodHandle, String methodName) {
		this.method = method;
			this.methodHandle = methodHandle;
		this.methodName = methodName;
	}

	
	public Method getMethod() {
		return method;
	}

	public MethodHandle getMethodHandle() {
		return methodHandle;
	}


	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if(method.getName().equals(methodName)) {
			return this.methodHandle.invokeWithArguments(args);
		}
		return method.invoke(this, args);
	}
	
}
