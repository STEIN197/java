package common;

/**
 * Реализация классической очереди (FIFO).
 * @param <T> Тип объектов, которые будут хранится в очереди.
 */
public class Queue<T> {

	/** Ссылка на самый первый элемент очереди или {@code null}, если очередь пуста. */
	private Item<T> head = null;
	/** Ссылка на самый последний элемент очереди или {@code null}, если очередь пуста. */
	private Item<T> tail = null;
	/** Размер очереди. */
	private int size = 0;

	/**
	 * Создаёт пустую очередь
	 */
	public Queue(){}

	/**
	 * Создаёт пустую очередь и заполняет её указанными элементами.
	 * При этом самый левый элемент в списке параметров будет лежать на самом верху списка.
	 * @param items Объекты, помещаемые в очередь.
	 */
	public Queue(T ...items){
		for(var e : items)
			this.inqueue(e);
	}

	/**
	 * Размещает в самом низу очереди переданный объект.
	 * @param item Размещаемый в очереди объект.
	 */
	public void inqueue(T item){
		this.size++;
		var wrapped = new Item<T>(item);
		if(this.head == null)
			this.head = wrapped;
		else
			this.tail.prevItem = wrapped;
		this.tail = wrapped;
	}

	/**
	 * Возвращает из очереди самый верхний первый и удаляет
	 * его из неё. Если очередь пуста, возвращает {@code null}.
	 * @return Самый первый элемент очереди.
	 */
	public T dequeue(){
		if(this.head == null)
			return null;
		this.size--;
		var item = this.head;
		this.head = item.prevItem;
		item.prevItem = null;
		return item.item;
	}

	/**
	 * Возвращает самый первый элемент очереди или
	 * {@code null}, если очередь пуста.
	 * @return Самый первый элемент очереди.
	 */
	public T peek(){
		try {
			return this.head.item;
		} catch(NullPointerException ex) {
			return null;
		}
	}

	/**
	 * Возвращает размер очереди.
	 * @return Количество элементов, хранящихся в очереди.
	 */
	public int size(){
		return this.size;
	}

	/**
	 * Внутренний класс-обёртка над сохраняемыми в очереди реальными
	 * значениями. Кроме данных хранит в себе ссылку на предыдущий объект в очереди.
	 * @param <T> Тип объекта, сохраняемого в обёртке.
	 */
	private static class Item<T> {

		/** Реальные данные */
		public final T item;
		/** Ссылка на предыдущий элемент или {@code null}, если такого нет. */
		public Item<T> prevItem;

		/**
		 * Создаёт обёртку над объектом.
		 * @param item Объект, вокруг которого будет создана обёртка.
		 * @param prevItem Ссылка на предыдущий элемента в очереди или {@code null},
		 *                 если такого нет.
		 */
		public Item(T item){
			this.item = item;
		}
	}
}