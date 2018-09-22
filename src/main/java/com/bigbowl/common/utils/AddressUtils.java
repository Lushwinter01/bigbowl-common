package com.bigbowl.common.utils;

import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * conan
 * 2018/8/27 下午3:58
 **/
public class AddressUtils {

    public static String getCity(String ip)
    {
        String result = getAddresses("ip=" + ip, "utf-8");
        JSONObject jsonObject = JSONObject.parseObject(result);
        JSONObject dataJson = jsonObject.getJSONObject("data");
        return dataJson.getString("city");
    }

    public static String getAddresses(String content, String encodingString)
    {
        String urlStr = "http://ip.taobao.com/service/getIpInfo.php";

        String returnStr = getResult(urlStr, content, encodingString);
        if (returnStr != null)
        {
            returnStr = decodeUnicode(returnStr);
            System.out.println(returnStr);
            String[] temp = returnStr.split(",");
            if (temp.length < 3) {
                return "0";
            }
            return returnStr;
        }
        return null;
    }

    private static String getResult(String urlStr, String content, String encoding)
    {
        URL url;
        HttpURLConnection connection = null;
        try
        {
            url = new URL(urlStr);
            connection = (HttpURLConnection)url.openConnection();
            connection.setConnectTimeout(2000);
            connection.setReadTimeout(2000);
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.connect();

            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            out.writeBytes(content);
            out.flush();
            out.close();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));

            StringBuilder buffer = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            return buffer.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    public static String decodeUnicode(String theString)
    {
        int len = theString.length();
        StringBuilder outBuffer = new StringBuilder(len);
        for (int x = 0; x < len;)
        {
            char aChar = theString.charAt(x++);
            if (aChar == '\\')
            {
                aChar = theString.charAt(x++);
                if (aChar == 'u')
                {
                    int value = 0;
                    for (int i = 0; i < 4; i++)
                    {
                        aChar = theString.charAt(x++);
                        switch (aChar)
                        {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + aChar - 48;
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + aChar - 97;
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + aChar - 65;
                                break;
                            case ':':
                            case ';':
                            case '<':
                            case '=':
                            case '>':
                            case '?':
                            case '@':
                            case 'G':
                            case 'H':
                            case 'I':
                            case 'J':
                            case 'K':
                            case 'L':
                            case 'M':
                            case 'N':
                            case 'O':
                            case 'P':
                            case 'Q':
                            case 'R':
                            case 'S':
                            case 'T':
                            case 'U':
                            case 'V':
                            case 'W':
                            case 'X':
                            case 'Y':
                            case 'Z':
                            case '[':
                            case '\\':
                            case ']':
                            case '^':
                            case '_':
                            case '`':
                            default:
                                throw new IllegalArgumentException("Malformed      encoding.");
                        }
                    }
                    outBuffer.append((char)value);
                }
                else
                {
                    if (aChar == 't') {
                        aChar = '\t';
                    } else if (aChar == 'r') {
                        aChar = '\r';
                    } else if (aChar == 'n') {
                        aChar = '\n';
                    } else if (aChar == 'f') {
                        aChar = '\f';
                    }
                    outBuffer.append(aChar);
                }
            }
            else
            {
                outBuffer.append(aChar);
            }
        }
        return outBuffer.toString();
    }

    public static void main(String[] args)
    {
        String ip = "221.221.151.143";
        String address = "";
        try
        {
            address = getCity(ip);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        System.out.println(address);
    }
}
