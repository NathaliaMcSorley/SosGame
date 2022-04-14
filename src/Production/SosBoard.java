package Production;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class SosBoard {

    public enum Cell {EMPTY, S, O}
    protected Cell[][] grid;
    public enum GameState {PLAYING, DRAW, B_WON, R_WON}
    public static GameState currentGameState;

    protected char turn;
    protected int bluePoints, redPoints;
	protected int totalMoves;
    public int size;
    protected boolean computer;
    Random rng = new Random();
    
    public final List<int[]> redWinningPatterns;
    public final List<int[]> blueWinningPatterns;

    public SosBoard(int size) {
    	this.size = size;
        
        redWinningPatterns = new ArrayList<>();
        blueWinningPatterns = new ArrayList<>();
        initBoard();
    }

    public void initBoard() {
        grid = new Cell[size][size];

        for (int row = 0; row < size; ++row) {
            for (int col = 0; col < size; ++col) {
                grid[row][col] = Cell.EMPTY;
            }
        }
        redWinningPatterns.clear();
        blueWinningPatterns.clear();

        currentGameState = GameState.PLAYING;
        turn = 'B';
        computer = false;
        totalMoves = 0;
        bluePoints = 0;
        redPoints = 0;
    }

    public Cell getCell(int row, int column) {
        if (row >= 0 && row < size && column >= 0 && column < size) {
            return grid[row][column];
        } else {
            return null;
        }
    }

    public void setCell(int row, int column, Cell cell) {
        if (row >= 0 && row < size && column >= 0 && column < size) {
            grid[row][column] = cell;
        }
    }
    
    public char getTurn() {
        return turn;
    }	
    
    public void setTurn(char t) {
		t = turn;
	}

    public GameState getGameState() {
        return currentGameState;
    }
    
    void makeAiMove(){

		int row = rng.nextInt(grid.length);
		int col = rng.nextInt(grid.length);
		if (!makeMove(row,col))
			makeAiMove();
    }
    
    public boolean makeMove(int row, int column) {
        Cell cell = getCell(row, column);
        if(cell != Cell.EMPTY){
            JOptionPane.showMessageDialog(null, "This cell is already occupied!", "Invalid Move", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        totalMoves += 1;
        int prevBPoints = 0;
        int prevRPoints = 0;
        if (turn == 'B') {
            if (SosGui.blueS.isSelected()) {
                setCell(row, column, Cell.S);
            } else if (SosGui.blueO.isSelected()){
                setCell(row, column, Cell.O);
            }
            prevBPoints = bluePoints;
            bluePoints += checkSos(row, column);
            if (prevBPoints == bluePoints) {
        		switchTurn();
        	}
        	else if (prevBPoints < bluePoints) {
        		doNotSwitchTurn();
            	prevBPoints += bluePoints;
        	}
        } else if (turn == 'R'){
            if (SosGui.redS.isSelected()) {
                setCell(row, column, Cell.S);
            } else if (SosGui.redO.isSelected()) {
                setCell(row, column, Cell.O);
            }
            prevRPoints = redPoints;
            redPoints += checkSos(row, column);
            if (prevRPoints < redPoints) {
        		doNotSwitchTurn();
        	}
        	else if (prevRPoints == redPoints) {
        		switchTurn();
            	prevRPoints += redPoints;
        	}
        }
        updateState();                
		System.out.println("-------------------");
    	System.out.println(currentGameState);
        System.out.println("Blue Score-> "+bluePoints);
        System.out.println("Red  Score-> "+redPoints);
        return true;
    } 

    public void updateState() {
        checkForWin();
        
        if (getGameState() == GameState.PLAYING) {
        	if ((turn =='B' && (SosGui.player1 =="Computer")))
        		makeAiMove();
        }
    }
	
    public void doNotSwitchTurn() {
    	if (turn =='B')
    		turn ='B';	
    	else if (turn =='R')
    		turn ='R';
    }
    
    public void switchTurn() {
    	if (turn == 'B') 
    		turn ='R';
    	else if(turn =='R')
    		turn ='B';
    }
    
	public int checkSos(int row, int col)
	{
        //bound check/empty check
        Cell cell = getCell(row, col);
        if(cell == null || cell==Cell.EMPTY) return 0;

        List<int[]> winningPatterns = (getTurn()=='R') ? redWinningPatterns : blueWinningPatterns;

        int points = 0;
        if(cell == Cell.O){
            if(getCell(row, col-1)==Cell.S && getCell(row, col+1)==Cell.S){
                winningPatterns.add(new int[]{row, col-1, row, col+1});
                points+=1;
            }
            if(getCell(row-1, col)==Cell.S && getCell(row+1, col)==Cell.S){
                winningPatterns.add(new int[]{row-1, col, row+1, col});
                points+=1;
            }
            if(getCell(row-1, col-1)==Cell.S && getCell(row+1, col+1)==Cell.S){
                winningPatterns.add(new int[]{row-1, col-1, row+1, col+1});
                points+=1;
            }
            if(getCell(row-1, col+1)==Cell.S && getCell(row+1, col-1)==Cell.S){
                winningPatterns.add(new int[]{row-1, col+1, row+1, col-1});
                points+=1;
            }
        }
        else if(cell == Cell.S){
            if(getCell(row, col-1)==Cell.O && getCell(row, col-2)==Cell.S){
                winningPatterns.add(new int[]{row, col, row, col-2});
                points+=1;
            }
            if(getCell(row, col+1)==Cell.O && getCell(row, col+2)==Cell.S){
                winningPatterns.add(new int[]{row, col, row, col+2});
                points+=1;
            }
            if(getCell(row-1, col)==Cell.O && getCell(row-2, col)==Cell.S){
                winningPatterns.add(new int[]{row, col, row-2, col});
                points+=1;
            }
            if(getCell(row+1, col)==Cell.O && getCell(row+2, col)==Cell.S){
                winningPatterns.add(new int[]{row, col, row+2, col});
                points+=1;
            }
            if(getCell(row-1, col-1)==Cell.O && getCell(row-2, col-2)==Cell.S){
                winningPatterns.add(new int[]{row, col, row-2, col-2});
                points+=1;
            }
            if(getCell(row+1, col+1)==Cell.O && getCell(row+2, col+2)==Cell.S){
                winningPatterns.add(new int[]{row, col, row+2, col+2});
                points+=1;
            }
            if(getCell(row-1, col+1)==Cell.O && getCell(row-2, col+2)==Cell.S){
                winningPatterns.add(new int[]{row, col, row-2, col+2});
                points+=1;
            }
            if(getCell(row+1, col-1)==Cell.O && getCell(row+2, col-2)==Cell.S){
                winningPatterns.add(new int[]{row, col, row+2, col-2});
                points+=1;
            }
        }
		return points;
	}

    public abstract void checkForWin();
   
}