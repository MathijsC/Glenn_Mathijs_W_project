package worms.model;

import java.util.Arrays;
import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class for a worm objects containing a x-coordinate, y-coordinate
 * looking direction, radius, name, action points and hit points of this worm.
 * The class also implements methods to shoot, fall, heal/hit, jump, turn and move.
 * 
 * @invar	The direction of this worm should be a valid direction at all time.
 * 			|isValidDirection(getDirection())
 * @invar	The radius of this worm should be a valid radius at all time.
 * 			|isValidRadius(getRadius())
 * @invar	The name of this worm should be a valid name at all time.
 * 			|isValidName(getName())
 * @invar	The mass of this worm should be equal to the calculated mass according 
 * 			to the radius of this worm.
 * 			|getMass() == calcMass(getRadius())
 * @invar	The amount of action points of this worm should be a valid amount at all time.
 * 			|canHaveAsActionPoints(getActionPoints())
 * @invar	The amount of hit points of this worm should be a valid amount at all time.
 * 			|canHaveAsHitPoints(getHitPoints())
 * 
 * @author 	Glenn Cools, Mathijs Cuppens

 * @version 1.33
*/

public class Worm extends Entity{

	/**
	 * Initialize this new worm with a given position (x- and y-coordinate), looking direction,
	 * radius and name and with calculated mass, action points, hit points and random team and a weapon.
	 * 
	 * @param	world
	 * 			The world of this new worm.
	 * @param 	x
	 * 			The x-coordinate of the position for this new worm (in meters).
	 * @param 	y
	 * 			The y-coordinate of the position for this new worm (in meters).
	 * @param 	direction
	 * 			The looking direction for this new worm (in radians).
	 * @param 	radius
	 * 			The radius for this new worm (in meters).
	 * @param 	name
	 * 			The name for this new worm.
	 * @effect	This new worm is initialized as a subobject of the class entity
	 * 			with the given world and position (x- and y-coordinate).
	 * 			|super(new Position(x, y), world)
	 * @effect	The looking direction of the new worm is equal to the given
	 * 			direction modulo 2*PI.
	 * 			|setDirection(direction)
	 * @effect	The radius of this new worm is set to the given radius. The mass
	 * 			of this new worm is also set.
	 * 			|setRadius(radius)
	 * @effect	If the given name is a valid name, the name of this new worm is
	 * 			equal to the given name.
	 * 			|setName(name)
	 * @effect	The action points of this new worm is set the the maximum possible
	 * 			action points for this new worm in accordance to its mass.
	 * 			|setActionPoints(getMaxActionPoints())
	 * @effect	The hit points of this new worm is set the the maximum possible
	 * 			hit points for this new worm in accordance to its mass.
	 * 			|setActionPoints(getMaxActionPoints())
	 * @effect	The weapon of this new worm is set to a starting weapon.
	 * 			|setWeapon(STARTING WEAPON)
	 * @effect	If there is a team in the given world, the team of this new worm
	 * 			is set to a ramdomly chosen team.
	 * 			|if (!world.getTeams().isEmpty())
	 * 			|	then setTeam(RANDOM TEAM)
	 */
	@Raw
	public Worm(World world, double x, double y, double direction,
			double radius, String name) {
		super(new Position(x, y), world);
		setDirection(direction);
		setRadius(radius);
		setActionPoints(getMaxActionPoints());
		setHitPoints(getMaxHitPoints());
		setName(name);
		setCurrentWeaponIndex(0);
		if (!world.getTeamList().isEmpty()) {
			setTeam(world.getTeamList().get(
					world.getSeed().nextInt(world.getTeamList().size())));
		}

	}

	/**
	 * Initialize this new worm with a given world and a random position (x- and y-coordinate), looking direction,
	 * radius name and with calculated mass, action points, hit points and random team and a weapon.
	 *
	 * @param	world
	 * 			The world of this new worm.
	 * @effect	Initialize this new worm with the given world, a dummy position (x- and y-coordinate),
	 * 			random direction,random radius and a dummy name.
	 * 			|this(world,0.0,0.0,RANDOM DIRECTION, RANDOM RADIUS,"Dummyname")
	 * @effect	Set the name of this new worm to a random name.
	 * 			|setName(RANDOM NAME)
	 * @effect	Set the position of this new worm to a random position in the given world.
	 * 			|setPosition(RANDOM X,RANDOM Y)
	 * 			
	 */
	@Raw
	public Worm(World world) {
		this(world, 0.0, 0.0, world.getSeed().nextDouble() * 2 * Math.PI,
				(1 + (world.getSeed().nextDouble())) * getMinRadius(),
				"Dummyname");
		final String[] wormNames = { "Glenn", "Mathijs", "Siri", "Bernd",
				"Tom", "Nick", "Toon", "Lieven", "Joeri", "Syd" };
		setName(wormNames[world.getSeed().nextInt(wormNames.length)]);
		double[] randCoord = world.getRandAdjacentTerrain(this.getRadius());
		this.setPosition(randCoord[0], randCoord[1]);
	}
	

