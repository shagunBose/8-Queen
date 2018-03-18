//Shagun Bose
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

// TODO row, col -> y,x
//TODO don't use final or static either, instead use volatile on stuff that is mutable
@SuppressWarnings("serial")
public class EightQueen extends JPanel implements MouseListener, MouseMotionListener{
	static final int SIZE_OF_BOARD_IN_SQUARES = 8;
	static final int SIZE_OF_BOARD_IN_PIXELS = 400;
	static final int SIZE_OF_SQUARE_IN_PIXELS = SIZE_OF_BOARD_IN_PIXELS/SIZE_OF_BOARD_IN_SQUARES;
	
	Tile[][] squares = new Tile[SIZE_OF_BOARD_IN_SQUARES][SIZE_OF_BOARD_IN_SQUARES]; //refer to Tile Class
	ArrayList<Queen>queens = new ArrayList<Queen>(); //array list to keep track of the queens. 
	Boolean firstQueen = true; //this is just to ensure that only the first queen is placed randomly but the rest are placed acc. to logic.
	
	//constructor
	public EightQueen() {

		System.out.println("\n>>New Board<<\n");
		System.out.println("size of board in squares: " + SIZE_OF_BOARD_IN_SQUARES);
		System.out.println("size of board in pixels: " + SIZE_OF_BOARD_IN_PIXELS);
		System.out.println("size of squares in pixels: " + SIZE_OF_SQUARE_IN_PIXELS);

		for(int y = 0; y < SIZE_OF_BOARD_IN_SQUARES; y++) {
			for(int x = 0; x < SIZE_OF_BOARD_IN_SQUARES; x++) {
				squares[x][y] = new Tile(x, y);
			}
		}
		
		repaint();
		
		//findEightQueen(0);
//		System.out.println("\nFinal Solution");
//		for(Tile i : queens) {
//			System.out.println("Queen at Tile [" + i.getRow() + ", " + i.getCol() + "]");
//		}
//		System.out.println("");
		
	}
	
	//paint component to draw board and queen. 
	@Override
	public void paintComponent(Graphics g) {
		update(g);
	}
	
	//updates board with the queens as they are placed
	public void update(Graphics g) {
		//Draw Board
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, SIZE_OF_BOARD_IN_PIXELS, SIZE_OF_BOARD_IN_PIXELS); //makes the basic black board 
				for(int y = 0; y < SIZE_OF_BOARD_IN_SQUARES; y++) {
					for (int x = 0; x < SIZE_OF_BOARD_IN_SQUARES; x++) { //add white squares 
						if(squares[x][y].isBlack()) {
							g.setColor(Color.BLACK);
							g.fillRect(y*SIZE_OF_SQUARE_IN_PIXELS, x*SIZE_OF_SQUARE_IN_PIXELS, SIZE_OF_SQUARE_IN_PIXELS, SIZE_OF_SQUARE_IN_PIXELS);
						} else if (squares[x][y].isWhite()) {
							g.setColor(Color.WHITE);
							g.fillRect(y*SIZE_OF_SQUARE_IN_PIXELS, x*SIZE_OF_SQUARE_IN_PIXELS, SIZE_OF_SQUARE_IN_PIXELS, SIZE_OF_SQUARE_IN_PIXELS);
						}
					}
				}
				
				//common
				int size = 30;
				int fillerSpace = (SIZE_OF_SQUARE_IN_PIXELS-size)/2;
				

				
				for(int i = 0; i < SIZE_OF_BOARD_IN_PIXELS; i=i+SIZE_OF_SQUARE_IN_PIXELS) {
					BufferedImage img = null;
					try {
						img = ImageIO.read(new File("queen.png"));
					} catch (IOException e) {}
					
					g.drawImage(img, (i*SIZE_OF_SQUARE_IN_PIXELS + fillerSpace), (0 + fillerSpace), 30, 30, null);
				}
				
//				for(Tile i: queens) {
//					g.setColor(Color.BLUE);
//					BufferedImage img = null;
//					try {
//						img = ImageIO.read(new File("queen.png"));
//					} catch (IOException e) {
//					}
//					g.drawImage(img, (i.getCol()*50 + 10), (i.getRow()*50 + 10), 30, 30, null);
//				}
				
				g.dispose();
	}
	
	//method to calculate which tiles would be rendered unsafe by a placed queen and set them as unsafe (Tile class has a boolean safe)
