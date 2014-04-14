package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;

public class IllegalWorldException extends RuntimeException {

    /**
     * Initialize this new illegal world exception with given worm
     * and given world.
     *
     * @param   worm
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
    public IllegalWorldException(Worm worm, World world) {
        this.worm = worm;
        this.world = world;
    }

    /**
     * Return the worm of this illegal world exception.
     */
    @Basic
    @Immutable
    public Worm getOwner() {
        return this.worm;
    }

    /**
     * Variable registering the worm of this illegal world exception.
     */
    private final Worm worm;

    /**
     * Return the world of this illegal world exception.
     */
    @Basic
    @Immutable
    public World getOwnable() {
        return this.world;
    }

    /**
     * Variable registering the world of this illegal world exception.
     */
    private final World world;

    
    private static final long serialVersionUID = 2003001L;
}
