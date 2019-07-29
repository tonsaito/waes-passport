package com.waes.passport.util;

import java.util.Base64;

import org.apache.commons.lang3.StringUtils;

/**
 * Encoding Utility class
 * 
 * @author tonsaito
 *
 */
public class EncodingUtil {
	private EncodingUtil() {}

	/**
	 * Transforms String objects in byte[] using base64 encoding
	 * 
	 * @param data the data that will be converted
	 * @return returns the follow byte[] of the given String object
	 * @throws IllegalArgumentException in case of invalid base64 entry
	 */
	public static byte[] decodeBase64ToByteArray(String data) throws IllegalArgumentException {
		if (StringUtils.isBlank(data))
			return null;
		try {
			return Base64.getDecoder().decode(data);
		} catch (Exception e) {
			throw new IllegalArgumentException("Invalid base64 entry", e);
		}
	}
}
