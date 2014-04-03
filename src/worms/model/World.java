package worms.model;

import java.util.ArrayList;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

import com.sun.xml.internal.bind.v2.TODO;

public class World {

	/**
	 * Initialize this new world with a given width, height, passableMap and
	 * a random.
	 * 
	 * @param 	width
	 * 			The width for this new world.
	 * @param 	height
	 * 			The height of this new world.
	 * @param 	passableMap
	 * 			The map matrix of this new world with the position that are passable.
	 * @param 	random
	 * 			TODO
	 * @post	The width of this new world is equal to the given width.
	 * 			| new.getWidth == width;	
	 * @post	The height of this new world is equal to the given height.
	 * 			| new.getHeight == height;
	 * @effect	The passable map of this new world is set to the given passable map matrix.
	 * 			| setPassableMap(passableMap)
	 * @post	The seed
	 * 			TODO	
	 */
	public World(double width, double height, boolean[][] passableMap,
			Random random) {
		this.width = width;
		this.height = height;
		this.setPassableMap(passableMap);
		this.seed = random;

		/*int m = 300;
		int n = passableMap[0].length-1;
		for (int i = 0; i < m; i++) {
			System.out.println(i+ "|\t");
		    for (int j = 0; j < n; j++) {
		    	if (passableMap[i][j] == false ){
		    		System.out.print("1 ");
		    	}
		    	else {
		    		System.out.print("0 ");
		    	}
		    }
		    System.out.print("\n");
		}*/

	}

	/**
	 * A variable containing the width of this world.
	 */
	private final double width;

	/**
	 * A variable containing the width of this world.
	 */
	private final double height;

	/**
	 * Return the width of this world.
	 * 
	 * @return The width of this world.
	 */
	@Basic
	@Raw
	@Immutable
	public double getWidth() {
		return width;
	}

	/**
	 * Return the height of this world.
	 * 
	 * @return The height of this world.
	 */
	@Basic
	@Raw
	@Immutable
	public double getHeight() {
		return height;
	}

	/**
	 * A matrix of booleans containing information about the passability of
	 * the terrain of this world.
	 */
	private boolean[][] passableMap;

	/**
	 * Return the passable map of this world.
	 * 
	 * @return The passable map of this world.
	 */
	@Basic
	@Raw
	public boolean[][] getPassableMap() {
		return passableMap;
	}

	/**
	 * Set the passable map of this world to the given passable map matrix.
	 * 
	 * @param 	passableMap
	 * 			The map matrix of this world with the position that are passable.
	 * 			
	 * @post	The passable map matrix of this world is equal to the given passable map.
	 * 			| new.getPassableMap() == passableMap.			
	 */
	@Raw
	@Basic
	public void setPassableMap(boolean[][] passableMap) {
		this.passableMap = passableMap;
	}

	/**
	 * TODO seed
	 */
	private final Random seed;

	/**
	 * TODO seed
	 * @return
	 */
	public Random getSeed() {
		return seed;
	}

	/**
	 * A list containing all the worms who are currently in this world.
	 */
	private ArrayList<Worm> worms = new ArrayList<Worm>();

	/**
	 * Add a new worm to this world.
	 * 
	 * @param 	worm
	 * 			The worm to be added to this world.
	 * @post	The last worm added to this world is the given worm.
	 * 			TODO formeel
	 * 
	 * 
	 */
	public void addWorm(Worm worm) throws IllegalStateException {
		if (!(worm.getWorld() == this)) {
			throw new IllegalStateException();
		}
		worms.add(worm);
	}

	/**
	 * Return a list of the worms who live in this world.
	 * 
	 * @return 	The list of worms who live in this world.
	 */
	public ArrayList<Worm> getWorms() {
		return worms;
	}

	/**
	 * The index of the worm who is at turn in the list of worms.
	 */
	private int currentWormIndex;

	/**
	 * Return the index of the worm who is at turn in the list of worms.
	 * 
	 * @return 	The index of the worm who is at turn in the list of worms.
	 */
	private int getCurrentWormIndex() {
		return currentWormIndex;
	}

