package worms.model;

import java.util.Arrays;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of projectiles used in the game worms with a position, world
 * weapon, direction, mass and force.
 * The class also implements methods to make this projectile jump(shoot).
 * 
 * @invar	The direction of this projectile should be a valid direction at all time.
 * 			|isValidDirection(getDirection())
 * @invar	The radius of this projectile should be a valid radius at all time.
 * 			|isValidRadius(getRadius())
 * @invar	This projectile should at all time have a weapon.
 * 			|hasWeapon()
 * @invar	This projectile should at all time have a valid force.
 * 			|isValidForce(getForce())
 * @author 	Glenn Cools & Mathijs Cuppens
 * @version	1.4
 */

public class Projectile extends Entity {

	/**
	 * Initialize this new projectile with the given position, world, weapon
	 * direction, mass and force.
	 * 
	 * @param 	position
	 * 			The position of this new projectile (an object position holding coordinates).
	 * @param 	world
	 * 			The world of this new projectile.
	 * @param 	weapon
	 * 			The weapon of this new projectile.
	 * @param 	direction
	 * 			The direction of this new projectile.
	 * @param 	mass
	 * 			The mass of this new projectile.
	 * @param 	force
	 * 			The force of this new projectile.
	 * @effect	This projectile is initialize as and subobject of the class Entity
	 * 			with the given position and world.
	 * 			| super(position,world)
	 * @effect	The direction of this new projectiile is equal to the given
	 * 			direction modulo 2*PI.
	 * 			| setDirection(direction)
	 * @post	The mass of this new projectile is set to the given mass.
	 * 			| new.getMass() == mass
	 * @post	The force of this new projectile is set to the given force.
	 * 			| new.getForce() == force
	 * @post	The weapon of this new projectile is set to the given weapon.
	 * 			| new.getWeapon() == weapon
	 */
	public Projectile(Position position, World world, Weapon weapon,
			double direction, double mass, double force) {
		super(position, world);
		this.setDirection(direction);
		this.mass = mass;
		this.force = force;
		this.setWeapon(weapon);
	}

	/** 
	 * Returns the weapon that created this projectile.
	 * 
	 * @return 	The weapon of this projectile.
	 */
	public Weapon getWeapon() {
		return weapon;
	}

	/** 
	 * Sets the weapon that created this projectile.
	 * 
	 * @param 	weapon 
	 * 			The weapon to set, for this projectile.
	 */
	private void setWeapon(Weapon weapon) throws IllegalArgumentException,IllegalStateException{
		if (hasWeapon()){
			throw new IllegalStateException();
		}
		if (!canHaveAsWeapon(weapon)){
			throw new IllegalArgumentException();
		}
		this.weapon = weapon;
	}
	
	/**
	 * Return true if this projectile has a weapon.
	 * 
	 * @return True if this projectile  has a weapon.
	 */
	public boolean hasWeapon(){
		return getWeapon() != null;
	}
	
	/**
	 * Return true if the projectile can have the given weapon as a weapon.
	 * 
	 * @param 	weapon
	 * 			The weapon to check if this projectile can have this as weapon.
	 * @return	True if the projectile can have the given weapon as a weapon.
	 * 			| weapon != null
	 */
	public boolean canHaveAsWeapon(Weapon weapon){
		return weapon != null;
	}

	/**
	 * Variable to register the weapon that created this projectile.
	 */
	private Weapon weapon;

	/**
	 * Variable to register the direction of this projectile.
	 */
	private double direction;

	/**
	 * Returns true if the given direction is a valid direction.
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
	 * Returns the direction of this projectile.
	 * 
	 * @return	The direction of this projectile.
	 */
	@Basic
	@Raw
	public double getDirection() {
		return direction;
	}

	/**
	 * Set the direction of this projectile to the given direction.
	 * 
	 * @param 	direction
	 * 			The new direction of this projectile.
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
	 *  Variable to register the mass of this projectile.
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
	 * Variable to register the force exerted on this projectile.
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
	 * Returns true is the given force is a valid amount of force.
	 * 
	 * @param 	force
	 * 			The force to check if it is valid.
	 * @return	True if the force is not negative, not zero, not NaN and not infinity.
	 */
	public static boolean isValidForce(double force){
		return ((force >=0) && (force != Double.NaN) && (force != Double.POSITIVE_INFINITY));
	}

	/**
	 * Variable to register the radius of this projectile.
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
	 * 			| radius >= getMinRadius()
	 */
	@Raw
	public static boolean isValidRadius(double radius) {
		return radius >= getMinRadius();
	}

	/** 
	 * Calculates the radius of this projectile based on its density and mass.
	 * 
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
	* @Post 	If this projectile is not terminated, the function will do it's work.
	* 			| if(!isTerminated)
	* 			|	then proceed
	* @effect 	A theoretical jump will be calculated to get the location where it will hit something (worm or terrain)
	* 			and this projectile's location will be set to the new location
	* 			| new.getPosition = possibleJump()
	* @effect  	If the new location is on a worm this projectile will deal damage to that worm
	* 			and it will be destroyed
	* 			| if(wormHit)
	* 			| 	then dealDamage() and terminate()
	* @post 	If this projectile doesn't hit a worm it will be destroyed
	* 			| if(!wormHit)
	* 			| 	then terminate()
	*/

	public void jump(double timeStep) {
		if (!isTerminated()) {
			double[] newPosition = Arrays.copyOfRange(
					this.possibleJump(timeStep), 0, 2);
			this.setPosition(newPosition[0], newPosition[1]);
			if (this.getWorld().checkProjectileHitWorm(this.getPosition(),
					this.getRadius())) {
				Worm wormHit = this.getWorld().getWormHit(this);
				wormHit.changeHealt(this.getWeapon().getDamage());
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

	/** 
	 * A theoretical jump will be performed to determine the location where it will hit something
	 * and to calculate the time it will take to perform that jump
	 * 
	 * The function will calculate step by step the next location on the trajectory of this projectile
	 * and will check if the location is passable or if the projectile will hit a worm
	 * at that location, 
	 * if so the function will stop and will return the final location of the jump, 
	 * if not the new position will be stored in a local variable and the next position
	 * will be calculated.
	 * 
	 * @param 	timeStep 
	 * 			An elementary time interval during which you may assume that this projectile will not completely move through a piece of impassable terrain.
	 * @return	Returns The location where the jump will end (by hitting anything) and the time it will take to perform that jump
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
					tempPosition.getYCoordinate(), this.getRadius())) {
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
	 * @thorws	IllegalStateException
	 * 			This projectile is terminated
	 * 			| isTerminated()
	 */
	public Position jumpStep(double time) throws IllegalArgumentException,
			IllegalStateException {
		if (time <= 0) {
			throw new IllegalArgumentException();
		}
		if (isTerminated()) {
			throw new IllegalStateException();
		}
		// TODO vraag aan assistent en controleer docu @throws erna
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
