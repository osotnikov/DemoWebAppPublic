package osotnikov.async.services.enums;

public enum ComputationState {

	COMPUTED("COMPUTED"),
	UNDER_PROCESSING("UNDER_PROCESSING"),
	NOT_REQUESTED("NOT_REQUESTED"),
	FAILED("FAILED");
	
	private String name;
	
	private ComputationState(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
