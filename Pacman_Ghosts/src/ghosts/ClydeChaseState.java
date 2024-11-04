package ghosts;

import java.util.Arrays;
import java.util.List;

import pacman.Game;
import pacman.GhostPlayer;
import pacman.Location;
import pacman.Message;
import pacman.Move;
import pacman.GameState;
import util.*;


public class ClydeChaseState implements State<Clyde> {
	
	private static ClydeChaseState instance = null;
	
	private int _enterTime, _iteration = 0, _radius = 8;
	
	private Location _target = new Location(0, -1);

	public static ClydeChaseState getInstance() {
		if(instance == null) {
			instance = new ClydeChaseState();
		}
		return instance;
	}
	
  @Override
  public void enter(Clyde ghost) {
	  System.out.println("Clyde: Currently Chasing");
	  
	  _enterTime = ghost.getGame().getTime();
	  _iteration ++;
  }
  
  @Override
  public void execute(Clyde ghost) {
	  
		GameState state = ghost.getGame().getCurrentGameState();
	    List<Move> moves = ghost.getGame().getLegalGhostMoves(ghost.getIndex());
	    if (moves.isEmpty()) ghost.setMove(null);
	    
	    double[] distances = new double[moves.size()];
	    Location pacManLoc = state.getPacManLocation();
	    
	    if(ghost.CheckDistance(_radius)) {
	    	for (int i=0; i<distances.length; i++) {
	     		Location newLoc = Game.getNextLocation(state.getGhostLocations().get(ghost.getIndex()), moves.get(i));
	      		distances[i] = Location.euclideanDistance(_target, newLoc);
	    	}
	    }
	    else {
	    	for (int i=0; i<distances.length; i++) {
	     		Location newLoc = Game.getNextLocation(state.getGhostLocations().get(ghost.getIndex()), moves.get(i));
	      		distances[i] = Location.euclideanDistance(pacManLoc, newLoc);
	    	}
	    }
	    
	    
	    int moveIndex = Utils.argmin(distances); // the move that minimizes the distance to PacMan
	    ghost.setMove(moves.get(moveIndex));
	    
		switch(ghost.getGame().getLevel()) {
			case 1:
				if(_iteration <= 3) {
					if (ghost.getGame().getTime() - _enterTime >= 60) {
						ghost.getStateMachine().ChangeState(ClydeScatterState.getInstance());
						return;
					}
				}
			default:
				if(_iteration <= 2) {
					if (ghost.getGame().getTime() - _enterTime >= 60) {
						ghost.getStateMachine().ChangeState(ClydeScatterState.getInstance());
						return;
					}
				}
		}
 
  }
  
  @Override
  public void exit(Clyde ghost) {
	  System.out.println("Clyde: Exiting Chase State.");
  }
  
  @Override
  public boolean atMessageReceived(Clyde ghost, Message message) {
	return false;
  }

}
