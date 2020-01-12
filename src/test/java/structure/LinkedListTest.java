package test.structure;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;

import common.structure.LinkedList;

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
	}

	@Test
	public void getFirst_AfterRemovingAtTheStart_Changes() {
		fillWithABC(this.list);
		this.list.removeFirst();
		assertEquals("B", this.list.getFirst());
		this.list.removeFirst();
		assertEquals("C", this.list.getFirst());
	}

	@Test
	public void getFirst_EqualsToTheZeroElement() {
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

	@Test(expected = NoSuchElementException.class)
	public void getFirst_AfterRemovingAtZeroInSingleItemList_ThrowsException() {
		this.list.addFirst("A");
		this.list.removeAt(0);
		this.list.getFirst();
	}

	@Test
	public void getFirst_AfterRemovingAtZero_Changes() {
		fillWithABC(this.list);
		this.list.removeAt(0);
		assertEquals("B", this.list.getFirst());
		this.list.removeAt(0);
		assertEquals("C", this.list.getFirst());
	}

	@Test(expected = NoSuchElementException.class)
	public void getFirst_AfterRemovingInSingleItemList_ThrowsException() {
		this.list.addLast("A");
		this.list.removeFirst();
		this.list.getFirst();
	}

	@Test
	public void getFirst_AfterRemoving_Changes() {
		fillWithABC(this.list);
		this.list.removeFirst();
		assertEquals("B", this.list.getFirst());
		this.list.removeFirst();
		assertEquals("C", this.list.getFirst());
	}

	@Test(expected = NoSuchElementException.class)
	public void getFirst_AfterRemovingLastInSingleItemList_ThrowsException() {
		this.list.addLast("A");
		this.list.removeLast();
		this.list.getFirst();
	}

	@Test
	public void getFirst_AfterRemovingLast_DoesNotChange() {
		fillWithABC(this.list);
		this.list.removeLast();
		assertEquals("A", this.list.getFirst());
		this.list.removeLast();
		assertEquals("A", this.list.getFirst());
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

	@Test(expected = NoSuchElementException.class)
	public void getFirst_InEmptyList_ThrowsException() {
		this.list.getFirst();
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
	}

	@Test
	public void getLast_AfterRemovingAtTheStart_DoesNotChange() {
		fillWithABC(this.list);
		this.list.removeFirst();
		assertEquals("C", this.list.getLast());
		this.list.removeFirst();
		assertEquals("C", this.list.getLast());
	}

	@Test
	public void getLast_EqualsToTheLastElementByIndex() {
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

	@Test(expected = NoSuchElementException.class)
	public void getLast_AfterRemovingAtLastInSingleItemList_ThrowsException() {
		this.list.addLast("A");
		this.list.removeAt(0);
		this.list.getLast();
	}

	@Test
	public void getLast_AfterRemovingAtLast_Changes() {
		fillWithABC(this.list);
		this.list.removeAt(2);
		assertEquals("B", this.list.getLast());
		this.list.removeAt(1);
		assertEquals("A", this.list.getLast());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void getLast_AfterRemovingInSingleItemList_ThrowsException() {
		this.list.addLast("A");
		this.list.removeLast();
		this.list.getLast();
	}

	@Test
	public void getLast_AfterRemoving_Changes() {
		fillWithABC(this.list);
		this.list.removeLast();
		assertEquals("B", this.list.getLast());
		this.list.removeLast();
		assertEquals("A", this.list.getLast());
	}

	@Test(expected = NoSuchElementException.class)
	public void getLast_AfterRemovingFirstInSingleItemList_ThrowsException() {
		this.list.addLast("A");
		this.list.removeFirst();
		this.list.getLast();
	}

	@Test
	public void getLast_AfterRemovingFirst_DoesNotChange() {
		fillWithABC(this.list);
		this.list.removeFirst();
		assertEquals("C", this.list.getLast());
		this.list.removeFirst();
		assertEquals("C", this.list.getLast());
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

	@Test
	public void contains_ReturnsCorrectValue() {
		assertFalse(this.list.contains("A"));
		assertFalse(this.list.contains(null));
		var b = "B";
		this.list.addLast(b);
		assertTrue(this.list.contains(b));
		assertTrue(this.list.contains("B"));
	}

	@Test
	public void contains_CanAccept_Null() {
		fillWithABC(this.list);
		assertFalse(this.list.contains(null));
	}

	@Test
	public void contains_IfNullIsPresent_ReturnsTrue() {
		fillWithABC(this.list);
		this.list.addLast(null);
		assertTrue(this.list.contains(null));
	}

	@Test(expected = NoSuchElementException.class)
	public void replaceFirst_AtEmptyList_ThrowsException() {
		this.list.replaceFirst("D");
	}

	@Test
	public void replaceFirst_DoesNotChangeSize() {
		fillWithABC(this.list);
		this.list.replaceFirst("A");
		assertEquals(3, this.list.getSize());
	}

	@Test
	public void replaceFirst_ReturnsCorrectItem() {
		fillWithABC(this.list);
		assertEquals("A", this.list.replaceFirst("D"));
		assertEquals("D", this.list.replaceFirst("E"));
	}

	@Test
	public void replaceFirst_ReallyReplacesItem() {
		fillWithABC(this.list);
		this.list.replaceFirst("D");
		assertEquals("D", this.list.getFirst());
	}

	@Test
	public void replaceFirst_InSingleItemList_ReplacesLast() {
		this.list.addLast("A");
		this.list.replaceFirst("B");
		assertEquals("B", this.list.getLast());
	}

	@Test(expected = NoSuchElementException.class)
	public void replaceLast_AtEmptyList_ThrowsException() {
		this.list.replaceLast("D");
	}

	@Test
	public void replaceLast_DoesNotChangeSize() {
		fillWithABC(this.list);
		this.list.replaceLast("A");
		assertEquals(3, this.list.getSize());
	}

	@Test
	public void replaceLast_ReturnsCorrectItem() {
		fillWithABC(this.list);
		assertEquals("C", this.list.replaceLast("D"));
		assertEquals("D", this.list.replaceLast("E"));
	}

	@Test
	public void replaceLast_ReallyReplacesItem() {
		fillWithABC(this.list);
		this.list.replaceLast("D");
		assertEquals("D", this.list.getLast());
	}

	@Test
	public void replaceLast_InSingleItemList_ReplacesFirst() {
		this.list.addLast("A");
		this.list.replaceLast("B");
		assertEquals("B", this.list.getFirst());
	}

	@Test(expected = NoSuchElementException.class)
	public void removeFirst_OnEmptyList_ThrowsException() {
		this.list.removeFirst();
	}

	@Test(expected = NoSuchElementException.class)
	public void removeFirst_OnSingleItemList_RemovesLast() {
		this.list.addLast("A");
		this.list.removeFirst();
		this.list.getLast();
	}

	@Test
	public void removeFirst_ReturnsCorrectItem() {
		fillWithABC(this.list);
		assertEquals("A", this.list.removeFirst());
		assertEquals("B", this.list.removeFirst());
		assertEquals("C", this.list.removeFirst());
	}

	@Test(expected = NoSuchElementException.class)
	public void removeLast_OnEmptyList_ThrowsException() {
		this.list.removeLast();
	}

	@Test(expected = NoSuchElementException.class)
	public void removeLast_OnSingleItemList_RemovesFirst() {
		this.list.addLast("A");
		this.list.removeLast();
		this.list.getFirst();
	}

	@Test
	public void removeLast_ReturnsCorrectItem() {
		fillWithABC(this.list);
		assertEquals("C", this.list.removeLast());
		assertEquals("B", this.list.removeLast());
		assertEquals("A", this.list.removeLast());
	}

	@Test
	public void elementAt_ReturnsCorrectItem() {
		fillWithABC(this.list);
		assertEquals("A", this.list.elementAt(0));
		assertEquals("B", this.list.elementAt(1));
		assertEquals("C", this.list.elementAt(2));
	}

	@Test
	public void replaceAt_ReturnsCorrectItem() {
		fillWithABC(this.list);
		assertEquals("A", this.list.replaceAt(0, "D"));
		assertEquals("D", this.list.replaceAt(0, "E"));
		assertEquals("C", this.list.replaceAt(2, "F"));
		assertEquals("B", this.list.replaceAt(1, "G"));
	}

	@Test
	public void replaceAt_ReallyReplacesItem() {
		fillWithABC(this.list);
		this.list.replaceAt(0, "D");
		assertEquals("D", this.list.getFirst());
		assertEquals("D", this.list.elementAt(0));
		this.list.replaceAt(2, "E");
		assertEquals("E", this.list.getLast());
		assertEquals("E", this.list.elementAt(2));
	}

	@Test
	public void removeAt_RemovesCorrectItem() {
		fillWithABC(this.list);
		assertEquals("B", this.list.removeAt(1));
		assertEquals("C", this.list.elementAt(1));
		assertFalse(this.list.contains("B"));
	}

	@Test(expected = NoSuchElementException.class)
	public void remove_IfItemDoesNotExist_ThrowException() {
		fillWithABC(this.list);
		this.list.remove("D");
	}

	@Test
	public void remove_RemovesCorrectItem() {
		fillWithABC(this.list);
		assertEquals("B", this.list.remove("B"));
		assertFalse(this.list.contains("B"));
	}

	@Test
	public void remove_RemovesOnlyFirstOccurence() {
		fillWithABC(this.list);
		this.list.addLast("A");
		this.list.addLast("A");
		this.list.remove("A");
		assertTrue(this.list.contains("A"));
		this.list.remove("A");
		assertTrue(this.list.contains("A"));
		this.list.remove("A");
		assertFalse(this.list.contains("A"));
	}

	@Test
	public void remove_First_ChangesFirst() {
		fillWithABC(this.list);
		this.list.remove("A");
		assertEquals("B", this.list.getFirst());
	}

	@Test
	public void remove_Last_ChangesLast() {
		fillWithABC(this.list);
		this.list.remove("C");
		assertEquals("B", this.list.getLast());
	}

	@Test
	public void toArray_ReturnsCorrectArray() {
		assertArrayEquals(new String[]{}, this.list.toArray());
		fillWithABC(this.list);
		assertArrayEquals(new String[]{"A", "B", "C"}, this.list.toArray());
	}

	@Test
	public void forEach_IsCorrect() {
		fillWithABC(this.list);
		var array = new String[this.list.getSize()];
		int i = 0;
		for (String e : this.list) {
			array[i] = e;
			i++;
		}
		assertArrayEquals(new String[]{"A", "B", "C"}, array);
	}

	@Test
	public void traverse_IsCorrect() {
		fillWithABC(this.list);
		final var array = new String[this.list.getSize()];
		AtomicInteger i = new AtomicInteger(0);
		this.list.traverse(e -> {
			array[i.get()] = e;
			i.incrementAndGet();
			return e;
		}, false);
		assertArrayEquals(new String[]{"A", "B", "C"}, array);
	}

	@Test
	public void traverse_Reverse_IsCorrect() {
		fillWithABC(this.list);
		final var array = new String[this.list.getSize()];
		AtomicInteger i = new AtomicInteger(0);
		this.list.traverse(e -> {
			array[i.get()] = e;
			i.incrementAndGet();
			return e;
		}, true);
		assertArrayEquals(new String[]{"C", "B", "A"}, array);
	}

	@Test
	public void traverse_CanChangeItems() {
		fillWithABC(this.list);
		this.list.traverse(e -> {
			return "A";
		}, false);
		assertArrayEquals(new String[]{"A", "A", "A"}, this.list.toArray());
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void insertAfter_AtEmptyList_ThrowsException() {
		this.list.insertAfter(0, "D");
	}

	@Test
	public void insertAfter_First_IsCorrect() {
		fillWithABC(this.list);
		this.list.insertAfter(0, "D");
		assertArrayEquals(new String[]{"A", "D", "B", "C"}, this.list.toArray());
	}

	@Test
	public void insertAfter_AtMiddle_IsCorrect() {
		fillWithABC(this.list);
		this.list.insertAfter(1, "D");
		assertArrayEquals(new String[]{"A", "B", "D", "C"}, this.list.toArray());
	}

	@Test
	public void insertAfter_Last_AddsElement() {
		fillWithABC(this.list);
		this.list.insertAfter(2, "D");
		assertArrayEquals(new String[]{"A", "B", "C", "D"}, this.list.toArray());
		assertEquals("D", this.list.getLast());
	}

	@Test(expected = ArrayIndexOutOfBoundsException.class)
	public void insertBefore_AtEmptyList_ThrowsException() {
		this.list.insertBefore(0, "D");
	}

	@Test
	public void insertBefore_First_AddsElement() {
		fillWithABC(this.list);
		this.list.insertBefore(0, "D");
		assertArrayEquals(new String[]{"D", "A", "B", "C"}, this.list.toArray());
		assertEquals("D", this.list.getFirst());
	}

	@Test
	public void insertBefore_AtMiddle_IsCorrect() {
		fillWithABC(this.list);
		this.list.insertBefore(1, "D");
		assertArrayEquals(new String[]{"A", "D", "B", "C"}, this.list.toArray());
	}

	@Test
	public void insertBefore_Last_IsCorrect() {
		fillWithABC(this.list);
		this.list.insertBefore(2, "D");
		assertArrayEquals(new String[]{"A", "B", "D", "C"}, this.list.toArray());
	}

	private static void fillWithABC(LinkedList<String> list) {
		list.addLast("A");
		list.addLast("B");
		list.addLast("C");
	}
}
