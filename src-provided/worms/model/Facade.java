package worms.model;

public class Facade implements IFacade {

	/**
	 * Defaut constructor
	 */
	public Facade() {
		
	}
	
	/**
	 * Create a new worm that is positioned at the given location,
	 * looks in the given direction, has the given radius and the given name.
	 * 
	 * @param x
	 * The x-coordinate of the position of the new worm (in meter)
	 * @param y
	 * The y-coordinate of the position of the new worm (in meter)
	 * @param direction
	 * The direction of the new worm (in radians)
	 * @param radius 
	 * The radius of the new worm (in meter)
	 * @param name
	 * The name of the new worm
	 */
	public Worm createWorm(double x, double y, double direction, double radius, String name) {
		return new Worm(x,y,direction,radius,name);
	}

	/**
	 * Returns whether or not the given worm can move a given number of steps.
	 */
	public boolean canMove(Worm worm, int nbSteps) {
		return false;
	}

	/**
	 * Moves the given worm by the given number of steps.
	 */
	public void move(Worm worm, int nbSteps) {
		
	}

	/**
	 * Returns whether or not the given worm can turn by the given angle.
	 */
	public boolean canTurn(Worm worm, double angle) {
		return false;
	}

	/**
	 * Turns the given worm by the given angle.
	 */
	public void turn(Worm worm, double angle) {
		
	}

	/**
	 * Makes the given worm jump.
	 */
	public void jump(Worm worm) {
		
	}

	/**
	 * Returns the total amount of time (in seconds) that a
	 * jump of the given worm would take.
	 */
	public double getJumpTime(Worm worm) {
		return 0;
	}

	/**
	 * Returns the location on the jump trajectory of the given worm
	 * after a time t.
	 *  
	 * @return An array with two elements,
	 *  with the first element being the x-coordinate and
	 *  the second element the y-coordinate
	 */
	public double[] getJumpStep(Worm worm, double t) {
		double[] array = {0,0};
		return array;
	}

	/**
	 * Returns the x-coordinate of the current location of the given worm.
	 */
	public double getX(Worm worm) {
		return worm.getXCoordinates();
	}

	/**
	 * Returns the y-coordinate of the current location of the given worm.
	 */
	public double getY(Worm worm) {
		return worm.getYCoordinates();
	}

	/**
	 * Returns the current orientation of the given worm (in radians).
	 */
	public double getOrientation(Worm worm) {
		return worm.getDirection();
	}

	/**
	 * Returns the radius of the given worm.
	 */
	public double getRadius(Worm worm) {
		return worm.getRadius();
	}
	
	/**
	 * Sets the radius of the given worm to the given value.
	 */
	public void setRadius(Worm worm, double newRadius) {
		worm.setRadius(newRadius);
	}
	
	/**
	 * Returns the minimal radius of the given worm.
	 */
	public double getMinimalRadius(Worm worm) {
		return worm.getMinRadius();
	}

	/**
	 * Returns the current number of action points of the given worm.
	 */
	public int getActionPoints(Worm worm) {
		return worm.getActionPoints();
	}
	
	/**
	 * Returns the maximum number of action points of the given worm.
	 */
	public int getMaxActionPoints(Worm worm) {
		return worm.getMaxActionPoints();
	}
	
	/**
	 * Returns the name the given worm.
	 */
	public String getName(Worm worm) {
		return worm.getName();
	}

	/**
	 * Renames the given worm.
	 */
	public void rename(Worm worm, String newName) {
		worm.setName(newName);
	}

	/**
	 * Returns the mass of the given worm.
	 */
	public double getMass(Worm worm) {
		return worm.getMass();
	}
	
	
}
