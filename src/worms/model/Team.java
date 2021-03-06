package worms.model;

import java.util.ArrayList;

import be.kuleuven.cs.som.annotate.*;

/**
 * A class of teams used in the game of worms with worms, a name and a world.
 * The class also implements a method to add worms to the team.
 * @invar	A team should at all time have a world.
 * 			|hasWorld()
 * @invar	A team should at all time have a valid name.
 * 			|isValidName(getName()) 
 * @author 	Glenn Cools & Mathijs Cuppens
 * @version	1.6
 *
 */
public class Team {
	
	/**
	 * Initialize a new Team with the given world and name.
	 * 
	 * @param 	world
	 * 			The world where the new team should be created.
	 * @param 	name
	 * 			The name of the new team.
	 * @effect	Set the name of the new team to the given name.
	 * 			| setName(name)
	 * @effect	Set the world of the new team to the given world
	 * 			| setWorld(world)
	 */
	@Raw
	public Team(World world, String name){
		setName(name);
		setWorldTo(world);
	}
	
	/**
	 * A variable with the name of this team.
	 */
	private String name;

	/**
	 * Return the name of this team.
	 * 
	 * @return	The name of this team.
	 */
	@Basic
	@Raw
	public String getName() {
			return name;
	}

	/**
	 * Set the name of this team to the given name.
	 * 
	 * @param 	name
	 * 			The new name of this team.
	 * @post	If the given name is a valid name, the new name
	 * 			of this team is equal to the given name.
	 * 			| if (isValidName(name)
	 * 			|	then new.getName == name
	 * @throws	IllegalArgumentException
	 * 			If the given name is an invalid name.
	 * 			| if(!isValidName(name))
	 */
	@Raw
	public void setName(String name) throws IllegalArgumentException{
		if (!isValidName(name))
			throw new IllegalArgumentException();
		this.name = name;
	}
	
	/**
	 * Check whether a given name is a valid name.
	 * 
	 * @param 	name
	 * 			The name to check whether it is valid or not.
	 * @return	True if the name is a valid name based on the 
	 * 			characters allowed in the name.
	 * 			| name.matches(A regular expression with the allowed characters);
	 */
	@Raw
	public static boolean isValidName(String name) {
		return name.matches("[A-Z][a-zA-Z]+");
	}

	/**
	 * A variable containing the world of this team.
	 */
	private World world;
	
	/**
	 * Set the world of this team to the given world.
	 * 
	 * @param 	world
	 * 			The world where this team is in.
	 * @effect	The world of this team is set to the given world.
	 * 			| setWorld(world)
	 * @effect	The world is added to the given world.
	 * 			| world.addTeam(this)
	 * @throws	IllegalStateException
	 * 			This team has a world already.
	 * 			|hasWorld() 
	 */
	@Raw
	@Model
	protected void setWorldTo(World world) throws IllegalStateException{
		if (hasWorld()){
			throw new IllegalStateException();
		}
		this.world = world;
		world.addTeam(this);
	}
	
	/**
	 * Sets the world of this team to the given world.
	 * 
	 * @param 	world
	 * 			The world to set as world for this team.
	 * @post	The new world of this team is equal to world.
	 * 			|new.getWorld = world.
	 * @throws	IllegalWorldExceptioin
	 * 			If this team cannot have the given world as its world.
	 * 			| if(!canHaveAsWorld(world)
	 */
	@Raw
	@Model
	private void setWorld(World world) throws IllegalWorldException {
		if (!canHaveAsWorld(world)) {
			throw new IllegalWorldException(this, world);
		}
		this.world = world;
	}

	/**
	 * Returns true is this team can have the given world as its world.
	 * 
	 * @param 	world
	 * 			The world to check if this team can have it as its world.
	 * @return	True if the world is not null.
	 * 			|world != null
	 */
	public boolean canHaveAsWorld(World world) {
		return (world != null);
	}
	
	/**
	 * Return true if the given has a world.
	 * 
	 * @param 	team
	 * 			The team to check if he lives in a world.			
	 * @return	True if the given team has a world.
	 * 			| team.getWorld() != null
	 */
	@Raw
	public boolean hasWorld(){
		return (getWorld() != null);
	}
	
	/**
	 * Return the world of this team.
	 * 
	 * @return	The world of this team.
	 */
	@Basic
	@Raw
	public World getWorld() {
		return world;
	}
	
	/**
	 * A list containing all the worms who are currently in this team.
	 */
	private ArrayList<Worm> worms = new ArrayList<Worm>();
	
	/**
	 * Add a new worm to this team.
	 * 
	 * @param 	worm
	 * 			The worm to be added to this team.
	 * @post	The last worm added to this team is equal to the given worm.
	 * 			|new.getWorms().get(getWorms().size()-1) == worm 
	 * @throws	IllegalStateException
	 * 			The given worm doesn't have this team as team attribute.
	 * 			|!(worm.getTeam() == this
	 */
	@Model
	protected void addWorm(Worm worm) throws IllegalStateException{
		if (!(worm.getTeam() == this)){
			throw new IllegalStateException();
		}
		worms.add(worm);
	}
	
	/**
	 * Return a list of the worms who plays in this team.
	 * 
	 * @return 	The list of worms who plays in this team.
	 */
	public ArrayList<Worm> getWorms(){
		return (ArrayList<Worm>)worms.clone();
	}
	

}
