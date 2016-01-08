package given;

public class DNAMolecule {

	// These are the ONLY fields you are allowed in this class.
	public DNAStrand strandA;
	public DNAStrand strandB;
	
	/*
	 * Constructs a new DNA molecule which has the given sequence as one of its strands.
	 * The other strand will be the complement of the given strand (which will lead to a
	 * non-ragged, perfectly connected DNA molecule).  Note that this method should call ZipUp,
	 * to connect the two strands created in the image and complement of the given sequence.ß
	 */
	public DNAMolecule(String sequence){
		strandA = new DNAStrand(sequence);
		strandB = new DNAStrand(strandA.fiveEnd.constructFullComplement());
		zipUp();
	}
	
	
	/*
	 * Constructs a new Molecule of DNA from two strands of DNA (should call zipUp)
	 */
	public DNAMolecule(DNAStrand strandA, DNAStrand strandB){
		this.strandA = strandA;
		this.strandB = strandB;
		if(!zipUp()) {
			throw new IllegalArgumentException("unable to zipUp");
		}
	}
	
	private DNAMolecule() {}
	
	
	/*
	 * Constructs a new molecule of DNA from an existing one.  The process you use
	 * for this method must mirror the biologic one, which is simple enough to explain.
	 * 
	 * In DNA replication, there is no "copier", which takes in a parent piece of DNA,
	 * and constructs a child molecule.
	 * 
	 * Rather, the way that it works is that it seperates the molecule into its two strands
	 * (breaking the bonds between the nucleotides across the strands), and then each one
	 * of the original strands serves as a tempate for constructing a new half.
	 * 
	 * Therefore, if we start with an ORIGINAL molecule, it is composed of two ORIGINAL
	 * strands.
	 * 
	 * Each resulting molecule of DNA contains one of the ORIGINAL strands, but is now
	 * paired with a NEW strand, which has been constructed to complement the ORIGINAL strand.
	 * 
	 * Therefore, this method should do two things:
	 *  - Remove one of the strands from the current molecule, and construct a new strand in its place
	 *  - Use the removed strand to construct a NEW molecule of DNA, and return that molecule as the result of the method.
	 */
	public DNAMolecule duplicate(){
		DNAStrand newStrandB = new DNAStrand(strandA.fiveEnd.constructFullComplementWithPeer());
		DNAStrand newStrandA = new DNAStrand(strandB.fiveEnd.constructFullComplementWithPeer());
		DNAMolecule newMolecule = new DNAMolecule();
		newMolecule.strandA = newStrandA;
		newMolecule.strandB = strandB;
		strandB = newStrandB;
		return newMolecule;
	}
	
	/*
	 * Attaches corresponding nucleotides to their partners on the opposite strand.
	 * This method should attempt to produce the LEAST RAGGED MOLECULE POSSIBLE.
	 * 
	 * So, if you have the very simple case:
	 * 
	 * Strand 1: (3) A=A=A=A=A (5)
	 * and
	 * Strand 2: (5) T=T=T=T (3)
	 * 
	 * There are many ways of combining the two into a valid molecule, but there are only
	 * two ways of combining it int one where you maximize the number of nucleotides
	 * which have base pairs.
	 * 
	 * If any zipping up is possible, this method returns true.  It is very much possible
	 * that no zipping up is possible (in which case, the method should return false).
	 * 
	 * Here is an example where zipUp should return false:
	 * 
	 * Strand 1:  (3) A=G=C=T (5)
	 * Strand 2:  (5) C=G=T=A (3)
	 * 
	 * You can quickly verify that any possible overlap of these two strands will lead to some
	 * base pair misalignment (remember C only pairs with G, and A only pairs with T, and visa vera)
	 * 
	 * This is a non-trivial part of this assignment.
	 */
	public boolean zipUp() {
		String strA = strandA.toStringPlain();
		String strB = strandB.toStringPlainReversed();
		int offsetMin = -(strB.length() - 1);
		int offsetMax = strA.length() - 1;
		int bestOffset = offsetMin;
		int bestOffsetMatched = 0;
		for(int i = offsetMin; i <= offsetMax; i++) {
			int ABegin, AEnd, BBegin;
			ABegin = i;
			if(ABegin < 0) ABegin = 0;
			AEnd = strB.length() + i - 1;
			if(AEnd >= strA.length()) AEnd = strA.length() - 1;
			if(AEnd - ABegin + 1 <= bestOffsetMatched) continue;
			BBegin = -i;
			if(BBegin < 0) BBegin = 0;
			boolean succ = true;
			for(int ANow = ABegin, BNow = BBegin; ANow <= AEnd; ANow++, BNow++) {
				char nowA = strA.charAt(ANow);
				char nowB = strB.charAt(BNow);
				if(!Nucleotide.isComplement(nowA, nowB)) {
					succ = false;
					break;
				}
			}
			if(succ) {
				bestOffset = i;
				bestOffsetMatched = AEnd - ABegin + 1;
			}
		}
		if(bestOffsetMatched == 0) return false;
		int ABegin = bestOffset; if(ABegin < 0) ABegin = 0;
		int AEnd = strB.length() + bestOffset - 1; if(AEnd >= strA.length()) AEnd = strA.length() - 1;
		int BBegin = -bestOffset; if(BBegin < 0) BBegin = 0;
		Nucleotide nowA = strandA.threeEnd;
		Nucleotide nowB = strandB.fiveEnd;
		for(int i = 0; i < ABegin; i++) nowA = nowA.getNext();
		for(int i = 0; i < BBegin; i++) nowB = nowB.getPrev();
		for(int i = 0; i <= AEnd - ABegin; i++) {
			nowA.peer = nowB;
			nowB.peer = nowA;
			nowA = nowA.getNext();
			nowB = nowB.getPrev();
		}
		return true;
	}
	
