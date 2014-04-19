package worms.model;

import java.util.ArrayList;
import java.util.Random;

import be.kuleuven.cs.som.annotate.*;
//TODO invar
/**
 * A class of worlds used in the game of worms with a height, width,
 * passable map matrix, random seed, list of worms, list of foods, 
 * list of teams, projectile, current playing worm.
 * The class also implements methods for the gameplay (start game,
 * next round,get winner,...) and to add entities and teams to the world.
 * 
 * @author 	Glenn Cools & Mathijs Cuppens
 * @version	1.25
 *
 */
public class World {

	/**
	 * Initialize this new world with a given width, height, passableMap and
	 * a random seed.
	 * 
	 * @param 	width
	 * 			The width for this new world.
	 * @param 	height
	 * 			The height of this new world.
	 * @param 	passableMap
	 * 			The map matrix of this new world with the position that are passable.
	 * @param 	random
	 * 			The random seed for this world.
	 * @post	The width of this new world is equal to the given width.
	 * 			| new.getWidth == width;	
	 * @post	The height of this new world is equal to the given height.
	 * 			| new.getHeight == height;
	 * @effect	The passable map of this new world is set to the given passable map matrix.
	 * 			| setPassableMap(passableMap)
	 * @post	The random seed of this new world is equal to the given random.
	 * 			| new.getSeed() == random	
	 */
	public World(double width, double height, boolean[][] passableMap,
			Random random) {
		this.width = width;
		this.height = height;
		this.setPassableMap(passableMap);
		this.seed = random;
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
	 * @post	The passable map matrix of this world is equal to the given passable map.
	 * 			| new.getPassableMap() == passableMap.			
	 */
	@Raw
	@Model
	private void setPassableMap(boolean[][] passableMap) {
		this.passableMap = passableMap;
	}

	/**
	 * A variable containing the random seed of this world.
	 */
	private final Random seed;

	/**
	 * Return the random seed of this world.
	 * 
	 * @return	The random seed of this world.
	 */
	@Basic
	@Raw
	public Random getSeed() {
		return seed;
	}

	/**
	 * A list containing all the worms who are currently in this world.
	 */
	private ArrayList<Worm> worms = new ArrayList<Worm>();

	/**
	 * A variable holding the projectile that is active in this world
	 */
	private Projectile projectile;

	/**
	 * A list containing all the food in this world.
	 */
	private ArrayList<Food> foodList = new ArrayList<Food>();

	/**
	 * Add the given entity to this world.
	 * 
	 * @param 	entity
	 * 			The entity do be added to this world.
	 * @post	If the given entity is a worm, the last worm added to this
	 * 			world is equal to the given entity(worm).
	 * 			|if (entity instanceof Worm)
	 * 			|	then new.getWormList().get(getWormList().size()-1) == entity
	 * @post	If the given entity is a food, the last food added to this
	 * 			world is equal to the given entity(food).
	 * 			|else if (entity instanceof Food)
	 * 			|	then new.getFoodList().get(getFoodList().size()-1) == entity
	 * @post	If the given entity is a projectile, the new projectile of
	 * 			this world is equal to the given entity(projectile)
	 * 			|else if (entity instanceof Projectile)
	 * 			|	new.getProjectile() == entity
	 * @throws 	IllegalStateException
	 * 			The world of the given entity is not this world.
	 * 			|entity.getWorld() != this
	 * @throws 	IllegalArgumentException
	 * 			If the given entity is an subobject of entity that cannot 
	 * 			be added to this world.
	 * 			|else
	 */
	@Model
	protected void addEntity(Entity entity) throws IllegalStateException,
			IllegalArgumentException {
		if (entity.getWorld() != this) {
			throw new IllegalStateException();
		}
		if (entity instanceof Worm) {
			getWormList().add((Worm) entity);
		} else if (entity instanceof Projectile) {
			this.projectile = (Projectile) entity;
		} else if (entity instanceof Food) {
			getFoodList().add((Food) entity);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Remove the given entity from this world.
	 * 
	 * @param 	entity
	 * 			The entity to be removed from this world.
	 * @post	If the given entity is a worm, the number of worms in this world
	 * 			is decremented by 1 and the worm list of this world does not contain
	 * 			the given entity(worm).
	 * 			|if (entity instanceof Worm)
	 * 			|	then new.getWormList().size() == getWormList().size() - 1
	 * 			|	then !new.getWormList().contain(entity)
	 * @effect	If the given entity is the last worm in worm the worm list, the next
	 * 			gameround starts. 
	 * 			|if (this.getCurrentWormIndex() >= getWormList().size())
	 * 			|	then startNextRound()
	 * @post	If the given entity is a food, the number of foods in this world
	 * 			is decremented by 1 and the food list of this world does not contain
	 * 			the given entity(food).
	 * 			|if (entity instanceof Food)
	 * 			|	then new.getFoodList().size() == getFoodList().size() - 1
	 * 			|	then !new.getFoodList().contain(entity)
	 * @post	If the given entity is a projectile, the projectile of this world is set
	 * 			to null.
	 * 			|else if (entity instanceof Projectile)
	 * 			|	then new.getProjectile = null
	 * @throws 	IllegalArgumentException
	 * 			If the given entity is an subobject of entity that cannot 
	 * 			be removed from this world.
	 * 			|else
	 */
	@Model
	protected void removeEntity(Entity entity) throws IllegalArgumentException{
		if (entity instanceof Worm) {
			getWormList().remove(entity);
			if (this.getCurrentWormIndex() >= getWormList().size()) {
				this.startNextRound();
			}
		} else if (entity instanceof Projectile) {
			this.projectile = null;
		} else if (entity instanceof Food) {
			foodList.remove(entity);
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Return a list of the worms who live in this world.
	 * 
	 * @return 	The list of worms who live in this world.
	 */
	public ArrayList<Worm> getWormList() {
		return worms;
	}

	/**
	 * Return the active projectile in this world
	 * 
	 * @return the active projectile in this world
	 */
	public Projectile getProjectile() {
		return this.projectile;
	}

	/**
	 * Return a list of the food who in this world.
	 * 
	 * @return 	The list of food in this world.
	 */
	public ArrayList<Food> getFoodList() {
		return foodList;
	}

	//------------TOT HIER GERAAKT, DE REST DOE IK OVER 2 UUR OFZO-----------//
	// TODO
	public Food getFoodAtIndex(int index) {
		return this.foodList.get(index);
	}

	// TODO
	public Worm getWormAtIndex(int index) {
		return worms.get(index);
	}

	// GAMEPLAY
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

	// TODO
	public boolean isGameFinished() {
		Team teamFirstWorm = getWormAtIndex(0).getTeam();
		for (Worm worm : worms) {
			if ((worm.getTeam() != teamFirstWorm)) {
				return false;
			}
		}
		if ((teamFirstWorm == null) && (getWormList().size() > 1)) {
			return false;
		}
		return true;
	}

	public String getWinnerName() {
		if (getWormList().size() == 1) {
			return getWormAtIndex(0).getName();
		} else {
			return getWormAtIndex(0).getTeam().getName();
		}

	}

	// TEAMS
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

	// TODO
	public Worm getWormHit(Projectile projectile) {
		int i = 0;
		boolean hit = false;
		Worm worm = null;
		while ((i < this.getWormList().size()) && (!hit)) {
			worm = this.getWormAtIndex(i);
			if (projectile.getPosition().distanceTo(worm.getPosition()) < projectile
					.getRadius() + worm.getRadius()) {
				hit = true;
			}
			i++;
		}
		if (hit == false) {
			worm = null;
		}

		return worm;
	}

	// TODO
	public boolean checkProjectileHitWorm(Position position, double radius) {
		int i = 0;
		boolean hit = false;
		Worm worm = null;
		while ((i < this.getWormList().size()) && (!hit)) {
			worm = this.getWormAtIndex(i);
			if ((position.distanceTo(worm.getPosition())) <= (radius + worm
					.getRadius()) && (worm != this.getCurrentWorm())) {
				hit = true;
			}
			i++;
		}

		return hit;
	}

	// TODO
	public Food getFoodEatenBy(Worm worm) {
		int i = 0;
		boolean eat = false;
		Food food = null;
		while ((i < this.getFoodList().size()) && (!eat)) {
			food = this.getFoodAtIndex(i);
			if (food.getPosition().distanceTo(worm.getPosition()) < worm
					.getRadius() + food.getRadius()) {
				eat = true;
			}
			i++;
		}
		if (eat == false) {
			food = null;
		}

		return food;
	}

	// TODO
	public boolean checkWormCanEatFood(Position position, double wormRadius) {
		int i = 0;
		boolean eat = false;
		Food food = null;
		while ((i < this.getFoodList().size()) && (!eat)) {
			food = this.getFoodAtIndex(i);
			if ((position.distanceTo(food.getPosition())) <= (wormRadius + food
					.getRadius())) {
				eat = true;
			}
			i++;
		}

		return eat;
	}
	
	//TODO check
	/**
	 * 
	 * @param pos	
	 * 				The position of the entity that needs to be checked if it is in this world
	 * @param radius
	 * 				The radius of the entity that nees to be checked if it is in this world
	 * 
	 * @return		True if the worm is in within the boundaries of this world
	 * 				False if the worm is no longer within the boundaries of this world
	 * 				|(0 < ENTITY_X < WIDTH) && (0 < ENTITY_Y < HEIGTH)
	 */
	public boolean entityInWorld(Position pos, double radius) {
		return (0 < (pos.getXCoordinate() - radius))
				&& (0 < (pos.getYCoordinate() - radius))
				&& ((pos.getXCoordinate() - radius) < this
						.getWidth())
				&& ((pos.getYCoordinate() - radius) < this
						.getHeight());
	}	

	// TODO docu
	// This version of isPassable is incomplete, but it is a faster one
	// to prevent lags during the gameplay.
	public boolean isPassable(double xCo, double yCo, double radius) {
		if (((xCo - radius) >= getWidth()) || ((yCo - radius) >= getHeight())
				|| ((yCo + radius) <= 0.00000) || ((xCo + radius) <= 0.00000)) {
			return true;
		}
		double boxHeight = this.getHeight() / this.getPassableMap().length;
		double boxWidth = this.getWidth() / this.getPassableMap()[0].length;

		double angle = 0;
		while (angle < 2 * Math.PI) {
			double circleX = radius * Math.cos(angle) + xCo;
			double circleY = radius * Math.sin(angle) + yCo;

			int row = (int) ((this.getPassableMap().length) - ((circleY) / boxHeight));
			int column = (int) ((circleX) / boxWidth);

			if ((row >= 0) && (row < getPassableMap().length) && (column >= 0)
					&& (column < getPassableMap()[0].length)
					&& (passableMap[row][column] != true)) {
				return false;
			}
			angle += (1.0 / 4.0 * Math.PI);
		}
		int row = (int) ((this.getPassableMap().length) - ((yCo) / boxHeight));
		int column = (int) ((xCo) / boxWidth);
		if ((row >= 0) && (row < getPassableMap().length) && (column >= 0)
				&& (column < getPassableMap()[0].length)
				&& (passableMap[row][column])) {
			return true;
		}
		return false;
	}

	// TODO docu and max values
	// This version of isPassable is complete, but it takes to long to run so
	// the game isn't playable due to the lags this function creates.
	public boolean isPassableCorrect(double xCo, double yCo, double radius) {

		if (((xCo - radius) >= getWidth()) || ((yCo - radius) >= getHeight())
				|| ((yCo + radius) <= 0.00000) || ((xCo + radius) <= 0.00000)) {
			return true;
		}

		double boxHeight = this.getHeight() / this.getPassableMap().length;
		double boxWidth = this.getWidth() / this.getPassableMap()[0].length;
		int upperRow = (int) ((this.getPassableMap().length) - ((yCo + radius) / boxHeight));
		int lowerRow = (int) ((this.getPassableMap().length) - ((yCo - radius) / boxHeight));
		int leftColumn = (int) ((xCo - radius) / boxWidth);
		int rightColumn = (int) ((xCo + radius) / boxWidth);
		// System.out.println("CheckPassable:");
		// System.out.println(this.getPassableMap()[0].length+
		// " | "+this.getPassableMap().length);
		// System.out.println(boxWidth+ " | "+boxHeight);
		// System.out.println("Coords: " + xCo + " | " +
		// yCo+" Circlerad: "+radius);

		// String rows = "Rows:";
		// String cols = "Cols:";
		// System.out.println(upperRow + " | " + lowerRow + " | " + leftColumn
		// + " | " + rightColumn);
		for (int row = upperRow; row <= lowerRow; row += 1) {
			// rows = rows+"\n"+Integer.toString(row)+": ";
			for (int column = leftColumn; column <= rightColumn; column += 1) {
				// rows = rows+" "+Integer.toString(column);
				if ((row >= 0) && (row < getPassableMap().length)
						&& (column >= 0)
						&& (column < getPassableMap()[0].length)
						&& (isBoxInRadius(row, column, xCo, yCo, radius))) {
					// rows = rows+"X";
					if (passableMap[row][column] != true) {
						/**System.out.println("is impassable");
						for (int i = upperRow; i <= lowerRow; i++) {
							System.out.print(i+ "| ");
						    for (int j = leftColumn; j <= rightColumn; j++) {
						    	if (passableMap[i][j] == true ){
						    		System.out.print("1 ");
						    	}
						    	else {
						    		System.out.print("0 ");
						    	}
						    }
						    System.out.print("\n");
						}*/
						return false;
					}
				}
				/**else {
				rows = rows+"_";
				}*/
			}
		}
		// System.out.println(rows);
		// System.out.println(cols);
		/*
		 * System.out.println("is passable"); for (int i = upperRow; i <=
		 * lowerRow; i++) { System.out.print(i+ "| "); for (int j = leftColumn;
		 * j <= rightColumn; j++) { if (passableMap[i][j] == true ){
		 * System.out.print("1 "); } else { System.out.print("0 "); } }
		 * System.out.print("\n"); }
		 */
		// System.out.println("isPass: " + xCo + " | " + yCo + " | " + column
		// + " | " + row + " | " + this.getPassableMap()[row][column]);
		// return this.getPassableMap()[row][column];

		return true;
	}

	public boolean isBoxInRadius(double row, double column, double xCo,
			double yCo, double radius) {
		double boxHeight = this.getHeight() / this.getPassableMap().length;
		double boxWidth = this.getWidth() / this.getPassableMap()[0].length;
		double[] leftTopCorner = { column * boxWidth,
				this.getHeight() - row * boxHeight };
		double[] rightTopCorner = { (column + 0.99) * boxWidth,
				this.getHeight() - row * boxHeight };
		double[] leftBottomCorner = { column * boxWidth,
				this.getHeight() - (row + 0.99) * boxHeight };
		double[] rightBottomCorner = { (column + 0.99) * boxWidth,
				this.getHeight() - (row + 0.99) * boxHeight };
		boolean result = (((Math.pow(leftTopCorner[0] - xCo, 2) + Math.pow(
				leftTopCorner[1] - yCo, 2)) < Math.pow(radius, 2))
				|| ((Math.pow(rightTopCorner[0] - xCo, 2) + Math.pow(
						rightTopCorner[1] - yCo, 2)) < Math.pow(radius, 2))
				|| ((Math.pow(leftBottomCorner[0] - xCo, 2) + Math.pow(
						leftBottomCorner[1] - yCo, 2)) < Math.pow(radius, 2)) || ((Math
				.pow(rightBottomCorner[0] - xCo, 2) + Math.pow(
				rightBottomCorner[1] - yCo, 2)) < Math.pow(radius, 2)));
		return result;
	}

	// TODO docu and max values
	public boolean isAdjacentTerrain(double radius, double xCo, double yCo) {
		if (!isPassable(xCo, yCo, radius)) {
			return false;
		}
		double angle = 0;
		while (angle <= (2 * Math.PI)) {
			double circleX = radius * 0.1 * Math.cos(angle) + xCo;
			double circleY = radius * 0.1 * Math.sin(angle) + yCo;
			if (!isPassable(circleX, circleY, radius)) {
				return true;
			}
			angle += Math.PI * (1.0 / 8.0);
		}
		return false;
	}

	// TODO docu
	public double[] getRandAdjacentTerrain(double radius) {
		int max = 0;
		boolean found = false;
		double[] coord = { 0, 0 };
		while ((max < 10) && (!found)) {
			max += 1;
			coord[0] = getSeed().nextDouble() * getWidth() - radius;
			coord[1] = getSeed().nextDouble() * getHeight() - radius;
			coord = checkPerimeter(radius, coord[0], coord[1]);
			if (isAdjacentTerrain(radius, coord[0], coord[1])) {
				found = true;
			}
		}
		return coord;
	}

	// TODO docu
	private double[] checkPerimeter(double radius, double xCo, double yCo) {
		double angleToCenter = getAngleToCenter(xCo, yCo);
		boolean found = false;
		int max = 0;
		while ((!found) && (max < 1000)) {
			max += 1;
			if (isAdjacentTerrain(radius, xCo, yCo)) {
				found = true;
			} else {
				xCo += 0.02 * Math.cos(angleToCenter);
				yCo += 0.02 * Math.sin(angleToCenter);
			}
		}
		double[] coord = { xCo, yCo };
		return coord;
	}

	// TODO docu
	private double getAngleToCenter(double xCo, double yCo) {
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
