package worms.model;

import java.util.Random;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class WorldTest {
	
	private Random random;
	private World worldSmall,worldBig;
	private Worm worm1,worm2;
	private Food food1,food2;
	private Team team;
	private boolean[][] passableMapSmall = new boolean[][] {
			{ false, false, false, false, false }, 
			{ true , true , true , true , true  },
			{ true , true , false, true , true  }, 
			{ true , true , true , true , true  },
			{ false, false, false, false, false }};
	private boolean[][] passableMapBig = new boolean[][] {
			{ false, false, false, false, false, false, false, false, false, false }, 
			{ true , true , true , true , true , true , true , true , true , true  },
			{ true , true , true , true , true , true , true , true , true , true  }, 
			{ true , true , true , true , true , true , true , true , true , true  },
			{ true , true , true , true , false, true , true , true , true , true  },
			{ true , true , true , true , true , true , true , true , true , true  },
			{ true , true , true , true , true , true , true , true , true , true  },
			{ true , true , true , true , true , true , true , true , true , true  },
			{ true , true , true , true , true , true , true , true , true , true  },
			{ false, false, false, false, false, false, false, false, false, false }};
	
	@Before
	public void setUp() throws Exception {
		random = new Random(7357);
		worldSmall = new World(5.0,5.0, passableMapSmall, random);
		worldBig = new World(10.0,10.0, passableMapBig, random);
		worm1 = new Worm(worldBig);
		team = new Team(worldBig, "Testteam");
		food1 = new Food(worldBig);
		worm2 = new Worm(worldBig);
		food2 = new Food(worldBig);		
	}
	
	/*
	 * A test for the constructor and some other getters in the class World.
	 */
	@Test
	public void constructor_SingleCase(){
		World newWorld = new World(10.0, 20.0, passableMapBig, random);
		assertTrue(newWorld.getPassableMap() == passableMapBig);
		assertEquals(10.0, newWorld.getWidth(),0.001);
		assertEquals(20.0, newWorld.getHeight(),0.001);
		assertEquals(random, newWorld.getSeed());
		assertFalse(newWorld.isGameStarted());
	}
	
	//Passable and Adjacent
	@Test
	public void isPassable_TrueCase(){
		assertTrue(worldSmall.isPassable(3.5, 3,0.25));
	}
	@Test
	public void isPassable_OutsideMap(){	
		assertTrue (worldSmall.isPassable(7, 2,0.25));
	}
	@Test
	public void isPassable_OutOfBounds_Positive(){	
		assertFalse (worldSmall.isPassable(5, 5,0.25));
	}
	@Test
	public void isPassable_OutOfBounds_Negative(){	
		assertFalse (worldSmall.isPassable(-0.00001, -0.00001,0.25));
	}
	/*
	 * This test proves that isPassable is incorrect. However this method is used
	 * in the game to prevent heavy lags. The method should return false because
	 * the false element in the center of the matrix is inside the circle with the
	 * given radius.
	 */
	@Test
	public void isPassable_Bug(){	
		assertFalse (worldBig.isPassable(5, 5.5,3));
	}
	/*
	 * This test proves that isPassableCorrect works like it has to. Howereven this method
	 * isn't use in het game to prevent heavy lags.
	 */	
	@Test
	public void isPassableCorrect_Bug(){	
		assertFalse (worldBig.isPassableCorrect(5, 5.5,3));
	}
	
	
	@Test
	public void isAdjacentTerrain_TrueCaseNextTo(){
		assertTrue(worldSmall.isAdjacentTerrain(0.5, 3.5, 2.5));
	}
	@Test
	public void isAdjacentTerrain_TrueCaseSmallGapUpperBoundry(){
		assertTrue(worldSmall.isAdjacentTerrain(0.5, 3.54999, 2.5));
	}
	@Test
	public void isAdjacentTerrain_FalseCaseSmallGap(){
		assertFalse(worldSmall.isAdjacentTerrain(0.5, 3.55, 2.5));
	}
	@Test
	public void isAdjacentTerrain_FalseCase(){
		assertFalse(worldSmall.isAdjacentTerrain(1, 2.999, 2.999));
	}
	@Test
	public void isAdjacentTerrain_OutOfBounds(){	
		assertFalse(worldSmall.isAdjacentTerrain(1, 5, 5));
	}
	@Test
	public void isAdjacentTerrain_OutsideMap(){	
		assertTrue(worldSmall.isAdjacentTerrain(1, 2.5, 6));
	}
	
	//Gameplay
	/*
	 * getCurrentWorm() is implicily tested in the tests of the gameplay
	 */
	@Test
	public void startGame(){
		worldBig.startGame();
		assertTrue(worldBig.isGameStarted());
		assertEquals(worldBig.getCurrentWorm(),worm1);
	}
	@Test
	public void startNextTurn(){
		worldBig.startGame();
		worldBig.startNextTurn();
		assertEquals(worldBig.getCurrentWorm(),worm2);
	}
	@Test
	public void isGameFinished_TrueCase_singleWormLeft(){
		new Worm(worldSmall);
		assertTrue(worldSmall.isGameFinished());
	}
	@Test
	public void isGameFinished_TrueCase_singleTeamLeft(){
		new Team(worldSmall,"Team");
		new Worm(worldSmall);
		new Worm(worldSmall);
		assertTrue(worldSmall.isGameFinished());
	}
	@Test
	public void isGameFinished_FalseCase(){
		new Worm(worldSmall);
		new Team(worldSmall,"Team");		
		new Worm(worldSmall);
		assertFalse(worldSmall.isGameFinished());		
	}
	
}
