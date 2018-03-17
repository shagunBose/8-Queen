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

@SuppressWarnings("serial")
public class EightQueen extends JPanel implements MouseListener, MouseMotionListener{
	static final int SIZE_OF_BOARD_IN_SQUARES = 8;
	static final int SIZE_OF_BOARD_IN_PIXELS = 400;
	static final int SIZE_OF_SQUARE_IN_PIXELS = SIZE_OF_BOARD_IN_PIXELS/SIZE_OF_BOARD_IN_SQUARES;
	
	Tile[][] tiles = new Tile[SIZE_OF_BOARD_IN_SQUARES][SIZE_OF_BOARD_IN_SQUARES]; //refer to Tile Class
	ArrayList<Queen>queens = new ArrayList<Queen>(); //array list to keep track of the queens. 
	Boolean firstQueen = true; //this is just to ensure that only the first queen is placed randomly but the rest are placed acc. to logic.
	
	//constructor
	public EightQueen() {

		System.out.println("\n>>New Board<<\n");
		System.out.println("Number of Squares: " + SIZE_OF_BOARD_IN_SQUARES);
		System.out.println("Lenght of Board: " + SIZE_OF_BOARD_IN_PIXELS);
		System.out.println("Ratio: " + SIZE_OF_SQUARE_IN_PIXELS);
		int count = 1;
		for(int i = 0; i < SIZE_OF_BOARD_IN_SQUARES; i++) {
			for(int j = 0; j < SIZE_OF_BOARD_IN_SQUARES; j++) {
				tiles[i][j] = new Tile(count);
				tiles[i][j].setRow(i);
				tiles[i][j].setCol(j);
				count++;
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
				for(int i = 0; i < SIZE_OF_BOARD_IN_SQUARES; i++) {
					for (int j = 0; j < SIZE_OF_BOARD_IN_SQUARES; j++) { //add white squares 
						g.setColor(Color.WHITE);
						int tileNumb = tiles[i][j].pos;
						if(i%2 == 0) { //every column
							if(tileNumb%2 == 0) { //each alernate tile > here it't the even tiles 
								g.fillRect(i*SIZE_OF_SQUARE_IN_PIXELS, j*SIZE_OF_SQUARE_IN_PIXELS, SIZE_OF_SQUARE_IN_PIXELS, SIZE_OF_SQUARE_IN_PIXELS); }
						}else {
							if(tileNumb%2 != 0) {
								g.fillRect(i*SIZE_OF_SQUARE_IN_PIXELS, j*SIZE_OF_SQUARE_IN_PIXELS, SIZE_OF_SQUARE_IN_PIXELS, SIZE_OF_SQUARE_IN_PIXELS); //here it's the odd tiles 
							}
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
	int row; 
	int col;
	
	//this looks at which tile# the tile is at on the 8x8 board. This is used to draw the white squares on the board.
	//It is a primarily meant for graphics. 
	int pos; 
	
	public Tile(int n) {
		pos = n;
		safe = true;
	}
	
	//getters and setters for each variable 
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isSafe() {
		return safe;
	}
	public void setSafe(boolean safe) {
		this.safe = safe;
	}
	public int getPos() {
		return pos;
	}
	public void setPos(int pos) {
		this.pos = pos;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	}
	public int getCol() {
		return col;
	}
	public void setCol(int col) {
		this.col = col;
	}
}