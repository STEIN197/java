package common;

import java.util.ArrayList;
import java.io.PrintStream;

public class ASCIITable implements Printable {

	/** Выравнивание содержимого по верхнему краю */
	public static final byte ALIGN_TOP = 0b0001;
	/** Выравнивание содержимого по правому краю */
	public static final byte ALIGN_RIGHT = 0b0010;
	/** Выравнивание содержимого по нижнему краю */
	public static final byte ALIGN_BOTTOM = 0b0100;
	/** Выравнивание содержимого по левому краю */
	public static final byte ALIGN_LEFT = 0b1000;

	/** Количество колонок в таблице. Минимальное количество колонок в таблице - 1 */
	public final int cols;

	/** Строки таблицы */
	private ArrayList<String[]> rows = new ArrayList<>();
	/** Массив размерностью с {@link #cols}. Каждый элемент массива хранит в себе максимальную длину значения для соответствующего столбца */
	private int[] maxWidth;
	/** Массив размерностью с {@link #cols}, хранящий фиксированные значения ширины столбцов */
	private int[] colWidth;
	/** Массив размерностью с {@link #cols} содержащий настройки выравнивания для каждого столбца */
	private byte[] alignment;

	/**
	 * Создаёт ascii-таблицу с указанным количеством колонок.
	 * Если колонок меньше 1, то выставляется 1
	 * @param cols Количество колонок
	 */
	public ASCIITable(int cols){
		this.cols = cols < 1 ? 1 : cols;
		this.maxWidth = new int[this.cols];
		this.colWidth = new int[this.cols];
		this.alignment = new byte[this.cols];
		this.setAlignment(ASCIITable.ALIGN_LEFT);
	}

	/**
	 * Добавляет одну строку в конец таблицы. Каждый аргумент соответствует своему столбцу.
	 * Если количество аргументов больше чем количество столбцов в таблице,
	 * то последние значения отбрасываются. Если же количество аргументов меньше,
	 * чем количество столбцов в таблице, то недостающие ячейки просто заполняются пустыми значениями.
	 * @param row Данные для заполнения строки
	 * @return Текущую таблицу
	 */
	public ASCIITable addRow(String ...row){
		String[] rowToInsert = new String[this.cols];
		for(int i = 0; i < this.cols; i++){
			try{
				rowToInsert[i] = row[i];
			} catch (ArrayIndexOutOfBoundsException ex) {
				rowToInsert[i] = "";
			}
			int l = rowToInsert[i].length();
			if(this.maxWidth[i] < l)
				this.maxWidth[i] = l;
		}
		this.rows.add(rowToInsert);
		return this;
	}

	@Override
	public void print(PrintStream out){
		StringBuilder output = new StringBuilder();
		int rowsCount = this.rows.size();
		for(int i = 0; i < rowsCount; i++){
			this.drawBorder(output);
			this.drawRow(output, i);
		}
		this.drawBorder(output);
		out.println(output);
	}

	/**
	 * Устанавливает выраванивание содержимого для указанного столбца.
	 * При этом выравнивание может быть как горизонтальным,
	 * так и вертикальным, а также и тем и другим.
	 * Для этого можно использовать маски выравниваний, например:
	 * <pre>
	 * ASCIITable t = new ASCIITable(3);
	 * t.setAlignment(1, ASCIITable.ALIGN_LEFT | ASCIITable.ALIGN_RIGHT); // Выравнивание по центру по горизонтали для центральной колонки
	 * t.setAlignment(0, ASCIITable.ALIGN_TOP | ASCIITable.ALIGN_BOTTOM); // Выравнивание по центру по вертикали для первой колонки
	 * </pre>
	 * @param index Номер столбца для которого указывается выравнивание
	 * @param alignment Выравнивание. Одна из четырёх констант {@code ASCIITAble.ALIGN_*},
	 *                  либо комбинация из нескольких
	 * @throws ArrayIndexOutOfBoundsException Если столбца с указанным смещением не существует
	 */
	public void setAlignment(int index, byte alignment) throws ArrayIndexOutOfBoundsException{
		try{
			this.alignment[index] = alignment;
		} catch(ArrayIndexOutOfBoundsException ex) {
			throw new ArrayIndexOutOfBoundsException("There is no such column with offset " + index);
		}
	}

	/**
	 * Устанавливает одинаковое выравнивание для всех столбцов таблицы
	 * @param alignment Выравнивание. Одна из четырёх констант {@code ASCIITAble.ALIGN_*}, либо комбинация из нескольких
	 * @see #setAlignment(int, byte)
	 */
	public void setAlignment(byte alignment){
		for(int i = 0; i < this.cols; i++)
			this.alignment[i] = alignment;
	}

