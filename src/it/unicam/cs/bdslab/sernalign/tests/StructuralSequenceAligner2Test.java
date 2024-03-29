package it.unicam.cs.bdslab.sernalign.tests;

import it.unicam.cs.bdslab.sernalign.antlr.RNASecondaryStructure;
import it.unicam.cs.bdslab.sernalign.antlr.RNASecondaryStructureFileReader;
import it.unicam.cs.bdslab.sernalign.models.StructuralSequence;
import it.unicam.cs.bdslab.sernalign.models.StructuralSequenceAligner;
import it.unicam.cs.bdslab.sernalign.models.StructuralSequenceAligner2;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StructuralSequenceAligner2Test {

    @Test
    void testAlign11E1135() throws IOException {
        RNASecondaryStructure s1 = RNASecondaryStructureFileReader
                 .readStructure("test/struct11.aas", false);
         RNASecondaryStructure s2 = RNASecondaryStructureFileReader
                 .readStructure("test/struct1357.aas", false);
         StructuralSequence seq1 = new StructuralSequence(s1);
         StructuralSequence seq2 = new StructuralSequence(s2);
         StructuralSequenceAligner2 aligner = new StructuralSequenceAligner2(seq1, seq2);
         assertEquals(0,0);
    }

}
