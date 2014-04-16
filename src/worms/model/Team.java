package worms.model;

import java.util.ArrayList;

/**
 * A class of teams used in the game of worms with worms, a name and a world.
 * The class also implements a method to add worms to the team.
 * @invar	A team should at all time have a world.
 * 			|hasWorld()
 * @invar	A team should at all time have a valid name.
 * 			|isValidName(getName()) 
 * @author 	Glenn Cools & Mathijs Cuppens
 * @version	1.2
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
	public Team(World world, String name){
		setName(name);
		setWorld(world);
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
	 * @post	The world of this team is set to the given world.
	 * 			| new.getWorld() == world
	 * @effect	The world is added to the given world.
	 * 			| world.addTeam(this)
	 * @throws	NullPointerException
	 * 			The given world is null.
	 * 			| if (world == null) 
	 */
	public void setWorld(World world) throws NullPointerException, IllegalStateException{
		if (world == null) {
			throw new NullPointerException();
		}
		if (hasWorld()){
			throw new IllegalStateException();
		}
		this.world = world;
		world.addTeam(this);
	}
	
	/**
	 * Return true if the given has a world.
	 * 
	 * @param 	team
	 * 			The team to check if he lives in a world.			
	 * @return	True if the given team has a world.
	 * 			| team.getWorld() != null
	 */
	private boolean hasWorld(){
		return (getWorld() != null);
	}
	
	/**
	 * Return the world of this team.
	 * 
	 * @return	The world of this team.
	 */
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
	 * @post	The last worm added to this team is the given worm.
	 * 			|getWorms().get(getWorms().size()-1) == worm 
	 */
	public void addWorm(Worm worm) throws IllegalStateException{
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
		return worms;
	}
	

}
