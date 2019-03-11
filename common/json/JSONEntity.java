package common.json;

/**
 * Общий тип для всех типов JSON-данных
 */
public abstract class JSONEntity implements Cloneable{

	/** Поле, жёстко указывающее на тип потомка {@code JSONEntity} */
	public final JSONType type = null;
	/** Внутреннее представление объекта */
	public final Object value = null;

	@Override
	public String toString(){
		return this.value.toString();
	}
}