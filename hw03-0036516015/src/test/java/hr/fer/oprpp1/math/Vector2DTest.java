package hr.fer.oprpp1.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class Vector2DTest {
	
	@Test
	void testGettersandConstructor() {
		Vector2D vector = new Vector2D(3, 4);
		assertEquals(3, vector.getX());
		assertEquals(4, vector.getY());
		Vector2D vector2 = new Vector2D(-3, 4.75);
		assertEquals(-3, vector2.getX());
		assertEquals(4.75, vector2.getY());
	}
	
	@Test
	void testAdd() {
		Vector2D vector = new Vector2D(3, 4);
		vector.add(new Vector2D(-3, -4));
		assertEquals(0, vector.getX());
		assertEquals(0, vector.getY());
		
		Vector2D vector2 = new Vector2D(3, 4);
		vector2.add(new Vector2D(-2.5, -4.5));
		assertEquals(0.5, vector2.getX());
		assertEquals(-0.5, vector2.getY());
		
		Vector2D vector3 = new Vector2D(3, 4);
		assertEquals(0.5, vector3.added(new Vector2D(10, -3.5)).getY());
	}
	
	@Test
	void testRotate() {
		Vector2D vector = new Vector2D(3, 4);
		vector.rotate(Math.PI/2);
		assertEquals(-4, vector.getX());
		assertEquals(3, vector.getY());
	}
	
	@Test
	void testScale() {
		Vector2D vector = new Vector2D(3, 4);
		vector.scale(3);
		assertEquals(9, vector.getX());
		assertEquals(12, vector.getY());
	}
	
	@Test
	void testCopy() {
		Vector2D vector = new Vector2D(3, 4);
		assertEquals(vector.getX(), vector.copy().getX());
		assertEquals(vector.getY(), vector.copy().getY());
	}

}
