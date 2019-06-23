package common;

/**
 * Реализация классического стека (LIFO).
 * @param <T> Тип объектов, которые будут хранится в стеке.
 */
public class Stack<T> {

	/** Ссылка на самый верхний элемент в стеке. */
	private Item<T> top = null;
	/** Размер стека. */
	private int size = 0;

	/**
	 * Создаёт пустой стек.
	 */
	public Stack(){}

	/**
	 * Создаёт пустой стек и заполняет его указанными элементами.
	 * При этом самый правый элемент в списке параметров будет лежать на самом верху списка.
	 * @param items Объекты, помещаемые в стек.
	 */
	public Stack(T ...items){
		for(var e : items)
			this.push(e);
	}

	/**
	 * Размещает в самом верху стека переданный объект.
	 * @param item Размещаемый в стеке объект.
	 */
	public void push(T item){
		this.size++;
		var wrapped = new Item<T>(item, this.top);
		this.top = wrapped;
	}

	/**
	 * Возвращает из стека самый верхний элемент и удаляет
	 * его из него. Если стек пуст, возвращает {@code null}.
	 * @return Самый верхний элемент стека.
	 */
	public T pop(){
		if(this.top == null)
			return null;
		this.size--;
		var item = this.top;
		this.top = item.prevItem;
		item.prevItem = null;
		return item.item;
	}

	/**
	 * Возвращает самый верхний элемент стека или
	 * {@code null}, если стек пуст.
	 * @return Самый верхний элемент стека.
	 */
	public T peek(){
		try {
			return this.top.item;
		} catch(NullPointerException ex) {
			return null;
		}
	}

	/**
	 * Возвращает размер стека.
	 * @return Количество элементов в стеке.
	 */
	public int size(){
		return this.size;
	}

	/**
	 * Внутренний класс-обёртка над сохраняемыми в стеке реальными
	 * значениями. Кроме данных хранит в себе ссылку на предыдущий объект в стеке.
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
		 * @param prevItem Ссылка на предыдущий элемента в стеке или {@code null},
		 *                 если такого нет
		 */
		public Item(T item, Item<T> prevItem){
			this.item = item;
			this.prevItem = prevItem;
		}
	}
}
