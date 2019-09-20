package common.structure;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

/**
 * Implementation of double-linked list. Size of this type of list is
 * unlimited. It behaves self like an array and has the same operations
 * that can be applied to regular arrays, i.e. traversal, pulling items
 * by index and forth. Each element in this structure has two references:
 * to the previous and to the next element in list. In other words, the list
 * has a form of a chain. It does not use arrays inside to store all items.
 * @param <T> Type of objects stored in a list.
 */
public class LinkedList<T> implements Iterable<T> {

	/** Holds count of elements in list. */
	private int size;

	/**
	 * Reference to the first element in a list. {@code null} if list is empty.
	 * If list has one element, then expression {@code first == last} evaluates
	 * to {@code true}, i.e. both references refer to the same object.
	 */
	private Item<T> first;

	/**
	 * Reference to the last element in a list. {@code null} if list is empty.
	 * If list has one element, then expression {@code first == last} evaluates
	 * to {@code true}, i.e. both references refer to the same object.
	 */
	private Item<T> last;

	/**
	 * Creates a list filled with {@code items}.
	 * @param items Items to be stored inside.
	 */
	public LinkedList(T... items) {
		for (var e : items)
			this.addLast(e);
	}

	/**
	 * Returns {@link #first} element,
	 * or null if list is empty.
	 * @return The first element of list.
	 */
	public T getFirst() {
		if (this.size == 0)
			return null;
		return this.first.item;
	}

	/**
	 * Returns {@link #last} element,
	 * or null if list is empty.
	 * @return The last element of list.
	 */
	public T getLast() {
		if (this.size == 0)
			return null;
		return this.last.item;
	}

	/**
	 * Adds an element to the begining of list.
	 * @param item Item to push to the start.
	 */
	public void addFirst(T item) {
		if (this.size == 0) {
			this.addToEmptyList(item);
			return;
		}
		var first = new Item<T>(item, null, this.first);
		this.first.prevItem = first;
		this.first = first;
		this.size++;
	}

	/**
	 * Adds an element to the end of list.
	 * @param item Item to push to the end.
	 */
	public void addLast(T item) {
		if (this.size == 0) {
			this.addToEmptyList(item);
			return;
		}
		var last = new Item<T>(item, this.last, null);
		this.last.nextItem = last;
		this.last = last;
		this.size++;
	}

	/**
	 * Replaces the first element in a list with {@code item}.
	 * It does not add an element, but if list is empty,
	 * then it just adds an element to the list returning {@code null}.
	 * @param item Object to be the first in a list.
	 * @return Previous element that was first. Removes it from list.
	 */
	public T replaceFirst(T item){
		if (this.size == 0) {
			this.addToEmptyList(item);
			return null;
		}
		var old = this.first.item;
		var newItem = new Item<T>(item);
		this.first = newItem;
		if (this.size == 1)
			this.last = this.first;
		return old;
	}

	/**
	 * Replaces the last element in a list with {@code item}.
	 * It does not add an element, but if list is empty,
	 * then it just adds an element to the list returning {@code null}.
	 * @param item Object to be the last in a list.
	 * @return Previous element that was last. Removes it from list.
	 */
	public T replaceLast(T item){
		if (this.size == 0) {
			this.addToEmptyList(item);
			return null;
		}
		var old = this.last.item;
		var newItem = new Item<T>(item);
		this.last = newItem;
		if (this.size == 1)
			this.first = this.last;
		return old;
	}

	/**
	 * Removes the first element from list.
	 * @return Removed element.
	 * @throws NoSuchElementException If list is empty.
	 */
	public T removeFirst() throws NoSuchElementException {
		if (this.size == 0)
			throw new NoSuchElementException("The list is empty");
		var first = this.first;
		if (this.size == 1) {
			this.first = this.last = null;
			this.size--;
			return first.item;
		}
		this.first = first.nextItem;
		this.first.prevItem = null;
		this.size--;
		return first.item;
	}

	/**
	 * Removes the last element from list.
	 * @return Removed element.
	 * @throws NoSuchElementException If list is empty.
	 */
	public T removeLast() throws NoSuchElementException {
		if (this.size == 0)
			throw new NoSuchElementException("The list is empty");
		var last = this.last;
		if (this.size == 1) {
			this.first = this.last = null;
			this.size--;
			return last.item;
		}
		this.last = last.prevItem;
		this.last.nextItem = null;
		this.size--;
		return last.item;
	}

