package it.unicam.cs.bdslab.sernalign.models;

import it.unicam.cs.bdslab.sernalign.models.utils.Operation;

import java.util.ArrayList;
import java.util.List;

/**
 * this class is used to align two sequences of integers using a heuristic approach
 */
public class StructuralSequenceAlignerHeuristic implements IStructuralSequenceAligner{

    private int[][] matrix;
    private IHeuristics heuristics;
    private List<EditOperation> editOperations;
    private StructuralSequence s1;
    private StructuralSequence s2;
    private int defaultCost = 2;
    private Operation[][] traceback;
    public StructuralSequenceAlignerHeuristic(StructuralSequence s1, StructuralSequence s2, IHeuristics heuristics, int defualtCost) {
        this.s1 = s1;
        this.s2 = s2;
        this.heuristics = heuristics;
        this.defaultCost = defualtCost;
        this.matrix = new int[s1.getStructuralSequence().length + 1][s2.getStructuralSequence().length + 1];
        this.traceback = new Operation[s1.getStructuralSequence().length + 1][s2.getStructuralSequence().length + 1];
        align();
    }
    public StructuralSequenceAlignerHeuristic(StructuralSequence s1, StructuralSequence s2) {
        this.s1 = s1;
        this.s2 = s2;
        this.heuristics = IHeuristics.DEFAULT;
        this.matrix = new int[s1.getStructuralSequence().length + 1][s2.getStructuralSequence().length + 1];
        this.traceback = new Operation[s1.getStructuralSequence().length + 1][s2.getStructuralSequence().length + 1];
        align();
    }


    private void align(){
        for (int i = 0; i < s1.getStructuralSequence().length + 1; i++) {
            matrix[i][0] = defaultCost * i;
            traceback[i][0] = Operation.Delete;
        }
        for (int j = 0; j < s2.getStructuralSequence().length + 1; j++) {
            matrix[0][j] = defaultCost * j;
            traceback[0][j] = Operation.Insert;
        }

        for (int i = 1; i < s1.getStructuralSequence().length + 1; i++) {
            for (int j = 1; j < s2.getStructuralSequence().length + 1; j++) {
                int cost = heuristics.apply(s1, s2, i - 1, j - 1);
                int min = Math.min(matrix[i - 1][j - 1] + cost, Math.min(matrix[i - 1][j] + defaultCost, matrix[i][j - 1] + defaultCost));
                matrix[i][j] = min;
                if (min == matrix[i - 1][j - 1] + cost) {
                    traceback[i][j] = Operation.Match;
                } else if (min == matrix[i - 1][j] + defaultCost) {
                    traceback[i][j] = Operation.Delete;
                } else {
                    traceback[i][j] = Operation.Insert;
                }
            }
        }
        editOperations = new ArrayList<>();
        int i = s1.getStructuralSequence().length;
        int j = s2.getStructuralSequence().length;
        while (i > 0 || j > 0) {
            switch (traceback[i][j]) {
                case Match:
                    editOperations.add(new EditOperation(s1.getStructuralSequence()[i - 1], s2.getStructuralSequence()[j - 1]));
                    i--;
                    j--;
                    break;
                case Insert:
                    editOperations.add(new EditOperation(null, s2.getStructuralSequence()[j - 1]));
                    j--;
                    break;
                case Delete:
                    editOperations.add(new EditOperation(s1.getStructuralSequence()[i - 1], null));
                    i--;
                    break;
            }
        }
        // reduce the memory usage
        traceback = null;

    }
    @Override
    public int[][] getMatrix() {
        return matrix;
    }

    @Override
    public int getDistance() {
        return matrix[matrix.length - 1][matrix[matrix.length - 1].length - 1];
    }

    @Override
    public List<EditOperation> getOptimalAlignment() {
        return new ArrayList<>(editOperations);
    }
}
