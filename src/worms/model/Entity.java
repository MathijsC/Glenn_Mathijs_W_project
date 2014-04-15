package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public class Entity {
	
	//TODO docu Constructor
	//TODO Class Invar
	//TODO DOCU check

	public Entity(Position position,World world) {
		this.position = position;
		this.setTerminated(false);
		this.setWorldTo(world);
	}

	private Position position;

	public Position getPosition() {
		return this.position;
	}
	
	/**Set the x-coordinate and y-coordinate of the position of this entity to the given xCoordinate and yCoordinate.
	 * 
	 * @param 	x
	 * 			The x-coordinate to be set as the position of this entity
	 * @param y
	 * 			The y-coordinate to be set as the position of this entity
	 * @post	The given xCoordinate is the new xCoordinate of this entity.
	 * 			| new.getXCoordinate() == x
	 * @post	The given yCoordinate is the new yCoordinate of this entity.
	 * 			| new.getYCoordinate() == y
	 * @throws	IllegalArgumentException
	 * 			The given xCoordinate is not a number
	 * 			|xCoordinate == Double.NaN
	 */
	protected void setPosition(double x, double y) throws IllegalArgumentException {
		this.position.setXCoordinate(x);
		this.position.setYcoordinate(y);
	}

	/**
	 * Return the x-coordinate of the position
	 * 
	 * @return	The x-coordinate of the position
	 */
	@Basic
	@Raw
	public double getXCoordinate() {
		return position.getXCoordinate();
	}

	/**
	 * Set the x-coordinate of the position of this entity to the given xCoordinate.
	 * 
	 * @param 	xCoordinate
	 * 			The x-coordinate to be set as the position of this entity
	 * @post	The given xCoordinate is the new position of this entity.
	 * 			| new.getXCoordinate() == xCoordinate
	 * @throws	IllegalArgumentException
	 * 			The given xCoordinate is not a number
	 * 			|xCoordinate == Double.NaN
	 */
	@Raw
	protected void setXCoordinate(double xCoordinate) throws IllegalArgumentException {
		position.setXCoordinate(xCoordinate);
	}

	/**
	 * Return the y-coordinate of the position of this entity.
	 * 
	 * @return	The y-coordinate of the position of this entity.
	 */
	@Basic
	@Raw
	public double getYCoordinate() {
		return position.getYCoordinate();
	}

	/**
	 * Set the y-coordinate of the position of this entity to the given yCoordinate.
	 * 
	 * @param 	yCoordinate
	 * 			The x-coordinate to be set as the position of this entity
	 * @post	The given yCoordinate is the new position of this entity.
	 * 			| new.getYCoordinate() == yCoordinate
	 * @throws	IllegalArgumentException
	 * 			The given xCoordinate is not a number
	 * 			|xCoordinate == Double.NaN
	 */
	@Raw
	protected void setYCoordinate(double yCoordinate) throws IllegalArgumentException{
		position.setYcoordinate(yCoordinate);
	}

	//STATE
	
	// TODO docu
	private boolean terminated;

	// TODO docu
	public void setTerminated(boolean terminated) {
		this.terminated = terminated;
	}

	// TODO docu
	public boolean isTerminated() {
		return this.terminated;
	}

	//TODO
	public void terminate() {
		setTerminated(true);
		unsetWorld();
	}

	
	//WORLD
	
	/**
	 * A variable containing the world where this entity is lives.
	 */
	private World world;

	/**
	 * Return the world where this entity lives.
	 * 
	 * @return The world where this entity lives.
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Set the world where this entity lives to the given world.
	 * 
	 * @param 	world
	 * 			The world where this entity lives in.
	 * @post	The world of this entity is set to the given world.
	 * 			| new.getWorld() == world
	 * @effect	The entity is added the the given world.
	 * 			| world.addEntity(this)
	 * @throws	IllegalWorldException
	 * 			The given world is illegal.
	 * 			| if (!canHaveAsWorld(world)) 
	 */
	public void setWorldTo(World world) throws IllegalWorldException,
			IllegalStateException {
		if (!canHaveAsWorld(world)) {
			throw new IllegalWorldException(this, world);
		}
		if (hasWorld(this)) {
			throw new IllegalStateException();
		}
		this.world = world;
		world.addEntity(this);
	}
	
	@Raw
	public void setWorld(World world){
		if (!canHaveAsWorld(world)) {
			throw new IllegalWorldException(this, world);
		}
		this.world = world;
	}
	
	// TODO
	public boolean canHaveAsWorld(World world){
		if (isTerminated()){
			return world == null;
		}
		return (world != null);
	}

	/**
	 * Return true if the given entity lives in a world.
	 * 
	 * @param 	entity
	 * 			The entity to check if he lives in a world.			
	 * @return	True if the given entity lives in a world.
	 * 			| TODO formeel
	 */
	private static boolean hasWorld(Entity entity) {
		return (entity.getWorld() != null);
	}
	
	//TODO
	public void unsetWorld() {
		if (hasWorld(this)) {
			World oldWorld = getWorld();
			setWorld(null);
			oldWorld.removeEntity(this);
		}
	}

}
