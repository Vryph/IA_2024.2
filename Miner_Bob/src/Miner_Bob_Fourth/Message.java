package Miner_Bob_Fourth;

public class Message {

	private Entity sender;
	
	private Entity recipient;
	
	private String messageText;
	
	private Object extraInformation;
	
	public Message(Entity sender, Entity recipient, String messageText, Object extraInformation) {
		this.sender = sender;
		this.recipient = recipient;
		this.messageText = messageText;
		this.extraInformation = extraInformation;
	}
	
	public String getStringMessage() {
		return messageText;
	}
}
