import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.AlphaComposite;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable{
	
	private static final long serialVersionUID = 1L;
	private Board board = new Board();
	private JFrame frame;
	public static ArrayList<Piece> pieces = new ArrayList<>();
	public static ArrayList<Piece> copyPieces = new ArrayList<>();
	final int BOARD_SIZE = 640;
	
	private boolean running = true;
	private final int FPS = 60;
	private final long targetTime = 1000 / FPS; 
	
	private Mouse mouse = new Mouse();
	private Piece activePiece, checkingPiece;
	boolean canMove;
	boolean validSquare;
	boolean takePiece;
	boolean gameOver;
	boolean whitesTurn = true;
	
	private King whiteKing, blackKing;
	
	public Game() {
		
		frame = new JFrame("Chess");
		frame.getContentPane().setPreferredSize(new Dimension(BOARD_SIZE,BOARD_SIZE));
		frame.pack(); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(this);
		
		addMouseMotionListener(mouse);
		addMouseListener(mouse);
		
		setPieces();
		
		frame.setVisible(true);
		new Thread(this).start();
	}
	
	@Override
    public void run() {
        while (running) {
            update();
            repaint();
            try {
                Thread.sleep(targetTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void update() {
    	
    	if(mouse.pressed) {
    		
    		for (Piece piece : pieces) {
        		
        		//If not currently holding a piece
        		if (activePiece == null) {
        			//Check if clicking a piece
        			if(	mouse.x >= piece.getxSquare() * board.SQUARE_SIZE &&
    					mouse.x <= piece.getxSquare() * board.SQUARE_SIZE + piece.getImage().getIconWidth() &&
    					mouse.y >= piece.getySquare() * board.SQUARE_SIZE &&
    					mouse.y <= piece.getySquare() * board.SQUARE_SIZE + piece.getImage().getIconHeight()) {
        				if (piece.isWhite() == whitesTurn) {
        					activePiece = piece;
        				}
        			}	
        		} else {
        			simulate();
        		}
    		}
    	} 

    	if (!mouse.pressed) {
    		
    		if(activePiece != null) {
    			
    		
	    			if(validSquare) {
	    				int x = activePiece.getxSquare();
	    				int y = activePiece.getySquare();
	    			
	    				//En Passant
	    				if(activePiece.type == Type.PAWN) {
	        				checkEnPassant(x, y);
	        			}
	    				
	    				//Update Position
	    				activePiece.setPrevX(x);
	        			activePiece.setPrevY(y);
	        			
	        			if(isKingInCheck() && isCheckmate()) {
	        				gameOver = true;
	        				if(whitesTurn) {
	        					JOptionPane.showMessageDialog(frame, "White wins!!");
	        				} else {
	        					JOptionPane.showMessageDialog(frame, "Black wins!!");
	        				}
	        				
	        			}
	        			
	        			//Check if it is a castling move or a promotion
	        			castleKing(x, y);
	        			promotePawn(x, y);
	   
	        			//It is no longer the pieces first move
	        			activePiece.setFirstMove(false);
	        			
	        			//Take piece 
	        			if(takePiece) {
	        				pieces.remove(activePiece.isHittingPiece(activePiece.getxSquare(), activePiece.getySquare()));
	        			}
	        			
	        			resetTwoSteppedStatus(!activePiece.isWhite());
	        			
        			
	        			//Change player turn
	        			whitesTurn = !whitesTurn;
	    			
	    			
    			} else {
    				resetPiece();
    			}	
    			
    		}
    		activePiece = null;		  		
    	}
    		
    }
    
    private void simulate() {
    	canMove = false;
    	validSquare = false;
    	takePiece = false;
    	
    	int tempX = (mouse.x) / board.SQUARE_SIZE;
		int tempY = (mouse.y) / board.SQUARE_SIZE;
		
		if (activePiece.canMove(tempX, tempY)) {
			
			canMove = true;
			validSquare = true;
			
			if(isIllegal(activePiece) || ( opponentCanCaptureKing() && !canCaptureCheckingPiece())) {
				validSquare = false;
			}
			
			if (activePiece.canTake(tempX, tempY)) {
				takePiece = true;
			}	
			
		}

		activePiece.setxSquare(tempX);
		activePiece.setySquare(tempY);

	}
    
    /*
    private boolean canCaptureWithPinnedPiece() {
    	int x = (mouse.x) / board.SQUARE_SIZE;
		int y = (mouse.y) / board.SQUARE_SIZE;
		
    	if(activePiece.canTake(x, y) && getPiece(x, y) != null) {
    		
    		pieces.add(getPiece(x, y));
    	}
    	
    	if(opponentCanCaptureKing() == false) {
    		copyList(copyPieces, pieces);
    		return true;
    	}
    	
    	copyList(copyPieces, pieces);
    	return false;
    }
    
    */
    
    private boolean canCaptureCheckingPiece() {
    	
    	int x = (mouse.x) / board.SQUARE_SIZE;
    	int y = (mouse.y) / board.SQUARE_SIZE;
    	
    	if(checkingPiece != null && activePiece.canTake(checkingPiece.getxSquare(), checkingPiece.getySquare()) 
				&& x == checkingPiece.getxSquare() && y == checkingPiece.getySquare()
				) {
		
			return true;
		}
    	
    	return false;
    }
    
    private boolean opponentCanCaptureKing() {	
    	Piece king = getKing(false);
    	
		for(Piece piece: pieces) {
			if(piece.isWhite() != king.isWhite() && piece.canMove(king.getxSquare(), king.getySquare())) {
					return true;
			}
		}
		
    	return false; 
    }
    
    private boolean isCheckmate() {
    	Piece king = getKing(true);
    	
    	if(kingCanMove(king)) {
    		return false;
    	} else {
    		
    		int colDiff = Math.abs(checkingPiece.getxSquare() - king.getxSquare());
    		int rowDiff = Math.abs(checkingPiece.getySquare() - king.getySquare());

    		if(colDiff == 0) {
    			//vertical attack
    			if(checkingPiece.getxSquare() < king.getxSquare()) {
    				for(int row = checkingPiece.getxSquare(); row < king.getxSquare(); row++) {
    					for(Piece piece: pieces) {
    						if(piece != king && piece.isWhite() != whitesTurn && piece.canMove(checkingPiece.getxSquare(), row)) {
    							return false;
    						}
    					}
    				}
    			}
    			
    			if(checkingPiece.getxSquare() > king.getxSquare()) {
    				for(int row = checkingPiece.getxSquare(); row > king.getxSquare(); row--) {
    					for(Piece piece: pieces) {
    						if(piece != king && piece.isWhite() != whitesTurn && piece.canMove(checkingPiece.getxSquare(), row)) {
    							return false;
    						}
    					}
    				}
    			}
    			 
    		} else if(rowDiff == 0) {
    			//horizontally
    			
    			if(checkingPiece.getySquare() < king.getySquare()) {
    				for(int col = checkingPiece.getySquare(); col < king.getySquare(); col++) {
    					for(Piece piece: pieces) {
    						if(piece != king && piece.isWhite() != whitesTurn && piece.canMove(col, checkingPiece.getySquare())) {
    							return false;
    						}
    					}
    				}
    			}
    			
    			if(checkingPiece.getySquare() > king.getySquare()) {
    				for(int col = checkingPiece.getySquare(); col > king.getySquare(); col--) {
    					for(Piece piece: pieces) {
    						if(piece != king && piece.isWhite() != whitesTurn && piece.canMove(col, checkingPiece.getySquare())) {
    							return false;
    						}
    					}
    				}
    			}
    			
    		} else if(colDiff == rowDiff) {
    			//diagonally
    			if(checkingPiece.getxSquare() < king.getxSquare()) {
    				if(checkingPiece.getySquare() < king.getySquare()) {
        				for(int col = checkingPiece.getySquare(), row = checkingPiece.getxSquare(); col < king.getySquare(); col++, row++) {
        					for(Piece piece: pieces) {
        						if(piece != king && piece.isWhite() != whitesTurn && piece.canMove(col, row)) {
        							return false;
        						}
        					}
        				}
        			}
    				if(checkingPiece.getySquare() > king.getySquare()) {
    					for(int col = checkingPiece.getySquare(), row = checkingPiece.getxSquare(); col > king.getySquare(); col--, row++) {
        					for(Piece piece: pieces) {
        						if(piece != king && piece.isWhite() != whitesTurn && piece.canMove(col, row)) {
        							return false;
        						}
        					}
        				}
        			}
    			}
    			
    			if(checkingPiece.getxSquare() > king.getxSquare()) {
    				if(checkingPiece.getySquare() < king.getySquare()) {
    					for(int col = checkingPiece.getySquare(), row = checkingPiece.getxSquare(); col < king.getySquare(); col++, row--) {
        					for(Piece piece: pieces) {
        						if(piece != king && piece.isWhite() != whitesTurn && piece.canMove(col, row)) {
        							return false;
        						}
        					}
        				}
        			}
    				if(checkingPiece.getySquare() > king.getySquare()) {
    					for(int col = checkingPiece.getySquare(), row = checkingPiece.getxSquare(); col > king.getySquare(); col--, row--) {
        					for(Piece piece: pieces) {
        						if(piece != king && piece.isWhite() != whitesTurn && piece.canMove(col, row)) {
        							return false;
        						}
        					}
        				}
        			} 
    			}
    		} 
    	}
    	
    	return true;
    }
    
    private boolean kingCanMove(Piece king) {
    	
    	if(isValidMove(king, -1, -1)) {return true;}
    	if(isValidMove(king, 0, -1)) {return true;}
    	if(isValidMove(king, 1, -1)) {return true;}
    	if(isValidMove(king, -1, 0)) {return true;}
    	if(isValidMove(king, -1, 1)) {return true;}
    	if(isValidMove(king, 1, 0)) {return true;}
    	if(isValidMove(king, 1, 1)) {return true;}
    	if(isValidMove(king, 0, 1)) {return true;}
    	
    	return false;
    }
    
    private boolean isValidMove(Piece king, int xPlus, int yPlus) {
    	
    	//copy Pieces
    	copyList(pieces, copyPieces);
    	
    	boolean isValidMove = false;
    	
    	int col = king.getPrevX() + xPlus;
    	int row = king.getPrevY() + yPlus;
    	//Simulate
    	king.setxSquare(col);
    	king.setySquare(row);
    	
    	if(king.canMove(king.getxSquare(), king.getySquare())) {
    		
    		if(king.getHittingPiece() != null) {
    			pieces.remove(king.getHittingPiece());
    		}
    		if(!isIllegal(king)) {
    			isValidMove = true;
    		}
    	}
    	
    	//Reset Position
    	king.setxSquare(col - xPlus);
    	king.setySquare(row - yPlus);
    	
    	copyList(copyPieces, pieces);
    	
    	return isValidMove;
    }
    
    private boolean isKingInCheck() {
        Piece king = getKing(true);
        
        for (Piece piece : pieces) {
            if (piece.isWhite() != king.isWhite() && piece.canMove(king.getxSquare(), king.getySquare())) {
                checkingPiece = piece; // Track the piece giving check
                return true;
            }
        }
        checkingPiece = null; // Reset if no piece is giving check
        return false;
    }

    private Piece getKing(boolean opponent) {
    	Piece king = null;
    	
    	for(Piece piece: pieces) {
    		if (opponent) {
    			if(piece.type == Type.KING && piece.isWhite() != whitesTurn) {
    				king = piece;
    			}
    		} else {
    			if(piece.type == Type.KING && piece.isWhite() == whitesTurn) {
    				king = piece;
    			}
    		}
    	}
    	return king;
    }

	public void resetPiece() {
    	activePiece.setxSquare(activePiece.getPrevX());
		activePiece.setySquare(activePiece.getPrevY());
    }
    
    public void promotePawn(int x, int y) {
    	if(activePiece.isWhite() && y == 0) {
    		pieces.remove(activePiece);
    		pieces.add(new Queen(true, x, y));
    	} else if(!activePiece.isWhite() && y == 7) {
    		pieces.remove(activePiece);
    		pieces.add(new Queen(false, x, y));
    	}
    }
    
    public void castleKing(int x, int y) {
    	
    	if(getWhiteKing().canCastle(x, y)) {
    		pieces.remove(getWhiteKing().getCastlingRook());
    		//White Short castle
    		if(x == 6) {
    			pieces.add(new Rook(true, 5, 7));
    		} else if (x == 2) {
    			//white long castle
    			pieces.add(new Rook(true, 3, 7));
    		}
    	}
    	
    	if(getBlackKing().canCastle(x, y)) {
    		pieces.remove(getBlackKing().getCastlingRook());
    		//Black Short castle
    		if(x == 6) {
    			pieces.add(new Rook(false, 5, 0));
    		} else if (x == 2) {
    			//black long castle
    			pieces.add(new Rook(false, 3, 0));
    		}
    	}
    	 	
    }
    
    public void checkEnPassant(int x, int y) {
    	if(((Pawn) activePiece).canEnPassant(x, y)) {
    		pieces.remove(((Pawn) activePiece).getEnPassantCapture());
    	}
    }
    
    public void resetTwoSteppedStatus(boolean white) {
    
		for(Piece piece: pieces) {
			if(piece.isWhite() == white && piece.type == Type.PAWN) {
				piece.setTwoStepped(false);
			}
		}
    	
    }
    
    public boolean isIllegal(Piece king) {
    	if(king.type == Type.KING) {
    		for(Piece piece: pieces) {
    			if(piece != king && piece.isWhite() != king.isWhite() && piece.canMove(king.getxSquare(), king.getySquare())) {
    				return true;
    			}
    		}
    	}
    	return false;
    }

	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        Graphics2D g2 = (Graphics2D)g;
        
        board.draw(g2);
        
        for (Piece piece: pieces) {
        	piece.paintComponent(g2);
        }
        
        if(activePiece != null) {
        	if (canMove) {
        		if(isIllegal(activePiece) || ( opponentCanCaptureKing() && !canCaptureCheckingPiece())) {
        			g2.setColor(Color.red);
                	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
                	g2.fillRect(activePiece.getxSquare()*board.SQUARE_SIZE, activePiece.getySquare()*board.SQUARE_SIZE, board.SQUARE_SIZE, board.SQUARE_SIZE);
                	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        		} else {
        			g2.setColor(Color.white);
                	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                	g2.fillRect(activePiece.getxSquare()*board.SQUARE_SIZE, activePiece.getySquare()*board.SQUARE_SIZE, board.SQUARE_SIZE, board.SQUARE_SIZE);
                	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        		}
        	}
      
        	activePiece.paintComponent(g2);
        } 
        if(checkingPiece != null) {
        	g2.setColor(Color.red);
        	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        	g2.fillRect(getKing(false).getPrevX()*board.SQUARE_SIZE, getKing(false).getPrevY()*board.SQUARE_SIZE, board.SQUARE_SIZE, board.SQUARE_SIZE);
        	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		}
        
    }

	
	public void setPieces() {
		
		//White Pieces
		pieces.add(new Pawn(true, 0, 6));
		pieces.add(new Pawn(true, 1, 6));
		pieces.add(new Pawn(true, 2, 6));
		pieces.add(new Pawn(true, 3, 4));
		pieces.add(new Pawn(true, 4, 4
				));
		pieces.add(new Pawn(true, 5, 6));
		pieces.add(new Pawn(true, 6, 6));
		pieces.add(new Pawn(true, 7, 6));
		pieces.add(new Rook(true, 0, 7));
		pieces.add(new Rook(true, 7, 7));
		pieces.add(new Knight(true, 1, 7));
		pieces.add(new Knight(true, 6, 7));
		pieces.add(new Bishop(true, 2, 7));
		pieces.add(new Bishop(true, 5, 7));
		pieces.add(new Queen(true, 3, 7));
		
		setWhiteKing(new King(true, 4, 7));
		pieces.add(getWhiteKing());
		
		
		//Black Pieces
		pieces.add(new Pawn(false, 0, 1));
		pieces.add(new Pawn(false, 1, 1));
		pieces.add(new Pawn(false, 2, 1));
		pieces.add(new Pawn(false, 3, 3));
		pieces.add(new Pawn(false, 4, 3));
		pieces.add(new Pawn(false, 5, 1));
		pieces.add(new Pawn(false, 6, 1));
		pieces.add(new Pawn(false, 7, 1));
		pieces.add(new Rook(false, 0, 0));
		pieces.add(new Rook(false, 7, 0));
		pieces.add(new Knight(false, 1, 0));
		pieces.add(new Knight(false, 6, 0));
		pieces.add(new Bishop(false, 2, 0));
		pieces.add(new Bishop(false, 5, 0));
		pieces.add(new Queen(false, 3, 0));
		
		setBlackKing(new King(false, 4, 0));
		pieces.add(getBlackKing());
	
	}
	
	public void copyList(ArrayList<Piece> original, ArrayList<Piece> target) {
		target.clear();
		for(int i = 0; i < original.size(); i ++) {
			target.add(original.get(i));
		}
		
	}
	
	public Piece getPiece(int x, int y) {
    	for (Piece piece: pieces) {
    		if (piece.getxSquare() == x && piece.getySquare() == y) {
    			return piece;
    		}
    	}
    	return null;
	 }

	public King getWhiteKing() {
		return whiteKing;
	}

	public void setWhiteKing(King whiteKing) {
		this.whiteKing = whiteKing;
	}

	public King getBlackKing() {
		return blackKing;
	}

	public void setBlackKing(King blackKing) {
		this.blackKing = blackKing;
	}
	
	// Main
		public static void main(String[] args) {
			new Game();
			
		}

}
