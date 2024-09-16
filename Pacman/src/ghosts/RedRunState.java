package ghosts;

import java.util.List;

import pacman.Game;
import pacman.GhostPlayer;
import pacman.Location;
import pacman.Message;
import pacman.Move;
import pacman.GameState;
import util.*;


public class RedRunState implements State<Red> {
	
	private static RedRunState instance = null;
	
	public static RedRunState getInstance() {
		if(instance == null) {
			instance = new RedRunState();
		}
		return instance;
	}
	
  @Override
  public void enter(Red ghost) {
	  System.out.println("Red: Currently Running Away");
	  ghost.resetMoveCounter();
  }
  
  @Override
  public void execute(Red ghost) {
	  
		GameState state = ghost.getGame().getCurrentGameState();
	    List<Move> moves = ghost.getGame().getLegalGhostMoves(ghost.getIndex());
	    if (moves.isEmpty()) ghost.setMove(null);
	    double[] distances = new double[moves.size()];
	    Location pacManLoc = state.getPacManLocation();
	    for (int i=0; i<distances.length; i++) {
	      Location newLoc = Game.getNextLocation(state.getGhostLocations().get(ghost.getIndex()), moves.get(i));
	      distances[i] = Location.euclideanDistance(pacManLoc, newLoc);
	    }
	    int moveIndex = Utils.argmax(distances); // the move that minimizes the distance to PacMan
	    ghost.setMove(moves.get(moveIndex));
	    ghost.increaseMoveCounter();
	    
	    if(ghost.getMoveCounter() >= 100) {
	    	ghost.getStateMachine().ChangeState(RedStalkingState.getInstance());
	    }
	    
  }
  
  @Override
  public void exit(Red ghost) {
	  System.out.println("Red: Exiting Run.");
  }
  
  @Override
  public boolean atMessageReceived(Red ghost, Message message) {
	return false;
  }

}
