package it.unicam.cs.bdslab.sernalign.tests;

import it.unicam.cs.bdslab.sernalign.models.StructuralSequence;
import it.unicam.cs.bdslab.sernalign.models.StructuralSequenceAligner;
import it.unicam.cs.bdslab.sernalign.models.StructuralSequenceAligner2;
import it.unicam.cs.bdslab.sernalign.models.StructuralSequenceAligner3;
import it.unicam.cs.bdslab.sernalign.models.utils.Printer;
import it.unicam.cs.bdslab.sernalign.tests.utlis.RandomSQBuilder;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class StructuralSequenceComparisonTest {

    @Test
    public void testRandomComparison() {
        Random random = new Random(3);
        List<Integer> distance1 = new ArrayList<>();
        List<Integer> distance2 = new ArrayList<>();
        List<Integer> distance3 = new ArrayList<>();
        for(int i = 0; i < 50; i++){

            int length1 = random.nextInt(15)+1;
            int length2 = random.nextInt(15)+1;
            StructuralSequence s1 = RandomSQBuilder.buildRandomSQ(length1,random.nextInt());
            StructuralSequence s2 = RandomSQBuilder.buildRandomSQ(length2,random.nextInt());
            //s1 > s2
            if (s2.getStructuralSequence().length > s1.getStructuralSequence().length) {
                StructuralSequence temp = s1;
                s1 = s2;
                s2 = temp;
            }
            StructuralSequenceAligner aligner1= new StructuralSequenceAligner(s1,s2);
            StructuralSequenceAligner2 aligner2 = new StructuralSequenceAligner2(s1,s2);
            StructuralSequenceAligner3 aligner3 = new StructuralSequenceAligner3(s1,s2);
            distance1.add(aligner1.getDistance());
            distance2.add(aligner2.getDistance());
            distance3.add(aligner3.getDistance());
            System.out.println("Test "+i);
            System.out.println("Aligner 1");
            Printer.printTest(aligner1,s1,s2);
            System.out.println("Aligner 2");
            Printer.printTest(aligner2,s1,s2);
            System.out.println("Aligner 3");
            Printer.printTest(aligner3,s1,s2);
        }
        System.out.println("---------------------------------------------------------");
        System.out.println("FINAL RESULT COMPARISON ");
        System.out.println("---------------------------------------------------------");
        System.out.println("Aligner 1");
        System.out.println("Average distance: "+distance1.stream().mapToInt(Integer::intValue).average().orElse(0));
        distance1.forEach(x-> System.out.print(x+ " "));
        System.out.println();
        System.out.println("---------------------------------------------------------");
        System.out.println("Aligner 2");
        System.out.println("Average distance: "+distance2.stream().mapToInt(Integer::intValue).average().orElse(0));
        distance2.forEach(x-> System.out.print(x+ " "));
        System.out.println();
        System.out.println("---------------------------------------------------------");
        System.out.println("Aligner 3");
        System.out.println("Average distance: "+distance3.stream().mapToInt(Integer::intValue).average().orElse(0));
        distance3.forEach(x-> System.out.print(x+ " "));
        System.out.println();



        }

    }

