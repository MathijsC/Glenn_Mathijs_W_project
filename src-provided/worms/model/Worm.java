package worms.model;

public class Worm {
	
	
	public Worm(double x, double y, double direction, double radius, String name) {
		setXCoordinate(x);
		setYCoordinate(y);
		setDirection(direction);
		setRadius(radius);
		setMass(calcMass(radius));
		setActionPoints(getMaxActionPoints());
		setName(name);
	}
	
	public double xCoordinate;

	public double getXCoordinate() {
		return xCoordinate;
	}

	public void setXCoordinate(double xCoordinate) {
		this.xCoordinate = xCoordinate;
	}

	public double yCoordinate;

	public double getYCoordinate() {
		return yCoordinate;
	}

	public void setYCoordinate(double yCoordinate) {
		this.yCoordinate = yCoordinate;
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
	
	// Movement
	
	/**
	 * Returns if this worm is able to turn or not
	 * @return a boolean that states if this worm is able to turn or not 
	 */
	public boolean canTurn(double angle){
		return getActionPoints() >= 60*(Math.abs(angle)/(2*Math.PI));
	}
		
	/** Lets this worm turn over a given angle at the expense of 60/(angle/2*PI) action points
	 * 		The expense of action points is rounded up
	 * @pre		the worm has to have enough action points left to make the turn
	 * 			|canTurn(angle)
	 * @post	the worm has turned over the given angle
	 * 			|new.getDirection() = this.getDirection + angle
	 * @post	the worms action points is decreased by the amount defined by the rule above
	 * 			|new.getActionPoints() = this.getActionPoints - (int) Math.ceil(60*(Math.abs(angle)/(2*Math.PI))));
	 */
	public void turn(double angle){
		if (canTurn(angle)) {
			setDirection(getDirection()+angle);
			setActionPoints(getActionPoints() - (int) Math.ceil(60*(Math.abs(angle)/(2*Math.PI))));
			}
	}
		
		/** Returns if this worm is able to move or not 
		 * @pre		the number of steps must be a positive integer number
		 * 			|steps > 0
		 * @return a boolean that states if this worm is able to turn or not
		 */
	public boolean canMove(int steps){
		return getActionPoints() >= (int) Math.ceil(steps*(Math.abs(Math.cos(getDirection())) + 4*Math.abs(Math.sin(getDirection()))));
	}
		
		/**	Let the worm move a given amount of steps in the direction angled whereas one step equals the worms radius
		 * 		This consumes 1 action point for each movement horizontally equal to the worms radius
		 * 		And it consumes 4 action points for each movement vertically equal to the worms radius
		 * 		The expense of action points is rounded up
		 * @pre		the number of steps must be a positive integer number
		 * 			|steps > 0
		 * @pre		the worm has to have enough action points left to move the amount of steps
		 * 			|canMove(steps)
		 * @post	the worm has moved the amount of steps in its current direction
		 *			|new.getXCoordinate() = this.getXCoordinate() + getRadius()*steps*Math.cos(getDirection()))
		 *			|new.getYCoordinate() = this.getYCoordinate() + getRadius()*steps*Math.sin(getDirection()));
		 * @post	the number of action points is decreased by the amount defined by the rules above
		 * 			|new.getActionPoints = this.getActionPoints - (int) Math.ceil(steps*(Math.abs(Math.cos(getDirection())) + 4*Math.abs(Math.sin(getDirection()))))
		 */
	public void move(int steps){
		if (canMove(steps)){
				setXCoordinate(getXCoordinate() + getRadius()*steps*Math.cos(getDirection()));
				setYCoordinate(getYCoordinate() + getRadius()*steps*Math.sin(getDirection()));
				setActionPoints(getActionPoints() - (int) Math.ceil(steps*(Math.abs(Math.cos(getDirection())) + 4*Math.abs(Math.sin(getDirection())))));
		}
	}
	/**
	 * The constant GRAVITY is used to 	easy manipulate the gravity in the different methods
	 */
	final double GRAVITY = 9.80665;
		
	/** calculates the initial velocity a worm has when it jumps based on the following formula
	 * 		force = 5*getActionPoints() + getMass()*GRAVITY
	 * 		velocity = force*time(0.5) / getMass()
	 * @return the initial velocity of the worm when it jumps
	 */
	public double initialVelocity(){
		double force = 5*getActionPoints() + getMass()*GRAVITY;
		return force/getMass()*0.5;
	}
		
	/** returns if this worm is able to jump or not
	 * @return a boolean that states if the worm is able to jump or not
	 */
	public boolean canJump(){
		return (getDirection()<Math.PI) && (getDirection()>0) && (getActionPoints()>0);
	}
		
	/** Let the worm jump over a distance based on his action points, direction and his mass
	²* 	This consumes all action points left
	 * @pre 	the worm must be able to jump
	 * 			|canJump()
	 * @post	the worm has moved a distance horizontally equal to the formula beneath
	 * 			|new.getXCoordinate() = this.getXCoordinate + Math.pow(initialVelocity(),2)*Math.sin(2*getDirection())/GRAVITY
	 * 			|new.getYCoordinate() = this.getYCoordinate()
	 * @post	the worm has consumed all its action points
	 * 			|new.getActionPoints = 0
	 */
	public void jump(){
		if (canJump()){
			double dist = Math.pow(initialVelocity(),2)*Math.sin(2*getDirection())/GRAVITY;
			setXCoordinate(getXCoordinate()+dist);
			setActionPoints(0);
		}
	}
		
	public double jumpTime(){
		if (canJump()){
			return initialVelocity()*Math.sin(2*getDirection())/(GRAVITY*Math.cos(getDirection()));
		} else {
			return 0;
		}
	}
		
		public double[] jumpStep(double time){
			double X = getXCoordinate()+initialVelocity()*Math.cos(getDirection())*time;
			double Y = getYCoordinate()+initialVelocity()*Math.sin(getDirection())*time-0.5*GRAVITY*Math.pow(time,2);
			double[] coord = {X,Y};
			return coord;
		}

	}
