package ghosts;

import java.util.List;
import java.util.Random;

import pacman.Move;
import pacman.Message;
import pacman.GhostPlayer;
import util.*;


public class RedRandomState implements State<Red> {
	
	private static RedRandomState instance = null;
	private static Random random;
	
	public static RedRandomState getInstance() {
		if(instance == null) {
			instance = new RedRandomState();
			random = new Random();
		}
		return instance;
	}
	
  @Override
  public void enter(Red ghost) {
	  System.out.println("Red: Currently Random");
  }
  
  @Override
  public void execute(Red ghost) {
	  
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
		
		if(ghost.getMoveCounter() >= 400) {
			ghost.getStateMachine().ChangeState(RedRunState.getInstance());
		}
	
	

  }
  
  @Override
  public void exit(Red ghost) {
	  System.out.println("Red: Exiting Random.");
  }
  
  @Override
  public boolean atMessageReceived(Red ghost, Message message) {
	return false;
  }
}
