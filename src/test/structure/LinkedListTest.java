package test.structure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import common.structure.LinkedList;


// TODO There are tests only on getFirst() and getLast() methods. Make more tests
public class LinkedListTest {

	private LinkedList<String> list;

	@Before
	public void before() {
		this.list = new LinkedList<>();
	}

	@Test
	public void getFirst_AfterPushingAtTheEnd_DoesNotChange() {
		var first = "A";
		this.list.addLast("A");
		assertEquals(first, this.list.getFirst());
		this.list.addLast("B");
		assertEquals(first, this.list.getFirst());
	}

	@Test
	public void getFirst_AfterPushingAtTheStart_Changes() {
		var first = "A";
		this.list.addFirst("A");
		assertEquals(first, this.list.getFirst());
		this.list.addFirst("B");
		assertNotEquals(first, this.list.getFirst());
	}

	@Test
	public void getFirst_AfterRemovingAtTheEnd_DoesNotChange() {
		var first = "A";
		fillWithABC(this.list);
		this.list.removeLast();
		assertEquals(first, this.list.getFirst());
		this.list.removeLast();
		assertEquals(first, this.list.getFirst());
		this.list.removeLast();
		assertEquals(null, this.list.getFirst());
	}

	@Test
	public void getFirst_AfterRemovingAtTheStart_Changes() {
		fillWithABC(this.list);
		this.list.removeFirst();
		assertEquals("B", this.list.getFirst());
		this.list.removeFirst();
		assertEquals("C", this.list.getFirst());
		this.list.removeFirst();
		assertNull(this.list.getFirst());
	}

	@Test
	public void getFirst_AfterClearing_IsNull() {
		fillWithABC(this.list);
		this.list.clear();
		assertNull(this.list.getFirst());
	}

	@Test
	public void getFirst_EqualsToTheZeroElement() {
		assertTrue(this.list.getFirst() == this.list.elementAt(0));
		fillWithABC(this.list);
		assertTrue(this.list.getFirst() == this.list.elementAt(0));
	}

	@Test
	public void getFirst_AfterInsertingAfterZero_DoesNotChange() {
		fillWithABC(this.list);
		this.list.insertAfter(0, "D");
		assertEquals("A", this.list.getFirst());
		this.list.insertAfter(0, "E");
		assertEquals("A", this.list.getFirst());
	}

	@Test
	public void getFirst_AfterInsertingBeforeZero_Changes() {
		fillWithABC(this.list);
		this.list.insertBefore(0, "D");
		assertEquals("D", this.list.getFirst());
		this.list.insertBefore(0, "E");
		assertEquals("E", this.list.getFirst());
	}

	@Test
	public void getFirst_AfterRemovingAtZeroInSingleItemList_IsNull() {
		this.list.addFirst("A");
		this.list.removeAt(0);
		assertNull(this.list.getFirst());
	}

	@Test
	public void getFirst_AfterRemovingAtZero_Changes() {
		fillWithABC(this.list);
		this.list.removeAt(0);
		assertEquals("B", this.list.getFirst());
		this.list.removeAt(0);
		assertEquals("C", this.list.getFirst());
		this.list.removeAt(0);
		assertNull(this.list.getFirst());
	}

	@Test
	public void getFirst_AfterRemovingInSingleItemList_IsNull() {
		this.list.addLast("A");
		this.list.removeFirst();
		assertNull(this.list.getFirst());
	}

	@Test
	public void getFirst_AfterRemoving_Changes() {
		fillWithABC(this.list);
		this.list.removeFirst();
		assertEquals("B", this.list.getFirst());
		this.list.removeFirst();
		assertEquals("C", this.list.getFirst());
		this.list.removeFirst();
		assertNull(this.list.getFirst());
	}

	@Test
	public void getFirst_AfterRemovingLastInSingleItemList_IsNull() {
		this.list.addLast("A");
		this.list.removeLast();
		assertNull(this.list.getFirst());
	}

	@Test
	public void getFirst_AfterRemovingLast_DoesNotChange() {
		fillWithABC(this.list);
		this.list.removeLast();
		assertEquals("A", this.list.getFirst());
		this.list.removeLast();
		assertEquals("A", this.list.getFirst());
		this.list.removeLast();
		assertNull(this.list.getFirst());
	}

	@Test
	public void getFirst_AfterReplacingAtZero_Changes() {
		fillWithABC(this.list);
		this.list.replaceAt(0, "D");
		assertEquals("D", this.list.getFirst());
	}

