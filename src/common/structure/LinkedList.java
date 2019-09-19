package common.structure;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements Iterable<T> {

	private int size;
	private Item<T> last;
	private Item<T> first;

	public LinkedList(T... items) {
		for (var e : items)
			this.addLast(e);
	}

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

	public void setLast(T item){} // TODO
	public void setFirst(T item){} // TODO

	public T removeLast() throws NoSuchElementException {
		if (this.size == 0)
			throw new NoSuchElementException("The list is empty");
		var last = this.last;
		this.last = last.prevItem;
		last.prevItem = null;
		this.last.nextItem = null; // TODO NPE might be thrown. Tests weren't ran.
		this.size--;
		return last.item;
	}

	public T removeFirst() throws NoSuchElementException {
		if (this.size == 0)
			throw new NoSuchElementException("The list is empty");
		var first = this.first;
		this.first = first.nextItem;
		first.nextItem = null;
		this.first.prevItem = null; // TODO NPE might be thrown. Tests weren't ran.
		this.size--;
		return first.item;
	}

	public T elementAt(int index) throws ArrayIndexOutOfBoundsException {
		if (index < 0 || this.size <= index)
			throw new ArrayIndexOutOfBoundsException(index);
		Item<T> cursor = this.first;
		for (int i = 0; i < index; i++)
			cursor = cursor.nextItem;
		return cursor.item;
	}

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
	public void setAt(int index, T item) {} // TODO
	public void insertAfter(int index, T item) {} // TODO
	public void insertBefore(int index, T item) {} // TODO

	public T[] toArray() {
		var result = (T[]) new Object[this.size];
		int i = 0;
		for (var e : this) {
			result[i] = e;
			i++;
		}
		return result;
	}

	public int getSize() {
		return this.size;
	}

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

	private void addToEmptyList(T item) {
		this.first = this.last = new Item<T>(item);
	}
}
