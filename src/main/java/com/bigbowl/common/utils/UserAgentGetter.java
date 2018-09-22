package com.bigbowl.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import eu.bitwalker.useragentutils.UserAgent;

/**
 *
 * @title UserAgentGetter.java
 *
 * @description 获取客户端设备信息
 *
 * @author Leon
 *
 * @time 2018年8月28日 下午4:42:41
 */

public class UserAgentGetter {
    private UserAgent userAgent;
    private String userAgentString;
    private HttpServletRequest request;

    public UserAgentGetter(HttpServletRequest request) {
        this.request = request;
        userAgentString = request.getHeader("User-Agent");
        userAgent = UserAgent.parseUserAgentString(userAgentString);
    }

    /**
     * 获取浏览器类型
     */
    public String getBrowser() {
        if (null == userAgent) {
            return "";
        }
        return userAgent.getBrowser().getName();
    }

    /**
     * 获取操作系统
     */
    public String getOS() {
        if (null == userAgent) {
            return "未知设备";
        }
        return userAgent.getOperatingSystem().getName();
    }

    /**
     * 获取设备型号
     */
    public String getDevice() {
        if (null == userAgentString) {
            return "未知设备";
        }
        if (userAgentString.contains("Android")) {
            String[] str = userAgentString.split("[()]+");
            str = str[1].split("[;]");
            String[] res = str[str.length - 1].split("Build/");
            return res[0].trim();
        } else if (userAgentString.contains("iPhone")) {
            String[] str = userAgentString.split("[()]+");
            String res = "iphone" + str[1].split("OS")[1].split("like")[0];
            return res.trim();
        } else if (userAgentString.contains("iPad")) {
            return "iPad";
        } else {
            return getOS().trim();
        }
    }

    /**
     * 获取ip地址
     */
    public String getIpAddr() {
        String ip = null;
        try {
            ip = request.getHeader("x-forwarded-for");
            if (isBlankIp(ip))
                ip = request.getHeader("Proxy-Client-IP");
            if (isBlankIp(ip))
                ip = request.getHeader("WL-Proxy-Client-IP");
            if (isBlankIp(ip))
                ip = request.getHeader("HTTP_CLIENT_IP");
            if (isBlankIp(ip))
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            if (isBlankIp(ip))
                ip = request.getRemoteAddr();
            // 使用代理，则获取第一个IP地址
            if (!isBlankIp(ip) && ip.length() > 15)
                ip = ip.split(",")[0];
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ip;
    }

    private boolean isBlankIp(String str) {
        return str == null || str.trim().isEmpty() || "unknown".equalsIgnoreCase(str);
    }

    public boolean isInner(String ip) {
        String reg = "((192\\.168|172\\.([1][6-9]|[2]\\d|3[01]))" + "(\\.([2][0-4]\\d|[2][5][0-5]|[01]?\\d?\\d)){2}|"
                + "^(\\D)*10(\\.([2][0-4]\\d|[2][5][0-5]|[01]?\\d?\\d)){3})";
        Pattern p = Pattern.compile(reg);
        Matcher matcher = p.matcher(ip);
        return matcher.find();
    }

    public static String getV4IP() {
        String ip = "";
        String chinaz = "http://www.ip138.com/";

        StringBuilder inputLine = new StringBuilder();
        String read = "";
        URL url = null;
        HttpURLConnection urlConnection = null;
        BufferedReader in = null;
        try {
            url = new URL(chinaz);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedReader( new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
            while((read=in.readLine())!=null){
                inputLine.append(read+"\r\n");
            }
            //System.out.println(inputLine.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }


        Pattern p = Pattern.compile("\\<dd class\\=\"fz24\">(.*?)\\<\\/dd>");
        Matcher m = p.matcher(inputLine.toString());
        if(m.find()){
            String ipstr = m.group(1);
            ip = ipstr;
            //System.out.println(ipstr);
        }
        return ip;
    }




    /**
     * test
     *
     * @param args
     */
    public static void main(String[] args) {
        try {
            System.out.println("ww");
            System.out.println(UserAgentGetter.getV4IP());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