	/**
	 * Устанавливает фиксированную ширину для столбца с указанным номером.
	 * При этом контент, превышающий ширину колонки будет разбит на несколько строк
	 * @param index Номер столбца для которого указывается выравнивание
	 * @param size Ширина столбца. Если она меньше нуля, то выставляется 0 (по умолчанию)
	 * @throws ArrayIndexOutOfBoundsException Если столбца с указанным смещением не существует
	 */
	public void setColWidth(int index, int size) throws ArrayIndexOutOfBoundsException {
		try {
			this.colWidth[index] = size < 0 ? 0 : size;
		} catch(ArrayIndexOutOfBoundsException ex){
			throw new ArrayIndexOutOfBoundsException("There is no such column with offset " + index);
		}
	}

	/**
	 * Устанавливает фиксированную ширину столбцов для всей таблицы
	 * @param size Размер столбца в пробелах. Если она меньше нуля, то выставляется в 0
	 */
	public void setColWidth(int size){
		size = size < 0 ? 0 : size;
		for(int i = 0; i < this.cols; i++)
			this.colWidth[i] = size;
	}

	/**
	 * Рисует горизонтальную границу между строками таблицы по типу:
	 * <p>
	 * <code>
	 * +---+-------+
	 * </code>
	 * <p>
	 * При этом граница всегда оканчиваниется символом новой строки {@code \n}
	 * @param output Строка к которой конкатенируется результат
	 */
	private void drawBorder(StringBuilder output){
		for(int i = 0; i < this.cols; i++){
			int width = this.getColWidth(i);
			output.append('+');
			for(int j = 0; j < width; j++)
				output.append('-');
		}
		output.append('+').append('\n');
	}

	/**
	 * Выводит каждую строку таблицы
	 * @param output Строка-таблица, к которой конкатенируется результат
	 * @param row Значения ячеек
	 */
	private void drawRow(StringBuilder output, int index){
		StringBuilder rowString = new StringBuilder();
		String[] row = this.rows.get(index);
		int height = this.getRowHeight(index);
		for(int i = 0; i < this.cols; i++){
			// Do smth
		}
		output.append(rowString);
	}

	/**
	 * Возвращает ширину указанного столбца, которая будет
	 * выведена в консоль.
	 * @param index Индекс столбца, выводимую ширину которого нужно вывести
	 * @return Ширину столбца
	 */
	private int getColWidth(int index){
		return this.colWidth[index] > 0 ? this.colWidth[index] : this.maxWidth[index];
	}

	/**
	 * Возвращает максимальное количество текстовых строк в указанной строке таблицы,
	 * если текст в ячейках дробится на несколько строк
	 * @param index номер указанной строки
	 * @return наибольшее количество строк текста в указанной строке таблицы
	 */
	private int getRowHeight(int index){
		int colWidth;
		String[] row = this.rows.get(index);
		int l;
		int totalCount = 1;
		int linesPerCell;
		for(int i = 0; i < this.cols; i++){
			l = row[i].length();
			colWidth = this.colWidth[i] == 0 ? l : this.colWidth[i];
			if(l > colWidth){
				linesPerCell = getLinesCount(row[i], colWidth);
				if(linesPerCell > totalCount)
					totalCount = linesPerCell;
			}
		}
		return totalCount;
	}

	/**
	 * Возвращает количество строк, которые бы получились,
	 * если бы текст {@code value} нужно было бы уместить
	 * в колонку шириной {@code width} пробелов
	 * @param value Содержимое ячейки
	 * @param width Ширина столбца
	 * @return Количество строк занимаемых строкой {@code value} в колонке шириной {@code width}
	 */
	private static int getLinesCount(String value, int width){
		double l = (double) value.length();
		return (int) Math.ceil(l / width);
	}

	/**
	 * Разбивает входную строку на массив строк таким образом,
	 * чтобы каждая строка в возвращаемом массиве помещалась бы в колонку
	 * шириной {@code width} пробелов
	 * @param value Разбиваемая строка
	 * @param width Ширина колонки
	 * @return Массив строк, помещающихся в колонку шириной {@code width}
	 */
	private static String[] getLines(String value, int width){
		int count = getLinesCount(value, width);
		String[] lines = new String[count];
		for(int i = 0; i < count; i++)
			lines[i] = value.substring(i * width);
		return lines;
	}
}
