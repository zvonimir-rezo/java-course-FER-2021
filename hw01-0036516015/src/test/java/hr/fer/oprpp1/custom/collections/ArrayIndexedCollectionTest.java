package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;


class ArrayIndexedCollectionTest {
	
	@Test
	void testConstructors() {
		assertThrows(IllegalArgumentException.class, () -> {
			ArrayIndexedCollection col1 = new ArrayIndexedCollection(0);
		});
		
		ArrayIndexedCollection col2 = new ArrayIndexedCollection(5);
		for (int i = 1; i < 6; i++) {
			col2.add(i);
		}
		
		assertThrows(NullPointerException.class, () -> {
			ArrayIndexedCollection col3 = new ArrayIndexedCollection(null);
		});
		
		ArrayIndexedCollection col4 = new ArrayIndexedCollection(col2);
		assertEquals(5, col4.size());
		
		ArrayIndexedCollection col5 = new ArrayIndexedCollection(10, col2);
		assertEquals(10, col5.getElements().length);
	}

	@Test
	void testAdd() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		int number = 5;
		collection.add(5);
		collection.add("test str");
		assertThrows(NullPointerException.class, () -> collection.add(null));
		assertEquals(number, collection.get(0));
		assertEquals("test str", collection.get(1));
	}
	
	@Test
	void testGet() {
		ArrayIndexedCollection collection = new ArrayIndexedCollection();
		collection.add(5);
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(-1));
		assertThrows(IndexOutOfBoundsException.class, () -> collection.get(2));
	}
	
	@Test
	void testContains() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(5);
		col.add(7);
		col.add(2);
		col.add("value");
		assertTrue(col.contains("value"));
		assertFalse(col.contains(5));	
	}
	
	@Test
	void testClear() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(5);
		col.add(13);
		//System.out.println(col.getElements()[0]);
		col.clear();
		assertEquals(0, col.size());
		//System.out.println(col.getElements()[0]);
		assertNull(col.getElements()[0]);
		
	}
	
	@Test
	void testInsert() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(5);
		for (int i = 1; i < 6; i++) {
			col.add(i);
		}
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert("value", -1));
		assertThrows(IndexOutOfBoundsException.class, () -> col.insert("value", 6));
		col.add(6);
		assertEquals(6, col.size());
		assertEquals(10, col.getElements().length);
	}
	
	@Test
	void testRemove() {
		ArrayIndexedCollection col = new ArrayIndexedCollection(5);
		for (int i = 1; i < 6; i++) {
			col.add(i);
		}
		col.remove(2);
		assertEquals(4, col.size());
		assertFalse(col.contains(3));
	}
	
	
	
	
	

}
