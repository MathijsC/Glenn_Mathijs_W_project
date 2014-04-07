package worms.model;

import be.kuleuven.cs.som.annotate.*;

@Value
public enum Weapon {
	
	Rifle(10,1.5,1.5,10,20),Bazooka(300,2.5,9.5,50,80);
	
	private Weapon(double mass, double minForce, double maxForce, double actionPoints, double hitPoints) {
		this.setMass(mass);
		this.setMinForce(minForce);
		this.setMaxForce(maxForce);
		this.setActionPoints(actionPoints);
		this.setHitPoints(hitPoints);
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
	public double getActionPoints() {
		return actionPoints;
	}

	/**
	 * @param actionPoints the actionPoints to set
	 */
	public void setActionPoints(double actionPoints) {
		this.actionPoints = actionPoints;
	}
	
	private double actionPoints;

	/**
	 * @return the hitPoints
	 */
	public double getHitPoints() {
		return hitPoints;
	}

	/**
	 * @param hitPoints the hitPoints to set
	 */
	public void setHitPoints(double hitPoints) {
		this.hitPoints = hitPoints;
	}
	
	private double hitPoints;
	
	public double calcForce(int propulsion) {
		return (this.getMinForce() + (propulsion * (this.getMaxForce()-this.getMinForce())/100));
	}

	

}
