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
