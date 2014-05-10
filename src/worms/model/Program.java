package worms.model;

import java.util.List;
import java.util.Map;

import worms.gui.game.IActionHandler;
import worms.model.programs.ProgramFactory;
import worms.model.programs.ProgramParser;

abstract class  Expression {
	
	public Expression (int l, int c){
		line = l;
		column = c;
	}
	
	abstract public <T> T getValue();
	
	public String toString(){
		return getValue().toString();
	}
	
	public int line;
	public int column;
	
}

abstract class VariableAcces extends Expression {
	
	public VariableAcces(int l, int c, String n){
		super(l,c);
		name = n;
	}

	
	public String name;
	
}

abstract class NoneExpression<T> extends Expression {
	
	public NoneExpression(int l, int c, T t){
		super(l,c);
		value = t;
	}
	
	public T value;
	
	
}

abstract class SingleExpression<T> extends Expression {
	
	public SingleExpression(int l, int c,Expression e){
		super(l,c);
		expression = e;
	}
		
	public Expression expression;
}

abstract class DoubleExpression<T> extends Expression {
	
	public DoubleExpression(int l, int c,Expression e1,Expression e2){
		super(l,c);
		expression1 = e1;
		expression2 = e2;
	}
	
	
	
	public Expression expression1;
	public Expression expression2;
}



abstract class Statement {

	public Statement(int l, int c){
		line = l;
		column = c;		
	}
	
	public int line;
	public int column;
	
	abstract public void run(); 
	abstract public String getName();
}

abstract class Sequence extends Statement{
	
	public Sequence(int l, int c,List<Statement> st){
		super(l,c);
		statements = st;
	}
	
	public List<Statement> statements;
}

abstract class ExpressionAction extends Statement{
	
	public ExpressionAction(int l, int c,Expression e){
		super(l,c);
		expression = e;
	}
	
	public Expression expression;
}

abstract class Assignment extends Statement{
	
	public Assignment(int l, int c,Expression e,String n){
		super(l,c);
		expression = e;
		name = n;
	}
	
	public Expression expression;
	public String name;
}

abstract class Type {
	
	abstract public <T> T getValue();
	abstract public String toString();
	
}

class DoubleType extends Type{
	
	public DoubleType(){
		value = 0.0;
	}
	
	public DoubleType(double v){
		value = v;
	}
	
	public Double getValue(){
		return value;
	}
	
	public String toString(){
		return ((Double)value).toString();
	}
	
	public double value;
	
}

class BooleanType extends Type{
	
	public BooleanType(){
		value = false;
	}
	
	public BooleanType(boolean v){
		value = v;
	}
	
	public Boolean getValue(){
		return value;
	}
	
	public String toString(){
		return ((Boolean)value).toString();
	}
	
	public boolean value;
	
}

class EntityType<T> extends Type{
	
	public void setValue(T v){
		value = v;
	}
	
	public T getValue(){
		return value;
	}
	
	public String toString(){
		if (value.getClass() == Worm.class){
			return ((Worm)value).getName();
		} else if (value.getClass() == Food.class){
			return "A Hamburger";
		} else {
			return null;
		}
	}
	
	public T value;
	
}

public class Program implements ProgramFactory<Expression, Statement, Type> {

		public Program(String programText, IActionHandler handler){
			System.out.println("Parse");
			ProgramParser<Expression, Statement, Type> parser = new ProgramParser<Expression, Statement, Type>(this);
			parser.parse(programText);
			actionHandler = handler;
			globals = parser.getGlobals();
			statement = parser.getStatement();
			System.out.println(globals);
			System.out.println(globals.get("x").getValue());
			System.out.println(statement.getName());
		}
		
		private IActionHandler actionHandler;
		private Map<String,Type> globals;
		private Statement statement;
		
		public void runProgram(){
			System.out.println("Run");
			statement.run();
			System.out.println(globals);
			System.out.println(globals.get("x").getValue());
			System.out.println(statement.getName());
		}
		
		@Override
		public Expression createDoubleLiteral(int line, int column, double d) {
			System.out.println("DoubleLiteral:"+line+"|"+column+" double:"+d);
			return  new NoneExpression<Double>(line, column, d){
				public Double getValue() {
					return value;
				}
			};
		}

		@Override
		public Expression createBooleanLiteral(int line, int column, boolean b) {
			System.out.println("BooleanLiteral:"+line+"|"+column+" bool:"+b);
			return new NoneExpression<Boolean>(line, column,b){
				public Boolean getValue(){
					return value;
				}
			};
		}

