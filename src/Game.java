import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private Board board = new Board();
	private JFrame frame;
	public static ArrayList<Piece> pieces = new ArrayList<>();
	final int BOARD_SIZE = 640;
	
	public Game() {
		
		frame = new JFrame("Chess");
		frame.getContentPane().setPreferredSize(new Dimension(BOARD_SIZE,BOARD_SIZE));
		frame.pack(); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(this);
		
		setPieces();
		frame.setVisible(true);
		/*
		for (int i = 0; i < pieces.size(); i++) {
			int x = pieces.get(i).getxSquare();
			int y = pieces.get(i).getySquare();

			board.getSquare(x, y).getPanel().add(pieces.get(i));
			pieces.get(i).setBounds(0, 0, 80, 80);
			pieces.get(i).setVisible(true);
		}
		*/
	}
	
	public void setPieces() {
		
		//White Pieces
		pieces.add(new Pawn(true, 0, 6));
		pieces.add(new Pawn(true, 1, 6));
		pieces.add(new Pawn(true, 2, 6));
		pieces.add(new Pawn(true, 3, 6));
		pieces.add(new Pawn(true, 4, 6));
		pieces.add(new Pawn(true, 5, 6));
		pieces.add(new Pawn(true, 6, 6));
		pieces.add(new Pawn(true, 7, 6));
		pieces.add(new Rook(true, 0, 7));
		pieces.add(new Rook(true, 7, 7));
		pieces.add(new Knight(true, 1, 7));
		pieces.add(new Knight(true, 6, 7));
		pieces.add(new Bishop(true, 2, 7));
		pieces.add(new Bishop(true, 5, 7));
		pieces.add(new Queen(true, 4, 7));
		pieces.add(new King(true, 3, 7));
		
		
		//Black Pieces
		pieces.add(new Pawn(false, 0, 1));
		pieces.add(new Pawn(false, 1, 1));
		pieces.add(new Pawn(false, 2, 1));
		pieces.add(new Pawn(false, 3, 1));
		pieces.add(new Pawn(false, 4, 1));
		pieces.add(new Pawn(false, 5, 1));
		pieces.add(new Pawn(false, 6, 1));
		pieces.add(new Pawn(false, 7, 1));
		pieces.add(new Rook(false, 0, 0));
		pieces.add(new Rook(false, 7, 0));
		pieces.add(new Knight(false, 1, 0));
		pieces.add(new Knight(false, 6, 0));
		pieces.add(new Bishop(false, 2, 0));
		pieces.add(new Bishop(false, 5, 0));
		pieces.add(new Queen(false, 4, 0));
		pieces.add(new King(false, 3, 0));
	
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        board.draw(g2);
        
        for (int i = 0; i < pieces.size(); i++) {
        	pieces.get(i).paintComponent(g2);
        }
    }

	// Main
	public static void main(String[] args) {
		new Game();
		
	}

}