	/**
	 * A variable holding the team of this worm.
	 */
	private Team team;

	/**
	 * Return the team of this worm.
	 * 
	 * @return	The team of this worm.
	 */
	@Raw
	@Basic
	public Team getTeam() {
		return team;
	}

	/**
	 * Add this worm to the given team.
	 * 
	 * @param	team
	 * 			The team where to add this worm to.
	 * @post	The new team of this worm is equal to the given team.
	 * 			|new.getTeam() == team
	 * @effect	This worm is added to the given team.
	 * 			|team.addWorm(this)
	 */
	@Raw
	@Model
	private void setTeam(Team team) {
		this.team = team;
		team.addWorm(this);
	}

	/**
	 * Return the weapon of this worm.
	 * 
	 * @return The weapon of this worm.
	 */
	@Basic
	@Raw
	public Weapon getWeapon() {
		return this.weaponTypeList.get(this.getCurrentWeaponIndex());
	}

	/**
	 * Return the current weapon index
	 * 
	 * @return the current weapon index
	 */
	@Basic
	@Raw
	private int getCurrentWeaponIndex() {
		return this.currentWeaponIndex;
	}

	/**
	 * Set the current weapon index of this worm to the given weapon index.
	 * 
	 * @param 	index
	 * 			The index to set as current weapon index of this worm.
	 * @post	The given weapon index is equal to the new current weapon index of this worm.
	 * 			|new.getCurrentWeaponIndex() == index
	 */
	@Raw
	@Model
	private void setCurrentWeaponIndex(int index) {
		this.currentWeaponIndex = index;
	}

	/**
	 * An arrayList holding all the possible weapons of this worm.
	 */
	private ArrayList<Weapon> weaponTypeList = new ArrayList<Weapon>(
			Arrays.asList(Weapon.Rifle, Weapon.Bazooka));

	/**
	 * A variable holding the index of the current weapon of this worm.
	 */
	private int currentWeaponIndex = 0;

	/**
	 * Set the next weapon of weaponTypeList as weapon of this worm.
	 * 
	 * @effect	If the current weapon is the last weapon in weaponTypeList,
	 * 			select the first weapon of that list as weapon for this worm
	 * 			and set the currentWeaponIndex to zero.
	 * 			|if (currentWeaponIndex >= (weaponTypeList.size()-1))
	 * 			|	then setCurrentWeaponIndex(0);
	 * @effect	Else select the next weapon in weaponTypeList and add 1 to the currentWeaponIndex of this worm.
	 * 			|else
	 * 			|	then setCurrentWeaponIndex(getCurrentWeaponIndex()+1)
	 */
	@Raw
	public void selectNextWeapon() {
		if (currentWeaponIndex >= (weaponTypeList.size() - 1)) {
			this.setCurrentWeaponIndex(0);
		} else {
			this.setCurrentWeaponIndex(this.getCurrentWeaponIndex() + 1);
		}
	}

	/**
	 * Make this worm shoot with his weapon and the given propulsion at
	 * the cost of action points.
	 * 
	 * @param 	yield
	 * 			This worm shoots his weapon with the given propulsion.
	 * @effect	If the this worm can shoot, shoot the weapon with the
	 * 			given propulsion.
	 * 			|if (canShoot(getWeapon))
	 * 			|	then getWeapon.shoot(getWorld(), this worm, yield)
	 * @effect	If the this worm can shoot, remove the action points needed 
	 * 			to fire this weapon.
	 * 			|	then setActionPoints(ACTION POINTS NEEDED)
	 */
	public void shoot(int yield) {
		if (this.canShoot(this.getWeapon())) {
			this.getWeapon().shoot(this.getWorld(), this, yield);
			this.setActionPoints((this.getActionPoints() - getWeapon()
					.getActionPointsCost()));
		}
	}

	/**
	 * Check if this worm can shoot with the given weapon.
	 * 
	 * @param 	weapon
	 * 			The weapon to check if this worm can shoot it.
	 * @return	True if this worm has enough action points to shoot.
	 * 			|(getActionPoints() - ACTION POINTS NEEDED) >= 0
	 */
	public boolean canShoot(Weapon weapon) {
		return (this.getActionPoints() - weapon.getActionPointsCost()) >= 0;
	}