//	public Boolean calculateSafeSpots(int row) {
//		if(queens.isEmpty()) {
//			return true;
//		}
//		//boolean checked is set to false for all tiles before iteration as it is primarily there to ensure
//		//that the same tiles which is marked unsafe initially due to one queen 
//		//is not marked safe later to another queen 
//		for(int j = 0; j < SIZE_OF_BOARD_IN_SQUARES; j++) {
//			tiles[row][j].setChecked(false); 
//		}
//		
//		boolean check = false; //if there are no safe spots this will return false. 
//		for(Tile t: queens) {
//			for(int j = 0; j < SIZE_OF_BOARD_IN_SQUARES; j++) {
//				int r = t.row;
//				int c = t.col;
//				int tr = tiles[row][j].row;
//				int tc = tiles[row][j].col;
//				if(tc == c || tr == r || r-c == tr-tc || r+c == tr+tc) {
//					tiles[row][j].setSafe(false);
//					//System.out.println("tile unsafe: row " + (row +1) + " col " + (j+1)  + " by QUEEN " + (queens.indexOf(t) + 1));
//					tiles[row][j].setChecked(true); //this is to ensure that the tile is not set safe later because another queen doesn't not cross there.
//				} else {
//					if(!tiles[row][j].isChecked()) { //only if a tile has not already been marked unsafe in this iteration is it made safe. 
//						tiles[row][j].setSafe(true);
//						check = true; //if there is even one safe spot this will return true
//					}
//				}
//					
//			}
//		}
//		return check;
//	}
	
	//Recursive formula with logic 
//	public boolean findEightQueen(int row) {
//		if(calculateSafeSpots(row)) { //only if there are safe spots will the function continue, otherwise it will backtrack to previous call
//			//set queen 
//			for (int j = 0; j < SIZE_OF_BOARD_IN_SQUARES; j++) {
//				if(tiles[row][j].isSafe()) {
//					//this is only for the first queen 
//					if(row == 0 && firstQueen) {
//						Random r = new Random();
//						int col = r.nextInt(8);
//						queens.add(tiles[row][col]);
//						System.out.println("Setting tile at row " + (row +1) + " col " + (col+1) + " as Queen " + (queens.indexOf(tiles[row][col])+1));
//						firstQueen = false;
//						
//					} else {
//						//set queen at first available safe spot. 
//						queens.add(tiles[row][j]); //add queen to queen array
//						System.out.println("Setting tile at row " + (row+1) + " col " + (j+1) + " as Queen " + (queens.indexOf(tiles[row][j])+1));
//					}
//					
//		
//					if(row < 7) {
//						if(!findEightQueen(row+1)) {
//							System.out.println("\nNo more moves for row " + (row+2));
//							queens.remove(queens.size()-1); //remove queen from queen array
//							System.out.println("Backtracking to previous queen: " + "Removing queen " + (queens.size() + 1));
//							continue; //this ensures that the function looks for the NEXT available safe space. 
//							//in essence this function is not called again when backtracked, it just goes to the next iteration 
//							//of the loop of safe spots which was calculated keeping the queen up to that point
//						}
//					}
//					return true;
//				}
//			}
//		}
//		return false;
//	}

	@Override
	public void mouseClicked(MouseEvent e) {}
	
	@Override
	public void mouseDragged(MouseEvent e) {
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
	}
	
	@Override
	public void mousePressed(MouseEvent e) {	
	}
	
	@Override
	public void mouseEntered(MouseEvent e) {}
	
	@Override
	public void mouseExited(MouseEvent e) {}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	//Main class to test program.
	public static void main (String[] args) {
		JFrame frame = new JFrame("Board");
		//GUI to refresh board to that different solutions can be seen
		JButton r = new JButton("Refresh");
		frame.setSize(400, 450);
		frame.setLayout(new BorderLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(r, BorderLayout.NORTH);
		frame.add(new EightQueen(), BorderLayout.CENTER);
		frame.setVisible(true);
	
		r.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("\n>>Board Refreshed<<\n");
				frame.add(new EightQueen(), BorderLayout.CENTER);
				frame.setVisible(true);
			}
			
		});
		
	}
}

class Tile {
	
	boolean safe = true; //checks if this tile is safe (default = true)
	boolean checked = false; //used to calculate safe spots (see CalculateSafeSpots() function in EightQueen
	final int row; 
	final int col;
	
	
	public Tile(int row, int col) {
		this.row = row;
		this.col = col;
		safe = true;
	}
	public boolean isWhite() {
		return !isBlack();
	}
	public boolean isBlack() {
		int indexOfSquare = (col + row * EightQueen.SIZE_OF_BOARD_IN_SQUARES);
		return indexOfSquare % 2 == 0;
	}

}

class Queen {
	int placedAtRow;
	int placedAtColumn;
	int X_Position; 
	int Y_Position;
	boolean inFocus;
	boolean isDragging;
	boolean placed;
	boolean safe;
	
	Color color; //unsafe queen is red, safe queen is blue
	
	//constructor
	public Queen(int x, int y) {
		X_Position = x;
		Y_Position = y;
	}
}