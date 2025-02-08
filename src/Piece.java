import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public abstract class Piece extends JPanel {
    private static final long serialVersionUID = 1L;
    public final int SQUARE_SIZE = 80;
    private boolean white = false;
    private boolean killed = false;
    private int prevX;
    private int prevY;
    private int xSquare;
    private int ySquare;
    private ImageIcon image;

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

    public boolean isKilled() {
        return this.killed;
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
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
}
