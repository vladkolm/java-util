package info.vladkolm.utils.reflection.classes;

public class GenericClass<T> {
    public int func(T t) {
        return t.toString().hashCode();
    }
}
