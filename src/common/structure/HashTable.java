package common.structure;

public class HashTable<K, V> {
	private Object[] values;
	private Object[] keys;
	private int capacity = 4;
	private int mask;
	private int size = 0;
	private float factor = 0.75f;

	public HashTable() {
		this.createTable();
	}
	
	public HashTable(int capacity) {
		this.capacity = capacity;
		this.createTable();
	}

	public HashTable(int capacity, float factor) {
		this.capacity = capacity;
		this.factor = factor;
		this.createTable();
	}

	public V get(K key) {
		int hash = this.hashKey(key);
		@SuppressWarnings
		V value = (V) this.values[hash];
		return value;
	}
	
	public V set(K key, V value) {}
	public V remove(K key) {}

	private void createTable() {
		int size = 1 << this.capacity;
		this.values = new Object[size];
		this.keys = new Object[size];
		this.mask = size - 1;
	}

	private int hashKey(K key) {
		int hash = key.hashCode();
		return hash & this.mask;
	}

	private void enlarge() {}
	private void rehash() {}
}
