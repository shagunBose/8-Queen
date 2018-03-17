import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class Queen implements MouseListener, MouseMotionListener{
	
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

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub	
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}
