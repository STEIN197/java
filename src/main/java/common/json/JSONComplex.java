package common.json;

abstract class JSONComplex extends JSONEntity{
	abstract protected void add(String key, JSONEntity value);
	abstract protected int size();
	abstract protected JSONType getType();
}