package worms.model;

//TODO fout in oude code bij doodgaan van wormen!!
//TODO fout in oude cobe bij wormen die van de map willen springen (endless loop)
//TODO check op TODO's in entity, world en  worm
//TODO skip werkt niet, programma blijft runnen, volgende worm komt ook aan beurt,
// dus er zijn 2 wormen tegelijkertijd aan beurt, en ik krijg een stackoverflow op mijn geheugen wrs
//TODO assertion error op 'move', hij springt niet,

/*
 * als een worm op het einde van zijn programma komt stop die automatisch!
 */

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
		return "double";
	}

	public VariableAcces(int l, int c, String n) {
		super(l, c);
		name = n;
	}

	public String name;

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
		System.out.println("execute with check:" + exeCheck + " and executed: "
				+ executed);
		if (executed != exeCheck) {
			System.out.println("not yet Executed");
			run(exeCheck);
		} else {
			System.out.println("Alraedy executed");
		}
	}

	abstract public void run(boolean exeCheck);
}

abstract class Sequence extends Statement {

	public Sequence(int l, int c, List<Statement> st) {
		super(l, c);
		statements = st;
	}

	public List<Statement> statements;
}

abstract class ExpressionAction extends Statement {

	public ExpressionAction(int l, int c, Expression<?> e) {
		super(l, c);
		expression = e;
	}

	public Expression<?> expression;
}

abstract class SingleExpressionCond extends Statement {

	public SingleExpressionCond(int l, int c, Expression<Boolean> cond,
			Statement b) {
		super(l, c);
		condition = cond;
		body = b;
	}

	public Expression<Boolean> condition;
	public Statement body;
}

abstract class DoubleExpressionCond extends Statement {

	public DoubleExpressionCond(int l, int c, Expression<Boolean> cond,
			Statement t, Statement f) {
		super(l, c);
		condition = cond;
		ifTrue = t;
		ifFalse = f;
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

	public Expression<?> expression;
	public String name;

}

class Type<T> {

	public Type() {
		value = null;
	}

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
		// ProgramParser<PrintingObject, PrintingObject, PrintingObject>
		// printParser = new ProgramParser<PrintingObject, PrintingObject,
		// PrintingObject>(new PrintingProgramFactoryImpl());
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

	public List<String> getErrors() {
		return errors;
	}

	public void setWorm(Worm w) {
		worm = w;
	}

