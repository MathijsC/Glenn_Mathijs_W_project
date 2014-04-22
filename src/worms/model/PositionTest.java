package worms.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PositionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	private Position pos;
	private double xCo;
	private double yCo;

	@Before
	public void setUp() throws Exception {
		pos = new Position(5, 7);
		xCo = 3;
		yCo = 13;

	}


	@Test
	public void constructor_SingleCase() {
		Position testPos = new Position(4, 2);
		assertEquals(testPos.getXCoordinate(), 4, 0.001);
		assertEquals(testPos.getYCoordinate(), 2, 0.001);
	}

	@Test
	public void setX_LegalCase() {
		pos.setXCoordinate(xCo);
		assertEquals(pos.getXCoordinate(), xCo, 0.001);

	}

	@Test (expected = IllegalArgumentException.class)
	public void setX_InvalidArgument() throws Exception{
		pos.setXCoordinate(Double.NaN);

	}

	@Test
	public void setY_LegalCase() {
		pos.setYCoordinate(yCo);
		assertEquals(pos.getYCoordinate(), yCo, 0.001);

	}

	@Test (expected = IllegalArgumentException.class)
	public void setY_InvalidArgument() throws Exception{
		pos.setYCoordinate(Double.NaN);

	}

	@Test
	public void distanceTo_SingleCase() {
		Position testPos = new Position(2,3);
		assertEquals(testPos.distanceTo(pos),5,0.001);
	}

}
