package worms.model;

import java.util.Arrays;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

public class Projectile extends Entity {
	
	//TODO docu Constructor
	//TODO Class Invar
	//TODO DOCU check

	public Projectile(Position position, World world, Weapon weapon,
			double direction, double mass, double force) {
		super(position,world);
		this.setDirection(direction);
		this.mass = mass;
		this.force = force;
		this.setWeapon(weapon);
	}


	/** Returns the weapon that created this projectile
	 * @return the weapon
	 */
	public Weapon getWeapon() {
		return weapon;
	}

	/** Sets the weapon that created this projectile
	 * @param weapon the weapon to set
	 */
	private void setWeapon(Weapon weapon) {
		this.weapon = weapon;
	}

	/**
	 * Variablie to register the weapon that created this projectile
	 */
	private Weapon weapon;

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
	
	/**
	 *  Variable to register the mass of this projectile
	 */
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
	
	/**
	 * Variable to register the force exerted on this projectile
	 */
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
	
	/**
	 * Variable to register the radius of this projectile
	 */
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

	/** 
	 * Calculates the radius of this projectile based on its density and mass.
	 * 
	 * @param	Mass
	 * 			The mass of this projectile to calculate the radius of this projectile.
	 * @return	Returns the radius of this projectile based on its density and mass.
	 * 			| VOLUME = MASS/DENSITY
	 * 			| VOLUME = 4/3*PI*R^3
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
	 * The constant GRAVITY is used to easy manipulate the gravity in the different methods
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

	/** 
	* Let this projectile jump over a distance.
	* 
	* If the projectile is still alive:
	* @effect A theoretical jump will be calculated to get the location where it will hit something (worm or terrain)
	* 			and the projectile's location will be set to the new location
	* 			| new.getPosition = possibleJump()
	* @effect  If the new location is on a worm the projectile will deal damage to that worm
	* 			and it will be destroyed
	* 				| if(wormHit)
	* 				| then dealDamage() and terminate()
	* 
	* @post If the projectile doesn't hit a worm it will be destroyed
	* 				| if(!wormHit)
	* 				| then terminate()
	* If the projectile is already terminate:
	* @Post nothing happens
	*/

	public void jump(double timeStep) {
		if (!isTerminated()) {
			double[] newPosition = Arrays.copyOfRange(
					this.possibleJump(timeStep), 0, 2);
			this.setPosition(newPosition[0], newPosition[1]);
			if (this.getWorld().checkProjectileHitWorm(this.getPosition(),
					this.getRadius())) {
				Worm wormHit = this.getWorld().getWormHit(this);
				wormHit.dealDamage(this.getWeapon().getDamage());
			}
			terminate();
		}
	}

	/**
	 * Return the time a jump of this projectile would take.
	 * 
	 * @effect A theoretical jump will be performed to get the time it takes to jump
	 * 
	 * @return	Return the time a jump of this projectile would take
	 * 			based on the direction of this projectile, the gravity
	 * 			of the environment and the initial velocity.
	 */
	public double jumpTime(double timeStep) {
		return this.possibleJump(timeStep)[2];

	}

	/** A theoretical jump will be performed to determine the location where it will hit something
	 * 			and to calculate the time it will take to perform that jump
	 * 
	 * The function will calc step by step the next location on the trajectory of this projectile
	 * 				and will check if the location is passable or if the projectile will hit a worm
	 * 				at that location, 
	 * 				if so the function will stop and will return the final location of the jump, 
	 * 				if not the new position will be stored in a local variable and the next position
	 * 				will be calculated
	 * 
	 * @param timeStep An elementary time interval during which you may assume
	 *                 that the projectile will not completely move through a piece of impassable terrain.
	 * @return	Returns The location where the jump will end (by hitting anything)
	 * 					 and the time it will take to perform that jump
	 */
	public double[] possibleJump(double timeStep) {
	
		Position position = this.getPosition();
		double time = timeStep;
		Position tempPosition;
		boolean jumping = true;
		boolean hit = false;

		while ((jumping) && (!hit)) {
			tempPosition = this.jumpStep(time);
			if (getWorld().isPassable(tempPosition.getXCoordinate(),
					tempPosition.getYCoordinate(),this.getRadius())) {
				position = tempPosition;				
				time = time + timeStep;
				if (this.getWorld().checkProjectileHitWorm(position,
						this.getRadius())) {
					hit = true;
				}
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
	public Position jumpStep(double time) throws IllegalArgumentException,IllegalStateException {
		if (time <= 0) {
			throw new IllegalArgumentException();
		}
		if (isTerminated()) {
			throw new IllegalStateException();
		}
		// jumpTime vraag een argument dat hier niet gegeven is ... :/
		/*
		 * if (time > this.jumpTime()) { throw new IllegalArgumentException(); }
		 */
		double X = getXCoordinate() + initialVelocity()
				* Math.cos(getDirection()) * time;
		double Y = getYCoordinate() + initialVelocity()
				* Math.sin(getDirection()) * time - 0.5 * GRAVITY
				* Math.pow(time, 2);
		Position coord = new Position(X, Y);
		return coord;
	}

}
