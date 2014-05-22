package worms.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a class of statements used in the game of worms to parse statements
 * of a program of a worm.
 * 
 * @author Glenn Cools & Mathijs Cuppens
 */
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
		}
	}

	protected void setExecuted(boolean exeSet) {
		executed = exeSet;
	}

	abstract public void run(boolean exeCheck);
}

//DIFFERENT STATEMENTS

abstract class Sequence extends Statement {

	public Sequence(int l, int c, List<Statement> st) {
		super(l, c);
		statements = st;
	}

	@Override
	protected void setExecuted(boolean exeSet) {
		for (Statement st : statements) {
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
	protected void setExecuted(boolean exeSet) {
		executed = exeSet;
	}

	public Expression<?> expression;
}

abstract class WhileStatement extends Statement {

	public WhileStatement(int l, int c, Expression<Boolean> cond, Statement b) {
		super(l, c);
		condition = cond;
		body = b;
	}

	@Override
	protected void setExecuted(boolean exeSet) {
		body.setExecuted(exeSet);
		executed = exeSet;
	}

	public Expression<Boolean> condition;
	public Statement body;
}

abstract class ForeachStatement extends Statement {

	public ForeachStatement(int l, int c,
			worms.model.programs.ProgramFactory.ForeachType t, String varName,
			Statement b) {
		super(l, c);
		variableName = varName;
		body = b;
		type = t;

	}

	private ArrayList<Entity> entList;

	@Override
	protected void setExecuted(boolean exeSet) {
		body.setExecuted(exeSet);
		executed = exeSet;
	}

	public String variableName;
	public Statement body;
	public worms.model.programs.ProgramFactory.ForeachType type;
}

abstract class IfStatement extends Statement {

	public IfStatement(int l, int c, Expression<Boolean> cond, Statement t,
			Statement f) {
		super(l, c);
		condition = cond;
		ifTrue = t;
		ifFalse = f;
	}

	@Override
	protected void setExecuted(boolean exeSet) {
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
	protected void setExecuted(boolean exeSet) {
		executed = exeSet;
	}

	public Expression<?> expression;
	public String name;

}
