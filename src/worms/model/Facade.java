package worms.model;

import java.util.Collection;
import java.util.Random;

import worms.gui.game.IActionHandler;
import worms.model.programs.ParseOutcome;
import worms.model.programs.ProgramParser;
import worms.model.programs.ParseOutcome.Success;

public class Facade implements IFacade {

	/**
	 * Default constructor
	 */
	public Facade() {

	}

	/**
	 * Create and add an empty team with the given name to the given world.
	 * 
	 * (For single-student groups that do not implement teams, this method should have no effect)
	 */
	public void addEmptyTeam(World world, String newName) throws ModelException {
		try {
			new Team(world, newName);
		} catch (IllegalArgumentException exc) {
			throw new ModelException("Illegal Name");
		} catch (IllegalStateException exc) {
			throw new ModelException("Illegal State");
		} catch (IllegalWorldException exc) {
			throw new ModelException("Illegal World");
		}
	}

	/**
	 * Create and add a new food ration to the given world.
	 * The food must be placed at a random adjacent location.
	 * 
	 * (For single-student groups that do not implement food, this method should have no effect)
	 */
	public void addNewFood(World world) throws ModelException {
		try {
			new Food(world);
		} catch (IllegalStateException exc) {
			throw new ModelException("Illegal State");
		} catch (IllegalWorldException exc) {
			throw new ModelException("Illegal World");
		}

	}

	/**
	 * Create and add a new worm to the given world.
	 * The new worm must be placed at a random adjacent location.
	 * The new worm can have an arbitrary (but valid) radius and direction.
	 * The new worm may (but isn't required to) have joined a team.
	 */
	public void addNewWorm(World world, Program program) {
		try {
			new Worm(world,program);
		} catch (IllegalStateException exc) {
			throw new ModelException("Illegal State");
		} catch (IllegalWorldException exc) {
			throw new ModelException("Illegal World");
		}

	}

	/**
	 * Returns whether or not the given worm can fall down
	 */
	public boolean canFall(Worm worm) {
		return worm.canFall();

	}

	/**
	 * Returns whether or not the given worm can move a given number of steps.
	 */
	public boolean canMove(Worm worm) {
		return worm.canMove();
	}

	/**
	 * Returns whether or not the given worm can turn by the given angle.
	 */
	public boolean canTurn(Worm worm, double angle) {
		return worm.canTurn(angle);
	}

	/**
	 * Create a new food ration that is positioned at the given location in the given world.
	 * 
	 * @param world
	 * The world in which to place the created food ration
	 * @param x
	 * The x-coordinate of the position of the new food ration (in meter)
	 * @param y
	 * The y-coordinate of the position of the new food ration (in meter)
	 * 
	 * (For single-student groups that do not implement food, this method should have no effect)
	 */
	public Food createFood(World world, double x, double y) throws ModelException{
		try {
			return new Food(world, x, y);
		} catch (IllegalStateException exc) {
			throw new ModelException("Illegal State");
		} catch (IllegalArgumentException exc) {
			throw new ModelException("Illegal argument");
		}
	}

	/**
	 * Creates a new world.
	 * 
	 * @param width The width of the world (in meter)
	 * 
	 * @param height The height of the world (in meter)
	 * 
	 * @param passableMap A rectangular matrix indicating which parts of the terrain are passable and impassable.
	 *  This matrix is derived from the transparency of the pixels in the image file of the terrain.
	 *  passableMap[r][c] is true if the location at row r and column c is passable, and false if that location is impassable.
	 *  The elements in the first row (row 0) represent the pixels at the top of the terrain (i.e., largest y-coordinates).
	 *  The elements in the last row (row passableMap.length-1) represent pixels at the bottom of the terrain (smallest y-coordinates).
	 *  The elements in the first column (column 0) represent the pixels at the left of the terrain (i.e., smallest x-coordinates).
	 *  The elements in the last column (column passableMap[0].length-1) represent the pixels at the right of the terrain (i.e., largest x-coordinates).     
	 * 
	 * @param random A random number generator, seeded with the value obtained from the command line or from GUIOptions,
	 *  that can be used to randomize aspects of the world in a repeatable way.
	 * 
	 * @return The world.
	 */
	public World createWorld(double width, double height,
			boolean[][] passableMap, Random random) {
		return new World(width, height, passableMap, random);
	}