	@Test
	public void getFirst_AfterReplacing_Changes() {
		fillWithABC(this.list);
		this.list.replaceFirst("D");
		assertEquals("D", this.list.getFirst());
	}

	@Test
	public void getFirst_AfterReplacingLastInSingleItemList_Changes() {
		this.list.addLast("A");
		this.list.replaceLast("B");
		assertEquals("B", this.list.getFirst());
	}

	@Test
	public void getFirst_InEmptyList_IsNull() {
		assertNull(this.list.getFirst());
	}

	@Test
	public void getLast_AfterPushingAtTheEnd_Changes() {
		this.list.addLast("A");
		assertEquals("A", this.list.getLast());
		this.list.addLast("B");
		assertEquals("B", this.list.getLast());
	}

	@Test
	public void getLast_AfterPushingAtTheStart_DoesNotChange() {
		this.list.addFirst("A");
		assertEquals("A", this.list.getLast());
		this.list.addFirst("B");
		assertEquals("A", this.list.getLast());
	}

	@Test
	public void getLast_AfterRemovingAtTheEnd_Changes() {
		fillWithABC(this.list);
		this.list.removeLast();
		assertEquals("B", this.list.getLast());
		this.list.removeLast();
		assertEquals("A", this.list.getLast());
		this.list.removeLast();
		assertEquals(null, this.list.getLast());
	}

	@Test
	public void getLast_AfterRemovingAtTheStart_DoesNotChange() {
		fillWithABC(this.list);
		this.list.removeFirst();
		assertEquals("C", this.list.getLast());
		this.list.removeFirst();
		assertEquals("C", this.list.getLast());
		this.list.removeFirst();
		assertNull(this.list.getLast());
	}

	@Test
	public void getLast_AfterClearing_IsNull() {
		fillWithABC(this.list);
		this.list.clear();
		assertNull(this.list.getLast());
	}

	@Test
	public void getLast_EqualsToTheLastElementByIndex() {
		assertTrue(this.list.getLast() == this.list.elementAt(0));
		fillWithABC(this.list);
		assertTrue(this.list.getLast() == this.list.elementAt(this.list.getSize() - 1));
	}

	@Test
	public void getLast_AfterInsertingAfterLast_Changes() {
		fillWithABC(this.list);
		this.list.insertAfter(this.list.getSize() - 1, "D");
		assertEquals("D", this.list.getLast());
		this.list.insertAfter(this.list.getSize() - 1, "E");
		assertEquals("E", this.list.getLast());
	}

	@Test
	public void getLast_AfterInsertingBeforeLast_DoesNotChange() {
		fillWithABC(this.list);
		this.list.insertBefore(this.list.getSize() - 1, "D");
		assertEquals("C", this.list.getLast());
		this.list.insertBefore(this.list.getSize() - 1, "E");
		assertEquals("C", this.list.getLast());
	}

	@Test
	public void getLast_AfterRemovingAtLastInSingleItemList_IsNull() {
		this.list.addLast("A");
		this.list.removeAt(0);
		assertNull(this.list.getLast());
	}

	@Test
	public void getLast_AfterRemovingAtLast_Changes() {
		fillWithABC(this.list);
		this.list.removeAt(2);
		assertEquals("B", this.list.getLast());
		this.list.removeAt(1);
		assertEquals("A", this.list.getLast());
		this.list.removeAt(0);
		assertNull(this.list.getLast());
	}
	
	@Test
	public void getLast_AfterRemovingInSingleItemList_IsNull() {
		this.list.addLast("A");
		this.list.removeLast();
		assertNull(this.list.getLast());
	}

	@Test
	public void getLast_AfterRemoving_Changes() {
		fillWithABC(this.list);
		this.list.removeLast();
		assertEquals("B", this.list.getLast());
		this.list.removeLast();
		assertEquals("A", this.list.getLast());
		this.list.removeLast();
		assertNull(this.list.getLast());
	}

	@Test
	public void getLast_AfterRemovingFirstInSingleItemList_IsNull() {
		this.list.addLast("A");
		this.list.removeFirst();
		assertNull(this.list.getLast());
	}

	@Test
	public void getLast_AfterRemovingFirst_DoesNotChange() {
		fillWithABC(this.list);
		this.list.removeFirst();
		assertEquals("C", this.list.getLast());
		this.list.removeFirst();
		assertEquals("C", this.list.getLast());
		this.list.removeFirst();
		assertNull(this.list.getLast());
	}

