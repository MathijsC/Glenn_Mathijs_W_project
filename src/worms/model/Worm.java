package worms.model;

import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class for a worm objects containing a x-coordinate, y-coordinate
 * looking direction, radius, name and action points of this worm.
 * The class also implements methods to jump, turn and move this worm.
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
 * 
 * @author 	Glenn Cools, Mathijs Cuppens
 *	
 * @version 1.30
 */
public class Worm {

	/**
	 * Initialize this new worm with a given position (x,y), looking direction,
	 * radius and name.
	 * 
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
	 * @effect	The looking direction of the new worm is equal to the given
	 * 			direction modulo 2*PI.
	 * 			| setDirection(direction)
	 * @effect	If the given radius is smaller then the lower bound, then the 
	 * 			radius of this new worm is equal to the lower bound. 
	 * 			Else the radius of this new worm is equal to the given radius.
	 * 			| setRadius(radius)
	 * @effect	If the given name is a valid name, the name of this new worm is
	 * 			equal to the given name.
	 * 			| setName(name)
	 * @post	The mass of this new worm is set to a value calculated like a
	 * 			sphere density and the given radius.
	 * 			| new.getMass() == calcMass(radius)
	 * @post	The action points of this new worm is set the the maximum possible
	 * 			action points for this new worm in accordance to its mass.
	 * 			| new.getActionPoints == getMaxActionPoints()
	 */
	@Raw
	public Worm(double x, double y, double direction, double radius, String name) {
		setXCoordinate(x);
		setYCoordinate(y);
		setDirection(direction);
		setRadius(radius);
		setActionPoints(getMaxActionPoints());
		setName(name);
	}
	
	public Worm(World world) {
		Random seed = world.getSeed();
		setXCoordinate(seed.nextDouble()*world.getWidth());
		setYCoordinate(seed.nextDouble()*world.getHeight());
		setRadius(getMinRadius());
		setDirection(seed.nextDouble()*2*Math.PI);
		setActionPoints(getMaxActionPoints());
		setHitPoints(getMaxHitPoints());
		setState(true);
		setName("Glenn");
	}
	
	private boolean state;
	
	public void setState(boolean state){
		this.state = state;
	}
	
	public boolean getState(){
		return this.state;
	}
	
	public void refresh(){
		this.setActionPoints(getMaxActionPoints());
		this.setHitPoints(getHitPoints()+10);
	}

	/**
	 * Variable registering the x-coordinate of the position of this worm.
	 */
	private double xCoordinate;

	/**
	 * Return the x-coordinate of the position of this worm.
	 * 
	 * @return	The x-coordinate of the position of this worm.
	 */
	@Basic @Raw
	public double getXCoordinate() {
		return xCoordinate;
	}

	/**
	 * Set the x-coordinate of the position of this worm to the given xCoordinate.
	 * 
	 * @param 	xCoordinate
	 * 			The x-coordinate to be set as the position of this worm
	 * @post	The given xCoordinate is the new position of this worm.
	 * 			| new.getXCoordinate() == xCoordinate
	 */
	@Raw
	private void setXCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	/**
	 * Variable registering the y-coordinate of the position of this worm.
	 */
	private double yCoordinate;

	/**
	 * Return the y-coordinate of the position of this worm.
	 * 
	 * @return	The y-coordinate of the position of this worm.
	 */
	@Basic @Raw
	public double getYCoordinate() {
		return yCoordinate;
	}

	/**
	 * Set the y-coordinate of the position of this worm to the given yCoordinate.
	 * 
	 * @param 	yCoordinate
	 * 			The x-coordinate to be set as the position of this worm
	 * @post	The given yCoordinate is the new position of this worm.
	 * 			| new.getYCoordinate() == yCoordinate
	 */
	@Raw
	private void setYCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
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
	public static boolean isValidDirection(double direction){
		return (direction >= 0) && (direction < Math.PI*2);
	}

	/**
	 * Return the looking direction of this worm.
	 * 
	 * @return	The looking direction of this worm.
	 */
	@Basic @Raw
	public double getDirection() {
		return direction;
	}

