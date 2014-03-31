package worms.model;

public class Entity {

	public Entity(Position position) {
		this.position = position;
	}

	private Position position;

	public double[] getPosition() {
		return (new double[] { position.getxCoordinate(),position.getYcoordinate() });
	}
	
	public void setPosition(double x, double y) {
		this.position.setxCoordinate(x);
		this.position.setYcoordinate(y);
	}

}
