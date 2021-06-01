package info.vladkolm.utils;

import java.util.Arrays;

// See also https://www.baeldung.com/java-array-permutations

/**
 *  This class produces permutations in lexicographical order
 *  Usage:
 *    Permutations permutations = Permutations.create(size);
 *    for(Permutation perm: permutations) {
 *      //Do something with the permutation
 *    }
 */
public class Permutation {
    private int [] data;

    public static Permutation create(int size) {
        return new Permutation(size);
    }

    public  Permutation copy() {
        return new Permutation(data);
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

    public boolean isLastPermutation() {
        return findIndexForNextPermutation() == -1;
    }

    public boolean next() {
        int index =  findIndexForNextPermutation();
        if(index == -1) return false;
        swap(index, findSwapIndex(index));
        reverseDataAfterIndex(index+1);
        return true;
    }

    @Override
    public String toString() {
        return "Permutation{" +
                "data=" + Arrays.toString(data) +
                '}';
    }

    private Permutation(int size) {
        data = new int[size];
        for(int index=0; index<size; index++) {
            data[index] = index;
        }
    }

    private Permutation(int [] array) {
        data = Arrays.copyOf(array, array.length);
    }

    public static Permutation create(int [] array) {
        int[] ints = Arrays.copyOf(array, array.length);
        Arrays.sort(ints);
        for(int i=0; i<ints.length; i++) {
            if(i != ints[i]) {
                throw new IllegalArgumentException("Array should be a permutation of [0, 1, 2, ....length-1]");
            }
        }
        return new Permutation(array);
    }


    int findIndexForNextPermutation() {
        for (int i = size()-1; i >0; i--) {
            if (data[i - 1] < data[i]) return i-1;
        }
        return -1;
    }

    int findSwapIndex(int index) {
        for (int i = data.length - 1; i > index; i--) {
            if (data[i]> data[index]) return i;
        }
        return index;
    }

    // Swaps two data elements with given indices
    void swap(int index1, int index2) {
        int saved = data[index1];
        data[index1] = data[index2];
        data[index2] = saved;
    }

    //Reverses the data-part after (including) index
    void reverseDataAfterIndex(int index) {
        int size = (data.length - index)/2;
        int last = data.length-1;
        for(int i=0; i<size; i++) {
            swap(index+i, last-i);
        }
    }
}
