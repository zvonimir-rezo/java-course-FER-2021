package hr.fer.oprpp1.custom.collections;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DictionaryTest {

	@Test
	void testNullKey() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		assertThrows(NullPointerException.class, () -> dict.put(null, 2));
	}
	
	@Test
	void testPutGet() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		dict.put("Ivana", 3);
		dict.put("Ante", 4);
		dict.put("Marko", 5);
		dict.put("Karlo", 2);
		dict.put("Ante", 2);
		assertEquals(2, dict.get("Ante"));
		assertEquals(5, dict.get("Marko"));
		assertEquals(4, dict.size());
	}
	
	@Test
	void testRemove() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		dict.put("Ivana", 3);
		dict.put("Ante", 4);
		dict.put("Marko", 5);
		dict.put("Karlo", 2);
		dict.put("Ante", 2);
		dict.remove("Marko");
		assertEquals(3, dict.size());
		assertNull(dict.get("Marko"));
	}
	
	@Test
	void testClearisEmpty() {
		Dictionary<String, Integer> dict = new Dictionary<>();
		dict.put("Ivana", 3);
		dict.put("Ante", 4);
		dict.put("Marko", 5);
		dict.put("Karlo", 2);
		dict.put("Ante", 2);
		dict.clear();
		assertEquals(0, dict.size());
		assertNull(dict.get("Karlo"));
		assertTrue(dict.isEmpty());
	}
	

}
