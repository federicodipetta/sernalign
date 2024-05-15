package it.unicam.cs.bdslab.sernalign.models;

import java.util.ArrayList;
import java.util.List;

public class StructuralSequenceAligner5 implements IStructuralSequenceAligner {

    private List<EditOperation> editOperations;
    private int distance;
    private StructuralSequence s1;
    private StructuralSequence s2;

    private int[][] matrix;

    public StructuralSequenceAligner5(StructuralSequence s1, StructuralSequence s2) {
        this.editOperations = new ArrayList<>();
        this.distance = 0;
        //sia s1 la più corta tra le sequenze strutturali
        this.s1 = s1;
        this.s2 = s2;

        align(this.s1,this.s2);
    }

    private void align(StructuralSequence s1,StructuralSequence s2){
        //risolvo la prima volta la matrice con prendendo le sottosequenze di s1 ed s2 di lunghezza s1
        if(s1.getStructuralSequence().length < s2.getStructuralSequence().length){
            StructuralSequence subSequence = getSubSequence(s2,s1.getStructuralSequence().length);
            StructuralSequenceAligner4 aligner = new StructuralSequenceAligner4(s1,subSequence);
            this.matrix = aligner.getMatrix();
            this.editOperations = aligner.getOptimalAlignment();
            //aggiungo le operazioni di inserimento per le lettere rimanenti
            for(int i = s1.getStructuralSequence().length; i < s2.getStructuralSequence().length; i++){
                this.editOperations.add(new EditOperation(null,s2.getStructuralSequence()[i]));
            }

        }else {
            StructuralSequence subSequence = getSubSequence(s1,s2.getStructuralSequence().length);
            StructuralSequenceAligner4 aligner = new StructuralSequenceAligner4(s2,subSequence);
            this.matrix = aligner.getMatrix();
            this.editOperations = aligner.getOptimalAlignment();
            //aggiungo le operazioni di inserimento per le lettere rimanenti
            for(int i = s2.getStructuralSequence().length; i < s1.getStructuralSequence().length; i++){
                this.editOperations.add(new EditOperation(s1.getStructuralSequence()[i],null));
            }
        }
        this.distance = matrix[matrix.length-1][matrix[0].length-1]
                + Math.abs(s1.getStructuralSequence().length - s2.getStructuralSequence().length);

    }

    private StructuralSequence getSubSequence(StructuralSequence s ,int end){
        int[] subSequence = new int[end];
        for(int i = 0; i < end; i++){
            subSequence[i] = s.getStructuralSequence()[i];
        }
        return new StructuralSequence(subSequence);
    }

    public int[][] getMatrix() {
        return matrix;
    }

    public List<EditOperation> getEditOperations() {
        return editOperations;
    }

    public int getDistance() {
        return distance;
    }

    @Override
    public List<EditOperation> getOptimalAlignment() {
        return getEditOperations();
    }

    public StructuralSequence getS1() {
        return s1;
    }

    public StructuralSequence getS2() {
        return s2;
    }

}
