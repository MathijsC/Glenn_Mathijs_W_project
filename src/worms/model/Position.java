package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public class Position {

	public Position(double xCoordinate, double yCoordinate) {
		setXCoordinate(xCoordinate);
		setYcoordinate(yCoordinate);
	}

	/**
	 * Variable registering the x-coordinate of this position
	 */
	private double xCoordinate;

	/**
	 * Return the x-coordinate of this position
	 * 
	 * @return	The x-coordinate of this position
	 */
	@Basic
	@Raw
	public double getXCoordinate() {
		return xCoordinate;
	}

	/**
	 * Set the x-coordinate of this position to the given xCoordinate.
	 * 
	 * @param 	xCoordinate
	 * 			The x-coordinate to be set as the new x-coordinate of this position
	 * @post	The given xCoordinate is the new xCoordinate of this position
	 * 			| new.getXCoordinate() == xCoordinate
	 */
	@Raw
	public void setXCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	/**
	 * Variable registering the y-coordinate of this position
	 */
	private double yCoordinate;

	/**
	 * Return the y-coordinate of this position.
	 * 
	 * @return	The y-coordinate of this position.
	 */
	@Basic
	@Raw
	public double getYCoordinate() {
		return yCoordinate;
	}

	/**
	 * Set the y-coordinate of this position to the given yCoordinate.
	 * 
	 * @param 	yCoordinate
	 * 			The y-coordinate to be set as the new y-coordinate of this position.
	 * @post	The given yCoordinate is the new yCoordinate of this position.
	 * 			| new.getYCoordinate() == yCoordinate
	 */
	@Raw
	public void setYcoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
	}

	public double distanceTo(Position other) {

		return Math
				.sqrt(Math.pow(
						(this.getXCoordinate() - other.getXCoordinate()), 2)
						+ Math.pow(
								(this.getYCoordinate() - other.getYCoordinate()),
								2));
	}

}
