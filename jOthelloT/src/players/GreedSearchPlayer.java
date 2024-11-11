package players;

import java.util.ArrayList;
import java.util.List;

import game.BoardSquare;
import game.Move;
import game.OthelloGame;

public class GreedSearchPlayer extends AbstractPlayer {

	int[][] directions = {
		    {-1, -1}, {-1, 0}, {-1, 1}, 
		    {0, -1},          {0, 1},   
		    {1, -1}, {1, 0}, {1, 1}     
		};
	
	double scoreWeight = 0.9;
	double mobilityWeight = 1.1;
	double frontierWeight = 1;
	double leavingOpponentStuckValue = 20;
	double cornerValue = 30;
	double enablingCornerPenalty = 15;
	OthelloGame game = new OthelloGame();
	
	boolean useSpecialistStrategies;
	
	List<Move> validMoves = new ArrayList<Move>();
	
	public GreedSearchPlayer(boolean b) {
		useSpecialistStrategies = b;
	}

	public BoardSquare play(int[][] board) {
		validMoves.clear();
		validMoves = game.getValidMoves(board, getMyBoardMark());
		List<Double> scores = new ArrayList<Double>();
		
		//Checks for a win, removes any draw or lost moves if possible
		BoardSquare winningMove = checkEndGameConditions();
		if(winningMove != null)
			return winningMove;
		
		if(useSpecialistStrategies) {
			//Applies all of the Specialist Strategies
	    	BoardSquare specialistStrategiesMove = applySpecialistStrategies();
	    	if (specialistStrategiesMove != null)
	    		return specialistStrategiesMove;
		}
		
		for(Move m : validMoves) {
			//Finally does the heuristics if the specialist strategies didn't apply
			double moveScore = EvaluateScore(m);
			scores.add(moveScore);
		}
		
		System.out.println(validMoves.get(maxArgIndex(scores)).getBoardSquare().toString());
		return validMoves.get(maxArgIndex(scores)).getBoardSquare();
	}
	
	public double EvaluateScore(Move move) {
		//Heuristics
		int[][] previewBoard = move.getBoard();
		double score = 0;
		score += scoreDifference(previewBoard, move) * scoreWeight;
		score += mobilityDifference(previewBoard, move) * mobilityWeight;
		score -= frontierPieceDifference(previewBoard, move) * frontierWeight; // The less frontier pieces you have the better
		if(enablesCornerForOpponent(move))
			score -= enablingCornerPenalty;
		if(isCorner(move.getBoardSquare()))
			score += cornerValue;
		
		return score;
	}
	
	public BoardSquare applySpecialistStrategies() {
		List<Move> cornerMoves = new ArrayList<Move>();
		List<Move> leavesOpponentStuck = new ArrayList<Move>();
		List<Double> scores = new ArrayList<Double>();
		
		//Checks for Specialist Moves
		for(Move m: validMoves) {
			BoardSquare square = m.getBoardSquare();
			if(isCorner(square)) 
				cornerMoves.add(m);
			if(opponentHasNoMoves(m)) 
				leavesOpponentStuck.add(m);		
		}
		
		//This strategy makes it so the AI HARD prioritizes corners over anything else
		if(cornerMoves.size() > 1) {
			for(Move c : cornerMoves) {
				double moveScore = EvaluateScore(c);
				scores.add(moveScore);
				
			}
			return cornerMoves.get(maxArgIndex(scores)).getBoardSquare();
		}
		else if(!cornerMoves.isEmpty()) {
			return cornerMoves.getFirst().getBoardSquare();
		}
		
		//This Strategy makes it so the Player HARD prioritizes making it so the enemy has no moves.
		if(leavesOpponentStuck.size() > 1) {
			for(Move l : leavesOpponentStuck) {
				double moveScore = EvaluateScore(l);
				scores.add(moveScore);
				
			}
			return leavesOpponentStuck.get(maxArgIndex(scores)).getBoardSquare();
		}
		else if(!leavesOpponentStuck.isEmpty()) {
			return leavesOpponentStuck.getFirst().getBoardSquare();
		}
		
		
		//Ignores any move that would enable the opponent to take the corner if there are other possible moves
		List<Move> movesToRemove = new ArrayList<Move>();
		
		for(Move n: validMoves) {
			if(enablesCornerForOpponent(n))
				if(validMoves.size() > movesToRemove.size() + 1)
					movesToRemove.add(n);
		}
		validMoves.removeAll(movesToRemove);
	
		return null;
		
	}
	
