package it.unicam.cs.bdslab.sernalign.models;

import it.unicam.cs.bdslab.sernalign.models.utils.ContextSet;

import java.util.Arrays;

@FunctionalInterface
public interface IHeuristics {
    /**
     * This method decide in a heuristic way the cost of the operation to apply for the match or mismatch of two elements
     * @param s1 the first sequence
     * @param s2 the second sequence
     * @param i the index of the first sequence
     * @param j the index of the second sequence
     * @return the cost of the operation
     */
    int apply(StructuralSequence s1, StructuralSequence s2, int i, int j);
    /**
     * This is the default implementation of the heuristic function
     */
    public static IHeuristics DEFAULT = (s1, s2, i, j) -> {
        if (i == j && s1.getStructuralSequence()[i] == s2.getStructuralSequence()[j]
                && Arrays.equals(s1.getStructuralSequence(), 0, i, s2.getStructuralSequence(), 0, j)) {
            return 0;
        } else if (s1.getStructuralSequence()[i] == 1 && s2.getStructuralSequence()[j] == 1) {
            return 0;
        } else if (s1.getStructuralSequence()[i] == 2 * i - 1 && s2.getStructuralSequence()[j] == 2 * j - 1) {
            return 0;
        } else if (s1.getStructuralSequence()[i] == s2.getStructuralSequence()[j]) {
            return 1;
        } else {
            return 2;
        }
    };

}
