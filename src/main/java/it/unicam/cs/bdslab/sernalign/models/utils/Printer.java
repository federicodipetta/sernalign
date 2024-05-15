package it.unicam.cs.bdslab.sernalign.models.utils;

import it.unicam.cs.bdslab.sernalign.models.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Printer {

    public static void printMatrix(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        // Stampa la parte superiore della matrice
        System.out.print("⎡ ");
        for (int i = 0; i < cols; i++) {
            System.out.print("─ ");
        }
        System.out.println("⎤");

        // Stampa i valori della matrice
        for (int[] row : matrix) {
            System.out.print("⎢ ");
            for (int value : row) {
                System.out.print(value + " ");
            }
            System.out.println("⎢");
        }

        // Stampa la parte inferiore della matrice
        System.out.print("⎣ ");
        for (int i = 0; i < cols; i++) {
            System.out.print("─ ");
        }
        System.out.println("⎦");
    }

    public static void printEditOperations(List<EditOperation> editOperations) {
        for (EditOperation editOperation : editOperations) {
            System.out.print(editOperation);
            System.out.print(" ");
        }
        System.out.println();
    }

    public static void printTest(StructuralSequenceAligner2 aligner , StructuralSequence s1, StructuralSequence s2) {
        printHelper(s1, s2, aligner.getMatrix());
        Printer.printEditOperations(aligner.getEditOperations());
        System.out.println("Distance: "+aligner.getDistance());
    }
    public static void printTest(StructuralSequenceAligner3 aligner , StructuralSequence s1, StructuralSequence s2) {
        printHelper(s1, s2, aligner.getMatrix());
        Printer.printEditOperations(aligner.getOptimalAlignment());
        System.out.println("Distance: "+aligner.getDistance());
    }
    public static void printTest(StructuralSequenceAligner aligner , StructuralSequence s1, StructuralSequence s2) {
        printHelper(s1, s2, aligner.getMatrix());
        Printer.printEditOperations(aligner.getOptimalAlignment());
        System.out.println("Distance: "+aligner.getDistance());
    }

    public static void printTest(IStructuralSequenceAligner aligner , StructuralSequence s1, StructuralSequence s2) {
        printHelper(s1, s2, aligner.getMatrix());
        Printer.printEditOperations(aligner.getOptimalAlignment());
        System.out.println("Distance: "+aligner.getDistance());
    }

    private static void printHelper(StructuralSequence s1, StructuralSequence s2, int[][] matrix) {
        System.out.println("S1: ");
        Arrays.stream(s1.getStructuralSequence()).forEach(x->System.out.print(x+" "));
        System.out.println();
        System.out.println("S2: ");
        Arrays.stream(s2.getStructuralSequence()).forEach(x->System.out.print(x+" "));
        System.out.println();
        Printer.printMatrix(matrix);
    }


}
