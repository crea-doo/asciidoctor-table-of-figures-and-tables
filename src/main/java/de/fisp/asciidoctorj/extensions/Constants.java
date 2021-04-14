package de.fisp.asciidoctorj.extensions;

import java.util.HashMap;
import java.util.Map;

public class Constants {

	public static final String ATTR_TITLE = "title";

	public static final String ATTR_CAPTION_SEPARATOR = "caption-separator";
	public static final String DEFAULT_CAPTION_SEPARATOR = ":";


	public static final String TOF_ANCHOR_PREFIX = "_tof";

	public static final String TOF_ID = "_tofSection";

	public static final String TOF_ATTR_NUMBERED = "tof-numbered";
	public static final String TOF_DEFAULT_VALUE_NUMBERED = "true";

	public static final String TOF_ATTR_TITLE = "tof-title";
	public static final String TOF_DEFAULT_VALUE_TITLE = "Table of Figures";

	public static final String TOF_ATTR_CAPTION = "tof-caption";
	public static final String TOF_DEFAULT_VALUE_CAPTION = "Figure";


	public static final String TOT_ANCHOR_PREFIX = "_tot";

	public static final String TOT_ID = "_totSection";

	public static final String TOT_ATTR_NUMBERED = "tot-numbered";
	public static final String TOT_DEFAULT_VALUE_NUMBERED = "true";

	public static final String TOT_ATTR_TITLE = "tot-title";
	public static final String TOT_DEFAULT_VALUE_TITLE = "Table of Tables";

	public static final String TOT_ATTR_CAPTION = "tot-caption";
	public static final String TOT_DEFAULT_VALUE_CAPTION = "Table";

	public static final Map<String, String> DEFAULT_VALUES = new HashMap<>();

	static {
		DEFAULT_VALUES.put(ATTR_CAPTION_SEPARATOR, DEFAULT_CAPTION_SEPARATOR);
		DEFAULT_VALUES.put(TOF_ATTR_NUMBERED, TOF_DEFAULT_VALUE_NUMBERED);
		DEFAULT_VALUES.put(TOF_ATTR_TITLE, TOF_DEFAULT_VALUE_TITLE);
		DEFAULT_VALUES.put(TOF_ATTR_CAPTION, TOF_DEFAULT_VALUE_CAPTION);
		DEFAULT_VALUES.put(TOT_ATTR_NUMBERED, TOT_DEFAULT_VALUE_NUMBERED);
		DEFAULT_VALUES.put(TOT_ATTR_TITLE, TOT_DEFAULT_VALUE_TITLE);
		DEFAULT_VALUES.put(TOT_ATTR_CAPTION, TOT_DEFAULT_VALUE_CAPTION);
	}

	private Constants() {
		//
	}
	
}
