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
		return worm.canMove(nbSteps);
	}

	/**
	 * Moves the given worm by the given number of steps.
	 * Can throw a model exception if the given worm can't move the given number of steps.
	 */
	public void move(Worm worm, int nbSteps) throws ModelException{
		try{
			worm.move(nbSteps);
		}catch(IllegalArgumentException exc){
			throw new ModelException("Illegal argument");
		}
	}

	/**
	 * Returns whether or not the given worm can turn by the given angle.
	 */
	public boolean canTurn(Worm worm, double angle) {
		return worm.canTurn(angle);
	}

	/**
	 * Turns the given worm by the given angle.
	 */
	public void turn(Worm worm, double angle) {
		worm.turn(angle);
	}

	/**
	 * Makes the given worm jump.
	 * Can throw a model exception when the given worm isn't able to jump.
	 */
	public void jump(Worm worm) throws ModelException {
		try{
			worm.jump();
		}catch(IllegalStateException exc){
			throw new ModelException("Illegal state");
		}
		
	}

	/**
	 * Returns the total amount of time (in seconds) that a
	 * jump of the given worm would take.
	 * Can throw a model exception when the given worm isn't able to jump.
	 */
	public double getJumpTime(Worm worm) throws ModelException {
		try{
			return worm.jumpTime();
		}catch(IllegalStateException exc){
			throw new ModelException("Illegal state");
		}
	}

	/**
	 * Returns the location on the jump trajectory of the given worm
	 * after a time t.
	 * Can throw a model exception when the given worm isn't able to jump.
	 *  
	 * @return An array with two elements,
	 *  with the first element being the x-coordinate and
	 *  the second element the y-coordinate
	 */
	public double[] getJumpStep(Worm worm, double t) {
		try{
			return worm.jumpStep(t);
		}catch(IllegalArgumentException exc){
			throw new ModelException("Illegal argument");
		}catch(IllegalStateException exc){
			throw new ModelException("Illegal state");
		}
	}

	/**
	 * Returns the x-coordinate of the current location of the given worm.
	 */
	public double getX(Worm worm) {
		return worm.getXCoordinate();
	}

	/**
	 * Returns the y-coordinate of the current location of the given worm.
	 */
	public double getY(Worm worm) {
		return worm.getYCoordinate();
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
	 * Can throw a model exception when the given radius is smaller than the minimum radius.
	 */
	public void setRadius(Worm worm, double newRadius) throws ModelException {
		try{
			worm.setRadius(newRadius);
		} catch(IllegalArgumentException exc){
			throw new ModelException("Illegal argument!");
		}
		
	}
	
	/**
	 * Returns the minimal radius of the given worm.
	 */
	public double getMinimalRadius(Worm worm) {
		return Worm.getMinRadius();
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
	 * Can throw a model exception when the new name doesn't fit the constraints.
	 */
	public void rename(Worm worm, String newName) throws ModelException{
		try{
			worm.setName(newName);
		}catch(IllegalArgumentException exc){
			throw new ModelException("Illegal argument");
		}
	}

	/**
	 * Returns the mass of the given worm.
	 */
	public double getMass(Worm worm) {
		return worm.getMass();
	}
	
	
}
