import java.util.ArrayList;
import java.text.ParseException;

public class Main{
	public static void main(String ...args) throws Exception{
		Main.H h = new Main.H();
		System.out.println(h.value);
		h.increment();
		System.out.println(h.value);

	}
	public static abstract class G{
		public int value = 20;
		public abstract void increment();
	}
	public static class H extends G{
		public void increment(){
			this.value++;
		}
	}
}