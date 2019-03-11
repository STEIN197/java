package common.math;

import java.io.PrintStream;
import java.util.function.DoubleFunction;
import common.Printable;

/**
 * Класс для работы с математическими матрицами.
 * Здесь перечислены все доступные операции над матрицами, такие как
 * умножение, деление, сложение и т.д. Любые действия так или иначе затрагивающие структуру матрицы
 * создают новый объект
 */
public class Matrix implements Cloneable, Printable {

	/** Количество строк в матрице */
	public final int rows;
	/** Количество столбцов матрицы */
	public final int cols;
	/** Внутреннее представление матрицы в виде двумерного массива */
	public final double[][] matrix;

	/** Ранг матрицы */
	private int rang = -1;

	/**
	 * Создаёт матрицу с уже предопределёнными числовыми значениями
	 * @param matrix Двумерный массив числовых значений
	 */
	public Matrix(double[][] matrix) {
		this.matrix = matrix;
		this.rows = matrix.length;
		this.cols = matrix[0].length;
	}

	/**
	 * Создаёт пустую матрицу заданной размерности, все значения заполняются нулями
	 * @param rows Количество строк
	 * @param cols Количество столбцов
	 */
	public Matrix(int rows, int cols) {
		this.matrix = new double[rows][cols];
		this.rows = rows;
		this.cols = cols;
	}

	/**
	 * Проверяет на равенство размерности текущую и переданную матрицу
	 * @param Matrix Матрица, размерность которой сравнивается с размерностью текущей
	 * @return {@code true} если обе матрицы одного размера
	 */
	public boolean hasIdenticalDimensions(Matrix mx){
		return this.rows == mx.rows && this.cols == mx.cols;
	}

	/**
	 * Складывает две матрицы
	 * @param mx Матрица с которой складывается текущая
	 * @return Новую матрицу
	 * @throws Exception Если матрицы имеют несовместимые размеры
	 */
	public Matrix add(Matrix mx) throws Exception {
		if(this.hasIdenticalDimensions(mx))
			throw new Exception("Given matrix has incompatible dimension size with current object");
		double[][] matrix = new double[this.rows][this.cols];
		for(int row = 0; row < this.rows; row++)
			for(int col = 0; col < this.cols; col++)
				matrix[row][col] = this.matrix[row][col] + mx.matrix[row][col];
		return new Matrix(matrix);
	}

	/**
	 * Умножает матрицу на указанное число.
	 * Умножение производится поэлементным умножением на {@code n}
	 * @param n Число на которое умножается матрица
	 * @return Новую матрицу
	 */
	public Matrix multipleByNumber(double n){
		Matrix mx = this.clone();
		mx.forEach(value -> value * n);
		return mx;
	}

	/**
	 * Умножает текущую матрицу (A) на матрицу {@code mx} (B).
	 * При этом, {@code A * B != B * A}. Умножение выполняется путём суммы произведений
	 * i-й строки матрицы A на j-й столбец матрицы B
	 * @param mx Матрица на которую умножается текущая
	 * @throws Exception Если количество столбцов матрицы A не равно количеству строк матрицы B
	 */
	public Matrix multiple(Matrix mx) throws Exception {
		if(this.cols != mx.rows)
			throw new Exception("Cannot multiple by given matrix with " + mx.rows + " rows");
		double[][] result = new double[this.rows][mx.cols];
		double[] row, col;
		for(int i = 0; i < this.rows; i++){
			row = this.getRow(i);
			for(int j = 0; j < mx.cols; j++){
				col = mx.getCol(j);
				double sum = 0;
				for(int pos = 0; pos < this.cols; pos++)
					sum += row[pos] * col[pos];
				result[i][j] = sum;
			}
		}
		return new Matrix(result);
	}

	/**
	 * Возвращает строку из матрицы по указанному индексу
	 * @param index Индекс строки
	 * @return Строку из матрицы по индексу {@code index}. При этом возвращается именно копия, а не ссылка на строку
	 */
	public double[] getRow(int index){
		double[] result = new double[cols];
		System.arraycopy(this.matrix[index], 0, result, 0, cols);
		return result;
	}

	/**
	 * Возвращает столбец из матрицы по указанному индексу
	 * @param index Индекс столбца
	 * @return Столбец из матрицы по индексу {@code index}
	 */
	public double[] getCol(int index){
		double[] result = new double[rows];
		for(int row = 0; row < this.rows; row++)
			result[row] = this.matrix[row][index];
		return result;
	}

	/**
	 * Меняет местами строки расположенные по индексам {@code a} и {@code b}
	 * @param a Первая строка
	 * @param b Вторая строка
	 */
	public void swapRows(int a, int b){
		double[] tmp = this.matrix[a];
		this.matrix[a] = this.matrix[b];
		this.matrix[b] = tmp;
		tmp = null;
	}

