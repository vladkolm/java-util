package info.vladkolm.utils.proxy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;

class MethodInfo {
    public MethodInfo(Method method) {
        this.methodName = method.getName();
        this.argTypes = method.getParameterTypes();
    }

    public MethodInfo(String methodName, Class<?>[] argTypes) {
        this.methodName = methodName;
        this.argTypes = argTypes;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MethodInfo)) return false;
        MethodInfo that = (MethodInfo) o;
        return methodName.equals(that.methodName) && Arrays.equals(argTypes, that.argTypes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(methodName);
        result = 31 * result + Arrays.hashCode(argTypes);
        return result;
    }

    private String methodName;
    private Class<?>[] argTypes;
}
