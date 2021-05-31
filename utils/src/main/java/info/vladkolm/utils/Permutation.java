package info.vladkolm.utils;

import java.util.Arrays;

// See https://www.baeldung.com/java-array-permutations
public class Permutation {
    private int [] data;

    public static Permutation create(int size) {
        return new Permutation(size);
    }

    Permutation(int size) {
        data = new int[size];
        for(int index=0; index<size; index++) {
            data[index] = index;
        }
    }

    public  Permutation copy() {
        return new Permutation(data);
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

    public int findIndexForNextPermutation() {
        for (int i = size()-1; i >0; i--) {
            if (data[i - 1] < data[i]) return i-1;
        }
        return -1;
    }

    public boolean isLastPermutation() {
        return findIndexForNextPermutation() == -1;
    }

    public boolean next() {
        int index =  findIndexForNextPermutation();
        if(index == -1) return false;
        swap(index, swapIndex(index));
        reverseDataAfterIndex(index+1);
        return true;
    }


    int swapIndex(int index) {
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

    @Override
    public String toString() {
        return "Permutation{" +
                "data=" + Arrays.toString(data) +
                '}';
    }

}
