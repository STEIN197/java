package common.json;

import java.util.LinkedList;

public class JSONList extends JSONComplex{
	public final JSONType type = JSONType.LIST;
	public final LinkedList<JSONEntity> value;

	// public JSONList(String data){
	// 	this.value = JSONList.parse(data);
	// }

	public JSONList(LinkedList<JSONEntity> data){
		this.value = data;
	}

	public JSONList(){
		this.value = new LinkedList<>();
	}

	@Override
	public String toString(){
		StringBuilder result = new StringBuilder();
		result.append('[');
		for(JSONEntity element : this.value){
			result.append(element);
			result.append(',');
		}
		if(this.value.size() > 0)
			result.deleteCharAt(result.length() - 1);
		result.append(']');
		return result.toString();
	}

	@Override
	protected void add(String key, JSONEntity value){
		this.value.add(value);
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