package worms.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WormTest {

	/**
	 * Runs once before entry testClass
	 * @throws Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	private Worm testWorm, noActionPoints;
	
	/**
	 * Creates worms with:
	 * X-coordinate: 	0				0
	 * Y-coordinate: 	0				0
	 * Direction: 		1				1
	 * Radius:			0.5				0.5
	 * Mass:			556.0619		556.0619
	 * ActionPoints:	556				300
	 * Name:			Test			Test
	 */
	@Before
	public void setUp() throws Exception {
		testWorm = new Worm(0,0,1,0.5,"Test");
		noActionPoints = new Worm(0,0,1,0.5,"Test");
		noActionPoints.setActionPoints(0);
	}


	@Test
	public final void constructor_SingleCase() {
		Worm newWorm = new Worm(10,20,Math.PI,0.75,"Jason");
		assertEquals(10, newWorm.getXCoordinate(),0.00001);
		assertEquals(20, newWorm.getYCoordinate(),0.00001);
		assertEquals(Math.PI, newWorm.getDirection(),0.00001);
		assertEquals(1876.708911, newWorm.getMass(),0.00001);
		assertEquals(1877, newWorm.getActionPoints());
		assertEquals("Jason", newWorm.getName());	
	}
	
	//DIRECTION
	@Test
	public final void isValidDirection_TrueCase(){
		assertTrue(Worm.isValidDirection(1));
	}
	@Test
	public final void isValidDirection_FalseCase(){
		assertFalse(Worm.isValidDirection(7));
	}
	@Test
	public final void isValidDirection_TrueCase_Boundry(){
		assertTrue(Worm.isValidDirection(0));
	}
	@Test
	public final void isValidDirection_FalseCase_Boundry(){
		assertFalse(Worm.isValidDirection(2*Math.PI));
	}
	@Test
	public final void setDirection_Mod(){
		testWorm.setDirection(2*Math.PI);
		assertEquals(0,testWorm.getDirection(),0.00001);
	}
	
	//RADIUS
	@Test
	public final void isValidRadius_TrueCase(){
		assertTrue(Worm.isValidRadius(1));
	}
	@Test
	public final void isValidRadius_FalseCase(){
		assertFalse(Worm.isValidRadius(0.1));
	}
	@Test
	public final void isValidRadius_TrueCase_Boundry(){
		assertTrue(Worm.isValidRadius(0.25));
	}
	
	//NAME
	@Test
	public final void isValidName_TrueCase(){
		assertTrue(Worm.isValidName("John O' Shay"));
	}
	@Test
	public final void isValidName_FalseCase_ToShort(){
		assertFalse(Worm.isValidName("J"));
	}
	@Test
	public final void isValidName_FalseCase_Numbers(){
		assertFalse(Worm.isValidName("John2703"));
	}
	@Test
	public final void isValidName_FalseCase_FirstNoCapital(){
		assertFalse(Worm.isValidName("john O' Shay"));
	}
	
	//ACTIONPOINTS
	@Test
	public final void isValidNbActionPoints_TrueCase(){
		assertTrue(testWorm.isValidNbActionPoints(450));
	}
	@Test
	public final void isValidNbActionPoints_FalseCase(){
		assertFalse(testWorm.isValidNbActionPoints(560));
	}	
	@Test
	public final void isValidNbActionPoints_TrueCase_UpperBoundry(){
		assertTrue(testWorm.isValidNbActionPoints(556));
	}
	@Test
	public final void isValidNbActionPoints_TrueCase_LowerBoundry(){
		assertTrue(testWorm.isValidNbActionPoints(0));
	}
	@Test
	public final void setActionPoints_ToMany(){
		noActionPoints.setActionPoints(1000);
		assertEquals(556,noActionPoints.getActionPoints());		
	}
	@Test
	public final void setActionPoints_Negative(){
		noActionPoints.setActionPoints(-200);
		assertEquals(0,noActionPoints.getActionPoints());		
	}
	
	//TURN
	@Test
	public final void canTurn_TrueCase(){
		assertTrue(testWorm.canTurn(Math.PI));
	}
	@Test
	public final void canTurn_FalseCase(){
		assertFalse(noActionPoints.canTurn(Math.PI));
	}
	@Test
	public final void canTurn_FalseCase_Boundry(){
		assertFalse(testWorm.canTurn(18.5334*Math.PI));
	}
	@Test
	public final void turn_singleCase(){
		testWorm.turn(3*Math.PI);
		assertEquals(556-90,testWorm.getActionPoints());
	}
	
	//MOVEMENT
	@Test
	public final void canMove_TrueCase(){
		assertTrue(testWorm.canMove(15));
	}
	@Test
	public final void canMove_FalseCase(){
		assertFalse(noActionPoints.canMove(15));
	}
	@Test
	public final void canMove_FalseCase_Boundry(){
		assertFalse(testWorm.canMove(143));
	}
	@Test
	public final void move_singleCase(){
		testWorm.move(50);
		assertEquals(556-196,testWorm.getActionPoints());
	}	
}
