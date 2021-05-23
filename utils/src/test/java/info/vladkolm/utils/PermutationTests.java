package info.vladkolm.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PermutationTests
{
    public static boolean compare(int []x, int[] y) {
        if(x.length != y.length) return false;
        for(int i=0; i<x.length; i++) {
            if(x[i] != y[i]) return false;
        }
        return true;
    }

    @Test
    public void testConstructor()
    {
        Permutation p = new Permutation(3);
        Assertions.assertEquals(3, p.size());
        for(int i=0; i<p.size(); i++) {
            Assertions.assertEquals(i, p.get(i));
        }
    }

    @Test
    public void testSwap()
    {
        Permutation p = new Permutation(3);
        p.swap(0, 2);
        Assertions.assertEquals(2, p.get(0));
        Assertions.assertEquals(0, p.get(2));
    }

    @Test
    public void testOrder_FullArray()
    {
        Permutation p = new Permutation(3);
        p.orderFromIndex(0);
        for(int i=0; i<p.size(); i++) {
            Assertions.assertEquals(3-i-1, p.get(i));
        }
    }

    @Test
    public void testOrder_PartOfArray()
    {
        Permutation p = new Permutation(4);
        p.orderFromIndex(1);
        Assertions.assertEquals(3, p.get(1));
        Assertions.assertEquals(2, p.get(2));
        Assertions.assertEquals(1, p.get(3));
    }

    @Test
    public void testFirstLastIndex_3elements()
    {
        Permutation p = new Permutation(3);
        int firstIndex = p.first();
        int lastIndex = p.last(firstIndex);
        Assertions.assertEquals(1, firstIndex);
        Assertions.assertEquals(2, lastIndex);
    }
    @Test
    public void testFirstLastIndex_4elements()
    {
        Permutation p = new Permutation(4);
        int firstIndex = p.first();
        int lastIndex = p.last(firstIndex);
        Assertions.assertEquals(2, firstIndex);
        Assertions.assertEquals(3, lastIndex);
    }

    @Test
    public void testFirstLastIndex_0_2_1()
    {
        Permutation p = new Permutation(new int[]{0, 2, 1});
        int firstIndex = p.first();
        Assertions.assertEquals(0, firstIndex);
        int lastIndex = p.last(firstIndex);
        Assertions.assertEquals(2, lastIndex);
        p.swap(0, 2);
        Assertions.assertTrue(compare(new int[]{1, 2, 0}, p.toArray()));

        p.orderFromIndex(firstIndex+1);
        Assertions.assertTrue(compare(new int[]{1, 0, 2}, p.toArray()));
    }

    @Test
    public void testFirstLastIndex_1_2_0()
    {
        Permutation p = new Permutation(new int[]{1, 2, 0});
        int firstIndex = p.first();
        Assertions.assertEquals(0, firstIndex);
        int lastIndex = p.last(firstIndex);
        Assertions.assertEquals(1, lastIndex);
        p.swap(firstIndex, lastIndex);
        Assertions.assertTrue(compare(new int[]{2, 1, 0}, p.toArray()));

        p.orderFromIndex(firstIndex+1);
        Assertions.assertTrue(compare(new int[]{2, 0, 1}, p.toArray()));
    }

    @Test
    public void TestExample() {
        //This is an example of usage, so no assertion here
        Permutation p = Permutation.create(3);
        int numberOfPermutations = p.getNumberOfPermutations().intValue();

        for(int start = p.first(); p.getPermutationNumber().intValue()<=numberOfPermutations; start = p.next(start)) {
            System.out.println(p.getPermutationNumber().intValue() + " " +p);
        }

    }

}
