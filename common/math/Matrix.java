package common.math;

import java.io.PrintStream;
import common.Printable;

public class Matrix implements Cloneable, Printable {
	private int rows;
	private int cols;
	private int[][] matrix;
	private int rang;

	public Matrix(int[][] matrix) {
		this.matrix = matrix;
		this.rows = matrix.length;
		this.cols = matrix[0].length;
	}

	public Matrix(int rows, int cols) {
		this.matrix = new int[rows][cols];
		this.rows = rows;
		this.cols = cols;
	}

	public boolean hasIdenticalDimensions(Matrix mx){
		return
			this.matrix.length == mx.matrix.length
				&&
			this.matrix[0].length == mx.matrix[0].length;
	}

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
	public void print(PrintStream output){

	}

}