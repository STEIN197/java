package common.math;

import java.io.PrintStream;
import common.Printable;

/**
 * Класс для работы с математическими матрицами
 * Здесь перечислены все доступные операции над матрицами, такие как
 * умножение, деление, сложение и т.д.
 */
public class Matrix implements Cloneable, Printable {

	/** Количество строк в матрице */
	private int rows;
	/** Количество столбцов матрицы */
	private int cols;
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
	 * Создаёт пустую матрицу заданной размерности
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
		return
			this.matrix.length == mx.matrix.length
				&&
			this.matrix[0].length == mx.matrix[0].length;
	}

	@Override
	public Matrix clone(){
		Matrix clone = new Matrix(this.rows, this.cols);
		// clone.rang = this.rang;
		// for(int rowNum = 0; rowNum < this.rows; rowNum++)
		// 	for(int col = 0; col < this.cols; col++)
		// 		clone.matrix[rowNum][col] = this.matrix[rowNum][col];
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