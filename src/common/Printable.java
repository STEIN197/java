package common;

import java.io.PrintStream;

/**
 * Все классы, реализующие этот интерфейс могут быть выведены в консоль
 * методом {@link #print(PrintStream)}
 */
public interface Printable{
	public void print(PrintStream output);
	default public void print(){
		this.print(System.out);
	}
}