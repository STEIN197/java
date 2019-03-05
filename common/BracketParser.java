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
public class BracketParser{

	/** Круглые скобки (( и )) */
	public static final byte BRACE_ROUND = 0b0001;
	/** Квадратные скобки ([ и ]) */
	public static final byte BRACE_SQUARE = 0b0010;
	/** Угловые скобки (< и >) */
	public static final byte BRACE_CORNER = 0b0100;
	/** Фигурные скобки ({ и }) */
	public static final byte BRACE_CURVE = 0b1000;

	/** Изначальная строка переданная в конструкторе */
	public final String raw;
	/** Скобки, которые будут искаться в исходной строке */
	public BracePair brace;

	/** Глубина залегания скобок */
	private int depth;

	public BracketParser(String data, byte brace, int depth) throws ParseException{
		this.raw = data;
		this.brace = new BracePair(brace);
		this.setDepth(depth);
	}

	public BracketParser(String data, BracePair brace, int depth){
		this.raw = data;
		this.brace = brace;
		this.setDepth(depth);
	}

	public BracketParser(String data, int depth){
		this.raw = data;
		this.setDepth(depth);
	}

	/**
	 * Устанавливает глубину выводимых скобок
	 * @param depth Устанавливаемая глубина. Если она меньше 1, то выставляется в 1 как минимальное значение
	 */
	public void setDepth(int depth){
		if(depth < 1)
			this.depth = 1;
		else
			this.depth = depth;
	}

	/**
	 * Парсит строку {@code data} и выделяет в ней подстроки в скобках глубины {@code depth}.
	 * @return Список найденых подстрок в скобках указанной глубины 
	 * @throws ParseException Если в исходной строке оказалось слишком много открывающих/закрывающих скобок
	 * @see #checkDepth(byte)
	 */
	public ArrayList<String> parse() throws ParseException{
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
	 * @param depth Проверяемое значение глубины. Всегда должно быть равно нулю
	 * @throws ParseException Если переданное значение меньше или больше нуля. Если значение не равно нулю,
	 * то открывающих или закрывающих скобок в строке больше чем ожидается
	 */
	private static void checkDepth(int depth) throws ParseException{
		if(depth < 0)
			throw new ParseException("Too many closing braces", -1);
		if(depth > 0)
			throw new ParseException("Too many opening braces", 1);
	}

	/**
	 * Представляет собой пары открывающих/закрывающих скобок.
	 * Имеет только два константных поля - {@code start} и {@code end}
	 */
	public static class BracePair{

		public final char start;
		public final char end;

		/**
		 * Создаёт пару открывающих/закрывающих скобок.
		 * @param brace Одна из констант {@code BracketParser.BRACE_*}
		 */
		public BracePair(byte brace) throws ParseException{
			switch(brace){
				case BracketParser.BRACE_ROUND:
					this.start = '(';
					this.end = ')';
					break;
				case BracketParser.BRACE_SQUARE:
					this.start = '[';
					this.end = ']';
					break;
				case BracketParser.BRACE_CORNER:
					this.start = '<';
					this.end = '>';
					break;
				case BracketParser.BRACE_CURVE:
					this.start = '{';
					this.end = '}';
					break;
				default:
					throw new ParseException("There is no brace with given code", 0);
			}
		}
	}
}