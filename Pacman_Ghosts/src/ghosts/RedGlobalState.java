package ghosts;

import pacman.Message;
import pacman.MessageDispatcher;

public class RedGlobalState implements State<Red> {
	
	private static RedGlobalState instance = null;
	
	public static RedGlobalState getInstance() {
		if(instance == null) {
			instance = new RedGlobalState();
		}
		return instance;
	}
	
	@Override
	public void enter(Red ghost) {
	}
	
	@Override
	public void execute(Red ghost) {
	}
	
	@Override
	public void exit(Red ghost) {
	}
	
	@Override
	public boolean atMessageReceived(Red ghost, Message message) {
		if (message.getStringMessage().compareTo("Stalk!") == 0) {
			ghost.getStateMachine().ChangeState(RedStalkingState.getInstance());
			return true;
		}
		else return false;
	}
}
