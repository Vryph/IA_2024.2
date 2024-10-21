package ghosts;

import pacman.Message;
import pacman.MessageDispatcher;

public class CyanGlobalState implements State<Cyan> {
	
	private static CyanGlobalState instance = null;
	private int startTimer = 0;
	
	public static CyanGlobalState getInstance() {
		if(instance == null) {
			instance = new CyanGlobalState();
		}
		return instance;
	}
	
	@Override
	public void enter(Cyan ghost) {
	}
	
	@Override
	public void execute(Cyan ghost) {
		if(ghost.CheckPosition() && ghost.getGame().getTime() - startTimer > 10) {
			MessageDispatcher.getInstance().dispatchMessage(ghost, ghost.getGame().getGhostPlayers().get(0), "Stalk!", null);
			MessageDispatcher.getInstance().dispatchMessage(ghost, ghost.getGame().getGhostPlayers().get(1), "Stalk!", null);
			MessageDispatcher.getInstance().dispatchMessage(ghost, ghost.getGame().getGhostPlayers().get(3), "Stalk!", null);
			startTimer = ghost.getGame().getTime();
			ghost.getStateMachine().ChangeState(CyanStalkingState.getInstance());
		}
	}
	
	@Override
	public void exit(Cyan ghost) {

	}
	
	@Override
	public boolean atMessageReceived(Cyan ghost, Message message) {
		return false;
	}
}
