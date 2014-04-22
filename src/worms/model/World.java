package worms.model;

import java.util.ArrayList;
import java.util.Random;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of worlds used in the game of worms with a height, width,
 * passable map matrix, random seed, list of worms, list of foods, 
 * list of teams, projectile, current playing worm.
 * The class also implements methods for the gameplay (start game,
 * next round,get winner,...) and to add entities and teams to the world.
 * 
 * @invar	A world contains at all times no more then a maximum amount of teams.
 * 			|getTeams().size() <= MAX
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
	 * @post	The game of this world is not started yet.
	 * 			|new.isStarted == false	
	 */
	public World(double width, double height, boolean[][] passableMap,
			Random random) {
		this.width = width;
		this.height = height;
		this.setPassableMap(passableMap);
		this.seed = random;
		isStarted = false;
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
	 * @throws 	IllegalStateException
	 * 			If the given entity is a projectile and he world has already a projectile.
	 * 			|if(entity instanceof Projectile)
	 * 			|	then getProjectile() != null
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
			worms.add((Worm) entity);
		} else if (entity instanceof Projectile) {
			if (getProjectile() != null){
				throw new IllegalStateException();
			}
			projectile = (Projectile) entity;
		} else if (entity instanceof Food) {
			foodList.add((Food) entity);
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
	protected void removeEntity(Entity entity) throws IllegalArgumentException {
		if (entity instanceof Worm) {
			worms.remove(entity);
			if (this.getCurrentWormIndex() >= getWormList().size()) {
				this.startNextRound();
			}
		} else if (entity instanceof Projectile) {
			projectile = null;
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
	@Basic
	@Raw
	public ArrayList<Worm> getWormList() {
		return (ArrayList<Worm>) worms.clone();
	}

	/**
	 * Return the active projectile in this world
	 * 
	 * @return the active projectile in this world
	 */
	@Basic
	@Raw
	public Projectile getProjectile() {
		return projectile;
	}

	/**
	 * Return a list of the food who in this world.
	 * 
	 * @return 	The list of food in this world.
	 */
	@Basic
	@Raw
	public ArrayList<Food> getFoodList() {
		return (ArrayList<Food>) foodList.clone();
	}

	/**
	 * Return the food at a given index in the food list of this world.
	 * 
	 * @param 	index
	 * 			The index of the place in the food list of the food
	 * 			you want to return.
	 * @return	The food at the given index.
	 * 			|foodList.get(index)
	 */
	@Raw
	@Model
	private Food getFoodAtIndex(int index) {
		return getFoodList().get(index);
	}

	/**
	 * Return the worm at a given index in the worm list of this world.
	 * 
	 * @param 	index
	 * 			The index of the place in the worm list of the food
	 * 			you want to return.
	 * @return	The worm at the given index.
	 * 			|wormList.get(index)
	 */
	@Raw
	@Model
	private Worm getWormAtIndex(int index) {
		return getWormList().get(index);
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
	@Basic
	@Raw
	@Model
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
	@Raw
	@Model
	private void setCurrentWormIndex(int currentWormIndex) {
		this.currentWormIndex = currentWormIndex;
	}

	/**
	 * Get the worm who is currently at turn in this world.
	 * 
	 * @return 	The worm who is currently at turn in this world.
	 * 			|getWormList().get(getCurrentWormIndex())
	 */
	public Worm getCurrentWorm() {
		return getWormList().get(this.getCurrentWormIndex());
	}

	/**
	 * Start the turn of the next worm if the game in this world is started.
	 * 
	 * @effect	If the game in this world is started and if the last worm has 
	 * 			finished its turn, start a new round.
	 * 			|if (isGameStarted())
	 * 			|	if(LAST WORM PLAYED)
	 * 			|		then startNextRound();
	 * @effect	If the game in this world is started and else set the next worm to the current worm.
	 * 			|	else
	 * 			|		then setCurrentWormIndex(next worm index)
	 * @post	If the game in this world is not started, do nothing.
	 */
	public void startNextTurn() {
		if (isGameStarted()) {
			if (getCurrentWormIndex() >= (worms.size() - 1))
				startNextRound();
			else
				setCurrentWormIndex(getCurrentWormIndex() + 1);
		}
	}

	/**
	 * Start a next round of the game.
	 * 
	 * @effect	All the worms in the world refresh.
	 * 			|worm.refresh()
	 * @effect	The current worm is set to the first worm (index = 0) in this world.
	 * 			|setCurrentWormIndex(0)
	 */
	@Model
	private void startNextRound() {

		for (Worm worm : worms) {
			worm.refresh();
		}
		setCurrentWormIndex(0);

	}

	/**
	 * A variable to hold the information if the game in this world is started.
	 */
	private boolean isStarted;

	/**
	 * Check if the game of this world is started already.
	 * 
	 * @return	True if the game in this world has already been started.
	 * 			|isStarted
	 */
	public boolean isGameStarted() {
		return isStarted;
	}

	/**
	 * Start a new game.
	 * 
	 * @effect	The current worm is set to the first worm (index = 0) in this world.
	 * 			|setCurrentWormIndex(0)
	 */
	public void startGame() {
		isStarted = true;
		setCurrentWormIndex(0);
	}

	/**
	 * Check if the game running in this world is finished.
	 * 
	 * @return	False if the game in this world isn't started yet.
	 * 			| if(!isGameStarted())
	 * 			|	then return false
	 * @return	True if the game is finished. The game is finished
	 * 			when there is only worms of one team left (or only one
	 * 			worm if he has no team).
	 */
	public boolean isGameFinished() {

		//This function iterates through the list of worms and checks if they
		//have the same team as the first worm. If not, the game isn't finished yet.

		if (!isGameStarted()) {
			return false;
		}

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

	/**
	 * Return the name of the winning worm (or winning team if more worms of the
	 * same team are left).
	 * 
	 * @return	If there is only one worm left in this world, return his name.
	 * 			|if (getWormList().size() == 1)
	 * 			|	then getWormAtIndex(0).getName()
	 * @return	Else return the name of the winning team (the team of the first worm)
	 * 			|else
	 * 			|	then getWormAtIndex(0).getTeam().getName()
	 */
	public String getWinnerName() {
		if (getWormList().size() == 1) {
			return getWormAtIndex(0).getName();
		} else {
			return getWormAtIndex(0).getTeam().getName();
		}

	}

	/**
	 * A list containing all the teams who are curently in this world.
	 */
	private ArrayList<Team> teams = new ArrayList<Team>();

	/**
	 * Add a new team to this world. A world cannot contain more then a certain
	 * amount of teams.
	 * 
	 * @param 	team
	 * 			The team to be added to this world.
	 * @post	The last team added to this world is equal to the given team.
	 * 			|new.getTeamList().get(getTeamList().size()-1) == team
	 * @throws	IllegalStateException
	 * 			The world of the given team is not this world.
	 * 			|team.getWorld() != this
	 * @throws 	IllegalStateException
	 * 			This world has already the maximum amount of teams.
	 * 			|getTeamList().size() >= MAX
	 */
	@Model
	protected void addTeam(Team team) throws IllegalStateException {
		if (team.getWorld() != this) {
			throw new IllegalStateException();
		}
		if (getTeamList().size() >= 10) {
			throw new IllegalStateException();
		}
		teams.add(team);
	}

	/**
	 * Return a list of the teams who are in this world.
	 * 
	 * @return 	The list of teams who are in this world.
	 */
	public ArrayList<Team> getTeamList() {
		return teams;
	}

	/**
	 * Get the worm who gets hit by the given projectile in this world.
	 * If no worm is hitted return null.
	 * 
	 * @param 	projectile
	 * 			The projectile to get the hitted worm from.
	 * @return	The worm from who the distance to the position of the given
	 * 			projectile is smaller than the sum of the radius of both.
	 * 			|if (projectile.getPosition().distanceTo(worm.getPosition()) < projectile
	 *			|	.getRadius() + worm.getRadius())
	 *			|		then return worm
	 * @return	If no worm is hitted return null.
	 * 			|else
	 * 			|	then return null
	 * @throws	IllegalArguementException
	 * 			If the given projectile is not in this world.
	 * 			|projectile.getWorld() != this
	 */
	public Worm getWormHit(Projectile projectile)
			throws IllegalArgumentException {

		//This function iterates through all the worms in this world and
		//checks the distance between the position of the worm and the position
		//of the given projectile. If the distance is smaller than the sum of the radius
		//of both, the worm gets hit.

		if (projectile.getWorld() != this) {
			throw new IllegalArgumentException();
		}

		int i = 0;
		boolean hit = false;
		Worm worm = null;
		while ((i < this.getWormList().size()) && (!hit)) {
			worm = this.getWormAtIndex(i);
			if (projectile.getPosition().distanceTo(worm.getPosition()) < (projectile
					.getRadius() + worm.getRadius())) {
				hit = true;
			}
			i++;
		}
		if (hit == false) {
			worm = null;
		}
		return worm;
	}

	/**
	 * Returns true if the given projectile hits a worm.
	 * 
	 * @param 	projectile
	 * 			The projectile to check if it hits a worm in this world.
	 * @return	True if the distance between the position of a worm and the
	 * 			position of the given projectile is smaller than the sum of the
	 * 			radius of both and the worm is not equal to the current worm.
	 * 			|if ((projectile.getPosition().distanceTo(worm.getPosition()) < projectile
	 *			|	.getRadius() + worm.getRadius()) && (worm != getCurrentWorm()))
	 *			|		then return true
	 * @throws	IllegalArguementException
	 * 			If the given projectile is not in this world.
	 * 			|projectile.getWorld() != this
	 */
	public boolean checkProjectileHitWorm(Projectile projectile) {

		if (projectile.getWorld() != this) {
			throw new IllegalArgumentException();
		}

		int i = 0;
		boolean hit = false;
		Worm worm = null;
		while ((i < this.getWormList().size()) && (!hit)) {
			worm = this.getWormAtIndex(i);
			if (projectile.getPosition().distanceTo(worm.getPosition()) < (projectile
					.getRadius() + worm.getRadius())
					&& (worm != this.getCurrentWorm())) {
				hit = true;
			}
			i++;
		}
		return hit;
	}

	/**
	 * Get the food who gets eaten by the given worm in this world.
	 * If no food is eaten return null.
	 * 
	 * @param 	worm
	 * 			The worm to get the eaten food from.
	 * @return	The food from who the distance to the position of the given
	 * 			worm is smaller than the sum of the radius of both.
	 * 			|if (food.getPosition().distanceTo(worm.getPosition()) < worm
	 *			|	.getRadius() + food.getRadius())
	 *			|		then return food
	 * @return	If no food is eaten return null.
	 * 			|else
	 * 			|	then return null
	 * @throws	IllegalArguementException
	 * 			If the given worm is not in this world.
	 * 			|worm.getWorld() != this
	 */
	public Food getFoodEatenBy(Worm worm) {

		//This function iterates through all the foods in this world and
		//checks the distance between the position of the food and the position
		//of the given worm. If the distance is smaller than the sum of the radius
		//of both, the food gets eaten.

		if (worm.getWorld() != this) {
			throw new IllegalArgumentException();
		}

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

	/**
	 * Returns true if the given worm can eat food.
	 * 
	 * @param 	worm
	 * 			The worm to check if he can eat food in this world.
	 * @return	True if the distance between the position of a food and the
	 * 			position of the given worm is smaller than the sum of the
	 * 			radius of both.
	 *			|if ((worm.getPosition().distanceTo(food.getPosition())) <= (worm.getRadius() + food
	 *			|	.getRadius()))
	 *			|		then return true
	 * @throws	IllegalArguementException
	 * 			If the given worm is not in this world.
	 * 			|worm.getWorld() != this
	 */
	public boolean checkWormCanEatFood(Worm worm) {

		if (worm.getWorld() != this) {
			throw new IllegalArgumentException();
		}

		int i = 0;
		boolean eat = false;
		Food food = null;
		while ((i < this.getFoodList().size()) && (!eat)) {
			food = this.getFoodAtIndex(i);
			if ((worm.getPosition().distanceTo(food.getPosition())) <= (worm
					.getRadius() + food.getRadius())) {
				eat = true;
			}
			i++;
		}

		return eat;
	}

	/**
	 * Check if the given position and a circle with the given radius around it is
	 * in this world.
	 * @param 	pos	
	 * 			The position of the entity that needs to be checked if it is in this world
	 * @param 	radius
	 * 			The radius of the entity that needs to be checked if it is in this world
	 * @return	True if the worm is in within the boundaries of this world
	 * 			False if the worm is no longer within the boundaries of this world
	 * 			|(0 < ENTITY_X < WIDTH) && (0 < ENTITY_Y < HEIGTH)
	 */
	public boolean entityInWorld(Position pos, double radius) {
		return (0 < (pos.getXCoordinate() - radius))
				&& (0 < (pos.getYCoordinate() - radius))
				&& ((pos.getXCoordinate() - radius) < this.getWidth())
				&& ((pos.getYCoordinate() - radius) < this.getHeight());
	}

	/**
	 * Checks if the given position (x- and y-coordinate) is passable for an
	 * entity with the given radius in this world.
	 * @param 	xCo
	 * 			The x-coordinate of the position to check.
	 * @param 	yCo
	 * 			The y-coordinate of the position to check.
	 * @param 	radius
	 * 			The radius of the entity to check.
	 * @return	True if the the positions inside the circle with given position
	 * 			(x- and y-coordinate) as center and given radius as radius are
	 * 			passable.
	 * 			|for each position checked
	 * 			|	if (passableMap[ROW AT yCO][COLUMN AT xCO] != true)
	 * 			|		then return false
	 */
	public boolean isPassable(double xCo, double yCo, double radius) {

		//This version of isPassable is incomplete, but it is a faster one
		//to prevent lags during the gameplay.		

		//This method checks the passability of 8 equaly divided positions
		//on the circle with the given radius around the given position and the center
		//of that circle. If one of these positions is impassable, the function returns
		//false. If the circle is fully outside this world, the method returns true.

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

	/**
	 * Checks if the given position (x- and y-coordinate) is passable for an
	 * entity with the given radius in this world.
	 * @param 	xCo
	 * 			The x-coordinate of the position to check.
	 * @param 	yCo
	 * 			The y-coordinate of the position to check.
	 * @param 	radius
	 * 			The radius of the entity to check.
	 * @return	True if the the positions inside the circle with given position
	 * 			(x- and y-coordinate) as center and given radius as radius are
	 * 			passable.
	 * 			|for each position checked
	 * 			|	if (passableMap[ROW AT yCO][COLUMN AT xCO] != true)
	 * 			|		then return false
	 */
	public boolean isPassableCorrect(double xCo, double yCo, double radius) {

		// This version of isPassable is complete, but it takes to long to run so
		// the game isn't playable due to the lags this function creates.

		//This method checks the passability of every position within the circle with 
		//the given radius around the given position and the center
		//of that circle. If one of these positions is impassable, the function returns
		//false. If the circle is fully outside this world, the method returns true.
		//The method does this by getting every box of the passableMap matrix of this world
		//inside a square around the circle. If the 'box' (one element of the matrix) is inside
		//the circle, the method checks the passability of that box.

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

		for (int row = upperRow; row <= lowerRow; row += 1) {
			for (int column = leftColumn; column <= rightColumn; column += 1) {
				if ((row >= 0) && (row < getPassableMap().length)
						&& (column >= 0)
						&& (column < getPassableMap()[0].length)
						&& (isBoxInRadius(row, column, xCo, yCo, radius))) {
					if (passableMap[row][column] != true) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Check if the element in the given row and column of the passableMap matrix is 
	 * inside a circle with center the given position (x- and y-coordinate) and radius
	 * the given radius.
	 * 
	 * @param 	row
	 * 			The row of the element to check.
	 * @param 	column
	 * 			The column of the element to check.
	 * @param 	xCo
	 * 			The x-coordinate of the center position of the circle to check.
	 * @param 	yCo
	 * 			The y-coordinate of the center position of the circle to check.
	 * @param 	radius
	 * 			The radius of the circle to check.
	 * @return	True if one of the corners of the rectangle (the element of the
	 * 			passableMap matrix) is 
	 */
	private boolean isBoxInRadius(double row, double column, double xCo,
			double yCo, double radius) {

		//This method sees an element of the passableMap matrix as a rectangle
		//with width (width of this world divided by the number of columns in the matrix)
		//with height (height of this world divided by the number of rows in the matrix).
		//If one of the four corners of this rectangle is closer to the center than the
		//radius, the rectangle is partially inside the circle and so this method returns true.		

		Position center = new Position(xCo, yCo);

		double boxHeight = this.getHeight() / this.getPassableMap().length;
		double boxWidth = this.getWidth() / this.getPassableMap()[0].length;
		Position leftTopCorner = new Position(column * boxWidth,
				this.getHeight() - row * boxHeight);
		Position rightTopCorner = new Position((column + 0.99) * boxWidth,
				this.getHeight() - row * boxHeight);
		Position leftBottomCorner = new Position(column * boxWidth,
				this.getHeight() - (row + 0.99) * boxHeight);
		Position rightBottomCorner = new Position((column + 0.99) * boxWidth,
				this.getHeight() - (row + 0.99) * boxHeight);

		boolean result = ((center.distanceTo(leftTopCorner) < radius)
				|| (center.distanceTo(rightTopCorner) < radius)
				|| (center.distanceTo(leftBottomCorner) < radius) || (center
				.distanceTo(rightBottomCorner) < radius));

		return result;
	}

	/**
	 * Check if the given position (x- and y-coordinate) is passable and adjacent
	 * to impassable terrain for an entity with the given radius in this world.
	 * 
	 * @param 	radius
	 * 			The radius to check if the position is adjacent.
	 * @param 	xCo
	 * 			The x-coordinate of the position to check.
	 * @param 	yCo
	 * 			The y-coordinate of the position to check.
	 * @return	False if the the given position is not passable terrain in this world.
	 * 			|if (!isPassable(xCo, yCo, radius))
	 *			|	then return false
	 * @return	True if the given position is passble terrain and there is impassable
	 *			terrain at a distance less than a partition of the enity's radius.
	 *			|if ((!isPassable(xCo, yCo, radius)) && (!isPassable(circleX, circleY, radius)))
	 *			|	then return true
	 * @return	False in other cases.
	 * 			|else
	 * 			|	then return false
	 */
	public boolean isAdjacentTerrain(double radius, double xCo, double yCo) {

		//This method first checks of the given position is passable and returns false if not.
		//Then checks positions close to the center position (closer (steps of 0.01) than 0.1 
		//times the given radius) if they are passable and returns true if one of them is not.

		if (!isPassable(xCo, yCo, radius)) {
			return false;
		}
		double angle = 0;
		while (angle <= (2 * Math.PI)) {
			double fact = 0.1;
			while (fact > 0) {
				double circleX = radius * fact * Math.cos(angle + Math.PI)
						+ xCo;
				double circleY = radius * fact * Math.sin(angle + Math.PI)
						+ yCo;
				if (!isPassable(circleX, circleY, radius)) {
					return true;
				}
				fact -= 0.01;
			}
			angle += Math.PI * (1.0 / 8.0);
		}
		return false;
	}

	/**
	 * Get a position (x- and y-coordinate) for an entity with given radius in this world 
	 * which is passable and adjacent to impassable terrain. This position is generated 
	 * with the random seed of this world.
	 * 
	 * @param 	radius
	 * 			The radius of the entity for who this position needs to be generated.
	 * @return	A double array with an x- and y-coordintate of the randomly generated
	 * 			position.
	 * 			|if (isAdjacentTerrain(radius, XCO, YCO)
	 * 			|	then return [XCO, YCO]
	 */
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

	/**
	 * Get a position (x- and y-coordinate) for an entity with the given radius
	 * which is passable and adjacent to impassable terrain in this world. The calculation
	 * of the position starts from a given start position (x- and y-coordinate). 
	 * 
	 * @param 	radius
	 * 			The radius of the entity for who this position needs to be generated.
	 * @param 	xCo
	 * 			The x-coordinate of the start position to check.
	 * @param 	yCo
	 * 			The y-coordinate of the start position to check.
	 * @return	A double array with an x- and y-coordintate of the found position.
	 * 			|return [XCO, YCO]
	 */
	private double[] checkPerimeter(double radius, double xCo, double yCo) {

		//This method gets the direction from the start position to the center
		//of this world. Next this method checks every position (steps of 0.02)
		//in the center direction. If one of these positions are passable and 
		//adjacent to impassable terrain, the coordinates of this position are
		//returned. If the function doesn't find any such position within 1000 steps
		//the method returns the last checked position.

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

	/**
	 * Get the angle of the direction from a given position (x- and y-coordinate) to
	 * the center of this world.
	 * 
	 * @param 	xCo
	 * 			The x-coordinate of the position to get the direction from.
	 * @param 	yCo
	 * 			The y-coordinate of the position to get the direction from.
	 * @return	The angle of the direction to the center of this world.
	 */
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