	/**
	 * Refresh this worm to start a new round by replenishing this worms action points to
	 * its max value and regenerate a certain amount of hit points.
	 * 
	 * @effect 	This worm's action points will be set to this worm's maximum action points
	 * 			|setActionPoints(getMaxActionPoints())
	 * @effect	Add a certain amount of hit points to the hit points of this worm.
	 * 			|addHealt(AMOUNT)
	 */
	protected void refresh() {
		int REGENERATION_OF_HEALTH = 10;
		this.setActionPoints(this.getMaxActionPoints());
		this.addHealt(REGENERATION_OF_HEALTH);
	}

	/**
	 * Variable to register the looking direction of this worm.
	 */
	private double direction;

	/**
	 * Return true if the given direction is a valid direction
	 * 
	 * @param 	direction
	 * 			The direction to check whether it is a valid one.
	 * @return	Return true if the given direction is a number between
	 * 			0 and 2*PI
	 * 			|(direction > 0) && (direction < Math.PI*2)
	 */
	@Raw
	public static boolean isValidDirection(double direction) {
		return (direction >= 0) && (direction < Math.PI * 2);
	}

	/**
	 * Return the looking direction of this worm.
	 * 
	 * @return	The looking direction of this worm.
	 */
	@Basic
	@Raw
	public double getDirection() {
		return direction;
	}

	/**
	 * Set the direction of this worm to the given direction
	 * 
	 * @param 	direction
	 * 			The new direction of this worm
	 * @pre		The given direction must be a number
	 * 			| direction != Double.NaN
	 * @post	If the new direction of this worm is positive after calculated
	 * 			module 2*PI, the direction is added to the base (2*PI) to get
	 * 			a positive direction between 0 and 2*PI.
	 * 			| if (direction % (Math.PI * 2) <0)
	 * 			|	then new.getDirection = (direction % (Math.PI * 2) + 2*Math.PI)
	 * @post	Else the new direction of this worm is positive after calculated
	 * 			module 2*PI, the direction of this worm is set to this number.
	 * 			| new.getDirection() == direction % (Math.PI*2)
	 */
	@Raw
	@Model
	private void setDirection(double direction) {
		if (direction % (Math.PI * 2) < 0)
			this.direction = (direction % (Math.PI * 2) + 2 * Math.PI);
		else
			this.direction = (direction % (Math.PI * 2));
	}

	/**
	 * Variable holding the radius of this worm
	 */
	private double radius;

	/**
	 * Return the radius of this worm.
	 * 
	 * @return	The radius of this worm.
	 */
	@Basic
	@Raw
	public double getRadius() {
		return radius;
	}

	/**
	 * Set the radius of this worm to the given radius if this given radius is valid.
	 * 
	 * @param radius
	 * 			The new radius of this worm.
	 * @effect	The mass of this worm is set to the calculated mass of this worm.
	 * 			| setMass(calcMass(radius))
	 * @effect	The action points of this worm are set to the old amount of action points.
	 * 			|setActionPoints(getActionPoints())
	 * @post	If the given radius is valid, then the new radius of this worm is equal
	 * 			to the given radius.
	 * 			| if(isValidRadius)
	 * 			| 	then new.getRadius() = radius
	 * @throws 	IllegalArgumentException
	 * 			The given radius is an invalid radius.
	 * 			| !isValidRadius(radius)
	 */
	@Raw
	public void setRadius(double radius) throws IllegalArgumentException {
		if (!isValidRadius(radius))
			throw new IllegalArgumentException();
		this.radius = radius;
		this.setMass(Worm.calcMass(radius));
		this.setActionPoints(this.getActionPoints());
	}

	/**
	 * Check if the given radius is a given radius.
	 * 
	 * @param 	radius
	 * 			The radius to check whether it is a valid one.
	 * @return	True if the given radius is valid.
	 * 			| radius >= getMinRadius();
	 */
	@Raw
	public static boolean isValidRadius(double radius) {
		return radius >= getMinRadius();
	}

	/**
	 * Return the minimal radius this worm should have.
	 * 
	 * @return	The minimal radius this worm should have.
	 */
	@Immutable
	@Raw
	public static double getMinRadius() {
		return 0.25;
	}

	/**
	 * Variable holding the name of this worm.
	 */
	private String name;

	/**
	 * Return the name of this worm.
	 * 
	 * @return	The name of this worm.
	 */
	@Basic
	@Raw
	public String getName() {
		return name;
	}

