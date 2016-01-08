package test;

import static org.junit.Assert.*;

import org.junit.Test;

import mycode.*;

public class BasicTests {

	@Test
	public void testComplementStringGenerator(){
		assertEquals("A", DNAStrand.getComplementStrand("T"));
		assertEquals("C", DNAStrand.getComplementStrand("G"));
		assertEquals("G", DNAStrand.getComplementStrand("C"));
		assertEquals("T", DNAStrand.getComplementStrand("A"));
		assertEquals("ACG", DNAStrand.getComplementStrand("CGT"));
		assertEquals("ACACACAC", DNAStrand.getComplementStrand("GTGTGTGT"));
		assertEquals("", DNAStrand.getComplementStrand(""));
		assertEquals("ACGACGATAC", DNAStrand.getComplementStrand("GTATCGTCGT"));	
	}
	
	@Test
	public void testBasicConstruction(){
		DNAMolecule d = new DNAMolecule("ACGTACGT");
		String expected = "(3) A=C=G=T=A=C=G=T (5)\n"+
		                  "    | | | | | | | |\n"+
	                      "(5) T=G=C=A=T=G=C=A (3)";
		assertEquals(expected, d.toString());
	}
	
	@Test
	public void testStrandConstruction(){
		DNAStrand strandA = new DNAStrand("ACGTACGT");
		DNAStrand strandB = new DNAStrand("ACGTACGTTTTT");
		DNAMolecule d = new DNAMolecule(strandA, strandB);
		
		String expected1 = "(3) A=C=G=T=A=C=G=T=T=T=T=T (5)\n"+
		                   "    | | | | | | | |\n"+
	                       "(5) T=G=C=A=T=G=C=A (3)";
		
		String expected2 = "        (3) A=C=G=T=A=C=G=T (5)\n"+
				           "            | | | | | | | |\n"+
                           "(5) T=T=T=T=T=G=C=A=T=G=C=A (3)";
		
		boolean valid = d.toString().equals(expected1) || d.toString().equals(expected2);
		assertTrue(valid);
	}
	
}
