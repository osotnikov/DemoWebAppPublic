package osotnikov.demowebapp.services.fibonacci.constants;

public enum FibCompStep {
	
	STARTED("STARTED"),
	COMPUTED("COMPUTED"),
	CREATED_BILL("CREATED_BILL"),
	CREATED_CRM_LOG("CREATED_CRM_LOG");
	
	private String stepName;
	
	private FibCompStep(String stepName){
		this.stepName = stepName;
	}

	public String getStepName() {
		return stepName;
	}
}
