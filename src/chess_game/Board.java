package chess_game;

import chess_pieces.Bishop;
import chess_pieces.King;
import chess_pieces.Knight;
import chess_pieces.Piece;
import chess_pieces.Queen;
import chess_pieces.Rook;

public class Board {
	private Piece[][] boardArray;
	
	// Board constructor
	public Board(){
		// Creating the board;
		boardArray = new Piece [8][8];
		
		// Positioning pieces at the board 
		// TODO - Create more pieces
		boardArray[0][0] = new Rook(Boolean.FALSE);
		boardArray[1][0] = new Knight(Boolean.FALSE);
		boardArray[2][0] = new Bishop(Boolean.FALSE);
		boardArray[3][0] = new Queen(Boolean.FALSE);
		boardArray[4][0] = new King(Boolean.FALSE);
		boardArray[5][0] = new Bishop(Boolean.FALSE);
		boardArray[6][0] = new Knight(Boolean.FALSE);
		boardArray[7][0] = new Rook(Boolean.FALSE);
		
		boardArray[0][7] = new Rook(Boolean.TRUE);
		boardArray[1][7] = new Knight(Boolean.TRUE);
		boardArray[2][7] = new Bishop(Boolean.TRUE);
		boardArray[3][7] = new Queen(Boolean.TRUE);
		boardArray[4][7] = new King(Boolean.TRUE);
		boardArray[5][7] = new Bishop(Boolean.TRUE);
		boardArray[6][7] = new Knight(Boolean.TRUE);
		boardArray[7][7] = new Rook(Boolean.TRUE);
	}
	
	public Piece getPiece(Integer x, Integer y){
		return boardArray[x][y];
	}
	
	// Board constructor for simulating scenarios. Creates a copy of a board
	public Board(Piece[][] boardArrayInput){
		// Creating the board;
		boardArray = new Piece [8][8];

		// Setting up the board;
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				boardArray[x][y] = boardArrayInput[x][y];
			}
	    }
	}
	
	public void applyMove(Integer x1, Integer y1, Integer x2, Integer y2){
		boardArray[x2][y2]= boardArray[x1][y1];
		boardArray[x1][y1] = null;
	}
	
	public String checkMove(Integer x1, Integer y1, Integer x2, Integer y2, Boolean black_white_turn, Boolean verifyCheck){
		// Check if the points are out of bounds
		if (!checkBounds(x1, y1, x2, y2)){ return "Values out of bounds!"; };

		// Check if you are attempting to move to the same position
		if (x1 == x2 && y1 == y2){ return "You can't move to the same position!"; };
		
		// Check if there is a Piece in the source location
		Piece movingPiece = boardArray[x1][y1];
		if (movingPiece == null){ return "There is no piece at (" + x1 + "," + y1 + ")."; };

		// Check if the Piece color is owned by the current player
		if (movingPiece.black_white != black_white_turn){ return "The piece at (" + x1 + "," + y1 + ") is not yours!"; };
		
		// Check if the Piece can move to this location
		if (!movingPiece.isValidMove(this, x1,y1,x2,y2)){ return "The piece cant move to (" + x2 + "," + y2 + ")"; };
		
		// Check if the player is attempting to attack himself
		Piece targetPiece = boardArray[x2][y2];
		if ((targetPiece != null) && targetPiece.black_white == black_white_turn){ return "You cant kill your Pieces!"; };
		
		if (verifyCheck){
			// Check if the new scenario is a check against the current player
			Board testingScnarioBoard = new Board(boardArray);
			testingScnarioBoard.applyMove(x1, y1, x2, y2);
			if (testingScnarioBoard.isInCheck(movingPiece.black_white)) { return "You can't let the king in danger!"; };	
		}
		
		// Returns true, because the move is possible
		return null;
	}
	
	// Check if the given coordinates are valid
	public Boolean checkBounds(Integer x1, Integer y1, Integer x2, Integer y2){
		if (x1 < 0 || x1 > 7) { return Boolean.FALSE; };
		if (y1 < 0 || y1 > 7) { return Boolean.FALSE; };
		if (x2 < 0 || x2 > 7) { return Boolean.FALSE; };
		if (y2 < 0 || y2 > 7) { return Boolean.FALSE; };
		return Boolean.TRUE;
	}
	
	// Check if the king is safe from attacks
	public Boolean isInCheck(Boolean black_white){
		Integer kingX;
		Integer kingY;
		kingX = -1;
		kingY = -1;
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if (boardArray[x][y] != null && boardArray[x][y].black_white == black_white && boardArray[x][y] instanceof King){
					kingX = x;
					kingY = y;
					break;
				}
			}
			if (kingX != -1){ break; }
		}
		
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if(checkMove(x, y, kingX, kingY, !black_white, Boolean.FALSE) == null){ return Boolean.TRUE; };
			}
		}
		
		return Boolean.FALSE;
	}
	
	// Check if the player have a possible move
	public Boolean noPossibleMove(Boolean black_white){
		for (int x1 = 0; x1 < 8; x1++) {
			for (int y1 = 0; y1 < 8; y1++) {
				for (int x2 = 0; x2 < 8; x2++) {
					for (int y2 = 0; y2 < 8; y2++) {
						if(checkMove(x1, y1, x2, y2, black_white, Boolean.TRUE) == null){ return Boolean.FALSE; };
					}
				}
			}
	    }
		return Boolean.TRUE;
	}
	
	public void printBoard(){
		String line;
		System.out.println("Y\\X 0  1  2  3  4  5  6  7");
		for (int x = 0; x < 8; x++) {
			line = x + " |";
			for (int y = 0; y < 8; y++) {
				if(boardArray[y][x] == null){
					line += "  |";
				} else {
					line += boardArray[y][x].identify() + "|";
				}
			}
			System.out.println(line);
	    }
	}
	
}