	@Test
	public void getLast_AfterReplacingAtLast_Changes() {
		fillWithABC(this.list);
		this.list.replaceAt(2, "D");
		assertEquals("D", this.list.getLast());
	}

	@Test
	public void getLast_AfterReplacing_Changes() {
		fillWithABC(this.list);
		this.list.replaceLast("D");
		assertEquals("D", this.list.getLast());
	}

	@Test
	public void getLast_AfterReplacingFirstInSingleItemList_Changes() {
		this.list.addLast("A");
		this.list.replaceFirst("B");
		assertEquals("B", this.list.getLast());
	}

	@Test
	public void getLast_InEmptyList_IsNull() {
		assertNull(this.list.getLast());
	}

	@Test
	public void bothEndings_OnEmptyList_AreNull() {
		assertNull(this.list.getFirst());
		assertNull(this.list.getLast());
	}

	@Test
	public void bothEndings_OnSingleItemList_AreEqual() {
		this.list.addLast("A");
		assertTrue(this.list.getFirst() == this.list.getLast());
	}

	@Test
	public void getSize_OnEmptyList_IsZero() {
		assertEquals(0, new LinkedList<String>().getSize());
	}

	@Test
	public void getSize_OnInitializedList_HasCorrectValue() {
		assertEquals(1, new LinkedList<String>("A").getSize());
		assertEquals(2, new LinkedList<String>("A", "B").getSize());
		assertEquals(3, new LinkedList<String>("A", "B", "C").getSize());
	}

	@Test
	public void getSize_AfterAddingAtTheStart_HasCorrectValue() {
		this.list.addFirst("A");
		assertEquals(1, this.list.getSize());
		this.list.addFirst("B");
		assertEquals(2, this.list.getSize());
		this.list.addFirst("C");
		assertEquals(3, this.list.getSize());
	}

	@Test
	public void getSize_AfterAddingAtTheEnd_HasCorrectValue() {
		this.list.addLast("A");
		assertEquals(1, this.list.getSize());
		this.list.addLast("B");
		assertEquals(2, this.list.getSize());
		this.list.addLast("C");
		assertEquals(3, this.list.getSize());
	}

	@Test
	public void getSize_AfterFirstReplacing_HasSameValue() {
		fillWithABC(this.list);
		this.list.replaceFirst("D");
		assertEquals(3, this.list.getSize());
	}

	@Test
	public void getSize_AfterLastReplacing_HasSameValue() {
		fillWithABC(this.list);
		this.list.replaceLast("D");
		assertEquals(3, this.list.getSize());
	}

	@Test
	public void getSize_AfterRemovingFirst_IsDecreasing() {
		fillWithABC(this.list);
		this.list.removeFirst();
		assertEquals(2, this.list.getSize());
		this.list.removeFirst();
		assertEquals(1, this.list.getSize());
		this.list.removeFirst();
		assertEquals(0, this.list.getSize());
	}
	
	@Test
	public void getSize_AfterRemovingLast_IsDecreasing() {
		fillWithABC(this.list);
		this.list.removeLast();
		assertEquals(2, this.list.getSize());
		this.list.removeLast();
		assertEquals(1, this.list.getSize());
		this.list.removeLast();
		assertEquals(0, this.list.getSize());
	}

	@Test
	public void getSize_AfterReplacing_HasSameValue() {
		fillWithABC(this.list);
		this.list.replaceAt(0, "D");
		assertEquals(3, this.list.getSize());
		this.list.replaceAt(2, "E");
		assertEquals(3, this.list.getSize());
	}

	@Test
	public void getSize_AfterRemoving_IsDecreasing() {
		fillWithABC(this.list);
		this.list.removeAt(2);
		assertEquals(2, this.list.getSize());
		this.list.remove("B");
		assertEquals(1, this.list.getSize());
		this.list.removeAt(0);
		assertEquals(0, this.list.getSize());
	}

	@Test
	public void getSize_AfterInserting_IsIncreasing() {
		fillWithABC(this.list);
		this.list.insertAfter(2, "D");
		assertEquals(4, this.list.getSize());
		this.list.insertBefore(0, "E");
		assertEquals(5, this.list.getSize());
	}

	private static void fillWithABC(LinkedList<String> list) {
		list.addLast("A");
		list.addLast("B");
		list.addLast("C");
	}
}
