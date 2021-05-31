package info.vladkolm.utils;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class Permutations  implements Iterable<Permutation> {
    private final int size;

    public static Permutations create(int size) {
        return new Permutations(size);
    }

    private Permutations(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public static boolean lessThen(Permutation p1, Permutation p2) {
        if(p1.size() != p2.size()) throw new IllegalArgumentException();
        for(int i=0; i<p1.size(); i++) {
            if(p1.get(i) == p2.get(i)) continue;
            return p1.get(i) < p2.get(i);
        }
        return false;
    }


    @Override
    public Iterator<Permutation> iterator() {
        return new PermutationIterator(size);
    }

    @Override
    public void forEach(Consumer<? super Permutation> action) {
        Iterable.super.forEach(action);
    }

    @Override
    public Spliterator<Permutation> spliterator() {
        return Iterable.super.spliterator();
    }
}
