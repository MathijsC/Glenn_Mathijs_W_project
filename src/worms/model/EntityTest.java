package worms.model;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class EntityTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/*
	 * You can't create an object of the class Entity by itself because
	 * the world object does not now how to handle an object of the class Entity.
	 * We test the methods of the class Entity by creating an object of the subclass
	 * Food.
	 */
	
	private Random random1;
	private World world;
	private Food entity;
	private boolean[][] passableMap1 = new boolean[][] {
			{ false, false, false, false, false },
			{ true, true, true, true, true }, { true, true, true, true, true },
			{ true, true, true, true, true },
			{ false, false, false, false, false } };
	@Before
	public void setUp() throws Exception {
		random1 = new Random(125);
		world = new World(50, 50, passableMap1, random1);
		entity = new Food(world, 15.0, 25.0);
	}

	/*
	 * A test for the constructor and all the other getters in the class Worm.
	 */
	@Test
	public void constructor_SingleCase() {
		Food newEntity = new Food(world, 15.0, 25.0);
		assertEquals(15.0, newEntity.getXCoordinate(),0.001);
		assertEquals(25.0, newEntity.getYCoordinate(),0.001);
		assertEquals(world,newEntity.getWorld());
		assertFalse(newEntity.isTerminated());
	}
	
	//World
	@Test
	public void hasWorld_TrueCase(){
		assertTrue(entity.hasWorld());
	}
	@Test
	public void canHaveAsWorld_TrueCase(){
		assertTrue(entity.canHaveAsWorld(world));
	}
	@Test
	public void canHaveAsWorld_FalseCase(){
		assertFalse(entity.canHaveAsWorld(null));
	}

}
