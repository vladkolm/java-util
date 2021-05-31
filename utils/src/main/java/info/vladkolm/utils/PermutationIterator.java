package info.vladkolm.utils;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PermutationIterator implements Iterator<Permutation> {
    private Permutation permutation;
    private int size;

    public PermutationIterator(int size) {
        if(size <= 0) throw new IllegalArgumentException("Invalid size: " + size);
        this.size = size;
    }

    @Override
    public boolean hasNext() {
        if(permutation == null) return true;
        return !permutation.isLastPermutation();
    }

    @Override
    public Permutation next() {
        if(permutation == null) {
            permutation = Permutation.create(size);
        } else {
            if(permutation.isLastPermutation()) throw new NoSuchElementException();
            permutation.next();
        }
        return permutation;
    }
}
