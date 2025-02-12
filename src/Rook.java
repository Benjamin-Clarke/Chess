import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Rook extends Piece {

	private static final long serialVersionUID = 1L;
	
	private ImageIcon image;

	public Rook(boolean white, int xSquare, int ySquare) {
		super(white, xSquare, ySquare);
		
		type = Type.ROOK;
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
        	image.paintIcon(this, g, getxSquare()*SQUARE_SIZE, getySquare()*SQUARE_SIZE);
        	
        } else {
            g.drawString("Image not found!", 50, 50);
        }
		
	}

	@Override
	public boolean canMove(int x, int y) {
		if (isWithinBoard(x, y) && (isHittingPiece(x,y) == null || isHittingPiece(x,y).isWhite() != this.isWhite())) {
			if ((getPrevX() == x || getPrevY() == y) && !pieceOnStraightLinePath(getPrevX(), getPrevY(), x, y)) {
				return true;
			}
		}
		return false;
	}
}
