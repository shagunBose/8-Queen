import java.awt.Color;

public class Queen {
	
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
