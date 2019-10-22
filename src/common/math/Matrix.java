package common.math;

import java.util.function.DoubleFunction;

/**
 * Class fow working with mathematical matrixes of doubles.
 * Every operation that produce new matrix creates new object.
 */
public class Matrix {

	/** Number of rows */
	public final int rows;
	/** Number of cols */
	public final int cols;
	/** Inner representation, actual matrix */
	private double[][] matrix;
	/** Matrix rang. Minus one represents that rang didn't calculated */
	private int rang = -1;

	/**
	 * Creates matrix with predefined values.
	 * Copies all the contens in {@code matrix} to the new one.
	 * @param matrix Array of double elements to be copied.
	 */
	public Matrix(double[][] matrix) {
		this.rows = matrix.length;
		this.cols = matrix[0].length;
		this.matrix = new double[this.rows][this.cols];
		for (int i = 0; i < matrix.length; i++)
			System.arraycopy(matrix[i], 0, this.matrix[i], 0, this.cols);
	}

	/**
	 * Creates empty matrix of size {@code rows x cols}.
	 * @param rows Number of rows.
	 * @param cols Number of cols.
	 */
	public Matrix(int rows, int cols) {
		this.matrix = new double[rows][cols];
		this.rows = rows;
		this.cols = cols;
	}

	/**
	 * Checks the matrix and {@code mx} matrix for equality
	 * of dimensions.
	 * @param Matrix Matrix against which the current one is checked.
	 * @return {@code true} if both matrixes have same size.
	 */
	public boolean hasIdenticalDimensions(Matrix mx) {
		return this.rows == mx.rows && this.cols == mx.cols;
	}

	/**
	 * Adds two matrixes
	 * @param mx Matrix to add to the current one.
	 * @return New matrix, result of addition.
	 * @throws Exception If two matrixes have different dimensions.
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
	 * Mupltiplies matrix by {@code n number}.
	 * @param n Number by which matrix is multiplied.
	 * @return New matrix.
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
}
// TODO Matrix rang, tests
