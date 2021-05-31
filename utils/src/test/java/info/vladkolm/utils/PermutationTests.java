package info.vladkolm.utils;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Spliterator.IMMUTABLE;
import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.StreamSupport.stream;
import static org.junit.jupiter.params.provider.Arguments.of;

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
    public void testCopy()
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

    private static void assertCorrectOrder(List<Permutation> permList) {
        for(int i = 1; i< permList.size(); i++) {
            Assertions.assertTrue(Permutations.lessThen(permList.get(i-1), permList.get(i)));
        }
    }

    // Ali Dehghani (Baeldung). Guide to JUnit 5 Parameterized Tests
    // https://www.baeldung.com/parameterized-tests-junit-5

    private static Stream<Arguments> argumentStream() {
        return Stream.of(of(3), of(4), of(5), of(6), of(7), of(8) );
    }

    @ParameterizedTest
    //Another approach is:  @ValueSource(ints = {3, 4, 5, 6, 7, 8})
    @MethodSource("argumentStream")
    public void testEnumerations_CorrectOrder_Parametrized(int size) {
        Permutations permutations = Permutations.create(size);
        int numberOfPermutations = MathEx.factorial(permutations.size()).intValue();
        List<Permutation> permList = new ArrayList<>();
        for(Permutation perm: permutations) {
            permList.add(perm.copy());
        }
        Assertions.assertEquals(numberOfPermutations, permList.size());
        assertCorrectOrder(permList);
    }

    @ParameterizedTest
    @MethodSource("argumentStream")
    public void testEnumerations_Stream(int size) {
        Permutations permutations = Permutations.create(size);
        Stream<Permutation> stream = stream(spliteratorUnknownSize(permutations.iterator(), IMMUTABLE), false);
        List<Permutation> permList = stream.map(Permutation::copy).collect(Collectors.toList());
        Assertions.assertEquals(MathEx.factorial(permutations.size()).intValue(), permList.size());
        assertCorrectOrder(permList);
    }


}

