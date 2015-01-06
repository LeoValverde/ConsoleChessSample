package chess_pieces;

import chess_game.Board;

public class Pawn extends Piece {

	private Integer Direction;
	private Boolean neverMoved;
	private Boolean liableOfCaptureBy_EnPassant;

	public Pawn(Boolean black_white) {
		super(black_white);
		
		//White moves forward, Black moves backward
		if(black_white){ Direction = -1; }
		else { Direction = 1; };
		
		neverMoved = Boolean.TRUE;
		liableOfCaptureBy_EnPassant = Boolean.FALSE;
	}

	@Override
	public String isValidMove(Board board, Integer x1, Integer y1, Integer x2, Integer y2){
		
		//Handle move attempts
		if(x1 == x2){
			//If pawn is moving 1 step and there's no piece, OK...
			if(y2 == y1 + Direction){
				if (board.getPiece(x2, y2) == null){ return "VALID"; }
				else { return "INVALID"; }
			}
			//If pawn is moving 2 steps and there's no piece through the way, OK...
			if((y2 == y1 +  2*Direction) && neverMoved){
				if ((board.getPiece(x2, y2) == null) && (board.getPiece(x2, y2 - Direction) == null)){ return "VALID"; }
				else { return "INVALID"; }
			}
		}
		//Handle capture attempts
		else if((Math.abs(x1 - x2) == 1) && (y2 == y1 + Direction)){
			//If there is another piece, OK...
			if (board.getPiece(x2, y2) != null){ return "VALID"; }
			//If you can perform "En passant" move, OK...
			else if(
				board.getPiece(x2, y1) instanceof Pawn
				&& board.getPiece(x2, y1).black_white != this.black_white
				&& ((Pawn)board.getPiece(x2, y1)).liableOfCaptureBy_EnPassant
			){
				return "EN_PASSANT";
			}
		}
		
		return "INVALID";
	}
	
	//Returns the string that identify the piece on the board
	@Override
	public String identify(){
		if (black_white){
			return "WP";
		}
		else{
			return "BP";
		}
	}

	//Tells the piece that it was moved from (x1,y1) to (x2,y2)
	@Override
	public void moved(Board board, Integer x1, Integer y1, Integer x2, Integer y2) {
		//Registers that the Pawn already moved
		neverMoved = Boolean.FALSE;
		
		//If the Pawn is moving 2 steps, it is liable of being captured by the "En Passant" move
		if (Math.abs(y1-y2) == 2){
			liableOfCaptureBy_EnPassant = Boolean.TRUE;
		}
	}
	
	public Piece clone(){
		Pawn newPawn = new Pawn(black_white);
		newPawn.setAttributes(neverMoved, liableOfCaptureBy_EnPassant);
		return newPawn;
	}
	
	protected void setAttributes(Boolean neverMoved, Boolean liableOfCaptureBy_EnPassant){
		this.neverMoved = neverMoved;
		this.liableOfCaptureBy_EnPassant = liableOfCaptureBy_EnPassant;
	}
	
	public void clearEnPassantLiability(){
		liableOfCaptureBy_EnPassant = Boolean.FALSE;
	}

}
