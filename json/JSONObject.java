package json;

import java.util.HashMap;

public class JSONObject{
	public final JSONType type = JSONType.OBJECT;
	private HashMap<String, JSONEntity> value;
}