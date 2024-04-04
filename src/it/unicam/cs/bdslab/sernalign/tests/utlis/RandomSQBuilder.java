package it.unicam.cs.bdslab.sernalign.tests.utlis;

import it.unicam.cs.bdslab.sernalign.models.StructuralSequence;
import it.unicam.cs.bdslab.sernalign.models.utils.ContextSet;

import java.util.Random;

public class RandomSQBuilder {


    public static StructuralSequence buildRandomSQ(int length,int seed) {
        Random random = new Random(seed);
        int[] sq = new int[length];
        for (int i = 0; i < length; i++) {
            sq[i] = ContextSet
                    .contextSetOf(i+1)
                    .stream()
                    .skip(
                            random.nextInt(ContextSet.contextSetOf(i+1).size())
                    )
                    .findFirst().orElseThrow();
        }
        return new StructuralSequence(sq);
    }
}
