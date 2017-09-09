package osotnikov.demowebapp.services.user_management.vo;
 
public enum GroupEnum {
     
    ADMINISTRATOR("ADMINISTRATOR"), 
    USER("USER"), 
    DEFAULT("DEFAULT");
	
	private String name;
	
	private GroupEnum(String name){
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
     
}