	/**
	 * Set the name of this worm to the given name.
	 * 
	 * @param 	name
	 * 			The new name this worm should have.
	 * @post	The given name is the new name of this worm.
	 * 			| new.getName() == name
	 * @throws 	IllegalArgumentException
	 * 			The name is an invalid name.
	 * 			| !isValidName(name)
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException {
		if (!isValidName(name))
			throw new IllegalArgumentException();
		this.name = name;
	}

	/**
	 * Check whether a given name is a valid name.
	 * 
	 * @param 	name
	 * 			The name to check whether it is valid or not.
	 * @return	True if the name is a valid name based on the 
	 * 			characters allowed in the name.
	 * 			| name.matches(A regular expression with the allowed characters);
	 */
	public static boolean isValidName(String name) {
		return name.matches("[A-Z][0-9a-zA-Z\'\" ]+");
	}

	/**
	 * Variable containing the mass of this worm.
	 */
	private double mass;

	/**
	 * Return the mass of this worm.
	 * 
	 * @return	The mass of this worm.
	 */
	@Basic
	@Raw
	public double getMass() {
		return mass;
	}

	/**
	 * Set the mass of this worm to the given mass
	 * 
	 * @param	mass
	 * 			The new mass of this worm.
	 * @post	The mass of the worm will now be equal to the given mass
	 * 			|new.getMass() = mass
	 * @throws	IllegalArgumentException
	 * 			The given mass is negative.
	 * 			| mass<0
	 */
	@Raw
	@Model
	private void setMass(double mass) throws IllegalArgumentException {
		if (mass < 0)
			throw new IllegalArgumentException();
		this.mass = mass;
	}

	/** 
	 * Calculates the mass of the worm by multiplying its density 
	 * by its volume.
	 * 
	 * @param	radius
	 * 			The radius of a worm to calculate the mass of that worm.
	 * @return	Returns the mass of a worm with the given radius.
	 * 			| DENSITY_OF_THE_WORM * VOLUME;
	 * @throws	IllegalArgumentException
	 * 			The radius is an invalid radius.
	 * 			| !isValidRadius(radius)
	 */
	public static double calcMass(double radius)
			throws IllegalArgumentException {
		final int DENSITY_OF_THE_WORM = 1062;

		if (!isValidRadius(radius))
			throw new IllegalArgumentException();
		return DENSITY_OF_THE_WORM * (4.0 / 3.0) * Math.pow(radius, 3)
				* Math.PI;
	}

	/**
	 * Variable holding the number of hit points of this worm 
	 */
	private int hitPoints;

	/** 
	 * Returns the current hit points of this worm.
	 * @return 	the hitPoints of this worm
	 */
	@Basic
	@Raw
	public int getHitPoints() {
		return hitPoints;
	}

	/**
	 * Set the number of hi tpoints of this worm to the given number of points and
	 * terminate this worm if he has zero hit points.
	 * 
	 * @param 	hitPoints
	 * 			The new number of hit points of this worm.
	 * @post	If the given hit points are negative, the hit points of
	 * 			this worm are set to zero.
	 * 			| if (hitPoints < 0)
	 * 			|	then new.getHitPoints() == 0
	 * @post	Else if the given hit points are greater then the maximum amount
	 * 			of hit point, the hit points of this worm are set to the
	 * 			maximum amount.
	 * 			| else if (hitPoints > this.getMaxHitPoints())
	 * 			| 	then new.getHitPoints() == this.getHitPoints()
	 * @post	Else the action points of this worm are set to the given 
	 * 			amount of action points.
	 * 			| else
	 * 			|	then new.getHitPoints() == hitPoints
	 * @effect	If (after changing) the hit points of this worm are zero, terminate
	 * 			this worm.
	 * 			|if (getHitpoints() <= 0)
	 * 			|	then terminate()
	 */
	@Raw
	@Model
	private void setHitPoints(int hitPoints) {
		if (hitPoints < 0) {
			this.hitPoints = 0;
		} else if (hitPoints > this.getMaxHitPoints()) {
			this.hitPoints = this.getMaxHitPoints();
		} else {
			this.hitPoints = hitPoints;
		}
		if (getHitPoints() <= 0) {
			terminate();
		}
	}

	/**
	 * Return the maximum number of hit points of this worm.
	 * 
	 * @return	The maximum number of hit points of this worm.
	 * 			|round(getMass())
	 */
	public int getMaxHitPoints() {
		return (int) Math.round(getMass());
	}

