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


	/**
	 * Runs before every test
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
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
	
	@Test
	public final void isValidDirection_TrueCase(){
		assertTrue(Worm.isValidDirection(1));
	}
	@Test
	public final void isValidDirection_FalseCase(){
		assertFalse(Worm.isValidDirection(7));
	}
	@Test
	public final void isValidDirection_TrueCaseBoundry(){
		assertTrue(Worm.isValidDirection(0));
	}
	@Test
	public final void isValidDirection_FalseCaseBoundry(){
		assertFalse(Worm.isValidDirection(2*Math.PI));
	}

}
