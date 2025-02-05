import java.util.ArrayList;

public class Game{
	
	private Board board;
	public static ArrayList<Piece> pieces = new ArrayList<>();
	
	public Game() {
		board = new Board();
		board.createBoard();
		
		setPieces();
		
		for (int i = 0; i < pieces.size(); i++) {
			int x = pieces.get(i).getxSquare();
			int y = pieces.get(i).getySquare();

			board.getSquare(x, y).getPanel().add(pieces.get(i));
			pieces.get(i).setBounds(0, 0, 80, 80);
			pieces.get(i).setVisible(true);
		}
	}
	
	public void setPieces() {
		
		//White Pieces
		pieces.add(new Pawn(true, 6, 0));
		pieces.add(new Pawn(true, 6, 1));
		pieces.add(new Pawn(true, 6, 2));
		pieces.add(new Pawn(true, 6, 3));
		pieces.add(new Pawn(true, 6, 4));
		pieces.add(new Pawn(true, 6, 5));
		pieces.add(new Pawn(true, 6, 6));
		pieces.add(new Pawn(true, 6, 7));
		pieces.add(new Rook(true, 7, 0));
		pieces.add(new Rook(true, 7, 7));
		pieces.add(new Knight(true, 7, 1));
		pieces.add(new Knight(true, 7, 6));
		pieces.add(new Bishop(true, 7, 2));
		pieces.add(new Bishop(true, 7, 5));
		pieces.add(new Queen(true, 7, 4));
		pieces.add(new King(true, 7, 3));
		
		
		//Black Pieces
		pieces.add(new Pawn(false, 1, 0));
		pieces.add(new Pawn(false, 1, 1));
		pieces.add(new Pawn(false, 1, 2));
		pieces.add(new Pawn(false, 1, 3));
		pieces.add(new Pawn(false, 1, 4));
		pieces.add(new Pawn(false, 1, 5));
		pieces.add(new Pawn(false, 1, 6));
		pieces.add(new Pawn(false, 1, 7));
		pieces.add(new Rook(false, 0, 0));
		pieces.add(new Rook(false, 0, 7));
		pieces.add(new Knight(false, 0, 1));
		pieces.add(new Knight(false, 0, 6));
		pieces.add(new Bishop(false, 0, 2));
		pieces.add(new Bishop(false, 0, 5));
		pieces.add(new Queen(false, 0, 4));
		pieces.add(new King(false, 0, 3));
	
	}

	// Main
	public static void main(String[] args) {
		new Game();
		
	}

}
