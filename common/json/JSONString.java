package common.json;

public class JSONString extends JSONEntity{
	public final JSONType type = JSONType.STRING;
	public final String value;

	public JSONString(String value){
		this.value = value;
	}

	@Override
	public String toString(){
		return '"' + this.value + '"';
	}
}