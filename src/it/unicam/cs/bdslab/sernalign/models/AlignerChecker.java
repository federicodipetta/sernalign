package it.unicam.cs.bdslab.sernalign.models;

import it.unicam.cs.bdslab.sernalign.models.utils.ContextSet;

import java.util.List;

public class AlignerChecker {
    private StructuralSequence x;
    private StructuralSequence y;
    private List<EditOperation> editOperations;

    public AlignerChecker(StructuralSequence x, StructuralSequence y, List<EditOperation> editOperations) {
        this.x = x;
        this.y = y;
        this.editOperations = editOperations;
    }

    public boolean check() {
       return checkAlignment(1,1,0);
    }
//    public boolean check() {
//        return checkAlignment(x.size(), y.size(), editOperations.size() - 1);
//    }
//    private boolean checkAlignment(int i, int j, int ell) {
//        if (ell == -1 || i == 0 || j == 0)
//            return true;
//        if (this.editOperations.get(ell).getJ() == null) {
//            // deletion operation
//            return ContextSet.isInSet(this.x.getStructuralSequence()[i - 1],
//                    j) && checkAlignment(i - 1, j, ell - 1);
//        }
//
//        if (this.editOperations.get(ell).getI() == null) {
//            // insertion operation
//            return ContextSet.isInSet(this.y.getStructuralSequence()[j - 1],
//                    i) && checkAlignment(i, j - 1, ell - 1);
//        }
//
//        // match/mismatch operation
//        return ContextSet.isInSet(this.x.getStructuralSequence()[i - 1], j)
//                && ContextSet.isInSet(this.y.getStructuralSequence()[j - 1],
//                i)
//                && checkAlignment(i - 1, j - 1, ell - 1);
//    }
private boolean checkAlignment(int i, int j, int ell) {
    if (ell == this.editOperations.size() || i >= x.size() || j >= y.size())
        return true;
    if (this.editOperations.get(ell).getJ() == null) {
        // deletion operation
        return ContextSet.isInSet(this.editOperations.get(ell).getI(),j) && checkAlignment(i + 1, j, ell + 1);
    }

    if (this.editOperations.get(ell).getI() == null) {
        // insertion operation
        return ContextSet.isInSet(this.editOperations.get(ell).getJ(),i)
                && checkAlignment(i, j + 1, ell + 1);
    }

    // match/mismatch operation
    return ContextSet.isInSet(this.editOperations.get(ell).getI(),j)
            && ContextSet.isInSet(this.editOperations.get(ell).getJ(),i)
            && checkAlignment(i + 1, j + 1, ell + 1);
}
}
