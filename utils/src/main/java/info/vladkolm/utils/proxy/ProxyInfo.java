package info.vladkolm.utils.proxy;

public interface ProxyInfo {
    boolean contains(Class<?> clazz, String methodName, Class<?>[] paramsClass);
}