	/**
	 * Create a new worm that is positioned at the given location in the given world,
	 * looks in the given direction, has the given radius and the given name.
	 * 
	 * @param 	x
	 * 			The x-coordinate of the position of the new worm (in meter)
	 * @param 	y
	 * 			The y-coordinate of the position of the new worm (in meter)
	 * @param 	direction
	 * 			The direction of the new worm (in radians)
	 * @param 	radius 
	 * 			The radius of the new worm (in meter)
	 * @param 	name
	 * 			The name of the new worm
	 * @throws	ModelException
	 * 			Throws a model exception when the given name, x,y or world are invalid.
	 */
	public Worm createWorm(World world, double x, double y, double direction,
			double radius, String name,Program program) throws ModelException{
		try {
			return new Worm(world, x, y, direction, radius, name);
		} catch (IllegalArgumentException exc) {
			throw new ModelException("Illegal Name");
		} catch (IllegalStateException exc) {
			throw new ModelException("Illegal State");
		} catch (IllegalWorldException exc) {
			throw new ModelException("Illegal World");
		}
	}

	/**
	 * Makes the given worm fall down until it rests on impassable terrain again.
	 */
	public void fall(Worm worm) {
		worm.fall();
	}

	/**
	 * Returns the current number of action points of the given worm.
	 */
	public int getActionPoints(Worm worm) {
		return worm.getActionPoints();
	}

	/**
	 * Returns the active projectile in the world, or null if no active projectile exists.
	 */
	public Projectile getActiveProjectile(World world) {
		return world.getProjectile();

	}

	/**
	 * Returns the active worm in the given world (i.e., the worm whose turn it is).
	 */
	public Worm getCurrentWorm(World world) {
		return world.getCurrentWorm();

	}

	/**
	 * Returns all the food rations in the world
	 * 
	 * (For single-student groups that do not implement food, this method must always return an empty collection)
	 */
	public Collection<Food> getFood(World world) {
		return world.getFoodList();

	}

	/**
	 * Returns the current number of hit points of the given worm.
	 */
	public int getHitPoints(Worm worm) {
		return worm.getHitPoints();

	}

	/**
	 * Returns the location on the jump trajectory of the given projectile after a
	 * time t.
	 * 
	 * @return 	An array with two elements, with the first element being the
	 *         	x-coordinate and the second element the y-coordinate
	 * @throws 	ModelException
	 * 			Throws a model exception when the given time is an illegal time.
	 * @throws 	ModelException
	 * 			Throws a model exception when the given projectile isn't able to jump.
	 */
	public double[] getJumpStep(Projectile projectile, double t)
			throws ModelException {
		try {
			Position position = projectile.jumpStep(t);
			return (new double[] { position.getXCoordinate(),
					position.getYCoordinate() });
		} catch (IllegalArgumentException exc) {
			throw new ModelException("Illegal argument");
		} catch (IllegalStateException exc) {
			throw new ModelException("Illegal state");
		}
	}

	/**
	 * Returns the location on the jump trajectory of the given worm after a
	 * time t.
	 * 
	 * @return 	An array with two elements, with the first element being the
	 *         	x-coordinate and the second element the y-coordinate
	 * @throws 	ModelException
	 * 			Throws a model exception when the given time is an illegal time.
	 * @throws 	ModelException
	 * 			Throws a model exception when the given worm isn't able to jump.
	 */
	public double[] getJumpStep(Worm worm, double t) throws ModelException {
		try {
			Position position = worm.jumpStep(t);
			return (new double[] { position.getXCoordinate(),
					position.getYCoordinate() });
		} catch (IllegalArgumentException exc) {
			throw new ModelException("Illegal argument");
		} catch (IllegalStateException exc) {
			throw new ModelException("Illegal state");
		}
	}

