import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Rook extends Piece {

	private static final long serialVersionUID = 1L;
	
	private ImageIcon image;

	public Rook(boolean white, int xSquare, int ySquare) {
		super(white, xSquare, ySquare);
	}


	public ImageIcon setColor() {
		if(super.isWhite()) {
			image = new ImageIcon("images/white_rook.png");
		} else {
			image = new ImageIcon("images/black_rook.png");
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
        	image.paintIcon(this, g, 0, 0);
        
        } else {
            g.drawString("Image not found!", 50, 50);
        }
		
	}
}
