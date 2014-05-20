package worms.model;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import worms.gui.game.IActionHandler;
import worms.model.programs.ProgramFactory;
import worms.model.programs.ProgramParser;


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

abstract class Statement {

	public Statement(int l, int c) {
		line = l;
		column = c;
		executed = false;
	}

	public boolean executed;

	public int line;
	public int column;

	public void execute(boolean exeCheck) {
		if (executed != exeCheck) {
			run(exeCheck);
		} else {
			System.out.println("already executed");
		}
	}
	
	protected void setExecuted(boolean exeSet){
		executed = exeSet;
	}

	abstract public void run(boolean exeCheck);
}

abstract class Sequence extends Statement {

	public Sequence(int l, int c, List<Statement> st) {
		super(l, c);
		statements = st;
	}
	
	@Override
	protected void setExecuted(boolean exeSet){
		for(Statement st:statements){
			st.setExecuted(exeSet);
		}
		executed = exeSet;
	}

	public List<Statement> statements;
}

abstract class ExpressionAction extends Statement {

	public ExpressionAction(int l, int c, Expression<?> e) {
		super(l, c);
		expression = e;
	}
	
	@Override
	protected void setExecuted(boolean exeSet){
		executed = exeSet;
	}

	public Expression<?> expression;
}

abstract class WhileStatement extends Statement {

	public WhileStatement(int l, int c, Expression<Boolean> cond,
			Statement b) {
		super(l, c);
		condition = cond;
		body = b;
	}
	
	@Override
	protected void setExecuted(boolean exeSet){
		body.setExecuted(exeSet);
		executed = exeSet;
	}

	public Expression<Boolean> condition;
	public Statement body;
}

abstract class ForeachStatement extends Statement {

	public ForeachStatement(int l, int c,worms.model.programs.ProgramFactory.ForeachType t, String varName, Statement b) {
		super(l, c);
		variableName = varName;
		body = b;
		type = t;
	}
	
	@Override
	protected void setExecuted(boolean exeSet){
		body.setExecuted(exeSet);
		executed = exeSet;
	}

	public String variableName;
	public Statement body;
	public worms.model.programs.ProgramFactory.ForeachType type;
}

abstract class IfStatement extends Statement {

	public IfStatement(int l, int c, Expression<Boolean> cond,
			Statement t, Statement f) {
		super(l, c);
		condition = cond;
		ifTrue = t;
		ifFalse = f;
	}
	
	@Override
	protected void setExecuted(boolean exeSet){
		ifTrue.setExecuted(exeSet);
		ifFalse.setExecuted(exeSet);
		executed = exeSet;
	}

	public Expression<Boolean> condition;
	public Statement ifTrue;
	public Statement ifFalse;

}

abstract class Assignment extends Statement {

	public Assignment(int l, int c, Expression<?> e, String n) {
		super(l, c);
		expression = e;
		name = n;
	}
	
	@Override
	protected void setExecuted(boolean exeSet){
		executed = exeSet;
	}

	public Expression<?> expression;
	public String name;

}

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
	
	public String getType(){
		if (value == null){
			System.out.println("val is null");
			return "null";
		} else if (value.getClass() == Double.class){
			return "double";
		} else if (value.getClass() == Boolean.class){
			return "boolean";
		} else if (value.getClass() == EntityType.class){
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
		} else if (value == null){
			return "null";
		} else {
			return "False enity";
		}
	}

	public Entity value;

}

