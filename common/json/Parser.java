package common.json;

import java.text.ParseException;

/**
 * Разбирает входную строку JSON-данных в структуры {@code JSONList} или {@code JSONObject}.
 * При этом если есть вложенные структуры, то парсер разбирает их рекурсивно.
 * Разбор происходит посимвольно
 */
public class Parser{

	/** Позиция курсора до начала данных (до [ или до {) */
	protected static final byte C_START = 0;
	/** Позиция курсора до начала имени ключа (если строка это объект) */
	protected static final byte C_BEFORE_KEY = 1;
	/** Позиция курсора внутри имени ключа (если строка это объект) */
	protected static final byte C_KEY = 2;
	/** Позиция курсора после имени ключа (если строка это объект) */
	protected static final byte C_AFTER_KEY = 3;
	/** Позиция курсора перед значением */
	protected static final byte C_BEFORE_VALUE = 4;
	/** Позиция курсора внутри значениея */
	protected static final byte C_VALUE = 5;
	/** Позиция курсора после значения */
	protected static final byte C_AFTER_VALUE = 6;
	/** Позиция курсора после окончания данных (после ] или }) */
	protected static final byte C_END = 7;

	/** Ключевое слово {@code null} которое не оборачивается в кавычки в JSON-структуре */
	private static final String KEYWORD_NULL = "null";
	/** Ключевое слово {@code true} которое не оборачивается в кавычки в JSON-структуре */
	private static final String KEYWORD_TRUE = "true";
	/** Ключевое слово {@code false} которое не оборачивается в кавычки в JSON-структуре */
	private static final String KEYWORD_FALSE = "false";

	/** Первоначальная строка данных */
	public final String raw;

	/** Позиция курсора */
	private int pos = 0;
	/** Объект в который записываются входные данные */
	private JSONComplex data;
	/** Статус курсора (внутри данных, вне и т.д.) */
	private byte cursor = C_START;
	/** Глубина вложенных JSON-структур */
	private int depth = 0;
	/** Строковое представление текущего ключа (если парсится объект) */
	private StringBuilder currentKey = new StringBuilder();
	/** Строковое представление текущего значения */
	private StringBuilder currentValue = new StringBuilder();
	/** Тип текущего значения */
	private JSONType currentType = null;

	/**
	 * Создаёт экземпляр парсера
	 * @param data Валидная JSON-строка данных
	 */
	public Parser(String data){
		this.raw = data;
	}

	/**
	 * Начинает разбор JSON-строки
	 * @return Один из объектов {@code JSONList} или {@JSONObject}
	 * @throws ParseException Если входная строка оказалась невалидной
	 */
	public JSONComplex parse() throws ParseException {
		for(char c : this.raw.toCharArray()){
			switch(this.cursor){
				case C_START:
					this.checkStart(c);
					// System.out.println("C_START");
					break;
				case C_BEFORE_KEY:
					this.checkBeforeKey(c);
					// System.out.println("C_BEFORE_KEY");
					break;
				case C_KEY:
					this.recordKey(c);
					// System.out.println("C_KEY");
					break;
				case C_AFTER_KEY:
					this.checkAfterKey(c);
					// System.out.println("C_AFTER_KEY");
					break;
				case C_BEFORE_VALUE:
					this.checkBeforeValue(c);
					// System.out.println("C_BEFORE_VALUE");
					break;
				case C_VALUE:
					this.verifyValue(c);
					// System.out.println("C_VALUE");
					break;
				case C_AFTER_VALUE:
					this.checkAfterValue(c);
					// System.out.println("C_AFTER_VALUE");
					break;
				case C_END:
					this.checkEnd(c);
					// System.out.println("C_END");
					break;
				}
			this.pos++;
		}
		if(this.cursor == C_START)
			throw new ParseException("Empty string", this.pos);
		if(this.cursor != C_END)
			throw new ParseException("Unexpected end of input. Expected " + (this.data.getType() == JSONType.LIST ? ']' : '}'), this.pos);
		this.pos = 0;
		this.cursor = C_START;
		return this.data;
	}

	/**
	 * Проверяет начало данных, до появления открывающих скобок.
	 * Метод используется только в самом начале парсинга вне зависимости от вложенности структуры
	 * Метод ожидает одной из открывающих скобок [ или {
	 * @param c Проверяемый символ
	 * @throws ParseException Если встретился любой символ отличный от открывающих скобок
	 */
	private void checkStart(char c) throws ParseException {
		if(isWhitespace(c))
			return;
		if(c != '[' && c != '{')
			this.throwException(c, "Expected open brace");
		if(c == '['){
			this.cursor = C_BEFORE_VALUE;
			this.data = new JSONList();
		} else {
			this.cursor = C_BEFORE_KEY;
			this.data = new JSONObject();
		}
	}

