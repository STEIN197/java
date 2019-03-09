package common.json;

import java.text.ParseException;
import static common.json.Parser.*;

public class ListParser {

	private static final String KEYWORD_TRUE = "true";
	private static final String KEYWORD_FALSE = "false";
	private static final String KEYWORD_NULL = "null";

	public final String raw;

	private byte cursor = C_START;
	private int pos = 0;
	private JSONList data = new JSONList();
	private int depth = 0;

	private StringBuilder currentValue = new StringBuilder();
	private JSONType currentType;

	public ListParser(String data){
		this.raw = data;
	}

	public ListParser(String data, int pos){
		this(data);
		this.pos = pos;
	}

	public JSONList parse() throws ParseException{
		int l = this.raw.length();
		char c;
		for(; this.pos < l; this.pos++){
			c = this.raw.charAt(this.pos);
			switch(this.cursor){
				case C_START:
					this.checkStart(c);
					break;
				case C_BEFORE_VALUE:
					this.checkBeforeValue(c);
					break;
				case C_VALUE:
					this.verifyValue(c);
					break;
				case C_AFTER_VALUE:
					this.checkAfterValue(c);
					break;
				case C_END:
					this.checkEnd(c);
					break;
			}
		}
		this.pos = 0;
		this.cursor = C_START;
		return this.data;
	}

	/**
	 * Проверяет все символы в строке до начала данных,
	 * т.е. до символа '['. При этом курсор далее ожидает начала значения, или закрывающую скобку (если список пустой)
	 * @param c Проверяемый символ
	 * @throws ParseException Если встретился любой символ отличный от пробельных или '['
	 */
	private void checkStart(char c) throws ParseException{
		if(isWhitespace(c)){
			return;
		} else if(c == '['){
			this.cursor = C_BEFORE_VALUE;
		} else {
			this.throwException(c, "Error occured at the start of input");
		}
	}

	/**
	 * Проверяет символы после ']'
	 * @param c Проверяемый символ
	 * @throws ParseException Если есть не-пробельные символы после окончания парсинга списка
	 */
	private void checkEnd(char c) throws ParseException{
		if(isWhitespace(c))
			return;
		else
			this.throwException(c, "Error occured at the end of input");
	}

	private void checkBeforeValue(char c) throws ParseException {
		if(isWhitespace(c)){
			return;
		} else if(c == ']'){
			if(this.data.value.size() == 0){
				this.cursor = C_END;
			} else {
				this.throwException(c, "Expected value start");
			}
		} else if(c == ','){
			this.throwException(c, "Expected value start or closing brace");
		} else {
			this.cursor = C_VALUE;
			this.verifyValue(c);
		}
	}

	private void checkAfterValue(char c) throws ParseException {
		if(isWhitespace(c)){
			return;
		} else if(c == ','){
			this.cursor = C_BEFORE_VALUE;
		} else if(c == ']'){
			this.cursor = C_END;
		} else {
			this.throwException(c, "Expected comma or end of list");
		}
	}

	private void verifyValue(char c) throws ParseException {
		if(this.currentType == null){
			this.detectType(c);
			if(this.currentType == JSONType.STRING)
				return;
		}
		this.recordValue(c);
	}

	private void detectType(char c) throws ParseException {
		switch(c){
			case '"':
				this.currentType = JSONType.STRING;
				break;
			case 't':
			case 'f':
				this.currentType = JSONType.BOOLEAN;
				break;
			case 'n':
				this.currentType = JSONType.NULL;
				break;
			case '[':
				this.currentType = JSONType.LIST;
				break;
			case '{':
				this.currentType = JSONType.OBJECT;
				break;
			default:
				if(isDigit(c)){
					this.currentType = JSONType.NUMBER;
				} else {
					throwException(c);
				}
		}
	}

	private void recordValue(char c) throws ParseException{
		String value;
		switch(this.currentType){
			case NULL:
				this.currentValue.append(c);
				value = this.currentValue.toString();
				if(KEYWORD_NULL.equals(value)){
					this.submitValue();
				} else if(!KEYWORD_NULL.startsWith(value)){
					throwException(c, "Expected null value");
				}
				break;
			case BOOLEAN:
				this.currentValue.append(c);
				value = this.currentValue.toString();
				if(value.charAt(0) == 't'){
					if(KEYWORD_TRUE.equals(value)){
						this.submitValue();
					} else if(!KEYWORD_TRUE.startsWith(value)){
						throwException(c, "Expected true value");
					}
				} else {
					if(KEYWORD_FALSE.equals(value)){
						this.submitValue();
					} else if(!KEYWORD_FALSE.startsWith(value)){
						throwException(c, "Expected false value");
					}
				}
				break;
			case NUMBER:
				// Проверить на наличие значений типа .2, .5
				if(isDigit(c) || c == '.'){
					this.currentValue.append(c);
				} else {
					this.pos--;
					this.submitValue();
				}
				break;
			case STRING:
				if(c == '"')
					this.submitValue();
				else
					this.currentValue.append(c);
				break;
			case LIST:
				this.currentValue.append(c);
				if(c == '['){
					this.depth++;
				} else if(c == ']'){
					this.depth--;
				}
				if(this.depth == 0){
					this.submitValue();
				}
		}
	}

	private void submitValue() throws ParseException {
		String value = this.currentValue.toString();
		switch(this.currentType){
			case NULL:
				this.data.value.add(null);
				break;
			case BOOLEAN:
				this.data.value.add(new JSONBoolean(value));
				break;
			case NUMBER:
				this.data.value.add(new JSONNumber(value));
				break;
			case STRING:
				this.data.value.add(new JSONString(this.currentValue.toString()));
				break;
			case LIST:
				this.data.value.add(new ListParser(value).parse());
				break;
		}
		this.currentType = null;
		this.currentValue.delete(0, this.currentValue.length());
		this.cursor = C_AFTER_VALUE;
	}

	private void throwException(char c) throws ParseException {
		throw new ParseException("Unexpected token \"" + c + "\" at position " + this.pos, pos);
	}

	private void throwException(char c, String additional) throws ParseException {
		throw new ParseException("Unexpected token \"" + c + "\" at position " + this.pos + ". " + additional, pos);
	}

	private static boolean isDigit(char c){
		return '0' <= c && c <= '9';
	}
	private static boolean isWhitespace(char c){
		return c == ' ' || c == '\t' || c == '\n' || c == '\r' || c == '\0';
	}
}