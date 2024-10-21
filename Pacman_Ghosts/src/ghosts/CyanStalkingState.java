package ghosts;

import java.util.List;

import pacman.Game;
import pacman.GhostPlayer;
import pacman.Location;
import pacman.Message;
import pacman.Move;
import pacman.GameState;
import util.*;


public class CyanStalkingState implements State<Cyan> {
	
	private static CyanStalkingState instance = null;
	private int startTimer;
	
	public static CyanStalkingState getInstance() {
		if(instance == null) {
			instance = new CyanStalkingState();
		}
		return instance;
	}
	
  @Override
  public void enter(Cyan ghost) {
	  System.out.println("Cyan: Currently Stalking");
	  startTimer = ghost.getGame().getTime();
  }
  
  @Override
  public void execute(Cyan ghost) {
	  
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
	    
	    if(ghost.getGame().getTime() - startTimer > 50) {
	    	ghost.getStateMachine().ChangeState(CyanRandomState.getInstance());
	    }
  }
  
  @Override
  public void exit(Cyan ghost) {
	  System.out.println("Cyan: Exiting Stalk.");
  }
  
  @Override
  public boolean atMessageReceived(Cyan ghost, Message message) {
	return false;
  }

}
