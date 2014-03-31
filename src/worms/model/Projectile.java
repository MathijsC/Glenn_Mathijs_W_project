package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

public class Projectile extends Entity {

	public Projectile(Position position, double direction, double mass,
			double force) {
		super(position);
		this.setDirection(direction);
		this.mass = mass;
		this.force = force;
		this.setState(true);
	}
	
	private boolean state;
	
	public void setState(boolean state){
		this.state = state;
	}
	
	public boolean getState(){
		return this.state;
	}

	/**
	 * Variable to register the direction of this projectile.
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
	 * Return the direction of this projectile.
	 * 
	 * @return	The direction of this projectile.
	 */
	@Basic
	@Raw
	public double getDirection() {
		return direction;
	}

	/**
	 * Set the direction of this projectile to the given direction
	 * 
	 * @param 	direction
	 * 			The new direction of this projectile
	 * @post	If the new direction of this projectile is positive after calculated
	 * 			module 2*PI, the direction is added to the base (2*PI) to get
	 * 			a positive direction between 0 and 2*PI.
	 * 			| if (direction % (Math.PI * 2) <0)
	 * 			|	then new.getDirection = (direction % (Math.PI * 2) + 2*Math.PI)
	 * @post	Else the new direction of this projectile is positive after calculated
	 * 			module 2*PI, the direction of this projectile is set to this number.
	 * 			| new.getDirection() == direction % (Math.PI*2)
	 */
	@Raw
	private void setDirection(double direction) {
		if (direction % (Math.PI * 2) < 0)
			this.direction = (direction % (Math.PI * 2) + 2 * Math.PI);
		else
			this.direction = (direction % (Math.PI * 2));
	}

	final double mass;

	/**
	 * Return the mass of this projectile.
	 * 
	 * @return	The mass of this projectile.
	 */
	@Basic
	@Raw
	public double getMass() {
		return this.mass;
	}

	final double force;

	/**
	 * Return the force that is exerted on this projectile.
	 * 
	 * @return	The force that is exerted on this projectile.
	 */
	@Basic
	@Raw
	public double getForce() {
		return force;
	}

	final double radius = calcRadius();

	/**
	 * Return the radius of this projectile.
	 * 
	 * @return	The radius of this projectile.
	 */
	@Basic
	@Raw
	public double getRadius() {
		return radius;
	}

	/**
	 * Return the minimal radius this projectile should have.
	 * 
	 * @return	The minimal radius this projectile should have.
	 */
	@Immutable
	public static double getMinRadius() {
		return 0.0;
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

	// TODO formele uitleg
	/** 
	 * Calculates the radius of this projectile based on its density and mass.
	 * 
	 * @param	Mass
	 * 			The mass of this projectile to calculate the radius of this projectile.
	 * @return	Returns the radius of this projectile based on its density and mass.
	 * 			| MASS/DENSITY;
	 * @throws	IllegalArgumentException
	 * 			The radius is an invalid radius.
	 * 			| !isValidRadius(radius)
	 */
	private double calcRadius() throws IllegalArgumentException {
		final int DENSITY_OF_THE_PROJECTILE = 7800;
		if (!isValidRadius(radius))
			throw new IllegalArgumentException();
		return Math.pow((3 / (4 * Math.PI))
				* (this.getMass() / DENSITY_OF_THE_PROJECTILE), 1.0 / 3);
	}

	/**
	 * The constant GRAVITY is used to 	easy manipulate the gravity in the different methods
	 */
	public final double GRAVITY = 9.80665;

	/** 
	 * Calculates the initial velocity this projectile has when it is launched.
	 * 
	 * @return 	Return the initial velocity of this projectile when it is launched based on the force  
	 * 			exerted on it and its mass. 
	 * 			| FORCE / MASS * CONTSTANT
	 */
	public double initialVelocity() {
		return this.getForce() / this.getMass() * 0.5;
	}
	
	//TODO docu formeel
		/** 
	 * Let this projectile jump over a distance.
	 * 
	 * @post	The new X-coordinate of this projectile is equal to the old X-coordinate added
	 * 			with the distance moved horizontally based on the gravity of the environment
	 * 			and the direction of this projectile. The Y-coordinate stays the same.
	 * 			|new.getXCoordinate() = this.getXCoordinate + DISTANCE_MOVED
	 * 			|new.getYCoordinate() = this.getYCoordinate()
	 */

	public void jump() {

		double dist = Math.pow(initialVelocity(), 2)
				* Math.sin(2 * getDirection()) / GRAVITY;
		setXCoordinate(getXCoordinate()+dist);
	}

	/**
	 * Return the time a jump of this projectile would take.
	 * 
	 * @return	Return the time a jump of this projectile would take
	 * 			based on the direction of this projectile, the gravity
	 * 			of the environment and the initial velocity.
	 */
	public double jumpTime() {

		return this.initialVelocity() * Math.sin(2 * getDirection())
				/ (GRAVITY * Math.cos(getDirection()));

	}

	/**
	 * Return the position (x-coordinate, y-coordinate) at a certain time 
	 * during the jump.
	 * 
	 * @param 	time
	 * 			The time during the jump where you want to know the position of this projectile
	 * @return	Return the position of this projectile at the given time of 
	 * 			the jump based on the old coordinates of this projectile, the initial velocity
	 * 			the direction of this projectile and the gravity of the environment.
	 * @throws 	IllegalArgumentException
	 * 			The given time is not during the jump.
	 * 			| time <=0
	 * 			| time > this.jumpTime()
	 */
	public double[] jumpStep(double time) throws IllegalArgumentException {
		if (time <= 0) {
			throw new IllegalArgumentException();
		}
		if (time > this.jumpTime()) {
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
