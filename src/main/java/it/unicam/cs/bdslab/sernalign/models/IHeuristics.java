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
        } else if(similar2(s1.getStructuralSequence()[i], s2.getStructuralSequence()[j], i, j)){
            return 1;
        }else {
            return 2;
        }
    };

    private static boolean similar(int xi, int yj, int i, int j) {
        return Math.abs(xi - yj) <= Math.abs( 2*i-1/xi - 2*j-1/yj);
    }
    private static boolean similar2(int xi, int yj, int i, int j) {
        return Math.abs(xi/(2.0*i-1) - yj/(2.0*j-1)) <= 0.2;
    }

}
