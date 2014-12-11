package chess_game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Game {

	private Boolean black_white_turn;
	private Board board;
	
	public Game(){
		//Creating the game board
		board = new Board();
		
		//White plays first
		black_white_turn = Boolean.TRUE;
	}
	
	public void playTurn(){
		board.printBoard();
		if(board.isInCheck(black_white_turn)){ System.out.println("CHECK!"); }
		printTurn();
		
		String moveResult;
		while(Boolean.TRUE){
			moveResult = inputMove();
			if(moveResult != null){
				board.printBoard();
				System.out.println(moveResult);
			} else {
				break;
			}
		};
	}
	
	public void printTurn(){
		if (black_white_turn){
			System.out.println("White plays now...");
		} else{
			System.out.println("Black plays now...");
		}
	}
	
	public String inputMove(){
		Integer x1, x2, y1, y2;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
		try {
			System.out.print("Origin X: "); 
			x1 = Integer.valueOf(in.readLine());
			System.out.print("Origin Y: "); 
			y1 = Integer.valueOf(in.readLine());
			System.out.print("Destination X: "); 
			x2 = Integer.valueOf(in.readLine());
			System.out.print("Destination Y: "); 
			y2 = Integer.valueOf(in.readLine());
			String moveResult = board.checkMove(x1,y1,x2,y2,black_white_turn, Boolean.TRUE);
			if (moveResult == null) {
				board.getPiece(x1,y1).applyMove(board,x1,y1,x2,y2);
			}
			return moveResult;
		} catch (IOException e) {
			return e.getMessage();
		} catch (NumberFormatException e){
			return "This is not a valid number.";
		}
	}
	
	public void changeTurn(){
		//After you play, you lose the right to use the EnPassant move over all Pawns of your enemy...
		board.clearEnPassingAllows(!black_white_turn);
		
		//Check if there is some Pawn promotion to be made
		if (board.checkForPromotionAvaliable(black_white_turn)){
			
			board.printBoard();
			System.out.println("You got a promotion!");
			
			//Asks the user to type the Piece to promote
			int selectedPiece = choosePieceForPromotion();
			
			//Try again if the player inputed an invalid value
			while (!board.applyPromotion(selectedPiece, black_white_turn)){
				System.out.println("Invalid choice. Please select a valid number...");
				selectedPiece = choosePieceForPromotion();
			}
		};
		//Changes the turn
		black_white_turn = !black_white_turn;
	}
	
	//Asks the user to type the Piece to promote
	private Integer choosePieceForPromotion(){
		System.out.println("Choose the promotion:");
		System.out.println("1 - Queen");
		System.out.println("2 - Rook");
		System.out.println("3 - Bishop");
		System.out.println("4 - Knight");
		System.out.print("Your choice: ");
		
		Integer result = 0;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in)); 
		try {
			result = Integer.valueOf(in.readLine());
		}
		catch (NumberFormatException e) {}
		catch (IOException e) {};
		return result;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		while(!game.board.noPossibleMove(game.black_white_turn)){
			game.playTurn();
			game.changeTurn();
		}
		game.board.printBoard();
		game.printTurn();
		if(game.board.isInCheck(game.black_white_turn)){
			if (game.black_white_turn){
				System.out.println("CHECKMATE!!! WHITE WINS!");
			} else {
				System.out.println("CHECKMATE!!! BLACK WINS!");
			}
		} else {
			System.out.println("This game is over. It's a DRAW!");
		}
		
		try {
		    Thread.sleep(5000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}
}
