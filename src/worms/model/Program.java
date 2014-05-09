package worms.model;

import java.util.List;

import worms.model.programs.ProgramFactory;

public class Program implements ProgramFactory<Object, Object, Object>{

	//dfdfdfd
	@Override
	public Object createDoubleLiteral(int line, int column, double d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createBooleanLiteral(int line, int column, boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createAnd(int line, int column, Object e1, Object e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createOr(int line, int column, Object e1, Object e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createNot(int line, int column, Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createNull(int line, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createSelf(int line, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createGetX(int line, int column, Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createGetY(int line, int column, Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createGetRadius(int line, int column, Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createGetDir(int line, int column, Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createGetAP(int line, int column, Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createGetMaxAP(int line, int column, Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createGetHP(int line, int column, Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createGetMaxHP(int line, int column, Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createSameTeam(int line, int column, Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createSearchObj(int line, int column, Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createIsWorm(int line, int column, Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createIsFood(int line, int column, Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createVariableAccess(int line, int column, String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createLessThan(int line, int column, Object e1, Object e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createGreaterThan(int line, int column, Object e1, Object e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createLessThanOrEqualTo(int line, int column, Object e1,
			Object e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createGreaterThanOrEqualTo(int line, int column, Object e1,
			Object e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createEquality(int line, int column, Object e1, Object e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createInequality(int line, int column, Object e1, Object e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createAdd(int line, int column, Object e1, Object e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createSubtraction(int line, int column, Object e1, Object e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createMul(int line, int column, Object e1, Object e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createDivision(int line, int column, Object e1, Object e2) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createSqrt(int line, int column, Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createSin(int line, int column, Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createCos(int line, int column, Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createTurn(int line, int column, Object angle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createMove(int line, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createJump(int line, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createToggleWeap(int line, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createFire(int line, int column, Object yield) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createSkip(int line, int column) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createAssignment(int line, int column, String variableName,
			Object rhs) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createIf(int line, int column, Object condition, Object then,
			Object otherwise) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createWhile(int line, int column, Object condition,
			Object body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createForeach(int line, int column, ForeachType type,
			String variableName, Object body) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createSequence(int line, int column, List statements) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createPrint(int line, int column, Object e) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createDoubleType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createBooleanType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object createEntityType() {
		// TODO Auto-generated method stub
		return null;
	}

}
