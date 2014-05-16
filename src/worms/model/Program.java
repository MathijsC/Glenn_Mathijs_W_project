package worms.model;

import java.util.List;
import java.util.Map;

import worms.gui.game.IActionHandler;
import worms.model.programs.ProgramFactory;
import worms.model.programs.ProgramParser;
import worms.model.programs.parser.PrintingProgramFactoryImpl;

abstract class Expression<T extends Comparable<?>> {

	public Expression(int l, int c) {
		line = l;
		column = c;
	}

	public int line;
	public int column;

	abstract public Type<T> getValue();

}

abstract class VariableAcces<T extends Comparable<?>> extends Expression<T> {

	public VariableAcces(int l, int c, String n) {
		super(l, c);
		name = n;
	}

	public String name;

}

abstract class NoneExpression<T extends Comparable<?>> extends Expression<T> {

	public NoneExpression(int l, int c, Type<T> t) {
		super(l, c);
		value = t;
	}

	public Type<T> value;

}

abstract class SingleExpression<T extends Comparable<?>> extends Expression<T> {

	public SingleExpression(int l, int c, Expression<?> e) {
		super(l, c);
		expression = e;
	}

	public Expression<?> expression;
}

abstract class DoubleExpression<T extends Comparable<?>> extends Expression<T> {

	public DoubleExpression(int l, int c, Expression<?> e1, Expression<?> e2) {
		super(l, c);
		expression1 = e1;
		expression2 = e2;
	}

	public Expression<?> expression1;
	public Expression<?> expression2;
}

abstract class Statement {

	public Statement(int l, int c) {
		line = l;
		column = c;
	}

	public int line;
	public int column;

	abstract public void run();
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

	public SingleExpressionCond(int l, int c, Expression<Boolean> cond, Statement b) {
		super(l, c);
		condition = cond;
		body = b;
	}

	public Expression<Boolean> condition;
	public Statement body;
}

abstract class DoubleExpressionCond extends Statement {