	/**
	 * Returns an element at specified position.
	 * @param index Position of an element that will return.
	 * @return Element at {@code index} position.
	 * @throws ArrayIndexOutOfBoundsException If {@code index} is less than 0 or greater than size of list.
	 */
	public T elementAt(int index) throws ArrayIndexOutOfBoundsException {
		if (index < 0 || this.size <= index)
			throw new ArrayIndexOutOfBoundsException(index);
		Item<T> cursor = this.first;
		for (int i = 0; i < index; i++)
			cursor = cursor.nextItem;
		return cursor.item;
	}

	/**
	 * Replaces element at position {@code index} with {@code item}.
	 * @param index Position of overrideable element.
	 * @param item Item that replaces {@code index} element.
	 * @return Overrided element.
	 * @throws ArrayIndexOutOfBoundsException If {@code index} is less than 0 or greater than list size.
	 */
	public T replaceAt(int index, T item) throws ArrayIndexOutOfBoundsException {
		if (index < 0 || this.size <= index)
			throw new ArrayIndexOutOfBoundsException(index);
		var cursor = this.first;
		for (int i = 0; i < index; i++)
			cursor = cursor.nextItem;
		T old = cursor.item;
		cursor.item = item;
		return old;
	}

	/**
	 * Removes element at specified positions.
	 * @param index Poisition of an element that will be removed.
	 * @return Removed element.
	 * @throws ArrayIndexOutOfBoundsException If {@code index} is less than 0 or greater than size of list.
	 */
	public T removeAt(int index) throws ArrayIndexOutOfBoundsException {
		if (index < 0 || this.size <= index)
			throw new ArrayIndexOutOfBoundsException(index);
		if (index == 0)
			return this.removeFirst();
		if (index + 1 == this.size)
			return this.removeLast();
		Item<T> cursor = this.first;
		for (int i = 0; i < index; i++)
			cursor = cursor.nextItem;
		var prev = cursor.prevItem;
		var next = cursor.nextItem;
		prev.nextItem = next;
		next.prevItem = prev;
		this.size--;
		return cursor.item;
	}

	/**
	 * Inserts item after {@code index} position.
	 * Suppose {@code index} is 2. Then the new item is inserted
	 * between third and fourth element.
	 * @param index Position after which element is inserted.
	 * @param item Item to insert.
	 * @throws ArrayIndexOutOfBoundsException If {@code index} is less than 0, or greater than list size.
	 */
	public void insertAfter(int index, T item) throws ArrayIndexOutOfBoundsException {
		if (index < 0 || this.size <= index)
			throw new ArrayIndexOutOfBoundsException(index);
		var cursor = this.first;
		if (index + 1 == this.size) {
			this.addLast(item);
			return;
		}
		for (int i = 0; i < index; i++)
			cursor = cursor.nextItem;
		var next = cursor.nextItem;
		var inserted = new Item<T>(item, cursor, next);
		cursor.nextItem = inserted;
		next.prevItem = inserted;
		this.size++;
	}
	
	/**
	 * Inserts item before {@code index} position.
	 * Suppose {@code index} is 2. Then the new item is inserted
	 * between second and third element.
	 * @param index Position before which element is inserted.
	 * @param item Item to insert.
	 * @throws ArrayIndexOutOfBoundsException If {@code index} is less than 0, or greater than list size.
	 */
	public void insertBefore(int index, T item) throws ArrayIndexOutOfBoundsException {
		if (index < 0 || this.size <= index)
			throw new ArrayIndexOutOfBoundsException(index);
		var cursor = this.first;
		if (index == 0) {
			this.addFirst(item);
			return;
		}
		for (int i = 0; i < index; i++)
			cursor = cursor.nextItem;
		var prev = cursor.prevItem;
		var inserted = new Item<T>(item, prev, cursor);
		cursor.prevItem = inserted;
		prev.nextItem = inserted;
		this.size++;
	}

	/**
	 * "Cast" list to a regular array.
	 * @return Array containing elements of list.
	 */
	public T[] toArray() {
		var result = (T[]) new Object[this.size];
		int i = 0;
		for (var e : this)
			result[i++] = e;
		return result;
	}

	public void traverse(Consumer<T> fn, boolean reverseOrder) {} // TODO
	public void traverse(UnaryOperator<T> fn, boolean reverseOrder) {} // TODO

	/**
	 * Return size of list.
	 * @return Size of list.
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>(){

			public Item<T> index = LinkedList.this.first;

			@Override
			public boolean hasNext() {
				return this.index != null;
			}

			@Override
			public T next() {
				var next = this.index;
				this.index = this.index.nextItem;
				return next.item;
			}
		};
	}

	/**
	 * Adds item to the empty list.
	 * @param item Item to be added to the list.
	 */
	private void addToEmptyList(T item) {
		this.first = this.last = new Item<T>(item);
		this.size++;
	}
}
