package chess_pieces;

import chess_game.Board;

public class Rook extends Piece {

	public Rook(Boolean black_white) {
		super(black_white);
	}

	public String isValidMove(Board board, Integer x1, Integer y1, Integer x2, Integer y2){
		if((x1==x2) && (y1==y2)){ return "VALID"; }
		if((x1-x2 != 0) && (y1-y2 != 0)){ return "INVALID"; }
		
		Integer stepBeforeX;
		Integer stepBeforeY;
		if (x2 > x1){ stepBeforeX = x2 - 1; stepBeforeY = y2;}
		else if (x1 > x2){ stepBeforeX = x2 + 1; stepBeforeY = y2;}
		else if (y2 > y1){ stepBeforeX = x2; stepBeforeY = y2 - 1;}
		else if (y1 > y2){ stepBeforeX = x2; stepBeforeY = y2 + 1;}
		else { stepBeforeX = x2; stepBeforeY = y2;}
		
		Piece pieceStepBefore = board.getPiece(stepBeforeX, stepBeforeY);
		
		if (pieceStepBefore == this){ return "VALID"; }
		if (pieceStepBefore == null){ return isValidMove(board,x1,y1,stepBeforeX,stepBeforeY); }
		return "INVALID";
	}
	
	public String identify(){
		if (black_white){
			return "WR";
		}
		else{
			return "BR";
		}
	}
	
	public Piece clone(){
		return new Rook(black_white);
	}

}
