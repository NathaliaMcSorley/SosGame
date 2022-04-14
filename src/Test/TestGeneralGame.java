package Test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Production.GeneralGame;
import Production.SosBoard;
import Production.SosBoard.Cell;
import Production.SosBoard.GameState;
import Production.SosGui;

public class TestGeneralGame {
	private SosBoard game;
	int size = 3;
	
	@Before
	public void setUp() throws Exception {
		game = new GeneralGame(size);
        new SosGui();
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	//AC 6.1 Valid move in an ongoing general game without SOS sequence
	@Test 
	public void validMoveNoSos() {
		game.setTurn('B'); //S isSelected() for both blue and red 
		game.makeMove(1, 0); //S blue
		game.makeMove(1, 1); //S red

		assertTrue(game.getTurn()=='B');
		assertTrue(game.getCell(1, 2)==Cell.EMPTY); 
		
		game.makeMove(1, 2); //S blue
		assertTrue(game.getCell(1, 2)==Cell.S); 
		assertTrue(game.checkSos(1, 2)==0);
		assertTrue(game.getTurn()=='R');
	}
	
	//AC 6.2 Valid move in an ongoing general game with new SOS sequence
	@Test 
	public void validMoveWithSos() {
		game.setTurn('B'); //S isSelected() for both blue and red 
		game.makeMove(1, 0); //S blue
		SosGui.redO.setSelected(true);
		game.makeMove(1, 1); //O red 

		assertTrue(game.getTurn()=='B');
		assertTrue(game.getCell(1, 2)==Cell.EMPTY); 

		game.makeMove(1, 2); //S blue
		assertTrue(game.getCell(1, 2)==Cell.S); 
		assertTrue(game.checkSos(1, 2)==1);
		assertTrue(game.getTurn()=='B');
	}

	//AC 6.3 Invalid move in an ongoing general game
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
	
	//AC 7.1 A decisive general game 
	@Test 
	public void blueWin() {
		game.setTurn('B'); //S isSelected() for both blue and red 
		game.makeMove(0, 0); //S blue		
		game.makeMove(0, 2); //S red 		
		game.makeMove(2, 0); //S blue 		
		game.makeMove(2, 2); //S red 
		SosGui.blueO.setSelected(true);
		game.makeMove(0, 1); //O blue		
		game.makeMove(1, 0); //O blue 		
		game.makeMove(1, 1); //O blue 1,1		
		SosGui.blueS.setSelected(true);
		game.makeMove(1, 2); //S blue 
		
		assertTrue(game.getTurn()=='R');
		assertTrue(game.getCell(2, 1)==Cell.EMPTY);
		
		SosGui.redO.setSelected(true);
		game.makeMove(2, 1); //O red 
		assertTrue(game.getCell(2, 1)==Cell.O); 
		assertTrue(game.getGameState()==GameState.B_WON);		
	}
	
	//AC 7.1 A decisive general game 
	@Test
	public void redWin() {
		game.setTurn('B'); //S isSelected() for both blue and red 
		game.makeMove(0, 0); //S blue 		
		game.makeMove(0, 2); //S red 		
		game.makeMove(2, 0); //S blue		
		game.makeMove(2, 2); //S red 
		game.makeMove(0, 1); //S blue		
		SosGui.redO.setSelected(true);
		game.makeMove(1, 0); //O red 		
		game.makeMove(1, 1); //O red 		
		game.makeMove(1, 2); //O red

		assertTrue(game.getTurn()=='R');
		assertTrue(game.getCell(2, 1)==Cell.EMPTY);
		
		game.makeMove(2, 1); //O red 
		assertTrue(game.getCell(2, 1)==Cell.O); 
		assertTrue(game.getGameState()==GameState.R_WON);		
	}
	
	//AC 7.2 A draw general game with SOS sequence
	@Test 
	public void drawWithSos() {
		game.setTurn('B'); //S isSelected() for both blue and red 
		game.makeMove(0, 0); //S blue		
		game.makeMove(0, 2); //S red 
		game.makeMove(1, 0); //S blue		
		game.makeMove(1, 2); //S red 		
		game.makeMove(2, 0); //S blue		
		game.makeMove(2, 2); //S red 
		SosGui.blueO.setSelected(true);
		game.makeMove(0, 1); //O blue		
		SosGui.blueS.setSelected(true);
		game.makeMove(1, 1); //S blue
		
		assertTrue(game.getTurn()=='R');
		assertTrue(game.getCell(2, 1)==Cell.EMPTY);
		
		SosGui.redO.setSelected(true);
		game.makeMove(2, 1); //O red
		assertTrue(game.getCell(2, 1)==Cell.O); 
		assertTrue(game.getGameState()==GameState.DRAW);
	}
	
	//AC 7.3 A draw general game without SOS sequence
	@Test 
	public void drawNoSos() {
		game.setTurn('B'); //S isSelected() for both blue and red 
		game.makeMove(0, 0); //S blue 		
		game.makeMove(0, 1); //S red 
		game.makeMove(0, 2); //S blue		
		game.makeMove(1, 0); //S red		
		game.makeMove(1, 1); //S blue 
		game.makeMove(1, 2); //S red 		
		game.makeMove(2, 0); //S blue 
		game.makeMove(2, 1); //S red
		
		assertTrue(game.getTurn()=='B');
		assertTrue(game.checkSos(2, 1)==0);
		assertTrue(game.getCell(2, 2)==Cell.EMPTY);
		
		game.makeMove(2, 2); //S blue 
		assertTrue(game.checkSos(2, 2)==0);
		assertTrue(game.getCell(2, 2)==Cell.S); 
		assertTrue(game.getGameState()==GameState.DRAW);		
	}

	//AC 7.4 A continuing general game
	@Test
	public void continuePlaying() {
		game.setTurn('B'); //S isSelected() for both blue and red 
		game.makeMove(0, 0); //S blue		
		game.makeMove(0, 1); //S red 
		
		assertTrue(game.getTurn()=='B');
		assertTrue(game.checkSos(0, 1)==0);
		assertTrue(game.getCell(1, 0)==Cell.EMPTY);
		assertTrue(game.getCell(1, 1)==Cell.EMPTY);
		
		game.makeMove(1, 0); //S blue
		assertTrue(game.getCell(1, 0)==Cell.S);
		assertTrue(game.checkSos(1, 0)==0);
		assertTrue(game.getGameState()==GameState.PLAYING);
		assertTrue(game.getTurn()=='R');
	}
}