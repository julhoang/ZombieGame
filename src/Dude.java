import java.awt.*;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Dude {
	int x,nx,nx2,left, dy; 
	Image img;
	public boolean gameRunning;
	Board game;
	
	// a list of image
	ImageIcon still = new ImageIcon("zombie2.png");
	ImageIcon walk= new ImageIcon("walk1.png");
	ImageIcon walk2 = new ImageIcon("walk2.png");
	ImageIcon walk3 = new ImageIcon("walk3.png");
	ImageIcon fail = new ImageIcon("dead.png");
	
	public Dude() {
		x = 75;
	
		// left is the relative position of zombie on the frame
		left = 150;
		
		// nX and nX2 are the 2 ends of the background picture
		nx = 0; 
		nx2= 685; // a bit less than the length of the frame (700)
		
		dy = 0;
		img = still.getImage();
	} // constructor
	
	public void move(boolean gameRunning) {
		// if there is a request to move
		if (gameRunning == true) {
			x += 5;
			nx2+= 5;
			nx += 5;
		} // if

	} // move

	public int getX() {
		return x;
	}
	
	public int getnX() {
		return nx;
	}
	
	public int getnX2() {
		return nx2;
	}
	
	public Image getImage(boolean onGround) {
	
		// change image according to move
		
		if (x == 75) {
			img = still.getImage();
		} else if ((int)(x / 20) % 3 == 0 && onGround) {
			img = walk3.getImage();
		} else if ((int)(x / 20) % 3 == 1 && onGround){
			img = walk2.getImage();
		} else if((int)(x / 20) % 3 == 2 && onGround){
			img = walk.getImage();
		}

		if (Board.getV() + getHeight() > 405) {
			img = fail.getImage();
		}
		return img;
	} // getImage
	
	// return width of image in pixels
	public int getWidth() {
		return img.getWidth(null);
	} // getWidth

    // return height of image in pixels
	public int getHeight() {
		return img.getHeight(null);
	} // getHeight
	   
} // Dude