public class MakeRoads {
	private int[][] board;//where a block will be stored
	
	//constructor
	MakeRoads(int w, int h){		
		board = new int[h / 50][w / 50 + 4];
		
		//creates a flat world
		for(int i = 7; i < h / 50; i++) {
			for(int j = 0; j < board[i].length; j++) {
				board[i][j] = 1;
			}//for
		}//for
		
		//adds texture to the terrain 
		edit();		
	}
	
	//if a block moves off of the screen delete that block and add a new one on the other side
	public void moveBoard(){
		
		//moves everything over one
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[i].length - 1; j++) {
				board[i][j] = board[i][j + 1];
			}//for
		}//for
		
		//clears the right most column
		board[6][board[6].length - 1] = 0;
		board[7][board[7].length - 1] = 0;
		board[8][board[8].length - 1] = 0;
		
		//logic for add blocks to the now empty column
		if(board[6][board[6].length - 2] == 1) {
			int chance = (int)(Math.random() * 5);//determines how many blocks would be added
			
			//adds a random number of blocks to the right most column
			switch(chance) {
				case 4:
				case 3:
					board[6][board[6].length - 1] = 1;
				case 2:
					board[7][board[7].length - 1] = 1;
				case 1:		
					board[8][board[8].length - 1] = 1;
			}//switch
		}else if(board[7][board[7].length - 2] == 1) {
			int chance = (int)(Math.random() * 5);//determines how many blocks would be added
			
			//adds a random number of blocks to the right most column
			switch(chance) {
				case 4:
					board[6][board[6].length - 1] = 1;
				case 3:
				case 2:
					board[7][board[7].length - 1] = 1;
				case 1:		
					board[8][board[8].length - 1] = 1;
			}//switch
		}else if(board[8][board[8].length - 2] == 1) {
			int chance = (int)(Math.random() * 5);//determines how many blocks would be added
			
			//adds a random number of blocks to the right most column
			switch(chance) {
				case 4:
					board[6][board[6].length - 1] = 1;
				case 3:
					board[7][board[7].length - 1] = 1;
				case 2:
				case 1:		
					board[8][board[8].length - 1] = 1;
			}//switch
		//if there is a space in the second right most column add a random number of blocks to the right most column 
		}else { 
			if(board[8][board[8].length - 2] == 0) {
				if(board[8][board[8].length - 3] == 0) {
						board[8][board[8].length - 1] = board[8][board[8].length - 4];
					}else if((int)(Math.random() * 2) == 1){
						board[8][board[8].length - 1] = board[8][board[8].length - 3];
					}//else if
				}else if((int)(Math.random() * 2) == 1) {
					if(board[6][board[6].length - 3] == 1) {
						
						//adds a random number of blocks to the right most column
						switch((int)(Math.random() * 4)) {
							case 0:
							case 1:
								board[6][board[6].length - 1] = 1;
							case 2:
								board[7][board[7].length - 1] = 1;
							case 3:		
								board[8][board[8].length - 1] = 1;
						}//switch
					}else if(board[7][board[7].length - 3] == 1) {
						
						//adds a random number of blocks to the right most column
						switch((int)(Math.random() * 4)) {
							case 0:
								board[6][board[6].length - 1] = 1;
							case 1:
							case 2:
								board[7][board[7].length - 1] = 1;
							case 3:		
								board[8][board[8].length - 1] = 1;
						}//switch
					}else if(board[8][board[8].length - 3] == 1) {
						
						//adds a random number of blocks to the right most column
						switch((int)(Math.random() * 4)) {
							case 0:
								board[6][board[6].length - 1] = 1;
							case 1:
								board[7][board[7].length - 1] = 1;
							case 2:
							case 3:		
								board[8][board[8].length - 1] = 1;
						}//switch
					}//else if
				}//else if
			}//else
		}//moveBoard
	
	//adds texture to the terrain
	private void edit() {
		for(int i = 0; i < board[6].length; i++) {
			if(board[6][i] == 0 && board[7][i] == 1 && (int)(Math.random() * 4) == 3) {
				int l = (int)(Math.random() * 4);
				for(int j = 0; j < l; j++) {
					if(i< board[6].length) {
						board[6][i] = 1;
					}//if
					i++;
				}//for
			}else if(board[6][i] == 0 && board[7][i] == 1 && (int)(Math.random() * 4) == 3){
				int l = (int)(Math.random() * 4);
				for(int j = 0; j < l; j++) {
					if(i< board[6].length) {
						board[7][i] = 0;
					}//if
					i++;
				}//for
			}//else if
		}//for
		
		//makes gaps
		for(int i = 5; i < board[6].length; i++) {
			if((int)(Math.random() * 5) == 4) {
				int l = (int)(Math.random() * 2 + 1);
				for(int j = 0; j < l; j++) {
					if(i < board[6].length) {
						board[6][i] = 0;
						board[7][i] = 0;
						board[8][i] = 0;
					}//if
					i++;
				}//for
			}//if
		}//for
	}//edit
	
	public int[][] getTable(){
		return board;
	}
	
	public int getRows() {
		return board.length;
	}
	
	public int getColumns() {
		return board[1].length;
	}
}