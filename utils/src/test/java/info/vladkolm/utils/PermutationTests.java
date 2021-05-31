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
        Permutation p2 = p1.copy();
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
        p.reverseDataAfterIndex(0);
        for(int i=0; i<p.size(); i++) {
            Assertions.assertEquals(3-i-1, p.get(i));
        }
    }

    @Test
    public void testOrder_PartOfArray()
    {
        Permutation p = new Permutation(4);
        p.reverseDataAfterIndex(1);
        Assertions.assertEquals(3, p.get(1));
        Assertions.assertEquals(2, p.get(2));
        Assertions.assertEquals(1, p.get(3));
    }

    @Test
    public void testFirstLastIndex_3elements()
    {
        Permutation p = new Permutation(3);
        int indexForNextPermutation = p.findIndexForNextPermutation();
        int lastIndex = p.swapIndex(indexForNextPermutation);
        Assertions.assertEquals(1, indexForNextPermutation);
        Assertions.assertEquals(2, lastIndex);
    }
    @Test
    public void testFirstLastIndex_4elements()
    {
        Permutation p = new Permutation(4);
        int indexForNextPermutation = p.findIndexForNextPermutation();
        int lastIndex = p.swapIndex(indexForNextPermutation);
        Assertions.assertEquals(2, indexForNextPermutation);
        Assertions.assertEquals(3, lastIndex);
    }

    @Test
    public void testFirstLastIndex_0_2_1()
    {
        Permutation p = new Permutation(new int[]{0, 2, 1});
        int indexForNextPermutation = p.findIndexForNextPermutation();
        Assertions.assertEquals(0, indexForNextPermutation);
        int swapIndex = p.swapIndex(indexForNextPermutation);
        Assertions.assertEquals(2, swapIndex);
        p.swap(0, 2);
        Assertions.assertArrayEquals(new int[]{1, 2, 0}, p.toArray());

        p.reverseDataAfterIndex(indexForNextPermutation+1);
        Assertions.assertArrayEquals(new int[]{1, 0, 2}, p.toArray());
    }

    @Test
    public void testFirstLastIndex_1_2_0()
    {
        Permutation p = new Permutation(new int[]{1, 2, 0});
        int indexForNextPermutation = p.findIndexForNextPermutation();
        Assertions.assertEquals(0, indexForNextPermutation);
        int swapIndex = p.swapIndex(indexForNextPermutation);
        Assertions.assertEquals(1, swapIndex);
        p.swap(indexForNextPermutation, swapIndex);
        Assertions.assertArrayEquals(new int[]{2, 1, 0}, p.toArray());

        p.reverseDataAfterIndex(indexForNextPermutation+1);
        Assertions.assertArrayEquals(new int[]{2, 0, 1}, p.toArray());
    }

    @Test
    public void testPermutationLess() {
        Permutation p1 = Permutation.create(3);
        Permutation p2 = Permutation.create(3);
        p2.swap(1,2);
        Assertions.assertTrue(Permutations.lessThen(p1, p2));
    }

    void generateAndTestPermutations(int size) {
        Permutations permutations = Permutations.create(size);
        int numberOfPermutations = MathEx.factorial(permutations.getSize()).intValue();
        Permutation prev = null;
        int permCount = 0;
        for(Permutation perm: permutations) {
            if(prev != null) {
                Assertions.assertTrue(Permutations.lessThen(prev, perm));
            }
            prev = perm.copy();
            permCount++;
        }
        Assertions.assertEquals(numberOfPermutations, permCount);
    }

    @Test
    public void testEnumerations_CorrectOrder_FourElementPermutation() {
        generateAndTestPermutations(4);
    }
    @Test
    public void testEnumerations_CorrectOrder_FiveElementPermutation() {
        generateAndTestPermutations(5);
    }
}

