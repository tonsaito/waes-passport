package com.waes.passport.service.impl;

import org.junit.Test;

public class DiffServiceImplTest {

	@Test
	public void test() {
		byte[] a = new String("abc").getBytes();
		byte[] b = new String("abT").getBytes();
		System.out.println("a.length :" + a.length);
		System.out.println("a.length :" + b.length);
		for (int i = 0; i < a.length; i++) {
			System.out.println(a[i]+" <> "+b[i]);
		}
		int a1 = 10;
		int a2 = 20;
		System.out.println(Math.abs(a1 - a2));
		a1 = 20;
		a2 = 10;
		System.out.println(Math.abs(a1 - a2));
	}
}