	/**
	 * Add a given amount to the hit points of this worm. If the given
	 * amount is less than zero the worm ends with less hit points as
	 * before.
	 * 
	 * @param 	amount 
	 * 			The amount of hit points to add to this worms hit points.
	 * @Effect	Add the given amount of hit points to the hit points of this
	 * 			worm.
	 * 			|setHitPoints(getHitpoints() + amount) 
	 */
	@Model
	protected void addHealt(int amount) {
		this.setHitPoints(this.getHitPoints() + amount);
	}

	/**
	 * Return true if the given amount of hit points is a valid
	 * amount of action points.
	 * 
	 * @param 	hitPoints
	 * 			The amount of hit points to check whether it is a valid amount.
	 * @return	Return true if the given amount of hit points is not
	 * 			negative and less or equal to the maximum amount of hit points
	 * 			of this worm.
	 * 			|(hitPoints >=0) && (hitPoints <= getMass())
	 */
	@Raw
	public boolean canHaveAsHitPoints(int hitPoints) {
		return (hitPoints >= 0) && (hitPoints <= getMaxHitPoints());
	}

	/**
	 * Variable holding the number of action points of this worm.
	 */
	private int actionPoints;

	/**
	 * Return true if the given amount of action points is a valid
	 * amount of action points.
	 * 
	 * @param 	actionPoints
	 * 			The amount of action points to check whether it is a valid amount.
	 * @return	Return true if the given amount of action points is not
	 * 			negative and less or equal to the maximum amount of action points
	 * 			of this worm.
	 * 			|(actionPoints >=0) && (actionPoints <= getMaxActionPoints())
	 */
	@Raw
	public boolean canHaveAsActionPoints(int actionPoints) {
		return (actionPoints >= 0) && (actionPoints <= getMaxActionPoints());
	}

	/**
	 * Return the number of action points of this worm.
	 * 
	 * @return	The number of action points of this worm.
	 */
	@Basic
	@Raw
	public int getActionPoints() {
		return actionPoints;
	}

	/**
	 * Set the number of action points of this worm to the given number of points.
	 * If this worm ends with zero action points, the turn of this worm ends and
	 * the turn of the next worm in the world starts.
	 * 
	 * @param 	actionPoints
	 * 			The new number of action points of this worm.
	 * @post	If the given action points are negative, the action points of
	 * 			this worm are set to zero.
	 * 			| if (actionPoints < 0)
	 * 			|	then new.getActionPoints() == 0
	 * @post	Else if the given action points are greater then the maximum amount
	 * 			of action point, the action points of this worm are set to the
	 * 			maximum amount.
	 * 			| else if (actionPoints > this.getMaxActionPoints())
	 * 			| 	then new.getActionPoints() == this.getActionPoints()
	 * @post	Else the action points of this worm are set to the given 
	 * 			amount of action points.
	 * 			| else
	 * 			|	then new.getActionPoints() == actionPoints
	 * @effect	If (after changing) the action points of this worm are zero, the
	 * 			next worm starts its turn and this worms turn ends.
	 * 			|if (getHitpoints() <= 0)
	 * 			|	then getWorld().startNextTurn()
	 */
	@Raw
	@Model
	private void setActionPoints(int actionPoints) {
		if (actionPoints < 0) {
			this.actionPoints = 0;
		} else if (actionPoints > this.getMaxActionPoints()) {
			this.actionPoints = this.getMaxActionPoints();
		} else
			this.actionPoints = actionPoints;
		if (getActionPoints() <= 0) {
			getWorld().startNextTurn();
		}
	}

	/**
	 * Return the maximum number of action points of this worm.
	 * 
	 * @return	The maximum number of action points of this worm.
	 * 			|round(getMass())
	 */
	public int getMaxActionPoints() {
		return (int) Math.round(getMass());
	}

	// Movement

	/**
	 * Returns if this worm is able to turn or not
	 * 
	 * @return 	a boolean that states if this worm is able to turn or not
	 * 			based on its action points, the given angle and the cost
	 * 			to turn a little. The expense of action points is rounded up
	 * 			to an integer.
	 * 			|getActionPoints() >= roundUp(COST * ANGLE) 
	 */
	public boolean canTurn(double angle) {
		return getActionPoints() >= (int) Math
				.ceil(60 * ((Math.abs(angle) % (Math.PI * 2)) / (2 * Math.PI)));
	}

