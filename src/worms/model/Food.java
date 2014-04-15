package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

public class Food extends Entity {
	
	//TODO docu Constructor
	//TODO Class Invar
	//TODO DOCU check
	
	public Food(World world, double x, double y) {
		super(new Position(x,y),world);
		setRadius(getMinRadius()*(1+world.getSeed().nextDouble()));
	}

	public Food(World world) {
		super(new Position(0, 0),world);
		setRadius(getMinRadius()*(1+world.getSeed().nextDouble()));
		double[] randCoord = world.getRandAdjacentTerrain(this.getRadius());
		this.setPosition(randCoord[0], randCoord[1]);
	}


	/**
	 * Variable holding the radius of this food
	 */
	private double radius;

	/**
	 * Return the radius of this food
	 * 
	 * @return	The radius of this food
	 */
	@Basic
	@Raw
	public double getRadius() {
		return radius;
	}

	/**
	 * Set the radius of this food to the given radius if this given radius is valid.
	 * 
	 * @param radius
	 * 			The new radius of this food.
	 * @post	If the given radius is valid, then the new radius of this food is equal
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
	 * Return the minimal radius this food should have.
	 * 
	 * @return	The minimal radius this food should have.
	 */
	@Immutable
	public static double getMinRadius() {
		return 0.20;
	}

	/** The worm consumes this food to grow by 10% in radius
	 *  
	 * @param worm the worm that eats this food
	 * @post	this food will be destroyed
	 * 			|terminate()
	 */
	public void getEatenBy(Worm worm){
		worm.setRadius(worm.getRadius()*1.1);
		terminate();
	}

}
