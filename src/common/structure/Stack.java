package common.structure;

/**
 * Implementation of simple stack (LIFO).
 * Stack can hold unlimited amount of items or (if {@link #limit} is not zero),
 * fixed amount of items. If stack limit is present and amount of stored items
 * is already equals to limit, then the bottomest item is removed from the stack.
 * @param <T> Type of objects stored in a stack.
 */
public class Stack<T> {

	/** Maximum number of objects that stack can store. 0 if stack has no limit (default). */
	public final int limit;

	/** Pointer to the topest item on a stack. */
	private Item<T> top = null;
	/** Pointer to the bottomest item on a stack. */
	private Item<T> bottom = null;
	/** Stack size. */
	private int size = 0;

	/**
	 * Creates an empty stack and restrict it's size by {@code limit} parameter.
	 * Zero if stack has no limit.
	 * @param limit Maximum number of objects that stack can store.
	 */
	public Stack(int limit){
		this.limit = limit;
	}

	/**
	 * Creates a stack and fills it with given elements.
	 * The most right argument will be on top of the stack.
	 * @param items Objects that will be pushed in the stack.
	 */
	public Stack(T ...items){
		this.limit = 0;
		for(var e : items)
			this.push(e);
	}

	/**
	 * Creates a stack, sets stack limit and fills it with given items.
	 * The most right argument will be on top of the stack.
	 * @param limit Maximum number of objects that stack can store.
	 * @param items Objects that will be pushed in the stack.
	 */
	public Stack(int limit, T ...items){
		this.limit = limit;
		for(var e : items)
			this.push(e);
	}

	/**
	 * Pushes an {@code item} on the top of the stack.
	 * If stack size exceeds {@link #limit}, then the bottomest
	 * item pops out of the stack.
	 * @param item An object to be pushed on top of the stack.
	 */
	public void push(T item){
		var wrapped = new Item<T>(item);
		if(this.size == 0){
			this.bottom = this.top = wrapped;
			this.size++;
		} else {
			this.top.nextItem = wrapped;
			wrapped.prevItem = this.top;
			this.top = wrapped;
			var limitHasReached = this.limit > 0 && this.size == this.limit;
			if(limitHasReached){
				this.bottom = this.bottom.nextItem;
				this.bottom.prevItem = null;
			} else {
				this.size++;
			}
		}
	}

	/**
	 * Pops an item from top of the stack and removes it
	 * from the stack. Returns {@code null} if stack is empty.
	 * @return Topest item in the stack.
	 */
	public T pop(){
		if(this.top == null)
			return null;
		this.size--;
		var item = this.top;
		this.top = item.prevItem;
		if(this.top != null)
			this.top.nextItem = null;
		return item.item;
	}

	/**
	 * Returns topest item on the stack or {@code null},
	 * if stack is empty.
	 * @return The topest item on the stack.
	 */
	public T peek(){
		try {
			return this.top.item;
		} catch(NullPointerException ex) {
			return null;
		}
	}

	/**
	 * Returns stack size.
	 * @return Amount of items stored in stack.
	 */
	public int size(){
		return this.size;
	}
}
