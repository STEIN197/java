package common.structure;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.UnaryOperator;

/**
 * Implementation of double-linked list. Size of this type of list is
 * unlimited. It behaves self like an array and has the same operations
 * that can be applied to regular arrays, i.e. traversal, pulling items
 * by index and forth. Each element in this structure has two references:
 * to the previous and to the next element in list. In other words, the list
 * has a form of a chain. It does not use arrays inside to store all items.
 * <p>
 * Time complexity of methods that use index as an argument (search/replace methods)
 * is O(n/2) because the list stores both references - to the end and to the start.
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
	 * Returns {@link #first} element.
	 * @return The first element of list.
	 * @throws NoSuchElementException If list is empty.
	 */
	public T getFirst() throws NoSuchElementException {
		if (this.first == null)
			throw new NoSuchElementException();
		return this.first.item;
	}

	/**
	 * Returns {@link #last} element.
	 * @return The last element of list.
	 * @throws NoSuchElementException If list is empty.
	 */
	public T getLast() {
		if (this.last == null)
			throw new NoSuchElementException();
		return this.last.item;
	}

	/**
	 * Return size of list.
	 * @return Size of list.
	 */
	public int getSize() {
		return this.size;
	}

	/**
	 * Checks if {@code item} contains in list.
	 * @param item Item to be checked.
	 * @return {@code true} if list contains the item.
	 */
	public boolean contains(T item) {
		for (T e : this)
			if (item.equals(e))
				return true;
		return false;
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
	 * @param item Object to be the first in a list.
	 * @return Previous element that was first. Removes it from list.
	 * @throws NoSuchElementException If list is empty
	 */
	public T replaceFirst(T item) throws NoSuchElementException {
		if (this.size == 0)
			throw new NoSuchElementException("List is empty");
		var old = this.first.item;
		var newItem = new Item<T>(item);
		this.first = newItem;
		if (this.size == 1)
			this.last = this.first;
		return old;
	}

	/**
	 * Replaces the last element in a list with {@code item}.
	 * @param item Object to be the last in a list.
	 * @return Previous element that was last. Removes it from list.
	 * @throws NoSuchElementException If list is empty
	 */
	public T replaceLast(T item) throws NoSuchElementException {
		if (this.size == 0)
			throw new NoSuchElementException("List is empty");
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
		Item<T> cursor;
		int middle = this.size / 2;
		if (index < middle) {
			cursor = this.first;
			for (int i = 0; i < index; i++)
				cursor = cursor.nextItem;
		} else {
			cursor = this.last;
			for (int i = this.size - 1; index != i; i--)
				cursor = cursor.prevItem;
		}
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
		int middle = this.size / 2;
		Item<T> cursor;
		if (index < middle) {
			cursor = this.first;
			for (int i = 0; i < index; i++)
				cursor = cursor.nextItem;
		} else {
			cursor = this.last;
			for (int i = this.size - 1; index != i; i--)
				cursor = cursor.prevItem;
		}
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
		if ((index + 1) == this.size)
			return this.removeLast();
		int middle = this.size / 2;
		Item<T> cursor;
		if (index < middle) {
			cursor = this.first;
			for (int i = 0; i < index; i++)
				cursor = cursor.nextItem;
		} else {
			cursor = this.last;
			for (int i = this.size - 1; index != i; i--)
				cursor = cursor.prevItem;
		}
		var prev = cursor.prevItem;
		var next = cursor.nextItem;
		prev.nextItem = next;
		next.prevItem = prev;
		this.size--;
		return cursor.item;
	}

	/**
	 * Removes the first occurence of {@code item} object and
	 * returns it.
	 * @param item Item to be removed from list.
	 * @return Removed item.
	 * @throws NoSuchElementException If there is no {@code item} element in list.
	 */
	public T remove(T item) throws NoSuchElementException {
		Item<T> cursor = this.first;
		while (cursor != null && !cursor.item.equals(item))
			cursor = cursor.nextItem;
		if (cursor == null)
			throw new NoSuchElementException();
		if (cursor == this.first)
			return this.removeFirst();
		if (cursor == this.last)
			return this.removeLast();
		var prev = cursor.prevItem;
		var next = cursor.nextItem;
		var old = cursor.item;
		prev.nextItem = next;
		next.prevItem = prev;
		this.size--;
		return old;
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

	/**
	 * Makes traversal of this list. It is different from foreach-loop
	 * because in this case we can perform traversal in reverse
	 * order. Additionally, it is possible to change value of each item.
	 * @param fn Function to be called per each item in list.
	 *           As it returns a value, the returned value is used to be
	 *           the new value of item.
	 * @param reverseOrder {@code true} if there is need to traverse list from last to the first element.
	 */
	public void traverse(UnaryOperator<T> fn, boolean reverseOrder) {
		if (this.size == 0)
			return;
		Item<T> cursor;
		if (reverseOrder) {
			cursor = this.last;
			while (cursor != null) {
				cursor.item = fn.apply(cursor.item);
				cursor = cursor.prevItem;
			}
		} else {
			cursor = this.first;
			while (cursor != null) {
				cursor.item = fn.apply(cursor.item);
				cursor = cursor.nextItem;
			}
		}
	}

	/**
	 * Clears entire list and deletes all items.
	 */
	public void clear() {
		this.size = 0;
		this.first = this.last = null;
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
