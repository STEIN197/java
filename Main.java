import java.text.ParseException;
import common.json.*;

// import common.json.*;

public class Main{
	public static void main(String ...args) throws Exception{
		String[] data = new String[]{
			"",
			"}",
			"{",
			"{}",
			"]",
			"[",
			"[]",
			" {   }",
			" [   ]",
			" [}",
			"{]  ",
			"[1]",
			"[ 1 ]",
			"[ 1 ,3, \"string\", \"string\"]",
			"[ 1 ,3, \"string\", \"string\",]",
			"{\"key\":\"value\"}",
			// "[ 1,{keyword: 1}]",
			"[ 1,{\"keyword\": 1}]",
			// "{key: 1}",
			// "{\"key\": 1, \"value\": 2}",
			// "{\"key\": 1, \"value\": 2, []}",
			// "{\"key\": 1, \"value\": 2, arr:[]}",
			// "{ \"key\": 1,\"value\":2,\"arr\":[]}",
		};
		for(String s : data){
			try{
				println(new Parser(s).parse());
			} catch(ParseException ex) {
				println(ex.getMessage());
			}
		}

	}
	public static void println(Object o){
		System.out.println(o);
	}
}