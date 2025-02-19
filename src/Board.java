import java.awt.Color;
import java.awt.Graphics2D;
import javax.swing.*;

public class Board {
	
	private JFrame frame;
	private Square[][] squares;
	final int boardSizePixels = 640;
	public final int SQUARE_SIZE = 80;
	
	
	public Board() {
		
	}
	
	public void draw(Graphics2D g2) {
		
		for(int row = 0; row < 8; row ++) {
			for (int col = 0; col < 8; col++) {
				
				if( (row + col) % 2 == 0) {
					//Make square light color
					g2.setColor(new Color(236,218,185));
				} else {
					//Make square dark color
					g2.setColor(new Color(174,138,104));
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
