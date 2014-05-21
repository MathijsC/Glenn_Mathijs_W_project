package worms.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * An enumeration containing different types of weapons used in the
 * game of worms to fire projectiles.
 * 
 * @invar	The minimum force of a weapon is at all time smaller than or equal to
 * 			then maximum force of that weapon.
 * 			|getMinForce() <= getMaxForce()
 * @invar	The minimum and maximum force is at all times bigger than zero.
 * 			|getMinForce() >0	&&	getMaxForce() >0
 * @invar	The mass of a projectile of this weapon is at all time bigger than zero.
 * 			|getMassProjectile()>0
 * @invar	The cost of actionpoints to fire a weapon is always bigger than zero.
 * 			|getActionPointsCost() >0
 * @invar	The damage a weapon deals is always bigger than zero.
 * 			|getDamage() >0
 * 
 * @author 	Glenn Cools & Mathijs Cuppens
 * @version	1.3
 *
 */
@Value
public enum Weapon {

	Rifle(0.010, 1.5, 1.5, 10, 20), Bazooka(0.300, 2.5, 9.5, 50, 80);

	/**
	 * Initialize the weapon with a given mass of projectile, minimum forde,
	 * maximum force, action points to fire and damage.
	 * 
	 * @param 	mass
	 * 			The mass of a projectile this weapon fires.
	 * @param 	minForce
	 * 			The minimum force this weapon uses to fire a projectile.
	 * @param 	maxForce
	 * 			The maximum force this weapon uses to fire a projectile.
	 * @param 	actionPointsCost
	 * 			The action points it cost to fire this weapon.
	 * @param 	damage
	 * 			The damage this weapon deals when hitting a worm.
	 * @effect	Set the mass of a projectile this weapon fires to the
	 * 			given mass.
	 * 			|setMassProjectile(mass)
	 * @effect	Set the minimum force of this weapon to the given minForce.
	 * 			|setMinForce(minForce)
	 * @effect	Set the maximum force of this weapon to the given maxForce.
	 * 			|setMaxForce(minForce)
	 * @effect	Set the cost of action points of this weapon to the given
	 * 			actionPointsCost.
	 * 			|setActionPoints(actionPointsCost)
	 * @effect	Set the damage of this weapon to the given damage..
	 * 			|setDamage(damage)
	 */
	@Raw
	private Weapon(double mass, double minForce, double maxForce,
			int actionPointsCost, int damage) {
		this.setMassProjectile(mass);
		this.setMaxForce(maxForce);
		this.setMinForce(minForce);		
		this.setActionPoints(actionPointsCost);
		this.setDamage(damage);
	}

	/**
	 * Get the mass of a projectile this weapon fires.
	 * 
	 * @return the mass
	 */
	@Basic
	@Raw
	public double getMassProjectile() {
		return massProjectile;
	}

	/**
	 * Set the mass of a projectile this weapon fires.
	 * 
	 * @param 	mass 
	 * 			The mass to set.
	 * @throws 	IllegalArgumentException
	 * 			The given mass is a negative number
	 * 			| mass < 0
	 * @Throws	IllegalArgumentException
	 * 			The given mass is not a number.
	 * 			| mass == Double.NaN
	 */
	@Raw
	@Model
	private void setMassProjectile(double mass) throws IllegalArgumentException {
		if (mass < 0) {
			throw new IllegalArgumentException();
		}
		if (mass == Double.NaN) {
			throw new IllegalArgumentException();
		}
		this.massProjectile = mass;
	}

	/**
	 * A variable containing the mass of the projectile this weapon fires.
	 */
	private double massProjectile;

	/**
	 * Get the minimum force this weapon can give to an projectile.
	 * 
	 * @return the minForce
	 */
	@Basic
	@Raw
	public double getMinForce() {
		return minForce;
	}

	/**
	 * Set the minimum force this weapon can give to an projectile.
	 * 
	 * @param 	minForce 
	 * 			the minForce to set
	 * @throws 	IllegalArgumentException
	 * 			The given minimum force is a negative number.
	 * 			| minForce < 0
	 * @Throws	IllegalArgumentException
	 * 			The given minimum force is not a number.
	 * 			| minForce == Double.NaN
	 * @Throws	IllegalArgumentException
	 * 			The given minimum force is bigger than the maximum force of this weapon.
	 * 			| minForce > getMaxForce
	 * 
	 */
	@Raw
	@Model
	private void setMinForce(double minForce) {
		if (minForce < 0) {
			throw new IllegalArgumentException();
		}
		if (minForce == Double.NaN) {
			throw new IllegalArgumentException();
		}
		if (minForce > this.getMaxForce()) {
			throw new IllegalArgumentException();
		}

		this.minForce = minForce;
	}

