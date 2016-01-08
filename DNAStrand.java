package given;

public class DNAStrand {

	// These are the ONLY fields you are allowed in this class
	Nucleotide threeEnd;
	Nucleotide fiveEnd;
	
	/*
	 * Constructs a new DNAStrand from a given sequence (in the three to five ordering)
	 */
	public DNAStrand(String sequence) {
		if(sequence.length() > 0) {
			fiveEnd = threeEnd = new Nucleotide(sequence.charAt(0));
			for(int i = 1; i < sequence.length(); i++) {
				Nucleotide now = new Nucleotide(sequence.charAt(i));
				fiveEnd.append(now);
				fiveEnd = now;
			}
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	/*
	 * Constructs a new DNAStrand which contains a given Nucleotide.
	 * This nucleotide should be able to be ANY of the many nucleotides in a strand
	 * (i.e. you will need to calculate the threeEnd and fiveEnd from any Nucleotide.)
	 */
	public DNAStrand(Nucleotide any) {
		if(any == null) {
			throw new IllegalArgumentException();
		} else {
			threeEnd = any.getThree();
			fiveEnd = any.getFive();
		}
	}
	
	/*
	 * Gives us a visual image of the Strand. Should be of the form:
	 * (3) A=G=C=T=T=T=A=C (5)
	 */
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(3) ");
		Nucleotide now = threeEnd;
		while(true) {
			sb.append(now.toString());
			now = now.getNext();
			if(now != null) {
				sb.append("=");
			} else {
				break;
			}
		}
		sb.append(" (5)");
		return sb.toString();
	}
	
	public String toStringReversed() {
		StringBuilder sb = new StringBuilder();
		sb.append("(5) ");
		Nucleotide now = fiveEnd;
		while(true) {
			sb.append(now.toString());
			now = now.getPrev();
			if(now != null) {
				sb.append("=");
			} else {
				break;
			}
		}
		sb.append(" (3)");
		return sb.toString();
	}
	
	public String toStringPlain() {
		StringBuilder sb = new StringBuilder();
		Nucleotide now = threeEnd;
		while(now != null) {
			sb.append(now.toString());
			now = now.getNext();
		}
		return sb.toString();
	}
	
	public String toStringPlainReversed() {
		StringBuilder sb = new StringBuilder();
		Nucleotide now = fiveEnd;
		while(now != null) {
			sb.append(now.toString());
			now = now.getPrev();
		}
		return sb.toString();
	}
	
	/*
	 * Constructs the valid complement string that could be fully zipped to a given one:
	 * (Both products and results are in 3 --> 5 order.
	 * I.E:
	 * AAAA --> TTTT
	 * ACG --> CGT
	 * CCAA --> TTGG
	 */
	public static String getComplementStrand(String s){
		return new DNAStrand(new DNAStrand(s).fiveEnd.constructFullComplement()).toStringPlain();
	}
}
