package osotnikov.demowebapp.ws.vo;

public class TextMessage {
	
	private String sender;
	private String subject;
	private String body;

	public TextMessage() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TextMessage(String sender, String subject, String body) {
		super();
		this.sender = sender;
		this.subject = subject;
		this.body = body;
	}
	
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "\n\tsender: " + sender + "\n\tsubject: " + subject + "\n\tbody: " + body;
	}
	
	
}
