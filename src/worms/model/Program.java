package worms.model;

import java.util.List;
import java.util.Map;

import worms.gui.game.IActionHandler;
import worms.model.programs.ProgramFactory;
import worms.model.programs.ProgramParser;
abstract class Expression<T> {


	public int line;
	public int column;
	public Expression(int l, int c){
		line = l;
		column = c;
	}
	
	
	public Expression(int line, int column,Type<T> d){
		this(line,column);
		value = d;
	}
	
	public Expression(int line, int column,Expression<?> e){
		this(line,column);
		expression1 = e;
	}
	
	public Expression(int line, int column,Expression<?> e1, Expression<?> e2){
		this(line,column);
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
	
	public String getType(){
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

class Type<T> {
	
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

class EntityType {

	public EntityType() {
		value = null;
	}

	public Entity value;

}

class WormType extends EntityType {

	public Worm value;

	public WormType(Worm w){
		value = w;
	}


	@Override
	public String toString() {
		return value.getName();
	}

}

class FoodType extends EntityType {

	public Food value;
	
	public FoodType(Food f){
		value = f;
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
		return new Expression<Double>(line, column, new Type<Double>(d)) {
			public Type<Double> getValue() {
				return value;
			}
			
			public String getType(){
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
			
			public String getType(){
				return "boolean";
			}
		};
	}

	@Override
	public Expression<Boolean> createAnd(int line, int column,
			Expression<?> e1, Expression<?> e2) {
		if ((e1.getType() != "boolean") || (e2.getType() != "boolean")){
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
		if ((e1.getType() != "boolean") || (e2.getType() != "boolean")){
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
		if ((e.getType() != "boolean")){
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
		return new Expression<Object>(line,column) {

			
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
	public Expression<WormType> createSelf(int line, int column) {
		return new Expression<WormType>(line,column,new Type<WormType>(new WormType(worm))){

			@Override
			public Type<WormType> getValue() {
				return value;
			}

			@Override
			public String getType() {
				return "worm";
			}
			
		};
	}

	@Override
	public Expression<Double> createGetX(int line, int column, Expression<?> e) {
		if ((e.getType() != "worm") && (e.getType() != "food")){
			throw new ClassCastException();
		}
		return new Expression<Double>(line,column,e) {

			@Override
			public Type<Double> getValue() {
				return new Type<Double>(((Entity)expression1.getValue().getValue()).getXCoordinate());
			}

			@Override
			public String getType() {
				return "double";
			}
		};
	}

	@Override
	public Expression<Double> createGetY(int line, int column, Expression<?> e) {
		if ((e.getType() != "worm") && (e.getType() != "food")){
			throw new ClassCastException();
		}
		return new Expression<Double>(line,column,e) {

			@Override
			public Type<Double> getValue() {
				return new Type<Double>(((Entity)expression1.getValue().getValue()).getYCoordinate());
			}

			@Override
			public String getType() {
				return "double";
			}	
		};
	}

	@Override
	public Expression<Double> createGetRadius(int line, int column, Expression<?> e) {
		if ((e.getType() != "worm") && (e.getType() != "food")){
			throw new ClassCastException();
		}
		return new Expression<Double>(line,column,e) {

			@Override
			public Type<Double> getValue() {
				return new Type<Double>(((Entity)expression1.getValue().getValue()).getRadius());
			}

			@Override
			public String getType() {
				return "double";
			}	
		};
	}

	@Override
	public Expression<Double> createGetDir(int line, int column, Expression<?> e) {
		if (e.getType() != "worm"){
			throw new ClassCastException();
		}
		return new Expression<Double>(line,column,e) {

			@Override
			public Type<Double> getValue() {
				return new Type<Double>(((Worm)expression1.getValue().getValue()).getDirection());
			}

			@Override
			public String getType() {
				return "double";
			}	
		};
	}

	@Override
	public Expression<Double> createGetAP(int line, int column, Expression<?> e) {
		if (e.getType() != "worm"){
			throw new ClassCastException();
		}
		return new Expression<Double>(line,column,e) {

			@Override
			public Type<Double> getValue() {
				return new Type<Double>((double)((Worm)expression1.getValue().getValue()).getActionPoints());
			}

			@Override
			public String getType() {
				return "double";
			}	
		};
	}

	@Override
	public Expression<Double> createGetMaxAP(int line, int column, Expression<?> e) {
		if (e.getType() != "worm"){
			throw new ClassCastException();
		}
		return new Expression<Double>(line,column,e) {

			@Override
			public Type<Double> getValue() {
				return new Type<Double>((double)((Worm)expression1.getValue().getValue()).getMaxActionPoints());
			}

			@Override
			public String getType() {
				return "double";
			}	
		};
	}

	@Override
	public Expression<Double> createGetHP(int line, int column, Expression<?> e) {
		if (e.getType() != "worm"){
			throw new ClassCastException();
		}
		return new Expression<Double>(line,column,e) {

			@Override
			public Type<Double> getValue() {
				return new Type<Double>((double)((Worm)expression1.getValue().getValue()).getHitPoints());
			}

			@Override
			public String getType() {
				return "double";
			}	
		};
	}

	@Override
	public Expression<Double> createGetMaxHP(int line, int column, Expression<?> e) {
		if (e.getType() != "worm"){
			throw new ClassCastException();
		}
		return new Expression<Double>(line,column,e) {

			@Override
			public Type<Double> getValue() {
				return new Type<Double>((double)((Worm)expression1.getValue().getValue()).getMaxHitPoints());
			}

			@Override
			public String getType() {
				return "double";
			}	
		};
	}

	@Override
	public Expression<Boolean> createSameTeam(int line, int column, Expression<?> e) {
		if (e.getType() != "worm"){
			throw new ClassCastException();
		}
		return new Expression<Boolean>(line,column,e) {

			@Override
			public Type<Boolean> getValue() {
				return new Type<Boolean>(((Worm)expression1.getValue().getValue()).getTeam() == worm.getTeam());
			}

			@Override
			public String getType() {
				return "boolean";
			}	
		};
	}

	@Override
	public Expression<Boolean> createIsWorm(int line, int column, Expression<?> e) {
		if ((e.getType() != "worm") || (e.getType() != "food")){
			throw new ClassCastException();
		}
		return new Expression<Boolean>(line,column,e) {

			@Override
			public Type<Boolean> getValue() {
				return new Type<Boolean>(expression1.getValue().getValue().getClass() == Worm.class);
			}

			@Override
			public String getType() {
				return "boolean";
			}			
			
		};
	}

	@Override
	public Expression<Boolean> createIsFood(int line, int column, Expression<?> e) {
		if ((e.getType() != "worm") || (e.getType() != "food")){
			throw new ClassCastException();
		}
		return new Expression<Boolean>(line,column,e) {

			@Override
			public Type<Boolean> getValue() {
				return new Type<Boolean>(expression1.getValue().getValue().getClass() == Food.class);
			}

			@Override
			public String getType() {
				return "boolean";
			}	
			
		};
	}

	@Override
	public Expression<EntityType> createSearchObj(int line, int column, Expression<?> e) {
		if (e.getType() != "double"){
			throw new ClassCastException();
		}
		return new Expression<EntityType>(line, column,e) {

			@Override
			public Type<EntityType> getValue() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getType() {
				// TODO Auto-generated method stub
				return null;
			}
			
		};
	}

	@Override
	public Expression<Boolean> createLessThan(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		if ((e1.getType() != e2.getType()) || (e1.getType() == "worm") && (e1.getType() == "food")){
			throw new ClassCastException();
		}
		return new Expression<Boolean>(line,column,e1,e2){

			@Override
			public Type<Boolean> getValue() {
				int compare = ((Comparable<Object>)expression1.getValue().getValue()).compareTo(((Comparable<Object>)expression2.getValue().getValue()));
				if (compare < 0){
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
	public Expression<Boolean> createGreaterThan(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		if ((e1.getType() != e2.getType()) || (e1.getType() == "worm") || (e1.getType() == "food")){
			throw new ClassCastException();
		}
		return new Expression<Boolean>(line,column,e1,e2){

			@Override
			public Type<Boolean> getValue() {
				int compare = ((Comparable<Object>)expression1.getValue().getValue()).compareTo(((Comparable<Object>)expression2.getValue().getValue()));
				if (compare > 0){
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
		if ((e1.getType() != e2.getType()) || (e1.getType() == "worm") || (e1.getType() == "food")){
			throw new ClassCastException();
		}
		return new Expression<Boolean>(line,column,e1,e2){

			@Override
			public Type<Boolean> getValue() {
				int compare = ((Comparable<Object>)expression1.getValue().getValue()).compareTo(((Comparable<Object>)expression2.getValue().getValue()));
				if (compare < 1){
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
		if ((e1.getType() != e2.getType()) || (e1.getType() == "worm") || (e1.getType() == "food")){
			throw new ClassCastException();
		}
		return new Expression<Boolean>(line,column,e1,e2){

			@Override
			public Type<Boolean> getValue() {
				int compare = ((Comparable<Object>)expression1.getValue().getValue()).compareTo(((Comparable<Object>)expression2.getValue().getValue()));
				if (compare > -1){
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
	public Expression<Boolean> createEquality(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		if ((e1.getType() != e2.getType()) || (e1.getType() == "worm") || (e1.getType() == "food")){
			throw new ClassCastException();
		}
		return new Expression<Boolean>(line,column,e1,e2){

			@Override
			public Type<Boolean> getValue() {
				return new Type<Boolean>(expression1.getValue().getValue().equals(expression2.getValue().getValue()));
			}

			@Override
			public String getType() {
				return "boolean";
			}
			
		};
	}

	@Override
	public Expression<Boolean> createInequality(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		if ((e1.getType() != e2.getType()) || (e1.getType() == "worm") || (e1.getType() == "food")){
			throw new ClassCastException();
		}
		return new Expression<Boolean>(line,column,e1,e2){

			@Override
			public Type<Boolean> getValue() {
				return new Type<Boolean>(!expression1.getValue().getValue().equals(expression2.getValue().getValue()));
			}

			@Override
			public String getType() {
				return "boolean";
			}
			
		};
	}

	@Override
	public Expression<?> createVariableAccess(int line, int column, String name) {
		System.out.println("CreateVariable:" + line + "|" + column + " name:"
				+ name);
		return new VariableAcces(line, column, name) {
			public Type<?> getValue() {
				System.out.println(name);
				return globals.get(name);
			}
		};
	}

	@Override
	public Expression<Double> createAdd(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		if ((e1.getType() != "double") || (e2.getType() != "double")){
			throw new ClassCastException();
		}
		return new Expression<Double>(line, column, e1, e2) {
			public Type<Double> getValue() {
				return new Type<Double>(
						((Double) expression1.getValue().getValue())
								+ ((Double) expression2.getValue().getValue()));
			}
			
			public String getType(){
				return "double";
			}
		};
	}

	@Override
	public Expression<Double> createSubtraction(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		if ((e1.getType() != "double") || (e2.getType() != "double")){
			throw new ClassCastException();
		}
		return new Expression<Double>(line, column, e1, e2) {
			public Type<Double> getValue() {
				return new Type<Double>(
						((Double) expression1.getValue().getValue())
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
		if ((e1.getType() != "double") || (e2.getType() != "double")){
			throw new ClassCastException();
		}
		return new Expression<Double>(line, column, e1, e2) {
			public Type<Double> getValue() {
				return new Type<Double>(
						((Double) expression1.getValue().getValue())
								* ((Double) expression2.getValue().getValue()));
			}

			@Override
			public String getType() {
				return "double";
			}
		};
	}

	@Override
	public Expression<Double> createDivision(int line, int column, Expression<?> e1,
			Expression<?> e2) {
		if ((e1.getType() != "double") || (e2.getType() != "double")){
			throw new ClassCastException();
		}
		return new Expression<Double>(line, column, e1, e2) {
			public Type<Double> getValue() {
				return new Type<Double>(
						((Double) expression1.getValue().getValue())
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
		if (e.getType() != "double"){
			throw new ClassCastException();
		}
		return new Expression<Double>(line, column, e) {
			public Type<Double> getValue() {
				return new Type<Double>(Math.sqrt(((Double) expression1.getValue().getValue())));
			}

			@Override
			public String getType() {
				return "double";
			}
		};
		
	}

	@Override
	public Expression<Double> createSin(int line, int column, Expression<?> e) {
		if (e.getType() != "double"){
			throw new ClassCastException();
		}
		return new Expression<Double>(line, column, e) {
			public Type<Double> getValue() {
				return new Type<Double>(Math.sin(((Double) expression1.getValue().getValue())));
			}

			@Override
			public String getType() {
				return "double";
			}
		};
		
	}

	@Override
	public Expression<Double> createCos(int line, int column, Expression<?> e) {
		if (e.getType() != "double"){
			throw new ClassCastException();
		}
		return new Expression<Double>(line, column, e) {
			public Type<Double> getValue() {
				return new Type<Double>(Math.cos(((Double) expression1.getValue().getValue())));
			}

			@Override
			public String getType() {
				return "double";
			}
		};
		
	}

	@Override
	public Statement createTurn(int line, int column, Expression<?> angle) {
		if (angle.getType() != "double"){
			throw new ClassCastException();
		}
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
		if (yield.getType() != "double"){
			throw new ClassCastException();
		}
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
		return new Type<Double>(new Double(0.0));
	}

	@Override
	public Type<Boolean> createBooleanType() {
		System.out.println("CreateBooleanType:");
		return new Type<Boolean>(new Boolean(false));
	}

	@Override
	public Type<EntityType> createEntityType() {
		System.out.println("CreateEntityType:");
		return new Type<EntityType>();
	}

}
