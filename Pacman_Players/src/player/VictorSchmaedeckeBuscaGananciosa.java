package player;

import java.util.Collection;
import java.util.List;
import java.util.Random;

import util.*;
import pacman.*;

public class VictorSchmaedeckeBuscaGananciosa implements PacManPlayer {

  static Random random = new Random();
  
  private double ghostWeight = 1.2;
  private double remainingDotsWeight = 1;
  private double distanceToDotsWeight = 0.8;
  
  private Move lastMove = null;

  //This method needs to be public, This is the search method
  public Move chooseMove(Game game) {
	  
    State s = game.getCurrentState();
    List<Move> legalMoves = game.getLegalPacManMoves(); 
    Counter<Move> scores = new Counter<Move>();

    //Glutton Search doesn't need to see ahead as it'll always pick the best option right now
    for (Move m : legalMoves) {
    	
        List<State> stateList = Game.getProjectedStates(s, m); 
        State last = stateList.get(stateList.size() - 1);
        double stateScore = evaluateState(s, last);
        double turnaroundPenalty = (lastMove == m.getOpposite() ? -10.0 : 0.0);
        scores.setCount(m, stateScore + turnaroundPenalty); 
        
      }
    
    scores = scores.exp().normalize(); 
    Move move = scores.argmax();
    
    lastMove = move; 
    return move;
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
    score += getMinDistance(pacManLocation, s.getGhostLocations()) * ghostWeight; 
    
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

}
