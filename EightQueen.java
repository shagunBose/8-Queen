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
	
	Tile[][] tiles = new Tile[8][8]; //refer to Tile Class
	ArrayList<Tile>queens = new ArrayList<Tile>(); //array list to keep track of the queens. 
	Boolean firstQueen = true; //this is just to ensure that only the first queen is placed randomly but the rest are placed acc. to logic.
	int numOfSquares = 8;
	int boardLength = 400;
	int ratio = boardLength/numOfSquares;
	
	//constructor
	public EightQueen() {
		System.out.println("\n>>New Board<<\n");
		int count = 1;
		for(int i = 0; i < numOfSquares; i++) {
			for(int j = 0; j < numOfSquares; j++) {
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
				g.fillRect(0, 0, boardLength, boardLength); //makes the basic black board 
				for(int i = 0; i < numOfSquares; i++) {
					for (int j = 0; j < numOfSquares; j++) { //add white squares 
						g.setColor(Color.WHITE);
						int tileNumb = tiles[i][j].pos;
						if(i%2 == 0) { //every column
							if(tileNumb%2 == 0) { //each alernate tile > here it't the even tiles 
								g.fillRect(i*ratio, j*ratio, ratio, ratio); }
						}else {
							if(tileNumb%2 != 0) {
								g.fillRect(i*ratio, j*ratio, ratio, ratio); //here it's the odd tiles 
							}
						}
					}
				}
				
				for(int i = 0; i < boardLength; i=i+ratio) {
					BufferedImage img = null;
					try {
						img = ImageIO.read(new File("queen.png"));
					} catch (IOException e) {
					}
					int size = 30;
					int fillerSpace = (ratio-size)/2;
					g.drawImage(img, (i*ratio + fillerSpace), (0 + fillerSpace), 30, 30, null);
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
	public Boolean calculateSafeSpots(int row) {
		if(queens.isEmpty()) {
			return true;
		}
		//boolean checked is set to false for all tiles before iteration as it is primarily there to ensure
		//that the same tiles which is marked unsafe initially due to one queen 
		//is not marked safe later to another queen 
		for(int j = 0; j < numOfSquares; j++) {
			tiles[row][j].setChecked(false); 
		}
		
		boolean check = false; //if there are no safe spots this will return false. 
		for(Tile t: queens) {
			for(int j = 0; j < numOfSquares; j++) {
				int r = t.row;
				int c = t.col;
				int tr = tiles[row][j].row;
				int tc = tiles[row][j].col;
				if(tc == c || tr == r || r-c == tr-tc || r+c == tr+tc) {
					tiles[row][j].setSafe(false);
					//System.out.println("tile unsafe: row " + (row +1) + " col " + (j+1)  + " by QUEEN " + (queens.indexOf(t) + 1));
					tiles[row][j].setChecked(true); //this is to ensure that the tile is not set safe later because another queen doesn't not cross there.
				} else {
					if(!tiles[row][j].isChecked()) { //only if a tile has not already been marked unsafe in this iteration is it made safe. 
						tiles[row][j].setSafe(true);
						check = true; //if there is even one safe spot this will return true
					}
				}
					
			}
		}
		return check;
	}
	
	//Recursive formula with logic 
	public boolean findEightQueen(int row) {
		if(calculateSafeSpots(row)) { //only if there are safe spots will the function continue, otherwise it will backtrack to previous call
			//set queen 
			for (int j = 0; j < numOfSquares; j++) {
				if(tiles[row][j].isSafe()) {
					//this is only for the first queen 
					if(row == 0 && firstQueen) {
						Random r = new Random();
						int col = r.nextInt(8);
						queens.add(tiles[row][col]);
						System.out.println("Setting tile at row " + (row +1) + " col " + (col+1) + " as Queen " + (queens.indexOf(tiles[row][col])+1));
						firstQueen = false;
						
					} else {
						//set queen at first available safe spot. 
						queens.add(tiles[row][j]); //add queen to queen array
						System.out.println("Setting tile at row " + (row+1) + " col " + (j+1) + " as Queen " + (queens.indexOf(tiles[row][j])+1));
					}
					
		
					if(row < 7) {
						if(!findEightQueen(row+1)) {
							System.out.println("\nNo more moves for row " + (row+2));
							queens.remove(queens.size()-1); //remove queen from queen array
							System.out.println("Backtracking to previous queen: " + "Removing queen " + (queens.size() + 1));
							continue; //this ensures that the function looks for the NEXT available safe space. 
							//in essence this function is not called again when backtracked, it just goes to the next iteration 
							//of the loop of safe spots which was calculated keeping the queen up to that point
						}
					}
					return true;
				}
			}
		}
		return false;
	}

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
