package common.json;

import java.util.LinkedList;

public class JSONList extends JSONEntity{
	public final JSONType type = JSONType.LIST;
	private LinkedList<JSONEntity> value;

	public JSONList(String data){
		this.value = JSONList.parse(data);
	}

	public JSONList(LinkedList<JSONEntity> data){
		this.value = data;
	}

	public JSONList(){
		this.value = new LinkedList<>();
	}

	public void add(JSONEntity e){
		this.value.add(e);
	}

	public JSONEntity remove(int index){
		return this.value.remove(index);
	}

	public int size(){
		return this.value.size();
	}

	public static LinkedList<JSONEntity> parse(String data){}
}