package common.structure;

import java.util.NoSuchElementException;

// import java.util.HashMap;

public class HashTable<K, V> {

	private Object[] values;
	private int capacity = 4;
	private int mask;
	private float factor = 0.75f;
	
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
			throw new IllegalArgumentException("Key can't be null");
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

	public V set(K key, V value) {}
	
	public V remove(K key) throws IllegalArgumentException, NoSuchElementException {
		if (key == null)
			throw new IllegalArgumentException("Key can't be null");
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

	private void enlarge() {}
	private void rehash() {}
	
	private int getIndex(K key) {
		return key.hashCode() & this.mask;
	}

	private static class Pair<K, V> {

		public final K key;
		public final V value;
		
		public Pair(K key, V value) {
			this.key = key;
			this.value = value;
		}
	}
}
