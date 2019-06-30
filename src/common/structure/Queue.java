package common.structure;

/**
 * Implementation of simple queue (FIFO).
 * Queue can hold unlimited amount of items or (if {@link #limit} is not zero),
 * fixed amount of items. If queue limit is present and amount of stored items
 * is already equals to limit, then the head item is removed from the queue.
 * @param <T> Type of objects stored in a queue.
 */
public class Queue<T> {

	/** Maximum number of objects that queue can store. 0 if queue has no limit (default). */
	public final int limit;

	/** Pointer to the head item on the queue. */
	private Item<T> head = null;
	/** Pointer to the last item on the queue. */
	private Item<T> tail = null;
	/** Stack size. */
	private int size = 0;

	/**
	 * Creates an empty queue and restrict it's size by {@code limit} parameter.
	 * Zero if queue has no limit.
	 * @param limit Maximum number of objects that queue can store.
	 */
	public Queue(int limit){
		this.limit = limit;
	}

	/**
	 * Creates a queue and fills it with given elements.
	 * The most left argument is a head of the queue.
	 * @param items Objects that will be pushed in the queue.
	 */
	public Queue(T ...items){
		this.limit = 0;
		for(var e : items)
			this.inqueue(e);
	}

	/**
	 * Creates a queue, sets queue limit and fills it with given items.
	 * The most left argument is a head of the queue.
	 * @param limit Maximum number of objects that queue can store.
	 * @param items Objects that will be pushed in the queue.
	 */
	public Queue(int limit, T ...items){
		this.limit = limit;
		for(var e : items)
			this.inqueue(e);
	}

	/**
	 * Places an item on the tail of the queue.
	 * If queue has limit and it is reached, then head item is removed from
	 * the queue and head pointer shifts to previous item.
	 * @param item Item to be placed in tail of the queue.
	 */
	public void inqueue(T item){
		var wrapped = new Item<T>(item);
		if(this.size == 0){
			this.tail = this.head = wrapped;
			this.size++;
		} else {
			this.tail.prevItem = wrapped;
			wrapped.nextItem = this.tail;
			this.tail = wrapped;
			var limitHasReached = this.limit > 0 && this.size == this.limit;
			if(limitHasReached){
				this.head = this.head.prevItem;
				this.head.nextItem = null;
			} else {
				this.size++;
			}
		}
	}

	/**
	 * Removes from the queue head item and returns it.
	 * Returns null if queue is empty.
	 * @return Head item.
	 */
	public T dequeue(){
		if(this.head == null)
			return null;
		this.size--;
		var item = this.head;
		this.head = item.prevItem;
		if(this.head != null)
			this.head.nextItem = null;
		return item.item;
	}

	/**
	 * Returns head item from queue.
	 * @return Head item.
	 */
	public T peek(){
		try {
			return this.head.item;
		} catch(NullPointerException ex) {
			return null;
		}
	}

	/**
	 * Returns queue size.
	 * @return Queue size.
	 */
	public int size(){
		return this.size;
	}
}
