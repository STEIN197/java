package common;

import java.text.ParseException;
import java.util.ArrayList;

/**
 * Класс для разбора строк и выявления в них скобок заданной глубины
 * Например:
 * <pre>
 * String data = "one {two {three} four} five {six}";
 * BracketParser.BracePair b = new BracketParser.BracerPair(BracketParser.BRACE);
 * BracketParser p = new BracketParser(data, b, 1);
 * p.parse();
 * </pre>
 * Вернёт список строк вида:
 * <pre>
 * {
 * 	"two {three} four",
 * 	"six"
 * }
 * </pre>
 * Один и тот же объект можно использовать для разбора только одной строки,
 * но разными способами, меняя глубину скобок и их тип
 */
public class BracketParser {

	/** Круглые скобки ('('' и ')') */
	public static final byte BRACE_ROUND = 0;
	/** Квадратные скобки ('[' и ']') */
	public static final byte BRACE_SQUARE = 1;
	/** Угловые скобки ('<' и '>') */
	public static final byte BRACE_CORNER = 2;
	/** Фигурные скобки ('{' и '}') */
	public static final byte BRACE_CURVE = 3;

	/** Изначальная строка переданная в конструкторе */
	public final String raw;
	/** Скобки, которые будут искаться в исходной строке */
	public BracePair brace;

	/** Глубина залегания скобок. Минимально допустимое значение - 1 */
	private int depth;

	/**
	 * Создаёт парсер с предопределёнными настройками для парсера.
	 * @param data Разбираемая строка.
	 * @param brace Одна из констант {@code BracketParser.BRACE_*}.
	 * @param depth Глубина залегания.
	 * @throws ParseException
	 */
	public BracketParser(String data, byte brace, int depth) throws ParseException {
		this(data, new BracePair(brace), depth);
	}

	/**
	 * Конструктор полностью аналогичен {@link #BracketParser(String, byte, int)},
	 * за исключением того, что скобка устанавливается не константой, а объектом
	 * {@link #BracketParser.BracePair}.
	 * @param data Разбираемая строка.
	 * @param brace Скобка.
	 * @param depth Глубина залегания.
	 */
	public BracketParser(String data, BracePair brace, int depth){
		this.raw = data;
		this.brace = brace;
		this.setDepth(depth);
	}

	/**
	 * Устанавливает глубину выводимых скобок
	 * @param depth Устанавливаемая глубина.
	 *              Если она меньше 1, то выставляется в 1 как минимальное значение.
	 */
	public void setDepth(int depth){
		this.depth = depth < 1 ? 1 : depth;
	}

	/**
	 * Парсит строку {@link #raw} и выделяет в ней подстроки в скобках глубины {@code depth}.
	 * @return Список найденых подстрок в скобках указанной глубины 
	 * @throws ParseException Если в исходной строке оказалось слишком много открывающих/закрывающих скобок
	 * @see #checkDepth(byte)
	 */
	public ArrayList<String> parse() throws ParseException {
		ArrayList<String> list = new ArrayList<>();
		byte depth = 0;
		StringBuilder current = null;
		for(char c : this.raw.toCharArray()){
			if(c == this.brace.start && ++depth == this.depth){
				current = new StringBuilder();
				continue;
			} else if(c == this.brace.end){
				depth--;
			}
			if(depth >= this.depth){
				current.append(c);
			} else {
				if(current != null){
					list.add(current.toString());
					current = null;
				}
			}
		}
		checkDepth(depth);
		return list;
	}

	/**
	 * Проверяет на равенство глубины скобок нулю. Используется только внутри метода {@link #parse()}
	 * @param depth Проверяемое значение глубины. Всегда должно быть равно нулю.
	 * @throws ParseException Если переданное значение меньше или больше нуля.
	 *                        Если значение не равно нулю, то открывающих или
	 *                        закрывающих скобок в строке больше чем ожидается.
	 */
	private static void checkDepth(int depth) throws ParseException {
		if(depth < 0)
			throw new ParseException("Too many closing braces", -1);
		if(depth > 0)
			throw new ParseException("Too many opening braces", 1);
	}

	/**
	 * Представляет собой пары открывающих/закрывающих скобок.
	 */
	public static class BracePair {

		/** Массив из открывающих скобок */
		private static final char[] startBraces = new char[]{
			'(', '[', '<', '{'
		};

		/** Массив из открывающих скобок */
		private static final char[] endBraces = new char[]{
			')', ']', '>', '}'
		};

		/** Открывающая скобка */
		public final char start;
		/** Закрывающая скобка */
		public final char end;

		/**
		 * Создаёт пару открывающих/закрывающих скобок.
		 * @param brace Одна из констант {@code BracketParser.BRACE_*}
		 */
		public BracePair(byte brace) throws ParseException {
			if(brace > startBraces.length)
				throw new ParseException("There is no brace with given code", 0);
			this.start = startBraces[brace];
			this.end = endBraces[brace];
		}
	}
}
