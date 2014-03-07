package worms.model;

import be.kuleuven.cs.som.annotate.Basic;

/**
 * A class for the worm objects who are used in the game Worms.
 * @author Glenn Cools, Mathijs Cuppens
 *	
 * @version 1.10
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
	 * @post	The looking direction of the new worm is equal to the given
	 * 			direction modulo 2*PI.
	 * 			| new.getDirection() == direction % (Math.PI*2)
	 * @post	If the given radius is smaller then the lower bound, then the 
	 * 			radius of this new worm is equal to the lower bound. 
	 * 			Else the radius of this new worm is equal to the given radius.
	 * 			| if (radius < getMinRadius())
	 * 			|	then radius == getMinRadius()
	 * 			| else
	 * 			|	then radius == radius
	 * @post	The mass of this new worm is set to a value calculated like a
	 * 			sphere density and the given radius.
	 * 			| new.getMass() == calcMass(radius)
	 * @post	The action points of this new worm is set the the maximum possible
	 * 			action points for this new worm in accordance to its mass.
	 * 			| new.getActionPoints == getMaxActionPoints()
	 * @post	If the given name is a valid name, the name of this new worm is
	 * 			equal to the given name.
	 * 			| if (isValidName(name))
	 * 			|	then new.getName == name
	 */
	public Worm(double x, double y, double direction, double radius, String name) {
		setXCoordinate(x);
		setYCoordinate(y);
		setDirection(direction);
		setRadius(radius);
		setMass(calcMass(radius));
		setActionPoints(getMaxActionPoints());
		setName(name);
	}

	/**
	 * Variable registering the x-coordinate of the position of this worm.
	 */
	public double xCoordinate;

	/**
	 * Return the x-coordinate of the position of this worm.
	 * 
	 * @return	The x-coordinate of the position of this worm.
	 */
	@Basic
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
	public void setXCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	/**
	 * Variable registering the y-coordinate of the position of this worm.
	 */
	public double yCoordinate;

	/**
	 * Return the y-coordinate of the position of this worm.
	 * 
	 * @return	The y-coordinate of the position of this worm.
	 */
	@Basic
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
	public void setYCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	/**
	 * Variable to register the looking direction of this worm.
	 */
	public double direction;

	/**
	 * Return the looking direction of this worm.
	 * 
	 * @return	The looking direction of this worm.
	 */
	@Basic
	public double getDirection() {
		return direction;
	}

	/**
	 * Set the direction of this worm to the given direction
	 * 
	 * @param 	direction
	 * 			The new direction of this worm
	 * @post	The new direction of this worm is equal to the
	 * 			given direction calculated module 2*PI
	 * 			| new.getDirection() == direction % (Math.PI*2)
	 */
	public void setDirection(double direction) {
		this.direction = (direction % (Math.PI * 2));
	}

	/**
	 * Variable holding the radius of this worm
	 */
	public double radius;

	/**
	 * Return the radius of this worm.
	 * 
	 * @return	The radius of this worm.
	 */
	@Basic
	public double getRadius() {
		return radius;
	}
	/**
	 * Set the radius of this worm to the given radius if this given radius is valid.
	 * 
	 * @param radius
	 * 			The new radius of this worm.
	 * @post	If the given radius is valid, then the new radius of this worm is equal
	 * 			to the given radius.
	 * 			| if(isValidRadius)
	 * 			| 	then new.radius = radius
	 * @throws 	IllegalArgumentException
	 * 			The given radius is an invalid radius.
	 * 			| !isValidRadius(radius)
	 */
	public void setRadius(double radius) throws IllegalArgumentException {
		if (!isValidRadius(radius))
			throw new IllegalArgumentException();
		this.radius = radius;
	}
	/**
	 * Check if the given radius is a given radius.
	 * 
	 * @param 	radius
	 * 			The radius to check whether it is a valid one.
	 * @return	True if the given radius is valid.
	 * 			| radius >= getMinRadius();
	 */
	public boolean isValidRadius(double radius) {
		return radius >= getMinRadius();
	}

	/**
	 * Return the minimal radius this worm should have.
	 * 
	 * @return	The minimal radius this worm should have.
	 */
	public double getMinRadius() {
		return 0.25;
	}

	/**
	 * Variable holding the name of this worm.
	 */
	public String name;

	/**
	 * Return the name of this worm.
	 * 
	 * @return	The name of this worm.
	 */
	@Basic
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
	 * @return	True if the name starts with a Capital letter
	 * 			and contains only letter, spaces, single quotes
	 * 			and/or double quotes and is at least two characters
	 * 			long.
	 * 			| name.matches("[A-Z][a-zA-Z\'\" ]+");
	 */
	public boolean isValidName(String name) {
		return name.matches("[A-Z][a-zA-Z\'\" ]+");
	}

	/**
	 * Variable containing the mass of this worm.
	 */
	public double mass;

	/**
	 * Return the mass of this worm.
	 * 
	 * @return	The mass of this worm.
	 */
	@Basic
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
	public void setMass(double mass) throws IllegalArgumentException {
		if (mass < 0)
			throw new IllegalArgumentException();
		this.mass = mass;
	}

	/** 
	 * Calculates the mass of the worm by multiplying its density 
	 * by its volume (spherical: 4/3 * PI * radius)
	 * 
	 * @param	radius
	 * 			The radius of a worm to calculate the mass of that worm.
	 * @post	The worm will now have a mass equal to the product of its density and volume
	 * 			|new.getMass() = DENSITY_OF_THE_WORM*VOLUME with VOLUME= 4/3*r^3*PI
	 * @return	Returns the mass of a worm with the given radius.
	 * 			| DENSITY_OF_THE_WORM * (4 / 3) * Math.pow(radius, 3) * Math.PI;
	 * @throws	IllegalArgumentException
	 * 			The radius is an invalid radius.
	 * 			| !isValidRadius(radius)
	 */
	public double calcMass(double radius) throws IllegalArgumentException {
		final int DENSITY_OF_THE_WORM = 1062;

		if (!isValidRadius(radius))
			throw new IllegalArgumentException();

		return DENSITY_OF_THE_WORM * (4 / 3) * Math.pow(radius, 3) * Math.PI;
	}

	/**
	 * Variable holding the number of action points of this worm.
	 */
	public int actionPoints;

	/**
	 * Return the number of action points of this worm.
	 * 
	 * @return	The number of action points of this worm.
	 */
	@Basic
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
	public void setActionPoints(int actionPoints) {
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

	// Movement

	/**
	 * Returns if this worm is able to turn or not
	 * 
	 * @return a boolean that states if this worm is able to turn or not
	 * 			|getActionPoints() >= 60 * (Math.abs(angle) / (2 * Math.PI)); 
	 */
	public boolean canTurn(double angle) {
		return getActionPoints() >= 60 * (Math.abs(angle) / (2 * Math.PI));
	}

	/** 
	 * Lets this worm turn over a given angle at the expense of 60/(angle/2*PI) action points.
	 * The expense of action points is rounded up.
	 * 
	 * @param	angle
	 * 			The angle this worm should turn his direction.
	 * @pre		the worm has to have enough action points left to make the turn
	 * 			|canTurn(angle)
	 * @post	the worm has turned over the given angle
	 * 			|new.getDirection() = this.getDirection + angle
	 * @post	the worms action points is decreased by the amount defined by the rule above
	 * 			|new.getActionPoints() = this.getActionPoints - 
	 * 			|	(int) Math.ceil(60*(Math.abs(angle)/(2*Math.PI))));
	 */
	public void turn(double angle) {
		setDirection(getDirection() + angle);
		setActionPoints(getActionPoints()
				- (int) Math.ceil(60 * (Math.abs(angle) / (2 * Math.PI))));
	}

	/** 
	 * Returns if this worm is able to move or not
	 * 
	 * @param	steps
	 * 			The amount of steps that is checked if the worm is able to move them.
	 * @return 	Return a boolean that states if this worm is able to turn or not
	 * 			|getActionPoints() >= (int) Math.ceil(steps * (Math.abs(Math.cos(getDirection())) 
	 * 			|	+ 4 * Math.abs(Math.sin(getDirection()))))
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
	 * Let the worm move a given amount of steps in the direction angle 
	 * whereas one step equals the worms radius.
	 * This consumes 1 action point for each movement horizontally equal to the worms radius.
	 * This consumes 4 action points for each movement vertically equal to the worms radius.
	 * The expense of action points is rounded up.
	 * 
	 * @param	steps
	 * 			The amount of steps this worm will move.
	 * @post	the worm has moved the amount of steps in its current direction
	 *			|new.getXCoordinate() = this.getXCoordinate() + getRadius()*steps*Math.cos(getDirection()))
	 *			|new.getYCoordinate() = this.getYCoordinate() + getRadius()*steps*Math.sin(getDirection()));
	 * @post	the number of action points is decreased by the amount defined by the rules above
	 * 			|new.getActionPoints = this.getActionPoints - (int) Math.ceil(steps*(Math.abs(Math.cos(getDirection())) + 4*Math.abs(Math.sin(getDirection()))))
	 * @throws	IllegalArgumentException
	 * 			This worm cannot move the given steps.
	 * 			| !canMove(steps)
	 * 
	 */
	public void move(int steps) throws IllegalArgumentException {
		if (!canMove(steps)) {
			throw new IllegalArgumentException();
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
	final double GRAVITY = 9.80665;

	/** 
	 * Calculates the initial velocity a worm has when it jumps based on the following formula:
	 * -> force = 5*getActionPoints() + getMass()*GRAVITY
	 * -> velocity = force*time(0.5) / getMass().
	 * 
	 * @return Return the initial velocity of the worm when it jumps.
	 */
	public double initialVelocity() {
		double force = 5 * getActionPoints() + getMass() * GRAVITY;
		return force / getMass() * 0.5;
	}

	/** 
	 * returns if this worm is able to jump or not
	 * 
	 * @return 	Return a boolean that states if the worm is able to jump or not
	 * 			|(getDirection() < Math.PI) && (getDirection() > 0) && (getActionPoints() > 0);
	 */
	public boolean canJump() {
		return (getDirection() < Math.PI) && (getDirection() > 0)
				&& (getActionPoints() > 0);
	}

	/** 
	 * Let the worm jump over a distance based on his action points, direction and his mass
	 * This consumes all action points left
	 * 
	 * @post	the worm has moved a distance horizontally equal to the formula beneath
	 * 			|new.getXCoordinate() = this.getXCoordinate + 
	 * 			|	Math.pow(initialVelocity(),2)*Math.sin(2*getDirection())/GRAVITY
	 * 			|new.getYCoordinate() = this.getYCoordinate()
	 * @post	the worm has consumed all its action points
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
	 * @return	Return the time a jump of this worm would take.
	 * 			|initialVelocity() * Math.sin(2 * getDirection())/ (GRAVITY * Math.cos(getDirection()))
	 * @throws 	IllegalStateException
	 * 			The worm cannot jump.
	 * 			| !canjump()
	 */
	public double jumpTime() throws IllegalStateException {
		if (!canJump()) {
			throw new IllegalStateException();
		}
		return initialVelocity() * Math.sin(2 * getDirection())
				/ (GRAVITY * Math.cos(getDirection()));

	}
	
	/**
	 * Return the position (x-coordinate, y-coordinate) at a certain time 
	 * during the jump.
	 * 
	 * @param 	time
	 * 			The time during the jump where you want to know the position of this worm.
	 * @return	Return the position at the given time of the jump.
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
