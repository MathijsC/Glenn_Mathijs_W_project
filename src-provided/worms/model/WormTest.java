package worms.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WormTest {

	private static Worm noActionPoints;
	/**
	 * Set up n immutable test fixture.
	 * 
	 * @post   The variable noActionPoints references a new
	 *         worm with: X-Coordinate 0, Y-Coordinate 0, Direction 1, Radius 0.5,
	 *         Mass 556.0919, ActionPoints 0 and Name No Action Points.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		noActionPoints = new Worm(0,0,1,0.5,"No Action Points");
		noActionPoints.jump();		
	}

	private Worm testWorm, noDirection;

	/**
	 * Set up a mutable test fixture.
	 * 
	 * @post   The variable testWorm references a new
	 *         worm with: X-Coordinate 0, Y-Coordinate 0, Direction 1, Radius 0.5,
	 *         Mass 556.0919, ActionPoints 556 and Name Test.
	 *         worm with: X-Coordinate 0, Y-Coordinate 0, Direction 0, Radius 0.5,
	 *         Mass 556.0919, ActionPoints 556 and Name No Direction.
	 */
	@Before
	public void setUp() throws Exception {
		testWorm = new Worm(0,0,1,0.5,"Test");
		noDirection = new Worm(0,0,0,0.5,"No Direction");
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
	public final void isValidDirection_TrueCase_Boundary(){
		assertTrue(Worm.isValidDirection(0));
	}
	@Test
	public final void isValidDirection_FalseCase_Boundary(){
		assertFalse(Worm.isValidDirection(2*Math.PI));
	}
	@Test
	public final void setDirection_Mod_Pos(){
		noDirection.turn(3*Math.PI);
		assertEquals(Math.PI,noDirection.getDirection(),0.00001);
	}
	@Test
	public final void setDirection_Mod_Neg(){
		noDirection.turn((-3)*Math.PI);
		assertEquals(Math.PI,noDirection.getDirection(),0.00001);
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
	public final void isValidRadius_TrueCase_Boundary(){
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
	public final void isValidNbActionPoints_TrueCase_UpperBoundary(){
		assertTrue(testWorm.isValidNbActionPoints(556));
	}
	@Test
	public final void isValidNbActionPoints_TrueCase_LowerBoundary(){
		assertTrue(testWorm.isValidNbActionPoints(0));
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
	public final void turn_SingleCase(){
		testWorm.turn(3*Math.PI);
		assertEquals(556-30,testWorm.getActionPoints());
		assertEquals((1+3*Math.PI) % (Math.PI * 2),testWorm.getDirection(),0.00001);
	}
	@Test
	public final void turn_SingleCase1(){
		testWorm.turn(5*Math.PI);
		assertEquals(556-30,testWorm.getActionPoints());
		assertEquals((1+5*Math.PI) % (Math.PI * 2),testWorm.getDirection(),0.00001);
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
	public final void canMove_FalseCase_Boundary(){
		assertFalse(testWorm.canMove(143));
	}
	@Test
	public final void move_SingleCase(){
		testWorm.move(50);
		assertEquals(556-196,testWorm.getActionPoints());
		//TEST DE COORDINATEN!!!!!!
	}
	
	//JUMP
	@Test
	public final void canJump_TrueCase(){
		assertTrue(testWorm.canJump());
	}
	@Test
	public final void canJump_FalseCase_ActionPoints(){
		assertFalse(noActionPoints.canJump());
	}
	@Test
	public final void canJump_FalseCase_Direction(){
		testWorm.turn(Math.PI*1.5);
		assertFalse(testWorm.canJump());
	}
	@Test
	public final void canJump_FalseCase_Direction_UpperBoundary(){
		noDirection.turn(Math.PI);
		assertFalse(noDirection.canJump());
	}
	@Test
	public final void canJump_FalseCase_Direction_LowerBoundary(){
		assertFalse(noDirection.canJump());
	}
}
