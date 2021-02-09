import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.*;

public class Board extends JPanel implements ActionListener, Runnable {
	Dude p;
	Bomb b;
	MakeRoads table = new MakeRoads(700, 450);
	
	int counter = 0;
	int jump = 0;
	int fall = 0;
	int record = 0;
	
	public Image img; // background image 
	Timer time;
	public static int v = 150; // y-position of zombie, will change accordingly
	Thread animator;

	boolean a = false;
	boolean done2 = false; // related to jumping activity
	
	public boolean restart = false;
	public boolean gameRunning = false;
	public boolean hitTarget = false;
	public boolean onGround = false;
	
	public ArrayList<Bomb> missile;
	
	public Board() {
		p = new Dude();
		
		missile = new ArrayList();
		
		addKeyListener(new AL());
		setFocusable(true);
		
		// create background image
		ImageIcon i = new ImageIcon("background2.jpg");
		img = i.getImage();
		time = new Timer(5, this); // update image every 5ms
		time.start(); // call ActionPerform method		
	}
	
	
	public void newGame() {
		gameRunning = false;
		//addKeyListener(new AL());
		a = false;
		done2 = false;
		hitTarget = false;
		onGround = false;
		v = 150;
		counter = 0;
		jump = 0;
		p = new Dude();
		missile = new ArrayList();
		table = new MakeRoads(700, 450);
		p.nx = 0;
		p.nx2 = 685;
		p.left = 150;
		p.x = 75;
		time = new Timer (5, this);
		time.start();

		repaint();
	}
	
	// THIS IS THE GAME LOOP
	public void actionPerformed(ActionEvent e) {
			
		collidesWith();
		
		p.move(gameRunning);
		
		
		if (p.x % 700 < 5) {
			missile.add(new Bomb(p.x + 500));		
		}
		
		// moving missile
		for (int w = 0; w < missile.size(); w++) {
             Bomb b = (Bomb) missile.get(w);
             if (b.getVisible() == true) {
                 b.move();
             } else {
            	 missile.remove(w);
             }
        }
		
		repaint();
		
	} // actionPerformed
	
	
	
	// PAINT METHOD
	public void paint(Graphics g) {
		
		//p.move(gameRunning);
		
		if ((p.nx == 0 || p.nx2 == 0) && p.x != 75) {
			return;
		}
		
		if (p.dy == 1 && done2 == false && gameRunning == true) {
			done2 = true;
			animator = new Thread(this);
			animator.start();
		}
	
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g; 
		
		// -------------------
		// draw background
		if ((p.getX() - 590) % 2400 == 0) {
			p.nx = 0;
		}
            
        if ((p.getX() - 1790) % 2400 == 0) {
        	p.nx2 = 0;
        }
            
        g2d.drawImage(img, 685 - p.nx2, 0, null);
        
        if (p.getX() > 590) {
            g2d.drawImage(img, 685 - p.nx, 0, null);
        }
        
        gravity();
        wall();
   
	    // -------------------
        // This session is for printing missile
       
        
        for (int w = 0; w < missile.size(); w++)
        {
            //This is how to get a current element in an arrayList
            //similar to x[2] in a normal array
             Bomb b = (Bomb) missile.get(w);//draw:s
             g2d.drawImage(b.getImage(), b.getX(), b.getY(), null);
        }
        
        
        
        // -------------------
        // This session is for printing the "road"
   
    // CAN YOU MAKE IT SO THAT THE FIRST 10 SECONDS IS KINDA FLAT?
    // OFTENLY THE GAME STARTS OUT TOO HARD AND THE PLAYER WILL LOSE RIGHT AWAY
        g2d.setColor(new Color(0, 0, 0));
        for(int i = 0; i < table.getRows(); i++) {
        	for(int j = 0; j < table.getColumns(); j ++) {
        		if(table.getTable()[i][j] == 1) {
        			Block temp = new Block(j * 50 - 100, i * 50, "road.png");
        			g2d.drawImage(temp.getImage(), j * 50 - 2 * (counter % 25), i * 50 - 40, null);
        		}
        	}
        }
        
        
        
        // -------------------
        // This session is for printing the zombie
        // if the zombie hasn't touched the very bottom, print it's true position
        
        if (v + p.getHeight() < 410) {
        	g2d.drawImage(p.getImage(onGround), p.left, v, null);
        }
        
        // if the zombie hit the bomb, ends game
        if (hitTarget) {
        	die(g2d);	        
	        return;
        }
        
        // if the zombie has touched the very bottom, print it at the bottom
        if (v + p.getHeight() > 410) {
        	v = 360;
        	die(g2d);		        
	        return;
		}
        
        // if the zombie has touched the left side of the screen, ends game
        if (p.left < -10) {
        	die(g2d);	
	        return;
        }
        
        // prints score on the corner
        if (gameRunning == true) {
        	g2d.setFont(new Font("Arial",Font.BOLD, 20));
	        g2d.setColor(Color.BLACK);
	        g2d.drawString("Distance travelled: " + (p.x - 75), 450, 20);
       
	        counter++;
        }
        
        
        if(counter % 25 == 0 && counter != 0) {
        	table.moveBoard();
        	counter = 0;
        }        
        
        if(!gameRunning) {
            g2d.setFont(new Font("Arial",Font.BOLD, 20));
                g2d.setColor(Color.GREEN);
                g2d.drawString("Try to get as far as you can without dying!", 125, 100);
                g2d.drawString("Press the up arrow for a big jump.", 170, 125);
                g2d.drawString("Press the space bar for a small jump.", 150, 150);
                g2d.drawString("Press any key to start.", 225, 175);
            }
        
	} // paint
	
