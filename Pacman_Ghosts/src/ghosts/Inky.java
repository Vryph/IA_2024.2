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


public class Inky extends GhostPlayer {
	private StateMachine<Inky> stateMachine;
	private Game game;
	private int index;
	public Move nextMove;
	public Move lastMove;
	private GameState gameState;
	
	public Inky(){
		stateMachine = new StateMachine<Inky>(this);
		stateMachine.SetCurrentState(InkyScatterState.getInstance());
	}
	
	
	public StateMachine<Inky> getStateMachine(){
		return stateMachine;
	}
	
	public GameState getGameState() {
		return gameState;
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
	
	
	public boolean CheckDistance(int distance) {
		Location newLocation = game.getNextLocation(gameState.getGhostLocations().get(index), nextMove); //Get our Next Location
		Location pacManLocation = gameState.getPacManLocation(); //Get Pac-Mans Location NOW.
		
		if (Location.euclideanDistance(pacManLocation, newLocation) <= distance) {     //The distance returned is basically in squares
			return true;
		}
		else return false;
	}
	
}
