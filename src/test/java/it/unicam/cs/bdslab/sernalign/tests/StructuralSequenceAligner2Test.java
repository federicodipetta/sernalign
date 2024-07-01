package it.unicam.cs.bdslab.sernalign.tests;

import it.unicam.cs.bdslab.sernalign.models.AlignerChecker;
import it.unicam.cs.bdslab.sernalign.models.StructuralSequence;
import it.unicam.cs.bdslab.sernalign.models.StructuralSequenceAligner;
import it.unicam.cs.bdslab.sernalign.models.StructuralSequenceAligner2;
import it.unicam.cs.bdslab.sernalign.models.utils.Printer;
import it.unicam.cs.bdslab.sernalign.tests.utlis.RandomSQBuilder;
import org.junit.jupiter.api.Test;

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
        assertTrue(new AlignerChecker(s1,s2,aligner.getOptimalAlignment()).check());
    }

    @Test
    public void optimalAlignmentForCompletelyDifferentSequences() {
        StructuralSequence s1 = new StructuralSequence(new int[]{1, 2, 3, 4});
        StructuralSequence s2 = new StructuralSequence(new int[]{1, 3, 5, 7});

        StructuralSequenceAligner2 aligner = new StructuralSequenceAligner2(s1, s2);

        assertEquals(3, aligner.getDistance());
        assertTrue(aligner.getEditOperations().stream().allMatch(op -> op.getI() != null && op.getJ() != null));
        assertTrue(new AlignerChecker(s1,s2,aligner.getOptimalAlignment()).check());
    }

    @Test
    public void optimalAlignmentForEmptySequences() {
        StructuralSequence s1 = new StructuralSequence(new int[]{});
        StructuralSequence s2 = new StructuralSequence(new int[]{});

        StructuralSequenceAligner2 aligner = new StructuralSequenceAligner2(s1, s2);

        assertEquals(0, aligner.getDistance());
        assertTrue(aligner.getEditOperations().isEmpty());
        assertTrue(new AlignerChecker(s1,s2,aligner.getOptimalAlignment()).check());
    }

    @Test
    public void optimalAlignmentForSequenceAndEmptySequence() {
        StructuralSequence s1 = new StructuralSequence(new int[]{1, 2, 3, 4});
        StructuralSequence s2 = new StructuralSequence(new int[]{});

        StructuralSequenceAligner2 aligner = new StructuralSequenceAligner2(s1, s2);
        Printer.printMatrix(aligner.getMatrix());
        Printer.printEditOperations(aligner.getEditOperations());
        assertEquals(4, aligner.getDistance());
        assertTrue(aligner.getEditOperations().stream().allMatch(op -> op.getJ() == null));
        assertTrue(new AlignerChecker(s1,s2,aligner.getOptimalAlignment()).check());
    }
    @Test
    public void optimalAlignment1() {
        StructuralSequence s1 = new StructuralSequence(new int[]{1, 2, 3,4,5});
        StructuralSequence s2 = new StructuralSequence(new int[]{1,1,2,3,4});

        StructuralSequenceAligner2 aligner = new StructuralSequenceAligner2(s1, s2);
        Printer.printMatrix(aligner.getMatrix());

        assertEquals(2, aligner.getDistance());
        assertTrue(new AlignerChecker(s1,s2,aligner.getOptimalAlignment()).check());
    }
    @Test
    public void optimalAlignment2() {
        StructuralSequence s1 = new StructuralSequence(new int[]{1, 2});
        StructuralSequence s2 = new StructuralSequence(new int[]{1,3,1,2});

        StructuralSequenceAligner2 aligner = new StructuralSequenceAligner2(s1, s2);
        Printer.printTest(aligner,s1,s2);

        assertEquals(3, aligner.getDistance());
        assertTrue(new AlignerChecker(s1,s2,aligner.getOptimalAlignment()).check());
    }
    @Test
    public void optimalSymmetricResult() {
        StructuralSequence s1 = new StructuralSequence(new int[]{1, 2, 3, 4});
        StructuralSequence s2 = new StructuralSequence(new int[]{1, 1});
        StructuralSequenceAligner2 aligner = new StructuralSequenceAligner2(s1, s2);
        Printer.printTest(aligner,s1,s2);

        StructuralSequenceAligner2 aligner2 = new StructuralSequenceAligner2(s2, s1);
        Printer.printTest(aligner2,s2,s1);


        assertEquals(3, aligner.getDistance());
        assertEquals(3, aligner2.getDistance());
        assertTrue(new AlignerChecker(s1,s2,aligner2.getOptimalAlignment()).check());
        assertTrue(new AlignerChecker(s2,s1,aligner.getOptimalAlignment()).check());
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

            StructuralSequenceAligner aligner = new StructuralSequenceAligner(s2,s1);
            System.out.println("Test "+i);
            Printer.printTest(aligner,s2,s1);
            assertTrue(new AlignerChecker(s2,s1,aligner.getOptimalAlignment()).check());
        }
    }
}