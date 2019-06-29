package common;

public class DoubleLinkedList<T> {
	
	private Item<T> head;
	private Item<T> tail;
	private int size;

	public DoubleLinkedList(){}

	public DoubleLinkedList(T ...items){
		for(var e : items)
			this.add(e);
	}

	public void add(T item){
		
	}

	public int size(){
		return this.size;
	}

	/**
	 * Внутренний класс-обёртка над сохраняемыми в списке реальными
	 * значениями. Кроме данных хранит в себе ссылку на предыдущий и следующий объект в списке.
	 * @param <T> Тип объекта, сохраняемого в обёртке.
	 */
	private static class Item<T> {

		/** Реальные данные */
		public final T item;
		/** Ссылка на предыдущий элемент или {@code null}, если такого нет. */
		public Item<T> prevItem;
		/** Ссылка на следующий элемент или {@code null}, если такого нет. */
		public Item<T> nextItem;

		/**
		 * Создаёт обёртку над объектом.
		 * @param item Объект, вокруг которого будет создана обёртка.
		 */
		public Item(T item){
			this.item = item;
		}
	}
}