package com.waes.passport.util;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * EncodingUtilTest tests EncodingUtil methods
 * @author tonsaito
 *
 */
public class EncodingUtilTest {


	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowUnsupportedEncodingExceptionWhenTryToDecodeBase64ToByteArray() throws IllegalArgumentException{
		EncodingUtil.decodeBase64ToByteArray("4rdHFh%2BHYoS8oLdVvbUzEVqB8Lvm7kSPnuwF0AAABYQ%3D");
	}
	
	@Test
	public void shouldDecodeBase64ToByteArrayCorrectly() throws IllegalArgumentException {
		assertTrue("test".equals(new String(EncodingUtil.decodeBase64ToByteArray("dGVzdA=="))));
	}
}
