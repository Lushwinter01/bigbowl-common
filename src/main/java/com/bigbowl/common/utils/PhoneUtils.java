package com.bigbowl.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

/**
 *
 * @title PhoneUtils.java
 *
 * @description 移动手机相关的工具类
 *
 * @author Leon
 *
 * @time 2018年8月28日 下午6:06:42
 */
public class PhoneUtils {

    /**
     * @Title: isPhoneNum
     * @Description: 检查是否是有效的电话号码 中国电信号段 133、149、153、173、177、180、181、189、199 中国联通号段
     *               130、131、132、145、155、156、166、175、176、185、186 中国移动号段
     *               134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188、198
     *               其他号段 14号段以前为上网卡专属号段，如中国联通的是145，中国移动的是147等等。 虚拟运营商
     *               电信：1700、1701、1702 移动：1703、1705、1706 联通：1704、1707、1708、1709、171
     *
     * @param phone
     * @return boolean
     * @author Leon
     * @date 2018年8月28日下午6:08:27
     */
    public static boolean isPhoneNum(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (StringUtils.isBlank(phone)) {
            return false;
        }
        if (phone.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(phone);
            boolean isMatch = m.matches();
            return isMatch;
        }
    }

    public static String replacePhoneNumWithCharacters(String mobile) {
        return mobile.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

}
