package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public class Entity {

	public Entity(Position position) {
		this.position = position;
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

}