		@Override
		public Expression createAnd(int line, int column, Expression e1, Expression e2) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createOr(int line, int column, Expression e1, Expression e2) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createNot(int line, int column, Expression e) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createNull(int line, int column) {
			// TODO Auto-generated method stub
			System.out.println("CreateNull:"+line+"|"+column);
			return null;
		}

		@Override
		public Expression createSelf(int line, int column) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createGetX(int line, int column, Expression e) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createGetY(int line, int column, Expression e) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createGetRadius(int line, int column, Expression e) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createGetDir(int line, int column, Expression e) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createGetAP(int line, int column, Expression e) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createGetMaxAP(int line, int column, Expression e) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createGetHP(int line, int column, Expression e) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createGetMaxHP(int line, int column, Expression e) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createSameTeam(int line, int column, Expression e) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createSearchObj(int line, int column, Expression e) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createIsWorm(int line, int column, Expression e) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createIsFood(int line, int column, Expression e) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createVariableAccess(int line, int column, String name) {
			System.out.println("CreateVariable:"+line+"|"+column+" name:"+name);
			return new VariableAcces(line,column,name){
				public Type getValue(){
					System.out.println(name);
					return globals.get(name);
				}
			};
		}

		@Override
		public Expression createLessThan(int line, int column, Expression e1, Expression e2) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createGreaterThan(int line, int column, Expression e1, Expression e2) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createLessThanOrEqualTo(int line, int column, Expression e1, Expression e2) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createGreaterThanOrEqualTo(int line, int column, Expression e1, Expression e2) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createEquality(int line, int column, Expression e1, Expression e2) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createInequality(int line, int column, Expression e1, Expression e2) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createAdd(int line, int column, Expression e1, Expression e2) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createSubtraction(int line, int column, Expression e1, Expression e2) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createMul(int line, int column, Expression e1, Expression e2) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createDivision(int line, int column, Expression e1, Expression e2) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createSqrt(int line, int column, Expression e) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createSin(int line, int column, Expression e) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Expression createCos(int line, int column, Expression e) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Statement createTurn(int line, int column, Expression angle) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Statement createMove(int line, int column) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Statement createJump(int line, int column) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Statement createToggleWeap(int line, int column) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Statement createFire(int line, int column, Expression yield) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Statement createSkip(int line, int column) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Statement createAssignment(int line, int column, String variableName, Expression rhs) {
			System.out.println("Assignment:"+line+"|"+column+" name:"+variableName+" rhs:"+rhs.getValue());
			return new Assignment(line,column,rhs,variableName) {
				public String getName(){
					return "Assignment";
				}
				
				public void run() {
					if (globals.get(name).getClass().getName() == "worms.model.DoubleType"){
						globals.put(name,new DoubleType((Double)expression.getValue()));
					} else if (globals.get(name).getClass().getName() == "worms.model.BooleanType"){
						globals.put(name,new BooleanType((Boolean)expression.getValue()));
					}
				}
			};
		}

		@Override
		public Statement createIf(int line, int column, Expression condition, Statement then, Statement otherwise) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Statement createWhile(int line, int column, Expression condition, Statement body) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Statement createForeach(int line, int column,
				worms.model.programs.ProgramFactory.ForeachType type,
				String variableName, Statement body) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public Statement createSequence(int line, int column, List<Statement> statements) {
			System.out.println("CreateSequence:"+line+"|"+column);
			return new Sequence(line,column,statements) {
				public String getName(){
					return "Sequence";
				}
				public void run() {
					for(Statement st:statements){
						st.run();
					}
					
				}
			};
		}

		@Override
		public Statement createPrint(int line, int column, Expression e) {
			System.out.println("CreatePrint:"+line+"|"+column);
			return new ExpressionAction(line,column,e) {
				
				@Override
				public void run() {
					actionHandler.print(expression.toString());					
				}
				
				@Override
				public String getName() {
					return "Print";
				}
			};
		}

		@Override
		public Type createDoubleType() {
			System.out.println("CreateDoubleType:");
			return new DoubleType();
		}

		@Override
		public Type createBooleanType() {
			System.out.println("CreateBooleanType:");
			return new BooleanType();
		}

		@Override
		public Type createEntityType() {
			// TODO Auto-generated method stub
			System.out.println("CreateEntityType:");
			return null;
		}

	

}