	public BoardSquare checkEndGameConditions() {
		List<Move> movesToRemove = new ArrayList<Move>();
		for(Move m : validMoves) {
			int[][] newBoard = m.getBoard();
			int myResult = game.testing_end_game(newBoard, getMyBoardMark());
			int opponentResult = 0;
			if(myResult == 0) {
			opponentResult = game.testing_end_game(newBoard, getOpponentBoardMark());}
			
			if(myResult == 1)
				return m.getBoardSquare();
			
			
			if(myResult == 2 || opponentResult == 1) {
				if(validMoves.size() > movesToRemove.size() + 1)
					movesToRemove.add(m);
			}		
		}
		validMoves.removeAll(movesToRemove);
		return null;
	}
	
	public double scoreDifference(int[][] board, Move move) {
		double gameScore = 0;
		for(int[] j: board) {
			for(int i : j) {
				gameScore += i;
			}
		}
		return gameScore;  //Returns your score - opponents Score
	}
	
	public double mobilityDifference(int[][] board, Move move) {
		int myMoves = game.getValidMoves(board, getMyBoardMark()).size();
		int opponentMoves = game.getValidMoves(board, getOpponentBoardMark()).size();
		double mobilityScore = myMoves - opponentMoves;
		if(opponentMoves <= 0)   //First Specialist Rule? If the opponent has no moves prioritize this move.
			mobilityScore += leavingOpponentStuckValue;
		return mobilityScore; //Returns the difference in number of available moves for you and the opponent	
	}
	
	public double frontierPieceDifference(int[][] board, Move move) {
		double myFrontierPieces = 0;
		double opponentFrontierPieces = 0;
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[0].length; col++) {
				if(board[row][col] != 0) {
					if(isFrontierPiece(board, row, col)) {
						if (board[row][col] == 1)
							myFrontierPieces++;
						else
							opponentFrontierPieces++;
					}
				}
			}
		}
		return myFrontierPieces - opponentFrontierPieces; // Returns the difference in frontier pieces
	}
	
	public boolean isFrontierPiece(int[][] board, int row, int column){
		for (int[] direction : directions) {
			int newRow = row + direction[0];
			int newColumn = column + direction[1] ;
			
			if(isWithinBounds(board, newRow, newColumn) && board[newRow][newColumn] == 0) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isWithinBounds(int[][] board, int row, int column) {
		return row >= 0 && row < board.length && column >= 0 && column < board[0].length;
	}
	
	public boolean isCorner(BoardSquare square) {
		return (square.getCol() == 0 || square.getCol() == 7) && (square.getRow() == 0 || square.getRow() == 7);
	}
	
	public boolean opponentHasNoMoves(Move move) {
		int[][] previewBoard = move.getBoard();
		return game.getValidMoves(previewBoard, getOpponentBoardMark()).size() <= 0;
	}
	
	public boolean enablesCornerForOpponent(Move move) {
		int[][] previewBoard = move.getBoard();
		List<Move> opponentMoves = game.getValidMoves(previewBoard, getOpponentBoardMark());
		for(Move m : opponentMoves) {
			if(isCorner(m.getBoardSquare())) 
				return true;
		}
		return false;
	}
	
	public int maxArgIndex(List<Double> list) {
		double maxValue = Double.NEGATIVE_INFINITY;
		int index = 0;
		for(double d : list) {
			if(d > maxValue){
				maxValue = d;
				index = list.indexOf(d);
			}
		}
		return index;
	}
}