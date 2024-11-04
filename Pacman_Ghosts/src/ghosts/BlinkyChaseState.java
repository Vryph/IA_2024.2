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


public class BlinkyChaseState implements State<Blinky> {
	
	private static BlinkyChaseState instance = null;
	
	private int _enterTime, _iteration = 0;
	
	List<Integer> _remainingDotThreshold = Arrays.asList(20, 30, 40, 50, 60, 80, 100, 120);
	
	public static BlinkyChaseState getInstance() {
		if(instance == null) {
			instance = new BlinkyChaseState();
		}
		return instance;
	}
	
  @Override
  public void enter(Blinky ghost) {
	  System.out.println("Blinky: Currently Chasing");
	  
	  _enterTime = ghost.getGame().getTime();
	  _iteration ++;
  }
  
  @Override
  public void execute(Blinky ghost) {
	  
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
	    
		switch(ghost.getGame().getLevel()) {
			case 1:
				if (ghost.getGameState().getDotLocations().size() > _remainingDotThreshold.get(0) ) {
					if(_iteration <= 3) {
						if (ghost.getGame().getTime() - _enterTime >= 60) {
							ghost.getStateMachine().ChangeState(BlinkyScatterState.getInstance());
							return;
						}
					}
				}
			case 2:
				if (ghost.getGameState().getDotLocations().size() > _remainingDotThreshold.get(1) ) {
					if(_iteration <= 2) {
						if (ghost.getGame().getTime() - _enterTime >= 60) {
							ghost.getStateMachine().ChangeState(BlinkyScatterState.getInstance());
							return;
						}
					}
				}
			case 3: case 4: case 5:
				if (ghost.getGameState().getDotLocations().size() > _remainingDotThreshold.get(2) ) {
					if(_iteration <= 2) {
						if (ghost.getGame().getTime() - _enterTime >= 60) {
							ghost.getStateMachine().ChangeState(BlinkyScatterState.getInstance());
							return;
						}
					}
				}
			case 6: case 7: case 8:
					if (ghost.getGameState().getDotLocations().size() > _remainingDotThreshold.get(3) ) {
						if(_iteration <= 2) {
							if (ghost.getGame().getTime() - _enterTime >= 60) {
								ghost.getStateMachine().ChangeState(BlinkyScatterState.getInstance());
								return;
							}
						}
					}
			case 9: case 10: case 11:
				if (ghost.getGameState().getDotLocations().size() > _remainingDotThreshold.get(4) ) {
					if(_iteration <= 2) {
						if (ghost.getGame().getTime() - _enterTime >= 60) {
							ghost.getStateMachine().ChangeState(BlinkyScatterState.getInstance());
							return;	
						}
					}
				}
			case 12: case 13: case 14:
				if (ghost.getGameState().getDotLocations().size() > _remainingDotThreshold.get(5) ) {
					if(_iteration <= 2) {
						if (ghost.getGame().getTime() - _enterTime >= 60) {
							ghost.getStateMachine().ChangeState(BlinkyScatterState.getInstance());
							return;
						}
					}
				}
			case 15: case 16: case 17: case 18:
				if (ghost.getGameState().getDotLocations().size() > _remainingDotThreshold.get(6) ) {
					if(_iteration <= 2) {
						if (ghost.getGame().getTime() - _enterTime >= 60) {
							ghost.getStateMachine().ChangeState(BlinkyScatterState.getInstance());
							return;
						}
					}
				}
			default:
				if (ghost.getGameState().getDotLocations().size() > _remainingDotThreshold.get(7) ) {
					if(_iteration <= 2) {
						if (ghost.getGame().getTime() - _enterTime >= 60) {
							ghost.getStateMachine().ChangeState(BlinkyScatterState.getInstance());
							return;
						}
					}
				}
		}
		
		
	    
  }
  
  @Override
  public void exit(Blinky ghost) {
	  System.out.println("Blinky: Exiting Chase State.");
  }
  
  @Override
  public boolean atMessageReceived(Blinky ghost, Message message) {
	return false;
  }

}
