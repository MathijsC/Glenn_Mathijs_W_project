package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public class Entity {

	public Entity(Position position,World world) {
		this.position = position;
		this.setTerminated(false);
		setWorldTo(world);
	}

	private Position position;

	public Position getPosition() {
		return this.position;
	}

	protected void setPosition(double x, double y) {
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
	 * Set the x-coordinate of the position of this worm to the given xCoordinate.
	 * 
	 * @param 	xCoordinate
	 * 			The x-coordinate to be set as the position of this worm
	 * @post	The given xCoordinate is the new position of this worm.
	 * 			| new.getXCoordinate() == xCoordinate
	 */
	@Raw
	protected void setXCoordinate(double xCoordinate) {
		position.setXCoordinate(xCoordinate);
	}

	/**
	 * Return the y-coordinate of the position of this worm.
	 * 
	 * @return	The y-coordinate of the position of this worm.
	 */
	@Basic
	@Raw
	public double getYCoordinate() {
		return position.getYCoordinate();
	}

	/**
	 * Set the y-coordinate of the position of this worm to the given yCoordinate.
	 * 
	 * @param 	yCoordinate
	 * 			The x-coordinate to be set as the position of this worm
	 * @post	The given yCoordinate is the new position of this worm.
	 * 			| new.getYCoordinate() == yCoordinate
	 */
	@Raw
	protected void setYCoordinate(double yCoordinate) {
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
	 * A variable containing the world where this worm is lives.
	 */
	private World world;

	/**
	 * Return the world where this worm lives.
	 * 
	 * @return The world where this worm lives.
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Set the world where this worm lives to the given world.
	 * 
	 * @param 	world
	 * 			The world where this worm lives in.
	 * @post	The world of this worm is set to the given world.
	 * 			| new.getWorld() == world
	 * @effect	The worm is added the the given world.
	 * 			| world.addWorm(this)
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
	 * Return true if the given worm lives in a world.
	 * 
	 * @param 	worm
	 * 			The worm to check if he lives in a world.			
	 * @return	True if the given worm lives in a world.
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
