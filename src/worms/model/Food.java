package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

public class Food extends Entity {
	
	public Food(World world, double x, double y) {
		super(new Position(x,y));
		setRadius(getMinRadius());
		this.setWorld(world);
		this.setState(true);
	}

	public Food(World world) {
		super(new Position(0, 0));
		setRadius(getMinRadius());
		this.setWorld(world);
		this.setState(true);
		double[] randCoord = world.getRandAdjacentTerrain(this.getRadius());
		this.setPosition(randCoord[0], randCoord[1]);
	}

	// TODO docu
	private boolean state;

	// TODO docu
	public void setState(boolean state) {
		this.state = state;
	}

	// TODO docu
	public boolean getState() {
		return this.state;
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

	/**
	 * A variable containing the world where this food is in.
	 */
	private World world;

	/**
	 * Return the world where this food is in.
	 * 
	 * @return The world where this food is in.
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Set the world where this food is in to the given world.
	 * 
	 * @param 	world
	 * 			The world where this food is in.
	 * @post	The world of this food is set to the given world.
	 * 			| new.getWorld() == world
	 * @effect	The food is added the the given world.
	 * 			| world.addFood(this)
	 * @throws	NullPointerException
	 * 			The given world is null.
	 * 			| if (world == null) 
	 */
	public void setWorld(World world) throws NullPointerException,
			IllegalStateException {
		if (world == null) {
			throw new NullPointerException();
		}
		if (Food.hasWorld(this)) {
			throw new IllegalStateException();
		}
		this.world = world;
		world.addFood(this);
	}

	/**
	 * Return true if the given food is in a world.
	 * 
	 * @param 	food
	 * 			The food to check if it is in a world.			
	 * @return	True if the given food is in a world.
	 * 			| TODO formeel
	 */
	private static boolean hasWorld(Food food) {
		return (food.getWorld() != null);
	}
	
	public void getEatenBy(Worm worm){
		setState(false);
		worm.setRadius(worm.getRadius()*1.1);
	}

}
