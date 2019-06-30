package common.structure;

/**
 * Inner class-wrapper over stored real data in structures
 * like {@link Stack}, {@link Queue} and {@link LinkedList}.
 * Besides real data stores references to the previous and next items.
 * @param <T> Type of object that is stored in wrapper.
 */
class Item<T> {
	
	/** Real data */
	public final T item;
	/** Reference to the next item in the structure. */
	public Item<T> nextItem;
	/** Reference to the previous item in the structure. */
	public Item<T> prevItem;

	/**
	 * Creates a wrapper.
	 * @param item An object around which wrapper is built.
	 */
	public Item(T item){
		this.item = item;
	}
}
