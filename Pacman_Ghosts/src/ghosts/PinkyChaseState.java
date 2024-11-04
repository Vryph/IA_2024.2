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


public class PinkyChaseState implements State<Pinky> {
	
	private static PinkyChaseState instance = null;
	
	private int _enterTime, _iteration = 0;
	
	private Location _target;

	public static PinkyChaseState getInstance() {
		if(instance == null) {
			instance = new PinkyChaseState();
		}
		return instance;
	}
	
  @Override
  public void enter(Pinky ghost) {
	  System.out.println("Pinky: Currently Chasing");
	  
	  _enterTime = ghost.getGame().getTime();
	  _iteration ++;
  }
  
  @Override
  public void execute(Pinky ghost) {
	  
		GameState state = ghost.getGame().getCurrentGameState();
	    List<Move> moves = ghost.getGame().getLegalGhostMoves(ghost.getIndex());
	    if (moves.isEmpty()) ghost.setMove(null);
	    
	    double[] distances = new double[moves.size()];
	    Location pacManLoc = state.getPacManLocation();
	    _target = pacManLoc;
	    
	    Move lastMove = ghost.getGame().getPacManMove();
	    
	    if (lastMove != null) {
	    	if (lastMove == Move.UP) {
	    		_target = new Location(_target.getX() - 4, _target.getY() + 4); 		
	    	}
	    	else if(lastMove == Move.DOWN) {
	    		_target = new Location(_target.getX(), _target.getY() - 4); 
	    	}
	    	else if(lastMove == Move.LEFT) {
	    		_target = new Location(_target.getX() - 4, _target.getY()); 
	    	}
	    	else if(lastMove == Move.RIGHT) {
	    		_target = new Location(_target.getX() + 4, _target.getY()); 
	    	}
	    }

	    for (int i=0; i<distances.length; i++) {
	     	Location newLoc = Game.getNextLocation(state.getGhostLocations().get(ghost.getIndex()), moves.get(i));
	      	distances[i] = Location.euclideanDistance(_target, newLoc);
	    }	    
	    
	    int moveIndex = Utils.argmin(distances); // the move that minimizes the distance to PacMan
	    ghost.setMove(moves.get(moveIndex));
	    
		switch(ghost.getGame().getLevel()) {
			case 1:
				if(_iteration <= 3) {
					if (ghost.getGame().getTime() - _enterTime >= 60) {
						ghost.getStateMachine().ChangeState(PinkyScatterState.getInstance());
						return;
					}
				}
			default:
				if(_iteration <= 2) {
					if (ghost.getGame().getTime() - _enterTime >= 60) {
						ghost.getStateMachine().ChangeState(PinkyScatterState.getInstance());
						return;
					}
				}
		}
 
  }
  
  @Override
  public void exit(Pinky ghost) {
	  System.out.println("Pinky: Exiting Chase State.");
  }
  
  @Override
  public boolean atMessageReceived(Pinky ghost, Message message) {
	return false;
  }

}
