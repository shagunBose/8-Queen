//Shagun Bose
//2960170
//Lab 6
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class EightQueen extends JPanel{
	
	Tile[][] tiles = new Tile[8][8]; //refer to Tile Class
	ArrayList<Tile>queens = new ArrayList<Tile>(); //array list to keep track of the queens. 
	Boolean firstQueen = true; //this is just to ensure that only the first queen is placed randomly but the rest are placed acc. to logic.
	
	//constructor
	public EightQueen() {
		System.out.println("\n>>New Board<<\n");
		int count = 1;
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				tiles[i][j] = new Tile(count);
				tiles[i][j].setRow(i);
				tiles[i][j].setCol(j);
				count++;
			}
		}
		repaint();
		
		findEightQueen(0);
		System.out.println("\nFinal Solution");
		for(Tile i : queens) {
			System.out.println("Queen at Tile [" + i.getRow() + ", " + i.getCol() + "]");
		}
		System.out.println("");
		
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
				g.fillRect(0, 0, 400, 400);
				for(int i = 0; i < 8; i++) {
					for (int j = 0; j < 8; j++) {
						g.setColor(Color.WHITE);
						int a = tiles[i][j].pos;
						if(i%2 ==0) {
							if(a%2 == 0) {
								g.fillRect(i*50, j*50, 50, 50);
							}
						}else {
							if(a%2 != 0) {
								g.fillRect(i*50, j*50, 50, 50);
							}
						}
					}
				}
				
				for(Tile i: queens) {
					g.setColor(Color.BLUE);
					BufferedImage img = null;
					try {
						img = ImageIO.read(new File("queen.png"));
					} catch (IOException e) {
					}
					g.drawImage(img, (i.getCol()*50 + 10), (i.getRow()*50 + 10), 30, 30, null);
				}
				
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
		for(int j = 0; j < 8; j++) {
			tiles[row][j].setChecked(false); 
		}
		
		boolean check = false; //if there are no safe spots this will return false. 
		for(Tile t: queens) {
			for(int j = 0; j < 8; j++) {
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
			for (int j = 0; j < 8; j++) {
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
