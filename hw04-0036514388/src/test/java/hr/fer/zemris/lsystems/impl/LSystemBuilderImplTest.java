package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;

public class LSystemBuilderImplTest {
	
	@Test
	public void generateTest() {
		LSystemBuilderImpl builderImpl = new LSystemBuilderImpl();
		LSystem l = builderImpl.setAxiom("F").registerProduction('F', "F+F--F+F").build();
		
		assertEquals("F", l.generate(0));
		assertEquals("F+F--F+F", l.generate(1));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", l.generate(2));
	}
}
