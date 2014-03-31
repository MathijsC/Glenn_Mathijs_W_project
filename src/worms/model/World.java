package worms.model;

import java.util.ArrayList;
import java.util.Random;

import be.kuleuven.cs.som.annotate.Basic;
import be.kuleuven.cs.som.annotate.Immutable;
import be.kuleuven.cs.som.annotate.Raw;



public class World {
	
	/**
	 * Initialize this new world with a given width, height, passableMap and
	 * a random.
	 * 
	 * @param 	width
	 * 			The width for this new world.
	 * @param 	height
	 * 			The height of this new world.
	 * @param 	passableMap
	 * 			The map matrix of this new world with the position that are passable.
	 * @param 	random
	 * 			TODO
	 * @post	The width of this new world is equal to the given width.
	 * 			| new.getWidth == width;	
	 * @post	The height of this new world is equal to the given height.
	 * 			| new.getHeight == height;
	 * @effect	The passable map of this new world is set to the given passable map matrix.
	 * 			| setPassableMap(passableMap)
	 * @post	The seed
	 * 			TODO	
	 */
	public World(double width, double height, boolean[][] passableMap,
			Random random) {
		this.width= width;
		this.height = height;
		this.setPassableMap(passableMap);
		this.seed = random;

	}
	
	/**
	 * A variable containing the width of this world.
	 */
	private final double width;
	
	/**
	 * A variable containing the width of this world.
	 */
	private final double height;
	
	/**
	 * Return the width of this world.
	 * 
	 * @return The width of this world.
	 */
	@Basic @Raw @Immutable
	public double getWidth() {
		return width;
	}
	
	/**
	 * Return the height of this world.
	 * 
	 * @return The height of this world.
	 */
	@Basic @Raw @Immutable
	public double getHeight() {
		return height;
	}
	
	/**
	 * A matrix of booleans containing information about the passability of
	 * the terrain of this world.
	 */
	private boolean[][] passableMap;
	
	/**
	 * Return the passable map of this world.
	 * 
	 * @return The passable map of this world.
	 */
	@Basic @Raw	
	public boolean[][] getPassableMap() {
		return passableMap;
	}

	/**
	 * Set the passable map of this world to the given passable map matrix.
	 * 
	 * @param 	passableMap
	 * 			The map matrix of this world with the position that are passable.
	 * 			
	 * @post	The passable map matrix of this world is equal to the given passable map.
	 * 			| new.getPassableMap() == passableMap.			
	 */
	@Raw @Basic
	public void setPassableMap(boolean[][] passableMap){ 
		this.passableMap = passableMap;
	}
	
	/**
	 * TODO seed
	 */
	private final Random seed;
	
	/**
	 * TODO seed
	 * @return
	 */
	public Random getSeed() {
		return seed;
	}

	/**
	 * A list containing all the worms who are curently in this world.
	 */
	private ArrayList<Worm> worms = new ArrayList<Worm>();
	
	/**
	 * Add a new worm to this world.
	 * 
	 * @param 	worm
	 * 			The worm to be added to this world.
	 * 
	 * 
	 */
	public void addWorm(Worm worm){
		worms.add(worm);
		
	}
	
	public ArrayList<Worm> getWorms(){
		return worms;
	}
	
	private int currentWormIndex;
	
	private int getCurrentWormIndex() {
		return currentWormIndex;
	}

	private void setCurrentWormIndex(int currentWormIndex) {
		this.currentWormIndex = currentWormIndex;
	}
	
	public Worm getCurrentWorm(){
		return this.worms.get(this.getCurrentWormIndex());
	}
	
	public void startNextTurn(){
		if (getCurrentWormIndex() >= (worms.size()-1))
			startNextRound();
		else
			setCurrentWormIndex(getCurrentWormIndex()+1);
			
	}
	
	private void startNextRound(){
		
		for (Worm worm: worms){
			worm.refresh();
		}
		setCurrentWormIndex(0);
		
	}
	
	public void	startGame(){
		setCurrentWormIndex(0);
	}


	
	
	
	

}
	
