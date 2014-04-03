package worms.model;

import java.util.Random;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class WorldTest {
	
	private Worm worm;
	private Random random;
	private World world;
	private boolean[][] passableMap = new boolean[][] {
			{ false, false, false, false, false }, 
			{ true, true, true, true, true },
			{ true, true, false, true,true }, 
			{ true, true, true, true,true },
			{ false, false, false, false,false } };
	
	@Before
	public void setUp() throws Exception {
		random = new Random(7357);
		world = new World(5.0,5.0, passableMap, random);
		worm = new Worm(world,0,0,1,0.5,"Worm");		
	}
	
	@Test
	public void isPassable_TrueCase(){
		assertTrue(world.isPassable(3, 3));
	}
	@Test
	public void isPassable_OutOfBounds_Positive(){	
		assertFalse (world.isPassable(5, 5));
	}
	@Test
	public void isPassable_OutOfBounds_Negative(){	
		assertFalse (world.isPassable(-0.00001, -0.00001));
	}
	@Test
	public void isAdjacentTerrain_TrueCase(){
		assertTrue(world.isAdjacentTerrain(1, 3.999, 3.999));
	}
	@Test
	public void isAdjacentTerrain_FalseCase(){
		assertFalse(world.isAdjacentTerrain(1, 2.999, 2.999));
	}
	@Test
	public void isAdjacentTerrain_OutOfBounds(){	
		assertFalse(world.isAdjacentTerrain(1, 5, 5));
	}
	@Test
	public void getAngleToCenter1(){
		System.out.println("1,1: ");
		assertEquals(Math.PI*(7.0/4.0), world.getAngleToCenter(1.25, 1.25), 0.001);
	}
	@Test
	public void getAngleToCenter2(){
		System.out.println("1,3: ");
		assertEquals(Math.PI*(1.0/4.0), world.getAngleToCenter(1.25, 3.75), 0.001);
	}
	@Test
	public void getAngleToCenter3(){
		System.out.println("3,1: ");
		assertEquals(Math.PI*(5.0/4.0), world.getAngleToCenter(3.75, 1.25), 0.001);
	}
	@Test
	public void getAngleToCenter4(){
		System.out.println("3,3: ");
		assertEquals(Math.PI*(3.0/4.0), world.getAngleToCenter(3.75, 3.75), 0.001);
	}
	@Test
	public void checkPerim(){
		System.out.println("Check Perim: ");
		double[] coord = world.checkPerimeter(0.5, 3.25, 3.25);
		System.out.println("End Perim: "+coord[0] + " | "+coord[1]);
	}
	
}
