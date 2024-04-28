package it.unicam.cs.bdslab.sernalign.models;

import java.util.List;

public interface IStructuralSequenceAligner {
    int[][] getMatrix();
    int getDistance();
    List<EditOperation> getOptimalAlignment();
}
