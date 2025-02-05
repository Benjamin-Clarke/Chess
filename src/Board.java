import java.awt.GridLayout;
import javax.swing.*;

public class Board {
	
	private JFrame frame;
	private Square[][] squares;
	private final int boardSizePixels = 640;
	
	public void createBoard() {
	
		frame = new JFrame("Chess");
		frame.setSize(boardSizePixels,boardSizePixels);
		frame.setLayout(new GridLayout(8,8));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		
		//frame.setIconImage(new ImageIcon("images/white_pawn.png").getImage());
		
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
		
		frame.setVisible(true);
		
	}
	
	public void setDocIcon() {
        this.frame.setIconImage(new ImageIcon("images/white_pawn.png").getImage());
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public Square getSquare(int x, int y) {
		return this.squares[x][y];
	}
	
}
