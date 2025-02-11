import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public abstract class Piece extends JPanel {
    private static final long serialVersionUID = 1L;
    public final int SQUARE_SIZE = 80;
    private boolean white;
	private boolean firstMove = true;
    private int prevX;
    private int prevY;
    private int xSquare;
    private int ySquare;
    private ImageIcon image;
    private Piece hittingPiece;

    public Piece(boolean white, int xSquare, int ySquare) {
        setWhite(white);
        setxSquare(xSquare);
        setySquare(ySquare);
        setPrevX(xSquare);
        setPrevY(ySquare);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Subclasses will override this method
    }
    
    public boolean canMove(int x, int y) {
    	return false;
    }
    
    public Piece isHittingPiece(int x, int y) {
    	for (Piece piece: Game.pieces) {
    		if (piece.getxSquare() == x && piece.getySquare() == y && piece != this) {
    			return piece;
    		}
    	}
    	return null;
    }
    
    public Piece getPiece(int x, int y) {
    	for (Piece piece: Game.pieces) {
    		if (piece.getxSquare() == x && piece.getySquare() == y) {
    			return piece;
    		}
    	}
    	return null;
    }
    
    public boolean canTake(int x, int y) {
    	if(isHittingPiece(x, y) != null && isHittingPiece(x, y).isWhite() != this.isWhite()) {
    		return true;
    	}
		return false;
	}
    
    public boolean pieceOnDiagonalPath(int startX, int startY, int targetX, int targetY) {
    	
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
    	
    	return false;
    }
    
    public boolean pieceOnStraightLinePath(int startX, int startY, int targetX, int targetY) {
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
    	
    	return false;
    }
    
    public boolean isWithinBoard(int x, int y) {
    	if(x <= 7 && x >= 0 && y <= 7 && y >= 0 ) {
    		return true;
    	}
    	return false;
    }

    public boolean isWhite() {
        return this.white;
    }

    public void setWhite(boolean white) {
        this.white = white;
    }

    public int getxSquare() {
        return xSquare;
    }

    public void setxSquare(int xSquare) {
        this.xSquare = xSquare;
    }

    public int getySquare() {
        return ySquare;
    }

    public void setySquare(int ySquare) {
        this.ySquare = ySquare;
    }
    
    public ImageIcon getImage() {
		return image;
	}

	public int getPrevX() {
		return prevX;
	}

	public void setPrevX(int prevX) {
		this.prevX = prevX;
	}

	public int getPrevY() {
		return prevY;
	}

	public void setPrevY(int prevY) {
		this.prevY = prevY;
	}

	public boolean isFirstMove() {
		return firstMove;
	}

	public void setFirstMove(boolean firstMove) {
		this.firstMove = firstMove;
	}

	public Piece getHittingPiece() {
		return hittingPiece;
	}

	public void setHittingPiece(Piece hittingPiece) {
		this.hittingPiece = hittingPiece;
	}
}
