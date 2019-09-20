package test.structure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import common.structure.LinkedList;


public class LinkedListTest {

	@Test
	public void emptyListHasZeroSize() {
		assertEquals(0, getEmptyList().getSize());
	}

	@Test
	public void singleItemListHasOneSize() {
		assertEquals(1, getListWithSingleItem().getSize());
	}

	@Test
	public void singleItemListHasEqualEndingReferences() {
		var item = getListWithSingleItem();
		assertTrue(item.getFirst() == item.getLast());
	}
	
	private static LinkedList<String> getEmptyList() {
		return new LinkedList<String>();
	}

	private static LinkedList<String> getListWithSingleItem() {
		return new LinkedList<>("String");
	}
}
