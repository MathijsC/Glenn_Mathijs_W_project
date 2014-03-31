package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

public class Projectile {

	public Projectile(Position position,double mass_In, double force_In) {
		this.position = position;
		this.mass = mass_In;
		this.force = force_In;
	}
	
	Position position; 

	final double mass;
	
	/**
	 * Return the mass of this projectile.
	 * 
	 * @return	The mass of this projectile.
	 */
	@Basic @Raw	
	public double getMass() {
		return this.mass;
	}

	final double force;
	
	/**
	 * Return the force that is exerted on this projectile.
	 * 
	 * @return	The force that is exerted on this projectile.
	 */
	@Basic @Raw
	public double getForce() {
		return force;
	}
	
	final double radius = calcRadius();
	
	/**
	 * Return the radius of this projectile.
	 * 
	 * @return	The radius of this projectile.
	 */
	@Basic @Raw
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
	
	//TODO formele uitleg
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
		return Math.pow((3/(4*Math.PI))*(this.getMass()/DENSITY_OF_THE_PROJECTILE),1.0/3);
	}
	

}
