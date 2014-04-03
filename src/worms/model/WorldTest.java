package worms.model;

import java.util.Random;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class WorldTest {
	
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
	
}
