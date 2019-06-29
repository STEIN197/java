package common;

import java.util.ArrayList;
import java.io.PrintStream;

/**
 * Простой класс для вывода табличных данных в консоль.
 */
public class ASCIITable implements Printable {

	/** Количество колонок в таблице. Минимальное количество колонок в таблице - 1 */
	public final int cols;

	/** Строки таблицы */
	private ArrayList<String[]> rows = new ArrayList<>();
	/** Массив размерностью с {@link #cols}. Каждый элемент массива хранит в себе максимальную длину значения для соответствующего столбца */
	private int[] maxWidth;
	/** Массив размерностью с {@link #cols}, хранящий фиксированные значения ширины столбцов */
	private int[] colWidth;

	/**
	 * Создаёт ascii-таблицу с указанным количеством колонок.
	 * Если колонок меньше 1, то выставляется 1
	 * @param cols Количество колонок
	 */
	public ASCIITable(int cols){
		this.cols = cols < 1 ? 1 : cols;
		this.maxWidth = new int[this.cols];
		this.colWidth = new int[this.cols];
	}

	/**
	 * Создаёт таблицу с предопределенными заголовками
	 * @param thead Первая строка таблицы. Исходя из
	 *              размера массива определяется количество колонок таблицы.
	 */
	public ASCIITable(String ...thead){
		this(thead.length);
		this.rows.add(thead);
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
		var output = new StringBuilder();
		var rowsCount = this.rows.size();
		for(int i = 0; i < rowsCount; i++){
			this.drawBorder(output);
			this.drawRow(output, i);
		}
		this.drawBorder(output);
		out.println(output);
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
	 * @param output Строка-таблица, с которой конкатенируется результат
	 * @param index Номер выводимой строки
	 */
	private void drawRow(StringBuilder output, int index){
		var data = this.getFormattedCells(index);
		for(int i = 0; i < data[0].length; i++){
			for(int j = 0; j < this.cols; j++){
				output.append("|").append(data[j][i]);
			}
			output.append("|\n");
		}
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
	 * Возвращает минимальную высоту строки таблицы, нужную
	 * для вмещения всех строк в ячеках.
	 * @param index Индекс указанной строки
	 * @return Наименьшую высоту строки для вмещения всего текста.
	 */
	private int getRowHeight(int index){
		String[] row = this.rows.get(index);
		int totalCount = 1;
		for(int i = 0; i < this.cols; i++){
			var cellLength = row[i].length();
			var colWidth = this.getColWidth(i);
			if(cellLength > colWidth){
				var linesPerCell = getLinesCount(row[i], colWidth);
				if(linesPerCell > totalCount)
					totalCount = linesPerCell;
			}
		}
		return totalCount;
	}

	/**
	 * Возвращает массив строк для строки {@code rowIndex} с отформатированными данными.
	 * Полученный массив строк можно использовать для вставки в таблицу при выводе
	 * на консоль
	 * @param rowIndex Индекс форматируемой строки
	 * @return Массив строк для вывода
	 */
	private String[][] getFormattedCells(int rowIndex){
		var row = this.rows.get(rowIndex);
		var height = this.getRowHeight(rowIndex);
		var result = new String[this.cols][height];
		for(int i = 0; i < this.cols; i++){
			var cellText = row[i];
			var colWidth = this.getColWidth(i);
			for(int j = 0; j < height; j++){
				String cellLine;
				int beginIndex = j * colWidth;
				int endIndex = beginIndex + colWidth;

				if(endIndex <= cellText.length())
					cellLine = cellText.substring(beginIndex, endIndex);
				else if(beginIndex <= cellText.length())
					cellLine = cellText.substring(beginIndex);
				else
					cellLine = "";

				if(cellLine.length() < colWidth){
					int spacesToInsert = colWidth - cellLine.length();
					cellLine += " ".repeat(spacesToInsert);
				}
				result[i][j] = cellLine;
			}
		}
		return result;
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
		return (int) Math.ceil((double) value.length() / width);
	}
}
