package com.bigbowl.common.utils;

import java.util.Random;

/**
 * 随机数工具类
 * 
 * @author fanmingrui
 *
 */
public class RandomUtils {

	public static final String ALLCHAR = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String LETTERCHAR = "abcdefghijkllmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public static final String NUMBERCHAR = "0123456789";

	/**
	 * 生成固定长度的纯数字随机数
	 * 
	 * @param len：随机数长度
	 * @return
	 */
	public static String generateNumericalRandom(int len) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < len; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(NUMBERCHAR.length())));
		}
		return sb.toString();

	}

	/**
	 * 返回一个定长的随机字符串(只包含大小写字母、数字)
	 * 
	 * @param length：随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(ALLCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯字母字符串(只包含大小写字母)
	 * 
	 * @param length：随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateMixString(int length) {
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(ALLCHAR.charAt(random.nextInt(LETTERCHAR.length())));
		}
		return sb.toString();
	}

	/**
	 * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
	 * 
	 * @param length：随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateLowerString(int length) {
		return generateMixString(length).toLowerCase();
	}

	/**
	 * 返回一个定长的随机纯小写字母字符串(只包含大小写字母)
	 * 
	 * @param length：随机字符串长度
	 * @return 随机字符串
	 */
	public static String generateUpperString(int length) {
		return generateMixString(length).toUpperCase();
	}

	public static void main(String[] args) {
		String numerical = generateNumericalRandom(10);
		System.out.println(numerical + "------------" + numerical.length());
		String allStr = generateString(10);
		System.out.println(allStr + "------------" + allStr.length());
		String charStr = generateMixString(10);
		System.out.println(charStr + "------------" + charStr.length());
		String lowerString = generateLowerString(10);
		System.out.println(lowerString + "------------" + lowerString.length());
		String upperString = generateUpperString(10);
		System.out.println(upperString + "------------" + upperString.length());
	}
}
