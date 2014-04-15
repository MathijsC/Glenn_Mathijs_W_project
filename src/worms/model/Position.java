package worms.model;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Raw;

public class Position {

	//TODO docu Constructor
	//TODO Class Invar
	//TODO DOCU check

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
	 * @throw	IllegalArgumentException
	 * 			The given xCoordinate is not a number
	 * 			|xCoordinate == Double.NaN
	 */
	@Raw
	public void setXCoordinate(double xCoordinate) throws IllegalArgumentException{
		if (xCoordinate == Double.NaN) {
			throw new IllegalArgumentException();
		}
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
	 * @throw	IllegalArgumentException
	 * 			The given yCoordinate is not a number
	 * 			|yCoordinate == Double.NaN
	 */
	@Raw
	public void setYcoordinate(double yCoordinate) throws IllegalArgumentException{
		if (yCoordinate == Double.NaN) {
			throw new IllegalArgumentException();
		}
		this.yCoordinate = yCoordinate;
	}
	
	/** calculates the distance between this position and the other position
	 * 
	 * @param other The other position where the distance to will be determined
	 * @return The distance between this position and the other position
	 */
	public double distanceTo(Position other) {

		return Math
				.sqrt(Math.pow(
						(this.getXCoordinate() - other.getXCoordinate()), 2)
						+ Math.pow(
								(this.getYCoordinate() - other.getYCoordinate()),
								2));
	}

}