	/**
	 * A variable holding the minimum force this weapon uses
	 * to fire.
	 */
	private double minForce;

	/**
	 * Get the maximum force this weapon can give to an projectile.
	 * 
	 * @return the maxForce
	 */
	@Basic
	@Raw
	public double getMaxForce() {
		return maxForce;
	}

	/**
	 * Set the maximum force this weapon can give to an projectile.
	 * 
	 * @param 	maxForce 
	 * 			the maxForce to set
	 * @Throws	IllegalArgumentException
	 * 			The given maximum force is not a number.
	 * 			| minForce == Double.NaN
	 * @Throws	IllegalArgumentException
	 * 			The given maximum force is smaller than the maximum force of this weapon.
	 * 			| minForce > getMaxForce
	 * 
	 */
	@Raw
	@Model
	private void setMaxForce(double maxForce) {
		if (maxForce == Double.NaN) {
			throw new IllegalArgumentException();
		}
		if (maxForce < this.getMinForce()) {
			throw new IllegalArgumentException();
		}
		this.maxForce = maxForce;
	}

	/**
	 * A variable holding the maximum force this weapon uses
	 * to fire.
	 */
	private double maxForce;

	/**
	 * Return the action points it cost to fire this weapon.
	 * 
	 * @return the actionPointsCost of this weapon
	 */
	@Basic
	@Raw
	public int getActionPointsCost() {
		return actionPointsCost;
	}

	/**
	 * Set the action points it cost to fire this weapon.
	 * 
	 * @param 	actionPointsCost
	 * 			the actionPointsCost to set to this weapon
	 * @throws 	IllegalArgumentException
	 * 			The given action point cost is a negative number.
	 * 			| actionPointsCost < 0
	 */
	@Raw
	@Model
	private void setActionPoints(int actionPointsCost)
			throws IllegalArgumentException {
		if (actionPointsCost < 0) {
			throw new IllegalArgumentException();
		}
		this.actionPointsCost = actionPointsCost;
	}

	/**
	 * A variable to store the action point cost of this weapon.
	 */
	private int actionPointsCost;

	/**
	 * Return the damage this weapon can deal of this weapon.
	 * 
	 * @return the damage the weapon can deal.
	 */
	@Basic
	@Raw
	public int getDamage() {
		return damage;
	}

	/**
	 * Set the damage this weapon can deal to a worm.
	 * 
	 * @param 	damage
	 * 			The damage to set to this weapon.
	 * @throws 	IllegalArgumentException
	 * 			The given damage is a negative number.
	 * 			| damage < 0
	 */
	@Raw
	private void setDamage(int damage) throws IllegalArgumentException {
		if (damage < 0) {
			throw new IllegalArgumentException();
		}
		this.damage = damage;
	}

	/**
	 * A variable to store the damage this weapon deals.
	 */
	private int damage;

	/** 
	 * The function will calculate the force this weapon will give to it's projectile based on
	 * on min- and maximum force of this weapon and the given yield.
	 * 
	 * @param 	yield
	 * 			An amount of power needed to launch a projectile.
	 * @return	The force the projectile will get based on min- and maximum force of this weapon 
	 * 			and the given yield.
	 * @throws	IllegalArgumentException
	 * 			The yield is not between 0 and 100
	 * 			|!((0<=yield)&&(yield<=100))
	 */
	public double calcForce(int yield) throws IllegalArgumentException {
		if(!((0<=yield)&&(yield<=100))){
			throw new IllegalArgumentException();
		}
		return (this.getMinForce() + (yield
				* (this.getMaxForce() - this.getMinForce()) / 100));
	}

	/** 
	 * A function the calculate the starting point of the projectile this weapon launches.
	 * 
	 * @param 	worm 
	 * 			The worm who uses this weapon.
	 * @return  The starting coordinates of the projectile.
	 */
	public Position calcStartingPoint(Worm worm) {
		Position startingCoord = new Position(worm.getXCoordinate()
				+ Math.cos(worm.getDirection()) * worm.getRadius(),
				worm.getYCoordinate() + Math.sin(worm.getDirection())
						* worm.getRadius());
		return startingCoord;
	}

	/** 
	 * The given worm will shoot this weapon with the given propulsion in the given world.
	 * 
	 * @param 	world
	 * 			The world this action will happen in.
	 * @param 	worm
	 * 			The worm who will shoot.
	 * @param 	yield
	 * 			How much percent of the force of this weapon the worm uses to shoot the weapon.
	 */
	public void shoot(World world, Worm worm, int yield) {
		Position startingPoint = calcStartingPoint(worm);
		System.out.println("new projectile");
		new Projectile(startingPoint.getXCoordinate(), startingPoint.getYCoordinate(), world, this,
				worm.getDirection(), yield);
	}
}
