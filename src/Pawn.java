import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Pawn extends Piece {
	
	private static final long serialVersionUID = 1L;
	
	private ImageIcon image;

	public Pawn(boolean white, int xSquare, int ySquare) {
		super(white, xSquare, ySquare);
	}
	
	public ImageIcon setColor() {
		if(super.isWhite()) {
			image = new ImageIcon("images/white_pawn.png");
		} else {
			image = new ImageIcon("images/black_pawn.png");
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
		
		int moveDirection;
		
		if(isWhite()) {
			moveDirection = -1;
		} else {
			moveDirection = 1;
		}
	
		if (isWithinBoard(x, y) && (isHittingPiece(x,y) == null || isHittingPiece(x,y).isWhite() != this.isWhite())) { 
			
			if( this.getPrevX() == x && (y == this.getPrevY() + (2*moveDirection)) && isFirstMove() 
					&& !pieceInfrontOfPawn()) {
				
				return true;
			} 
			
			//One space movement
			if(this.getPrevX() == x && y == (this.getPrevY() + moveDirection)
					&& !pieceInfrontOfPawn()) {
					return true;
			}
			
			//Taking Pieces 
			setHittingPiece(isHittingPiece(x, y));
			
			if( Math.abs(this.getPrevX() - x) == 1 && y == (this.getPrevY() + moveDirection) 
					&& getHittingPiece() != null) {
				return true;
			}
			
			
		}
		return false;
	}

	private boolean pieceInfrontOfPawn() {

		
		if(isWhite()) {
			if(isHittingPiece(getPrevX(), getPrevY() - 1) != null) {
				return true;
			}
		} else {
			if(isHittingPiece(getPrevX(), getPrevY() + 1) != null) {
				return true;
			}
		}
		
		return false;
	}

}
