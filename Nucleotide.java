package given;

public class Nucleotide {

	private char base;
	
	// The rest of this class is up to you. Consider it your Node Class. Remember that none of your
	// classes can use Arrays, or any other form of collections (which leaves you a pretty
	// stark choice of how to represent your data. 
	private Nucleotide prev, next;
	public Nucleotide peer = null;
	
	public Nucleotide(char base) {
		switch(base) {
		case 'A': case 'T': case 'C': case 'G': break;
		default: throw new IllegalArgumentException();
		}
		this.base = base;
		this.prev = null;
		this.next = null;
	}
	
	public void append(Nucleotide other) {
		if(this.next != null)
			this.next.prev = null;
		if(other != null) {
			if(other.prev != null)
				other.prev.next = null;
			other.prev = this;
		}
		this.next = other;
	}
	
	public Nucleotide getThree() {
		if(prev == null) return this;
		return prev.getThree();
	}
	
	public Nucleotide getFive() {
		if(next == null) return this;
		return next.getFive();
	}
	
	public Nucleotide getNext() {
		return next;
	}
	
	public Nucleotide getPrev() {
		return prev;
	}
	
	public Nucleotide constructSingleComplement() {
		if(base == 'A') return new Nucleotide('T');
		if(base == 'T') return new Nucleotide('A');
		if(base == 'G') return new Nucleotide('C');
		return new Nucleotide('G');
	}
	
	public Nucleotide constructFullComplement() {
		Nucleotide now = getFive();
		Nucleotide complement = now.constructSingleComplement();
		now = now.prev;
		while(now != null) {
			Nucleotide newComplement = now.constructSingleComplement();
			complement.append(newComplement);
			complement = newComplement;
			now = now.prev;
		}
		return complement;
	}
	
	public Nucleotide constructFullComplementWithPeer() {
		Nucleotide now = getFive();
		Nucleotide nowComplement = null;
		Nucleotide lastPeer = null;
		while(now != null) {
			if(now.peer != null) {
				lastPeer = now.peer;
				if(nowComplement == null) {
					nowComplement = now.constructSingleComplement();
					Nucleotide nowComplementPrev = nowComplement;
					Nucleotide nowPeer = now.peer.prev;
					while(nowPeer != null) {
						Nucleotide newOne = new Nucleotide(nowPeer.base);
						newOne.append(nowComplementPrev);
						nowComplementPrev = newOne;
						nowPeer = nowPeer.prev;
					}
				} else {
					Nucleotide newOne = now.constructSingleComplement();
					nowComplement.append(newOne);
					nowComplement = newOne;
				}
				nowComplement.peer = now;
				now.peer = nowComplement;
			} else {
				lastPeer = null;
			}
			now = now.prev;
		}
		if(lastPeer != null) {
			lastPeer = lastPeer.next;
			while(lastPeer != null) {
				Nucleotide newOne = new Nucleotide(lastPeer.base);
				nowComplement.append(newOne);
				nowComplement = newOne;
				lastPeer = lastPeer.next;
			}
		}
		return nowComplement;
	}
	
	public int getSizeLeft() {
		if(prev == null) return 0;
		return 1 + prev.getSizeLeft();
	}
	
	public int getSizeRight() {
		if(next == null) return 0;
		return 1 + next.getSizeRight();
	}
	
	public int getSize() {
		return 1 + getSizeLeft() + getSizeRight();
	}
	
	public String toString(){
		return "" + base;
	}
	
	public boolean isComplement(Nucleotide other) {
		return isComplement(base, other.base);
	}
	
	public static boolean isComplement(char a, char b) {
		if(a == 'A') return b == 'T';
		if(a == 'T') return b == 'A';
		if(a == 'C') return b == 'G';
		if(a == 'G') return b == 'C';
		throw new IllegalArgumentException("unknown base");
	}
	
	public static int countInterval(Nucleotide begin, Nucleotide end) {
		if(begin == end) return 1;
		return countInterval(begin.getNext(), end) + 1;
	}
}
