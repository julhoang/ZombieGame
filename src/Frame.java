	import javax.swing.*;

	public class Frame {
		Dude d;
		static Board board = new Board();
		static JFrame frame;
		
		public Frame(){
			frame = new JFrame();
			frame.add(board);	
			frame.setTitle("Zombie in da City");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(700,450);
			frame.setVisible(true);
			frame.setLocationRelativeTo(null);
		}

		public static void main(String[] args){
			new Frame();
			
		}
	}