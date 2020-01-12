package test.structure;

import common.structure.Queue;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class QueueTest {
	@Test
	public void sizeIsCorrect(){
		var q = new Queue();
		q.inqueue(1);
		assertEquals("Stack size length", 1, q.size());
		q.inqueue(2);
		assertEquals("Stack size length", 2, q.size());
		q.inqueue(3);
		assertEquals("Stack size length", 3, q.size());
		q.dequeue();
		assertEquals("Stack size length", 2, q.size());
		q.inqueue(3);
		assertEquals("Stack size length", 3, q.size());
	}

	@Test
	public void sizeIsZero(){
		var q = new Queue();
		assertEquals("Stack size is 0", 0, q.size());
		q.dequeue();
		assertEquals("Stack size is 0", 0, q.size());
		q.inqueue(12);
		q.inqueue(13);
		q.dequeue();
		q.dequeue();
		q.dequeue();
		assertEquals(0, q.size());
	}

	@Test
	public void sizeDoesNotExceedLimit(){
		var q = new Queue(3);
		q.inqueue(10);
		q.inqueue(20);
		q.inqueue(30);
		q.inqueue(40);
		assertEquals(q.limit, q.size());
		q.inqueue(50);
		assertEquals(q.limit, q.size());
	}

	@Test
	public void limitIsCorrect(){
		var q = new Queue(1);
		assertEquals(1, q.limit);
		q = new Queue(100);
		assertEquals(100, q.limit);
	}

	@Test
	public void unlimitedQueueIfLimitIsZero(){
		var q = new Queue(0);
		q.inqueue(1);
		q.inqueue(2);
		assertEquals(2, q.size());
	}

	@Test
	public void limitedQueueSizeIsCorrect(){
		var q = new Queue(5);
		q.inqueue(1);
		assertEquals(1, q.size());
		q.inqueue(1);
		assertEquals(2, q.size());
		q.inqueue(1);
		assertEquals(3, q.size());
		q.inqueue(1);
		assertEquals(4, q.size());
		q.inqueue(1);
		assertEquals(5, q.size());
		q.inqueue(1);
		assertEquals(5, q.size());
		q.dequeue();
		assertEquals(4, q.size());
		q.dequeue();
		assertEquals(3, q.size());
	}

	@Test
	public void unlimitedQueuePopOrderIsCorrect(){
		var q = new Queue<String>();
		q.inqueue("a");
		q.inqueue("ab");
		q.inqueue("abc");
		assertEquals("a", q.dequeue());
		assertEquals("ab", q.dequeue());
		assertEquals("abc", q.dequeue());
		assertEquals(null, q.dequeue());
	}

	@Test
	public void queueCanHoldNull(){
		var q = new Queue<Integer>();
		q.inqueue(0);
		q.inqueue(null);
		q.inqueue(1);
		assertEquals((int) 0, (int) q.dequeue());
		assertEquals(null, q.dequeue());
		assertEquals((int) 1, (int) q.dequeue());
	}

	@Test
	public void fullOfNullIsCorrect(){
		var q = new Queue();
		q.inqueue(null);
		q.inqueue(null);
		assertEquals(null, q.dequeue());
		assertEquals(null, q.dequeue());
		assertEquals(null, q.dequeue());
	}

	@Test
	public void sizeIsCorrectIfQueueHoldsNull(){
		var q = new Queue();
		assertEquals(0, q.size());
		q.inqueue(null);
		assertEquals(1, q.size());
		q.inqueue(null);
		assertEquals(2, q.size());
		q.dequeue();
		assertEquals(1, q.size());
		q.dequeue();
		assertEquals(0, q.size());
	}

	@Test
	public void limitedQueuePopOrderIsCorrect(){
		var q = new Queue<Double>(3);
		q.inqueue(1d);
		q.inqueue(2d);
		q.inqueue(3d);
		assertEquals(new Double(1), q.dequeue());
		assertEquals(new Double(2), q.dequeue());
		assertEquals(new Double(3), q.dequeue());
		assertEquals(null, q.dequeue());
		q = new Queue<Double>(3);
		q.inqueue(1d);
		q.inqueue(2d);
		q.inqueue(3d);
		q.inqueue(4d);
		q.inqueue(5d);
		assertEquals(new Double(3), q.dequeue());
		assertEquals(new Double(4), q.dequeue());
		assertEquals(new Double(5), q.dequeue());
		assertEquals(null, q.dequeue());
	}

	public void peekIsCorrect(){
		var q = new Queue<String>();
		assertEquals(null, q.peek());
		q.inqueue("s");
		assertEquals("s", q.peek());
		q.inqueue("a");
		assertEquals("a", q.peek());
	}

	public void autoFillIsCorrect(){
		var s = new Queue<String>("a", "b", "c");
		assertEquals("a", s.dequeue());
		assertEquals("b", s.dequeue());
		assertEquals("c", s.dequeue());
	}
}
