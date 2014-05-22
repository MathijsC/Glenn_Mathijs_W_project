package worms.model;

/**
 * This is a class of expressions used in the game of worms to parse expressions
 * of a program of a worm.
 * 
 * @author Glenn Cools & Mathijs Cuppens
 *
 * @param <T>
 * The type which this expression will have.
 */
abstract class Expression<T> {

	public int line;
	public int column;

	public Expression(int l, int c) {
		line = l;
		column = c;
	}

	public Expression(int line, int column, Type<T> d) {
		this(line, column);
		value = d;
	}

	public Expression(int line, int column, Expression<?> e) {
		this(line, column);
		expression1 = e;
	}

	public Expression(int line, int column, Expression<?> e1, Expression<?> e2) {
		this(line, column);
		expression1 = e1;
		expression2 = e2;
	}

	public Expression<?> expression1;
	public Expression<?> expression2;
	public Type<T> value;

	abstract public Type<T> getValue();

	abstract public String getType();

}

//SPECIAL EXPRESSION VARIABLE ACCES

abstract class VariableAcces<T> extends Expression<T> {

	public String getType() {
		return type;
	}

	public VariableAcces(int l, int c, String n, Type<?> t) {
		super(l, c);
		varName = n;
		type = t.getType();
	}

	private String type;

	public String varName;

}