	/**
	 * Меняет местами столбцы расположенные по индексам {@code a} и {@code b}
	 * @param a Первый столбец
	 * @param b Второй столбец
	 */
	public void swapCols(int a, int b){
		double tmp;
		for(int row = 0; row < this.rows; row++){
			tmp = this.matrix[row][a];
			this.matrix[row][a] = this.matrix[row][b];
			this.matrix[row][b] = tmp;
		}
	}

	/**
	 * Транспонирует матрицу
	 * @return Новую траспонированную матрицу
	 */
	public Matrix transpose(){
		double[][] result = new double[this.cols][this.rows];
		for(int row = 0; row < this.rows; row++)
			for(int col = 0; col < this.cols; col++)
				result[col][row] = this.matrix[row][col];
		return new Matrix(result);
	}

	/**
	 * Применяет результат работы переданной функции
	 * к каждому элементу матрицы.
	 * @param f Функция. Принимает на вход текущее значение элемента. Возвращаемое значение заменяет текущее
	 */
	public void forEach(DoubleFunction<Double> f){
		for(int row = 0; row < this.rows; row++)
			for(int col = 0; col < this.cols; col++)
				this.matrix[row][col] = f.apply(this.matrix[row][col]);
	}

	/**
	 * Проверяет что все элементы матрицы удовлетворяют условию {@code f}
	 * @param f Функция проверки
	 * @return {@code true} если все элементы матрицы удовлетворяют условию в функции
	 */
	public boolean every(DoubleFunction<Boolean> f){
		boolean result = true;
		for(int row = 0; row < this.rows; row++)
			for(int col = 0; col < this.cols; col++)
				result &= f.apply(this.matrix[row][col]);
		return result;
	}

	/**
	 * Проверяет, что текущая матрица является нулевой
	 * @return {@code true} если матрица нулевая
	 */
	public boolean isZero(){
		return this.every(value -> value == 0);
	}

	/**
	 * Создаёт копию текущей матрицы
	 * @return Копию текущей матрицы
	 */
	@Override
	public Matrix clone(){
		Matrix clone = new Matrix(this.rows, this.cols);
		clone.rang = this.rang;
		for(int rowNum = 0; rowNum < this.rows; rowNum++)
			for(int col = 0; col < this.cols; col++)
				clone.matrix[rowNum][col] = this.matrix[rowNum][col];
		return clone;
	}

	@Override
	public boolean equals(Object mx){
		if(this == mx)
			return true;
		if(this == null)
			return true;
		if(mx instanceof Matrix)
			return this.equals((Matrix) mx);
		return false;
	}

	/**
	 * Проверяет, являются ли две матрицы равными.
	 * Матрицы считаются равными, если они имеют одинаковую размерность и
	 * значения для каждого пересечения строки/столбца одинаковы
	 * @param mx Матрица с которой сравнивается текущая
	 * @return {@code true} если матрицы равны
	 */
	public boolean equals(Matrix mx){
		if(!this.hasIdenticalDimensions(mx))
			return false;
		for(int row = 0; row < this.rows; row++)
			for(int col = 0; col < this.cols; col++)
				if(this.matrix[row][col] != mx.matrix[row][col])
					return false;
		return true;
	}

	@Override
	public int hashCode(){
		int result = 12;
		long tmp;
		result = 31 * result + this.rows;
		result = 31 * result + this.cols;
		for(int row = 0; row < this.rows; row++)
			for(int col = 0; col < this.cols; col++){
				tmp = Double.doubleToLongBits(this.matrix[row][col]);
				result = 31 * result + ((int) (tmp ^ (tmp >>> 32)));
			}
		return result;
	}

	@Override
	public void print(PrintStream output){
		int[] max = new int[this.cols]; // Хранит количество символов, нужного для представления каждого столбца
		StringBuilder result = new StringBuilder();
		for(int row = 0; row < this.rows; row++){
			for(int col = 0; col < this.cols; col++){
				int length = Double.toString(this.matrix[row][col]).length();
				if(max[col] < length)
					max[col] = length;
			}
		}
		for(int row = 0; row < this.rows; row++){
			for(int col = 0; col < this.cols; col++){
				result.append('+');
				for(int i = 0; i < max[col]; i++){
					result.append('-');
				}
			}
			result.append('+').append('\n');
			for(int col = 0; col < this.cols; col++){
				result.append('|');
				int length = Double.toString(this.matrix[row][col]).length();
				int spaces = max[col] - length;
				result.append(this.matrix[row][col]);
				for(int i = 0; i < spaces; i++){
					result.append(' ');
				}
			}
			result.append('|').append('\n');
		}
		for(int col = 0; col < this.cols; col++){
			result.append('+');
			for(int i = 0; i < max[col]; i++){
				result.append('-');
			}
		}
		result.append('+');
		output.println(result);
	}

}