import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.*;

public class Board {
	
	private JFrame frame;
	private Square[][] squares;
	final int boardSizePixels = 640;
	public final int SQUARE_SIZE = 80;
	
	
	public Board() {
	
		/*
		squares = new Square[8][8];
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				squares[i][j] = new Square(i,j);
				
				if( (i+j) % 2 == 0) {
					//Make square dark color
					squares[i][j].drawSquare(true);
				} else {
					//Make square light color
					squares[i][j].drawSquare(false);
				}
				
				frame.add(squares[i][j].getPanel());
			}	
		}
		
		*/
		
	}
	
	public void draw(Graphics2D g2) {
		
		for(int row = 0; row < 8; row ++) {
			for (int col = 0; col < 8; col++) {
				
				if( (row + col) % 2 == 0) {
					//Make square dark color
					g2.setColor(new Color(174,138,104));
				} else {
					//Make square light color
					g2.setColor(new Color(236,218,185));
				}
				g2.fillRect(row*SQUARE_SIZE, col*SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
			}
		}
	}

	public JFrame getFrame() {
		return frame;
	}
	
	public Square getSquare(int x, int y) {
		return this.squares[x][y];
	}
	
}
