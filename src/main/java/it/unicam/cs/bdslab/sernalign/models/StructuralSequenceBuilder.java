package it.unicam.cs.bdslab.sernalign.models;

import it.unicam.cs.bdslab.sernalign.antlr.RNASecondaryStructure;

import java.util.List;
import java.util.Objects;

public class StructuralSequenceBuilder {
    


    public StructuralSequence build(RNASecondaryStructure secondaryStructure) {
        return new StructuralSequence(secondaryStructure);
    }

    public StructuralSequence build(List<EditOperation> editOperations) {

        return new StructuralSequence(
                editOperations.stream()
                        .map(EditOperation::getJ)
                        .filter(Objects::nonNull)
                        .mapToInt(Integer::intValue)
                        .toArray()
        );
    }




}
