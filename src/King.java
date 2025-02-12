import java.awt.Graphics;

import javax.swing.ImageIcon;

public class King extends Piece {

	private static final long serialVersionUID = 1L;
	
	private ImageIcon image;
	private Rook castlingRook;

	public King(boolean white, int xSquare, int ySquare) {
		super(white, xSquare, ySquare);
		
		type = Type.KING;
	}


	public ImageIcon setColor() {
		if(super.isWhite()) {
			image = new ImageIcon("images/white_king.png");
		} else {
			image = new ImageIcon("images/black_king.png");
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
			
			//System.out.println(x + ", " + y);
		if(canCastle(x,y)) {
				return true;
			}
			
			if(Math.abs(x - getPrevX()) + Math.abs(y - getPrevY()) == 1 ||
					Math.abs(x - getPrevX()) * Math.abs(y - getPrevY()) == 1) {
				return true;
			}
			
			
		}
		return false;
	}
	
	public boolean canCastle(int x, int y) {
		
		//Castling short
		if(x == 6) {
			//With white
			if(this.isWhite()) {
				setCastlingRook((Rook)getPiece(7,7));
				
				if(y == 7 && !pieceOnStraightLinePath(4, 7, 7, 7) && this.isFirstMove()
						&& getCastlingRook() != null && getCastlingRook().isFirstMove()) {
					return true;
				}
			} else {
				//Black
				setCastlingRook((Rook)getPiece(7,0));
				
				if(y == 0 && !pieceOnStraightLinePath(4, 0, 7, 0) && this.isFirstMove()
						&& getCastlingRook() != null && getCastlingRook().isFirstMove()) {
					return true;
				}
			}
			
		}
		//Castling long
				if(x == 2) {
					//With white
					if(this.isWhite()) {
						setCastlingRook((Rook)getPiece(0,7));
						
						if(y == 7 && !pieceOnStraightLinePath(4, 7, 0, 7) && this.isFirstMove()
								&& getCastlingRook() != null && getCastlingRook().isFirstMove()) {
							return true;
						}
					} else {
						//Black
						setCastlingRook((Rook)getPiece(0,0));
						
						if(y == 0 && !pieceOnStraightLinePath(4, 0, 0, 0) && this.isFirstMove()
								&& getCastlingRook() != null && getCastlingRook().isFirstMove()) {
							return true;
						}
					}
					
				}
		return false;
	}


	public Rook getCastlingRook() {
		return castlingRook;
	}


	public void setCastlingRook(Rook castlingRook) {
		this.castlingRook = castlingRook;
	}
	
}
