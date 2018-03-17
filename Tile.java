import javax.swing.JFrame;

public class Tile {
	
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
