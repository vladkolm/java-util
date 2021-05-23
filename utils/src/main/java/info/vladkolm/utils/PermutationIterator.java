package info.vladkolm.utils;

import java.math.BigInteger;
import java.util.Iterator;

public class PermutationIterator implements Iterator<Permutation> {
    private Permutation permutation;
    private int start;
    private int size;

    public PermutationIterator(int size) {
        this.size = size;
    }

    @Override
    public boolean hasNext() {
        if(permutation == null && size > 0) return true;
        return permutation.getPermutationNumber().compareTo(permutation.getNumberOfPermutations())<0;
    }

    @Override
    public Permutation next() {
        if(permutation == null) {
            permutation = Permutation.create(size);
            start = permutation.first();
        } else {
            start = permutation.next(start);
        }
        return permutation;
    }
}
