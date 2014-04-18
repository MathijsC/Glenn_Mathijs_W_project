package worms.model;

import be.kuleuven.cs.som.annotate.*;

@Value
public enum Weapon {

	Rifle(0.010, 1.5, 1.5, 10, 20), Bazooka(0.300, 2.5, 9.5, 50, 80);

	private Weapon(double mass, double minForce, double maxForce,
			int actionPointsCost, int damage) {
		this.setMass(mass);
		this.setMinForce(minForce);
		this.setMaxForce(maxForce);
		this.setActionPoints(actionPointsCost);
		this.setDamage(damage);
	}

	/**
	 * @return the mass
	 */
	public double getMass() {
		return mass;
	}

	/**
	 * @param 	mass the mass to set
	 * @throws 	IllegalArgumentException
	 * 			The given mass is a negative number
	 * 			| mass < 0
	 * @Throws	IllegalArgumentException
	 * 			The given mass is not a number.
	 * 			| mass == Double.NaN
	 */
	private void setMass(double mass) throws IllegalArgumentException {
		if (mass < 0) {
			throw new IllegalArgumentException();
		}
		if (mass == Double.NaN) {
			throw new IllegalArgumentException();
		}
		this.mass = mass;
	}

	private double mass;

	/**
	 * @return the minForce
	 */
	public double getMinForce() {
		return minForce;
	}

	/**
	 * @param minForce the minForce to set
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

	private double minForce;

	/**
	 * @return the maxForce
	 */
	public double getMaxForce() {
		return maxForce;
	}

	/**
	 * @param 	maxForce the maxForce to set
	 * @Throws	IllegalArgumentException
	 * 			The given maximum force is not a number.
	 * 			| minForce == Double.NaN
	 * @Throws	IllegalArgumentException
	 * 			The given maximum force is smaller than the maximum force of this weapon.
	 * 			| minForce > getMaxForce
	 * 
	 */
	private void setMaxForce(double maxForce) {
		if (maxForce == Double.NaN) {
			throw new IllegalArgumentException();
		}
		if (maxForce < this.getMinForce()) {
			throw new IllegalArgumentException();
		}
		this.maxForce = maxForce;
	}

	private double maxForce;

	/**
	 * @return the actionPointsCost of this weapon
	 */
	public int getActionPointsCost() {
		return actionPointsCost;
	}

	/**
	 * @param 	actionPointsCost
	 * 			the actionPointsCost to set to this weapon
	 * @throws 	IllegalArgumentException
	 * 			The given action point cost is a negative number.
	 * 			| actionPointsCost < 0
	 */
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
	 * @return the damage the weapon can deal.
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * @param 	damage
	 * 			The damage to set to this weapon.
	 * @throws 	IllegalArgumentException
	 * 			The given damage is a negative number.
	 * 			| damage < 0
	 */
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

	/** The function will calculate the force this weapon will give to it's projectile based on
	 * 	on min- and maximum force of this weapon and the given yield.
	 * 
	 * @param 	yield
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

	/** A function the calculate the starting point of the projectile this weapon launches.
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

	/** The given worm will shoot this weapon with the given propulsion in the given world.
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
		new Projectile(startingPoint.getXCoordinate(), startingPoint.getYCoordinate(), world, this,
				worm.getDirection(), this.getMass(), this.calcForce(yield));
	}
}
