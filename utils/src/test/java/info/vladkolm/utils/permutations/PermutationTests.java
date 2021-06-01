package info.vladkolm.utils.permutations;

import info.vladkolm.utils.math.MathEx;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Spliterator.IMMUTABLE;
import static java.util.Spliterators.spliteratorUnknownSize;
import static java.util.stream.StreamSupport.stream;
import static org.junit.jupiter.params.provider.Arguments.of;

public class PermutationTests {
    private static final int DEFAULT_SIZE = 3;
    private Permutation permutation;

    @BeforeEach
    public void beforeEach() {
        permutation = Permutation.create(DEFAULT_SIZE);
    }

    @Test
    public void testConstructor() {
        Assertions.assertEquals(DEFAULT_SIZE, permutation.size());
        for (int i = 0; i < permutation.size(); i++) {
            Assertions.assertEquals(i, permutation.get(i));
        }
    }

    @Test
    public void testCopy() {
        Permutation permutation2 = permutation.copy();
        Assertions.assertArrayEquals(permutation.toArray(), permutation2.toArray());
    }

    @Test
    public void testSwap() {
        permutation.swap(0, 2);
        Assertions.assertEquals(2, permutation.get(0));
        Assertions.assertEquals(0, permutation.get(2));
    }

    @Test
    public void testOrder_FullArray() {
        permutation.reverseDataAfterIndex(0);
        for (int i = 0; i < permutation.size(); i++) {
            Assertions.assertEquals(3 - i - 1, permutation.get(i));
        }
    }

    @Test
    public void testOrder_PartOfArray() {
        Permutation p = Permutation.create(4);
        p.reverseDataAfterIndex(1);
        Assertions.assertEquals(3, p.get(1));
        Assertions.assertEquals(2, p.get(2));
        Assertions.assertEquals(1, p.get(3));
    }

    @Test
    public void testFirstLastIndex_3elements() {
        int indexForNextPermutation = permutation.findIndexForNextPermutation();
        int lastIndex = permutation.findSwapIndex(indexForNextPermutation);
        Assertions.assertEquals(1, indexForNextPermutation);
        Assertions.assertEquals(2, lastIndex);
    }

    @Test
    public void testFirstLastIndex_4elements() {
        Permutation p = Permutation.create(4);
        int indexForNextPermutation = p.findIndexForNextPermutation();
        int lastIndex = p.findSwapIndex(indexForNextPermutation);
        Assertions.assertEquals(2, indexForNextPermutation);
        Assertions.assertEquals(3, lastIndex);
    }

    @Test
    public void testFirstLastIndex_0_2_1() {
        Permutation p = Permutation.create(new int[]{0, 2, 1});
        int indexForNextPermutation = p.findIndexForNextPermutation();
        Assertions.assertEquals(0, indexForNextPermutation);
        int swapIndex = p.findSwapIndex(indexForNextPermutation);
        Assertions.assertEquals(2, swapIndex);
        p.swap(0, 2);
        Assertions.assertArrayEquals(new int[]{1, 2, 0}, p.toArray());

        p.reverseDataAfterIndex(indexForNextPermutation + 1);
        Assertions.assertArrayEquals(new int[]{1, 0, 2}, p.toArray());
    }

    @Test
    public void testFirstLastIndex_1_2_0() {
        Permutation p = Permutation.create(new int[]{1, 2, 0});
        int indexForNextPermutation = p.findIndexForNextPermutation();
        Assertions.assertEquals(0, indexForNextPermutation);
        int swapIndex = p.findSwapIndex(indexForNextPermutation);
        Assertions.assertEquals(1, swapIndex);
        p.swap(indexForNextPermutation, swapIndex);
        Assertions.assertArrayEquals(new int[]{2, 1, 0}, p.toArray());

        p.reverseDataAfterIndex(indexForNextPermutation + 1);
        Assertions.assertArrayEquals(new int[]{2, 0, 1}, p.toArray());
    }

    @Test
    public void testPermutationLess() {
        Permutation permutation2 = Permutation.create(3);
        permutation2.swap(1, 2);
        Assertions.assertTrue(Permutations.lessThen(permutation, permutation2));
    }

    private static void assertCorrectOrder(List<Permutation> permList) {
        for (int i = 1; i < permList.size(); i++) {
            Assertions.assertTrue(Permutations.lessThen(permList.get(i - 1), permList.get(i)));
        }
    }

    // Ali Dehghani (Baeldung). Guide to JUnit 5 Parameterized Tests
    // https://www.baeldung.com/parameterized-tests-junit-5
    private static Stream<Arguments> argumentStream() {
        return IntStream.range(3, 9).boxed().map(Arguments::of);
    }

    @ParameterizedTest
    //Another approach is:  @ValueSource(ints = {3, 4, 5, 6, 7, 8})
    @MethodSource("argumentStream")
    public void testEnumerations_CorrectOrder_Parametrized(int size) {
        Permutations permutations = Permutations.create(size);
        int numberOfPermutations = MathEx.factorial(permutations.size()).intValue();
        List<Permutation> permList = new ArrayList<>();
        for (Permutation perm : permutations) {
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

