package common.json;

import java.text.ParseException;
import java.util.HashMap;
import java.util.ArrayList;
import common.BracketParser;

public class JSONObject extends JSONEntity{
	public final JSONType type = JSONType.OBJECT;
	public final HashMap<String, JSONEntity> value;

	// public JSONObject(String data){
	// 	this.value = JSONObject.parse(data);
	// }
	public JSONObject(HashMap<String, JSONEntity> data){
		this.value = data;
	}
	public JSONObject(){
		this.value = new HashMap<>();
	}

	private static class Parser{
		public final String raw;
		public Parser(String data){
			this.raw = data;
		}
	}

	public static HashMap<String, JSONEntity> parse(String data) throws ParseException, Exception{
		HashMap<String, JSONEntity> result = new HashMap<>();
		data = trim(data);
		StringBuilder key;
		StringBuilder value;
		for(char c : data.toCharArray()){
			
		}
		return result;
	}

	private static String trim(String data) throws ParseException, Exception{
		BracketParser parser = new BracketParser(data, BracketParser.BRACE_CURVE, 1);
		ArrayList<String> list = parser.parse();
		if(list.size() > 1)
			throw new Exception("There are more than one JSON objects in given string");
		if(list.size() == 0)
			throw new Exception("Given string is not JSON object");
		return list.get(0);
	}
}