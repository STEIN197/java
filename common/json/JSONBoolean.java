package common.json;

/**
 * Обёртка для логических значений в JSON-структурах
 */
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
	
	@Override
	public String toString(){
		return this.value.toString();
	}

	@Override
	public JSONBoolean clone(){
		return new JSONBoolean(this.value.booleanValue());
	}
}