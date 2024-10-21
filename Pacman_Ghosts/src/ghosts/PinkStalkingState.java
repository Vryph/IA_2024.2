package ghosts;

import java.util.List;

import pacman.Game;
import pacman.GhostPlayer;
import pacman.Location;
import pacman.Message;
import pacman.Move;
import pacman.GameState;
import util.*;


public class PinkStalkingState implements State<Pink> {
	
	private static PinkStalkingState instance = null;
	
	public static PinkStalkingState getInstance() {
		if(instance == null) {
			instance = new PinkStalkingState();
		}
		return instance;
	}
	
  @Override
  public void enter(Pink ghost) {
	  System.out.println("Pink: Currently Stalking");
	  ghost.resetMoveCounter();
  }
  
  @Override
  public void execute(Pink ghost) {
	  
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
	    
	    if(ghost.CheckRandom(5) && ghost.getMoveCounter() >= 5) {
	    	ghost.getStateMachine().ChangeState(PinkRandomState.getInstance());
	    }
	    
  }
  
  @Override
  public void exit(Pink ghost) {
	  System.out.println("Pink: Exiting Stalk.");
  }
  
  @Override
  public boolean atMessageReceived(Pink ghost, Message message) {
	return false;
  }

}
