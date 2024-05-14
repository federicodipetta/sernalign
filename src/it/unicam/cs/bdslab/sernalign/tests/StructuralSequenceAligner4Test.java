package it.unicam.cs.bdslab.sernalign.tests;

import it.unicam.cs.bdslab.sernalign.models.AlignerChecker;
import it.unicam.cs.bdslab.sernalign.models.StructuralSequence;
import it.unicam.cs.bdslab.sernalign.models.StructuralSequenceAligner4;
import it.unicam.cs.bdslab.sernalign.models.utils.Printer;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StructuralSequenceAligner4Test {

    @Test
    public void optimalAlignmentForIdenticalSequences() {
        StructuralSequence s1 = new StructuralSequence(new int[]{1, 2, 3, 4});
        StructuralSequence s2 = new StructuralSequence(new int[]{1, 2, 3, 4});

        StructuralSequenceAligner4 aligner = new StructuralSequenceAligner4(s1, s2);

        assertEquals(0, aligner.getDistance());
        assertTrue(aligner.getOptimalAlignment().stream().allMatch(op -> Objects.equals(op.getI(), op.getJ())));
    }

    @Test
    public void optimalAlignmentForCompletelyDifferentSequences() {
        StructuralSequence s1 = new StructuralSequence(new int[]{1, 2, 3, 4});
        StructuralSequence s2 = new StructuralSequence(new int[]{1, 3, 5, 7});

        StructuralSequenceAligner4 aligner = new StructuralSequenceAligner4(s1, s2);
        assertTrue(new AlignerChecker(s1,s2,aligner.getOptimalAlignment()).check());

    }

    @Test
    public void optimalAlignmentForEmptySequences() {
        StructuralSequence s1 = new StructuralSequence(new int[]{});
        StructuralSequence s2 = new StructuralSequence(new int[]{});

        StructuralSequenceAligner4 aligner = new StructuralSequenceAligner4(s1, s2);
        assertTrue(new AlignerChecker(s1,s2,aligner.getOptimalAlignment()).check());

    }

    @Test
    public void optimalAlignmentForSequenceAndEmptySequence() {
        StructuralSequence s1 = new StructuralSequence(new int[]{1, 2, 3, 4});
        StructuralSequence s2 = new StructuralSequence(new int[]{});

        StructuralSequenceAligner4 aligner = new StructuralSequenceAligner4(s1, s2);

        assertTrue(aligner.getOptimalAlignment().stream().allMatch(op -> op.getJ() == null));
    }
    @Test
    public void optimalAlignmentfor() {
        StructuralSequence s1 = new StructuralSequence(new int[]{1, 2, 3,4,5});
        StructuralSequence s2 = new StructuralSequence(new int[]{1,1,2,3,4});

        StructuralSequenceAligner4 aligner = new StructuralSequenceAligner4(s1, s2);
        //Printer.printMatrix(aligner.getMatrix());
        Printer.printTest(aligner,s1,s2);
        assertTrue(new AlignerChecker(s1,s2,aligner.getOptimalAlignment()).check());
       // assertEquals(2, aligner.getDistance());

    }

    @Test
    public void optimalAlignmentfor2() {
        StructuralSequence s1 = new StructuralSequence(new int[]{1,2,3,1,9});
        StructuralSequence s2 = new StructuralSequence(new int[]{1});

        StructuralSequenceAligner4 aligner = new StructuralSequenceAligner4(s1, s2);
        Printer.printTest(aligner,s1,s2);
        assertTrue(new AlignerChecker(s1,s2,aligner.getOptimalAlignment()).check());

    }
}
