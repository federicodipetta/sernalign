package it.unicam.cs.bdslab.sernalign.tests;

import it.unicam.cs.bdslab.sernalign.models.EditOperation;
import it.unicam.cs.bdslab.sernalign.models.StructuralSequence;
import it.unicam.cs.bdslab.sernalign.models.StructuralSequenceBuilder;
import org.junit.jupiter.api.Test;

import java.util.List;

public class StructuralSequenceBuilderTest {


    @Test
    void testBuild() {
        EditOperation[] editOperations = {
                new EditOperation(1,1),
                new EditOperation(1,1),
                new EditOperation(null,3),
                new EditOperation(null,5),
        };
        List<EditOperation> editOperationList = List.of(editOperations);
        StructuralSequenceBuilder structuralSequenceBuilder = new StructuralSequenceBuilder();
        StructuralSequence sq = structuralSequenceBuilder.build(editOperationList);
        System.out.println(sq.printStructuralSequence());
    }
}
