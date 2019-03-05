
public class Main{
	public static void main(String ...args) throws Exception{
		String str = "Str\"i\\n\ng";
		for(char c : str.toCharArray()){
			System.out.println(c);
		}
	}
}