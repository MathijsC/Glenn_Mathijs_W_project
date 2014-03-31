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
	 * A list containing all the worms who are currently in this world.
	 */
	private ArrayList<Worm> worms = new ArrayList<Worm>();
	
	/**
	 * Add a new worm to this world.
	 * 
	 * @param 	worm
	 * 			The worm to be added to this world.
	 * @post	The last worm added to this world is the given worm.
	 * 			TODO formeel
	 * 
	 * 
	 */
	public void addWorm(Worm worm) throws IllegalStateException{
		if (!(worm.getWorld() == this)){
			throw new IllegalStateException();
		}
		worms.add(worm);		
	}
	
	/**
	 * Return a list of the worms who live in this world.
	 * 
	 * @return 	The list of worms who live in this world.
	 */
	public ArrayList<Worm> getWorms(){
		return worms;
	}
	
	/**
	 * The index of the worm who is at turn in the list of worms.
	 */
	private int currentWormIndex;
	
	/**
	 * Return the index of the worm who is at turn in the list of worms.
	 * 
	 * @return 	The index of the worm who is at turn in the list of worms.
	 */
	private int getCurrentWormIndex() {
		return currentWormIndex;
	}

	/**
	 * Set the index of the worm who is at turn to the given current index.
	 * 
	 * @param 	currentWormIndex
	 * 			The index to set the current index to.
	 * @post	The new current index is equal to the given index.
	 * 			| new.getCurrentWormIndex() == currentWormIndex
	 */
	private void setCurrentWormIndex(int currentWormIndex) {
		this.currentWormIndex = currentWormIndex;
	}
	
	/**
	 * Get the worm who is currently at turn in this world.
	 * 
	 * @return The worm who is currently at turn in this world.
	 */
	public Worm getCurrentWorm(){
		return this.worms.get(this.getCurrentWormIndex());
	}
	
	/**
	 * Start the turn of the next worm.
	 * 
	 * @effect	If the last worm has finished its turn, start a new round.
	 * 			| if(last worm played)
	 * 			|	then startNextRound();
	 * @effect	Else set the next worm to the current worm.
	 * 			| else
	 * 			|	then setCurrentWormIndex(next worm index)
	 */
	public void startNextTurn(){
		if (getCurrentWormIndex() >= (worms.size()-1))
			startNextRound();
		else
			setCurrentWormIndex(getCurrentWormIndex()+1);
			
	}
	
	/**
	 * Start a next round of the game.
	 * 
	 * TODO check ALL
	 * @effect	All the worms in the world refresh.
	 * 			| worm.refresh()
	 * @post	The current worm is set to the first worm (index = 0) in this world.
	 * 			| new.getCurrentWormIndex == 0
	 */
	private void startNextRound(){
		
		for (Worm worm: worms){
			worm.refresh();
		}
		setCurrentWormIndex(0);
		
	}
	
	/**
	 * Start a new game.
	 * 
	 * @post	The current worm is set to the first worm (index = 0) in this world.
	 * 			| new.getCurrentWormIndex == 0
	 */
	public void	startGame(){
		setCurrentWormIndex(0);
	}
	
	/**
	 * A list containing all the teams who are curently in this world.
	 */
	private ArrayList<Team> teams = new ArrayList<Team>();
	
	/**
	 * Add a new team to this world.
	 * 
	 * @param 	team
	 * 			The team to be added to this world.
	 * @post	The last team added to this world is the given team.
	 * 			TODO formeel
	 * 
	 * 
	 */
	public void addTeam(Worm team) throws IllegalStateException{
		if (!(team.getWorld() == this)){
			throw new IllegalStateException();
		}
		worms.add(team);
		System.out.println("Added");
	}
	
	/**
	 * Return a list of the teams who are in this world.
	 * 
	 * @return 	The list of teams who are in this world.
	 */
	public ArrayList<Team> getTeams(){
		return teams;
	}
	
	/**
	 * A list containing all the food in this world.
	 */
	private ArrayList<Food> foodList = new ArrayList<Food>();
	
	/**
	 * Add a new food to this world.
	 * 
	 * @param 	Food
	 * 			The food to be added to this world.
	 * @post	The last food added to this world is the given food.
	 * 			TODO formeel
	 * 
	 * 
	 */
	public void addFood(Food food) throws IllegalStateException{
		//TODO food.getWorld()
		/*if (!(food.getWorld() == this)){
			throw new IllegalStateException();
		}*/
		foodList.add(food);		
	}
	
	/**
	 * Return a list of the food who in this world.
	 * 
	 * @return 	The list of food in this world.
	 */
	public ArrayList<Food> getFoodList(){
		return foodList;
	}
	

}
	
