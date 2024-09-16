package ghosts;

import pacman.Message;
import pacman.MessageDispatcher;

public class OrangeGlobalState implements State<Orange> {
	
	private static OrangeGlobalState instance = null;
	
	public static OrangeGlobalState getInstance() {
		if(instance == null) {
			instance = new OrangeGlobalState();
		}
		return instance;
	}
	
	@Override
	public void enter(Orange ghost) {
	}
	
	@Override
	public void execute(Orange ghost) {
	}
	
	@Override
	public void exit(Orange ghost) {
	}
	
	@Override
	public boolean atMessageReceived(Orange ghost, Message message) {
		if (message.getStringMessage().compareTo("Stalk!") == 0) {
			ghost.getStateMachine().ChangeState(OrangeStalkingState.getInstance());
			ghost.setGlobalStalkState(true);
			return true;
		}
		else return false;
	}
}
