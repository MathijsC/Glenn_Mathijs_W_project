package worms.model;

/**
 * This is a class of types used in the game of worms to parse types
 * of a program of a worm.
 * 
 * @author Glenn Cools & Mathijs Cuppens
 *
 * @param <T>
 * The type of object which this Type will hold.
 */
class Type<T> {

	public Type(T v) {
		value = v;
	}

	private T value;

	public T getValue() {
		return value;
	}

	public void setValue(Expression<?> v) {
		value = (T) v.getValue().getValue();
	}

	public String getType() {
		if (value == null) {
			return "null";
		} else if (value.getClass() == Double.class) {
			return "double";
		} else if (value.getClass() == Boolean.class) {
			return "boolean";
		} else if (value.getClass() == EntityType.class) {
			return "entity";
		} else {
			return "unkown";
		}
	}

	public String toString() {
		if (value == null) {
			return "null";
		}
		return value.toString();
	}

}

/**
 * This is a class of entityTypes used in the game of worms to parse an entity
 * of a program of a worm.
 * 
 * @author Glenn Cools & Mathijs Cuppens *
 */
class EntityType {

	public EntityType() {
		value = null;
	}

	public EntityType(Entity ent) {
		value = ent;
	}

	public Entity getValue() {
		return value;
	}

	@Override
	public String toString() {
		if (value instanceof Worm) {
			return ((Worm) value).getName();
		} else if (value instanceof Food) {
			return "A Hamburger";
		} else if (value == null) {
			return "null";
		} else {
			return "False enity";
		}
	}

	public Entity value;

}
