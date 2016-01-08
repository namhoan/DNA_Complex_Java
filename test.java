package given;

public class test {
	public static void testCut(DNAMolecule molecule, String seq) {
		System.out.println(molecule.toString());
		DNAMolecule result = molecule.restrictionCut(seq);
		if(result == null) {
			System.out.println("failed to cut");
		} else {
			System.out.println("left: ");
			System.out.println(molecule.toString());
			System.out.println("right: ");
			System.out.println(result.toString());
		}
	}
	
	public static void main(String[] args) {
		//System.out.println(DNAStrand.getComplementStrand("ATCG"));
		//System.out.println(new DNAMolecule("ATCGTATATATACCGCG").toString());
		//System.out.println(new DNAMolecule(new DNAStrand("TTATATCG"), new DNAStrand("CTAGTTCG")).toString());
		//System.out.println(new DNAMolecule(new DNAStrand("TTATATCG"), new DNAStrand("TAGTTCG")).toString());
		//System.out.println(new DNAMolecule(new DNAStrand("TTCGTATGCGTA"), new DNAStrand("TACGAAGG")).toString());
		/* {
			DNAMolecule testMolecule = new DNAMolecule(new DNAStrand("TTCGTATGCGTA"), new DNAStrand("TACGAAGG"));
			System.out.println(testMolecule.toString());
			System.out.println(testMolecule.strandA.hashCode());
			System.out.println(testMolecule.strandB.hashCode());
			DNAMolecule testMolecule2 = testMolecule.duplicate();
			System.out.println(testMolecule.toString());
			System.out.println(testMolecule.strandA.hashCode());
			System.out.println(testMolecule.strandB.hashCode());
			System.out.println(testMolecule2.toString());
			System.out.println(testMolecule2.strandA.hashCode());
			System.out.println(testMolecule2.strandB.hashCode());
		} //*/
		//System.out.println(new DNAMolecule("ATATATAT").tryToCombineWith(new DNAMolecule("CGCGCGCG")).toString());
		//System.out.println(new DNAMolecule(new DNAStrand("TTAAAATTAT"), new DNAStrand("CCATAATT")).tryToCombineWith(
		//		new DNAMolecule(new DNAStrand("GGAATT"), new DNAStrand("GGAATTCC"))).toString());
		//testCut(new DNAMolecule("ATCGGGTCAATCC"), "GGT");
		//testCut(new DNAMolecule("GGTATCCACC"), "GGT");
		testCut(new DNAMolecule(new DNAStrand("GGTATCCACCATATATATATCCCT"), new DNAStrand("AAAAAAAAAAATAGGGATATATATATGGTGGA")), "GGT");
	}
}
