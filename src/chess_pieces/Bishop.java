package chess_pieces;

import chess_game.Board;

public class Bishop extends Piece {

	public Bishop(Boolean black_white) {
		super(black_white);
	}

	public String isValidMove(Board board, Integer x1, Integer y1, Integer x2, Integer y2){
		if(Math.abs(x1-x2) != Math.abs(y1-y2)){ return "INVALID"; }
		
		Integer stepBeforeX;
		Integer stepBeforeY;
		if (x2 > x1){ stepBeforeX = x2 - 1; }
		else { stepBeforeX = x2 + 1; };
		if (y2 > y1){ stepBeforeY = y2 - 1; }
		else { stepBeforeY = y2 + 1; };
		
		Piece pieceStepBefore = board.getPiece(stepBeforeX, stepBeforeY);
		
		if (pieceStepBefore == this){ return "VALID"; }
		if (pieceStepBefore == null){ return isValidMove(board,x1,y1,stepBeforeX,stepBeforeY); }
		return "INVALID";
	}
	
	public String identify(){
		if (black_white){
			return "WB";
		}
		else{
			return "BB";
		}
	}
	
	public Piece clone(){
		return new Bishop(black_white);
	}

}
