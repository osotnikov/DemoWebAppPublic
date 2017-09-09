package osotnikov.web.enums;

public enum TransferMethodEnum {
	
	FORWARD("FORWARD"),
	INTERNAL_REDIRECT("INTERNAL_REDIRECT"),
	EXTERNAL_REDIRECT("EXTERNAL_REDIRECT"),
	INCLUDE_DIRECTIVE("INCLUDE_DIRECTIVE"),
	INCLUDE_ACTION("INCLUDE_ACTION");
	
	private String name;
	
	private TransferMethodEnum(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
}
