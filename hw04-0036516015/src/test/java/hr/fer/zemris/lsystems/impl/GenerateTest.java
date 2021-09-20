package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;

class GenerateTest {

	@Test
	void testGenerate() {
		LSystemBuilderImpl impl = new LSystemBuilderImpl();
		impl.setAxiom("F");
		impl.registerProduction('F', "F+F--F+F");
		LSystem system = impl.build();
		assertEquals("F", system.generate(0));
		assertEquals("F+F--F+F", system.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", system.generate(2));
	}

}
