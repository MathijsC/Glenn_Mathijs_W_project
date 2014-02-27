package worms.model;
import be.kuleuven.cs.som.annotate.*;

/**
 * A class for the worm objects who are used in the game Worms.
 * @author Glenn Cools, Mathijs Cuppens
 *
 */
public class Worm {
	
	/**
	 * Initialize this new worm with a given position (x,y), looking direction,
	 * radius and name.
	 * @param 	x
	 * 			The x-coordinate of the position for this new worm (in meters).
	 * @param 	y
	 * 			The y-coordinate of the position for this new worm (in meters).
	 * @param 	direction
	 * 			The looking direction for this new worm (in radians).
	 * @param 	radius
	 * 			The radius for this new worm (in meters).
	 * @param 	name
	 * 			The name for this new worm.
	 * @post	The looking direction of the new worm is equal to the given
	 * 			direction modulo 2*PI.
	 * 			| new.getDirection() == direction % (Math.PI*2)
	 * @post	If the given radius is smaller then the lower bound, then the 
	 * 			radius of this new worm is equal to the lower bound. 
	 * 			Else the radius of this new worm is equal to the given radius.
	 * 			| if (radius < getMinRadius())
	 * 			|	then radius == getMinRadius()
	 * 			| else
	 * 			|	then radius == radius
	 * @post	The mass of this new worm is set to a value calculated like a
	 * 			sphere density and the given radius.
	 * 			| new.getMass() == calcMass(radius)
	 * @post	The actionpoints of this new worm is set the the maximum possible
	 * 			actionpoints for this new worm in accordance to its mass.
	 * 			| new.getActionPoints == getMaxActionPoints()
	 * @post	If the given name is a valid name, the name of this new worm is
	 * 			equal to the given name.
	 * 			| if (isValidName(name))
	 * 			|	then new.getName == name
	 */
	public Worm(double x, double y, double direction, double radius, String name) {
		setXCoordinates(x);
		setYCoordinates(y);
		setDirection(direction);
		setRadius(radius);
		setMass(calcMass(radius));
		setActionPoints(getMaxActionPoints());
		setName(name);
	}
	
	public double xCoordinates;

	public double getXCoordinates() {
		return xCoordinates;
	}

	public void setXCoordinates(double xCoordinates) {
		this.xCoordinates = xCoordinates;
	}

	public double yCoordinates;

	public double getYCoordinates() {
		return yCoordinates;
	}

	public void setYCoordinates(double yCoordinates) {
		this.yCoordinates = yCoordinates;
	}
	
	public double direction;

	public double getDirection() {
		return direction;
	}

	public void setDirection(double direction) {
		this.direction = (direction % (Math.PI*2));
	}
	
	public double radius;
	
	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		if (radius < getMinRadius())
			this.radius = getMinRadius();
		else
			this.radius = radius;
	}
	
	public double getMinRadius(){
		return 0.25;
	}
	
	public String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(isValidName(name))
			this.name = name;
	}
	
	public boolean isValidName(String name){
		return name.matches("[A-Z][a-zA-Z\'\" ]+");
	}
	
	public double mass;
	

	public double getMass() {
		return mass;
	}

	public void setMass(double mass) {
		this.mass = mass;
	}
	
	public double calcMass(double r){
		return 1062*(4/3)*Math.pow(r, 3)*Math.PI;
	}

	public int actionPoints;
	
	public int getActionPoints() {
		return actionPoints;
	}

	public void setActionPoints(int actionPoints) {
		this.actionPoints = actionPoints;
	}
	
	public int getMaxActionPoints(){
		return (int) Math.round(getMass());
	}

}
