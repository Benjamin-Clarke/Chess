import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Bishop extends Piece {

	private static final long serialVersionUID = 1L;
	
	private ImageIcon image;

	public Bishop(boolean white, int xSquare, int ySquare) {
		super(white, xSquare, ySquare);
		
		type = Type.BISHOP;
	}


	public ImageIcon setColor() {
		if(super.isWhite()) {
			image = new ImageIcon("images/white_bishop.png");
		} else {
			image = new ImageIcon("images/black_bishop.png");
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
		
		//Check if hitting another piece
		//setHittingPiece(isHittingPiece(x,y));
		
		if (isWithinBoard(x, y) && (isHittingPiece(x,y) == null || isHittingPiece(x,y).isWhite() != this.isWhite())) {
			if( (Math.abs(x - getPrevX()) == Math.abs(y - getPrevY())) 
					&& !pieceOnDiagonalPath(getPrevX(), getPrevY(), x, y) ) {
				if(x != getPrevX() && y != getPrevY()) {
					return true;
				}
				
			}
		}
		
		return false;
	}
}
