package test.math;

import common.math.Point;

import org.junit.Test;
import org.junit.runner.RunWith;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

@RunWith(JUnitParamsRunner.class)
public class PointTest {

	@Test
	@Parameters(method = "data_sizeConstructorFillsWithZeros")
	public void sizeConstructorFillsWithZeros(double[] expected, Point p){
		assertArrayEquals(expected, p.coordinates, 0);
	}

	private Object[][] data_sizeConstructorFillsWithZeros(){
		return new Object[][]{
			{new double[]{}, new Point(0)},
			{new double[]{0}, new Point(1)},
			{new double[]{0, 0}, new Point(2)},
			{new double[]{0, 0, 0}, new Point(3)},
		};
	}

	@Test
	@Parameters(method = "data_dataConstructorIsCorrect")
	public void dataConstructorIsCorrect(double[] expected, Point p){
		assertArrayEquals(expected, p.coordinates, 0);
	}

	private Object[][] data_dataConstructorIsCorrect(){
		return new Object[][]{
			{new double[]{1}, new Point(1d)},
			{new double[]{2d, -3.5d}, new Point(2d, -3.5d)},
			{new double[]{3d, -7, 12}, new Point(3d, -7, 12)},
		};
	}

	@Test
	public void canModifyPoint(){
		var p1 = new Point(1d);
		p1.coordinates[0] = 12;
		assertEquals(12, p1.coordinates[0], 0);
		var p2 = new Point(23, 34);
		p2.coordinates[0] = 1;
		p2.coordinates[1] = -23.2;
		assertArrayEquals(new double[]{1, -23.2}, p2.coordinates, 0);
	}

	@Test
	public void canModifyZeroPoint(){
		var p1 = new Point(1);
		p1.coordinates[0] = 12;
		assertEquals(12, p1.coordinates[0], 0);
		var p2 = new Point(2);
		p2.coordinates[0] = 1;
		p2.coordinates[1] = -23.2;
		assertArrayEquals(new double[]{1, -23.2}, p2.coordinates, 0);
	}

	@Test
	@Parameters(method = "data_distanceIsCorrect")
	public void distanceIsCorrect(Point p1, Point p2, double expected){
		assertEquals(expected, p1.distanceTo(p2), 0);
	}

	private Object[][] data_distanceIsCorrect(){
		return new Object[][]{
			{new Point(0d, 0d), new Point(2d, 0d), 2d},
			{new Point(0d, 0d), new Point(1d, 1d), Math.sqrt(2)},
			{new Point(1d, 3d), new Point(-3d, 3.4d), 4.019950248448356d},
			{new Point(2d, 5d, 3d), new Point(-3d, -5d, 7d), Math.sqrt(
				Math.pow(2d - -3d, 2) + Math.pow(5d - -5d, 2) + Math.pow(3d - 7d, 2)
			)}
		};
	}

	@Test
	@Parameters(method = "data_toStringIsCorrect")
	public void toStringIsCorrect(String expected, Point p){
		assertEquals(expected, p.toString());
	}

	private Object[][] data_toStringIsCorrect(){
		return new Object[][]{
			{"()", new Point(0)},
			{"(0)", new Point(1)},
			{"(0; 0)", new Point(2)},
			{"(2; -3.3)", new Point(2d, -3.3d)},
			{"()", new Point()},
			{"(12; -33; -4.56; 23)", new Point(12, -33, -4.56, 23)},
		};
	}

	@Test
	@Parameters(method = "data_equalPointsAreEqual")
	public void equalPointsAreEqual(Point p1, Point p2, boolean areEqual){
		assertEquals(areEqual, p1.equals(p2));
	}

	private Object[][] data_equalPointsAreEqual(){
		var result = new Object[7][3];
		Point p1, p2;
		result[0] = new Object[]{new Point(), new Point(), true};
		result[1] = new Object[]{new Point(2), new Point(2), true};
		result[2] = new Object[]{new Point(2), new Point(3), false};
		result[3] = new Object[]{new Point(23, 43, 55), new Point(23d, 43.0, 55), true};
		p1 = new Point(2);
		p2 = new Point(23, 43);
		p1.coordinates[0] = 23;
		p1.coordinates[1] = 43;
		result[4] = new Object[]{p1, p2, true};
		result[5] = new Object[]{p1, p1, true};
		result[6] = new Object[]{new Point(1), null, false};
		return result;
	}
}
