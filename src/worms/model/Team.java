package worms.model;

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
	 * 			The world to set as world from this team.
	 * @post	The new world of this team is equal to world.
	 * 			| new.getWorld() == world
	 */
	public void setWorld(World world){
		this.world = world;
	}
	
	/**
	 * Return the world of this team.
	 * 
	 * @return	The world of this team.
	 */
	public World getWorld() {
		return world;
	}
	
	

}
