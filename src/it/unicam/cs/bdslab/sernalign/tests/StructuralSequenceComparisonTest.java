package it.unicam.cs.bdslab.sernalign.tests;

import it.unicam.cs.bdslab.sernalign.models.*;
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
        Random random = new Random(10);
        List<Integer> distance1 = new ArrayList<>();
        List<Integer> distance2 = new ArrayList<>();
        List<Integer> distance3 = new ArrayList<>();
        List<Integer> distance4 = new ArrayList<>();
        double average1 = 0 , average2 = 0 ,averageSum = 0;
        double averageTime1 = 0 , averageTime2 = 0 ,averageTime3 = 0;
        int n= 500;
        for(int i = 0; i < n; i++){

            int length1 = random.nextInt(200)+1;
            int length2 = random.nextInt(200)+1;
            StructuralSequence s1 = RandomSQBuilder.buildRandomSQ(length1,random.nextInt());
            StructuralSequence s2 = RandomSQBuilder.buildRandomSQ(length2,random.nextInt());
            average1 += s1.getStructuralSequence().length;
            average2 += s2.getStructuralSequence().length;
            averageSum += s1.getStructuralSequence().length + s2.getStructuralSequence().length;
            //s1 > s2
            if (s2.getStructuralSequence().length > s1.getStructuralSequence().length) {
                StructuralSequence temp = s1;
                s1 = s2;
                s2 = temp;
            }
            double time = System.currentTimeMillis();
            StructuralSequenceAligner aligner1= new StructuralSequenceAligner(s1,s2);
            time = System.currentTimeMillis() - time;
            averageTime1 += time;
            time = System.currentTimeMillis();
            StructuralSequenceAligner2 aligner2 = new StructuralSequenceAligner2(s1,s2);
            time = System.currentTimeMillis() - time;
            averageTime2 += time;
            time = System.currentTimeMillis();
            StructuralSequenceAligner3 aligner3 = new StructuralSequenceAligner3(s1,s2);
            time = System.currentTimeMillis() - time;
            averageTime3 += time;

            StructuralSequenceAligner4 aligner4 = new StructuralSequenceAligner4(s1,s2);
            distance1.add(aligner1.getDistance());
            distance2.add(aligner2.getDistance());
            distance3.add(aligner3.getDistance());
            distance4.add(aligner4.getDistance());
//            System.out.println("Test "+i);
//            System.out.println("Aligner 1");
//            Printer.printTest(aligner1,s1,s2);
//            System.out.println("Aligner 2");
//            Printer.printTest(aligner2,s1,s2);
//            System.out.println("Aligner 3");
//            Printer.printTest(aligner3,s1,s2);
        }
        System.out.println("---------------------------------------------------------");
        System.out.println("FINAL RESULT COMPARISON ");
        System.out.println("Average length of s1: "+average1/n);
        System.out.println("Average length of s2: "+average2/n);
        System.out.println("Average length of s1 + s2: "+averageSum/n);
        System.out.println("---------------------------------------------------------");
        System.out.println("Aligner 1");
        System.out.println("Average distance: "+distance1.stream().mapToInt(Integer::intValue).average().orElse(0));
        System.out.println("Average time: "+averageTime1/n);
        distance1.forEach(x-> System.out.print(x+ " "));
        System.out.println();
        System.out.println("---------------------------------------------------------");
        System.out.println("Aligner 2");
        System.out.println("Average distance: "+distance2.stream().mapToInt(Integer::intValue).average().orElse(0));
        System.out.println("Average time: "+averageTime2/n);
        distance2.forEach(x-> System.out.print(x+ " "));
        System.out.println();
        System.out.println("---------------------------------------------------------");
        System.out.println("Aligner 3");
        System.out.println("Average distance: "+distance3.stream().mapToInt(Integer::intValue).average().orElse(0));
        System.out.println("Average time: "+averageTime3/n);
        distance3.forEach(x-> System.out.print(x+ " "));
        System.out.println();
        System.out.println("---------------------------------------------------------");
        System.out.println("Aligner 4");
        System.out.println("Average distance: "+distance4.stream().mapToInt(Integer::intValue).average().orElse(0));
        System.out.println("Average time: "+averageTime3/n);
        distance4.forEach(x-> System.out.print(x+ " "));

        System.out.println();



        }

    }

