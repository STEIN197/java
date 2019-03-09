package common.json;

import java.util.HashMap;
import java.util.Map;

public class JSONObject extends JSONComplex{
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

	@Override
	public String toString(){
		StringBuilder result = new StringBuilder();
		result.append('{');
		String key;
		JSONEntity value;
		for(Map.Entry<String, JSONEntity> element : this.value.entrySet()){
			key = element.getKey();
			value = element.getValue();
			result.append('"' + key + '"' + ':');
			result.append(value);
			result.append(',');
		}
		if(this.value.size() > 0)
			result.deleteCharAt(result.length() - 1);
		result.append('}');
		return result.toString();
	}

	public String prettify(){
		StringBuilder result = new StringBuilder();
		return result.toString();
	}

	@Override
	protected void add(String key, JSONEntity value){
		this.value.put(key, value);
	}

	@Override
	protected int size(){
		return this.value.size();
	}

	@Override
	protected JSONType getType(){
		return this.type;
	}
}