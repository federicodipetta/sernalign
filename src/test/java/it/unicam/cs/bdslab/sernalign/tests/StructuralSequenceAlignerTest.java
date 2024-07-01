/**
 * SERNAlign - Structural sEquence RNA secondary structure Alignment
 * 
 * Copyright (C) 2023 Luca Tesei, Francesca Levi, Michela Quadrini, 
 * Emanuela Merelli - BioShape and Data Science Lab at the University of 
 * Camerino, Italy - http://www.emanuelamerelli.eu/bigdata/
 *  
 * This file is part of SERNAlign.
 * 
 * SERNAlign is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * any later version.
 * 
 * SERNAlign is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with SERNAlign. If not, see <http://www.gnu.org/licenses/>.
 */
package it.unicam.cs.bdslab.sernalign.tests;

import it.unicam.cs.bdslab.sernalign.antlr.RNASecondaryStructure;
import it.unicam.cs.bdslab.sernalign.antlr.RNASecondaryStructureFileReader;
import it.unicam.cs.bdslab.sernalign.models.StructuralSequence;
import it.unicam.cs.bdslab.sernalign.models.StructuralSequenceAligner;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StructuralSequenceAlignerTest {
    
    
    @Test
    void testSRNSsequenceAligner1() throws IOException {
	RNASecondaryStructure s1 = RNASecondaryStructureFileReader
		.readStructure("test/struct11.aas", false);
	StructuralSequence seq1 = new StructuralSequence(s1);
	System.out.println("Seq 1 = " + seq1.printStructuralSequence());
	// { 1, 1}
	RNASecondaryStructure s2 = RNASecondaryStructureFileReader
		.readStructure("test/struct1357.aas", false);
	StructuralSequence seq2 = new StructuralSequence(s2);
	System.out.println("Seq 2 =" + seq2.printStructuralSequence());
	// { 1, 3, 5, 7 }
	StructuralSequenceAligner a1 = new StructuralSequenceAligner(seq1, seq2);
	System.out.println("Alignment Matrix Seq 1 - Seq 2:");
	printMatrix(a1.getMatrix());
	System.out.println("Optimal Alignment Seq 1 - Seq 2: ");
	System.out.println(a1.printOptimalAlignment());
	System.out.println("Constraints of Optimal Alignment Seq 1 - Seq 2:");
	System.out.println(a1.printOptimalAlignmentConstraints());
	System.out.println("");
//assertTrue(a1.checkOptimalAlignment());
	System.out.println("");
	StructuralSequenceAligner a2 = new StructuralSequenceAligner(seq2, seq1);
	System.out.println("Alignment Matrix Seq 2 - Seq 1:");
	printMatrix(a2.getMatrix());
	System.out.println("Optimal Alignment Seq 2 - Seq 1: ");
	System.out.println(a2.printOptimalAlignment());
	System.out.println("Constraints of Optimal Alignment Seq 2 - Seq 1:");
	System.out.println(a2.printOptimalAlignmentConstraints());
	System.out.println("");
//	assertTrue(a2.checkOptimalAlignment());
    }

    @Test
    void testSRNSequenceAligner2() throws IOException {
	RNASecondaryStructure s1 = RNASecondaryStructureFileReader
		.readStructure("test/struct1153.aas", false);
	StructuralSequence seq1 = new StructuralSequence(s1);
	System.out.println("Seq 1 = " + seq1.printStructuralSequence());
	// { 1, 1, 5, 3}
	RNASecondaryStructure s2 = RNASecondaryStructureFileReader
		.readStructure("test/struct11552.aas", false);
	StructuralSequence seq2 = new StructuralSequence(s2);
	System.out.println("Seq 2 =" + seq2.printStructuralSequence());
	// { 1, 1, 5, 5, 2}
	StructuralSequenceAligner a1 = new StructuralSequenceAligner(seq1, seq2);
	System.out.println("Alignment Matrix Seq 1 - Seq 2:");
	printMatrix(a1.getMatrix());
	System.out.println("Optimal Alignment Seq 1 - Seq 2: ");
	System.out.println(a1.printOptimalAlignment());
	System.out.println("Constraints of Optimal Alignment Seq 1 - Seq 2:");
	System.out.println(a1.printOptimalAlignmentConstraints());
	System.out.println("");
	assertTrue(a1.checkOptimalAlignment());
	StructuralSequenceAligner a2 = new StructuralSequenceAligner(seq2, seq1);
	System.out.println("Alignment Matrix Seq 2 - Seq 1:");
	printMatrix(a2.getMatrix());
	System.out.println("Optimal Alignment Seq 2 - Seq 1: ");
	System.out.println(a2.printOptimalAlignment());
	System.out.println("Constraints of Optimal Alignment Seq 2 - Seq 1:");
	System.out.println(a2.printOptimalAlignmentConstraints());
	System.out.println("");
	assertTrue(a2.checkOptimalAlignment());
    }
    
    @Test
    void testSRNSequenceAligner2bisreverse() throws IOException {
	RNASecondaryStructure s1 = RNASecondaryStructureFileReader
		.readStructure("test/struct11552.aas", false);
	StructuralSequence seq1 = new StructuralSequence(s1);
	System.out.println("Seq 1 = " + seq1.printStructuralSequence());
	// { 1, 1, 5, 5, 2}
	RNASecondaryStructure s2 = RNASecondaryStructureFileReader
		.readStructure("test/struct1153.aas", false);
	StructuralSequence seq2 = new StructuralSequence(s2);
	System.out.println("Seq 2 =" + seq2.printStructuralSequence());
	// { 1, 1, 5, 3}
	StructuralSequenceAligner a1 = new StructuralSequenceAligner(seq1, seq2);
	System.out.println("Alignment Matrix Seq 1 - Seq 2:");
	printMatrix(a1.getMatrix());
	System.out.println("Optimal Alignment Seq 1 - Seq 2: ");
	System.out.println(a1.printOptimalAlignment());
	System.out.println("Constraints of Optimal Alignment Seq 1 - Seq 2:");
	System.out.println(a1.printOptimalAlignmentConstraints());
	System.out.println("");
	assertTrue(a1.checkOptimalAlignment());
	StructuralSequenceAligner a2 = new StructuralSequenceAligner(seq2, seq1);
	System.out.println("Alignment Matrix Seq 2 - Seq 1:");
	printMatrix(a2.getMatrix());
	System.out.println("Optimal Alignment Seq 2 - Seq 1: ");
	System.out.println(a2.printOptimalAlignment());
	System.out.println("Constraints of Optimal Alignment Seq 2 - Seq 1:");
	System.out.println(a2.printOptimalAlignmentConstraints());
	System.out.println("");
	assertTrue(a2.checkOptimalAlignment());
    }
    
    @Test
    void testSRNSequenceAligner2tris() throws IOException {
	RNASecondaryStructure s1 = RNASecondaryStructureFileReader
		.readStructure("test/struct1335.aas", false);
	StructuralSequence seq1 = new StructuralSequence(s1);
	System.out.println("Seq 1 = " + seq1.printStructuralSequence());
	// { 1, 1, 5, 5, 2}
	RNASecondaryStructure s2 = RNASecondaryStructureFileReader
		.readStructure("test/struct1153.aas", false);
	StructuralSequence seq2 = new StructuralSequence(s2);
	System.out.println("Seq 2 =" + seq2.printStructuralSequence());
	// { 1, 1, 5, 3}
	StructuralSequenceAligner a1 = new StructuralSequenceAligner(seq1, seq2);
	System.out.println("Alignment Matrix Seq 1 - Seq 2:");
	printMatrix(a1.getMatrix());
	System.out.println("Optimal Alignment Seq 1 - Seq 2: ");
	System.out.println(a1.printOptimalAlignment());
	System.out.println("Constraints of Optimal Alignment Seq 1 - Seq 2:");
	System.out.println(a1.printOptimalAlignmentConstraints());
	System.out.println("");
	assertTrue(a1.checkOptimalAlignment());
	StructuralSequenceAligner a2 = new StructuralSequenceAligner(seq2, seq1);
	System.out.println("Alignment Matrix Seq 2 - Seq 1:");
	printMatrix(a2.getMatrix());
	System.out.println("Optimal Alignment Seq 2 - Seq 1: ");
	System.out.println(a2.printOptimalAlignment());
	System.out.println("Constraints of Optimal Alignment Seq 2 - Seq 1:");
	System.out.println(a2.printOptimalAlignmentConstraints());
	System.out.println("");
	assertTrue(a2.checkOptimalAlignment());
    }
    
    @Test
    void testSRNSequenceAligner2bis() throws IOException {
	RNASecondaryStructure s1 = RNASecondaryStructureFileReader
		.readStructure("test/struct1153.aas", false);
	StructuralSequence seq1 = new StructuralSequence(s1);
	System.out.println("Seq 1 = " + seq1.printStructuralSequence());
	// { 1, 1, 5, 3}
	RNASecondaryStructure s2 = RNASecondaryStructureFileReader
		.readStructure("test/struct11553.aas", false);
	StructuralSequence seq2 = new StructuralSequence(s2);
	System.out.println("Seq 2 =" + seq2.printStructuralSequence());
	// { 1, 1, 5, 5, 3}
	StructuralSequenceAligner a1 = new StructuralSequenceAligner(seq1, seq2);
	System.out.println("Alignment Matrix Seq 1 - Seq 2:");
	printMatrix(a1.getMatrix());
	System.out.println("Optimal Alignment Seq 1 - Seq 2: ");
	System.out.println(a1.printOptimalAlignment());
	System.out.println("Constraints of Optimal Alignment Seq 1 - Seq 2:");
	System.out.println(a1.printOptimalAlignmentConstraints());
	System.out.println("");
	assertTrue(a1.checkOptimalAlignment());
	StructuralSequenceAligner a2 = new StructuralSequenceAligner(seq2, seq1);
	System.out.println("Alignment Matrix Seq 2 - Seq 1:");
	printMatrix(a2.getMatrix());
	System.out.println("Optimal Alignment Seq 2 - Seq 1: ");
	System.out.println(a2.printOptimalAlignment());
	System.out.println("Constraints of Optimal Alignment Seq 2 - Seq 1:");
	System.out.println(a2.printOptimalAlignmentConstraints());
	System.out.println("");
	assertTrue(a2.checkOptimalAlignment());
    }


    
    @Test
    void testSRNSequenceAligner3() throws IOException {
	RNASecondaryStructure s1 = RNASecondaryStructureFileReader
		.readStructure("test/struct122.aas", false);
	StructuralSequence seq1 = new StructuralSequence(s1);
	System.out.println("Seq 1 = " + seq1.printStructuralSequence());
	// { 1, 2, 2}
	RNASecondaryStructure s2 = RNASecondaryStructureFileReader
		.readStructure("test/struct124.aas", false);
	StructuralSequence seq2 = new StructuralSequence(s2);
	System.out.println("Seq 2 =" + seq2.printStructuralSequence());
	// { 1, 2, 4}
	StructuralSequenceAligner a1 = new StructuralSequenceAligner(seq1, seq2);
	System.out.println("Alignment Matrix Seq 1 - Seq 2:");
	printMatrix(a1.getMatrix());
	System.out.println("Optimal Alignment Seq 1 - Seq 2: ");
	System.out.println(a1.printOptimalAlignment());
	System.out.println("Constraints of Optimal Alignment Seq 1 - Seq 2:");
	System.out.println(a1.printOptimalAlignmentConstraints());
	System.out.println("");
	assertTrue(a1.checkOptimalAlignment());
	StructuralSequenceAligner a2 = new StructuralSequenceAligner(seq2, seq1);
	System.out.println("Alignment Matrix Seq 2 - Seq 1:");
	printMatrix(a2.getMatrix());
	System.out.println("Optimal Alignment Seq 2 - Seq 1: ");
	System.out.println(a2.printOptimalAlignment());
	System.out.println("Constraints of Optimal Alignment Seq 2 - Seq 1:");
	System.out.println(a2.printOptimalAlignmentConstraints());
	System.out.println("");
	assertTrue(a2.checkOptimalAlignment());
    }

	//simple case
	@Test
	void testSRBSequenceAligner4() throws IOException {
		RNASecondaryStructure s1 = RNASecondaryStructureFileReader
				.readStructure("test/struct11.aas", false);
		StructuralSequence seq1 = new StructuralSequence(s1);
		System.out.println("Seq 1 = " + seq1.printStructuralSequence());
		// { 1, 2, 2}
		RNASecondaryStructure s2 = RNASecondaryStructureFileReader
				.readStructure("test/struct13.aas", false);
		StructuralSequence seq2 = new StructuralSequence(s2);
		System.out.println("Seq 2 =" + seq2.printStructuralSequence());
		// { 1, 2, 4}
		StructuralSequenceAligner a1 = new StructuralSequenceAligner(seq1, seq2);
		System.out.println("Alignment Matrix Seq 1 - Seq 2:");
		printMatrix(a1.getMatrix());
		System.out.println("Optimal Alignment Seq 1 - Seq 2: ");
		System.out.println(a1.printOptimalAlignment());
		System.out.println("Constraints of Optimal Alignment Seq 1 - Seq 2:");
		System.out.println(a1.printOptimalAlignmentConstraints());
		System.out.println("");
		assertTrue(a1.checkOptimalAlignment());
		StructuralSequenceAligner a2 = new StructuralSequenceAligner(seq2, seq1);
		System.out.println("Alignment Matrix Seq 2 - Seq 1:");
		printMatrix(a2.getMatrix());
		System.out.println("Optimal Alignment Seq 2 - Seq 1: ");
		System.out.println(a2.printOptimalAlignment());
		System.out.println("Constraints of Optimal Alignment Seq 2 - Seq 1:");
		System.out.println(a2.printOptimalAlignmentConstraints());
		System.out.println("");
		assertTrue(a2.checkOptimalAlignment());
	}



		private void printMatrix(int[][] matrix) {
			for (int i = 0; i < matrix.length; i++) {
				for (int j = 0; j < matrix[0].length; j++)
					System.out.print(matrix[i][j] + " ");
					System.out.print("\n");
			}
    }

    private void checkEqualMatrices(int[][] matrix1, int[][] matrix2) {
	assertTrue(matrix1.length == matrix2.length);
	assertTrue(matrix1[0].length == matrix2[0].length);
	for (int i = 0; i < matrix1.length; i++) {
	    for (int j = 0; j < matrix1[0].length; j++)
		assertTrue(matrix1[i][j] == matrix2[i][j]);
	}
    }

//    @Test
//    void testGetMatrix() {
//	fail("Not yet implemented");
//    }
//
//    @Test
//    void testGetDistance() {
//	fail("Not yet implemented");
//    }
//
//    @Test
//    void testGetOptimalAlignment() {
//	fail("Not yet implemented");
//    }

}
