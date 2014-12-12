package chess_game;

import chess_pieces.Bishop;
import chess_pieces.King;
import chess_pieces.Knight;
import chess_pieces.Pawn;
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
		boardArray[0][0] = new Rook(Boolean.FALSE);
		boardArray[1][0] = new Knight(Boolean.FALSE);
		boardArray[2][0] = new Bishop(Boolean.FALSE);
		boardArray[3][0] = new Queen(Boolean.FALSE);
		boardArray[4][0] = new King(Boolean.FALSE);
		boardArray[5][0] = new Bishop(Boolean.FALSE);
		boardArray[6][0] = new Knight(Boolean.FALSE);
		boardArray[7][0] = new Rook(Boolean.FALSE);
		
		boardArray[0][1] = new Pawn(Boolean.FALSE);
		boardArray[1][1] = new Pawn(Boolean.FALSE);
		boardArray[2][1] = new Pawn(Boolean.FALSE);
		boardArray[3][1] = new Pawn(Boolean.FALSE);
		boardArray[4][1] = new Pawn(Boolean.FALSE);
		boardArray[5][1] = new Pawn(Boolean.FALSE);
		boardArray[6][1] = new Pawn(Boolean.FALSE);
		boardArray[7][1] = new Pawn(Boolean.FALSE);
		
		boardArray[0][7] = new Rook(Boolean.TRUE);
		boardArray[1][7] = new Knight(Boolean.TRUE);
		boardArray[2][7] = new Bishop(Boolean.TRUE);
		boardArray[3][7] = new Queen(Boolean.TRUE);
		boardArray[4][7] = new King(Boolean.TRUE);
		boardArray[5][7] = new Bishop(Boolean.TRUE);
		boardArray[6][7] = new Knight(Boolean.TRUE);
		boardArray[7][7] = new Rook(Boolean.TRUE);
		
		boardArray[0][6] = new Pawn(Boolean.TRUE);
		boardArray[1][6] = new Pawn(Boolean.TRUE);
		boardArray[2][6] = new Pawn(Boolean.TRUE);
		boardArray[3][6] = new Pawn(Boolean.TRUE);
		boardArray[4][6] = new Pawn(Boolean.TRUE);
		boardArray[5][6] = new Pawn(Boolean.TRUE);
		boardArray[6][6] = new Pawn(Boolean.TRUE);
		boardArray[7][6] = new Pawn(Boolean.TRUE);
	}
	
	// Getter method of the board elements
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
				if (boardArrayInput[x][y] != null){
					boardArray[x][y] = boardArrayInput[x][y].clone();
				}
			}
	    }
	}
	
	//Check the move from (x1,y1) to (x2,y2) and execute if possible
	public String move(Integer x1, Integer y1, Integer x2, Integer y2, Boolean black_white_turn){
		//Check if the move is valid
		String checkResult = checkMove(x1, y1, x2, y2, black_white_turn, Boolean.TRUE);
		
		//If it's not an error, apply the move
		if (checkValid(checkResult)){
			applyMove(x1, y1, x2, y2, checkResult);
			checkResult = null;
		}
		return checkResult;
	}
	
	//Check if the move from (x1,y1) to (x2,y2) is possible
	private String checkMove(Integer x1, Integer y1, Integer x2, Integer y2, Boolean black_white_turn, Boolean verifyCheck){
		// Check if the points are out of bounds
		if (!checkBounds(x1, y1, x2, y2)){ return "Values out of bounds!"; };

		// Check if you are attempting to move to the same position
		if (x1 == x2 && y1 == y2){ return "You can't move to the same position!"; };
		
		// Check if there is a Piece in the source location
		Piece movingPiece = boardArray[x1][y1];
		if (movingPiece == null){ return "There is no piece at (" + x1 + "," + y1 + ")."; };

		// Check if the Piece color is owned by the current player
		if (movingPiece.black_white != black_white_turn){ return "The piece at (" + x1 + "," + y1 + ") is not yours!"; };
		
		// Check if the player is attempting to attack himself
		Piece targetPiece = boardArray[x2][y2];
		if ((targetPiece != null) && targetPiece.black_white == black_white_turn){ return "You can't kill your Pieces!"; };
		
		// Check if the Piece can move to this location
		String validationResult = movingPiece.isValidMove(this, x1,y1,x2,y2);
		if (validationResult == "INVALID"){ return "The piece can't move to (" + x2 + "," + y2 + ")"; };
		
		if (verifyCheck){
			// Check if the new scenario is a check against the current player
			Board testingScnarioBoard = new Board(boardArray);
			testingScnarioBoard.applyMove(x1, y1, x2, y2, validationResult);
			if (testingScnarioBoard.isInCheck(movingPiece.black_white)) { return "You can't let the king in danger!"; };	
		}
		
		// Returns true, because the move is possible
		return validationResult;
	}
	
	//Move the piece from (x1,y1) to (x2,y2)
	protected void applyMove(Integer x1, Integer y1, Integer x2, Integer y2, String checkResult) {			
		boardArray[x2][y2]= boardArray[x1][y1];
		boardArray[x1][y1] = null;
		
		//If the check move is an "En Passant", captures the piece at (x2,y1)
		if (checkResult == "EN_PASSANT"){
				boardArray[x2][y1] = null;
		}
		
		boardArray[x2][y2].moved(this, x1, y1, x2, y2);
	}
	
	private Boolean checkValid(String checkResult){
		if (checkResult == "VALID" || checkResult == "EN_PASSANT"){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	// Check if the given coordinates are valid
	private Boolean checkBounds(Integer x1, Integer y1, Integer x2, Integer y2){
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
				if(checkValid(checkMove(x, y, kingX, kingY, !black_white, Boolean.FALSE))){ return Boolean.TRUE; };
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
						if(checkValid(checkMove(x1, y1, x2, y2, black_white, Boolean.TRUE))){ return Boolean.FALSE; };
					}
				}
			}
	    }
		return Boolean.TRUE;
	}
	
	//Prints the board to the player
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
	
	//Clears the condition of the pawns (that belongs to the team passed by parameter) that flags
	//	that they are liable to be captured by "En Passant" move
	public void clearEnPassingAllows(Boolean black_white){
		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if(
					boardArray[x][y] != null
					&& boardArray[x][y].black_white == black_white
					&& (boardArray[x][y] instanceof Pawn)
				){
					((Pawn)boardArray[x][y]).clearEnPassantLiability();
				}
			}
	    }
	}
	
	//Check if some Pawn (that belongs to the team passed by parameter) has reached the final line
	public Boolean checkForPromotionAvaliable(Boolean black_white){
		int y;
		if (black_white){ y = 0; }
		else { y = 7; };
		for (int x = 0; x < 8; x++) {
			if(
					boardArray[x][y] != null
					&& boardArray[x][y].black_white == black_white
					&& (boardArray[x][y] instanceof Pawn)
			){
				return Boolean.TRUE;
			}
	    }
		return Boolean.FALSE;
	}
	
	//Promotes the Pawn that reached the final line to the piece selected by the user
	public Boolean applyPromotion(int selectedPiece, Boolean black_white){
		int y;
		if (black_white){ y = 0; }
		else { y = 7; };
		for (int x = 0; x < 8; x++) {
			if(
					boardArray[x][y] != null
					&& boardArray[x][y].black_white == black_white
					&& (boardArray[x][y] instanceof Pawn)
			){
				if (selectedPiece == 1){ boardArray[x][y] = new Queen(black_white); }
				else if (selectedPiece == 2){ boardArray[x][y] = new Rook(black_white); }
				else if (selectedPiece == 3){ boardArray[x][y] = new Bishop(black_white); }
				else if (selectedPiece == 4){ boardArray[x][y] = new Knight(black_white); }
				else { return Boolean.FALSE; };
			}
	    }
		return Boolean.TRUE;
	}
	
}