	/**
	 * Проверяет символы после запятой или открывающей скобки на наличие начала имени ключа.
	 * Используется метод только при разборе объектов (т.е. если курсор находится внутри объекта).
	 * При этом метод ожидает либо двойную кавычку (начало имени ключа),
	 * либо закрывающую фигурную скобку, что означает что курсор находится внутри пустого объекта.
	 * @param c Проверяемый символ
	 * @throws ParseException Если какое-либо из условий описанных выше не соблюдается
	 */
	private void checkBeforeKey(char c) throws ParseException {
		if(isWhitespace(c))
			return;
		if(c == '"'){
			this.cursor = C_KEY;
		} else {
			boolean isEmpty = this.data.getType() == JSONType.OBJECT && c == '}' && this.data.size() == 0;
			if(isEmpty){
				this.cursor = C_END;
			} else {
				this.throwException(c, "Expected double quote or closing brace");
			}
		}
	}

	/**
	 * Посимвольно записывает имя ключа в объекте.
	 * Вызывается только если курсор находится в объекте
	 * @param c Символ, добавляемый к имени ключа
	 */
	private void recordKey(char c){
		if(c == '"')
			this.cursor = C_AFTER_KEY;
		else
			this.currentKey.append(c);
	}

	/**
	 * Посимвольно записывает значения хранящиеся в структуре
	 * @param c Символы значения
	 * @throws ParseException Если значения неккоректны
	 */
	private void recordValue(char c) throws ParseException {
		switch(this.currentType){
			case NULL:
			case BOOLEAN:
				this.currentValue.append(c);
				String value = this.currentValue.toString();
				String keyword;
				char first = this.currentValue.charAt(0);
				if(first == 'n')
					keyword = KEYWORD_NULL;
				else if(first == 't')
					keyword = KEYWORD_TRUE;
				else
					keyword = KEYWORD_FALSE;

				if(keyword.equals(value))
					this.submitValue();
				else if(!keyword.startsWith(value))
					this.throwException(c, "Expected " + keyword + " value");
				break;
			case NUMBER:
				if(isDigit(c)){
					this.currentValue.append(c);
				} else if(isWhitespace(c)){
					if(this.currentValue.charAt(this.currentValue.length() - 1) != '.')
						this.submitValue();
					else
						this.throwException(c, "Expected digit token");
				} else {
					switch(c){
						case '.':
							if(this.currentValue.indexOf(".") < 0)
								this.currentValue.append(c);
							else
								this.throwException(c, "There is more than one decimal point in number");
							break;
						case ',':
							if(this.currentValue.charAt(this.currentValue.length() - 1) != '.'){
								this.submitValue();
								if(this.data.getType() == JSONType.LIST)
									this.cursor = C_BEFORE_VALUE;
								else
									this.cursor = C_BEFORE_KEY;
							} else {
								this.throwException(c, "Expected digit token");
							}
							break;
						case ']':
						case '}':
							this.submitValue();
							this.cursor = C_END;
							break;
						default:
							this.throwException(c);
					}
				}
				break;
			case STRING:
				if(c == '"')
					this.submitValue();
				else
					this.currentValue.append(c);
				break;
			// TODO
			// Ошибки, если встретится структура вида "{key: {keyInner: "value}Inner"}}" или подобная,
			// т.к. парсер обрабатывает вложенные структуры рекурсивно
			// Убрать в таком случае рекурсию и сделать парсинг линейным
			case LIST:
				this.currentValue.append(c);
				if(c == '[')
					this.depth++;
				else if(c == ']')
					this.depth--;
				if(this.depth == 0)
					this.submitValue();
				break;
			case OBJECT:
				this.currentValue.append(c);
				if(c == '{')
					this.depth++;
				else if(c == '}')
					this.depth--;
				if(this.depth == 0)
					this.submitValue();
				break;
		}
	}

	/**
	 * Проверяет все символы идущие после имени ключа
	 * Метод ожидает найти двоеточие. Вызвается только если курсор находится внутри объекта
	 * @param c Проверяемый символ
	 * @throws ParseException Если встретился символ отличный от пробельных или двоеточия
	 */
	private void checkAfterKey(char c) throws ParseException{
		if(isWhitespace(c))
			return;
		else if(c == ':')
			this.cursor = C_BEFORE_VALUE;
		else
			this.throwException(c, "Expected semicolon");
	}

	/**
	 * Проверяет все символы до начала первого символа, представляющего значение
	 * Ожидает любой токен который может представлять из себя значение, либо закрывающую
	 * квадратную скобку, что означает что курсор находится внутри пустого списка
	 * @param c Проверяемый символы
	 * @throws ParseException Если какое-либо из условий описанных выше не соблюдается
	 */
	private void checkBeforeValue(char c) throws ParseException {
		if(isWhitespace(c))
			return;
		boolean isEmptyList = this.data.getType() == JSONType.LIST && c == ']' && this.data.size() == 0;
		if(isEmptyList){
			this.cursor = C_END;
		} else if(c != ',') {
			this.cursor = C_VALUE;
			this.verifyValue(c);
		} else {
			this.throwException(c, "Expected value token or closing brace");
		}
	}

