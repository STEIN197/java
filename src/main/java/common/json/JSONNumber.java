package common.json;


public class JSONNumber extends JSONEntity{
	public final JSONType type = JSONType.NUMBER;
	public final Double value;

	public JSONNumber(double value){
		this.value = Double.valueOf(value);
	}

	public JSONNumber(int value){
		this.value = Double.valueOf(value);
	}

	public JSONNumber(String value){
		this.value = Double.valueOf(value);
	}

	public JSONNumber(Double value){
		this.value = value;
	}

	@Override
	public String toString(){
		double value = this.value.doubleValue();
		if(value % 1 == 0)
			return Integer.toString((int) value);
		return this.value.toString();
	}
}