	/** 
	 * Turns this worm over a given angle at the expense of action points.
	 * The expense of action points is rounded up.
	 * 
	 * @param	angle
	 * 			The angle this worm should turn this direction.
	 * @pre		the worm has to have enough action points left to make the turn
	 * 			|canTurn(angle)
	 * @post	The new direction of this worm is equal to its old direction
	 * 			added with the given angle to turn.
	 * 			|new.getDirection() = this.getDirection + angle
	 * @post	The amount of action points of this worm is decreased by an amount
	 * 			of action points based on the given angle and the cost to turn a little.
	 * 			This amount is rounded up to an integer. 
	 * 			|new.getActionPoints() = this.getActionPoints() - roundUp(COST * ANGLE)
	 */
	public void turn(double angle) {
		assert (this.canTurn(angle));
		setDirection(getDirection() + angle);
		setActionPoints(getActionPoints()
				- (int) Math
						.ceil(60 * ((Math.abs(angle) % (Math.PI * 2)) / (2 * Math.PI))));
	}

	/** 
	 * Returns if this worm is able to move.
	 * 
	 * @return 	Return a boolean that states if this worm is able to move or not
	 *			based on its action points and the cost
	 * 			to move a step in the direction of this worm. The amount of action points
	 * 			is rounded up to an integer.	
	 * 			|getActionPoints() >= roundUp(COST)
	 */
	public boolean canMove() {
		double[] bestStepData = getBestStep();
		int actionPointsNeeded = (int) Math
				.ceil((bestStepData[0] / getRadius())
						* (Math.abs(Math.cos(bestStepData[1])) + 4 * Math
								.abs(Math.sin(bestStepData[1]))));
		return getActionPoints() >= actionPointsNeeded;
	}

	/**	
	 * Let this worm move in the direction of this worm
	 * at the expense of an amount of action points.
	 * 
	 * @effect	The new coordinates of this worm are set to the new position
	 *			|setPosition(NEW X, NEW Y)
	 * @effect	The number of action points of this worm is decreased by the amount based on the 
	 * 			the cost to move a step in the direction of this worm.
	 * 			This expense is rounded up to an integer.
	 * 			|setActionPoints(getActionPoints() - roundUp(STEPS_IN_X_DIRECTION * 
	 * 				COST_X + STEPS_IN_Y_DIRECTION * COST_Y)
	 * @effect	If this worm can eat food at his new position, the food gets eaten by him.
	 * 			|if (getWorld().checkWormCanEatFood(getPosition(), getRadius()))
	 * 			|	then getWorld().getFoodEatenBy(this).getEatenBy(this)
	 * @throws	IllegalStateException
	 * 			This worm cannot move the given steps.
	 * 			|!canMove()
	 */
	public void move() throws IllegalStateException {
		if (!canMove()) {
			throw new IllegalStateException();
		}
		double[] bestStepData = getBestStep();
		double xCo = (getXCoordinate() + bestStepData[0]
				* Math.cos(bestStepData[1]));
		double yCo = (getYCoordinate() + bestStepData[0]
				* Math.sin(bestStepData[1]));
		setPosition(xCo, yCo);
		setActionPoints(getActionPoints()
				- (int) Math.ceil((bestStepData[0] / getRadius())
						* (Math.abs(Math.cos(bestStepData[1])) + 4 * Math
								.abs(Math.sin(bestStepData[1])))));
		if (getWorld().checkWormCanEatFood(this)) {
			getWorld().getFoodEatenBy(this).getEatenBy(this);
		}
	}

	/**
	 * Return a double with data of the best step this worm can make calculated
	 * with the radius and direction of this worm. The data is the distance and
	 * the direction of the step.
	 *  
	 * @return	The data of the best possible step this worm can take.
	 * 			|[DISTANCE, DIRECTION]
	 */
	private double[] getBestStep() {

		// This function will run through every possible position in a distance range
		// from the radius of the worm to 0.1 (steps of 0.01) and an angle range from
		// -0.75 to 0.75 radians (steps of 0.0175). The function starts at the farest
		// distance so if it finds the farest (best) adjacent and/or position first.
		// When a position with equal distance and better angle is found, this one is stored.
		// When an adjacent position is found, this one is taken, if not, the best (farest dist,
		// closest angle) step to passable terrain is chosen.

		double[] bestAdj = { 0, -0.75 };
		double[] bestNAdj = { 0, -0.75 };

		double dist = getRadius();
		boolean bestAdjFound = false;
		boolean bestNAdjFound = false;

		while ((!bestAdjFound) && (dist >= 0.1)) {
			double angle = -0.75;

			while (angle <= 0.75) {
				double xCo = (getXCoordinate() + dist
						* Math.cos(getDirection() + angle));
				double yCo = (getYCoordinate() + dist
						* Math.sin(getDirection() + angle));
				//check adjacent end positions
				if ((!bestAdjFound)
						&& getWorld().isAdjacentTerrain(getRadius(), xCo, yCo)) {
					if (Math.abs(angle) < Math.abs(bestAdj[1])) {
						bestAdj[1] = angle;
					}
					bestAdj[0] = dist;
				}
				//check not adjacent end positions
				if ((!bestNAdjFound)
						&& getWorld().isPassable(xCo, yCo, getRadius())) {
					if (Math.abs(angle) < Math.abs(bestNAdj[1])) {
						bestNAdj[1] = angle;
					}
					bestNAdj[0] = dist;
				}
				angle += 0.0175;
			}
			if (bestAdj[0] > 0) {
				bestAdjFound = true;
			}
			if (bestNAdj[0] > 0) {
				bestNAdjFound = true;
			}
			if ((dist != 0.1) && ((dist - 0.1) < 0.01)) {
				dist = 0.1;
			} else {
				dist -= 0.01;
			}
		}
		bestAdj[1] += getDirection();
		bestNAdj[1] += getDirection();
		if (bestAdjFound) {
			return bestAdj;
		} else {
			return bestNAdj;
		}

	}

