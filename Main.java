import common.Queue;

public class Main {
	public static void main(String ...args){
		var q = new Queue<String>("1", "3", "5");
		q.inqueue("7");
		while(q.size() != 0)
			System.out.println(q.dequeue());
	}
}
