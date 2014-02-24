package worms.model;

public class Worm {
	
	
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
		this.direction = direction;
	}
	
	public double radius;
	
	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
	
	public boolean canTurn(double angle){
		return getActionPoints() >= 60*(Math.abs(angle)/(2*Math.PI));
	}
	
	public void turn(double angle){
		if (canTurn(angle)) {
			setDirection(getDirection()+angle);
			setActionPoints(getActionPoints() - (int) Math.ceil(60*(Math.abs(angle)/(2*Math.PI))));
		}
	}
	
	public boolean canMove(int steps){
		return getActionPoints() >= (int) Math.ceil(steps*(Math.abs(Math.cos(getDirection())) + 4*Math.abs(Math.sin(getDirection()))));
	}
	
	public void move(int steps){
		if (canMove(steps)){
			setXCoordinates(getXCoordinates() + getRadius()*steps*Math.cos(getDirection()));
			setYCoordinates(getYCoordinates() + getRadius()*steps*Math.sin(getDirection()));
			setActionPoints(getActionPoints() - (int) Math.ceil(steps*(Math.abs(Math.cos(getDirection())) + 4*Math.abs(Math.sin(getDirection())))));
		}
	}
	
	public double initialVelocity(){
		double force = 5*getActionPoints() + getMass()*9.80665;
		return force/getMass()*0.5;
	}
	
	public boolean canJump(){
		return (getDirection()<Math.PI) && (getDirection()>0) && (getActionPoints()>0);
	}
	
	public void jump(){
		if (canJump()){
			double dist = Math.pow(initialVelocity(),2)*Math.sin(2*getDirection())/9.80665;
			setXCoordinates(getXCoordinates()+dist);
			setActionPoints(0);
		}
	}
	
	public double jumpTime(){
		if (canJump()){
			return initialVelocity()*Math.sin(2*getDirection())/(9.80665*Math.cos(getDirection()));
		} else {
			return 0;
		}
	}
	
	public double[] jumpStep(double time){
		double X = getXCoordinates()+initialVelocity()*Math.cos(getDirection())*time;
		double Y = getYCoordinates()+initialVelocity()*Math.sin(getDirection())*time-0.5*9.80665*Math.pow(time,2);
		double[] coord = {X,Y};
		return coord;
	}

}