	/**
	 * Checks if a worm can fall or not.
	 * 
	 * @return	True if the position of this worm is not adjacent
	 * 			to any passable terrain.
	 * 			|!getWorld().isAdjacentTerrain(getRadius(), getXCoordinate(),
				|	getYCoordinate()) 			
	 */
	public boolean canFall() {
		return !getWorld().isAdjacentTerrain(getRadius(), getXCoordinate(),
				getYCoordinate());
	}

	/**
	 * Let this worm fall. After falling the worm gets hit an certain amount of
	 * hit points calculated with the meters he fell down. If this worm is able to
	 * eat foot at his new position, this worm will eat the food. If this worm ends 
	 * outside the world, the worm gets terminated.
	 * 
	 * @effect	Set the new position of this worm to a new position under the
	 * 			current position of this worm.
	 * 			|setPosition(OLD x, NEW Y)
	 * @effect	Add a negative amount of hit points to this worm according to
	 * 			the meters this worm fell down.
	 * 			|addHealth(-CONTANT * METERS FELL DOWN)
	 * @effect	If this worm falls out of the world, get it terminated.
	 * 			|if (getYCoordinate() - getRadius() < 0)
	 * 			|	then terminate()
	 * @effect	Else and If this worm can eat food at its new position, let the worm eat
	 * 			the food.
	 * 			|else
	 * 			|	then if (getWorld().checkWormCanEatFood(getPosition(),...)
	 * 			|		then getWorld().getFoodEatenBy(this).getEatenBy(this)
	 */
	public void fall() {
		Position oldPos = new Position(this.getXCoordinate(),
				this.getYCoordinate());
		while ((!getWorld().isAdjacentTerrain(getRadius(), getXCoordinate(),
				getYCoordinate()))
				&& (getWorld().isPassable(getXCoordinate(), getYCoordinate(),
						getRadius())) && (getYCoordinate() - getRadius() > 0)) {
			setPosition(getXCoordinate(), getYCoordinate() - 0.01);
		}
		if (getYCoordinate() - getRadius() < 0) {
			terminate();
		} else {
			if (((int) (3 * (this.getPosition().distanceTo(oldPos))) < getHitPoints())) {
				if (getWorld().checkWormCanEatFood(this)) {
					getWorld().getFoodEatenBy(this).getEatenBy(this);
				}
			}
			this.addHealt(-(int) (3 * (this.getPosition().distanceTo(oldPos))));
		}
	}

	/**
	 * The constant GRAVITY is used to 	easy manipulate the gravity in the different methods
	 */
	private final double GRAVITY = 9.80665;

	/** 
	 * Calculates the initial velocity this worm has when it jumps.
	 * 
	 * @return 	Return the initial velocity of this worm when it jumps based on the force
	 * 			a worm pushes himself of the ground and its mass. The force of this worm
	 * 			is based on the action points of this worm, its mass and the gravity
	 * 			of the environment.
	 * 			| FORCE / MASS * CONTSTANT
	 */
	private double initialVelocity() {
		double force = 5 * getActionPoints() + getMass() * GRAVITY;
		return force / getMass() * 0.5;
	}

	/** 
	 * Returns if this worm is able to jump or not.
	 * 
	 * @return 	Return a boolean that states if the worm is able to jump or not
	 * 			based on its direction and action points.
	 * 			|(getDirection() < Math.PI) && (getDirection() > 0) && (getActionPoints() > 0);
	 */
	public boolean canJump() {
		return (getActionPoints() > 0);
	}