	public void die(Graphics g2d) {
		record = Math.max(record, p.x - 75);
		
		g2d.drawImage(p.getImage(onGround), p.left, v, null);
		
		g2d.setFont(new Font("Arial",Font.BOLD, 30));
        g2d.setColor(Color.GREEN);
        g2d.drawString("The record is: " + record, 200, 115);
		
    	g2d.setFont(new Font("Arial",Font.BOLD, 30));
        g2d.setColor(Color.GREEN);
        g2d.drawString("You died at: " + (p.x-75), 200, 140);
        
        g2d.setFont(new Font("Arial",Font.BOLD, 20));
        g2d.setColor(Color.RED);
        g2d.drawString("Hit 'R' to restart game.", 210, 165);
        
        gameRunning = false;
        time.stop();
	}

	//stuff here
	
	// AL - ActionListener
	private class AL extends KeyAdapter {

		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			
			if(key != KeyEvent.VK_ESCAPE && key != KeyEvent.VK_R) {
				gameRunning = true;
			}
			
			if(key == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
			
			if(key == KeyEvent.VK_R) {
				gameRunning = false;
				time.stop();
				newGame();
			}
			
			if (key == KeyEvent.VK_UP && onGround) {
				jump = 25;
				System.out.println("big");
			}
			
			if(key == KeyEvent.VK_SPACE && onGround) {
				jump = 15;
				System.out.println("small");
			}
		}
		
	} // AL
	
	boolean h = false; // move down?
	boolean done = false; // on the ground?
	
	
	// -------------------
	// to determine if zombie has hit a wall or not
	// if yes -- move the zombie backward with the block
	
	public void wall() {
		for(int i = 0; i < 9; i++) {
			for(int j = 6; j < table.getRows(); j++) {
			//	System.out.println((p.left + p.getWidth() + 50) + "  "  +  (i * 50 - (counter % 50)) + " | " + (p.left + p.getWidth()) + "  " + (i * 50 - (counter % 50)));
				if(table.getTable()[j][i] == 1 && (p.left + p.getWidth() <= i * 50 + 50 - 2 * (counter % 25) && p.left + p.getWidth() >=  i * 50 - 2 * (counter % 25)) && v + p.getHeight() > j * 50 - 40) {
					p.left -= 2;
					
				//	System.out.println("hi");
					return;
				}
			}
		}
		
		if(p.left < 150) {
			p.left++;
		}
	}
	
	// -------------------
	// to control the falling down after a jump
	
	public void gravity() {
		v -= jump;		
		
		fall++;
			
		for(int i = 0; i < 9; i++) {
			for(int j = 6; j < table.getRows(); j++) {
				if(table.getTable()[j][i] == 1 && (p.left + p.getWidth() < i * 50 + 90 - 2 * (counter % 25) && p.left + p.getWidth() >  i * 50 + 10 - 2 * (counter % 25)) && v + fall + p.getHeight() >= j * 50 - 40) {
					onGround = true;
					fall = 0;
					jump = 0;
					return;
				}
			}
		}
		
		onGround = false;
		
		v += fall;
		
	} // gravity
	
	public static int getV() {
		return v;
	}
	
	
	// Purpose: to check if zombie has collided with the bomb
	public boolean collidesWith() {
		Rectangle zombie = new Rectangle();	
		zombie.setBounds(p.left, v, p.getWidth(), p.getHeight());     
	
		try {
			for (int w = 0; w < missile.size(); w++) {
		        
	             Bomb b = (Bomb) missile.get(w);
	             Rectangle currentBomb = b.getBounds();
	             
	             if (currentBomb.intersects(zombie)) {
	            	 gameRunning = false;
	            	 hitTarget = true;
	            	 time.stop();
	            	 repaint();
	             }
	        }
		} catch (Exception e) {	}
	       
	    return gameRunning;
	} //collidesWith

	
	@Override
	public void run() {
		

		long beforeTime, timeDiff, sleep;

		beforeTime = System.currentTimeMillis();
		while (gameRunning == true) {

			gravity();

			timeDiff = System.currentTimeMillis() - beforeTime;
			sleep = 10 - timeDiff;
			sleep = 5;
			if (sleep < 0)
				sleep = 2;
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				System.out.println("interrupted");
			}

			beforeTime = System.currentTimeMillis();
		}
		done = false;
		h = false;
		done2 = false;
	}


}