	public void runProgram() {
		System.out.println("Run with check: " + executionCheck);
		try {
			statement.execute(executionCheck);
			executionCheck = !executionCheck;
		} catch (Exception exc) {
			System.out.println("error");

		}
		worm.getWorld().startNextTurn();
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
			throw new ClassCastException();
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
			throw new ClassCastException();
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
			throw new ClassCastException();
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
				return null;
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
				return "worm";
			}

		};
	}

	@Override
	public Expression<Double> createGetX(int line, int column, Expression<?> e) {
		if ((e.getType() != "worm") && (e.getType() != "food")) {
			throw new ClassCastException();
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
		if ((e.getType() != "worm") && (e.getType() != "food")) {
			throw new ClassCastException();
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
		if ((e.getType() != "worm") && (e.getType() != "food")) {
			throw new ClassCastException();
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				return new Type<Double>(((Entity) expression1.getValue()
						.getValue()).getRadius());
			}

			@Override
			public String getType() {
				return "double";
			}
		};
	}

	@Override
	public Expression<Double> createGetDir(int line, int column, Expression<?> e) {
		if (e.getType() != "worm") {
			throw new ClassCastException();
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				return new Type<Double>(((Worm) expression1.getValue()
						.getValue()).getDirection());
			}

			@Override
			public String getType() {
				return "double";
			}
		};
	}

	@Override
	public Expression<Double> createGetAP(int line, int column, Expression<?> e) {
		if (e.getType() != "worm") {
			throw new ClassCastException();
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				return new Type<Double>((double) ((Worm) expression1.getValue()
						.getValue()).getActionPoints());
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
		if (e.getType() != "worm") {
			throw new ClassCastException();
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				return new Type<Double>((double) ((Worm) expression1.getValue()
						.getValue()).getMaxActionPoints());
			}

			@Override
			public String getType() {
				return "double";
			}
		};
	}

	@Override
	public Expression<Double> createGetHP(int line, int column, Expression<?> e) {
		if (e.getType() != "worm") {
			throw new ClassCastException();
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				return new Type<Double>((double) ((Worm) expression1.getValue()
						.getValue()).getHitPoints());
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
		if (e.getType() != "worm") {
			throw new ClassCastException();
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				return new Type<Double>((double) ((Worm) expression1.getValue()
						.getValue()).getMaxHitPoints());
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
		if (e.getType() != "worm") {
			throw new ClassCastException();
		}
		return new Expression<Boolean>(line, column, e) {

			@Override
			public Type<Boolean> getValue() {
				return new Type<Boolean>(((Worm) expression1.getValue()
						.getValue()).getTeam() == worm.getTeam());
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
		if ((e.getType() != "worm") || (e.getType() != "food")) {
			throw new ClassCastException();
		}
		return new Expression<Boolean>(line, column, e) {

			@Override
			public Type<Boolean> getValue() {
				return new Type<Boolean>(expression1.getValue().getValue()
						.getClass() == Worm.class);
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
		if ((e.getType() != "worm") || (e.getType() != "food")) {
			throw new ClassCastException();
		}
		return new Expression<Boolean>(line, column, e) {

			@Override
			public Type<Boolean> getValue() {
				return new Type<Boolean>(expression1.getValue().getValue()
						.getClass() == Food.class);
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
			throw new ClassCastException();
		}
		return new Expression<EntityType>(line, column, e) {

			@Override
			public Type<EntityType> getValue() {
				/*
				 * Entity ent =
				 * worm.getWorld().search((Double)expression1.getValue
				 * ().getValue()); return new Type<EntityType>(new
				 * EntityType(ent));
				 */
				return null;
			}

			@Override
			public String getType() {
				if (value.getValue().getValue() instanceof Worm) {
					return "worm";
				} else if (value.getValue().getValue() instanceof Food) {
					return "food";
				} else {
					return "False enity";
				}
			}

		};
	}

	@Override
	public Expression<Boolean> createLessThan(int line, int column,
			Expression<?> e1, Expression<?> e2) {
		if ((e1.getType() != e2.getType()) || (e1.getType() == "worm")
				&& (e1.getType() == "food")) {
			throw new ClassCastException();
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
		if ((e1.getType() != e2.getType()) || (e1.getType() == "worm")
				|| (e1.getType() == "food")) {
			throw new ClassCastException();
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
		if ((e1.getType() != e2.getType()) || (e1.getType() == "worm")
				|| (e1.getType() == "food")) {
			throw new ClassCastException();
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
		if ((e1.getType() != e2.getType()) || (e1.getType() == "worm")
				|| (e1.getType() == "food")) {
			throw new ClassCastException();
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
		if ((e1.getType() != e2.getType()) || (e1.getType() == "worm")
				|| (e1.getType() == "food")) {
			throw new ClassCastException();
		}
		return new Expression<Boolean>(line, column, e1, e2) {

			@Override
			public Type<Boolean> getValue() {
				return new Type<Boolean>(expression1.getValue().getValue()
						.equals(expression2.getValue().getValue()));
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
		if ((e1.getType() != e2.getType()) || (e1.getType() == "worm")
				|| (e1.getType() == "food")) {
			throw new ClassCastException();
		}
		return new Expression<Boolean>(line, column, e1, e2) {

			@Override
			public Type<Boolean> getValue() {
				return new Type<Boolean>(!expression1.getValue().getValue()
						.equals(expression2.getValue().getValue()));
			}

			@Override
			public String getType() {
				return "boolean";
			}

		};
	}

	@Override
	public Expression<?> createVariableAccess(int line, int column, String name) {
		return new VariableAcces(line, column, name) {
			public Type<?> getValue() {
				return globals.get(name);
			}
		};
	}

	@Override
	public Expression<Double> createAdd(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		if ((e1.getType() != "double") || (e2.getType() != "double")) {
			throw new ClassCastException();
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
			throw new ClassCastException();
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
			throw new ClassCastException();
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
			throw new ClassCastException();
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
			throw new ClassCastException();
		}
		return new Expression<Double>(line, column, e) {
			public Type<Double> getValue() {
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
			throw new ClassCastException();
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
			throw new ClassCastException();
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
				System.out.println("Turn:");
				if (worm.canTurn((Double) expression.getValue().getValue())) {
					actionHandler.turn(worm, (Double) expression.getValue()
							.getValue());
					executed = exeCheck;
				} else {
					System.out.println("cannotTurn");
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
				System.out.println("move:");
				if (worm.canMove()) {
					actionHandler.move(worm);
					executed = exeCheck;
				} else {
					System.out.println("cannotMove");
					throw new IllegalStateException();
				}
			}
		};
	}

	@Override
	public Statement createJump(int line, int column) {
		return new Statement(line, column) {

			public void run(boolean exeCheck) {
				System.out.println("Jump:");
				if (worm.canJump()) {
					System.out.println("canJump");
					actionHandler.jump(worm);
					executed = exeCheck;
				} else {
					System.out.println("cannotJump");
					throw new IllegalStateException();
				}
			}

		};
	}

	@Override
	public Statement createToggleWeap(int line, int column) {
		return new Statement(line, column) {
			public void run(boolean exeCheck) {
				System.out.println("toggleWeapon");
				actionHandler.toggleWeapon(worm);
				executed = exeCheck;
			}
		};
	}

	@Override
	public Statement createFire(int line, int column, Expression<?> yield) {
		if (yield.getType() != "double") {
			throw new ClassCastException();
		}
		return new ExpressionAction(line, column, yield) {

			@Override
			public void run(boolean exeCheck) {
				System.out.println("fire:");
				if (worm.canShoot(worm.getWeapon())) {
					actionHandler.fire(worm, ((Double) expression.getValue()
							.getValue()).intValue());
					executed = exeCheck;
				} else {
					System.out.println("cannotJump");
					throw new IllegalStateException();
				}
			}
		};
	}

	@Override
	public Statement createSkip(int line, int column) {
		return new Statement(line, column) {
			public void run(boolean exeCheck) {
				System.out.println("Skip:");
				worm.getWorld().startNextTurn();
				executed = exeCheck;
			}
		};
	}

	@Override
	public Statement createAssignment(int line, int column,
			String variableName, Expression<?> rhs) {
		return new Assignment(line, column, rhs, variableName) {
			public void run(boolean exeCheck) {
				System.out.println("assign");
				// TODO runtime error handling bij verkeerde assignment
				globals.get(name).setValue(expression);
				executed = exeCheck;
			}
		};
	}

	@Override
	public Statement createIf(int line, int column, Expression<?> condition,
			Statement then, Statement otherwise) {
		return new DoubleExpressionCond(line, column,
				(Expression<Boolean>) condition, then, otherwise) {

			public void run(boolean exeCheck) {
				System.out.println("if:");
				if ((Boolean) condition.getValue().getValue()) {
					ifTrue.execute(exeCheck);
				} else {
					ifFalse.execute(exeCheck);
				}
				executed = exeCheck;
			}
		};
	}

	@Override
	public Statement createWhile(int line, int column, Expression<?> condition,
			Statement body) {
		return new SingleExpressionCond(line, column,
				(Expression<Boolean>) condition, body) {

			private boolean whileExecutionCheck = true;

			public void run(boolean exeCheck) {
				System.out.println("while");
				while ((Boolean) condition.getValue().getValue()) {
					System.out.println("Ex body");
					body.execute(whileExecutionCheck);
					body.executed = whileExecutionCheck;
					System.out.println("whileExecutionCheck: "
							+ whileExecutionCheck);
					whileExecutionCheck = !whileExecutionCheck;
					System.out.println("whileExecutionCheck: "
							+ whileExecutionCheck);
				}
				System.out.println("endWhile");
				executed = exeCheck;
			}
		};
	}

	@Override
	public Statement createForeach(int line, int column,
			worms.model.programs.ProgramFactory.ForeachType type,
			String variableName, Statement body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Statement createSequence(int line, int column,
			List<Statement> statements) {
		return new Sequence(line, column, statements) {
			public void run(boolean exeCheck) {
				System.out.println("runSequence with check: " + executionCheck);
				for (Statement st : statements) {
					st.execute(exeCheck);
				}
				System.out.println("end Sequence");
				executed = exeCheck;
			}
		};
	}

	@Override
	public Statement createPrint(int line, int column, Expression<?> e) {
		return new ExpressionAction(line, column, e) {

			@Override
			public void run(boolean exeCheck) {
				System.out.println("print");
				actionHandler.print(expression.getValue().toString());
				executed = exeCheck;
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
		return new Type<EntityType>();
	}

}
