package ghosts;

import java.util.ArrayList;
import java.util.List;

import pacman.Game;
import pacman.GhostPlayer;
import pacman.Move;
import pacman.StateMachine;
import pacman.GameState;
import pacman.Location;
import util.Pair;


public class Orange extends GhostPlayer {
	private StateMachine<Orange> stateMachine;
	private Game game;
	private int index;
	public Move nextMove;
	private GameState gameState;
	private boolean isGlobalStalk = false;
	
	private int stateMoveCounter = 0;
	
	public Orange(){
		stateMachine = new StateMachine<Orange>(this);
		stateMachine.SetCurrentState(OrangeCampingState.getInstance());
		stateMachine.SetGlobalState(OrangeGlobalState.getInstance());
	}
	
	
	
	public StateMachine<Orange> getStateMachine(){
		return stateMachine;
	}
	
	public Game getGame() {
		return game;
	}
	
	public int getIndex() {
		return index;
	}
	
	 public List<Pair<Move, Double>> getMoveDistribution() {
			List<Pair<Move, Double>> distribution = new ArrayList<Pair<Move, Double>>();
			List<Move> legalMoves = Game.getLegalGhostMoves(gameState, index);
			double uniformProb = 1.0 / (double)legalMoves.size();
			for(Move move : legalMoves) {
					distribution.add(new Pair<Move, Double>(move, uniformProb));
			}
			return distribution;
	 }
	
	public void setMove(Move move) {
		nextMove = move;
	}
	
	public void setGlobalStalkState(boolean input) {
		isGlobalStalk = input;
	}
	
	public boolean getGlobalStalkState() {
		return isGlobalStalk;
	}
	
	public void Update(Game game, int index) {
		
		this.game = game;
		gameState = game.getCurrentGameState();
		this.index = index;
		
		stateMachine.Update();
	}
	
	@Override
	public Move chooseMove(Game game, int index) {
		Update(game, index);
		return nextMove;
	}
	
	
	public boolean CheckPacManDistance(int distance) {
		Location newLocation = game.getNextLocation(gameState.getGhostLocations().get(index), nextMove); //Get our Next Location
		Location pacManLocation = gameState.getPacManLocation(); //Get Pac-Mans Location NOW.
		
		if (Location.euclideanDistance(pacManLocation, newLocation) <= distance) {     //The distance returned is basically in squares
			return true;
		}
		else return false;
	}
	
	public boolean CheckPortalDistance(int distance) {
		Location newLocation = game.getNextLocation(gameState.getGhostLocations().get(index), nextMove);
		return Location.checkForPortals(newLocation, distance);
	}
	
	
	public void increaseMoveCounter() {
		stateMoveCounter += 1;
	}
	
	public int getMoveCounter() {
		return stateMoveCounter;
	}
	
	public void resetMoveCounter() {
		stateMoveCounter = 0;
	}
	
}
