package common.math;

public class SquareMatrix extends Matrix {

	/** Определитель матрицы */
	private double dt;
	/** {@code false} если определительн для матрицы ещё не был вычислен. Устанавливается только единожды */
	private boolean hasDt = false;

	/**
	 * Создаёт пустую квадратную матрицу заданной размерности, все значения заполняются нулями
	 * @param size Размерность матрицы
	 */
	public SquareMatrix(int size){
		super(size, size);
	}

	/**
	 * Создаёт квадратную матрицу с уже предопределёнными числовыми значениями
	 * @param matrix Двумерный массив числовых значений
	 */
	public SquareMatrix(double[][] matrix) throws Exception {
		super(matrix);
		for(int row = 0; row < matrix.length; row++)
			if(matrix[0].length != matrix.length)
				throw new Exception("Given two-dimensional array has invalid amount of rows and columns for square matrix");
	}

	public double getDt(){
		this.hasDt = true;
		switch(this.rows){
			case 1:
				return this.dt = this.matrix[0][0];
			case 2:
				return this.dt = this.getQuadraticDt();
			case 3:
				return this.dt = this.getCubicDt();
			// default:
			// 	return this.dt = this.getCommonDt();
		}
		return 0;
	}

	public double getDt(boolean recalc){
		if(recalc || !this.hasDt)
			return this.getDt();
		else
			return this.dt;
	}

	// public boolean isIdentical(){

	// }

	// public SquareMatrix getReverse(){
		
	// }

	/**
	 * Возвращает детерминант для матрицы {@code 2x2}
	 * @return Детерминант матрицы {@code 2x2}
	 */
	private double getQuadraticDt(){
		return this.matrix[0][0] * this.matrix[1][1] - this.matrix[0][1] * this.matrix[1][0];
	}

	/**
	 * Возвращает детерминант для матрицы {@code 3x3} через правило треугольника
	 * @return Детерминант матрицы {@code 3x3}
	 */
	private double getCubicDt(){
		double primaryD = this.matrix[0][0] * this.matrix[1][1] * this.matrix[2][2];
		double primaryT1 = this.matrix[0][1] * this.matrix[1][2] * this.matrix[2][0];
		double primaryT2 = this.matrix[0][2] * this.matrix[2][1] * this.matrix[1][0];

		double secondaryD = this.matrix[0][2] * this.matrix[1][1] * this.matrix[0][2];
		double secondaryT1 = this.matrix[0][1] * this.matrix[1][0] * this.matrix[2][2];
		double secondaryT2 = this.matrix[0][0] * this.matrix[2][0] * this.matrix[1][2];

		return (primaryD + primaryT1 + primaryT2) - (secondaryD + secondaryT1 + secondaryT2);
	}

	// private double getCommonDt(){
		
	// }
}