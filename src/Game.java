import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.awt.AlphaComposite;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Game extends JPanel implements Runnable{
	
	private static final long serialVersionUID = 1L;
	private Board board = new Board();
	private JFrame frame;
	public static ArrayList<Piece> pieces = new ArrayList<>();
	final int BOARD_SIZE = 640;
	private boolean running = true;
	private final int FPS = 60;
	private final long targetTime = 1000 / FPS; 
	private Mouse mouse = new Mouse();
	private Piece activePiece;
	boolean canMove;
	boolean validSquare;
	
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
        				activePiece = piece;
        			}	
        		} else {
        			simulate();
        		}
    		}
    	} 

    	if (!mouse.pressed) {
    		
    		if(activePiece != null) {
    			
    			if(validSquare) {
    				//Update Position
    				activePiece.setPrevX(activePiece.getxSquare());
        			activePiece.setPrevY(activePiece.getySquare());
    			} else {
    				//Reset Position
    				activePiece.setxSquare(activePiece.getPrevX());
    				activePiece.setySquare(activePiece.getPrevY());
    			}	
    			
    		}
    		activePiece = null;		  		
    	}
    		
    }
    
    private void simulate() {
    	canMove = false;
    	validSquare = false;
    	
    	int tempX = (mouse.x) / board.SQUARE_SIZE;
		int tempY = (mouse.y) / board.SQUARE_SIZE;
		
		if (activePiece.canMove(tempX, tempY)) {
			validSquare = true;
			canMove = true;
		}
		activePiece.setxSquare(tempX);
		activePiece.setySquare(tempY);
		
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
        		g2.setColor(Color.white);
            	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
            	g2.fillRect(activePiece.getxSquare()*board.SQUARE_SIZE, activePiece.getySquare()*board.SQUARE_SIZE, board.SQUARE_SIZE, board.SQUARE_SIZE);
            	g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
        	}
      
        	activePiece.paintComponent(g2);
        }
    }

	
	public void setPieces() {
		
		//White Pieces
		pieces.add(new Pawn(true, 0, 6));
		pieces.add(new Pawn(true, 1, 6));
		pieces.add(new Pawn(true, 2, 6));
		pieces.add(new Pawn(true, 3, 6));
		pieces.add(new Pawn(true, 4, 6));
		pieces.add(new Pawn(true, 5, 6));
		pieces.add(new Pawn(true, 6, 6));
		pieces.add(new Pawn(true, 7, 6));
		pieces.add(new Rook(true, 0, 7));
		pieces.add(new Rook(true, 7, 7));
		pieces.add(new Knight(true, 1, 7));
		pieces.add(new Knight(true, 6, 7));
		pieces.add(new Bishop(true, 2, 7));
		pieces.add(new Bishop(true, 5, 7));
		pieces.add(new Queen(true, 4, 7));
		pieces.add(new King(true, 3, 7));
		
		
		//Black Pieces
		pieces.add(new Pawn(false, 0, 1));
		pieces.add(new Pawn(false, 1, 1));
		pieces.add(new Pawn(false, 2, 1));
		pieces.add(new Pawn(false, 3, 1));
		pieces.add(new Pawn(false, 4, 1));
		pieces.add(new Pawn(false, 5, 1));
		pieces.add(new Pawn(false, 6, 1));
		pieces.add(new Pawn(false, 7, 1));
		pieces.add(new Rook(false, 0, 0));
		pieces.add(new Rook(false, 7, 0));
		pieces.add(new Knight(false, 1, 0));
		pieces.add(new Knight(false, 6, 0));
		pieces.add(new Bishop(false, 2, 0));
		pieces.add(new Bishop(false, 5, 0));
		pieces.add(new Queen(false, 4, 0));
		pieces.add(new King(false, 3, 0));
	
	}
	
	// Main
	public static void main(String[] args) {
		new Game();
		
	}

}
