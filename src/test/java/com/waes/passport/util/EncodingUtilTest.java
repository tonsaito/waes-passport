package com.waes.passport.util;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertNull;

import org.junit.Test;

/**
 * EncodingUtilTest tests EncodingUtil methods
 * @author tonsaito
 *
 */
public class EncodingUtilTest {
	
	@Test
	public void shouldReturnNullBase64ToByteArray() throws IllegalArgumentException {
		assertNull(EncodingUtil.decodeBase64ToByteArray(" "));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldThrowUnsupportedEncodingExceptionWhenTryToDecodeBase64ToByteArray() throws IllegalArgumentException{
		EncodingUtil.decodeBase64ToByteArray("4rdHFh%2BHYoS8oLdVvbUzEVqB8Lvm7kSPnuwF0AAABYQ%3D");
	}
	
	@Test
	public void shouldDecodeBase64ToByteArrayCorrectly() throws IllegalArgumentException {
		assertTrue("test".equals(new String(EncodingUtil.decodeBase64ToByteArray("dGVzdA=="))));
	}
}
