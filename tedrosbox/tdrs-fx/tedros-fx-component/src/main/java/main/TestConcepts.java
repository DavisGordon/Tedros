package main;

import java.math.BigDecimal;
import java.math.BigInteger;

public class TestConcepts {

	public static void main(String[] args) {
		
		TestConcepts tc = new TestConcepts();
		
		BigDecimal bd = new BigDecimal("0.54");
		System.out.println("BigDecimal: " + bd.toString());
		tc.print(bd);
		
		BigInteger bi = new BigInteger("-45");
		System.out.println("BigInteger: " + bi.toString());
		tc.print(bi);
		
		Double d = 0.745D;
		System.out.println("Double: " + d.toString());
		tc.print(d);
		
		Long l = 222L;
		System.out.println("Long: " + l.toString());
		tc.print(l);
		
		Integer i = 1;
		System.out.println("Integer: " + i.toString());
		tc.print(i);
		
		Float f = 0.1f;
		System.out.println("Float: " + f.toString());
		tc.print(f);
	}
	
	
	public void print(Number n){
		System.out.println("doubleValue() = " + n.doubleValue());
		System.out.println("longValue() = " + n.longValue());
		System.out.println("intValue() = " + n.intValue());
		System.out.println("----------------");
	}

}
