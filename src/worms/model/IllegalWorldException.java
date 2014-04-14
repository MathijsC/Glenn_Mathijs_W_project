package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

public class IllegalWorldException extends RuntimeException {

	//TODO check all docu
    /**
     * Initialize this new illegal world exception with given worm
     * and given world.
     *
     * @param   entity
     *          The worm for this new illegal world exception.
     * @param   world
     *          The world for this new illegal world exception.
     * @post    The worm for this new illegal world exception
     *          is the same as the given worm.
     *        | new.geWorm() == worm
     * @post    The world for this new illegal world exception
     *          is the same as the given world.
     *        | new.getWorld() == world
     */
    @Raw
    public IllegalWorldException(Entity entity, World world) {
        this.entity = entity;
        this.world = world;
    }

    /**
     * Return the worm of this illegal world exception.
     */
    @Basic
    @Immutable
    public Entity getEntity() {
        return this.entity;
    }

    /**
     * Variable registering the worm of this illegal world exception.
     */
    private final Entity entity;

    /**
     * Return the world of this illegal world exception.
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