	/**
	 * Determine the time that the given projectile can jump until it hits the terrain, hits a worm, or leaves the world.
	 * The time should be determined using the given elementary time interval.
	 * 
	 * @param 	projectile 
	 * 			The projectile for which to calculate the jump time.
	 * @param 	timeStep 
	 * 			An elementary time interval during which you may assume
	 *          that the projectile will not completely move through a piece of impassable terrain.             
	 * @return 	The time duration of the projectile's jump.
	 * @throws	ModelException
	 * 			Throws a ModelException if the timeStep is invalid.
	 */
	public double getJumpTime(Projectile projectile, double timeStep) throws ModelException{
		try {
			return projectile.jumpTime(timeStep);
		} catch (IllegalArgumentException exc) {
			throw new ModelException("Illegal argument");
		}
	}

	/**
	 * Determine the time that the given worm can jump until it hits the terrain or leaves the world.
	 * The time should be determined using the given elementary time interval.
	 * 
	 * @param worm The worm for which to calculate the jump time.
	 * 
	 * @param timeStep An elementary time interval during which you may assume
	 *                 that the worm will not completely move through a piece of impassable terrain.
	 *                 
	 * @return The time duration of the worm's jump.
	 * 
	 * @throws Throws a model exception when the given worm isn't able to jump.
	 */
	public double getJumpTime(Worm worm, double timeStep) throws ModelException {
		try {
			return worm.jumpTime(timeStep);
		} catch (IllegalStateException exc) {
			throw new ModelException("Illegal state");
		}
	}

	/**
	 * Returns the mass of the given worm.
	 */
	public double getMass(Worm worm) {
		return worm.getMass();
	}

	/**
	 * Returns the maximum number of action points of the given worm.
	 */
	public int getMaxActionPoints(Worm worm) {
		return worm.getMaxActionPoints();
	}

	/**
	 * Returns the maximum number of hit points of the given worm.
	 */
	public int getMaxHitPoints(Worm worm) {
		return worm.getMaxHitPoints();

	}

	/**
	 * Returns the minimal radius of the given worm.
	 */
	public double getMinimalRadius(Worm worm) {
		return Worm.getMinRadius();
	}

	/**
	 * Returns the name the given worm.
	 */
	public String getName(Worm worm) {
		return worm.getName();
	}

	/**
	 * Returns the current orientation of the given worm (in radians).
	 */
	public double getOrientation(Worm worm) {
		return worm.getDirection();
	}

	/**
	 * Returns the radius of the given food ration
	 * 
	 * (For single-student groups that do not implement food, this method may return any value)
	 */
	public double getRadius(Food food) {
		return food.getRadius();

	}

	/**
	 * Returns the radius of the given projectile.
	 */
	public double getRadius(Projectile projectile) {
		return projectile.getRadius();

	}

	/**
	 * Returns the radius of the given worm.
	 */
	public double getRadius(Worm worm) {
		return worm.getRadius();
	}

	/**
	 * Returns the name of the weapon that is currently active for the given worm,
	 * or null if no weapon is active.
	 */
	public String getSelectedWeapon(Worm worm) {
		return worm.getWeapon().toString();

	}

	/**
	 * Returns the name of the team of the given worm, or returns null if this
	 * worm is not part of a team.
	 * 
	 * (For single-student groups that do not implement teams, this method should always return null)
	 */
	public String getTeamName(Worm worm) {
		if (worm.getTeam() == null)
			return " ";
		return worm.getTeam().getName();

	}

	/**
	 * Returns the name of a single worm if that worm is the winner, or the name
	 * of a team if that team is the winner. This method should null if there is no winner.
	 * 
	 * (For single-student groups that do not implement teams, this method should always return the name of the winning worm, or null if there is no winner)
	 */
	public String getWinner(World world) {
		if (world.isGameFinished()) {
			return world.getWinnerName();
		} else {
			return "No Winner Yet";
		}

	}

