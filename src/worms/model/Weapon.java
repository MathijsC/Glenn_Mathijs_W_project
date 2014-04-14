package worms.model;

import be.kuleuven.cs.som.annotate.*;

@Value
public enum Weapon {

	Rifle(0.010, 1.5, 1.5, 10, 20), Bazooka(0.300, 2.5, 9.5, 50, 80);

	private Weapon(double mass, double minForce, double maxForce,
			int actionPoints, int damage) {
		this.setMass(mass);
		this.setMinForce(minForce);
		this.setMaxForce(maxForce);
		this.setActionPoints(actionPoints);
		this.setDamage(damage);
	}

	/**
	 * @return the mass
	 */
	public double getMass() {
		return mass;
	}

	/**
	 * @param mass the mass to set
	 */
	public void setMass(double mass) {
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
	 */
	public void setMinForce(double minForce) {
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
	 * @param maxForce the maxForce to set
	 */
	public void setMaxForce(double maxForce) {
		this.maxForce = maxForce;
	}

	private double maxForce;

	/**
	 * @return the actionPoints
	 */
	public int getActionPoints() {
		return actionPoints;
	}

	/**
	 * @param actionPoints the actionPoints to set
	 */
	public void setActionPoints(int actionPoints) {
		this.actionPoints = actionPoints;
	}

	private int actionPoints;

	/**
	 * @return the damage the weapon can deal
	 */
	public int getDamage() {
		return damage;
	}

	/**
	 * @param damage the damage to set to
	 */
	public void setDamage(int damage) {
		this.damage = damage;
	}

	private int damage;

	public double calcForce(int propulsion) {
		return (this.getMinForce() + (propulsion
				* (this.getMaxForce() - this.getMinForce()) / 100));
	}

	public Position calcStartingPoint(Worm worm) {
		Position startingPoint = new Position(worm.getXCoordinate()
				+ Math.cos(worm.getDirection()) * worm.getRadius(),
				worm.getYCoordinate() + Math.sin(worm.getDirection())
						* worm.getRadius());
		return startingPoint;
	}

	public void shoot(World world, Worm worm, int propulsion) {
		new Projectile(this.calcStartingPoint(worm),
				world,this, worm.getDirection(), this.getMass(),
				this.calcForce(propulsion));
	}
}
