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


public class PinkyScatterState implements State<Pinky> {
	
	private static PinkyScatterState instance = null;
	
	private int _enterTime, _iteration = 1;
	
	private Location _target = new Location(2, 31);
	
	public static PinkyScatterState getInstance() {
		if(instance == null) {
			instance = new PinkyScatterState();
		}
		return instance;
	}
	
  @Override
  public void enter(Pinky ghost) {
	  System.out.println("Pinky: Currently Scattering");
	  
	  _enterTime = ghost.getGame().getTime();
	  _iteration ++;
  }
  
  @Override
  public void execute(Pinky ghost) {
	  
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
	    
	   
	    
		switch(ghost.getGame().getLevel()) {
			case 1: case 2: case 3: case 4:
				switch(_iteration) {
					case 1: case 2:
						if (ghost.getGame().getTime() - _enterTime >= 21) {
							ghost.getStateMachine().ChangeState(PinkyChaseState.getInstance());
							return;
					}
					case 3: case 4:
						if (ghost.getGame().getTime() - _enterTime >= 15) {
							ghost.getStateMachine().ChangeState(PinkyChaseState.getInstance());
							return;
					}
				}
			case 5:
				if (ghost.getGame().getTime() - _enterTime >= 15) {
						ghost.getStateMachine().ChangeState(PinkyChaseState.getInstance());
						return;
				}
			}
  }
  
  @Override
  public void exit(Pinky ghost) {
	  System.out.println("Pinky: Exiting Scatter State.");
  }
  
  @Override
  public boolean atMessageReceived(Pinky ghost, Message message) {
	return false;
  }

}