	/**
	 * Set the direction of this worm to the given direction
	 * 
	 * @param 	direction
	 * 			The new direction of this worm
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
	private void setDirection(double direction) {
		if (direction % (Math.PI * 2) <0)
			this.direction = (direction % (Math.PI * 2) + 2*Math.PI);
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
	@Basic @Raw
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
	@Basic @Raw
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
		return name.matches("[A-Z][a-zA-Z\'\" ]+");
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
	@Basic @Raw
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
	public static double calcMass(double radius) throws IllegalArgumentException {
		final int DENSITY_OF_THE_WORM = 1062;

		if (!isValidRadius(radius))
			throw new IllegalArgumentException();
		return DENSITY_OF_THE_WORM * (4.0 / 3.0) * Math.pow(radius, 3) * Math.PI;
	}
	/**
	 * Variable holding the number of hitpoints of this worm 
	 */
	private int hitPoints;
	
	/** returns the current hitpoints of this worm
	 * @return the hitPoints of this worm
	 */
	public int getHitPoints() {
		return hitPoints;
	}

	/**
	 * Set the number of hitpoints of this worm to the given number of points.
	 * 
	 * @param 	hitPoints
	 * 			The new number of hitpoints of this worm.
	 * @post	If the given hitpoints are negative, the hitpoints of
	 * 			this worm are set to zero.
	 * 			| if (hitPoints < 0)
	 * 			|	then new.getHitPoints() == 0
	 * @post	Else if the given hitpoints are greater then the maximum amount
	 * 			of hitpoint, the hitpoints of this worm are set to the
	 * 			maximum amount.
	 * 			| else if (hitPoints > this.getMaxHitPoints())
	 * 			| 	then new.getHitPoints() == this.getHitPoints()
	 * @post	Else the action points of this worm are set to the given 
	 * 			amount of action points.
	 * 			| else
	 * 			|	then new.getHitPoints() == hitPoints
	 */
	@Raw
	private void setHitPoints(int hitPoints) {
		if (hitPoints < 0)
			this.hitPoints = 0;
		else if (hitPoints > this.getMaxHitPoints())
			this.hitPoints = this.getMaxHitPoints();
		else
			this.hitPoints = hitPoints;
	}

	/**
	 * Return the maximum number of hitpoints of this worm.
	 * 
	 * @return	The maximum number of hitpoints of this worm.
	 * 			|(int) Math.round(getMass());
	 */
	public int getMaxHitPoints() {
		return (int) Math.round(getMass());
	}
	
	/**
	 * heals this worm for a given amount of hitpoints
	 * @param amount of hitPoints the worm will heal
	 * @Effect	setHitPoints(getHitpoints() + amount)
	 * 
	 */
	public void heal(int amount){
		this.setHitPoints(this.getHitPoints()+amount);
	}
	
	
	/**
	 * Variable holding the number of action points of this worm.
	 */
	private int actionPoints;
	
	/**
	 * Return true if the given amount of action points if a valid
	 * amount of action points.
	 * 
	 * @param 	actionPoints
	 * 			The amount of action points to check whether it is a valid amount.
	 * @return	Return true if the given amount of action points is not
	 * 			negative and less or equal to the maximum amount of action points
	 * 			of this worm.
	 * 			|(actionPoints >=0) && (actionPoints <= getMaxActionPoints())
	 */
	public boolean canHaveAsActionPoints(int actionPoints){
		return (actionPoints >=0) && (actionPoints <= getMaxActionPoints());
	}

	/**
	 * Return the number of action points of this worm.
	 * 
	 * @return	The number of action points of this worm.
	 */
	@Basic @Raw
	public int getActionPoints() {
		return actionPoints;
	}

	/**
	 * Set the number of action points of this worm to the given number of points.
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
	 */
	@Raw
	private void setActionPoints(int actionPoints) {
		if (actionPoints < 0)
			this.actionPoints = 0;
		else if (actionPoints > this.getMaxActionPoints())
			this.actionPoints = this.getMaxActionPoints();
		else
			this.actionPoints = actionPoints;
	}

