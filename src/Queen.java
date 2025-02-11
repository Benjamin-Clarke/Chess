import java.awt.Graphics;

import javax.swing.ImageIcon;

public class Queen extends Piece {

	private static final long serialVersionUID = 1L;
	
	private ImageIcon image;

	public Queen(boolean white, int xSquare, int ySquare) {
		super(white, xSquare, ySquare);
	}


	public ImageIcon setColor() {
		if(super.isWhite()) {
			image = new ImageIcon("images/white_queen.png");
		} else {
			image = new ImageIcon("images/black_queen.png");
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
			if(Math.abs(x - getPrevX()) == Math.abs(y - getPrevY()) || getPrevX() == x || getPrevY() == y ) {
				if(!pieceOnQueenPath(getPrevX(), getPrevY(), x, y)) {
					return true;
				}	
			}
		}
		return false;
	}
	
	public boolean pieceOnQueenPath(int startX, int startY, int targetX, int targetY) {
    	
		if(getPrevX() == targetX || getPrevY() == targetY) {
			//Moving left 
	    	if (startX > targetX) {
	    		for (int c = startX - 1; c > targetX; c-- ) {
	        		if(isHittingPiece(c, targetY) != null) {
	        			setHittingPiece(isHittingPiece(c, targetY));
	        			return true;
	        		}
	        	}
	    	}
	    	//Moving right 
	    	if (startX < targetX) {
	    		for (int c = startX + 1; c < targetX; c++ ) {
	        		if(isHittingPiece(c, targetY) != null) {
	        			setHittingPiece(isHittingPiece(c, targetY));
	        			return true;
	        		}
	        	}
	    	}
	    	//Moving up 
	    	if (startY > targetY) {
	    		for (int c = startY - 1; c > targetY; c-- ) {
	        		if(isHittingPiece(targetX, c) != null) {
	        			setHittingPiece(isHittingPiece(targetX, c));
	        			return true;
	        		}
	        	}
	    	}
	    	//Moving down 
	    	if (startY < targetY) {
	    		for (int c = startY + 1; c < targetY; c++ ) {
	        		if(isHittingPiece(targetX, c) != null) {
	        			setHittingPiece(isHittingPiece(targetX, c));
	        			return true;
	        		}
	        	}
	    	}
			
		} else {
	    	//Up Left
	    	if(startX > targetX && startY > targetY) {
	    		for(int c = 1; c < Math.abs(startX - targetX); c++) {
	    			if(isHittingPiece(startX - c, startY - c) != null) {
	        			setHittingPiece(isHittingPiece(startX - c, startY - c));
	        			return true;
	        		}
	    		}
	    	}
	    	//Up Right
	    	if(startX < targetX && startY > targetY) {
	    		for(int c = 1; c < Math.abs(startX - targetX); c++) {
	    			if(isHittingPiece(startX + c, startY - c) != null) {
	        			setHittingPiece(isHittingPiece(startX + c, startY - c));
	        			return true;
	        		}
	    		}
	    	}
	    	//Down Left
	    	if(startX > targetX && startY < targetY) {
	    		for(int c = 1; c < Math.abs(startX - targetX); c++) {
	    			if(isHittingPiece(startX - c, startY + c) != null) {
	        			setHittingPiece(isHittingPiece(startX - c, startY + c));
	        			return true;
	        		}
	    		}
	    	}
	    	//Up Right
	    	if(startX < targetX && startY < targetY) {
	    		for(int c = 1; c < Math.abs(startX - targetX); c++) {
	    			if(isHittingPiece(startX + c, startY + c) != null) {
	        			setHittingPiece(isHittingPiece(startX + c, startY + c));
	        			return true;
	        		}
	    		}
	    	}
		}
    	
    	return false;
    }
}