	/**
	 * Проверяет на корректность все символы входящие в значение.
	 * При этом, если идет самый первый символ в значении, то по нему определяется тип значения.
	 * @param c Проверяемый символ
	 * @throws ParseException Если значение указано некорректно
	 */
	private void verifyValue(char c) throws ParseException{
		if(this.currentType == null){
			this.guessType(c);
			if(this.currentType == JSONType.STRING)
				return;
		}
		this.recordValue(c);
	}

	/**
	 * Проверяет все символы после удачной записи очередной пары ключ-значение
	 * Метод ожидает либо запятую, либо закрывающую скобку
	 * @param c Проверяемый символ
	 * @throws ParseException Если ни одно из вышеперечисленных условий не соблюлось
	 */
	private void checkAfterValue(char c) throws ParseException {
		if(isWhitespace(c)){
			return;
		} else if(c == ','){
			if(this.data.getType() == JSONType.LIST)
				this.cursor = C_BEFORE_VALUE;
			else
				this.cursor = C_BEFORE_KEY;
		} else if(c == ']'){
			if(this.data.getType() == JSONType.OBJECT)
				this.throwException(c, "Wrong closing bracket for object json type");
			this.cursor = C_END;
		} else if(c == '}') {
			if(this.data.getType() == JSONType.LIST)
				this.throwException(c, "Wrong closing bracket for list json type");
			this.cursor = C_END;
		} else {
			this.throwException(c, "Expected comma or closing bracket");
		}
	}
	
	/**
	 * Проверяет все символы после окончания потока данных
	 * @param c Проверяемый символ
	 * @throws ParseException Если встретился любой символ отличный от пробельных
	 */
	private void checkEnd(char c) throws ParseException {
		if(isWhitespace(c))
			return;
		this.throwException(c, "There should be no characters after data ending except for spaces");
	}

	/**
	 * Записывает очередную пару ключ-значение (или только значение) в
	 * поле {@code data}, когда записался последний символ значения.
	 * Это значит что ошибок до этого в разборе никаких не было.
	 * При этом перед записью значение конвертируется в конкретный тип {@code JSONEntity}
	 * @throws ParseException Если возникли ошибки внутри вложенных структур при рекурсивном разборе
	 */
	private void submitValue() throws ParseException{
		String key = this.currentKey.toString();
		String value = this.currentValue.toString();
		switch(this.currentType){
			case NULL:
				this.data.add(key, null);
				break;
			case BOOLEAN:
				this.data.add(key, new JSONBoolean(value));
				break;
			case NUMBER:
				this.data.add(key, new JSONNumber(value));
				break;
			case STRING:
				this.data.add(key, new JSONString(value));
				break;
			case LIST:
				this.data.add(key, (JSONList) new Parser(value).parse());
				break;
			case OBJECT:
				this.data.add(key, (JSONObject) new Parser(value).parse());
				break;
		}
		this.currentType = null;
		this.currentKey.delete(0, this.currentKey.length());
		this.currentValue.delete(0, this.currentValue.length());
		this.cursor = C_AFTER_VALUE;
	}

	/**
	 * Бросает ошибку парсинга со стандартным текстом
	 * @param c Символ на котором произошла ошибка
	 */
	private void throwException(char c) throws ParseException {
		throw new ParseException("Unexpected token \"" + c + "\" at position " + this.pos, this.pos);
	}
	
	/**
	 * Бросает ошибку парсинга со стандартным текстом
	 * @param c Символ на котором произошла ошибка
	 * @param additional Дополнительная информация об ошибке
	 */
	private void throwException(char c, String additional) throws ParseException {
		throw new ParseException("Unexpected token \"" + c + "\" at position " + this.pos + ". " + additional, this.pos);
	}

	/**
	 * Пытается угадать тип значения по первому символу.
	 * При этом всегда вызывается только при значении курсора равном {@code C_VALUE}
	 * и только при обнаружении первого символа.
	 * @param c Символ, с помощью которого производится попытка угадать тип значения
	 * @throws ParseException Если не удаётся определить тип значения
	 */
	private void guessType(char c) throws ParseException {
		switch(c){
			case 'n':
				this.currentType = JSONType.NULL;
				break;
			case 't':
			case 'f':
				this.currentType = JSONType.BOOLEAN;
				break;
			case '"':
				this.currentType = JSONType.STRING;
				break;
			case '{':
				this.currentType = JSONType.OBJECT;
				break;
			case '[':
				this.currentType = JSONType.LIST;
				break;
			default:
				if(isDigit(c) && c != '0')
					this.currentType = JSONType.NUMBER;
				else
					this.throwException(c, "Unknown value type");
		}
	}

	/**
	 * Проверяет, является ли переданный символ пробельным
	 * @param c Проверяемый символ
	 * @return {@code true} если символ пробельный
	 */
	private static boolean isWhitespace(char c){
		return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '\0';
	}

	/**
	 * Проверяет, является ли переданный символ числом от 0 до 9
	 * @param c Проверяемый символ
	 * @return {@code true} если символ это число
	 */
	private static boolean isDigit(char c){
		return '0' <= c && c <= '9';
	}
}