	/*
	 * Trys to combine this DNA molecule with another one. Returns the combined
	 * one as a result, if it is possible. Returns null otherwise.
	 * 
	 * Remember that a 3 end can only bond with a 5 end,
	 * and that any "ragged ends" of the DNA need to be compatible if they
	 * are going to be joined on that end.
	 * 
	 * There are up to four different possible ways to combine a DNA molecule
	 * if multiple are possible, any choice is acceptable. 
	 * 
	 */
	public DNAMolecule tryToCombineWith(DNAMolecule other) {
		DNAMolecule result;
		result = tryCombineImpl(this.duplicate(), other.duplicate());
		if(result != null) return result;
		result = tryCombineImpl(other.duplicate(), this.duplicate());
		if(result != null) return result;
		result = tryCombineImpl(this.duplicate().flip(), other.duplicate());
		if(result != null) return result;
		return tryCombineImpl(this.duplicate(), other.duplicate().flip());
	}
	
	public DNAMolecule flip() {
		DNAStrand tmp = this.strandA;
		this.strandA = this.strandB;
		this.strandB = tmp;
		return this;
	}
	
	static private DNAMolecule doCombine(DNAMolecule left, DNAMolecule right) {
		left.strandA.fiveEnd.append(right.strandA.threeEnd);
		left.strandA.fiveEnd = right.strandA.fiveEnd;
		
		right.strandB.fiveEnd.append(left.strandB.threeEnd);
		left.strandB.threeEnd = right.strandB.threeEnd;
		
		return left;
	}
	
	static private DNAMolecule tryCombineImpl(DNAMolecule left, DNAMolecule right) {
		int offsetLeft, offsetRight;
		if(left.strandA.fiveEnd.peer != null) {
			if(left.strandB.threeEnd.peer != null) {
				offsetLeft = 0;
			} else {
				offsetLeft = left.strandA.fiveEnd.peer.getSizeLeft();
			}
		} else {
			if(left.strandB.threeEnd.peer != null) {
				offsetLeft = -left.strandB.threeEnd.peer.getSizeRight();
			} else {
				throw new IllegalStateException("unzipped DNAMolecule(left)");
			}
		}
		if(right.strandB.fiveEnd.peer != null) {
			if(right.strandA.threeEnd.peer != null) {
				offsetRight = 0;
			} else {
				offsetRight = right.strandB.fiveEnd.peer.getSizeLeft();
			}
		} else {
			if(right.strandA.threeEnd.peer != null) {
				offsetRight = right.strandA.threeEnd.peer.getSizeRight();
			} else {
				throw new IllegalStateException("unzipped DNAMolecule(right)");
			}
		}
		if(offsetLeft != offsetRight) return null;
		if(offsetLeft == 0) return doCombine(left, right);
		if(offsetLeft > 0) {
			Nucleotide nowA = right.strandA.threeEnd;
			Nucleotide nowB = left.strandB.threeEnd;
			for(int i = 0; i < offsetLeft - 1; i++) nowB = nowB.getNext();
			for(int i = 0; i < offsetLeft; i++) {
				if(!nowA.isComplement(nowB)) return null;
				nowA.peer = nowB;
				nowB.peer = nowA;
				nowA = nowA.getNext();
				nowB = nowB.getPrev();
			}
			return doCombine(left, right);
		}
		/* offsetLeft < 0 */ {
			offsetLeft = -offsetLeft;
			Nucleotide nowA = left.strandA.fiveEnd;
			for(int i = 0; i < offsetLeft - 1; i++) nowA = nowA.getPrev();
			Nucleotide nowB = right.strandB.fiveEnd;
			for(int i = 0; i < offsetLeft; i++) {
				if(!nowA.isComplement(nowB)) return null;
				nowA.peer = nowB;
				nowB.peer = nowA;
				nowA = nowA.getNext();
				nowB = nowB.getPrev();
			}
			return doCombine(left, right);
		}
	}
	
	/*
	 * Should display the expected display of a DNA strand.  Note that the end notes
	 * should be at the far end (even if the strand isn't complete).  Also note that this
	 * is how a lot of tests will judge the validity of your code, so make sure that it is 
	 * correct:
	 * 
	 * Example output:
	 * 
	  (3) A=C=T=G=C=C=G=T=C=T=C=A=A=T=A=G (5)    
                | | | | | | | | | | | | |            
            (5) C=G=G=C=A=G=A=G=T=T=A=T=C=T=G (3)

	 * (Equally valid is the flipped version)

	  (3) G=T=C=T=A=T=T=G=A=G=A=C=G=G=C (5)
	          | | | | | | | | | | | | |
	      (5) G=A=T=A=A=C=T=C=T=G=C=C=G=T=A=C (3)

	 * 
	 * Use the \n character to indicate a new line after the first two lines, but do not
	 * inclued it after the third line.
	 */
	
