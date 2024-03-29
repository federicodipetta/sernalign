package it.unicam.cs.bdslab.sernalign.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * this class is used to align two structural sequences
 */
public class StructuralSequenceAligner2 {

    private List<List<EditOperation>> editOperations;
    private List<Integer> distances;
    private StructuralSequence s1;
    private StructuralSequence s2;


    public StructuralSequenceAligner2(StructuralSequence s1, StructuralSequence s2) {
        this.editOperations = new ArrayList<>();
        this.distances = new ArrayList<>();
        //sia s1 la più corta tra le sequenze strutturali
        this.s1 = s1.getStructuralSequence().length < s2.getStructuralSequence().length ? s1 : s2;
        this.s2 = s1.getStructuralSequence().length < s2.getStructuralSequence().length ? s2 : s1;
        align(s1,s2);
    }

    private void align(StructuralSequence s1,StructuralSequence s2){
        //risolvo la prima volta la matrice con prendendo le sottosequenze di s1 ed s2 di lunghezza s1
        StructuralSequence subSequence = getSubSequence(s2,s1.getStructuralSequence().length);
        StructuralSequenceAligner aligner = new StructuralSequenceAligner(s1,subSequence);
        editOperations.add(aligner.getOptimalAlignment());
        distances.add(aligner.getDistance());
        StructuralSequenceBuilder builder = new StructuralSequenceBuilder();
        StructuralSequence newS1 = builder.build(editOperations.get(0));
        do {
            //prendo la sottosequenza di s2 di lunghezza s1
            subSequence = getSubSequence(s2,newS1.getStructuralSequence().length+1);
            aligner = new StructuralSequenceAligner(newS1,subSequence);
            editOperations.add(aligner.getOptimalAlignment());
            distances.add(aligner.getDistance());
            newS1 = builder.build(editOperations.get(editOperations.size()-1));

        }while(newS1.getStructuralSequence().length < s2.getStructuralSequence().length);

    }

    private StructuralSequence getSubSequence(StructuralSequence s ,int end){
        int[] subSequence = new int[end];
        for(int i = 0; i < end; i++){
            subSequence[i] = s.getStructuralSequence()[i];
        }
        return new StructuralSequence(subSequence);
    }

}