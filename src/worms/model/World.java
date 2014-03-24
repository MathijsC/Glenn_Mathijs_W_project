package worms.model;

import java.util.Collection;
import java.util.Random;

public class World {
	private final double width;
	private final double height;
	
	public World(double width, double height, boolean[][] passableMap,
			Random random) {
		this.width= width;
		this.height = height;
		this.setPassableMap(passableMap);

	}
	
	public double getWidth() {
		return width;
	}

	public double getHeight() {
		return height;
	}
	
	private boolean[][] passableMap;
	
	public boolean[][] getPassableMap() {
		return passableMap;
	}

	public void setPassableMap(boolean[][] passableMap){ 
		this.passableMap = passableMap;
	}
	
	private Collection<Worm> worms;
	
	public void addWorm(Worm worm){
		worms.add(worm);
		
		
	}

}
	
