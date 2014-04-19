package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

/**
 * A class of foods used in the game of worms with a position(x- and y-coordinate)
 * and a world.
 * The class also implements a method to get eaten by a worm.
 * 
 * @invar	The radius of this food is a valid radius at all time.
 * 			|isValidRadius(getRadius())
 * @author  Glenn Cools & Mathijs Cuppens
 * @version	1.3
 *
 */
public class Food extends Entity {
	
	/**
	 * Initialize a new food with the given world, position (x- and y-coordinate)
	 * and a random radius.
	 * 
	 * @param 	world
	 * 			The world of this new food.
	 * @param 	x
	 * 			The x-coordinate of the position of this new food.
	 * @param 	y
	 * 			The x-coordinate of the position of this new food.
	 * @effect	This new food is initialized as a subobject of the class Entity
	 * 			with the given position (x- and y-coordinate) and world.
	 * 			| super(new Position(x,y),world)
	 * @effect	The radius of this new food is set to a random generated radius bigger
	 * 			than the minimal radius.
	 * 			|setRadius(getMinRadius()*(1+RANDOM))
	 */
	@Raw
	public Food(World world, double x, double y) {
		super(new Position(x,y),world);
		setRadius(getMinRadius()*(1+world.getSeed().nextDouble()));
	}

	/**
	 * Initialize a new food with the given world, random position (x- and y-coordinate)
	 * and a random radius.
	 * 
	 * @param 	world
	 * 			The world of this new food.
	 * @effect	This new food is initialized as a subobject of the class Entity
	 * 			with the given world and random position.
	 * 			|super(new Position(0,0),world)
	 * 			|setPosition(randXCoord, randYCoord);
	 * @effect	The radius of this new food is set to a random generated radius bigger
	 * 			than the minimal radius.
	 * 			|setRadius(getMinRadius()*(1+RANDOM))
	 */
	@Raw
	public Food(World world) {
		super(new Position(0, 0),world);
		setRadius(getMinRadius()*(1+world.getSeed().nextDouble()));
		double[] randCoord = world.getRandAdjacentTerrain(this.getRadius());
		this.setPosition(randCoord[0], randCoord[1]);
	}

	/**
	 * Variable holding the radius of this food.
	 */
	private double radius;

	/**
	 * Return the radius of this food.
	 * 
	 * @return	The radius of this food.
	 */
	@Basic
	@Raw
	public double getRadius() {
		return radius;
	}

	/**
	 * Set the radius of this food to the given radius if this given radius is valid.
	 * 
	 * @param 	radius
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
	private void setRadius(double radius) throws IllegalArgumentException {
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

	/** 
	 * A worm consumes this food to grow by 10% in radius.
	 *  
	 * @param 	worm 
	 * 			The worm that eats this food.
	 * @effect	This food will be destroyed
	 * 			|terminate()
	 * @effect	The radius of the worm who eats this food will grow by 10%.
	 * 			|worm.setRadius(worm.getRadius()*1.1)
	 */
	public void getEatenBy(Worm worm) {
		worm.setRadius(worm.getRadius()*1.1);
		terminate();
	}

}
