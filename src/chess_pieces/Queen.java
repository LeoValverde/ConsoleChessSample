package chess_pieces;

import chess_game.Board;

public class Queen extends Piece {

	public Queen(Boolean black_white) {
		super(black_white);
	}

	public Boolean isValidMove(Board board, Integer x1, Integer y1, Integer x2, Integer y2){
		String KindOfMove = "None";
		if((x1-x2 == 0) || (y1-y2 == 0)){ KindOfMove = "Rook"; }
		if(Math.abs(x1-x2) == Math.abs(y1-y2)){ KindOfMove = "Bishop"; }
		if (KindOfMove == "None") { return Boolean.FALSE; }

		Integer stepBeforeX;
		Integer stepBeforeY;
		
		if (KindOfMove == "Rook"){
			if (x2 > x1){ stepBeforeX = x2 - 1; stepBeforeY = y2;}
			else if (x1 > x2){ stepBeforeX = x2 + 1; stepBeforeY = y2;}
			else if (y2 > y1){ stepBeforeX = x2; stepBeforeY = y2 - 1;}
			else if (y1 > y2){ stepBeforeX = x2; stepBeforeY = y2 + 1;}
			else { stepBeforeX = x2; stepBeforeY = y2;}
		} else {
			if (x2 > x1){ stepBeforeX = x2 - 1; }
			else { stepBeforeX = x2 + 1; };
			if (y2 > y1){ stepBeforeY = y2 - 1; }
			else { stepBeforeY = y2 + 1; };
		}
		
		Piece pieceStepBefore = board.getPiece(stepBeforeX, stepBeforeY);
		
		if (pieceStepBefore == this){ return Boolean.TRUE; }
		if (pieceStepBefore == null){ return isValidMove(board,x1,y1,stepBeforeX,stepBeforeY); }
		return Boolean.FALSE;
	}
	
	public String identify(){
		if (black_white){
			return "WQ";
		}
		else{
			return "BQ";
		}
	}
	
	public Piece clone(){
		return new Queen(black_white);
	}

}
