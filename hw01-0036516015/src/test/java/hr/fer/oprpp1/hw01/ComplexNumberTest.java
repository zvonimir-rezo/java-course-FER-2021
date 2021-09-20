package hr.fer.oprpp1.hw01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ComplexNumberTest {

	@Test
	void testConstructor() {
		ComplexNumber cn = new ComplexNumber(5, -2);
		assertEquals("5.0-2.0i", cn.toString());
	}
	
	@Test
	void testFactoryMethods() {
		ComplexNumber cn1 = ComplexNumber.fromReal(10);
		ComplexNumber cn2 = ComplexNumber.fromImaginary(-2.7);
		ComplexNumber cn3 = ComplexNumber.fromMagnitudeAndAngle(6, 1.27);
		double roundedReal = (double) Math.round(cn3.getReal() * 100) / 100;
		double roundedImaginary = (double) Math.round(cn3.getImaginary() * 100) / 100;
		ComplexNumber cn4 = new ComplexNumber(roundedReal, roundedImaginary);
		
		assertEquals(10, cn1.getReal());
		assertEquals(-2.7, cn2.getImaginary());
		assertEquals("1.78+5.73i", cn4.toString());
		
	}
	
	@Test
	void testParse() {
		assertEquals("351.0", ComplexNumber.parse("351").toString());
		assertEquals("-317.0", ComplexNumber.parse("-317").toString());
		assertEquals("-3.17", ComplexNumber.parse("-3.17").toString());
		assertEquals("351.0i", ComplexNumber.parse("351i").toString());
		assertEquals("-317.0i", ComplexNumber.parse("-317i").toString());
		assertEquals("3.51i", ComplexNumber.parse("3.51i").toString());
		assertEquals("3.17i", ComplexNumber.parse("3.17i").toString());
		assertEquals("2.71", ComplexNumber.parse("+2.71").toString());
		assertEquals("2.71+3.15i", ComplexNumber.parse("+2.71+3.15i").toString());
		assertEquals("-1.0i", ComplexNumber.parse("-i").toString());
		assertEquals("-2.71-3.15i", ComplexNumber.parse("-2.71-3.15i").toString());
		assertEquals("31.0+24.0i", ComplexNumber.parse("31+24i").toString());
		assertEquals("31.0-24.0i", ComplexNumber.parse("31-24i").toString());
		assertEquals("-1.0-1.0i", ComplexNumber.parse("-1-i").toString());
		
	}
	
	@Test
	void testAdd() {
		ComplexNumber cn1 = new ComplexNumber(5, 10);
		ComplexNumber cn2 = new ComplexNumber(7, -2);
		assertEquals("12.0+8.0i", cn1.add(cn2).toString());
	}
	
	@Test
	void testSub() {
		ComplexNumber cn1 = new ComplexNumber(5, 10);
		ComplexNumber cn2 = new ComplexNumber(7, -2);
		assertEquals("-2.0+12.0i", cn1.sub(cn2).toString());		
	}
	
	@Test
	void testMul() {
		ComplexNumber cn1 = new ComplexNumber(5, 10);
		ComplexNumber cn2 = new ComplexNumber(7, -2);
		assertEquals("55.0+60.0i", cn1.mul(cn2).toString());		
	}
	
	@Test
	void testDiv() {
		ComplexNumber cn1 = new ComplexNumber(5, 10);
		ComplexNumber cn2 = new ComplexNumber(7, -2);
		ComplexNumber cn3 = cn1.div(cn2);
		double roundedReal = (double) Math.round(cn3.getReal() * 100) / 100;
		double roundedImaginary = (double) Math.round(cn3.getImaginary() * 100) / 100;
		ComplexNumber cn4 = new ComplexNumber(roundedReal, roundedImaginary);
		assertEquals("0.28+1.51i", cn4.toString());		
	}
	
	@Test
	void testPower() {
		ComplexNumber cn1 = new ComplexNumber(5, 10);
		ComplexNumber cn2 = new ComplexNumber(7, -2);
		cn1 = cn1.power(3);
		cn2 = cn2.power(3);
		double roundedReal1 = (double) Math.round(cn1.getReal() * 100) / 100;
		double roundedImaginary1 = (double) Math.round(cn1.getImaginary() * 100) / 100;		
		double roundedReal2 = (double) Math.round(cn2.getReal() * 100) / 100;
		double roundedImaginary2 = (double) Math.round(cn2.getImaginary() * 100) / 100;
		ComplexNumber cn3 = new ComplexNumber(roundedReal1, roundedImaginary1);
		ComplexNumber cn4 = new ComplexNumber(roundedReal2, roundedImaginary2);
		
		assertEquals("-1375.0-250.0i", cn3.toString());
		assertEquals("259.0-286.0i", cn4.toString());
	}
	
	@Test
	void testRoot() {
		ComplexNumber cn = new ComplexNumber(0, 2);
		
		ComplexNumber cn2 = cn.root(2)[1];
		double roundedReal = (double) Math.round(cn2.getReal() * 100) / 100;
		double roundedImaginary = (double) Math.round(cn2.getImaginary() * 100) / 100;
		ComplexNumber cn3 = new ComplexNumber(roundedReal, roundedImaginary);
		
		assertEquals("-1.0-1.0i", cn3.toString());
		
	}
	
}
