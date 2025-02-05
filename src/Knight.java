import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Knight extends Piece {

	private static final long serialVersionUID = 1L;
	
	private ImageIcon image;

	public Knight(boolean white, int xSquare, int ySquare) {
		super(white, xSquare, ySquare);
	}


	public ImageIcon setColor() {
		if(super.isWhite()) {
			image = new ImageIcon("images/white_knight.png");
		} else {
			image = new ImageIcon("images/black_knight.png");
		}
		return image;
	}
	
	public ImageIcon getImage() {
		return image;
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.setColor();
		
        if (image != null) {
        	this.setBackground(null);
        	image.paintIcon(this, g, getxSquare()*SQUARE_SIZE, getySquare()*SQUARE_SIZE);
        
        } else {
            g.drawString("Image not found!", 50, 50);
        }
		
	}
}
