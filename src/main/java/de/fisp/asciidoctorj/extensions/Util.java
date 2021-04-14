package de.fisp.asciidoctorj.extensions;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

public class Util {

	private Util() {
		//
	}

	public static String getStringAttributeValueOrDefault(final Map<String, Object> attributes, final String key) {
		final String result = getStringAttributeValue(attributes, key);
		return (result != null) ? result : Constants.DEFAULT_VALUES.get(key);
	}

	public static String getStringAttributeValueOrDefault(final Map<String, Object> attributes, final String key, final String defaultValue) {
		final String result = getStringAttributeValue(attributes, key);
		return (result != null) ? result : defaultValue;
	}

	public static String getStringAttributeValue(final Map<String, Object> attributes, final String key) {
		if (attributes == null || StringUtils.isBlank(key)) {
			return null;
		}

		final Object value = attributes.get(StringUtils.trim(key));
		if (value != null) {
			if (value instanceof String) {
				return (String) value;
			} else {
				return value.toString();
			}
		}

		return null;
	}

	public static boolean getBoolAttributeValueOrDefault(final Map<String, Object> attributes, final String key) {
		final Boolean result = getBoolAttributeValue(attributes, key);
		return (result != null) ? result : Boolean.parseBoolean(Constants.DEFAULT_VALUES.get(key));
	}

	public static boolean getBoolAttributeValueOrDefault(final Map<String, Object> attributes, final String key, final boolean defaultValue) {
		final Boolean result = getBoolAttributeValue(attributes, key);
		return (result != null) ? result : defaultValue;
	}

	public static Boolean getBoolAttributeValue(final Map<String, Object> attributes, final String key) {
		if (attributes == null || StringUtils.isBlank(key)) {
			return null;
		}

		final Object value = attributes.get(StringUtils.trim(key));
		if (value != null) {
			if (value instanceof String) {
				return Boolean.parseBoolean((String) value);
			} else if (value instanceof Boolean) {
				return (Boolean) value;
			} else {
				return Boolean.parseBoolean(value.toString());
			}
		}

		return null;
	}

}
