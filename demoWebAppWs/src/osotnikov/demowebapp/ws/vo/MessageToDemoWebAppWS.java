package osotnikov.demowebapp.ws.vo;

public class MessageToDemoWebAppWS {

	private TextMessage textMessage;
	private int messageId;
	private String callerId;

	public MessageToDemoWebAppWS() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MessageToDemoWebAppWS(TextMessage textMessage, int messageId, String callerId) {
		super();
		this.textMessage = textMessage;
		this.messageId = messageId;
		this.callerId = callerId;
	}

	public TextMessage getTextMessage() {
		return textMessage;
	}

	public void setTextMessage(TextMessage textMessage) {
		this.textMessage = textMessage;
	}

	public int getMessageId() {
		return messageId;
	}

	public void setMessageId(int messageId) {
		this.messageId = messageId;
	}

	public String getCallerId() {
		return callerId;
	}

	public void setCallerId(String callerId) {
		this.callerId = callerId;
	}

	@Override
	public String toString() {
		return "\ntextMessage:\n" + textMessage + "\n\nmessageId: " + 
				messageId + "\ncallerId: " + callerId;
	}
	
	
}
