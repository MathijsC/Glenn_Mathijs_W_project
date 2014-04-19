package worms.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of entities used in the game of worms with a position and a world.
 * The class also implements methods to terminate entities.
 * 
 * @invar	An entity who is not terminated should at all time have a world
 * 			|if (!isTerminated)
 * 			|	then hasWorld()
 * @author 	Glenn Cools & Mathijs Cuppens
 * @version	1.7
 */
public class Entity {

	/**
	 * Initialize a new entity with a given position and world.
	 * 
	 * @param 	position
	 * 			The position to give this new entity.
	 * @param 	world
	 * 			The world to give this new entity.
	 * @post	The position of this new entity is equal to position.
	 * 			|new.getPosition() == position
	 * @post	This new entity is set to not terminated.
	 * 			|new.isTerminated == false
	 * @effect	The world of this new entity is set to world.
	 * 			|setWorld(world)
	 */
	@Raw
	public Entity(Position position, World world) {
		this.position = position;
		this.terminated = false;
		this.setWorldTo(world);
	}

	/**
	 * A variable containing the position of this entity.
	 */
	private Position position;

	/**
	 * Return the position of this Entity.
	 * 
	 * @return The position of this Entity.
	 */
	@Basic
	@Raw
	public Position getPosition() {
		return this.position;
	}

	/**
	 * Set the x-coordinate and y-coordinate of the position of this entity to the given xCoordinate and yCoordinate.
	 * 
	 * @param 	x
	 * 			The x-coordinate to be set as the position of this entity
	 * @param 	y
	 * 			The y-coordinate to be set as the position of this entity
	 * @effect	The given xCoordinate is the new xCoordinate of this entity.
	 * 			| position.setXCoordinate(x)
	 * @effect	The given yCoordinate is the new yCoordinate of this entity.
	 * 			| position.setYcoordinate(y)
	 */
	@Raw
	@Model
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
	 * Return the y-coordinate of the position of this entity.
	 * 
	 * @return	The y-coordinate of the position of this entity.
	 */
	@Basic
	@Raw
	public double getYCoordinate() {
		return position.getYCoordinate();
	}

	//STATE

	/**
	 * A variable containing if this entity is terminated.
	 */
	private boolean terminated;

	/**
	 * Returns true is this entity is terminated.
	 * @return	True if this entity is terminated
	 * 			|terminated
	 */
	@Basic
	@Raw
	public boolean isTerminated() {
		return this.terminated;
	}

	/**
	 * Terminates this entity and removes it from its gameworld.
	 * 
	 * @post	This entity is set to be terminated.
	 * 			| new.isTerminated = true
	 * @effect	This entity is removed from the gameworld.
	 * 			|unsetWorld()
	 */
	@Model
	protected void terminate() {
		this.terminated = true;
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
	@Basic
	@Raw
	public World getWorld() {
		return world;
	}

	/**
	 * Set the world where this entity lives to the given world.
	 * 
	 * @param 	world
	 * 			The world where this entity lives in.
	 * @effect	The world of this entity is set to the given world.
	 * 			| setWorld(world)
	 * @effect	The entity is added the the given world.
	 * 			| world.addEntity(this)
	 * @throws	IllegalStateException
	 * 			This entity has a world already.
	 * 			| hasWorld()
	 */
	@Model
	protected void setWorldTo(World world) throws IllegalStateException {
		if (hasWorld()) {
			throw new IllegalStateException();
		}
		setWorld(world);
		world.addEntity(this);
	}

	/**
	 * Sets the world of this entity to the given world.
	 * 
	 * @param 	world
	 * 			The world to set as world for this entity.
	 * @post	The new world of this entity is equal to world.
	 * 			|new.getWorld = world.
	 * @throws	IllegalWorldExceptioin
	 * 			If this entity cannot have the given world as its world.
	 * 			| if(!canHaveAsWorld(world)
	 */
	@Raw
	@Model
	private void setWorld(World world) throws IllegalWorldException {
		if (!canHaveAsWorld(world)) {
			throw new IllegalWorldException(this, world);
		}
		this.world = world;
	}

	/**
	 * Returns true is this entity can have the given world as its world.
	 * 
	 * @param 	world
	 * 			The world to check if this entity can have it as its world.
	 * @return	True if the world is not null.
	 * 			|world != null
	 * @return	True if this entity is terminated and the world is null.
	 * 			| if (isTerminated())
	 * 			| 	then world == null
	 */
	public boolean canHaveAsWorld(World world) {
		if (isTerminated()) {
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
	 * 			| getWorld() != null
	 */
	@Raw
	public boolean hasWorld() {
		return (getWorld() != null);
	}

	/**
	 * Removes this entity from its world.
	 * 
	 * @post	If this entity is terminated and this entity has a world,
	 * 			set the world of this entity to null.
	 * 			|if ((isTerminated()) && (hasWorld()))
	 * 			|	new.getWorld() == null
	 * @effect	If this entity is terminated and this entity has a world,
	 * 			remove this entity from its world.
	 * 			|if ((isTerminated()) && (hasWorld()))
	 * 			|	oldWorld.removeEntity(this)
	 */
	public void unsetWorld() {
		if ((isTerminated()) && (hasWorld())) {
			World oldWorld = getWorld();
			setWorld(null);
			oldWorld.removeEntity(this);
		}
	}

}