	/**
	 * Returns all the worms in the given world
	 */
	public Collection<Worm> getWorms(World world) {
		return world.getWormList();

	}

	/**
	 * Returns the x-coordinate of the given food ration
	 * 
	 * (For single-student groups that do not implement food, this method may return any value)
	 */
	public double getX(Food food) {
		return food.getXCoordinate();

	}

	/**
	 * Returns the x-coordinate of the given projectile.
	 */
	public double getX(Projectile projectile) {
		return projectile.getXCoordinate();

	}

	/**
	 * Returns the x-coordinate of the current location of the given worm.
	 */
	public double getX(Worm worm) {
		return worm.getXCoordinate();
	}

	/**
	 * Returns the y-coordinate of the given food ration
	 * 
	 * (For single-student groups that do not implement food, this method may return any value)
	 */
	public double getY(Food food) {
		return food.getYCoordinate();

	}

	/**
	 * Returns the y-coordinate of the given projectile.
	 */
	public double getY(Projectile projectile) {
		return projectile.getYCoordinate();

	}

	/**
	 * Returns the y-coordinate of the current location of the given worm.
	 */
	public double getY(Worm worm) {
		return worm.getYCoordinate();
	}

	/**
	 * Returns whether or not the given food ration is alive (active), i.e., not eaten.
	 * 
	 * (For single-student groups that do not implement food, this method should always return false)
	 */
	public boolean isActive(Food food) {
		return !food.isTerminated();

	}

	/**
	 * Returns whether the given projectile is still alive (active).
	 */
	public boolean isActive(Projectile projectile) {
		return !projectile.isTerminated();

	}

	/**
	 * Checks whether the given circular region of the given world,
	 * defined by the given center coordinates and radius,
	 * is passable and adjacent to impassable terrain. 
	 * 
	 * @param 	world 
	 * 			The world in which to check adjacency
	 * @param 	x 
	 * 			The x-coordinate of the center of the circle to check  
	 * @param 	y 
	 * 			The y-coordinate of the center of the circle to check
	 * @param 	radius 
	 * 			The radius of the circle to check
	 * 
	 * @return 	True if the given region is passable and adjacent to impassable terrain, false otherwise.
	 */
	public boolean isAdjacent(World world, double x, double y, double radius) {
		return world.isAdjacentTerrain(radius, x, y);

	}

	/**
	 * Returns whether the given worm is alive
	 */
	public boolean isAlive(Worm worm) {
		return !worm.isTerminated();
	}

	/**
	 * Returns whether the game in the given world has finished.
	 */
	public boolean isGameFinished(World world) {
		return world.isGameFinished();

	}

	/**
	 * Checks whether the given circular region of the given world,
	 * defined by the given center coordinates and radius,
	 * is impassable. 
	 * 
	 * @param 	world 
	 * 			The world in which to check impassability 
	 * @param 	x 
	 * 			The x-coordinate of the center of the circle to check  
	 * @param 	y 
	 * 			The y-coordinate of the center of the circle to check
	 * @param 	radius 
	 * 			The radius of the circle to check
	 * 
	 * @return 	True if the given region is impassable, false otherwise.
	 */
	public boolean isImpassable(World world, double x, double y, double radius) {
		return !world.isPassable(x, y, radius);

	}

	/**
	 * Make the given projectile jump to its new location.
	 * The new location should be determined using the given elementary time interval. 
	 *  
	 * @param projectile The projectile that needs to jump
	 * 
	 * @param timeStep An elementary time interval during which you may assume
	 *                 that the projectile will not completely move through a piece of impassable terrain.
	 */
	public void jump(Projectile projectile, double timeStep) {
		projectile.jump(timeStep);
	}

