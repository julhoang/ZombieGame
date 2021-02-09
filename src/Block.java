import javax.swing.ImageIcon;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Block {
	public Board board;
	int x, y, nx2;
	int count;
	public Image img;
	
	public int speed;
	
	// String location stores image location in the computer
	public Block(int startX, int startY, String location) {
		x = startX;
		y = startY;
		speed = 1;
		ImageIcon l = new ImageIcon(location);
		img = l.getImage();	
	} // constructor
		
	public void move() {
			x-=speed;		
	} // move
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setSpeed(int a) {
		this.speed = a;
	}
	
	// return width of image in pixels
	public int getWidth() {
		return img.getWidth(null);
	} 
    
	
	public Image getImage() {
		return img;
	}
} // block