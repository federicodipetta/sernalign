package it.unicam.cs.bdslab.sernalign.tests;

import it.unicam.cs.bdslab.sernalign.models.StructuralSequence;
import it.unicam.cs.bdslab.sernalign.models.StructuralSequenceAligner2;
import it.unicam.cs.bdslab.sernalign.models.utils.Printer;
import it.unicam.cs.bdslab.sernalign.tests.utlis.RandomSQBuilder;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StructuralSequenceAligner2Test {

    @Test
    public void optimalAlignmentForIdenticalSequences() {
        StructuralSequence s1 = new StructuralSequence(new int[]{1, 2, 3, 4});
        StructuralSequence s2 = new StructuralSequence(new int[]{1, 2, 3, 4});

        StructuralSequenceAligner2 aligner = new StructuralSequenceAligner2(s1, s2);

        assertEquals(0, aligner.getDistance());
        assertTrue(aligner.getEditOperations().stream().allMatch(op -> Objects.equals(op.getI(), op.getJ())));
    }

    @Test
    public void optimalAlignmentForCompletelyDifferentSequences() {
        StructuralSequence s1 = new StructuralSequence(new int[]{1, 2, 3, 4});
        StructuralSequence s2 = new StructuralSequence(new int[]{1, 3, 5, 7});

        StructuralSequenceAligner2 aligner = new StructuralSequenceAligner2(s1, s2);

        assertEquals(3, aligner.getDistance());
        assertTrue(aligner.getEditOperations().stream().allMatch(op -> op.getI() != null && op.getJ() != null));
    }

    @Test
    public void optimalAlignmentForEmptySequences() {
        StructuralSequence s1 = new StructuralSequence(new int[]{});
        StructuralSequence s2 = new StructuralSequence(new int[]{});

        StructuralSequenceAligner2 aligner = new StructuralSequenceAligner2(s1, s2);

        assertEquals(0, aligner.getDistance());
        assertTrue(aligner.getEditOperations().isEmpty());
    }

    @Test
    public void optimalAlignmentForSequenceAndEmptySequence() {
        StructuralSequence s1 = new StructuralSequence(new int[]{1, 2, 3, 4});
        StructuralSequence s2 = new StructuralSequence(new int[]{});

        StructuralSequenceAligner2 aligner = new StructuralSequenceAligner2(s1, s2);
        Printer.printMatrix(aligner.getMatrix());
        Printer.printEditOperations(aligner.getEditOperations());
        assertEquals(4, aligner.getDistance());
        assertTrue(aligner.getEditOperations().stream().allMatch(op -> op.getI() == null));
    }
    @Test
    public void optimalAlignmentfor() {
        StructuralSequence s1 = new StructuralSequence(new int[]{1, 2, 3,4,5});
        StructuralSequence s2 = new StructuralSequence(new int[]{1,1,2,3,4});

        StructuralSequenceAligner2 aligner = new StructuralSequenceAligner2(s1, s2);
        Printer.printMatrix(aligner.getMatrix());

        assertEquals(2, aligner.getDistance());
    }

    @Test
    public void randomTests(){
        Random random = new Random(3);
        for(int i = 0; i < 10; i++){
            int length1 = random.nextInt(10)+1;
            int length2 = random.nextInt(10)+1;
            StructuralSequence s1 = RandomSQBuilder.buildRandomSQ(length1,1);
            StructuralSequence s2 = RandomSQBuilder.buildRandomSQ(length2,200);
            //s1 > s2
            s1 = s1.getStructuralSequence().length > s2.getStructuralSequence().length ? s1 : s2;
            s2 = s1.getStructuralSequence().length > s2.getStructuralSequence().length ? s2 : s1;
            StructuralSequenceAligner2 aligner = new StructuralSequenceAligner2(s1,s2);
            System.out.println("Test "+i);
            Printer.printTest(aligner,s1,s2);
        }
    }
}