package common.json;

public abstract class JSONEntity implements Cloneable{
	public final JSONType type = null;
	public final Object value = null;

	@Override
	public String toString(){
		return this.value.toString();
	}
}