package json;

abstract class JSONEntity{
	public final JSONType type = null;
	public Object value;
	public Object getValue(){
		return this.value;
	}
}