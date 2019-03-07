package common.json;

public class JSONLong extends JSONEntity{
	public final JSONType type = JSONType.LONG;
	public final Long value;
	
	public JSONLong(Long value){
		this.value = value;
	}

	public JSONLong(long value){
		this.value = new Long(value);
	}

	public JSONLong(String value){
		this.value = Long.valueOf(value);
	}

	@Override
	public String toString(){
		return this.value.toString();
	}
}