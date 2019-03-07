import java.text.ParseException;
import java.util.HashMap;
import common.json.*;

public class Main{
	public static void main(String ...args) throws Exception{
		String[] data = new String[]{
			"[\"ss\",\"gg\",\"smg\"]",
			"[\"ss\",\"gg\",\"smg\",false,false]",
			"[\"ss\",\"gg\",\"smg\",false, true]",
			"[\"ss\",\"gg\",\"smg\",false, true , 	null]",
			"[\"ss\",\"gg\",\"smg\",false, true , 	nullw]",
			"[12  ,43.5  ,3]",
			"[12  ,43.5,3]",
			"[12,43.5,3]",
			"[12,43.5,\"GG\"]",
			"[12,43.5 , \"GG\"]",
			// "[\"ss\",\"gg\",\"smg\",12, true , 	nullw]",
			// "[\"s\",12, true , 	nullw]",
			// "[\"s\", 12 , true , 	nullw]",
			// "[\"s\", 12, true , 	nullw]",
			// "[\"s\",12 , true , 	nullw]",
			// "[\"s\",12 ,true , 	nullw]",
			// "[\"s\",12 ,false ,nullw]",
			// "[\"s\",12 ,false ,nullw]end",
			// "start[\"s\",12 ,false ,nullw]end",
		};
		for(String s : data){
			try{
				JSONList l = new ListParser(s).parse();
				println(l);
			} catch(ParseException ex){
				println(ex.getMessage());
				ex.printStackTrace();
			}
		}
	}
	public static void println(Object o){
		System.out.println(o);
	}
}