	public String toString() {
		String strA = strandA.toString();
		String strB = strandB.toStringReversed();
		int offset;
		//offset > 0: XXXX
		//             XXXX
		//
		//offset < 0:  XXXX
		//            XXXX
		//
		//offset = 0: XXXX
		//            XXXX
		if(strandA.threeEnd.peer != null) {
			if(strandB.fiveEnd.peer != null) {
				offset = 0;
			} else {
				offset = -strandA.threeEnd.peer.getSizeRight();
			}
		} else {
			if(strandB.fiveEnd.peer != null) {
				offset = strandB.fiveEnd.peer.getSizeLeft();
			} else {
				throw new IllegalStateException("unzipped DNAMolecule");
			}
		}
		String strMiddle = "    ";
		for(int i = 0; i < Math.abs(offset); i++) {
			if(offset > 0) strB = "  " + strB;
			else strA = "  " + strA;
			strMiddle += "  ";
		}
		int nPeers = 0;
		Nucleotide nowPeer;
		if(offset <= 0) {
			nowPeer = strandA.threeEnd;
			while(nowPeer != null && nowPeer.peer != null) {
				nPeers++;
				nowPeer = nowPeer.getNext();
			}
		} else {
			nowPeer = strandB.fiveEnd;
			while(nowPeer != null && nowPeer.peer != null) {
				nPeers++;
				nowPeer = nowPeer.getPrev();
			}
		}
		for(int i = 0; i < nPeers; i++) {
			if(i < nPeers - 1) strMiddle += "| ";
			else strMiddle += "|";
		}
		return strA + "\n" + strMiddle + "\n" + strB;
	}
	
	private DNAMolecule tryCutAt(Nucleotide pos, int len) {
		if(pos.getPrev() == null) return null;
		if(pos.peer.getNext() == null) return null;
		Nucleotide posEnd = pos; for(int i = 1; i < len; i++) posEnd = posEnd.getNext();
		if(posEnd.getNext() == null) return null;
		if(posEnd.peer.getPrev() == null) return null;
		Nucleotide posPeer = pos.peer;
		//remove peer
		Nucleotide now = pos;
		for(int i = 0; i < len; i++) {
			now.peer.peer = null;
			now.peer = null;
			now = now.getNext();
		}
		//cut
		posEnd.append(null);
		posPeer.append(null);
		DNAMolecule newMolecule = new DNAMolecule();
		newMolecule.strandA = new DNAStrand(this.strandA.fiveEnd);
		newMolecule.strandB = new DNAStrand(this.strandB.threeEnd);
		this.strandA = new DNAStrand(this.strandA.threeEnd);
		this.strandB = new DNAStrand(this.strandB.fiveEnd);
		return newMolecule;
	}
	
	public DNAMolecule restrictionCut(String sequence) {
		Nucleotide ABegin, AEnd, BBegin/*, BEnd*/;
		if(strandA.threeEnd.peer != null) {
			ABegin = strandA.threeEnd;
			//BEnd = strandA.threeEnd.peer;
		} else if(strandB.fiveEnd.peer != null) {
			//BEnd = strandB.fiveEnd;
			ABegin = strandB.fiveEnd.peer;
		} else {
			throw new IllegalStateException("unzipped DNAMolecule");
		}
		if(strandA.fiveEnd.peer != null) {
			AEnd = strandA.fiveEnd;
			BBegin = strandA.fiveEnd.peer;
		} else if(strandB.threeEnd.peer != null) {
			BBegin = strandB.threeEnd;
			AEnd = strandB.threeEnd.peer;
		} else {
			throw new IllegalStateException("unzipped DNAMolecule");
		}
		int interval = Nucleotide.countInterval(ABegin, AEnd);
		if(sequence.length() > interval) return null;
		Nucleotide ANow = ABegin, BNow = BBegin;
		for(int i = 0; i <= interval - sequence.length(); i++) {
			Nucleotide ANow2 = ANow, BNow2 = BNow;
			boolean AFailed = false, BFailed = false;
			for(int j = 0; j < sequence.length(); j++) {
				if(ANow2.toString().charAt(0) != sequence.charAt(j)) AFailed = true;
				if(BNow2.toString().charAt(0) != sequence.charAt(j)) BFailed = true;
				ANow2 = ANow2.getNext();
				BNow2 = BNow2.getNext();
			}
			if(!AFailed) {
				DNAMolecule result = tryCutAt(ANow, sequence.length());
				if(result != null) return result;
			}
			if(!BFailed) {
				DNAMolecule result = tryCutAt(BNow, sequence.length());
				if(result != null) return result;
			}
			ANow = ANow.getNext();
			BNow = BNow.getNext();
		}
		return null;
	}
}
