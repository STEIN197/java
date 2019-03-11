import java.text.ParseException;
import common.json.*;
import common.math.*;

public class Main{
	public static void main(String ...args) throws Exception{
		Matrix mx = new Matrix(new double[][]{
			{1, 2},
			{3, 4}
		});
		mx.swapRows(0, 1);
		mx.print();
	}

	public static void println(Object o){
		System.out.println(o);
	}

	static void testJson(){
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
			"[1,2,3,4,[5,6]]",
			"[1,2,3,4,[5,6, [7,\"8\"] ,  9]  , 10, [\"11\", {\"12\" : \"]\"}]]",
			"{\"key\":\"value\", \"k2\":[1, 2]}",
			"[ 1,{\"keyword\": 1 }]",
			"[ \"gg\",{\"keyword\": 1 }]",
			"{\"key\": \"value\", \"arr\" : [1,2 ,true, false,  null, {\"inner\" : null}]}",
			"{\"key\": \"value\", \"arr\" : [1,2 ,true, false,  null, {\"inner\" : null}],}",
			"{\"key\": \"value\", \"arr\" : [1,2 ,true, false,  null, {\"inner\" : nullw}],}"
		};
		for(String s : data){
			try{
				println(new Parser(s).parse());
			} catch(ParseException ex) {
				println(s);
				println(ex.getMessage());
			}
			println("---------------------------------------------------------");
		}
	}
}