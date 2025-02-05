import java.awt.Color;

import javax.swing.JPanel;

public class Square {
	
	private Piece piece;
	private int x;
	private int y;
	private JPanel panel;
	//private boolean dark;
	
	public Square(int x, int y) {
		setX(x);
		setY(y);
	}
	
	public int getX() {
		return this.x;
	}
	
	public JPanel getPanel() {
		return this.panel;
	}

	public int getY() {
		return this.y;
	}
	
	public Piece getPiece() {
		return this.piece;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	/*
	public void setDark(boolean dark) {
		this.dark = dark;
	}
	
	public boolean getDark() {
		return this.dark;
	}
	*/
	
	public void setPiece(Piece piece) {
		this.piece = piece;
	}
	
	public void drawSquare(boolean dark) {
		panel = new JPanel();
		panel.setBounds(0, 0, 80, 80);
		panel.setLayout(null);
		
		if(dark) {
			panel.setBackground(new Color(174,138,104));
		} else {
			panel.setBackground(new Color(236,218,185));
		}
		
	}
	
	
	
}
