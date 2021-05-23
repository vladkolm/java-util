package info.vladkolm.utils;

import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Class produces all permutations of given dimension starting of [1,2,3,4,...]
 * Example of usage:
 *  int number = 1;
 *  Permutation p = Permutation.create(3);
 *  for(int start = p.first(); number<7; start = p.next(start)) {
 *      System.out.println(number++ + " " +p);
 *  }
 */
public class Permutation {
    private int [] data;
    private BigInteger numberOfPermutations = BigInteger.ZERO;
    private BigInteger permutationNumber = BigInteger.ZERO;

    public static Permutation create(int size) {
        return new Permutation(size);
    }

    Permutation(int size) {
        numberOfPermutations = factorial(size);
        permutationNumber = BigInteger.ONE;
        data = new int[size];
        for(int index=0; index<size; index++) {
            data[index] = index;
        }
    }

    @NotNull
    public BigInteger getNumberOfPermutations() {
        return numberOfPermutations;
    }

    @NotNull
    public BigInteger getPermutationNumber() {
        return permutationNumber;
    }

    Permutation(int [] array) {
        data = Arrays.copyOf(array, array.length);
    }

    public int size() {
        return data.length;
    }

    public int get(int index) {
        return data[index];
    }


    public int[] toArray() {
        return Arrays.copyOf(data, data.length);
    }

    public int first() {
        if(size() > 1) {
            for (int i = size()-1; i >0; i--) {
                if (data[i - 1] < data[i]) return i-1;
            }
        }
        return -1;
    }

    public int next(int firstIndex) {
        permutationNumber = permutationNumber.add(BigInteger.ONE);
        if(firstIndex <0) return -1;
        int lastIndex = last(firstIndex);
        if(lastIndex <0) return -1;
        swap(firstIndex, lastIndex);
        orderFromIndex(firstIndex+1);
        return first();
    }


    int last(int firstIndex) {
        if(size() > 1) {
            int element = data[firstIndex];
            int min = -1;
            for (int i = firstIndex+1; i < size(); i++) {
               if(data[i] > element) {
                   if(min < 0) min = i;
                   if(data[i] < data[min]) min = i;
               }
            }
            if(size() != min) return min;
        }
        return -1;
    }

    void swap(int index1, int index2) {
        int saved = data[index1];
        data[index1] = data[index2];
        data[index2] = saved;
    }

    //Orders the part after (including) index
    //This part should be ordered inversely
    void orderFromIndex(int index) {
        int size = (data.length - index)/2;
        int last = data.length-1;
        for(int i=0; i<size; i++) {
            swap(index+i, last-i);
        }
    }

    @Override
    public String toString() {
        return "Permutation{" +
                "data=" + Arrays.toString(data) +
                '}';
    }

    private static BigInteger factorial(int n) {
        return Stream.iterate (BigInteger.ONE, i -> i.add(BigInteger.ONE)).limit(n).reduce(BigInteger.ONE, BigInteger::multiply);
    }

}
