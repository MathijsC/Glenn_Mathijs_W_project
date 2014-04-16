package worms.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class WormTest {

	private static Worm noActionPoints;
	private static Random random;
	private static World world;
	private static boolean[][] passableMap = new boolean[][] {
			{ false, false, false, false, false }, 
			{ true, true, true, true, true },
			{ true, true, false, true,true }, 
			{ true, true, true, true,true },
			{ false, false, false, false,false } };
	/**
	 * Set up an immutable test fixture.
	 * 
	 * @post   The variable noActionPoints references a new
	 *         worm with: X-Coordinate 0, Y-Coordinate 0, Direction 1, Radius 0.5,
	 *         Mass 556.0919, ActionPoints 0 and Name No Action Points.
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		world = new World(5.0,5.0, passableMap, random);
		noActionPoints = new Worm(world,0,0,1,0.5,"No Action Points");
		// 0.1 is gekozen, misschien hier toch iets anders nemen ? 
		noActionPoints.jump(0.1);		
	}

	private Worm testWorm, noDirection;
	private Random random1;
	private World world1;
	private boolean[][] passableMap1 = new boolean[][] {
			{ false, false, false, false, false }, 
			{ true, true, true, true, true },
			{ true, true, false, true,true }, 
			{ true, true, true, true,true },
			{ false, false, false, false,false } };

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
		world1 = new World(5.0,5.0, passableMap1, random1);
		testWorm = new Worm(world1,0,0,1,0.5,"Test");
		noDirection = new Worm(world1,0,0,0,0.5,"No Direction");		
	}


	@Test
	public final void constructor_SingleCase() throws Exception{
		Worm newWorm = new Worm(world1,10,20,Math.PI,0.75,"Jason");
		assertEquals(10, newWorm.getXCoordinate(),0.00001);
		assertEquals(20, newWorm.getYCoordinate(),0.00001);
		assertEquals(Math.PI, newWorm.getDirection(),0.00001);
		assertEquals(1876.708911, newWorm.getMass(),0.00001);
		assertEquals(1877, newWorm.getActionPoints());
		assertEquals(1877, newWorm.getHitPoints());
		assertEquals("Jason", newWorm.getName());	
	}
	
	//DIRECTION	
	/**
	 * getDirection is implicitly tested in the constructor test and
	 * other tests to check the current direction of the worm.
	 */
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
	/**
	 * getRadius is implicitly tested in the constructor test and
	 * other tests to check the current radius of the worm.
	 */
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
	@Test
	public final void setRadius_LegalCase() throws Exception{
		testWorm.setRadius(1.25);
		assertEquals(1.25, testWorm.getRadius(),0.00001);
	}
	@Test (expected = IllegalArgumentException.class)
	public final void setRadius_InvalidRadius() throws Exception {
		testWorm.setRadius(0.1);
	}
	@Test
	public final void getMinRadius_singleCase(){
		assertEquals(0.25, Worm.getMinRadius(),0.000001);
	}
	
	//NAME
	/**
	 * getName is implicitly tested in the constructor test and
	 * other tests to check the current name of the worm.
	 */
	@Test
	public final void isValidName_TrueCase(){
		assertTrue(Worm.isValidName("John O' Shay"));
	}
	@Test
	public final void isValidName_FalseCase_ToShort(){
		assertFalse(Worm.isValidName("J"));
	}
	@Test
	public final void isValidName_TrueCase_Numbers(){
		assertTrue(Worm.isValidName("John2703"));
	}
	@Test
	public final void isValidName_FalseCase_FirstNoCapital(){
		assertFalse(Worm.isValidName("john O' Shay"));
	}
	@Test
	public final void setName_LegalCase() throws Exception{
		testWorm.setName("This is my Name");
		assertEquals("This is my Name", testWorm.getName());
	}
	@Test (expected = IllegalArgumentException.class)
	public final void setName_IllegalName() throws Exception{
		testWorm.setName("no Capital to start");
	}
	
	//MASS
	/**
	 * getMass is implicitly tested in the constructor test and
	 * other tests to check the current mass of the worm.
	 */
	@Test
	public final void calcMass_LegalCase() throws Exception{
		assertEquals(8688.46718258427, Worm.calcMass(1.25),0.000001);
	}
	@Test (expected = IllegalArgumentException.class)
	public final void calcMass_InvalidRadius() throws Exception{
		Worm.calcMass(0.05);
	}
	
	//ACTIONPOINTS
	/**
	 * getActionPoints is implicitly tested in the constructor test and
	 * other tests to check the current action points of the worm.
	 */
	@Test
	public final void canHaveAsActionPoints_TrueCase(){
		assertTrue(testWorm.canHaveAsActionPoints(450));
	}
	@Test
	public final void canHaveAsActionPoints_FalseCase(){
		assertFalse(testWorm.canHaveAsActionPoints(560));
	}	
	@Test
	public final void canHaveAsActionPoints_TrueCase_UpperBoundary(){
		assertTrue(testWorm.canHaveAsActionPoints(556));
	}
	@Test
	public final void canHaveAsActionPoints_TrueCase_LowerBoundary(){
		assertTrue(testWorm.canHaveAsActionPoints(0));
	}
	@Test
	public final void getMaxActionPoints_SingleCase(){
		assertEquals(556, testWorm.getMaxActionPoints());
	}
	
	//HITPOINTS
	/**
	 * getHitPoints is implicitly tested in the constructor test and
	 * other tests to check the current action points of the worm.
	 */
	
	@Test
	public final void canHaveHitPoints_TrueCase(){
		assertTrue(testWorm.canHaveAsHitPoints(450));
	}
	@Test
	public final void canHaveAsHitPoints_FalseCase(){
		assertFalse(testWorm.canHaveAsHitPoints(560));
	}	
	@Test
	public final void canHaveAsHitPoints_TrueCase_UpperBoundary(){
		assertTrue(testWorm.canHaveAsHitPoints(556));
	}
	@Test
	public final void canHaveAsHitPoints_TrueCase_LowerBoundary(){
		assertTrue(testWorm.canHaveAsHitPoints(0));
	}
	@Test
	public final void getMaxHitPoints_SingleCase(){
		assertEquals(556, testWorm.getMaxHitPoints());
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
	public final void turn_PositiveAngle(){
		testWorm.turn(3*Math.PI);
		assertEquals(556-30,testWorm.getActionPoints());
		assertEquals((1+3*Math.PI) % (Math.PI * 2),testWorm.getDirection(),0.00001);
	}
	@Test
	public final void turn_NegativeAngle(){
		testWorm.turn(-1*Math.PI);
		assertEquals(556-30,testWorm.getActionPoints());
		assertEquals((1-1*Math.PI+2*Math.PI) % (Math.PI * 2),testWorm.getDirection(),0.00001);
	}
	
	//MOVEMENT
	@Test
	public final void canMove_TrueCase() throws Exception{
		assertTrue(testWorm.canMove());
	}
	@Test
	public final void canMove_FalseCase() throws Exception{
		assertFalse(noActionPoints.canMove());
	}
	@Test
	public final void canMove_FalseCase_Boundary() throws Exception{
		assertFalse(testWorm.canMove());
	}
	@Test (expected = IllegalArgumentException.class)
	public final void canMove_NegativeSteps() throws Exception{
		testWorm.canMove();
	}
	/**@Test
	public final void move_LegalCase() throws Exception{
		testWorm.move();
		assertEquals(556-196,testWorm.getActionPoints());
		assertEquals(13.507557646703495,testWorm.getXCoordinate(),0.00001);
		assertEquals(21.036774620197413,testWorm.getYCoordinate(),0.00001);
	}
	@Test(expected = IllegalArgumentException.class)
	public final void move_NegativeSteps() throws Exception{
		testWorm.move();
	}
	@Test(expected = IllegalStateException.class)
	public final void move_ToManySteps() throws Exception{
		testWorm.move();
	}*/
	
	
	
	
	
	
	
	
	
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
	@Test
	public final void initialVelocity_SingleCase(){
		assertEquals(7.403046705059146, testWorm.initialVelocity(),0.00001);
	}
	// functie jump aangepast
	/*@Test
	public final void jump_LegalCase() throws Exception{
		testWorm.jump();
		assertEquals(0, testWorm.getActionPoints());
		assertEquals(5.08166773335359, testWorm.getXCoordinate(),0.00001);
		assertEquals(0, testWorm.getYCoordinate(),0.00001);
	}
	@Test (expected = IllegalStateException.class)
	public final void jump_NoActionPoints() throws Exception{
		noActionPoints.jump();
	}
	@Test
	public final void jumpTime_LegalCase() throws Exception{
		assertEquals(1.2704540289466786, testWorm.jumpTime(),0.00001);
	}
	@Test (expected = IllegalStateException.class)
	public final void jumpTime_NoActionPoints() throws Exception{
		noActionPoints.jumpTime();
	}*/
	@Test
	public final void jumpStep_LegalCase() throws Exception{
		Position coords = testWorm.jumpStep(1);
		assertEquals(3.999883205192991, coords.getXCoordinate(),0.00001);
		assertEquals(1.3261240014849731, coords.getYCoordinate(),0.00001);
	}
	@Test (expected = IllegalArgumentException.class)
	public final void jumpStep_AlreadyLanded() throws Exception{
		testWorm.jumpStep(1.3);
	}
	@Test (expected = IllegalArgumentException.class)
	public final void jumpStep_NegativeTime() throws Exception{
		testWorm.jumpStep(-1);
	}
	@Test (expected = IllegalStateException.class)
	public final void jumpStep_NoActionPoints() throws Exception{
		noActionPoints.jumpStep(1);
	}
}
