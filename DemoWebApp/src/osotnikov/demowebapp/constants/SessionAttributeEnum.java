package osotnikov.demowebapp.constants;

public enum SessionAttributeEnum {
	
	COMMON_BG_COLOR_SESSION_ATT("COMMON_BG_COLOR_SESSION_ATT"),
	LAST_FIB_COMP_REQ("LAST_FIB_COMP_REQ"),
	LOGGED_IN_USER("LOGGED_IN_USER");
	
	private String name;
	
	private SessionAttributeEnum(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
