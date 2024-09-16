package pacman;

public class Message {
	private GhostPlayer sender;
	
	private GhostPlayer recipient;
	
	private String messageText;
	
	private Object extraInformation;
	
	public Message(GhostPlayer sender, GhostPlayer recipient, String messageText, Object extraInformation){
		this.sender = sender;
		this.recipient = recipient;
		this.messageText = messageText;
		this.extraInformation = extraInformation;
	}
	
	public String getStringMessage() {
		return messageText;
	}
}