	public DoubleExpressionCond(int l, int c, Expression<Boolean> cond, Statement t,
			Statement f) {
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

class Type<T extends Comparable<?>> {
	
	public Type(){
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
		value = (T)v.getValue().getValue();
	}
	
	public String toString(){
		if (value == null){
			return "null";
		}
		return value.toString();
	}

}

/*class DoubleType extends Type {

	public DoubleType() {
		value = 0.0;
	}

	public DoubleType(double v) {
		value = v;
	}

	public String toString() {
		return ((Double) value).toString();
	}

	public double toDouble() {
		return value;
	}

	public int toInteger() {
		return ((Double) value).intValue();
	}

	public boolean toBoolean() {
		if (value == 0.0) {
			return false;
		} else {
			return true;
		}
	}

	public double value;

}*/

/*class BooleanType extends Type {

	public BooleanType() {
		value = false;
	}

	public BooleanType(boolean v) {
		value = v;
	}

	public Boolean getValue() {
		return value;
	}

	public String toString() {
		return ((Boolean) value).toString();
	}

	public double toDouble() {
		if (value == false) {
			return 0.0;
		} else {
			return 1.0;
		}
	}

	public boolean toBoolean() {
		return value;
	}

	public boolean value;
}*/

abstract class EntityType implements Comparable<Object> {

	public EntityType() {
		value = null;
	}

	public Object value;

	public abstract String toString();

}

class WormType extends EntityType {

	public Worm value;

	@Override
	public int compareTo(Object arg0) {
		Worm w = (Worm) arg0;
		return 0;
	}

	@Override
	public String toString() {
		return value.getName();
	}

}

class FoodType extends EntityType {

	public Food value;

	@Override
	public int compareTo(Object arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String toString() {
		return "A Hamburger";
	}

}

public class Program implements
		ProgramFactory<Expression<?>, Statement, Type<?>> {

	public Program(String programText, IActionHandler handler) {
		System.out.println("Parse");
		ProgramParser<Expression<?>, Statement, Type<?>> parser = new ProgramParser<Expression<?>, Statement, Type<?>>(
				this);
		//ProgramParser<PrintingObject, PrintingObject, PrintingObject> printParser = new ProgramParser<PrintingObject, PrintingObject, PrintingObject>(new PrintingProgramFactoryImpl());
		parser.parse(programText);
		actionHandler = handler;
		globals = parser.getGlobals();
		statement = parser.getStatement();
		System.out.println(globals);
	}

	private IActionHandler actionHandler;
	private Map<String, Type<?>> globals;
	private Statement statement;
	private Worm worm;

	public void setWorm(Worm w) {
		worm = w;
	}

	public void runProgram() {
		System.out.println("Run");
		statement.run();
		System.out.println(globals);

	}

	@Override
	public Expression<Double> createDoubleLiteral(int line, int column, double d) {
		System.out.println("DoubleLiteral:" + line + "|" + column + " double:"
				+ d);
		return new NoneExpression<Double>(line, column, new Type<Double>(d)) {
			public Type<Double> getValue() {
				return value;
			}
		};
	}

	@Override
	public Expression<Boolean> createBooleanLiteral(int line, int column,
			boolean b) {
		System.out.println("BooleanLiteral:" + line + "|" + column + " bool:"
				+ b);
		return new NoneExpression<Boolean>(line, column, new Type<Boolean>(b)) {
			public Type<Boolean> getValue() {
				return value;
			}
		};
	}

	@Override
	public Expression<Boolean> createAnd(int line, int column,
			Expression<?> e1, Expression<?> e2) {
		System.out.println("BooleanLiteral:" + line + "|" + column);
		return new DoubleExpression<Boolean>(line, column, e1, e2) {
			public Type<Boolean> getValue() {
				return new Type<Boolean>(((Boolean) expression1.getValue()
						.getValue())
						&& ((Boolean) expression1.getValue().getValue()));
			}
		};
	}

	@Override
	public Expression<Boolean> createOr(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		return new DoubleExpression<Boolean>(line, column, e1, e2) {
			public Type<Boolean> getValue() {
				return new Type<Boolean>(((Boolean) expression1.getValue()
						.getValue())
						|| ((Boolean) expression1.getValue().getValue()));
			}
		};
	}

	@Override
	public Expression<Boolean> createNot(int line, int column, Expression<?> e) {
		return new SingleExpression<Boolean>(line, column, e) {
			public Type<Boolean> getValue() {
				return new Type<Boolean>(
						!((Boolean) expression.getValue().getValue()));
			}
		};
	}

	@Override
	public Expression createNull(int line, int column) {
		// TODO Auto-generated method stub
		System.out.println("CreateNull:" + line + "|" + column);
		return null;
	}

	@Override
	public Expression createSelf(int line, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetX(int line, int column, Expression<?> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetY(int line, int column, Expression<?> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetRadius(int line, int column, Expression<?> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetDir(int line, int column, Expression<?> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetAP(int line, int column, Expression<?> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetMaxAP(int line, int column, Expression<?> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetHP(int line, int column, Expression<?> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGetMaxHP(int line, int column, Expression<?> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createSameTeam(int line, int column, Expression<?> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createIsWorm(int line, int column, Expression<?> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createIsFood(int line, int column, Expression<?> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createSearchObj(int line, int column, Expression<?> e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createLessThan(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGreaterThan(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createLessThanOrEqualTo(int line, int column,
			Expression<?> e1, Expression<?> e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createGreaterThanOrEqualTo(int line, int column,
			Expression<?> e1, Expression<?> e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createEquality(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createInequality(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Expression createVariableAccess(int line, int column, String name) {
		System.out.println("CreateVariable:" + line + "|" + column + " name:"
				+ name);
		return new VariableAcces(line, column, name) {
			public Type getValue() {
				System.out.println(name);
				return globals.get(name);
			}
		};
	}

	@Override
	public Expression<Double> createAdd(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		return new DoubleExpression<Double>(line, column, e1, e2) {
			public Type<Double> getValue() {
				return new Type<Double>(
						((Double) expression1.getValue().getValue())
								+ ((Double) expression2.getValue().getValue()));
			}
		};
	}

	@Override
	public Expression<Double> createSubtraction(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		return new DoubleExpression<Double>(line, column, e1, e2) {
			public Type<Double> getValue() {
				return new Type<Double>(
						((Double) expression1.getValue().getValue())
								- ((Double) expression2.getValue().getValue()));
			}
		};
	}

	@Override
	public Expression<Double> createMul(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		return new DoubleExpression<Double>(line, column, e1, e2) {
			public Type<Double> getValue() {
				return new Type<Double>(
						((Double) expression1.getValue().getValue())
								* ((Double) expression2.getValue().getValue()));
			}
		};
	}

	@Override
	public Expression<Double> createDivision(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		return new DoubleExpression<Double>(line, column, e1, e2) {
			public Type<Double> getValue() {
				return new Type<Double>(
						((Double) expression1.getValue().getValue())
								/ ((Double) expression2.getValue().getValue()));
			}
		};
	}

	@Override
	public Expression<Double> createSqrt(int line, int column, Expression<?> e) {
		return new SingleExpression<Double>(line, column, e) {
			public Type<Double> getValue() {
				return new Type<Double>(Math.sqrt(((Double) expression.getValue().getValue())));
			}
		};
	}

	@Override
	public Expression<Double> createSin(int line, int column, Expression<?> e) {
		return new SingleExpression<Double>(line, column, e) {
			public Type<Double> getValue() {
				return new Type<Double>(Math.sin(((Double) expression.getValue().getValue())));
			}
		};
	}

	@Override
	public Expression<Double> createCos(int line, int column, Expression<?> e) {
		return new SingleExpression<Double>(line, column, e) {
			public Type<Double> getValue() {
				return new Type<Double>(Math.cos(((Double) expression.getValue().getValue())));
			}
		};
	}

	@Override
	public Statement createTurn(int line, int column, Expression<?> angle) {
		return new ExpressionAction(line, column, angle) {

			@Override
			public void run() {
				actionHandler.turn(worm,
						(Double) expression.getValue().getValue());
			}
		};
	}

	@Override
	public Statement createMove(int line, int column) {
		return new Statement(line, column) {

			@Override
			public void run() {
				actionHandler.move(worm);
			}
		};
	}

	@Override
	public Statement createJump(int line, int column) {
		return new Statement(line, column) {

			@Override
			public void run() {
				actionHandler.jump(worm);
			}

		};
	}

	@Override
	public Statement createToggleWeap(int line, int column) {
		return new Statement(line, column) {
			public void run() {
				actionHandler.toggleWeapon(worm);
			}
		};
	}

	@Override
	public Statement createFire(int line, int column, Expression<?> yield) {
		return new ExpressionAction(line, column, yield) {

			@Override
			public void run() {
				actionHandler.fire(worm,
						((Double) expression.getValue().getValue()).intValue());
			}
		};
	}

	@Override
	public Statement createSkip(int line, int column) {
		return new Statement(line, column) {
			public void run() {
				worm.getWorld().startNextTurn();
			}
		};
	}

	@Override
	public Statement createAssignment(int line, int column,
			String variableName, Expression<?> rhs) {
		System.out.println("Assignment:" + line + "|" + column + " name:"
				+ variableName);
		return new Assignment(line, column, rhs, variableName) {
			public void run() {
				System.out.println("assign " + expression.getValue() + " to "
						+ name);
				globals.get(name).setValue(expression);
			}
		};
	}

	@Override
	public Statement createIf(int line, int column, Expression<?> condition,
			Statement then, Statement otherwise) {
		return new DoubleExpressionCond(line, column, (Expression<Boolean>)condition, then,
				otherwise) {

			public void run() {
				if ((Boolean) condition.getValue().getValue()) {
					ifTrue.run();
				} else {
					ifFalse.run();
				}
			}
		};
	}

	@Override
	public Statement createWhile(int line, int column, Expression<?> condition,
			Statement body) {
		return new SingleExpressionCond(line, column, (Expression<Boolean>)condition, body) {
			public void run() {
				while ((Boolean) condition.getValue().getValue()) {
					body.run();
				}
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
		System.out.println("CreateSequence:" + line + "|" + column);
		return new Sequence(line, column, statements) {
			public void run() {
				for (Statement st : statements) {
					st.run();
				}
			}
		};
	}

	@Override
	public Statement createPrint(int line, int column, Expression<?> e) {
		System.out.println("CreatePrint:" + line + "|" + column);
		return new ExpressionAction(line, column, e) {

			@Override
			public void run() {
				actionHandler.print(expression.getValue().toString());
			}
		};
	}

	@Override
	public Type<Double> createDoubleType() {
		System.out.println("CreateDoubleType:");
		return new Type<Double>();
	}

	@Override
	public Type<Boolean> createBooleanType() {
		System.out.println("CreateBooleanType:");
		return new Type<Boolean>();
	}

	@Override
	public Type<EntityType> createEntityType() {
		System.out.println("CreateEntityType:");
		return new Type<EntityType>();
	}

}
