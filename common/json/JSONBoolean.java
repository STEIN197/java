package common.json;

public class JSONBoolean extends JSONEntity{

	public final JSONType type = JSONType.BOOLEAN;
	public final Boolean value;

	public JSONBoolean(boolean value){
		this.value = Boolean.valueOf(value);
	}

	public JSONBoolean(Boolean value){
		this.value = value;
	}

	public JSONBoolean(String value){
		this.value = Boolean.valueOf(value);
	}
}