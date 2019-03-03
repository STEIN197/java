package json;

public class JSONBoolean extends JSONEntity{

	public final JSONType type = JSONType.BOOLEAN;
	public Boolean value;

	public JSONBoolean(boolean value){
		this.value = new Boolean(value);
	}

	public JSONBoolean(Boolean value){
		this.value = value;
	}
}