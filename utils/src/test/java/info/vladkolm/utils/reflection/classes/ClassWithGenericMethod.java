package info.vladkolm.utils.reflection.classes;

public class ClassWithGenericMethod {

    public static <T> T genFun(T x) {
        return x;
    }
    public static int genFun2(Object x, Object y) {
        return 1;
    }

}