	/** 
	 * Let this worm jump over a distance and consumes all action points left.
	 * 
	 * @effect	The position of this worm is calculated and set as new position
	 * 			|setPosition(NEW X COORDINATE, NEW Y COORDINATE)
	 * @effect	The new amount of action points is equal to zero.
	 * 			|setActionPoints(0)
	 * @throws	IllegalStateException
	 * 			The worm is not able to jump.
	 * 			| !canJump()
	 */
	public void jump(double timeStep) throws IllegalStateException {
		if (!canJump()) {
			throw new IllegalStateException();
		}
		double[] newPosition = Arrays.copyOfRange(this.possibleJump(timeStep),
				0, 2);
		this.setPosition(newPosition[0], newPosition[1]);
		setActionPoints(0);
		if (getWorld().checkWormCanEatFood(this)) {
			getWorld().getFoodEatenBy(this).getEatenBy(this);
		}
	}

	/**
	 * Return the time a jump of this worm would take.
	 * 
	 * @param	timeStep
	 * 			An elementary time interval used to calculate the jumptime.
	 * @effect 	A theoretical jump will be performed to get the time it takes to jump.
	 * 			|possibleJump(timeStep)
	 * @return	Return the time a jump of this worm would take
	 * 			based on the direction of this worm, the gravity
	 * 			of the environment, the initial velocity and the world of this worm.
	 * @throws 	IllegalStateException
	 * 			The worm cannot jump.
	 * 			| !canjump()
	 */
	public double jumpTime(double timeStep) throws IllegalStateException {
		if (!canJump()) {
			throw new IllegalStateException();
		}

		return this.possibleJump(timeStep)[2];

	}

	/** 
	 * A theoretical jump will be performed to determine the position where this
	 * worm will end his jump. The theoretical jump also calculates the time 
	 * it will take to perform that jump. The calculated position and time will be
	 * returned.
	 * 
	 * @param 	timeStep 
	 * 			An elementary time interval used to calculate the jumptime.
	 * @return	The position where the jump of this worm will end (by reaching terrain
	 * 			that is impassable) and the time it will take to perform that jump.
	 * @Throws	IllegalArgumentException
	 * 			The given timestep is not a number.
	 * 			| timeStep == Double.NaN
	 */
	private double[] possibleJump(double timeStep) {

		// The function will calculate step by step the next position on the
		// jump of this worm
		// and will check if the position is passable.
		// If so, the function will stop and will return the final position and
		// time of the jump.
		// If not, the new position will be stored in a local variable and the
		// next position
		// will be calculated.

		if (timeStep == Double.NaN) {
			throw new IllegalArgumentException();
		}

		Position position = this.getPosition();
		double time = timeStep;
		Position tempPosition;
		boolean jumping = true;

		while (jumping) {
			tempPosition = this.jumpStep(time);
			if (getWorld().isPassable(tempPosition.getXCoordinate(),
					tempPosition.getYCoordinate(), this.getRadius())) {
				position = tempPosition;
				time = time + timeStep;
			} else {
				jumping = false;
			}

		}
		double[] data = { position.getXCoordinate(), position.getYCoordinate(),
				time };
		return data;
	}

	/**
	 * Return the position (x-coordinate, y-coordinate) at a certain time 
	 * during the jump of this worm.
	 * 
	 * @param 	time
	 * 			The time during the jump where you want to know the position of this worm.
	 * @return	The position of this worm at the given time of the jump based on the old 
	 * 			coordinates of this worm, the initial velocity the direction of this worm, 
	 * 			the gravity of the environment and the world of this worm.
	 * @throws 	IllegalArgumentException
	 * 			The given time is negative.
	 * 			| time <=0
	 * @throws 	IllegalStateException
	 * 			The worm cannot jump.
	 * 			| ! canJump()
	 */
	public Position jumpStep(double time) throws IllegalArgumentException,
			IllegalStateException {
		if (!canJump()) {
			throw new IllegalStateException();
		}
		if (time <= 0) {
			throw new IllegalArgumentException();
		}
		double X = getXCoordinate() + initialVelocity()
				* Math.cos(getDirection()) * time;
		double Y = getYCoordinate() + initialVelocity()
				* Math.sin(getDirection()) * time - 0.5 * GRAVITY
				* Math.pow(time, 2);
		Position coord = new Position(X, Y);
		return coord;
	}
	
	/**
	 * Returns true is this entity can have the given world as its world.
	 * 
	 * @param 	world
	 * 			The world to check if this entity can have it as its world.
	 * @return	False if the game in the given world is already started.
	 * 			|if (world.isGameStarted())
	 * 			|	then return false;
	 */
	@Override
	public boolean canHaveAsWorld(World world) {
		
		if (world.isGameStarted()){
			return false;
		}
		return super.canHaveAsWorld(world);
	}

}
