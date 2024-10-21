package player;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import util.*;
import pacman.*;

public class VictorSchmaedeckeBuscaProfundidade implements PacManPlayer {

  static Random random = new Random();
  
  private double ghostWeight = 1.2;
  private double remainingDotsWeight = 1;
  private double distanceToDotsWeight = 0.8;
  private double avgGhostDistanceWeight = 0.8;
  
  private int maxDepth = 1;
  private int greatMoveThreshold = 4;
  private int losingThreshold = -1000;
  
  
  
  private Move lastMove = null;
  Counter<Move> scores = new Counter<Move>(); 

  //This method needs to be public, This is the search method
  public Move chooseMove(Game game) {
	  
    State s = game.getCurrentState();
    scores.clear();
    
    //Depth search is in another method as to enable recursion
    depthSearch(0, s, null);
    
    //Picks the best option every time
    scores = scores.exp().normalize(); 
    //Move move = scores.argmax();
    Move move = scores.sampleFromDistribution(random);  // Adds randomness to the decisions, also works to prevent loops
    
    lastMove = move; 
    return move;
    }
    

  public void depthSearch(int depth, State state, Move firstMove) {
	  if(scores.max() < greatMoveThreshold) {
		  List<Move> legalMoves = Game.getLegalPacManMoves(state);
		 
		  for (Move m : legalMoves) {
			  List<State> intersectionList = Game.getProjectedStates(state, m);
			  State last = intersectionList.get(intersectionList.size() - 1);
			  if(depth == 0) 
				  firstMove = m;
			  if(depth + 1 < maxDepth) {
				  depthSearch(depth + 1, last, firstMove);
			  }
			  
			  double stateScore = evaluateState(state, last);
			  double turnaroundPenalty = (lastMove == firstMove.getOpposite() ? -10.0 : 0.0); //Turnaround Penalty works to avoid loops
			  stateScore += turnaroundPenalty;
			  //Improves the previous score for the root move if it won't make you loose
			  if (scores.getCount(firstMove) > losingThreshold) {
				  scores.improveCount(firstMove, stateScore);}
		  }
	  }
  }

  public double evaluateState(State s, State next) {
	  if (Game.isLosing(next))
      return -1000.0; 
    if (Game.isWinning(next))
      return 0.0; 
    double score = 0.0;
    
    Location pacManLocation = next.getPacManLocation();
    score -= s.getDotLocations().size() * remainingDotsWeight;
    score -= getMinDistance(pacManLocation, s.getDotLocations()) * distanceToDotsWeight;
    //score += getMinDistance(pacManLocation, s.getGhostLocations()) * ghostWeight; 
    score += getAvgDistance(pacManLocation, s.getGhostLocations()) * avgGhostDistanceWeight;
    
    return score;
  }

  private double getMinDistance(Location sourceLocation, Collection<Location> targetLocations) {
    double minDistance = Double.POSITIVE_INFINITY;
    for (Location dotLocation : targetLocations) {
      double distance = Location.manhattanDistance(sourceLocation, dotLocation); // one
                                                                                  // way
                                                                                  // to
                                                                                  // measure
                                                                                  // distance
      if (distance < minDistance) {
        minDistance = distance;
      }
    }
    if (Double.isInfinite(minDistance))
      throw new RuntimeException();
    return minDistance;
  }
  
  private double getAvgDistance(Location sourceLocation, Collection<Location> targetLocations) {
	    double sum = 0;
	    double counter = 0;
	    for (Location dotLocation : targetLocations) {
	      double distance = Location.manhattanDistance(sourceLocation, dotLocation); 
	      sum += distance;
	      counter++;
	    }
	    return sum / counter;
	  }
}
