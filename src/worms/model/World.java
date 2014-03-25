package worms.model;

import java.util.ArrayList;
import java.util.Random;



public class World {
	private final double width;
	private final double height;
	
	public World(double width, double height, boolean[][] passableMap,
			Random random) {
		this.width= width;
		this.height = height;
		this.setPassableMap(passableMap);
		this.seed = random;

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
	
	private final Random seed;
	
	public Random getSeed() {
		return seed;
	}

	private ArrayList<Worm> worms = new ArrayList<Worm>();
	
	public void addWorm(Worm worm){
		worms.add(worm);
		
	}
	
	public ArrayList<Worm> getWorms(){
		return worms;
	}

}
	
