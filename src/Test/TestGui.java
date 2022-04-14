package Test;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import Production.SosGui;

public class TestGui{
	private SosGui gui;
	
	@Before
	public void setUp() throws Exception {
		gui = new SosGui();
	}
	
	@After
	public void tearDown() throws Exception {
	}
	
	//AC 1.1 Valid board Size, number between 3-10
	@Test 
	public void validBoardSize() { 
		assertTrue(gui.validSize(5)==true);
	}
	
	//AC 1.2 Invalid board Size, number less than 3 or greater than 10
	@Test 
	public void invalidBoardSize() { 
        assertEquals(gui.validSize(2), false);
        assertEquals(gui.validSize(15), false);
	}
}