	/**
	 * Return the maximum number of action points of this worm.
	 * 
	 * @return	The maximum number of action points of this worm.
	 * 			|(int) Math.round(getMass());
	 */
	public int getMaxActionPoints() {
		return (int) Math.round(getMass());
	}
	/**
	 * Replenishes this worms action points to its max
	 * @post this worm's action points will be set to this worm's maximum action points
	 * 			|new.getActionPoints()==this.getMaxActionPoints()
	 */
	public void replenishActionPoints(){
		this.setActionPoints(this.getMaxActionPoints());
	}
	// Movement

	/**
	 * Returns if this worm is able to turn or not
	 * 
	 * @return 	a boolean that states if this worm is able to turn or not
	 * 			based on its action points, the given angle and the cost
	 * 			to turn a little. The expense of action points is rounded up
	 * 			to an integer.
	 * 			|getActionPoints() >= (int) Math.ceil(COST * ANGLE); 
	 */
	public boolean canTurn(double angle) {
		return getActionPoints() >= (int) Math.ceil(60 * ((Math.abs(angle) % (Math.PI * 2)) / (2 * Math.PI)));
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
	 * 			|new.getActionPoints() = this.getActionPoints() - 
	 * 			|	(int) Math.ceil(COST * ANGLE);
	 */
	public void turn(double angle) {
		assert(this.canTurn(angle));
		setDirection(getDirection() + angle);
		setActionPoints(getActionPoints()
				- (int) Math.ceil(60 * ((Math.abs(angle) % (Math.PI * 2)) / (2 * Math.PI))));
	}

	/** 
	 * Returns if this worm is able to move the given amount of steps.
	 * 
	 * @param	steps
	 * 			The amount of steps that is checked if the worm is able to move them.
	 * @return 	Return a boolean that states if this worm is able to turn or not
				based on its action points, the given amount of steps and the cost
	 * 			to move one step in the direction of this worm.	The amount of action points
	 * 			is rounded up to an integer.	
	 * 			|getActionPoints() >= (int) Math.ceil(steps * COST)
	 * @throws	IllegalArgumentException
	 * 			The steps are negative.
	 * 			| steps <0
	 */
	public boolean canMove(int steps) throws IllegalArgumentException {
		if (steps < 0) {
			throw new IllegalArgumentException();
		}
		return getActionPoints() >= (int) Math.ceil(steps
				* (Math.abs(Math.cos(getDirection())) + 4 * Math.abs(Math
						.sin(getDirection()))));
	}

	/**	
	 * Let this worm move a given amount of steps in the direction of this worm
	 * at the expense of an amount of action points.
	 * 
	 * @param	steps
	 * 			The amount of steps this worm will move.
	 * @post	The new coordinates of this worm are equal to the old coordinate added to
	 * 			the amount of steps moved in the direction of this worm. The length of a 
	 * 			step is based on the radius of this worm.
	 *			|new.getXCoordinate() = this.getXCoordinate() + getRadius()*STEPS_IN_X_DIRECTION
	 *			|new.getYCoordinate() = this.getYCoordinate() + getRadius()*STEPS_IN_Y_DIRECTION;
	 * @post	The number of action points of this worm is decreased by the amount based on the 
	 * 			given amount of steps and the cost to move one step in the direction of this worm.
	 * 			This expense is rounded up to an integer.
	 * 			|new.getActionPoints = this.getActionPoints - (int) Math.ceil(STEPS_IN_X_DIRECTION * 
	 * 				COST_X + STEPS_IN_Y_DIRECTION * COST_Y)
	 * @throws	IllegalStateException
	 * 			This worm cannot move the given steps.
	 * 			| !canMove(steps)
	 */
	public void move(int steps) throws IllegalStateException {
		if (!canMove(steps)) {
			throw new IllegalStateException();
		}
		setXCoordinate(getXCoordinate() + getRadius() * steps
				* Math.cos(getDirection()));
		setYCoordinate(getYCoordinate() + getRadius() * steps
				* Math.sin(getDirection()));
		setActionPoints(getActionPoints()
				- (int) Math.ceil(steps
						* (Math.abs(Math.cos(getDirection())) + 4 * Math
								.abs(Math.sin(getDirection())))));

	}

	/**
	 * The constant GRAVITY is used to 	easy manipulate the gravity in the different methods
	 */
	public final double GRAVITY = 9.80665;

	/** 
	 * Calculates the initial velocity this worm has when it jumps.
	 * 
	 * @return 	Return the initial velocity of this worm when it jumps based on the force
	 * 			a worm pushes himself of the ground and its mass. The force of this worm
	 * 			is based on the action points of this worm, its mass and the gravity
	 * 			of the environment.
	 * 			| FORCE / MASS * CONTSTANT
	 */
	public double initialVelocity() {
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
		return (getDirection() < Math.PI) && (getDirection() > 0)
				&& (getActionPoints() > 0);
	}

	/** 
	 * Let this worm jump over a distance and consumes all action points left.
	 * 
	 * @post	The new X-coordinate of this worm is equal to the old X-coordinate added
	 * 			with the distance moved horizontally based on the gravity of the environment
	 * 			and the direction of this worm. The Y-coordinate stays the same.
	 * 			|new.getXCoordinate() = this.getXCoordinate + DISTANCE_MOVED
	 * 			|new.getYCoordinate() = this.getYCoordinate()
	 * @post	The new amount of action points is equal to zero.
	 * 			|new.getActionPoints = 0
	 * @throws	IllegalStateException
	 * 			The worm is not able to jump.
	 * 			| !canJump()
	 */
	public void jump() throws IllegalStateException {
		if (!canJump()) {
			throw new IllegalStateException();
		}
		double dist = Math.pow(initialVelocity(), 2)
				* Math.sin(2 * getDirection()) / GRAVITY;
		setXCoordinate(getXCoordinate() + dist);
		setActionPoints(0);
	}
	/**
	 * Return the time a jump of this worm would take.
	 * 
	 * @return	Return the time a jump of this worm would take
	 * 			based on the direction of this worm, the gravity
	 * 			of the environment and the initial velocity.
	 * @throws 	IllegalStateException
	 * 			The worm cannot jump.
	 * 			| !canjump()
	 */
	public double jumpTime() throws IllegalStateException {
		if (!canJump()) {
			throw new IllegalStateException();
		}
		return this.initialVelocity() * Math.sin(2 * getDirection())
				/ (GRAVITY * Math.cos(getDirection()));

	}
	
	/**
	 * Return the position (x-coordinate, y-coordinate) at a certain time 
	 * during the jump.
	 * 
	 * @param 	time
	 * 			The time during the jump where you want to know the position of this worm.
	 * @return	Return the position of this worm at the given time of 
	 * 			the jump based on the old coordinates of this worm, the initial velocity
	 * 			the direction of this worm and de gravity of the environment.
	 * @throws 	IllegalArgumentException
	 * 			The given time is not during the jump.
	 * 			| time <=0
	 * 			| time > this.jumpTime()
	 * @throws 	IllegalStateException
	 * 			The worm cannot jump.
	 * 			| ! canJump()
	 */
	public double[] jumpStep(double time) throws IllegalArgumentException,IllegalStateException {
		if (!canJump()) {
			throw new IllegalStateException();
		}
		if (time <= 0) {
			throw new IllegalArgumentException();
		}
		if (time > this.jumpTime()){
			throw new IllegalArgumentException();
		}
		double X = getXCoordinate() + initialVelocity()
				* Math.cos(getDirection()) * time;
		double Y = getYCoordinate() + initialVelocity()
				* Math.sin(getDirection()) * time - 0.5 * GRAVITY
				* Math.pow(time, 2);
		double[] coord = { X, Y };
		return coord;
	}

}
