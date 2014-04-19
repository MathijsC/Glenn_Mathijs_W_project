package worms.model;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of illegalWorldExceptions used in the game of worms with
 * an object and a world.
 * 
 * @author 	Glenn Cools & Mathijs Cuppens
 * @version	1.2
 *
 */
public class IllegalWorldException extends RuntimeException {

    /**
     * Initialize this new illegal world exception with given entity
     * and given world.
     *
     * @param   entity
     *          The worm for this new illegal world exception.
     * @param   world
     *          The world for this new illegal world exception.
     * @post    The object for this new illegal world exception
     *          is the same as the given entity.
     *        	|new.getObject() == entity
     * @post    The world for this new illegal world exception
     *          is the same as the given world.
     *        	|new.getWorld() == world
     */
    @Raw
    public IllegalWorldException(Entity entity, World world) {
        this.object = entity;
        this.world = world;
    }

    /**
     * Initialize this new illegal world exception with given team
     * and given world.
     *
     * @param   entity
     *          The worm for this new illegal world exception.
     * @param   world
     *          The world for this new illegal world exception.
     * @post    The Object for this new illegal world exception
     *          is the same as the given team.
     *         	|new.getObject() == entity
     * @post    The world for this new illegal world exception
     *          is the same as the given world.
     *        	|new.getWorld() == world
     */
    @Raw
    public IllegalWorldException(Team team, World world) {
        this.object = team;
        this.world = world;
	}

	/**
     * Return the object of this illegal world exception.
     * 
     * @return	The object of this illegal world exception.
     */
    @Basic
    @Immutable
    public Object getObject() {
        return this.object;
    }

    /**
     * Variable registering the worm of this illegal world exception.
     */
    private final Object object;

    /**
     * Return the world of this illegal world exception.
     * 
     * @return	 The world of this illegal world exception.
     */
    @Basic
    @Immutable
    public World getWorld() {
        return this.world;
    }

    /**
     * Variable registering the world of this illegal world exception.
     */
    private final World world;

    
    private static final long serialVersionUID = 2003001L;
}
