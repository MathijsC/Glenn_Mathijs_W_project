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

		double boxHeight = this.getHeight() / this.getPassableMap().length;
		double boxWidth = this.getWidth() / this.getPassableMap()[0].length;
		System.out.println(this.getPassableMap()[0].length + " | "
				+ this.getPassableMap().length);
		System.out.println(boxWidth + " | " + boxHeight);
		/*
		 * int m = 450; int n = passableMap[0].length-1; for (int i = 0; i < m;
		 * i++) { System.out.println(i+ "|\t"); for (int j = 0; j < n; j++) { if
		 * (passableMap[i][j] == true ){ System.out.print("1 "); } else {
		 * System.out.print("0 "); } } System.out.print("\n"); }
		 */

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

	// TODO
	public Worm getWormAtIndex(int index) {
		return worms.get(index);
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
	public void setProjectile(Projectile projectile)
			throws IllegalStateException {
		if (!(projectile.getWorld() == this)) {
			throw new IllegalStateException();
		}
		this.projectile = projectile;
	}

	// TODO
	public Worm getWormHit(Projectile projectile) {
		int i = 0;
		boolean hit = false;
		Worm worm = null;
		while ((i < this.getWorms().size()) && (!hit)) {
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
		while ((i < this.getWorms().size()) && (!hit)) {
			worm = this.getWormAtIndex(i);
			if ((position.distanceTo(worm.getPosition())) <= (radius + worm
					.getRadius()) && (worm != this.getCurrentWorm())) {
				hit = true;
			}
			i++;
		}

		return hit;
	}

	/**
	 * Return a list of the food who in this world.
	 * 
	 * @return 	The list of food in this world.
	 */
	public ArrayList<Food> getFoodList() {
		return foodList;
	}

	// TODO
	public Food getFoodAtIndex(int index) {
		return this.foodList.get(index);
	}

	/*
	 * // TODO public Food checkWormAteFood(Worm worm) { int i = 0; boolean ate
	 * = false; Food food = null; while ((i < this.getFoodList().size()) &&
	 * (!ate)) { food = this.getFoodAtIndex(i); if
	 * (worm.getPosition().distanceTo(food.getPosition()) < food .getRadius() +
	 * worm.getRadius()) { ate = true; } i++; } if (i > this.getWorms().size())
	 * { food = null; }
	 * 
	 * return food; }
	 */

	// TODO docu and max values
	public boolean isPassable(double xCo, double yCo, double radius) {

		if (((xCo + radius) >= getWidth()) || ((yCo + radius) >= getHeight())
				|| ((yCo - radius) <= 0.00000) || ((xCo - radius) <= 0.00000)) {
			//System.out.println("OutOfBounds: " + xCo + " | " + yCo);
			return false;
		}

		double boxHeight = this.getHeight() / this.getPassableMap().length;
		double boxWidth = this.getWidth() / this.getPassableMap()[0].length;
		//System.out.println("world: " + this.getPassableMap().length + " | "
				//+ boxHeight + " | " + boxWidth + " | " + this.getHeight()
				//+ " | " + this.getWidth());
		//System.out.println("Passable: " + xCo + " | " + yCo + " | " + radius);
		int upperRow = (int) ((this.getPassableMap().length) - ((yCo + radius) / boxHeight));
		int lowerRow = (int) ((this.getPassableMap().length) - ((yCo - radius) / boxHeight));
		int leftColumn = (int) ((xCo - radius) / boxWidth);
		int rightColumn = (int) ((xCo + radius) / boxWidth);
		//System.out.println("CheckPassable:");
		//System.out.println(this.getPassableMap()[0].length+ " | "+this.getPassableMap().length);
		//System.out.println(boxWidth+ " | "+boxHeight);
		//System.out.println("Coords: " + xCo + " | " + yCo+" Circlerad: "+radius);

		//String rows = "Rows:";
		//String cols = "Cols:";
		//System.out.println(upperRow + " | " + lowerRow + " | " + leftColumn
				//+ " | " + rightColumn);
		for (int row = upperRow; row <= lowerRow; row+=5) {
			//rows = rows+"\n"+Integer.toString(row)+": ";
			for (int column = leftColumn; column <= rightColumn; column+=5) {
				//rows = rows+" "+Integer.toString(column);
				if (isBoxInRadius(row, column, xCo, yCo, radius)) {
					//rows = rows+"X";
					if (passableMap[row][column] != true) {
						/*System.out.println("is impassable");
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
				} /*else {
					rows = rows+"_";
					}*/
			}
		}
		//System.out.println(rows);
		//System.out.println(cols);
		/*System.out.println("is passable");
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
		// System.out.println("isPass: " + xCo + " | " + yCo + " | " + column
		// + " | " + row + " | " + this.getPassableMap()[row][column]);
		//return this.getPassableMap()[row][column];

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
		if ((xCo >= getWidth()) || (yCo >= getHeight()) || (yCo < 0)
				|| (xCo < 0)) {
			return false;
		}
		//System.out.println("CheckAdj->first");
		if (!isPassable(xCo, yCo, radius)) {
			return false;
		}
		double angle = Math.PI + (Math.PI * (127.0 / 256.0));
		//System.out.println("CheckAdj:");
		while (angle <= (2 * Math.PI - (Math.PI * (127.0 / 256.0)))) {
			double circleX = radius * 0.1 * Math.cos(angle) + xCo;
			double circleY = radius * 0.1 * Math.sin(angle) + yCo;
			//System.out.println("CheckAdj->passable:");
			if (!isPassable(circleX, circleY, radius)) {
				//System.out.println("Adj");
				return true;
			}
			angle += Math.PI * (1.0 / 256.0);
		}
		//System.out.println("NotAdj");
		return false;
	}

	public double[] getRandAdjacentTerrain(double radius) {
		int max = 0;
		boolean found = false;
		double[] coord = { 0, 0 };
		while ((max < 10) && (!found)) {
			//System.out.println("\nPerim number: " + max + " ->coords: "
			// + coord[0] + " | " + coord[1]);
			max += 1;
			coord[0] = getSeed().nextDouble() * getWidth() - radius;
			coord[1] = getSeed().nextDouble() * getHeight() - radius;
			//coord[0] = 15;
			//coord[1] = 13;
			new Worm(this, coord[0], coord[1], 0.2, 0.25, "Start"
			+ Integer.toString(max));
			coord = checkPerimeter(radius, coord[0], coord[1]);
			if (isAdjacentTerrain(radius, coord[0], coord[1])) {
				found = true;
				System.out.println("Found2");
			}
		}
		return coord;
	}

	private double[] checkPerimeter(double radius, double xCo, double yCo) {
		double angleToCenter = getAngleToCenter(xCo, yCo);
		boolean found = false;
		int max = 0;
		while ((!found) && (max < 1000)) {
			// System.out.println("Step number: " + max);
			max += 1;
			if (isAdjacentTerrain(radius, xCo, yCo)) {
				found = true;
				System.out.println("FOUND!");
			} else {
				xCo += 0.02 * Math.cos(angleToCenter);
				yCo += 0.02 * Math.sin(angleToCenter);
				// System.out.println("New Coords: " + xCo + " | " + yCo);
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
