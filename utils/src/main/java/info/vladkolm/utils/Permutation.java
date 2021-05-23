package info.vladkolm.utils;

import java.util.Arrays;

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
}