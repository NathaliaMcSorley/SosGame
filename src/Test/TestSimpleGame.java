package Test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Production.SimpleGame;
import Production.SosBoard;
import Production.SosBoard.Cell;
import Production.SosBoard.GameState;
import Production.SosGui;

public class TestSimpleGame {
	private SosBoard game;
	int size = 4;
	
	@Before
	public void setUp() throws Exception {
		game = new SimpleGame(size);
        new SosGui();
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	//AC 4.1 Valid move in an ongoing simple game
	@Test 
	public void validMove() {
		game.setTurn('B'); //S isSelected() for both blue and red 
		game.makeMove(1, 0); //S blue
		game.makeMove(1, 1); //S red

		assertTrue(game.getTurn()=='B');
		assertTrue(game.getCell(1, 2)==Cell.EMPTY); 
		
		game.makeMove(1, 2); //S blue
		assertTrue(game.getCell(1, 2)==Cell.S); 
		assertTrue(game.getTurn()=='R');
	}

	//AC 4.2 Invalid move in an ongoing simple game 
	@Test  
	public void invalidMove() {
		game.setTurn('B'); //S isSelected() for both blue and red 
		game.makeMove(1, 0); //S blue
		game.makeMove(1, 1); //S red

		assertTrue(game.getTurn()=='B');
		assertTrue(game.getCell(1,1)!=Cell.EMPTY);
		
		SosGui.blueO.setSelected(true);
		game.makeMove(1, 1);  //O blue
		assertTrue(game.makeMove(1, 1)==false);
		assertTrue(game.getCell(1, 1)==Cell.S);
		assertTrue(game.getTurn()=='B');
	}

	//AC 5.1 A decisive simple game 
	@Test 
	public void blueWin() {
		game.setTurn('B'); //S isSelected() for both blue and red 
		game.makeMove(0, 0); //S blue 		
		game.makeMove(0, 2); //S red 		
		game.makeMove(2, 0); //S blue 		
		game.makeMove(2, 2); //S red 

		assertTrue(game.getTurn()=='B');
		assertTrue(game.checkSos(2, 2)==0);
		assertTrue(game.getGameState()==GameState.PLAYING);
		assertTrue(game.getCell(0, 1)==Cell.EMPTY);

		SosGui.blueO.setSelected(true);
		game.makeMove(0, 1); //O blue 
		assertTrue(game.checkSos(0, 1)==1);
		assertTrue(game.getCell(0, 1)==Cell.O); 
		assertTrue(game.getGameState()==GameState.B_WON);		
	}
	
	//AC 5.1 A decisive simple game 
	@Test
	public void redWin() {
		game.setTurn('B'); //S isSelected() for both blue and red 
		game.makeMove(0, 0); //S blue 		
		game.makeMove(0, 2); //S red 
		game.makeMove(2, 0); //S blue 		
		game.makeMove(2, 2); //S red 
		game.makeMove(0, 1); //S blue 
		
		assertTrue(game.getTurn()=='R');
		assertTrue(game.checkSos(0, 1)==0);
		assertTrue(game.getGameState()==GameState.PLAYING);
		assertTrue(game.getCell(1, 0)==Cell.EMPTY);
		
		SosGui.redO.setSelected(true);
		game.makeMove(1, 0); //O red
		assertTrue(game.checkSos(1, 0)==1);
		assertTrue(game.getCell(1, 0)==Cell.O);
		assertTrue(game.getGameState()==GameState.R_WON);		
	}
	
	//AC 5.2 A draw simple game
	@Test 
	public void draw() {
		game.setTurn('B'); //S isSelected() for both blue and red 
		game.makeMove(0, 0); //S blue
		game.makeMove(0, 1); //S red
		game.makeMove(0, 2); //S blue
		game.makeMove(0, 3); //S red
		game.makeMove(1, 0); //S blue
		game.makeMove(1, 1); //S red
		game.makeMove(1, 2); //S blue
		game.makeMove(1, 3); //S red
		game.makeMove(2, 0); //S blue
		game.makeMove(2, 1); //S red
		game.makeMove(2, 2); //S blue
		game.makeMove(2, 3); //S red
		game.makeMove(3, 0); //S blue
		game.makeMove(3, 1); //S red
		game.makeMove(3, 2); //S blue
		
		assertTrue(game.getTurn()=='R');
		assertTrue(game.checkSos(3, 2)==0);
		assertTrue(game.getGameState()==GameState.PLAYING);
		assertTrue(game.getCell(3, 3)==Cell.EMPTY);

		game.makeMove(3, 3); //S red 
		assertTrue(game.checkSos(3, 3)==0);
		assertTrue(game.getGameState()==GameState.DRAW);
	}

	//AC 5.3 A continuing simple game
	@Test
	public void continuePlaying() {
		game.setTurn('B'); //S isSelected() for both blue and red 
		game.makeMove(0, 0); //S blue 		
		game.makeMove(0, 1); //S red 
		
		assertTrue(game.getTurn()=='B');
		assertTrue(game.getGameState()==GameState.PLAYING);
		assertTrue(game.getCell(1, 0)==Cell.EMPTY);
		assertTrue(game.getCell(1, 1)==Cell.EMPTY);

		game.makeMove(1, 0); //S blue
		assertTrue(game.getCell(1, 0)==Cell.S);
		assertTrue(game.checkSos(1, 0)==0);
		assertTrue(game.getGameState()==GameState.PLAYING);
		assertTrue(game.getTurn()=='R');
	}
}