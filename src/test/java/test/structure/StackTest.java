package test.structure;

import common.structure.Stack;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class StackTest {
	@Test
	public void sizeIsCorrect(){
		var s = new Stack();
		s.push(1);
		assertEquals("Stack size length", 1, s.size());
		s.push(2);
		assertEquals("Stack size length", 2, s.size());
		s.push(3);
		assertEquals("Stack size length", 3, s.size());
		s.pop();
		assertEquals("Stack size length", 2, s.size());
		s.push(3);
		assertEquals("Stack size length", 3, s.size());
	}

	@Test
	public void sizeIsZero(){
		var s = new Stack();
		assertEquals("Stack size is 0", 0, s.size());
		s.pop();
		assertEquals("Stack size is 0", 0, s.size());
		s.push(12);
		s.push(13);
		s.pop();
		s.pop();
		s.pop();
		assertEquals(0, s.size());
	}

	@Test
	public void sizeDoesNotExceedLimit(){
		var s = new Stack(3);
		s.push(10);
		s.push(20);
		s.push(30);
		s.push(40);
		assertEquals(s.limit, s.size());
		s.push(50);
		assertEquals(s.limit, s.size());
	}

	@Test
	public void limitIsCorrect(){
		var s = new Stack(1);
		assertEquals(1, s.limit);
		s = new Stack(100);
		assertEquals(100, s.limit);
	}

	@Test
	public void unlimitedStackIfLimitIsZero(){
		var s = new Stack(0);
		s.push(1);
		s.push(2);
		assertEquals(2, s.size());
	}

	@Test
	public void limitedStackSizeIsCorrect(){
		var s = new Stack(5);
		s.push(1);
		assertEquals(1, s.size());
		s.push(1);
		assertEquals(2, s.size());
		s.push(1);
		assertEquals(3, s.size());
		s.push(1);
		assertEquals(4, s.size());
		s.push(1);
		assertEquals(5, s.size());
		s.push(1);
		assertEquals(5, s.size());
		s.pop();
		assertEquals(4, s.size());
		s.pop();
		assertEquals(3, s.size());
	}

	@Test
	public void unlimitedStackPopOrderIsCorrect(){
		var s = new Stack<String>();
		s.push("a");
		s.push("ab");
		s.push("abc");
		assertEquals("abc", s.pop());
		assertEquals("ab", s.pop());
		assertEquals("a", s.pop());
		assertEquals(null, s.pop());
	}

	@Test
	public void stackCanHoldNull(){
		var s = new Stack<Integer>();
		s.push(0);
		s.push(null);
		s.push(1);
		assertEquals((int) 1, (int) s.pop());
		assertEquals(null, s.pop());
		assertEquals((int) 0, (int) s.pop());
	}

	@Test
	public void fullOfNullIsCorrect(){
		var s = new Stack();
		s.push(null);
		s.push(null);
		assertEquals(null, s.pop());
		assertEquals(null, s.pop());
		assertEquals(null, s.pop());
	}

	@Test
	public void sizeIsCorrectIfStackHoldsNull(){
		var s = new Stack();
		assertEquals(0, s.size());
		s.push(null);
		assertEquals(1, s.size());
		s.push(null);
		assertEquals(2, s.size());
		s.pop();
		assertEquals(1, s.size());
		s.pop();
		assertEquals(0, s.size());
	}

	@Test
	public void limitedStackPopOrderIsCorrect(){
		var s = new Stack<Double>(3);
		s.push(1d);
		s.push(2d);
		s.push(3d);
		assertEquals(new Double(3), s.pop());
		assertEquals(new Double(2), s.pop());
		assertEquals(new Double(1), s.pop());
		assertEquals(null, s.pop());
		s = new Stack<Double>(3);
		s.push(1d);
		s.push(2d);
		s.push(3d);
		s.push(4d);
		s.push(5d);
		assertEquals(new Double(5), s.pop());
		assertEquals(new Double(4), s.pop());
		assertEquals(new Double(3), s.pop());
		assertEquals(null, s.pop());
	}

	public void peekIsCorrect(){
		var s = new Stack<String>();
		assertEquals(null, s.peek());
		s.push("s");
		assertEquals("s", s.peek());
		s.push("a");
		assertEquals("a", s.peek());
	}

	public void autoFillIsCorrect(){
		var s = new Stack<String>("a", "b", "c");
		assertEquals("c", s.pop());
		assertEquals("b", s.pop());
		assertEquals("a", s.pop());
	}
}
