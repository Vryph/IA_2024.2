package ghosts;

import java.util.List;
import java.util.Random;

import pacman.Move;
import pacman.Message;
import pacman.GhostPlayer;
import util.*;


public class PinkRandomState implements State<Pink> {
	
	private static PinkRandomState instance = null;
	private static Random random;
	
	public static PinkRandomState getInstance() {
		if(instance == null) {
			instance = new PinkRandomState();
			random = new Random();
		}
		return instance;
	}
	
  @Override
  public void enter(Pink ghost) {
	  System.out.println("Pink: Currently Random");
	  ghost.resetMoveCounter();
  }
  
  @Override
  public void execute(Pink ghost) {
	  
	  Move sendMove = null;
	  List<Pair<Move, Double>> distribution = ghost.getMoveDistribution();
		double dart = random.nextDouble();
		double sum = 0.0;
		try {
	    for(Pair<Move, Double> pair : distribution) {
	    		sum += pair.second();
	    		if(sum >= dart) {
	    			sendMove = pair.first();
	    			if (sendMove != null) break;
	    		}
	    }
	    throw new RuntimeException("No move selected in" + this.getClass().getName() + " for 'dart'=" + dart);
		} catch(RuntimeException re) {}
		ghost.setMove(sendMove);
		ghost.increaseMoveCounter();
		
		if(ghost.CheckDistance(5) && ghost.getMoveCounter() >= 10) {
			ghost.getStateMachine().ChangeState(PinkStalkingState.getInstance());
		}
	
	

  }
  
  @Override
  public void exit(Pink ghost) {
	  System.out.println("Pink: Exiting Random.");
  }
  
  @Override
  public boolean atMessageReceived(Pink ghost, Message message) {
	return false;
  }
}
