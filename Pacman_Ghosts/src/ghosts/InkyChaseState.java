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


public class InkyChaseState implements State<Inky> {
	
	private static InkyChaseState instance = null;
	
	private int _enterTime, _iteration = 0;
	
	private Location _target;

	public static InkyChaseState getInstance() {
		if(instance == null) {
			instance = new InkyChaseState();
		}
		return instance;
	}
	
  @Override
  public void enter(Inky ghost) {
	  System.out.println("Inky: Currently Chasing");
	  
	  _enterTime = ghost.getGame().getTime();
	  _iteration ++;
  }
  
  @Override
  public void execute(Inky ghost) {
	  
		GameState state = ghost.getGame().getCurrentGameState();
	    List<Move> moves = ghost.getGame().getLegalGhostMoves(ghost.getIndex());
	    if (moves.isEmpty()) ghost.setMove(null);
	    
	    double[] distances = new double[moves.size()];
	    
	    Location pacManLoc = state.getPacManLocation();  
	    _target = determineInkyTarget(ghost);
	    
	    System.out.print(_target);
	    System.out.println(" + " + pacManLoc);
	    
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
						ghost.getStateMachine().ChangeState(InkyScatterState.getInstance());
						return;
					}
				}
			default:
				if(_iteration <= 2) {
					if (ghost.getGame().getTime() - _enterTime >= 60) {
						ghost.getStateMachine().ChangeState(InkyScatterState.getInstance());
						return;
					}
				}
		}
 
  }
  
  private Location determineInkyTarget(Inky ghost) {
	  	GameState state = ghost.getGame().getCurrentGameState();
	    Location target = state.getPacManLocation();   
	    Move lastMove = ghost.getGame().getPacManMove();
	    
		if (lastMove != null) {
		    if (lastMove == Move.UP) {
		    	target = new Location(target.getX() - 2, target.getY() + 2); 		
		    }
		    else if(lastMove == Move.DOWN) {
		    	target = new Location(target.getX(), target.getY() - 2); 
		    }
		    else if(lastMove == Move.LEFT) {
		    	target = new Location(target.getX() - 2, target.getY()); 
		    }
		    else if(lastMove == Move.RIGHT) {
		    	target = new Location(target.getX() + 2, target.getY()); 
		    }
		}
		
		Location blinky = ghost.getGameState().getGhostLocations().getFirst();
		Location targetVector = new Location(target.getX() - blinky.getX(), target.getY() - blinky.getY());
		target = new Location(target.getX() + targetVector.getX(), target.getY() + targetVector.getY());
	    return target;
  }
  
  @Override
  public void exit(Inky ghost) {
	  System.out.println("Inky: Exiting Chase State.");
  }
  
  @Override
  public boolean atMessageReceived(Inky ghost, Message message) {
	return false;
  }

}
