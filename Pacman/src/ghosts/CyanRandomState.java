package ghosts;

import java.util.List;
import java.util.Random;

import pacman.Move;
import pacman.Message;
import pacman.GhostPlayer;
import util.*;


public class CyanRandomState implements State<Cyan> {
	
	private static CyanRandomState instance = null;
	private static Random random;
	
	public static CyanRandomState getInstance() {
		if(instance == null) {
			instance = new CyanRandomState();
			random = new Random();
		}
		return instance;
	}
	
  @Override
  public void enter(Cyan ghost) {
	  System.out.println("Cyan: Currently Random");
  }
  
  @Override
  public void execute(Cyan ghost) {
	  
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
		
		if (!ghost.CheckDistance(25)) {
			ghost.getStateMachine().ChangeState(CyanFarStalkState.getInstance());
		}

  }
  
  @Override
  public void exit(Cyan ghost) {
	  System.out.println("Cyan: Exiting Random.");
  }
  
  @Override
  public boolean atMessageReceived(Cyan ghost, Message message) {
	return false;
  }
}
