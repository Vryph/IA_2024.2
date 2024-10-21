package pacman;


public class MessageDispatcher {
	
	private static MessageDispatcher instance = null;
	
	public static MessageDispatcher getInstance() {
		if(instance == null) {
			instance = new MessageDispatcher();
		}
		return instance;
	}
	
	public void dispatchMessage(GhostPlayer sender, GhostPlayer recipient, String messageText, Object extraInformation) {
		Message message = new Message(sender, recipient, messageText, extraInformation);
		System.out.println(messageText);
		deliverMessage(recipient, message);
	}
	
	private void deliverMessage(GhostPlayer recipient, Message message) {
		if(!recipient.treatMessage(message)) {
			System.out.println("Mensagem não processada, erro!");
		}
	}
}
