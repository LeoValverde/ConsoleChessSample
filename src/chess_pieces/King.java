package chess_pieces;

import chess_game.Board;

public class King extends Piece {

	public King(Boolean black_white) {
		super(black_white);
	}

	public Boolean isValidMove(Board board, Integer x1, Integer y1, Integer x2, Integer y2){
		if(Math.abs(x1-x2) <= 1 && Math.abs(y1-y2) <= 1){
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
	
	public String identify(){
		if (black_white){
			return "WK";
		}
		else{
			return "BK";
		}
	}
	
}
