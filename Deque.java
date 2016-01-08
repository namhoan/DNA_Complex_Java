import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
	private int SIZE; // size of deque
	private Node firstNode; // first Node
	private Node lastNode; // last Node

	// constructor
	public Deque() {
		firstNode = null;
		lastNode = null;
		SIZE = 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Deque [N=" + SIZE + ", first=" + firstNode + ", last="
				+ lastNode + "]";
	}

	private class Node {
		private Item item;
		private Node nextNode;
		private Node prevNode;
	}

	// if deque empty
	public boolean isEmpty() {
		if (firstNode == null)
			return true;
		else
			return false;
	}

	// insert the item at the front
	public void addFront(Item itemNew) {
		if (itemNew == null)
			throw new java.lang.NullPointerException();

		if (firstNode == null || lastNode == null) {
			// if first node is null and obviously last node is also null
			Node tempNode = new Node();
			tempNode.item = itemNew;
			tempNode.nextNode = null;
			tempNode.prevNode = null;
			firstNode = tempNode;
			lastNode = tempNode;
		} else {
			// inserting node in front of first
			Node tempNode = new Node();
			tempNode.item = itemNew;
			tempNode.nextNode = firstNode;
			tempNode.prevNode = null;
			firstNode.prevNode = tempNode;
			firstNode = tempNode;
		}
		SIZE++; // Add 1 to size
	}

	// delete and return the front item
	public Item removeFront() {
		if (isEmpty())
			throw new java.util.NoSuchElementException("Deque underflow");

		Item item = firstNode.item;
		firstNode = firstNode.nextNode;
		SIZE--;

		if (SIZE == 0) {
			firstNode = null;
			lastNode = null;
		}

		return item;
	}

	// insert the item in the end
	public void addRear(Item itemNew) {
		if (itemNew == null)
			throw new java.lang.NullPointerException();

		if (lastNode == null || firstNode == null) {
			// if first node is null and obviously last node is also null
			Node tempNode = new Node();
			tempNode.item = itemNew;
			tempNode.nextNode = null;
			tempNode.prevNode = null;
			firstNode = tempNode;
			lastNode = tempNode;
		} else {
			// insert node back of last
			Node tempNode = new Node();
			tempNode.item = itemNew;
			tempNode.nextNode = null;
			tempNode.prevNode = lastNode;
			lastNode.nextNode = tempNode;
			lastNode = tempNode;
		}
		SIZE++;
	}

	// delete and return the last item
	public Item removeRear() {
		if (isEmpty())
			throw new java.util.NoSuchElementException("Deque underflow");

		Item item = lastNode.item;
		lastNode = lastNode.prevNode;
		if (lastNode != null)
			lastNode.nextNode = null;
		SIZE--;

		if (SIZE == 0) {
			firstNode = null;
			lastNode = null;
		}

		return item;
	}

	// return the number of items on the deque
	public int size() {
		return SIZE;
	}

	public Iterator<Item> iterator() {
		return new DequeIterator();
	}

	private class DequeIterator implements Iterator<Item> {
		private Node current = firstNode;

		public boolean hasNext() {
			return current != null;
		}

		public Item next() {
			if (current == null)
				throw new java.util.NoSuchElementException();
			Item item = current.item;
			current = current.nextNode;
			return item;
		}

		public void remove() {
			throw new java.lang.UnsupportedOperationException();
		}

	}

	// test client(optional)
	public static void main(String[] args) // unit testing
	{
		Deque<Integer> objDeque = new Deque<Integer>();

		for (int i = 0; i < 5; i++) {
			Integer item = new Integer(i);
			objDeque.addRear(item);
			System.out.print("Add Rear " + item);
			System.out.print("\t{ ");
			for (Integer s : objDeque)
				System.out.print(s + " ");
			System.out.println("}");
		}
		for (int i = 5; i < 10; i++) {
			Integer item = new Integer(i);
			objDeque.addFront(item);
			System.out.print("Add Front " + item);
			System.out.print("\t{ ");
			for (Integer s : objDeque)
				System.out.print(s + " ");
			System.out.println("}");
		}
		for (int i = 0; i < 5; i++) {
			System.out.print("Remove Front " + objDeque.removeFront());
			System.out.print("\t{ ");
			for (Integer s : objDeque)
				System.out.print(s + " ");
			System.out.println("}");
		}
		for (int i = 0; i < 5; i++) {
			System.out.print("Remove Rear " + objDeque.removeRear());
			System.out.print("\t{ ");
			for (Integer s : objDeque)
				System.out.print(s + " ");
			System.out.println("}");
		}

	}
}
