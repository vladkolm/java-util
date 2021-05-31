package info.vladkolm.utils;

import java.math.BigInteger;
import java.util.stream.Stream;

public class MathEx {
    public static BigInteger factorial(int n) {
        return Stream.iterate(BigInteger.ONE, i -> i.add(BigInteger.ONE))
                .limit(n)
                .reduce(BigInteger.ONE, BigInteger::multiply);
    }

}
