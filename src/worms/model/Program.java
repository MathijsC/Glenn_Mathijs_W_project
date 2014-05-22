package worms.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import worms.gui.game.IActionHandler;
import worms.model.programs.ProgramFactory;
import worms.model.programs.ProgramParser;

/**
 * This is a class of programs used in the game of worms to represend a program
 * of a worm. This class also implements a method to run this program.
 * 
 * @author Glenn Cools & Mathijs Cuppens *
 */
public class Program implements
		ProgramFactory<Expression<?>, Statement, Type<?>> {

	public Program(String programText, IActionHandler handler) {
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

	/**
	 * This method runs this programs statements.
	 */
	public void runProgram() {
		if (!(worm.getWorld().isGameFinished())) {
			try {
				if (count >= 1000) {
					throw new StackOverflowError(
							"Last runtime, you executed 1000 statements. We suppose you are in an endless loop!");
				}
				count = 0;
				statement.execute(executionCheck);
				executionCheck = !executionCheck;
				if (worm.getWorld().getCurrentWorm() == worm) {
					worm.getWorld().startNextTurn();
				}
			} catch (StopProgramException exc) {
				System.out
						.println("StopProgramException! |" + exc.getMessage());
			} catch (IllegalStateException exc) {
				System.out.println("Illegal State error! |" + exc.getMessage());
				if (worm.getActionPoints() != 0) {
					worm.getWorld().startNextTurn();
				}
			} catch (ClassCastException exc) {
				System.out.println("ClassCastException! |" + exc.getMessage());
				worm.getWorld().startNextTurn();
			} catch (StackOverflowError exc) {
				System.out.println("StackOverflowError! |" + exc.getMessage());
				worm.getWorld().startNextTurn();
			}
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
			throw new IllegalArgumentException(
					"Expected a expressions with a boolean value! | line: "
							+ line + " column: " + column + " in your program.");
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
			throw new IllegalArgumentException(
					"Expected a expressions with a boolean value! | line: "
							+ line + " column: " + column + " in your program.");
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
			throw new IllegalArgumentException(
					"Expected an expression with a boolean value! | line: "
							+ line + " column: " + column + " in your program.");
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
		if (e.getType() != "entity") {
			throw new IllegalArgumentException(
					"Expected an expression with an entity value! | line: "
							+ line + " column: " + column + " in your program.");
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				if (((EntityType) expression1.getValue().getValue()).getValue() == null) {
					throw new IllegalArgumentException(
							"Expected an expression with an entity value which is not NULL! | line: "
									+ line + " column: " + column
									+ " in your program.");
				}
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
		if (e.getType() != "entity") {
			throw new IllegalArgumentException(
					"Expected an expression with an entity value! | line: "
							+ line + " column: " + column + " in your program.");
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				if (((EntityType) expression1.getValue().getValue()).getValue() == null) {
					throw new IllegalArgumentException(
							"Expected an expression with an entity value which is not NULL! | line: "
									+ line + " column: " + column
									+ " in your program.");
				}
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
		if (e.getType() != "entity") {
			throw new IllegalArgumentException(
					"Expected an expression with an entity value! | line: "
							+ line + " column: " + column + " in your program.");
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				if (((EntityType) expression1.getValue().getValue()).getValue() == null) {
					throw new IllegalArgumentException(
							"Expected an expression with an entity value which is not NULL! | line: "
									+ line + " column: " + column
									+ " in your program.");
				}
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
			throw new IllegalArgumentException(
					"Expected an expression with an entity value! | line: "
							+ line + " column: " + column + " in your program.");
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				if (((EntityType) expression1.getValue().getValue()).getValue() == null) {
					throw new IllegalArgumentException(
							"Expected an expression with an entity value which is not NULL! | line: "
									+ line + " column: " + column
									+ " in your program.");
				}
				if (((EntityType) expression1.getValue().getValue()).getValue()
						.getClass() != Worm.class) {
					throw new ClassCastException(
							"Expected an expression with a worm value! | line: "
									+ line + " column: " + column
									+ " in your program.");
				}
				return new Type<Double>(((Worm) ((EntityType) expression1
						.getValue().getValue()).getValue()).getDirection());
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
			throw new IllegalArgumentException(
					"Expected an expression with an entity value! | line: "
							+ line + " column: " + column + " in your program.");
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				if (((EntityType) expression1.getValue().getValue()).getValue() == null) {
					throw new IllegalArgumentException(
							"Expected an expression with an entity value which is not NULL! | line: "
									+ line + " column: " + column
									+ " in your program.");
				}
				if (((EntityType) expression1.getValue().getValue()).getValue()
						.getClass() != Worm.class) {
					throw new ClassCastException(
							"Expected an expression with a worm value! | line: "
									+ line + " column: " + column
									+ " in your program.");
				}
				return new Type<Double>(
						(double) (((Worm) ((EntityType) expression1.getValue()
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
			throw new IllegalArgumentException(
					"Expected an expression with an entity value! | line: "
							+ line + " column: " + column + " in your program.");
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				if (((EntityType) expression1.getValue().getValue()).getValue() == null) {
					throw new IllegalArgumentException(
							"Expected an expression with an entity value which is not NULL! | line: "
									+ line + " column: " + column
									+ " in your program.");
				}
				if (((EntityType) expression1.getValue().getValue()).getValue()
						.getClass() != Worm.class) {
					throw new ClassCastException(
							"Expected an expression with a worm value! | line: "
									+ line + " column: " + column
									+ " in your program.");
				}
				return new Type<Double>(
						(double) (((Worm) ((EntityType) expression1.getValue()
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
			throw new IllegalArgumentException(
					"Expected an expression with an entity value! | line: "
							+ line + " column: " + column + " in your program.");
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				if (((EntityType) expression1.getValue().getValue()).getValue() == null) {
					throw new IllegalArgumentException(
							"Expected an expression with an entity value which is not NULL! | line: "
									+ line + " column: " + column
									+ " in your program.");
				}
				if (((EntityType) expression1.getValue().getValue()).getValue()
						.getClass() != Worm.class) {
					throw new ClassCastException(
							"Expected an expression with a worm value! | line: "
									+ line + " column: " + column
									+ " in your program.");
				}
				return new Type<Double>(
						(double) (((Worm) ((EntityType) expression1.getValue()
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
			throw new IllegalArgumentException(
					"Expected an expression with an entity value! | line: "
							+ line + " column: " + column + " in your program.");
		}
		return new Expression<Double>(line, column, e) {

			@Override
			public Type<Double> getValue() {
				if (((EntityType) expression1.getValue().getValue()).getValue() == null) {
					throw new IllegalArgumentException(
							"Expected an expression with an entity value which is not NULL! | line: "
									+ line + " column: " + column
									+ " in your program.");
				}
				if (((EntityType) expression1.getValue().getValue()).getValue()
						.getClass() != Worm.class) {
					throw new ClassCastException(
							"Expected an expression with a worm value! | line: "
									+ line + " column: " + column
									+ " in your program.");
				}
				return new Type<Double>(
						(double) (((Worm) ((EntityType) expression1.getValue()
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
			throw new IllegalArgumentException(
					"Expected an expression with an entity value! | line: "
							+ line + " column: " + column + " in your program.");
		}
		return new Expression<Boolean>(line, column, e) {

			@Override
			public Type<Boolean> getValue() {
				if (((EntityType) expression1.getValue().getValue()).getValue() == null) {
					return new Type<Boolean>(false);
				}
				if (((EntityType) expression1.getValue().getValue()).getValue()
						.getClass() != Worm.class) {
					throw new ClassCastException(
							"Expected an expression with a worm value! | line: "
									+ line + " column: " + column
									+ " in your program.");
				}
				return new Type<Boolean>(
						(worm.getTeam() != null)
								&& (((Worm) ((EntityType) expression1
										.getValue().getValue()).getValue())
										.getTeam() == worm.getTeam()));
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
			throw new IllegalArgumentException(
					"Expected an expression with an entity value! | line: "
							+ line + " column: " + column + " in your program.");
		}
		return new Expression<Boolean>(line, column, e) {

			@Override
			public Type<Boolean> getValue() {
				if (((EntityType) expression1.getValue().getValue()).getValue() == null) {
					return new Type<Boolean>(false);
				}
				return new Type<Boolean>(((EntityType) expression1.getValue()
						.getValue()).getValue().getClass() == Worm.class);
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
			throw new IllegalArgumentException(
					"Expected an expression with an entity value! | line: "
							+ line + " column: " + column + " in your program.");
		}
		return new Expression<Boolean>(line, column, e) {

			@Override
			public Type<Boolean> getValue() {
				if (((EntityType) expression1.getValue().getValue()).getValue() == null) {
					return new Type<Boolean>(false);
				}
				return new Type<Boolean>(((EntityType) expression1.getValue()
						.getValue()).getValue().getClass() == Food.class);
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
			throw new IllegalArgumentException(
					"Expected an expression with a double value! | line: "
							+ line + " column: " + column + " in your program.");
		}
		return new Expression<EntityType>(line, column, e) {

			@Override
			public Type<EntityType> getValue() {
				Entity ent = worm.getWorld().searchObjects(worm,
						(Double) expression1.getValue().getValue());
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
			throw new IllegalArgumentException(
					"Expected an expressions with a double or boolean value! | line: "
							+ line + " column: " + column + " in your program.");
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
			throw new IllegalArgumentException(
					"Expected an expressions with a double or boolean value! | line: "
							+ line + " column: " + column + " in your program.");
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
			throw new IllegalArgumentException(
					"Expected an expressions with a double or boolean value! | line: "
							+ line + " column: " + column + " in your program.");
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
			throw new IllegalArgumentException(
					"Expected an expressions with a double or boolean value! | line: "
							+ line + " column: " + column + " in your program.");
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
		if ((e1.getType() != "null") && (e2.getType() != "null")
				&& (e1.getType() != e2.getType())) {
			throw new IllegalArgumentException(
					"Expected two expressions with the same value type! | line: "
							+ line + " column: " + column + " in your program.");
		}
		return new Expression<Boolean>(line, column, e1, e2) {

			@Override
			public Type<Boolean> getValue() {
				if ((expression1.getType() == "null")
						|| (expression2.getType() == "null")) {
					return new Type<Boolean>(
							(expression2.getValue().toString() == expression1
									.getValue().toString()));
				} else {
					if (expression1.getValue().getType() != "entity") {
						return new Type<Boolean>(expression1.getValue()
								.getValue()
								.equals(expression2.getValue().getValue()));
					} else {
						return new Type<Boolean>(((EntityType) expression1
								.getValue().getValue()).getValue()
								.equals(((EntityType) expression2.getValue()
										.getValue()).getValue()));
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
		if ((e1.getType() != "null") && (e2.getType() != "null")
				&& (e1.getType() != e2.getType())) {
			throw new IllegalArgumentException(
					"Expected two expressions with the same value type! | line: "
							+ line + " column: " + column + " in your program.");
		}
		return new Expression<Boolean>(line, column, e1, e2) {

			@Override
			public Type<Boolean> getValue() {
				if ((expression1.getType() == "null")
						|| (expression2.getType() == "null")) {
					return new Type<Boolean>(!(expression2.getValue()
							.toString() == expression1.getValue().toString()));
				} else {
					if (expression1.getValue().getType() != "entity") {
						return new Type<Boolean>(!expression1.getValue()
								.getValue()
								.equals(expression2.getValue().getValue()));
					} else {
						return new Type<Boolean>(!((EntityType) expression1
								.getValue().getValue()).getValue()
								.equals(((EntityType) expression2.getValue()
										.getValue()).getValue()));
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
			throw new IllegalArgumentException(
					"Expected an expressions with a double value! | line: "
							+ line + " column: " + column + " in your program.");
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
			throw new IllegalArgumentException(
					"Expected an expressions with a double value! | line: "
							+ line + " column: " + column + " in your program.");
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
			throw new IllegalArgumentException(
					"Expected an expressions with a double value! | line: "
							+ line + " column: " + column + " in your program.");
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
			throw new IllegalArgumentException(
					"Expected an expressions with a double value! | line: "
							+ line + " column: " + column + " in your program.");
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
			throw new IllegalArgumentException(
					"Expected an expression with a double value! | line: "
							+ line + " column: " + column + " in your program.");
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
			throw new IllegalArgumentException(
					"Expected an expression with a double value! | line: "
							+ line + " column: " + column + " in your program.");
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
			throw new IllegalArgumentException(
					"Expected an expression with a double value! | line: "
							+ line + " column: " + column + " in your program.");
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
				if ((worm.isTerminated()) || (worm.getWorld().isGameFinished())){
					throw new StopProgramException();
				}
				if (count >= 1000) {
					throw new StackOverflowError(
							"You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				if (worm.canTurn((Double) expression.getValue().getValue())) {
					actionHandler.turn(worm, (Double) expression.getValue()
							.getValue());
					executed = exeCheck;
				} else {
					throw new IllegalStateException("Worm cannot turn!");
					
				}
			}

		};
	}

	@Override
	public Statement createMove(int line, int column) {
		return new Statement(line, column) {

			@Override
			public void run(boolean exeCheck) {
				if ((worm.isTerminated()) || (worm.getWorld().isGameFinished())){
					throw new StopProgramException();
				}
				if (count >= 1000) {
					throw new StackOverflowError(
							"You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				if (worm.canMove()) {
					actionHandler.move(worm);
					executed = exeCheck;
				} else {
					throw new IllegalStateException("Worm cannot move!");
				}
			}
		};
	}

	@Override
	public Statement createJump(int line, int column) {
		return new Statement(line, column) {

			public void run(boolean exeCheck) {
				if ((worm.isTerminated()) || (worm.getWorld().isGameFinished())){
					throw new StopProgramException();
				}
				if (count >= 1000) {
					throw new StackOverflowError(
							"You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				if (worm.canJump()) {
					actionHandler.jump(worm);
					executed = exeCheck;
					throw new StopProgramException();
				} else {
					throw new IllegalStateException("Worm cannot jump!");
				}
			}

		};
	}

	@Override
	public Statement createToggleWeap(int line, int column) {
		return new Statement(line, column) {
			public void run(boolean exeCheck) {
				if ((worm.isTerminated()) || (worm.getWorld().isGameFinished())){
					throw new StopProgramException();
				}
				if (count >= 1000) {
					throw new StackOverflowError(
							"You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				actionHandler.toggleWeapon(worm);
				executed = exeCheck;
			}
		};
	}

	@Override
	public Statement createFire(int line, int column, Expression<?> yield) {
		if (yield.getType() != "double") {
			throw new IllegalArgumentException(
					"Expected an expression with a double value! | line: "
							+ line + " column: " + column + " in your program.");
		}
		return new ExpressionAction(line, column, yield) {

			@Override
			public void run(boolean exeCheck) {
				if ((worm.isTerminated()) || (worm.getWorld().isGameFinished())){
					throw new StopProgramException();
				}
				if (count >= 1000) {
					throw new StackOverflowError(
							"You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				if (worm.canShoot(worm.getWeapon())) {
					actionHandler.fire(worm, ((Double) expression.getValue()
							.getValue()).intValue());
					executed = exeCheck;
				} else {
					throw new IllegalStateException("Worm cannot fire!");
				}
			}
		};
	}

	@Override
	public Statement createSkip(int line, int column) {
		return new Statement(line, column) {
			public void run(boolean exeCheck) {
				if ((worm.isTerminated()) || (worm.getWorld().isGameFinished())){
					throw new StopProgramException();
				}
				if (count >= 1000) {
					throw new StackOverflowError(
							"You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				executed = exeCheck;
				throw new IllegalStateException("NewSkip!");
			}
		};
	}

	@Override
	public Statement createAssignment(int line, int column,
			String variableName, Expression<?> rhs) {
		return new Assignment(line, column, rhs, variableName) {
			public void run(boolean exeCheck) {
				if ((worm.isTerminated()) || (worm.getWorld().isGameFinished())){
					throw new StopProgramException();
				}
				if (count >= 1000) {
					throw new StackOverflowError(
							"You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				if (expression.getType() != "null") {
					if ((globals.get(name).getType() == "double")
							&& (expression.getType() != "double")) {
						throw new IllegalArgumentException(
								"Expected an expression with a double value! | line: "
										+ line + " column: " + column
										+ " in your program.");
					}
					if ((globals.get(name).getType() == "boolean")
							&& (expression.getType() != "boolean")) {
						throw new IllegalArgumentException(
								"Expected an expression with a boolean value! | line: "
										+ line + " column: " + column
										+ " in your program.");
					}
					if ((globals.get(name).getType() == "entity")
							&& (expression.getType() != "entity")) {
						throw new IllegalArgumentException(
								"Expected an expression with an entity value! | line: "
										+ line + " column: " + column
										+ " in your program.");
					}
					globals.get(name).setValue(expression);
				} else {
					if (globals.get(name).getType() == "double") {
						globals.put(name, new Type<Double>(new Double(null)));
					}
					if (globals.get(name).getType() == "boolean") {
						globals.put(name, new Type<Boolean>(new Boolean(null)));
					}
					if (globals.get(name).getType() == "entity") {
						globals.put(name, new Type<EntityType>(new EntityType(
								null)));
					}
				}
				executed = exeCheck;
			}
		};
	}

	@Override
	public Statement createIf(int line, int column, Expression<?> condition,
			Statement then, Statement otherwise) {
		if (condition.getType() != "boolean") {
			throw new IllegalArgumentException(
					"Expected an expression with a boolean value! | line: "
							+ line + " column: " + column + " in your program.");
		}
		return new IfStatement(line, column, (Expression<Boolean>) condition,
				then, otherwise) {

			public void run(boolean exeCheck) {
				if ((worm.isTerminated()) || (worm.getWorld().isGameFinished())){
					throw new StopProgramException();
				}
				if (count >= 1000) {
					throw new StackOverflowError(
							"You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				if ((Boolean) condition.getValue().getValue()) {
					ifTrue.execute(exeCheck);
					ifFalse.setExecuted(exeCheck);
				} else {
					ifFalse.execute(exeCheck);
					ifTrue.setExecuted(exeCheck);
				}
				executed = exeCheck;
			}
		};
	}

	@Override
	public Statement createWhile(int line, int column, Expression<?> condition,
			Statement body) {
		if (condition.getType() != "boolean") {
			throw new IllegalArgumentException(
					"Expected an expression with a boolean value! | line: "
							+ line + " column: " + column + " in your program.");
		}
		return new WhileStatement(line, column,
				(Expression<Boolean>) condition, body) {

			private boolean whileExecutionCheck = true;

			public void run(boolean exeCheck) {
				if ((worm.isTerminated()) || (worm.getWorld().isGameFinished())){
					throw new StopProgramException();
				}
				if (count >= 1000) {
					throw new StackOverflowError(
							"You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				while ((Boolean) condition.getValue().getValue()) {
					body.execute(whileExecutionCheck);
					body.executed = whileExecutionCheck;
					whileExecutionCheck = !whileExecutionCheck;
				}
				executed = exeCheck;
			}
		};
	}

	@Override
	public Statement createForeach(int line, int column,
			worms.model.programs.ProgramFactory.ForeachType type,
			String variableName, Statement body) {
		return new ForeachStatement(line, column, type, variableName, body) {

			private boolean foreachExecutionCheck = true;

			private int index = 0;

			private ArrayList<Entity> entList = new ArrayList<Entity>();

			@Override
			public void run(boolean exeCheck) {
				if ((worm.isTerminated()) || (worm.getWorld().isGameFinished())){
					throw new StopProgramException();
				}
				if (count >= 1000) {
					throw new StackOverflowError(
							"You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				boolean any = false;
				Type<?> oldVar = globals.get(variableName);
				if (type == worms.model.programs.ProgramFactory.ForeachType.ANY) {
					any = true;
				}
				if ((any)
						|| (type == worms.model.programs.ProgramFactory.ForeachType.WORM)) {
					entList.addAll(worm.getWorld().getWormList());
				}
				if ((any)
						|| (type == worms.model.programs.ProgramFactory.ForeachType.FOOD)) {
					entList.addAll(worm.getWorld().getFoodList());
				}
				while (index < entList.size()) {
					globals.put(variableName, new Type<EntityType>(
							new EntityType(entList.get(index))));
					body.execute(foreachExecutionCheck);
					foreachExecutionCheck = !foreachExecutionCheck;
					index++;
				}
				globals.put(variableName, oldVar);
				executed = exeCheck;
				index = 0;
			}
		};
	}

	@Override
	public Statement createSequence(int line, int column,
			List<Statement> statements) {
		return new Sequence(line, column, statements) {

			public boolean started = false;

			public void run(boolean exeCheck) {
				if ((worm.isTerminated()) || (worm.getWorld().isGameFinished())){
					throw new StopProgramException();
				}
				if (count >= 1000) {
					throw new StackOverflowError(
							"You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
				started = true;
				count = count + 1;
				for (Statement st : statements) {
					st.execute(exeCheck);
				}
				executed = exeCheck;
			}
		};
	}

	@Override
	public Statement createPrint(int line, int column, Expression<?> e) {
		return new ExpressionAction(line, column, e) {

			@Override
			public void run(boolean exeCheck) {
				if ((worm.isTerminated()) || (worm.getWorld().isGameFinished())){
					throw new StopProgramException();
				}
				if (count >= 1000) {
					throw new StackOverflowError(
							"You executed 1000 statements. We suppose you are in an endless loop!");
				}
				count += 1;
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
		return new Type<EntityType>(new EntityType());
	}

	@Override
	public Expression<Object> createVariableAccess(int line, int column,
			String name, Type<?> type) {
		return new VariableAcces<Object>(line, column, name, type) {
			public Type<Object> getValue() {
				return (Type<Object>) globals.get(varName);
			}
		};
	}

}