	/**
	 * Set the index of the worm who is at turn to the given current index.
	 * 
	 * @param 	currentWormIndex
	 * 			The index to set the current index to.
	 * @post	The new current index is equal to the given index.
	 * 			| new.getCurrentWormIndex() == currentWormIndex
	 */
	private void setCurrentWormIndex(int currentWormIndex) {
		this.currentWormIndex = currentWormIndex;
	}

	/**
	 * Get the worm who is currently at turn in this world.
	 * 
	 * @return The worm who is currently at turn in this world.
	 */
	public Worm getCurrentWorm() {
		return this.worms.get(this.getCurrentWormIndex());
	}

	/**
	 * Start the turn of the next worm.
	 * 
	 * @effect	If the last worm has finished its turn, start a new round.
	 * 			| if(last worm played)
	 * 			|	then startNextRound();
	 * @effect	Else set the next worm to the current worm.
	 * 			| else
	 * 			|	then setCurrentWormIndex(next worm index)
	 */
	public void startNextTurn() {
		if (getCurrentWormIndex() >= (worms.size() - 1))
			startNextRound();
		else
			setCurrentWormIndex(getCurrentWormIndex() + 1);

	}

	/**
	 * Start a next round of the game.
	 * 
	 * TODO check ALL
	 * @effect	All the worms in the world refresh.
	 * 			| worm.refresh()
	 * @post	The current worm is set to the first worm (index = 0) in this world.
	 * 			| new.getCurrentWormIndex == 0
	 */
	private void startNextRound() {

		for (Worm worm : worms) {
			worm.refresh();
		}
		setCurrentWormIndex(0);

	}

	/**
	 * Start a new game.
	 * 
	 * @post	The current worm is set to the first worm (index = 0) in this world.
	 * 			| new.getCurrentWormIndex == 0
	 */
	public void startGame() {
		setCurrentWormIndex(0);
	}

	/**
	 * A list containing all the teams who are curently in this world.
	 */
	private ArrayList<Team> teams = new ArrayList<Team>();

	/**
	 * Add a new team to this world.
	 * 
	 * @param 	team
	 * 			The team to be added to this world.
	 * @post	The last team added to this world is the given team.
	 * 			TODO formeel
	 * @throws	TODO
	 * 
	 */
	public void addTeam(Team team) throws IllegalStateException {
		if (!(team.getWorld() == this)) {
			throw new IllegalStateException();
		}
		teams.add(team);
	}

	/**
	 * Return a list of the teams who are in this world.
	 * 
	 * @return 	The list of teams who are in this world.
	 */
	public ArrayList<Team> getTeams() {
		return teams;
	}

	/**
	 * A list containing all the food in this world.
	 */
	private ArrayList<Food> foodList = new ArrayList<Food>();

	/**
	 * Add a new food to this world.
	 * 
	 * @param 	Food
	 * 			The food to be added to this world.
	 * @post	The last food added to this world is the given food.
	 * 			TODO formeel
	 * 
	 * 
	 */
	public void addFood(Food food) throws IllegalStateException {
		if (!(food.getWorld() == this)) {
			throw new IllegalStateException();
		}
		foodList.add(food);
	}

	/**
	 * A variable holding the projectile that is active in this world
	 */
	private Projectile projectile;

	/**
	 * Return the active projectile in this world
	 * 
	 * @return the active projectile in this world
	 */
	public Projectile getProjectile() {
		return this.projectile;
	}

	/**
	 * Sets the active projectile in this world to the given projectile
	 * 
	 * @param projectile the new projectile active in this world
	 * @Post the active projetile in this world is the given projectile
	 * 			|new.getProjectile() = projectile 
	 */
	public void setProjectile(Projectile projectile) {
		this.projectile = projectile;
	}

	/**
	 * Return a list of the food who in this world.
	 * 
	 * @return 	The list of food in this world.
	 */
	public ArrayList<Food> getFoodList() {
		return foodList;
	}