	/**
	 * Make the given worm jump to its new location.
	 * The new location should be determined using the given elementary time interval. 
	 *  
	 * @param worm The worm that needs to jump
	 * 
	 * @param timeStep An elementary time interval during which you may assume
	 *                 that the worm will not completely move through a piece of impassable terrain.
	 * 
	 * @throws Throws a model exception when the given worm isn't able to jump.
	 */
	public void jump(Worm worm, double timeStep) throws ModelException {
		try {
			worm.jump(timeStep);
		} catch (IllegalStateException exc) {
			throw new ModelException("Illegal state");
		}

	}

	/**
	 * Moves the given worm according to the rules in the assignment.
	 * 
	 * @throws Throws a model exception if the given worm can't move the given number of steps.
	 */
	public void move(Worm worm) throws ModelException {
		try {
			worm.move();
		} catch (IllegalArgumentException exc) {
			throw new ModelException("Illegal argument");
		} catch (IllegalStateException exc) {
			throw new ModelException("Worm cannot move!");
		}
	}

	/**
	 * Renames the given worm.
	 * 
	 * @throws Throws a model exception when the new name doesn't fit the constraints.
	 */
	public void rename(Worm worm, String newName) throws ModelException {
		try {
			worm.setName(newName);
		} catch (IllegalArgumentException exc) {
			throw new ModelException("Illegal argument");
		}
	}

	/**
	 * Activates the next weapon for the given worm
	 */
	public void selectNextWeapon(Worm worm) {
		worm.selectNextWeapon();
	}

	/**
	 * Sets the radius of the given worm to the given value.
	 * 
	 * @throws Throws a model exception when the given radius is smaller than the minimum radius.
	 */
	public void setRadius(Worm worm, double newRadius) throws ModelException {
		try {
			worm.setRadius(newRadius);
		} catch (IllegalArgumentException exc) {
			throw new ModelException("Illegal argument!");
		}

	}

	/**
	 * Makes the given worm shoot its active weapon with the given propulsion yield.
	 */
	public void shoot(Worm worm, int yield) {
		worm.shoot(yield);
	}

	/**
	 * Starts a game in the given world.
	 */
	public void startGame(World world) {
		world.startGame();
	}

	/**
	 * Starts the next turn in the given world
	 */
	public void startNextTurn(World world) {
		world.startNextTurn();
	}

	/**
	 * Turns the given worm by the given angle.
	 */
	public void turn(Worm worm, double angle) {
		worm.turn(angle);
	}
	
	/**
	 * Try to parse the given program.
	 * You can use an instance of the worms.model.programs.ProgramParser.
	 * 
	 * When the program is executed, the execution of an action statement
	 * must call the corresponding method of the given action handler.
	 * This executes the action as if a human player has initiated it, and
	 * will eventually call the corresponding method on the facade. 
	 * 
	 * @param programText The program text to parse
	 * @param handler The action handler on which to execute commands
	 * 
	 * @return the outcome of parsing the program, which can be a success or a failure.
	 * You can create a ParseOutcome object by means of its two static methods, success and failure. 
	 */
	public ParseOutcome<?> parseProgram(String programText, IActionHandler handler){
		Program program = new Program(programText,handler);
		ParseOutcome<?> out;
		if (!program.getErrors().isEmpty()){
			out = ParseOutcome.failure(program.getErrors());
		} else {
			out = ParseOutcome.success(program);
		}
		return out;
	}
	
	/**
	 * Returns whether or not the given worm is controlled by a program. 
	 * 
	 * @return true if the given worm is controlled by a program, false otherwise
	 */
	public boolean hasProgram(Worm worm){
		return false;
	}
	
	/**
	 * Returns whether or not the given program is well-formed.
	 * A program is well-formed if a for-each statement does not (directly or
	 * indirectly) contain one or more action statements.
	 * 
	 * @param program The program to check
	 * 
	 * @return true if the program is well-formed; false otherwise 
	 */
	public boolean isWellFormed(Program program){
		return true;
	}

}
