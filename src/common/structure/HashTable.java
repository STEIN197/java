package common.structure;

import java.util.NoSuchElementException;
import java.util.Iterator;

// import java.util.HashMap;

public class HashTable<K, V> {

	private static final String MESSAGE_KEY_IS_NULL = "Key value is null";

	private Object[] values;
	private int capacity = 4;
	private int mask;
	private float factor = 0.75f;
	private int size;
	
	public HashTable(int capacity) {
		this.capacity = capacity;
		this.createTable();
	}

	public HashTable(int capacity, float factor) {
		this.capacity = capacity;
		this.factor = factor;
		this.createTable();
	}

	public V get(K key) throws IllegalArgumentException, NoSuchElementException {
		if (key == null)
			throw new IllegalArgumentException(MESSAGE_KEY_IS_NULL);
		int index = this.getIndex(key);
		@SuppressWarnings(value = "unchecked")
		var list = (LinkedList<Pair<K, V>>) this.values[index];
		var listSize = list.getSize();
		if (list == null || listSize == 0)
			throw new NoSuchElementException("Values associated with " + key.toString() + " key do not exist");
		Pair<K, V> current = list.getFirst();
		for (int i = 1; i < listSize && !current.key.equals(key); i++) {
			current = list.elementAt(i);
		}
		if (current.key.equals(key))
			return current.value;
		throw new NoSuchElementException("Values associated with " + key.toString() + " key do not exist");
	}

	public V set(K key, V value) {
		if (key == null)
			throw new IllegalArgumentException(MESSAGE_KEY_IS_NULL);
		int index = this.getIndex(key);
		// Add value to empty cell
		if (this.values[index] == null) {
			if (this.tableHasReachedLimit()) {
				
			} else {
				this.values[index] = new LinkedList<Pair<K, V>>(new Pair(key, value));
				this.size++;
				return null;
			}
		// Add value at existing list
		} else {
			@SuppressWarnings(value = "unchecked")
			var list = (LinkedList<Pair<K, V>>) this.values[index];
			Iterator<Pair<K, V>> iterator = list.iterator();
			Pair<K, V> current = null;
			while (iterator.hasNext() && !(current = iterator.next()).key.equals(key));
			if (current.key.equals(key)) {
				var old = current.value;
				current.value = value;
				return old;
			} else {
				list.addLast(new Pair<K, V>(key, value));
				return null;
			}
		}
	} // TODO Enlarge table if it is too small
	
	public V remove(K key) throws IllegalArgumentException, NoSuchElementException {
		if (key == null)
			throw new IllegalArgumentException(MESSAGE_KEY_IS_NULL);
		int index = this.getIndex(key);
		@SuppressWarnings(value = "unchecked")
		var list = (LinkedList<Pair<K, V>>) this.values[index];
		int listSize = list.getSize();
		if (list == null || listSize == 0)
			throw new NoSuchElementException("Values associated with " + key.toString() + " key do not exist");
		Pair<K, V> current = list.getFirst();
		int i = 1;
		for (; i < listSize && !current.key.equals(key); i++) {
			current = list.elementAt(i);
		}
		if (current.key.equals(current)) {
			this.size--;
			V prev = current.value;
			if (listSize == 1) {
				this.values[index] = null;
			} else {
				list.removeAt(i);
			}
			return prev;
		}
		throw new NoSuchElementException("Values associated with " + key.toString() + " key do not exist");
	}

	private void createTable() {
		int size = 1 << this.capacity;
		this.values = new Object[size];
		this.mask = size - 1;
	}

	private void enlarge() {
		Object[] old = this.values;
		this.capacity++;
		this.createTable();
		this.rehash(old);
	}
	private void rehash(Object[] old) {}
	
	private int getIndex(K key) {
		return key.hashCode() & this.mask;
	}

	private boolean tableHasReachedLimit() {
		float coef = this.size / this.values.length;
		return coef <= this.factor;
	}

	private static class Pair<K, V> {

		public final K key;
		public V value;
		
		public Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}
}
// TODO Check hashes for overflow