package it.unicam.cs.bdslab.sernalign.tests;

import it.unicam.cs.bdslab.sernalign.models.StructuralSequence;
import it.unicam.cs.bdslab.sernalign.models.StructuralSequenceAligner;
import it.unicam.cs.bdslab.sernalign.models.StructuralSequenceAligner3;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StructuralSequenceAligner3Test {
    @Test
    public void testGetOptimalAlignment() {
        StructuralSequence s1 = new StructuralSequence(new int[]{1, 1});
        StructuralSequence s2 = new StructuralSequence(new int[]{1, 3,5,7});

        StructuralSequenceAligner3 aligner = new StructuralSequenceAligner3(s1, s2);

        assertEquals(3, aligner.getDistance());
    }


}
