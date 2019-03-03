package json;

import java.util.ArrayList;

public class JSONList extends JSONEntity{
	public final JSONType type = JSONType.LIST;
	private ArrayList<JSONEntity> value;
}