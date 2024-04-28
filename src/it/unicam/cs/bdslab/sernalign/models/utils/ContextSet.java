package it.unicam.cs.bdslab.sernalign.models.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ContextSet {
    /**
     * this method return the set of possible integer values at the i position
     * @param i the position
     * @return the set or the context having all the possible integer ath the i position
     */
    public static Set<Integer> contextSetOf(int i)
    {
        return IntStream.rangeClosed(1,2*i-1)
                .collect(HashSet::new, HashSet::add, HashSet::addAll);
    }

    public static int contextSetLimit(int i)
    {
        return 2*i-1;
    }
}
