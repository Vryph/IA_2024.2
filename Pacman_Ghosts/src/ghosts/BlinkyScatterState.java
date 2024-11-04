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


public class BlinkyScatterState implements State<Blinky> {
	
	private static BlinkyScatterState instance = null;
	
	private int _enterTime, _iteration = 1;
	
	private Location _target = new Location(24, 31);
	
	List<Integer> _remainingDotThreshold = Arrays.asList(20, 30, 40, 40, 40, 50, 50, 50, 60, 60, 60, 80, 80, 80, 100, 100, 100, 100);
	
	public static BlinkyScatterState getInstance() {
		if(instance == null) {
			instance = new BlinkyScatterState();
		}
		return instance;
	}
	
  @Override
  public void enter(Blinky ghost) {
	  System.out.println("Blinky: Currently Scattering");
	  
	  _enterTime = ghost.getGame().getTime();
	  _iteration ++;
  }
  
  @Override
  public void execute(Blinky ghost) {
	  
		GameState state = ghost.getGame().getCurrentGameState();
	    List<Move> moves = ghost.getGame().getLegalGhostMoves(ghost.getIndex());
	    if (moves.isEmpty()) ghost.setMove(null);
	    
	    double[] distances = new double[moves.size()];
	    
	    for (int i=0; i<distances.length; i++) {
	      Location newLoc = Game.getNextLocation(state.getGhostLocations().get(ghost.getIndex()), moves.get(i));
	      distances[i] = Location.euclideanDistance(_target, newLoc);
	    }

	    int moveIndex = Utils.argmin(distances); // the move that minimizes the distance to PacMan
	    ghost.setMove(moves.get(moveIndex));
	    
	    
	    if (ghost.getGame().getLevel() <= 18) {
	    	if(ghost.getGameState().getDotLocations().size() < _remainingDotThreshold.get(ghost.getGame().getLevel() - 1)){
	    		ghost.getStateMachine().ChangeState(BlinkyChaseState.getInstance());
	    		return;
	    	}
	    }
	    else {
	    	if(ghost.getGameState().getDotLocations().size() < 120){
	    		ghost.getStateMachine().ChangeState(BlinkyChaseState.getInstance());
	    		return;
	    	}
	    }
	    
		switch(ghost.getGame().getLevel()) {
			case 1: case 2: case 3: case 4:
				switch(_iteration) {
					case 1: case 2:
						if (ghost.getGame().getTime() - _enterTime >= 21) {
							ghost.getStateMachine().ChangeState(BlinkyChaseState.getInstance());
							return;
					}
					case 3: case 4:
						if (ghost.getGame().getTime() - _enterTime >= 15) {
							ghost.getStateMachine().ChangeState(BlinkyChaseState.getInstance());
							return;
					}
				}
			case 5:
				if (ghost.getGame().getTime() - _enterTime >= 15) {
						ghost.getStateMachine().ChangeState(BlinkyChaseState.getInstance());
						return;
				}
			}   
  }
  
  @Override
  public void exit(Blinky ghost) {
	  System.out.println("Blinky: Exiting Scatter State.");
  }
  
  @Override
  public boolean atMessageReceived(Blinky ghost, Message message) {
	return false;
  }

}
