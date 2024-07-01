package it.unicam.cs.bdslab.sernalign.models;

import it.unicam.cs.bdslab.sernalign.models.utils.ContextSet;
import it.unicam.cs.bdslab.sernalign.models.utils.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * This class is used to align two structural sequences, aware of the actual index of the edits
 */
public class StructuralSequenceAligner3 implements IStructuralSequenceAligner{
    private List<EditOperation> operations;
    private StructuralSequence s1;
    private StructuralSequence s2;

    private int[][] matrix;

    private int[][] actualIndexMatrix;

    private Operation[][] operationsMatrix;

    public StructuralSequenceAligner3(StructuralSequence s1, StructuralSequence s2) {
        this.operations = new ArrayList<>();
        this.s1 = s1;
        this.s2 = s2;
        align();
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public List<EditOperation> getOptimalAlignment() {
        return operations;
    }

    public int getDistance() {
        return matrix[s1.getStructuralSequence().length][s2.getStructuralSequence().length];
    }

    private void align(){
        matrix = initMatrix();
        actualIndexMatrix = initActualIndexMatrix();
        operationsMatrix = initOperationsMatrix();
        for ( int i = 1; i < matrix.length; i++){
            for( int j = 1; j < matrix[i].length; j++ ){
                Operation getOperation = getOperation(s1.getStructuralSequence()[i-1],s2.getStructuralSequence()[j-1],i,j);
                applyOperation(getOperation,s1.getStructuralSequence()[i-1],s2.getStructuralSequence()[j-1],i,j);
            }
        }
        operations = generateEditOperations();
    }

    private void applyOperation(Operation operation, int xi, int yj, int i, int j){
        if(validateOperation(operation,xi,yj,i,j)){
            switch (operation){
                case Match -> {
                    matrix[i][j] = matrix[i-1][j-1];
                    actualIndexMatrix[i][j] = actualIndexMatrix[i-1][j-1]+1;
                    operationsMatrix[i][j] = Operation.Match;
                }
                case Replace -> {
                    matrix[i][j] = matrix[i-1][j-1]+1;
                    actualIndexMatrix[i][j] = actualIndexMatrix[i-1][j-1]+1;
                    operationsMatrix[i][j] = Operation.Replace;
                }
                case Insert -> {
                    matrix[i][j] = matrix[i][j-1]+1;
                    actualIndexMatrix[i][j] = actualIndexMatrix[i][j-1]+1;
                    operationsMatrix[i][j] = Operation.Insert;
                }
                case Delete -> {
                    matrix[i][j] = matrix[i-1][j]+1;
                    actualIndexMatrix[i][j] = actualIndexMatrix[i-1][j];
                    operationsMatrix[i][j] = Operation.Delete;
                }
            }
        }
    }

    private Operation getOperation(int xi,int yj ,int i, int j ){
        int min = Integer.MAX_VALUE;
        Operation op = Operation.Delete;
        if(xi == yj && validateOperation(Operation.Match,xi,yj,i,j) && min > matrix[i-1][j-1]){
            min = matrix[i-1][j-1];
            op = Operation.Match;
        }


        if(min > matrix[i-1][j-1] + 1 && validateOperation(Operation.Replace,xi,yj,i,j)){
            min = matrix[i-1][j-1] + 1;
            op = Operation.Replace;
        }


        if(min > matrix[i-1][j] + 1 && validateOperation(Operation.Delete,xi,yj,i,j)){
            min = matrix[i-1][j] + 1;
            op = Operation.Delete;
        }

        if(min > matrix[i][j-1] + 1 && validateOperation(Operation.Insert,xi,yj,i,j)){
            min = matrix[i][j-1] + 1;
            op = Operation.Insert;
        }


        return op;
    }

    private int getMin(int xi, int yj, int i, int j) {
        //doesn't work
        int replace = Integer.MAX_VALUE;
        int delete = Integer.MAX_VALUE;
        int insert = Integer.MAX_VALUE;

        if(xi == yj && validateOperation(Operation.Match,xi,yj,i,j))
            replace = matrix[i-1][j-1];


        if(xi != yj && validateOperation(Operation.Replace,xi,yj,i,j))
            replace = matrix[i-1][j-1] + 1;


        if(validateOperation(Operation.Delete,xi,yj,i,j))
            delete = matrix[i-1][j] + 1;


        if(validateOperation(Operation.Insert,xi,yj,i,j))
            insert = matrix[i][j-1] + 1;


        return IntStream.of(replace,delete,insert).min().getAsInt();

    }

    private boolean validateOperation(Operation operation, int xi,int yj, int i, int j){
        return switch (operation) {
            case Match,Replace -> true//ContextSet.contextSetLimit(actualIndexMatrix[i-1][j-1]+1)>=xi
                    && ContextSet.contextSetLimit(actualIndexMatrix[i-1][j-1]+1) >=yj;
            case Insert -> ContextSet.contextSetLimit(actualIndexMatrix[i][j-1]+1)>=yj;
            case Delete -> true;//ContextSet.contextSetLimit(actualIndexMatrix[i-1][j])>=xi;
        };

    }

    private List<EditOperation> generateEditOperations(){
        List<EditOperation> editOperations = new ArrayList<>();
        int i = s1.getStructuralSequence().length;
        int j = s2.getStructuralSequence().length;
        while(i > 0 || j > 0){
            Operation operation = operationsMatrix[i][j];
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


    private int[][] initMatrix(){
        int[][] matrix = new int[s1.getStructuralSequence().length + 1][s2.getStructuralSequence().length + 1];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][0] = i;
        }
        for (int i = 0; i < matrix[0].length; i++) {
            matrix[0][i] = i;
        }
        return matrix;
    }

    private int[][] initActualIndexMatrix(){
        int[][] matrix = new int[s1.getStructuralSequence().length + 1][s2.getStructuralSequence().length + 1];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i][0] = 1;
        }
        for (int i = 0; i < matrix[0].length; i++) {
            matrix[0][i] = i+1;
        }
        return matrix;
    }

    private Operation[][]initOperationsMatrix(){
        Operation[][] matrixOp = new Operation[s1.getStructuralSequence().length + 1][s2.getStructuralSequence().length + 1];
        for (int i = 0; i < matrixOp.length; i++) {
            matrixOp[i][0] = Operation.Delete;
        }
        for (int i = 0; i < matrixOp[0].length; i++) {
            matrixOp[0][i] = Operation.Insert;
        }
        return matrixOp;
    }





    public void printMatrix(int[][] matrix){
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }

}
