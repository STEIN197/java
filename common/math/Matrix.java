package common.math;

import java.io.PrintStream;
import java.util.function.DoubleFunction;

import common.Printable;

/**
 * Класс для работы с математическими матрицами
 * Здесь перечислены все доступные операции над матрицами, такие как
 * умножение, деление, сложение и т.д.
 */
public class Matrix implements Cloneable, Printable {

	/** Количество строк в матрице */
	private final int rows;
	/** Количество столбцов матрицы */
	private final int cols;
	/** Внутреннее представление матрицы в виде двумерного массива */
	private double[][] matrix;
	/** Ранг матрицы */
	private int rang;

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
	 * Умножает матрицу на указанное число
	 * @param n Число на которое умножается матрица
	 * @return Новую матрицу
	 */
	public Matrix multipleByNumber(double n){
		Matrix mx = this.clone();
		mx.forEach(value -> value * n);
		return mx;
	}

	/**
	 * Возвращает строку из матрицы по указанному индексу
	 * @param index Индекс строки
	 * @return Строку из матрицы по индексу {@code index}. При этом возвращается именно копия, а не ссылка на строку
	 */
	public double[] getRow(int index){
		double[] result = new int[cols];
		for(int i = 0; i < this.cols; i++)
			result[i] = this.matrix[index][i];
		return result;
	}

	public Matrix multiple(Matrix mx) throws Exception {
		if(this.cols != mx.rows)
			throw new Exception("Cannot multiple by given matrix with " + mx.rows + " rows");
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
	 * Создаёт копию текущей матрицы
	 * @return Копию текущей
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

	// @Override
	// public boolean equals(Matrix mx){
	// 	// if(!this.hasIdenticalDimensions(mx))
	// 		// return false;
	// 	// return true;
	// }

	// public int hashCode(){
	// 	// return super.hashCode();
	// }

	@Override
	public void print(PrintStream output){

	}

}