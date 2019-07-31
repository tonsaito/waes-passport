package com.waes.passport.util;

import java.util.Base64;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.waes.passport.config.Constants;

/**
 * Encoding Utility class
 * 
 * @author tonsaito
 *
 */
public class EncodingUtil {
	private static final Logger LOGGER = Logger.getLogger(EncodingUtil.class);
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
			LOGGER.error(Constants.INVALID_BASE64_VALUE, e);
			throw new IllegalArgumentException(Constants.INVALID_BASE64_VALUE, e);
		}
	}
}
