import java.util.ArrayList;
import java.util.Collections;

import edu.du.dudraw.Draw;
import edu.du.dudraw.DrawListener;
public class Maze implements DrawListener{
	enum CellValue{
		WALL , OPEN , EXPLORED 
	}

	public CellValue[][] cells;
	public int height;
	public int width;
	private Draw draw;

	
	public Maze(int x, int y) {
		draw = new Draw();
		draw.setCanvasSize(500, 500);
		//scale values so nothing is cut out
		draw.setXscale(-0.5,x+.5);
		draw.setYscale(-0.5,y+.5);
		this.width = x;
		this.height = y;
		draw.addListener(this);
		cells = new CellValue[height][width];
		
		//set all cells to wall initially
		for(int i = 0; i < cells.length;i++) {
			for (int j = 0; j < cells[i].length;j++) {
				cells[i][j] = CellValue.WALL;
			}
		}
		draw.startUpdate();

	}

	public void draw() {
		//cell array
		Cell[][] mycells = new Cell[height][width];
		for(int i = 0; i < mycells.length;i++) {
			for (int j = 0; j < mycells[i].length;j++) {
				mycells[i][j] = new Cell(j,i);
			}
		}
		for(int i = 0; i < mycells.length;i++) {
			for (int j = 0; j < mycells[i].length;j++) {
				//check if wall
				if(cells[i][j]==CellValue.WALL) {
					draw.setPenColor(0,0,0);
					draw.filledSquare(j+.5, i+.5, .5);
				}
				//check if open
				if(cells[i][j]==CellValue.OPEN) {
					draw.setPenColor(Draw.WHITE);
					draw.filledSquare(j+.5, i+.5, .5);
				}
				//check if explored
				if(cells[i][j]==CellValue.EXPLORED) {
					draw.setPenColor(Draw.GREEN);
					draw.filledSquare(j+.5, i+.5, .5);
				}

			}

		}

	}
	
	public void generateMaze() {
		//cell array
		Cell[][] mycells = new Cell[height][width];
		for(int i = 0; i < mycells.length;i++) {
			for (int j = 0; j < mycells[i].length;j++) {
				mycells[i][j] = new Cell(j,i);
			}
		}
		//stack for generating
		DLLStack<Cell> mystack = new DLLStack<Cell>();
		int x = 5;
		int y =5;
		Cell currentCell = mycells[y][x];
		cells[x][y] = CellValue.OPEN;
		mystack.push(currentCell);
		while(mystack.size()>0) {
			//get next cell
			Cell newcell = mystack.pop();
			x = newcell.column;
			y = newcell.row;
			ArrayList<Cell> neighbors = new ArrayList<Cell>();
			//check cell north
			if(y+2<height && cells[y+2][x]==CellValue.WALL) {
				if(cells[y+1][x]==CellValue.WALL) {
					Cell c = mycells[y+2][x];
					neighbors.add(c);
					cells[y+2][x]=CellValue.OPEN;
					cells[y+1][x]=CellValue.OPEN;
				}		
			}
			//check cell east
			if(x+2<width && cells[y][x+2]==CellValue.WALL) {
				if(cells[y][x+1]==CellValue.WALL) {
					Cell c = mycells[y][x+2];
					neighbors.add(c);
					cells[y][x+2]=CellValue.OPEN;
					cells[y][x+1]=CellValue.OPEN;
				}		
			}
			//check cell south
			if(y-2>=0 && cells[y-2][x]==CellValue.WALL) {
				if(cells[y-1][x]==CellValue.WALL) {
					Cell c = mycells[y-2][x];
					neighbors.add(c);
					cells[y-2][x]=CellValue.OPEN;
					cells[y-1][x]=CellValue.OPEN;
				}		
			}
			//check south west
			if(x-2>=0 && cells[y][x-2]==CellValue.WALL) {
				if(cells[y][x-1]==CellValue.WALL) {
					Cell c = mycells[y][x-2];
					neighbors.add(c);
					cells[y][x-2]=CellValue.OPEN;
					cells[y][x-1]=CellValue.OPEN;
				}		
			}
			//randomize arraylist of neighbors
			Collections.shuffle(neighbors);
			//push neighbors onto stack
			for(int i = 0; i < neighbors.size();i++) {
				mystack.push(neighbors.get(i));
			}
			
			
		}		
	}
	
	public void solveMaze() {
		//cell array
		Cell[][] mycells = new Cell[height][width];
		for(int i = 0; i < mycells.length;i++) {
			for (int j = 0; j < mycells[i].length;j++) {
				mycells[i][j] = new Cell(j,i);
			}
		}
		int x = 1;
		int y = 1;
		//goal values
		int goalx = width-2;
		int goaly = height-2;
		Cell currentCell = mycells[y][x];
		cells[y][x]=CellValue.EXPLORED;
		//queue for solving
		DLLQueue<Cell> myqueue = new DLLQueue<Cell>();
		myqueue.enqueue(currentCell);
		while(!myqueue.isEmpty()) {
			//get next cell
			Cell newcell = myqueue.dequeue();
			x = newcell.column;
			y = newcell.row;
			if(x ==goalx && y ==goaly) {
				return;
			}
			//check cell to right
			if(x+1<width &&(cells[y][x+1]==CellValue.OPEN)) {
				cells[y][x+1]=CellValue.EXPLORED;
				Cell n = mycells[y][x+1];
				myqueue.enqueue(n);
				
			}
			//check cell to left
			if(x-1>=0 &&(cells[y][x-1]==CellValue.OPEN)) {
				cells[y][x-1]=CellValue.EXPLORED;
				Cell n = mycells[y][x-1];
				myqueue.enqueue(n);
				
			}
			//check cell above
			if(y+1<height &&(cells[y+1][x]==CellValue.OPEN)) {
				cells[y+1][x]=CellValue.EXPLORED;
				Cell n = mycells[y+1][x];
				myqueue.enqueue(n);
				
			}
			//check cell below
			if(y-1>=0 &&(cells[y-1][x]==CellValue.OPEN)) {
				cells[y-1][x]=CellValue.EXPLORED;
				Cell n = mycells[y-1][x];
				myqueue.enqueue(n);
				
			}
			//draw();
			
		}
		
	}
	//cell class
	public class Cell{
		public int row;
		public int column;

		public Cell(int column,int row){
			this.column = column;
			this.row = row;

		}
	}
	@Override
	public void keyPressed(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(char c) {
		if(c=='q') {
			System.exit(0);
		}		
	}
	@Override
	public void mouseClicked(double arg0, double arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(double arg0, double arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(double arg0, double arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(double arg0, double arg1) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update() {
		draw();
		
	}


}
