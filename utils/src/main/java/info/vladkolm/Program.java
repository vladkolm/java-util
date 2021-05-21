package info.vladkolm;

import info.vladkolm.utils.LazySingleton;

class A {
    public A() {
        System.out.println("Constructor");
    }
}

public class Program
{
    public static void main( String[] args )
    {
        LazySingleton<A> singleton = new LazySingleton<>(A::new);
        A a1 = singleton.createOrGet();
        A a2 = singleton.createOrGet();
    }
}