	//TODO docu and max values
	public boolean isPassable(double xCo, double yCo) {
		if ((xCo >= getWidth()) || (yCo >= getHeight()) || (yCo < 0)
				|| (xCo < 0)) {
			//System.out.println("OutOfBounds: "+xCo+" | "+yCo);
			return false;
		}
		double boxHeight = this.getHeight() / this.getPassableMap().length;
		double boxWidth = this.getWidth() / this.getPassableMap()[0].length;
		int row = (int) ((this.getPassableMap().length) - (yCo / boxHeight));
		int column = (int) ((this.getPassableMap()[0].length) - (xCo / boxWidth));
		//System.out.println("isPass: "+xCo+" | "+yCo+ " | "+ column +" | "+ row + " | "+this.getPassableMap()[row][column]);
		return this.getPassableMap()[row][column];
	}

	//TODO docu and max values
	public boolean isAdjacentTerrain(double radius, double xCo, double yCo) {
		if ((xCo >= getWidth()) || (yCo >= getHeight()) || (yCo < 0)
				|| (xCo < 0)) {
			return false;
		}
		double angle = 0;
		//System.out.println("CheckAdj:");
		while (angle < 2 * Math.PI) {
			double circleX = radius * Math.cos(angle) + xCo;
			double circleY = radius * Math.sin(angle) + yCo;
			if (!isPassable(circleX, circleY)) {
				return true;
			}
			angle += Math.PI * (1.0 / 6.0);
		}
		return false;
	}

	public double[] getRandAdjacentTerrain(double radius) {
		//new Worm(this, getWidth() / 2, getHeight() / 2, 0.2, 0.25, "Mid");
		double[] coord = {getSeed().nextDouble() * getWidth(),getSeed().nextDouble() * getHeight()};
		//double[] coord = { 16, 14 };
		new Worm(this, coord[0], coord[1], 0.2, 0.25, "Start");
		int max = 0;
		while ((max < 10) && (!isAdjacentTerrain(radius, coord[0], coord[1]))) {
			new Worm(this, coord[0], coord[1], 0.2, 0.25, "Start");
			coord[0] = getSeed().nextDouble() * getWidth();
			coord[1] = getSeed().nextDouble() * getHeight();
			//coord[0] = 16;
			//coord[1] = 14;
			//System.out.println("Perim number: "+max+ "coords: "+ coord[0]+ " | "+ coord[1]);
			max += 1;
			coord = checkPerimeter(radius, coord[0], coord[1]);
		}
		return coord;
	}

	public double[] checkPerimeter(double radius, double xCo, double yCo) {
		double angleToCenter = getAngleToCenter(xCo, yCo);
		boolean found = false;
		int max = 0;
		//System.out.println("Angle: "+angleToCenter);
		//System.out.println("Dist: "+Math.abs(getWidth()/2-xCo)+" | "+ Math.abs(getHeight()/2-yCo));
		while ((0.50 < Math.abs(getWidth() / 2 - xCo))
				&& (0.50 < Math.abs(getHeight() / 2 - yCo)) && (!found)
				&& (max < 200)) {
			//System.out.println("Step number: " + max);
			//System.out.println("Dist: "+Math.abs(getWidth()/2-xCo)+" | "+ Math.abs(getHeight()/2-yCo));
			max += 1;
			if (isAdjacentTerrain(radius, xCo, yCo)) {
				found = true;
				//System.out.println("FOUND!");
			} else {
				xCo += 0.3 * Math.cos(angleToCenter);
				yCo += 0.3 * Math.sin(angleToCenter);
				//System.out.println("New Coords: "+ xCo + " | "+ yCo);
				//new Worm(this,xCo,yCo,0.2,0.25,"Step");
			}
		}
		double[] coord = { xCo, yCo };
		return coord;
	}

	//TODO docu
	public double getAngleToCenter(double xCo, double yCo) {
		double angleToCenter;
		if (xCo < getWidth() / 2) {
			angleToCenter = Math.atan((yCo - getHeight() / 2)
					/ (xCo - getWidth() / 2));
		} else if (xCo - getWidth() / 2 == 0) {

			if (yCo - getHeight() / 2 >= 0) {
				angleToCenter = 1.5 * Math.PI;
			} else {
				angleToCenter = 0.5 * Math.PI;
			}
		} else {
			angleToCenter = Math.PI
					+ Math.atan((yCo - getHeight() / 2)
							/ (xCo - getWidth() / 2));
		}
		return angleToCenter;
	}
}