public class Program implements
		ProgramFactory<Expression<?>, Statement, Type<?>> {

	public Program(String programText, IActionHandler handler) {
		System.out.println("Parse");
		executionCheck = true;
		ProgramParser<Expression<?>, Statement, Type<?>> parser = new ProgramParser<Expression<?>, Statement, Type<?>>(
				this);
		parser.parse(programText);
		actionHandler = handler;
		globals = parser.getGlobals();
		statement = parser.getStatement();
		errors = parser.getErrors();
	}

	private IActionHandler actionHandler;
	private Map<String, Type<?>> globals;
	private Statement statement;
	private Worm worm;
	private boolean executionCheck;
	private List<String> errors;
	public int count = 0;

	public List<String> getErrors() {
		return errors;
	}

	public void setWorm(Worm w) {
		worm = w;
	}

	public void runProgram() {
		System.out.println("Run");
		try {
			if (count >= 1000){
				throw new RuntimeException("Last runtime, you executed 1000 statements. We suppose you are in an endless loop!");
			}
			count = 0;
			statement.execute(executionCheck);
			executionCheck = !executionCheck;
			System.out.println("PROGRAM ENDED!!");
			if (worm.getWorld().getCurrentWorm() == worm){
				worm.getWorld().startNextTurn();
			}
		} catch (IllegalStateException exc) {
			System.out.println("Illegal State error!");
			worm.getWorld().startNextTurn();
		} catch (ClassCastException exc) {
			System.out.println("ClassCastException! |"+exc.getMessage());
			worm.getWorld().startNextTurn();
		} catch (RuntimeException exc) {
			System.out.println("RuntimeException! |"+exc.getMessage());
			worm.getWorld().startNextTurn();
		}
		
	}

	@Override
	public Expression<Double> createDoubleLiteral(int line, int column, double d) {
		return new Expression<Double>(line, column, new Type<Double>(d)) {
			public Type<Double> getValue() {
				return value;
			}

			public String getType() {
				return "double";
			}
		};
	}

	@Override
	public Expression<Boolean> createBooleanLiteral(int line, int column,
			boolean b) {
		return new Expression<Boolean>(line, column, new Type<Boolean>(b)) {
			public Type<Boolean> getValue() {
				return value;
			}

			public String getType() {
				return "boolean";
			}
		};
	}

	@Override
	public Expression<Boolean> createAnd(int line, int column,
			Expression<?> e1, Expression<?> e2) {
		if ((e1.getType() != "boolean") || (e2.getType() != "boolean")) {
			throw new IllegalArgumentException("Expected a expressions with a boolean value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Boolean>(line, column, e1, e2) {
			public Type<Boolean> getValue() {
				return new Type<Boolean>(((Boolean) expression1.getValue()
						.getValue())
						&& ((Boolean) expression2.getValue().getValue()));
			}

			@Override
			public String getType() {
				return "boolean";
			}
		};
	}

	@Override
	public Expression<Boolean> createOr(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		if ((e1.getType() != "boolean") || (e2.getType() != "boolean")) {
			throw new IllegalArgumentException("Expected a expressions with a boolean value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Boolean>(line, column, e1, e2) {
			public Type<Boolean> getValue() {
				return new Type<Boolean>(((Boolean) expression1.getValue()
						.getValue())
						|| ((Boolean) expression2.getValue().getValue()));
			}

			@Override
			public String getType() {
				return "boolean";
			}
		};
	}

	@Override
	public Expression<Boolean> createNot(int line, int column, Expression<?> e) {
		if ((e.getType() != "boolean")) {
			throw new IllegalArgumentException("Expected an expression with a boolean value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Boolean>(line, column, e) {
			public Type<Boolean> getValue() {
				return new Type<Boolean>(!(Boolean) expression1.getValue()
						.getValue());
			}

			@Override
			public String getType() {
				return "boolean";
			}
		};
	}

	@Override
	public Expression<Object> createNull(int line, int column) {
		return new Expression<Object>(line, column) {

			@Override
			public Type<Object> getValue() {
				return new Type<Object>(null);
			}

			@Override
			public String getType() {
				return "null";
			}

		};
	}

	@Override
	public Expression<EntityType> createSelf(int line, int column) {
		return new Expression<EntityType>(line, column) {

			@Override
			public Type<EntityType> getValue() {
				return new Type<EntityType>(new EntityType(worm));
			}

			@Override
			public String getType() {
				return "entity";
			}

		};
	}

	@Override
	public Expression<Double> createGetX(int line, int column, Expression<?> e) {
		if (e.getType() != "entity"){
			throw new IllegalArgumentException("Expected an expression with an entity value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				return new Type<Double>((((EntityType) expression1.getValue()
						.getValue()).getValue()).getXCoordinate());
			}

			@Override
			public String getType() {
				return "double";
			}
		};
	}

	@Override
	public Expression<Double> createGetY(int line, int column, Expression<?> e) {
		if (e.getType() != "entity"){
			throw new IllegalArgumentException("Expected an expression with an entity value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				return new Type<Double>((((EntityType) expression1.getValue()
						.getValue()).getValue()).getYCoordinate());
			}

			@Override
			public String getType() {
				return "double";
			}
		};
	}

	@Override
	public Expression<Double> createGetRadius(int line, int column,
			Expression<?> e) {
		if (e.getType() != "entity"){
			throw new IllegalArgumentException("Expected an expression with an entity value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				return new Type<Double>(((EntityType) expression1.getValue()
						.getValue()).getValue().getRadius());
			}

			@Override
			public String getType() {
				return "double";
			}
		};
	}

	@Override
	public Expression<Double> createGetDir(int line, int column, Expression<?> e) {
		if (e.getType() != "entity") {
			throw new IllegalArgumentException("Expected an expression with an entity value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				if (((EntityType)expression1.getValue().getValue()).getValue().getClass() != Worm.class){
					throw new ClassCastException("Expected an expression with a worm value! | line: "+line+" column: "+column+" in your program.");
				}
				return new Type<Double>(((Worm)((EntityType) expression1.getValue()
						.getValue()).getValue()).getDirection());
			}

			@Override
			public String getType() {
				return "double";
			}
		};
	}

	@Override
	public Expression<Double> createGetAP(int line, int column, Expression<?> e) {
		if (e.getType() != "entity") {
			throw new IllegalArgumentException("Expected an expression with an entity value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				if (((EntityType)expression1.getValue().getValue()).getValue().getClass() != Worm.class){
					throw new ClassCastException("Expected an expression with a worm value! | line: "+line+" column: "+column+" in your program.");
				}
				return new Type<Double>((double) (((Worm)((EntityType) expression1.getValue()
						.getValue()).getValue()).getActionPoints()));
			}

			@Override
			public String getType() {
				return "double";
			}
		};
	}

	@Override
	public Expression<Double> createGetMaxAP(int line, int column,
			Expression<?> e) {
		if (e.getType() != "entity") {
			throw new IllegalArgumentException("Expected an expression with an entity value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				if (((EntityType)expression1.getValue().getValue()).getValue().getClass() != Worm.class){
					throw new ClassCastException("Expected an expression with a worm value! | line: "+line+" column: "+column+" in your program.");
				}
				return new Type<Double>((double) (((Worm)((EntityType) expression1.getValue()
						.getValue()).getValue()).getMaxActionPoints()));
			}

			@Override
			public String getType() {
				return "double";
			}
		};
	}

	@Override
	public Expression<Double> createGetHP(int line, int column, Expression<?> e) {
		if (e.getType() != "entity") {
			throw new IllegalArgumentException("Expected an expression with an entity value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				if (((EntityType)expression1.getValue().getValue()).getValue().getClass() != Worm.class){
					throw new ClassCastException("Expected an expression with a worm value! | line: "+line+" column: "+column+" in your program.");
				}
				return new Type<Double>((double) (((Worm)((EntityType) expression1.getValue()
						.getValue()).getValue()).getHitPoints()));
			}

			@Override
			public String getType() {
				return "double";
			}
		};
	}

	@Override
	public Expression<Double> createGetMaxHP(int line, int column,
			Expression<?> e) {
		if (e.getType() != "entity") {
			throw new IllegalArgumentException("Expected an expression with an entity value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				if (((EntityType)expression1.getValue().getValue()).getValue().getClass() != Worm.class){
					throw new ClassCastException("Expected an expression with a worm value! | line: "+line+" column: "+column+" in your program.");
				}
				return new Type<Double>((double) (((Worm)((EntityType) expression1.getValue()
						.getValue()).getValue()).getMaxHitPoints()));
			}

			@Override
			public String getType() {
				return "double";
			}
		};
	}

	@Override
	public Expression<Boolean> createSameTeam(int line, int column,
			Expression<?> e) {
		if (e.getType() != "entity") {
			throw new IllegalArgumentException("Expected an expression with an entity value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Boolean>(line, column, e) {

			@Override
			public Type<Boolean> getValue() {
				if (((EntityType)expression1.getValue().getValue()).getValue().getClass() != Worm.class){
					throw new ClassCastException("Expected an expression with a worm value! | line: "+line+" column: "+column+" in your program.");
				}
				return new Type<Boolean>((worm.getTeam() != null) && (((Worm) ((EntityType)expression1.getValue().getValue()).getValue()).getTeam() == worm.getTeam()));
			}

			@Override
			public String getType() {
				return "boolean";
			}
		};
	}

	@Override
	public Expression<Boolean> createIsWorm(int line, int column,
			Expression<?> e) {
		if (e.getType() != "entity") {
			throw new IllegalArgumentException("Expected an expression with an entity value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Boolean>(line, column, e) {

			@Override
			public Type<Boolean> getValue() {
				return new Type<Boolean>(((EntityType)expression1.getValue().getValue()).getValue().getClass() == Worm.class);
			}

			@Override
			public String getType() {
				return "boolean";
			}

		};
	}

	@Override
	public Expression<Boolean> createIsFood(int line, int column,
			Expression<?> e) {
		if (e.getType() != "entity") {
			throw new IllegalArgumentException("Expected an expression with an entity value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Boolean>(line, column, e) {

			@Override
			public Type<Boolean> getValue() {
				return new Type<Boolean>(((EntityType)expression1.getValue().getValue()).getValue().getClass() == Food.class);
			}

			@Override
			public String getType() {
				return "boolean";
			}

		};
	}

	@Override
	public Expression<EntityType> createSearchObj(int line, int column,
			Expression<?> e) {
		if (e.getType() != "double") {
			throw new IllegalArgumentException("Expected an expression with a double value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<EntityType>(line, column, e) {

			@Override
			public Type<EntityType> getValue() {
				 Entity ent =
				 worm.getWorld().searchObjects(worm,(Double)expression1.getValue
				 ().getValue()); 
				 return new Type<EntityType>(new EntityType(ent));
			}

			@Override
			public String getType() {
				return "entity";
			}

		};
	}

	@Override
	public Expression<Boolean> createLessThan(int line, int column,
			Expression<?> e1, Expression<?> e2) {
		if ((e1.getType() != e2.getType()) || (e1.getType() == "entity")) {
			throw new IllegalArgumentException("Expected an expressions with a double or boolean value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Boolean>(line, column, e1, e2) {

			@Override
			public Type<Boolean> getValue() {
				int compare = ((Comparable<Object>) expression1.getValue()
						.getValue())
						.compareTo(((Comparable<Object>) expression2.getValue()
								.getValue()));
				if (compare < 0) {
					return new Type<Boolean>(true);
				} else {
					return new Type<Boolean>(false);
				}

			}

			@Override
			public String getType() {
				return "boolean";
			}

		};
	}

	@Override
	public Expression<Boolean> createGreaterThan(int line, int column,
			Expression<?> e1, Expression<?> e2) {
		if ((e1.getType() != e2.getType()) || (e1.getType() == "entity")) {
			throw new IllegalArgumentException("Expected an expressions with a double or boolean value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Boolean>(line, column, e1, e2) {

			@Override
			public Type<Boolean> getValue() {
				int compare = ((Comparable<Object>) expression1.getValue()
						.getValue())
						.compareTo(((Comparable<Object>) expression2.getValue()
								.getValue()));
				if (compare > 0) {
					return new Type<Boolean>(true);
				} else {
					return new Type<Boolean>(false);
				}

			}

			@Override
			public String getType() {
				return "boolean";
			}

		};
	}

	@Override
	public Expression<Boolean> createLessThanOrEqualTo(int line, int column,
			Expression<?> e1, Expression<?> e2) {
		if ((e1.getType() != e2.getType()) || (e1.getType() == "entity")) {
			throw new IllegalArgumentException("Expected an expressions with a double or boolean value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Boolean>(line, column, e1, e2) {

			@Override
			public Type<Boolean> getValue() {
				int compare = ((Comparable<Object>) expression1.getValue()
						.getValue())
						.compareTo(((Comparable<Object>) expression2.getValue()
								.getValue()));
				if (compare < 1) {
					return new Type<Boolean>(true);
				} else {
					return new Type<Boolean>(false);
				}

			}

			@Override
			public String getType() {
				return "boolean";
			}

		};
	}

	@Override
	public Expression<Boolean> createGreaterThanOrEqualTo(int line, int column,
			Expression<?> e1, Expression<?> e2) {
		if ((e1.getType() != e2.getType()) || (e1.getType() == "entity")) {
			throw new IllegalArgumentException("Expected an expressions with a double or boolean value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Boolean>(line, column, e1, e2) {

			@Override
			public Type<Boolean> getValue() {
				int compare = ((Comparable<Object>) expression1.getValue()
						.getValue())
						.compareTo(((Comparable<Object>) expression2.getValue()
								.getValue()));
				if (compare > -1) {
					return new Type<Boolean>(true);
				} else {
					return new Type<Boolean>(false);
				}

			}

			@Override
			public String getType() {
				return "boolean";
			}

		};
	}

	@Override
	public Expression<Boolean> createEquality(int line, int column,
			Expression<?> e1, Expression<?> e2) {
		if ((e1.getType() != "null") && (e2.getType() != "null") && (e1.getType() != e2.getType())) {
			throw new IllegalArgumentException("Expected two expressions with the same value type! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Boolean>(line, column, e1, e2) {

			@Override
			public Type<Boolean> getValue() {
				System.out.println("GetV equality "+line + " "+ column+" "+ expression1.getType() + " "+ expression2.getType() + " "+ expression1.getValue() + " "+ expression2.getValue());
				if ((expression1.getType()=="null") || (expression2.getType()=="null")){
					return new Type<Boolean>(expression2.getValue().getValue() == expression1.getValue().getValue());
				} else {
					if (expression1.getValue().getType() != "entity") {
						return new Type<Boolean>(expression1.getValue().getValue()
							.equals(expression2.getValue().getValue()));
					} else {
						return new Type<Boolean>(((EntityType)expression1.getValue().getValue()).getValue().equals(((EntityType)expression2.getValue().getValue()).getValue()));
					}
				}
			}

			@Override
			public String getType() {
				return "boolean";
			}

		};
	}

	@Override
	public Expression<Boolean> createInequality(int line, int column,
			Expression<?> e1, Expression<?> e2) {
		if ((e1.getType() != "null") && (e2.getType() != "null") && (e1.getType() != e2.getType())) {
			throw new IllegalArgumentException("Expected two expressions with the same value type! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Boolean>(line, column, e1, e2) {

			@Override
			public Type<Boolean> getValue() {
				System.out.println("GetV INequality "+line + " "+ column+" "+ expression1.getType() + " "+ expression2.getType() + " "+ expression1.getValue() + " "+ expression2.getValue());
				if ((expression1.getType()=="null") || (expression2.getType()=="null")){
					return new Type<Boolean>(!(expression2.getValue().getValue() == expression1.getValue().getValue()));
				} else {
					if (expression1.getValue().getType() != "entity") {
						return new Type<Boolean>(!expression1.getValue().getValue()
							.equals(expression2.getValue().getValue()));
					} else {
						return new Type<Boolean>(!((EntityType)expression1.getValue().getValue()).getValue().equals(((EntityType)expression2.getValue().getValue()).getValue()));
					}
				}
			}
			
			@Override
			public String getType() {
				return "boolean";
			}

		};
	}

	@Override
	public Expression<?> createVariableAccess(int line, int column, String name) {
		return null;
	}

	@Override
	public Expression<Double> createAdd(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		if ((e1.getType() != "double") || (e2.getType() != "double")) {
			throw new IllegalArgumentException("Expected an expressions with a double value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Double>(line, column, e1, e2) {
			public Type<Double> getValue() {
				return new Type<Double>(((Double) expression1.getValue()
						.getValue())
						+ ((Double) expression2.getValue().getValue()));
			}

			public String getType() {
				return "double";
			}
		};
	}

	@Override
	public Expression<Double> createSubtraction(int line, int column,
			Expression<?> e1, Expression<?> e2) {
		if ((e1.getType() != "double") || (e2.getType() != "double")) {
			throw new IllegalArgumentException("Expected an expressions with a double value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Double>(line, column, e1, e2) {
			public Type<Double> getValue() {
				return new Type<Double>(((Double) expression1.getValue()
						.getValue())
						- ((Double) expression2.getValue().getValue()));
			}

			@Override
			public String getType() {
				return "double";
			}
		};
	}

	@Override
	public Expression<Double> createMul(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		if ((e1.getType() != "double") || (e2.getType() != "double")) {
			throw new IllegalArgumentException("Expected an expressions with a double value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Double>(line, column, e1, e2) {
			public Type<Double> getValue() {
				return new Type<Double>(((Double) expression1.getValue()
						.getValue())
						* ((Double) expression2.getValue().getValue()));
			}

			@Override
			public String getType() {
				return "double";
			}
		};
	}

	@Override
	public Expression<Double> createDivision(int line, int column,
			Expression<?> e1, Expression<?> e2) {
		if ((e1.getType() != "double") || (e2.getType() != "double")) {
			throw new IllegalArgumentException("Expected an expressions with a double value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Double>(line, column, e1, e2) {
			public Type<Double> getValue() {
				return new Type<Double>(((Double) expression1.getValue()
						.getValue())
						/ ((Double) expression2.getValue().getValue()));
			}

			@Override
			public String getType() {
				return "double";
			}
		};
	}

	@Override
	public Expression<Double> createSqrt(int line, int column, Expression<?> e) {
		if (e.getType() != "double") {
			throw new IllegalArgumentException("Expected an expression with a double value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Double>(line, column, e) {
			public Type<Double> getValue() {
				System.out.println("SQRT");
				return new Type<Double>(Math.sqrt(((Double) expression1
						.getValue().getValue())));
			}

			@Override
			public String getType() {
				return "double";
			}
		};

	}

	@Override
	public Expression<Double> createSin(int line, int column, Expression<?> e) {
		if (e.getType() != "double") {
			throw new IllegalArgumentException("Expected an expression with a double value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Double>(line, column, e) {
			public Type<Double> getValue() {
				return new Type<Double>(Math.sin(((Double) expression1
						.getValue().getValue())));
			}

			@Override
			public String getType() {
				return "double";
			}
		};

	}

	@Override
	public Expression<Double> createCos(int line, int column, Expression<?> e) {
		if (e.getType() != "double") {
			throw new IllegalArgumentException("Expected an expression with a double value! | line: "+line+" column: "+column+" in your program.");
		}
		return new Expression<Double>(line, column, e) {
			public Type<Double> getValue() {
				return new Type<Double>(Math.cos(((Double) expression1
						.getValue().getValue())));
			}

			@Override
			public String getType() {
				return "double";
			}
		};

	}

	@Override
	public Statement createTurn(int line, int column, Expression<?> angle) {
		if (angle.getType() != "double") {
			throw new ClassCastException();
		}
		return new ExpressionAction(line, column, angle) {

			@Override
			public void run(boolean exeCheck) {
				if (count >= 1000){
					throw new RuntimeException("You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				if (worm.canTurn((Double) expression.getValue().getValue())) {
					actionHandler.turn(worm, (Double) expression.getValue()
							.getValue());
					this.setExecuted(exeCheck);
				} else {
					throw new IllegalStateException();
				}
			}

		};
	}

	@Override
	public Statement createMove(int line, int column) {
		return new Statement(line, column) {

			@Override
			public void run(boolean exeCheck) {
				if (count >= 1000){
					throw new RuntimeException("You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				if (worm.canMove()) {
					actionHandler.move(worm);
					this.setExecuted(exeCheck);
				} else {
					throw new IllegalStateException();
				}
			}
		};
	}

	@Override
	public Statement createJump(int line, int column) {
		return new Statement(line, column) {

			public void run(boolean exeCheck) {
				if (count >= 1000){
					throw new RuntimeException("You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				if (worm.canJump()) {
					actionHandler.jump(worm);
					this.setExecuted(exeCheck);
				} else {
					throw new IllegalStateException();
				}
			}

		};
	}

	@Override
	public Statement createToggleWeap(int line, int column) {
		return new Statement(line, column) {
			public void run(boolean exeCheck) {
				if (count >= 1000){
					throw new RuntimeException("You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				actionHandler.toggleWeapon(worm);
				this.setExecuted(exeCheck);
			}
		};
	}

	@Override
	public Statement createFire(int line, int column, Expression<?> yield) {
		if (yield.getType() != "double") {
			throw new IllegalArgumentException("Expected an expression with a double value! | line: "+line+" column: "+column+" in your program.");
		}
		return new ExpressionAction(line, column, yield) {

			@Override
			public void run(boolean exeCheck) {
				if (count >= 1000){
					throw new RuntimeException("You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				if (worm.canShoot(worm.getWeapon())) {
					actionHandler.fire(worm, ((Double) expression.getValue()
							.getValue()).intValue());
					this.setExecuted(exeCheck);
				} else {
					throw new IllegalStateException();
				}
			}
		};
	}

	@Override
	public Statement createSkip(int line, int column) {
		return new Statement(line, column) {
			public void run(boolean exeCheck) {
				if (count >= 1000){
					throw new RuntimeException("You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				this.setExecuted(exeCheck);
				throw new IllegalStateException();
			}
		};
	}

	@Override
	public Statement createAssignment(int line, int column,
			String variableName, Expression<?> rhs) {
		return new Assignment(line, column, rhs, variableName) {
			public void run(boolean exeCheck) {
				if (count >= 1000){
					throw new RuntimeException("You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				if (expression.getType() != "null"){
					if ((globals.get(name).getType() == "double") && (expression.getType() !="double")){
						throw new IllegalArgumentException("Expected an expression with a double value! | line: "+line+" column: "+column+" in your program.");
					}
					if ((globals.get(name).getType() == "boolean") && (expression.getType() !="boolean")){
						throw new IllegalArgumentException("Expected an expression with a boolean value! | line: "+line+" column: "+column+" in your program.");
					}
					if ((globals.get(name).getType() == "entity") && (expression.getType() !="entity")){
						throw new IllegalArgumentException("Expected an expression with an entity value! | line: "+line+" column: "+column+" in your program.");
					}
					globals.get(name).setValue(expression);
				} else {
					if (globals.get(name).getType() == "double"){
						globals.put(name,new Type<Double>(new Double(null)));
					}
					if (globals.get(name).getType() == "boolean"){
						globals.put(name,new Type<Boolean>(new Boolean(null)));
					}
					if (globals.get(name).getType() == "entity"){
						globals.put(name,new Type<EntityType>(new EntityType(null)));
					}
				}
				this.setExecuted(exeCheck);
			}
		};
	}

	@Override
	public Statement createIf(int line, int column, Expression<?> condition,
			Statement then, Statement otherwise) {
		if (condition.getType() != "boolean") {
			throw new IllegalArgumentException("Expected an expression with a boolean value! | line: "+line+" column: "+column+" in your program.");
		}
		return new IfStatement(line, column,
				(Expression<Boolean>) condition, then, otherwise) {

			public void run(boolean exeCheck) {
				if (count >= 1000){
					throw new RuntimeException("You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				if ((Boolean) condition.getValue().getValue()) {
					ifTrue.execute(exeCheck);					
				} else {
					ifFalse.execute(exeCheck);					
				}
				this.setExecuted(exeCheck);
			}
		};
	}

	@Override
	public Statement createWhile(int line, int column, Expression<?> condition,
			Statement body) {
		if (condition.getType() != "boolean") {
			throw new IllegalArgumentException("Expected an expression with a boolean value! | line: "+line+" column: "+column+" in your program.");
		}
		return new WhileStatement(line, column,
				(Expression<Boolean>) condition, body) {

			private boolean whileExecutionCheck = true;

			public void run(boolean exeCheck) {
				if (count >= 1000){
					throw new RuntimeException("You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				while ((Boolean) condition.getValue().getValue()) {
					body.execute(whileExecutionCheck);
					body.executed = whileExecutionCheck;
					whileExecutionCheck = !whileExecutionCheck;
				}
				this.setExecuted(exeCheck);
			}
		};
	}

	@Override
	public Statement createForeach(int line, int column,
			worms.model.programs.ProgramFactory.ForeachType type,
			String variableName, Statement body) {
		return new ForeachStatement(line,column,type,variableName,body) {
			
			private boolean foreachExecutionCheck = true;
			
			@Override
			public void run(boolean exeCheck) {
				if (count >= 1000){
					throw new RuntimeException("You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				boolean any = false;
				Type<?> oldVar = globals.get(variableName);
				if (type == worms.model.programs.ProgramFactory.ForeachType.ANY){
					any = true;
				}
				if ((any) || (type == worms.model.programs.ProgramFactory.ForeachType.WORM)){
					ArrayList<Worm> worms = worm.getWorld().getWormList();
					for(Worm w: worms){
						globals.put(variableName,new Type<EntityType>(new EntityType(w)));			
						body.execute(foreachExecutionCheck);
						foreachExecutionCheck = !foreachExecutionCheck;
					}
				}
				if ((any) || (type == worms.model.programs.ProgramFactory.ForeachType.FOOD)){
					ArrayList<Food> foods = worm.getWorld().getFoodList();
					for(Food f:foods){
						globals.put(variableName,new Type<EntityType>(new EntityType(f)));
						body.execute(foreachExecutionCheck);
						foreachExecutionCheck = !foreachExecutionCheck;
					}
				}
				globals.put(variableName,oldVar);
				this.setExecuted(exeCheck);				
			}
		};
	}

	@Override
	public Statement createSequence(int line, int column,
			List<Statement> statements) {
		return new Sequence(line, column, statements) {
			
			public boolean started = false;
			
			public void run(boolean exeCheck) {
				if (count >= 1000){
					throw new RuntimeException("You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				started = true;
				count = count +1;
				for (Statement st : statements) {
					st.execute(exeCheck);
				}
				this.setExecuted(exeCheck);
			}
		};
	}

	@Override
	public Statement createPrint(int line, int column, Expression<?> e) {
		return new ExpressionAction(line, column, e) {

			@Override
			public void run(boolean exeCheck) {
				if (count >= 1000){
					throw new RuntimeException("You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;				
				actionHandler.print(expression.getValue().toString());
				this.setExecuted(exeCheck);
			}
		};
	}

	@Override
	public Type<Double> createDoubleType() {
		return new Type<Double>(new Double(0.0));
	}

	@Override
	public Type<Boolean> createBooleanType() {
		return new Type<Boolean>(new Boolean(false));
	}

	@Override
	public Type<EntityType> createEntityType() {
		return new Type<EntityType>(new EntityType());
	}

	@Override
	public Expression<Object> createVariableAccess(int line, int column,
			String name, Type<?> type) {
		return new VariableAcces<Object>(line, column, name, type) {
			public Type<Object> getValue() {
				return (Type<Object>)globals.get(varName);
			}
		};
	}

}
