package it.unicam.cs.bdslab.sernalign.models;

import it.unicam.cs.bdslab.sernalign.models.utils.ContextSet;
import it.unicam.cs.bdslab.sernalign.models.utils.Operation;

import java.util.ArrayList;
import java.util.List;

public class StructuralSequenceAligner4 implements IStructuralSequenceAligner{
    StructuralSequence s1;
    StructuralSequence s2;

    int[][] matrix;
    int[][] P;
    int[][] Q;

    Operation[][] traceback;

    List<EditOperation> editOperations;

    GFunction g;

    @Override
    public int[][] getMatrix() {
        return matrix;
    }

    @Override
    public int getDistance() {
        return matrix
                [matrix.length - 1]
                [matrix[matrix.length - 1].length - 1];
    }
    public StructuralSequenceAligner4(StructuralSequence s1, StructuralSequence s2) {
        this(s1, s2, 10, 1);
    }

    public StructuralSequenceAligner4(StructuralSequence s1, StructuralSequence s2, int a,int b) {
        this.s1 = s1;
        this.s2 = s2;
        this.editOperations = new StructuralSequenceAligner(s1, s2).getOptimalAlignment();
        this.matrix = new int[s1.getStructuralSequence().length + 1][s2.getStructuralSequence().length + 1];
        this.P = new int[s1.getStructuralSequence().length + 1][s2.getStructuralSequence().length + 1];
        this.Q = new int[s1.getStructuralSequence().length + 1][s2.getStructuralSequence().length + 1];
        this.g = new GFunction(a,b);
        this.traceback = new Operation[s1.getStructuralSequence().length + 1][s2.getStructuralSequence().length + 1];
        align(s1,s2,a,b);
    }

    private void align(StructuralSequence s1,StructuralSequence s2,int a, int b){

        for(int i = 1 ; i < s1.getStructuralSequence().length + 1; i++){
            this.traceback[i][0] = Operation.Delete;
            matrix[i][0] = g.G(i);
            P[i][0] = 0;
            Q[i][0] = Short.MAX_VALUE;
        }

        for(int j = 1 ; j < s2.getStructuralSequence().length + 1; j++){
            this.traceback[0][j] = Operation.Insert;
            matrix[0][j] = g.G(j);
            P[0][j] = Short.MAX_VALUE;
            Q[0][j] = 0;
        }

        for (int i = 1; i < s1.getStructuralSequence().length + 1; i++) {
            for (int j = 1; j < s2.getStructuralSequence().length + 1; j++) {
                int xi = s1.getStructuralSequence()[i-1];
                int yj = s2.getStructuralSequence()[j-1];
                P[i][j] = Math.min(matrix[i - 1][j] + g.G(1), P[i - 1][j] + b);
                Q[i][j] = Math.min(matrix[i][j - 1] + g.G(1), Q[i][j - 1] + b);
                int match = xi == yj ? 0 : 1;
                //controllo che le operazioni siano applicabili
                if(!validateOperation(Operation.Match,xi,yj,i,j))
                    match = Short.MAX_VALUE;
                if(!validateOperation(Operation.Insert,xi,yj,i,j))
                    P[i][j] = Short.MAX_VALUE;
                if(!validateOperation(Operation.Delete,xi,yj,i,j))
                    Q[i][j] = Short.MAX_VALUE;
                matrix[i][j] = Math.min(match + matrix[i - 1][j - 1], Math.min(P[i][j], Q[i][j]));
                traceback(match + matrix[i-1][j-1],P[i][j],Q[i][j],i,j);
            }
        }
        this.editOperations = generateEditOperations();
    }

    private void traceback(int match, int insert, int delete,int i,int j){
        if(match <= insert && match <= delete)
            traceback[i][j] = Operation.Match;
        else if(insert <= match && insert <= delete)
            traceback[i][j] = Operation.Insert;
        else
            traceback[i][j] = Operation.Delete;
    }

    private boolean validateOperation(Operation operation, int xi,int yj, int i, int j){
        return switch (operation) {
            case Match,Replace -> ContextSet.contextSetLimit(j)>=xi
                    && ContextSet.contextSetLimit(i) >=yj;
            case Insert -> ContextSet.contextSetLimit(i)>=yj;
            case Delete -> ContextSet.contextSetLimit(j)>=xi;
        };

    }

    private List<EditOperation> generateEditOperations(){
        List<EditOperation> editOperations = new ArrayList<>();
        int i = s1.getStructuralSequence().length;
        int j = s2.getStructuralSequence().length;
        while(i > 0 || j > 0){
            Operation operation = traceback[i][j];
            switch (operation){
                case Match, Replace -> {
                    editOperations.add(new EditOperation(s1.getStructuralSequence()[i-1], s2.getStructuralSequence()[j-1]));
                    i--;
                    j--;
                }
                case Insert -> {
                    editOperations.add(new EditOperation(null,s2.getStructuralSequence()[j-1]));
                    j--;
                }
                case Delete -> {
                    editOperations.add(new EditOperation(s1.getStructuralSequence()[i-1],null));
                    i--;
                }
            }
        }
        return editOperations.reversed();
    }



    @Override
    public List<EditOperation> getOptimalAlignment() {
        return this.editOperations;
    }
}
