package common.parsing.bnf;

public class Element {

	public final String token;
	public final boolean isTerminal;

	public Element(String token, boolean isTerminal) {
		this.token = token;
		this.isTerminal = isTerminal;
	}
}
