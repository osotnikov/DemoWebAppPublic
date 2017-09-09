package osotnikov.testing.mockups;

public interface ProcessingMockup {

	String process(String mockupName, long duration);

	int getMockupState();

	void setMockupState(int mockupState);

}