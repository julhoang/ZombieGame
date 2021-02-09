import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class Bomb {
	Dude p;
	int x,y, speed;
	Image img;
	
	boolean visible;
	
	public Bomb(int startX) {
		ImageIcon newBomb = new ImageIcon("bomb.png");
		img = newBomb.getImage();
		x = startX;
	
		speed = (int) (Math.random()*8+4);
		y = (int)(Math.random()*51 + 150);
		
		visible = true;
		
	} // default constructor
	
	public void move() {
		x-= speed;
		
		// if bomb moves off the screen
		// then make it invisible --> don't print it
		if (x < 0) {
			visible = false;
		}
	} // move

	public Rectangle getBounds()
    {
        return new Rectangle(x,y, img.getWidth(null), img.getHeight(null));
    }
	
	public boolean getVisible() {
		return visible;
	}

	public Image getImage() {
		return img;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setVisisble(boolean a) {
		visible = a;
	}
	
} // Bomb