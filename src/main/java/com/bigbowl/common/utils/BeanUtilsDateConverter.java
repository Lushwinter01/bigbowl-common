package com.bigbowl.common.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BeanUtilsDateConverter {
	private static Logger log = LoggerFactory.getLogger(BeanUtilsDateConverter.class);
	private static Map<String, DateFormat> patternMap = new HashMap<String, DateFormat>();

	static {
		patternMap.put(
				"^\\w{3} \\w{3} \\d{2} \\d{2}:\\d{2}:\\d{2} \\w{3} \\d{4}$",
				new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US));
		patternMap.put(
				"^\\w{3} \\w{3} \\d{2} \\d{2}:\\d{2}:\\d{2} \\w{3}\\+\\d{2}:\\d{2} \\d{4}$",
				new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US));
		patternMap.put("^\\d{4}-\\d{2}-\\d{2}$", new SimpleDateFormat(
				"yyyy-MM-dd"));
		patternMap.put("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$",
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		patternMap.put("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$",
				new SimpleDateFormat("yyyy-MM-dd HH:mm"));
		patternMap.put("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}\\.\\d{1}$",
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
		patternMap.put("^[A-Z]{1}[a-z]{2} \\d{1,2}, \\d{4} \\d{2}:\\d{2}:\\d{2} [A-Z]{2}$", 
				new SimpleDateFormat("MMM d, yyyy hh:mm:ss a", Locale.US));
	}

	/**
	 * 转换StringToDate
	 */
	public static Date convertStrToDate(Object value) {
		if (value instanceof String) {
			String p = (String) value;
			if (p == null || p.trim().length() == 0) {
				return null;
			}
			try {
				// Thu Mar 26 11:15:43 CST 2009
				// Mon May 26 00:00:00 GMT+08:00 2008
				// 2008-01-03
				// 13-7-1
				// 2008-01-03 09:09:09
				for (String regex : patternMap.keySet()) {
					if (p.matches(regex)) {
						return patternMap.get(regex).parse(p.trim());
					}
				}
				return null;
			} catch (Exception e) {
				log.error("日期格式有误 {}", e);
				return null;
			}
		}
		return null;
	}
}
