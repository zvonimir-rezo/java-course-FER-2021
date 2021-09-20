package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class LinkedListIndexedCollectionTest {

	@Test
	void testConstructors() {
		LinkedListIndexedCollection col1 = new LinkedListIndexedCollection();
		assertEquals(0, col1.size());
		assertThrows(IndexOutOfBoundsException.class, () -> col1.get(0));
		for (int i = 1; i < 6; i++) {
			col1.add(i);
		}
		
		LinkedListIndexedCollection col2 = new LinkedListIndexedCollection();
		col2.addAll(col1);
		//System.out.println(col1.size());
		assertEquals(col1.size(), col2.size());
		
	}
	
	@Test
	void testToArray() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		Object[] array = {1,2,3,4,5};
		for (int i = 1; i < 6; i++) {
			col.add(i);
		}
		assertArrayEquals(array, col.toArray());
	}
	
	@Test
	void testAdd() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		int number = 5;
		col.add(5);
		col.add("test str");
		assertThrows(NullPointerException.class, () -> col.add(null));
		assertEquals(number, col.get(0));
		assertEquals("test str", col.get(1));
	}
	
	@Test
	void testGet() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(5);
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> col.get(2));
	}
	
	@Test
	void testContains() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(5);
		col.add(2);
		col.add("value");
		assertTrue(col.contains("value"));
		assertFalse(col.contains("value2"));	
	}
	
	@Test
	void testClear() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		col.add(13);
		col.add(14);
		col.clear();
		assertEquals(0, col.size());
		
	}
	
	@Test
	void testInsert() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		for (int i = 1; i < 6; i++) {
			col.add(i);
		}
		System.out.println("Size: " + col.size());
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert("value", -1));
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert("value", 6));
		col.insert("value", 2);
		col.insert(7, 4);
		/*
		for(Object obj: col.toArray()) {
			System.out.println(obj);
		} */
		assertEquals("value", col.get(2));
		assertEquals(7, col.get(4));
		assertEquals(7, col.size());
	}
	
	@Test
	void testRemove() {
		LinkedListIndexedCollection col = new LinkedListIndexedCollection();
		for (int i = 1; i < 6; i++) {
			col.add(i);
		}
		System.out.println(col.size());
		col.remove(4);
		assertEquals(4, col.size());
		assertFalse(col.contains(7));
	}

}
