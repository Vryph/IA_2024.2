package ghosts;

import pacman.Message;
import pacman.MessageDispatcher;

public class PinkGlobalState implements State<Pink> {
	
	private static PinkGlobalState instance = null;
	private int startTimer = 0;
	
	public static PinkGlobalState getInstance() {
		if(instance == null) {
			instance = new PinkGlobalState();
		}
		return instance;
	}
	
	@Override
	public void enter(Pink ghost) {
	}
	
	@Override
	public void execute(Pink ghost) {
	}
	
	@Override
	public void exit(Pink ghost) {
	}
	
	@Override
	public boolean atMessageReceived(Pink ghost, Message message) {
		if (message.getStringMessage().compareTo("Stalk!") == 0) {
			ghost.getStateMachine().ChangeState(PinkStalkingState.getInstance());
			return true;
		}
		else return false;
	}
}
