package Production;

public class GeneralGame extends SosBoard{
	
	public GeneralGame(int size){
		super(size);
	}
	
	public void checkForWin() {
		int maxPossibleMoves = size * size;
		
		if(totalMoves < maxPossibleMoves)
			currentGameState = GameState.PLAYING;
		else if (bluePoints > redPoints)
			currentGameState = GameState.B_WON;
		else if (redPoints > bluePoints)
			currentGameState = GameState.R_WON;
		else 
			currentGameState = GameState.DRAW;
	}
}