package ghosts;

import java.util.List;

import pacman.Game;
import pacman.GhostPlayer;
import pacman.Location;
import pacman.Message;
import pacman.Move;
import pacman.GameState;
import util.*;


public class OrangeStalkingState implements State<Orange> {
	
	private static OrangeStalkingState instance = null;
	private int startTimer;
	
	public static OrangeStalkingState getInstance() {
		if(instance == null) {
			instance = new OrangeStalkingState();
		}
		return instance;
	}
	
  @Override
  public void enter(Orange ghost) {
	  System.out.println("Orange: Currently Stalking");
	  startTimer = ghost.getGame().getTime();
	  ghost.resetMoveCounter();
  }
  
  @Override
  public void execute(Orange ghost) {
	  
		GameState state = ghost.getGame().getCurrentGameState();
	    List<Move> moves = ghost.getGame().getLegalGhostMoves(ghost.getIndex());
	    if (moves.isEmpty()) ghost.setMove(null);
	    double[] distances = new double[moves.size()];
	    Location pacManLoc = state.getPacManLocation();
	    for (int i=0; i<distances.length; i++) {
	      Location newLoc = Game.getNextLocation(state.getGhostLocations().get(ghost.getIndex()), moves.get(i));
	      distances[i] = Location.euclideanDistance(pacManLoc, newLoc);
	    }
	    int moveIndex = Utils.argmin(distances); // the move that minimizes the distance to PacMan
	    ghost.setMove(moves.get(moveIndex));
	    ghost.increaseMoveCounter();
	    
	    if (ghost.getGame().getPoints() < 1800 && ghost.getMoveCounter() >= 20) {
		    if(ghost.CheckPortalDistance(moveIndex) && !ghost.getGlobalStalkState()) {
		    	ghost.getStateMachine().ChangeState(OrangeCampingState.getInstance());
		    }
		    else if(ghost.getGlobalStalkState() && ghost.getGame().getTime() - startTimer >= 60) {
		    	ghost.getStateMachine().ChangeState(OrangeCampingState.getInstance());
		    }
	    }
	    
  }
  
  @Override
  public void exit(Orange ghost) {
	  System.out.println("Orange: Exiting Stalk.");
  }
  
  @Override
  public boolean atMessageReceived(Orange ghost, Message message) {
	return false;
  }

}
