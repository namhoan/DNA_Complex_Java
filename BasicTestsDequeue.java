package test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import mycode.Deque;

import org.junit.Test;

public class BasicTestsDequeue {

	@Test
	public void testBackAdditionOnly(){
		Deque<Integer> d = new Deque<Integer>();
		d.addRear(1);
		d.addRear(2);
		d.addRear(3);
		d.addRear(4);
		assertEquals(4, d.size());
		assertEquals(new Integer(4), d.removeRear());
		assertEquals(new Integer(3), d.removeRear());
		assertEquals(2, d.size());
		d.addRear(5);
		assertEquals(3, d.size());
		assertEquals(new Integer(5), d.removeRear());
		assertEquals(new Integer(2), d.removeRear());
		assertEquals(new Integer(1), d.removeRear());
		assertEquals(0, d.size());
	}
	
	@Test(expected=java.lang.NullPointerException.class)
	public void testAddNullFront(){
		Deque<Integer> d = new Deque<Integer>();
		d.addFront(null);
	}
	
	@Test(expected=java.lang.NullPointerException.class)
	public void testAddNullBack(){
		Deque<Integer> d = new Deque<Integer>();
		d.addRear(null);
	}
	
	@Test(expected=java.lang.UnsupportedOperationException.class)
	public void testUnsupportedIteratorRemove(){
		Deque<Integer> d = new Deque<Integer>();
		Iterator<Integer> ints = d.iterator();
		ints.remove();
	}
	
	@Test(expected=java.util.NoSuchElementException.class)
	public void testEmptyIteratorNext(){
		Deque<Integer> d = new Deque<Integer>();
		Iterator<Integer> ints = d.iterator();
		ints.next();
	}
	
	@Test
	public void testIteratorNext(){
		Deque<Integer> d = new Deque<Integer>();
		for(int i = 0; i < 1000; i++){
			d.addRear(i);
		}
		Iterator<Integer> ints = d.iterator();
		for(int i = 0; i < 1000; i++){
			ints.next();
		}
	}
	
	@Test(expected=java.util.NoSuchElementException.class)
	public void testEmptyIteratorNextFailure(){
		Deque<Integer> d = new Deque<Integer>();
		for(int i = 0; i < 1000; i++){
			d.addRear(i);
		}
		Iterator<Integer> ints = d.iterator();
		for(int i = 0; i < 1000; i++){
			ints.next();
		}
		ints.next();
	}
	
	@Test
	public void testWithStrings(){
		Deque<String> d = new Deque<String>();
		d.addFront("L");
		d.addRear("L");
		d.addRear("D");
		d.addFront("E");
		d.removeRear();
		d.addRear("O WO");
		d.addRear("ZS");
		d.addFront("ANTONELLA");
		d.removeRear();
		d.addRear("RL");
		d.removeFront();
		d.addFront("H");
		d.addRear("D");
		d.addRear("ABCDEFGHIHIHIH");
		d.removeRear();
		String result = "";
		for (String s : d){
			result += s;
		}
		assertEquals("HELLO WORLD", result);
	}
	
	@Test(expected=java.util.NoSuchElementException.class)
	public void testSimpleOutOfBoundsFront(){
		Deque<Integer> d = new Deque<Integer>();
		d.removeFront();
	}
	
	@Test(expected=java.util.NoSuchElementException.class)
	public void testSimpleOutOfBoundsRear(){
		Deque<Integer> d = new Deque<Integer>();
		d.removeRear();
	}
	
	@Test
	public void testUpTheLadder(){
		Deque<Integer> d = new Deque<Integer>();
		for(int i = 0; i < 100; i++){
			assertEquals(0, d.size());
			d.addFront(i);
			assertEquals(new Integer(i), d.removeRear());
		}
	}
	
	@Test
	public void testBackEnd(){
		Deque<Integer> d = new Deque<Integer>();
		d.addRear(1);
		assertEquals(new Integer(1), d.removeFront());
	}
	
	@Test
	public void testFrontEnd(){
		Deque<Integer> d = new Deque<Integer>();
		d.addFront(1);
		assertEquals(new Integer(1), d.removeRear());
	}
	
	@Test
	public void testAddingBothWays(){
		Deque<Integer> d = new Deque<Integer>();
		for(int i = 0; i < 100; i++){
			if (i % 2 == 0){
				d.addFront(i);
				d.addRear(1000 - i);
			} else {
				d.addFront(1000 - i);
				d.addRear(i);
			}
		}
		
		assertEquals(200, d.size());
		
		for (int i = 99; i >= 0; i--){
			int expected = i;
			if (i % 2 == 0){
				expected = 1000 - i;
			}
			assertEquals(new Integer(expected), d.removeRear());
		}
		
		assertEquals(100, d.size());
		
		for (int i = 99; i >= 0; i--){
			int expected = i;
			if (i % 2 == 1){
				expected = 1000 - i;
			}
			assertEquals(new Integer(expected), d.removeFront());
		}
	}
	
	@Test
	public void testRandomBehavior(){
		List<Integer> list = new ArrayList<Integer>();
		Deque<Integer> d = new Deque<Integer>();
		
		for (int i = 0; i < 1000; i++){
			Integer newInt = new Integer((int) (1000 * Math.random()));
			double command = Math.random();
			if (command < .2){
				list.add(0, newInt);
				d.addFront(newInt);
				assertEquals(list.size(), d.size());
			} else if (command < .4) {
				list.add(list.size(), newInt);
				d.addRear(newInt);
				assertEquals(list.size(), d.size());
			} else if (command < .7 && d.size() > 0) {
				assertEquals(list.remove(list.size()-1), d.removeRear());
				assertEquals(list.size(), d.size());
			} else if (command < 1 && d.size() > 0){
				assertEquals(list.remove(0), d.removeFront());
				assertEquals(list.size(), d.size());
			}
		}
	}
	
	@Test
	public void testIterator(){
		List<Integer> list = new ArrayList<Integer>();
		Deque<Integer> d = new Deque<Integer>();
		
		for (int i = 0; i < 1000; i++){
			Integer newInt = new Integer((int) (1000 * Math.random()));
			double command = Math.random();
			if (command < .2){
				list.add(0, newInt);
				d.addFront(newInt);
				assertEquals(list.size(), d.size());
			} else if (command < .4) {
				list.add(list.size(), newInt);
				d.addRear(newInt);
				assertEquals(list.size(), d.size());
			} else if (command < .7 && d.size() > 0) {
				assertEquals(list.remove(list.size()-1), d.removeRear());
				assertEquals(list.size(), d.size());
			} else if (command < 1 && d.size() > 0){
				assertEquals(list.remove(0), d.removeFront());
				assertEquals(list.size(), d.size());
			}
		}
	}
	
	private static void testDequeIterator(List<Object> expected, Deque<Object> deque){
		int index = 0;
		for (Object o : deque){
			assertEquals(expected.get(index), o);
			index++;
		}
		index = 0;
		Iterator<Object> actual = deque.iterator();
		
		while(actual.hasNext()){
			assertEquals(expected.get(index++), actual.next());
		}
		assertEquals(index, deque.size());
		assertEquals(index, expected.size());
	}
}
