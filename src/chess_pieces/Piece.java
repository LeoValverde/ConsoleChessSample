package chess_pieces;

import chess_game.Board;

public abstract class Piece {

	// Value 0 - White piece | Value 1 - Black piece
	public Boolean black_white;
	
	public Piece(Boolean black_white){
		this.black_white = black_white;
	}

	// Iterates over the board to check if this piece can move from (x1,y1) to (x2,y2)
	public abstract String isValidMove(Board board, Integer x1, Integer y1, Integer x2, Integer y2);
	
	//Tells the piece that it was moved from (x1,y1) to (x2,y2)
	public void moved(Board board, Integer x1, Integer y1, Integer x2, Integer y2) {}
	
	//Returns the string that identify the piece on the board
	public abstract String identify();

	//Returns a clone of this object
	public abstract Piece clone();

}
