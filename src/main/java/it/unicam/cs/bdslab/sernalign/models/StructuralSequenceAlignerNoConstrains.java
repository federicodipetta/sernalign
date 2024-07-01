package it.unicam.cs.bdslab.sernalign.models;

import it.unicam.cs.bdslab.sernalign.models.utils.Operation;

import java.util.List;

public class StructuralSequenceAlignerNoConstrains implements IStructuralSequenceAligner {
    private StructuralSequence s1;
    private StructuralSequence s2;
    private int[][] matrix;
    private List<EditOperation> editOperations;
    private Operation[][] traceback;

    public StructuralSequenceAlignerNoConstrains(StructuralSequence s1, StructuralSequence s2) {
        this.s1 = s1;
        this.s2 = s2;
        this.matrix = new int[s1.getStructuralSequence().length + 1][s2.getStructuralSequence().length + 1];
        this.traceback = new Operation[s1.getStructuralSequence().length + 1][s2.getStructuralSequence().length + 1];
        align();
    }

    private void align(){
        for (int i = 0; i < s1.getStructuralSequence().length + 1; i++) {
            matrix[i][0] = i;
            traceback[i][0] = Operation.Delete;
        }
        for (int j = 0; j < s2.getStructuralSequence().length + 1; j++) {
            matrix[0][j] = j;
            traceback[0][j] = Operation.Insert;
        }
        int[] s1 = this.s1.getStructuralSequence();
        int[] s2 = this.s2.getStructuralSequence();
        for (int i = 1; i < s1.length ; i++)
            for (int j = 1; j < s2.length; j++){
                int cost = s1[i] == s2[j] ? 0 : 1;
                int min = Math.min(matrix[i - 1][j - 1] + cost, Math.min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1));
                matrix[i][j] = min;
                if (min == matrix[i - 1][j - 1] + cost) {
                    traceback[i][j] = Operation.Match;
                } else if (min == matrix[i - 1][j] + 1) {
                    traceback[i][j] = Operation.Delete;
                } else {
                    traceback[i][j] = Operation.Insert;
                }
            }
        // traceback
        int i = s1.length-1;
        int j = s2.length-1;
        while (i > 0 || j > 0) {
            switch (traceback[i][j]) {
                case Match:
                    if (s1[i] == s2[j]) {
                        editOperations.add(new EditOperation(Operation.Match, s1[i], s2[j]));
                    } else {
                        editOperations.add(new EditOperation(Operation.Replace, s1[i], s2[j]));
                    }
                    i--;
                    j--;
                    break;
                case Insert:
                    editOperations.add(new EditOperation(Operation.Insert, s1[i], s2[j]));
                    j--;
                    break;
                case Delete:
                    editOperations.add(new EditOperation(Operation.Delete, s1[i], s2[j]));
                    i--;
                    break;
            }
        }


    }

    @Override
    public int[][] getMatrix() {
        return new int[0][];
    }

    @Override
    public int getDistance() {
        return 0;
    }

    @Override
    public List<EditOperation> getOptimalAlignment() {
        return null;
    }
}
