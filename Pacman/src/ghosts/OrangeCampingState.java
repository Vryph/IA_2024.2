package ghosts;

import java.util.List;

import pacman.Game;
import pacman.GhostPlayer;
import pacman.Location;
import pacman.Message;
import pacman.Move;
import pacman.GameState;
import util.*;


public class OrangeCampingState implements State<Orange> {
	
	private static OrangeCampingState instance = null;
	
	public static OrangeCampingState getInstance() {
		if(instance == null) {
			instance = new OrangeCampingState();
		}
		return instance;
	}
	
  @Override
  public void enter(Orange ghost) {
	  System.out.println("Orange: Currently Camping");
	  ghost.resetMoveCounter();
  }
  
  @Override
  public void execute(Orange ghost) {
	  
		GameState state = ghost.getGame().getCurrentGameState();
	    List<Move> moves = ghost.getGame().getLegalGhostMoves(ghost.getIndex());
	    if (moves.isEmpty()) ghost.setMove(null);
	    double[] distances1 = new double[moves.size()];
	    double[] distances2 = new double[moves.size()];
	    Location portalLoc1 = new Location(3, 15);
	    Location portalLoc2 = new Location(22, 15);//25, 15  0, 15
	    for (int i=0; i<distances1.length; i++) {
	      Location newLoc = Game.getNextLocation(state.getGhostLocations().get(ghost.getIndex()), moves.get(i));
	      distances1[i] = Location.euclideanDistance(portalLoc1, newLoc);
	      distances2[i] = Location.euclideanDistance(portalLoc2, newLoc);
	    }
	    int moveIndex1 = Utils.argmin(distances1); // the move that minimizes the distance to PacMan
	    int moveIndex2 = Utils.argmin(distances2); // the move that minimizes the distance to PacMan
	    if( distances1[moveIndex1] > distances2[moveIndex2]) ghost.setMove(moves.get(moveIndex2));
	    else ghost.setMove(moves.get(moveIndex1));
	    ghost.increaseMoveCounter();
	    
	    if(ghost.CheckPacManDistance(8) && ghost.getMoveCounter() > 20) {
	    	ghost.getStateMachine().ChangeState(OrangeStalkingState.getInstance());
	    }
	    else if (ghost.getGame().getPoints() >= 1800) {
	    	ghost.getStateMachine().ChangeState(OrangeStalkingState.getInstance());
	    }
	    	
	    
  }
  
  @Override
  public void exit(Orange ghost) {
	  System.out.println("Orange: Exiting Camp.");
  }
  
  @Override
  public boolean atMessageReceived(Orange ghost, Message message) {
	return false;
  }

}
