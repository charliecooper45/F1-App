package f1app.core;

public enum Tyres {
	OPTION, PRIME, INTER, WET;
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder(name().toLowerCase());
		char c = builder.charAt(0);
		c = Character.toUpperCase(c);
		builder.setCharAt(0, c);
		return builder.toString();
	}
}
