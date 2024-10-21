package player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import util.*;
import pacman.*;

public class VictorSchmaedeckeBuscaAEstrela implements PacManPlayer {

  static Random random = new Random();
  
  private double ghostWeight = 1.2;
  private double remainingDotsWeight = 1;
  private double distanceToDotsWeight = 0.8;
  private double avgDotPositionWeight = 0.4;
  
  private int maxDepth = 2;
  private int greatMoveThreshold = 4;
  private int losingThreshold = -200;
  private double depthMultiplier = 0.2;
  
  private Move lastMove = null;
  Counter<Move> scores = new Counter<Move>(); 
  List<State> possibleStates = new ArrayList<State>();
  List<Double> possibleScores = new ArrayList<Double>();
  List<Integer> depthList = new ArrayList<Integer>();
  List<Integer> exploredStates = new ArrayList<Integer>();

  //This method needs to be public, This is the search method
  public Move chooseMove(Game game) {
	  
    State s = game.getCurrentState();
    scores.clear();
    if(!possibleStates.isEmpty()) {
        possibleStates.clear();
        possibleScores.clear();
        depthList.clear();
        exploredStates.clear();
    }

    
    //Depth search is in another method as to enable recursion
    aStarSearch(0, s, null, 0, 0);
    
    //Picks the best option every time
    scores = scores.exp().normalize(); 
    Move move = scores.argmax();
    
    lastMove = move; 
    return move;
    }
    

  public void aStarSearch(int depth, State state, Move rootMove, double accumulatedScore, double bestScore) {
	  if(scores.max() < greatMoveThreshold) {
		  List<Move> legalMoves = Game.getLegalPacManMoves(state);
		 
		  for (Move m : legalMoves) {
			  List<State> intersectionList = Game.getProjectedStates(state, m);
			  State last = intersectionList.get(intersectionList.size() - 1);
			  
			  
			  if(depth == 0) 
				  rootMove = m;
			  
			  double stateScore = evaluateState(state, last);
			  double turnaroundPenalty = (lastMove == rootMove.getOpposite() ? -10.0 : 0.0);
			  stateScore += turnaroundPenalty + (accumulatedScore * (depth * depthMultiplier));
			  
			  
			  possibleStates.add(last);
			  possibleScores.add(stateScore);
			  depthList.add(depth);
			  
			  
			  if (scores.getCount(rootMove) > losingThreshold)
				  scores.incrementCount(rootMove, stateScore);}
	  }
	  
	  double prevBestScore = getBestScore();
	  if (depth + 1 < maxDepth && bestScore <= prevBestScore ) {
		  if (multipleBestScores()) {
			  int index = getBestIndex();
			  while (exploredStates.contains(index)) {
				  int counter = 1;
				  index = getBestScoreByIndex(counter);
			  }
			  if (index != 200) {
				  aStarSearch(depthList.get(index) + 1, possibleStates.get(index), rootMove, accumulatedScore, prevBestScore);
			  }
		  }
		  else
		  {
			  int index = getBestIndex();
			  exploredStates.add(index);
			  aStarSearch(depthList.get(index) + 1, possibleStates.get(index), rootMove, accumulatedScore, prevBestScore);
		  }
	  }
  }
  
  private int getBestScoreByIndex(int index){
	  double bestScore = Double.NEGATIVE_INFINITY;
	  List<Integer> indexList = new ArrayList<Integer>();
	  for (double m : possibleScores) {
		  if (m > bestScore)
			  bestScore = m;
		  		indexList.clear();
		  		indexList.add(possibleScores.indexOf(m));
		  	if(m == bestScore) {
		  		indexList.add(possibleScores.indexOf(m));
		  	}
		  
	  }
	  if(indexList.size() > index)
		  return indexList.get(index);
	  else
		  return 200;
  } 
  
  private boolean multipleBestScores(){
	  double bestScore = Double.NEGATIVE_INFINITY;
	  List<Double> scoreList = new ArrayList<Double>();
	  for (double m : possibleScores) {
		  if (m > bestScore)
			  bestScore = m;
		  		scoreList.clear();
		  		scoreList.add(m);
		  	if(m == bestScore) {
		  		scoreList.add(m);
		  	}
		  
	  }
	  if (scoreList.size() > 1) 
		  return true;
	  else
		  return false;
  } 
  
  private double getBestScore(){
	  double bestScore = Double.NEGATIVE_INFINITY;
	  for (double m : possibleScores) {
		  if (m > bestScore)
			  bestScore = m;
	  }
	  return bestScore;
  } 
  private int getBestIndex(){
	  double bestScore = Double.NEGATIVE_INFINITY;
	  int index = 0;
	  for (double m : possibleScores) {
		  if (m > bestScore)
			  bestScore = m;
		  	  index = possibleScores.indexOf(m);
		  	
	  }
	  return index;
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
    score -= getAvgDistance(s.getDotLocations()) * avgDotPositionWeight;
    
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
  
  private double getAvgDistance(Collection<Location> targetLocations) {
	    double sum = 0;
	    double count = 0;
	    for (Location dotLocation : targetLocations) {
	    	for ( Location otherDotLocation : targetLocations) {	
	    		if (otherDotLocation != dotLocation) {
	    			double distance = Location.manhattanDistance(otherDotLocation, dotLocation); 
	    			sum += distance;
	    			count++;
	    		}
	    	}
	    }
	    return sum/count;
  		}
}
