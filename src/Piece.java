import java.awt.Graphics;
import javax.swing.JPanel;

public abstract class Piece extends JPanel {
    private static final long serialVersionUID = 1L;
    private boolean white = false;
    private boolean killed = false;
    private int xPixels;
    private int yPixels;
    private int xSquare;
    private int ySquare;

    public Piece(boolean white, int xSquare, int ySquare) {
        setWhite(white);
        setxSquare(xSquare);
        setySquare(ySquare);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Subclasses will override this method
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

    public int getxPixels() {
        return xPixels;
    }

    public void setxPixels(int xPixels) {
        this.xPixels = xPixels;
    }

    public int getyPixels() {
        return yPixels;
    }

    public void setyPixels(int yPixels) {
        this.yPixels = yPixels;
    }

    public int getySquare() {
        return ySquare;
    }

    public void setySquare(int ySquare) {
        this.ySquare = ySquare;
    }
}
