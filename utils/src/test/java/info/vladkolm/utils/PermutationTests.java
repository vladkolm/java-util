package info.vladkolm.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PermutationTests
{
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
    public void testClone()
    {
        Permutation p1 = new Permutation(4);
        Permutation p2 = p1.clone();
        Assertions.assertArrayEquals(p1.toArray(), p2.toArray());
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
        Assertions.assertArrayEquals(new int[]{1, 2, 0}, p.toArray());

        p.orderFromIndex(firstIndex+1);
        Assertions.assertArrayEquals(new int[]{1, 0, 2}, p.toArray());
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
        Assertions.assertArrayEquals(new int[]{2, 1, 0}, p.toArray());

        p.orderFromIndex(firstIndex+1);
        Assertions.assertArrayEquals(new int[]{2, 0, 1}, p.toArray());
    }

    @Test
    public void testExample() {
        //This is an example of usage, so no assertion here
        Permutation p = Permutation.create(3);
        int numberOfPermutations = p.getNumberOfPermutations().intValue();

        for(int start = p.first(); p.getPermutationNumber().intValue()<=numberOfPermutations; start = p.next(start)) {
            System.out.println(p.getPermutationNumber().intValue() + " " +p+", start: "+start);
        }

    }

    @Test
    public void testPermutationLess() {
        Permutation p1 = Permutation.create(3);
        Permutation p2 = Permutation.create(3);
        p2.swap(1,2);
        Assertions.assertTrue(p1.lessThen(p2));
    }

    @Test
    public void testEnumerations_CorrectOrder() {
        Permutations permutations = Permutations.create(4);
        Permutation prev = null;
        for(Permutation perm: permutations) {
            if(prev != null) {
                Assertions.assertTrue(prev.lessThen(perm));
            }
            prev = perm.clone();
        }
    }
}

