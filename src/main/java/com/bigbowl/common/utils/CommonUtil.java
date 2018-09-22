package com.bigbowl.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 常用工具处理.
 * @author li.zhuang
 *
 */
public class CommonUtil {
    /**
     * 〈删除字第串最前面多余〉
     * @author 庄立
     * @param str 字符串
     * @return 处理后字符串
     */
    public static String removeBegin(String str)
    {
        if ( null == str )
        {
            return "";
        }
        else
        {
            Matcher matcher = Pattern.compile("^,+").matcher(str);
            str = matcher.replaceAll("");
            return str;
        }
    }
    
    /**
     * 〈删除字第串最后面多余〉
     * @author 庄立
     * @param str 字符串
     * @return 处理后字符串
     */
    public static String removeEnd(String str)
    {
    	if ( null == str )
        {
            return "";
        }
        else
        {
            Matcher matcher = Pattern.compile(",+$").matcher(str);
            str = matcher.replaceAll("");
            return str;
        }
    }
 
    /**
     * 〈字符串是否为空〉
     * @author 庄立
     * @param str 字符串
     * @return boolean 为null返回true;否则返回false
     */
    public static boolean isEmpty(String str)
    {
        return "".equals(trimString(str));
    }
    
    /**
     * 〈对象是否为空〉
     * @author 庄立
     * @param str 字符串
     * @return boolean 为null返回true;否则返回false
     */
    public static boolean isEmpty(Object obj)
    {
        return null == obj;
    }
    
    /**
     * 〈对象是否为空〉
     * @author 庄立
     * @param str 字符串
     * @return boolean 为null返回true;否则返回false
     */
    public static boolean isEmpty(Long obj)
    {
        return null == obj;
    }
    
    /**
     * 〈集合是否为空〉
     * @author 庄立
     * @param str 字符串
     * @return boolean 为null返回true;否则返回false
     */
    public static boolean isEmpty(List<?> list)
    {
        return null == list || list.isEmpty();
    }
    
    /**
     * 〈map是否为空〉
     * @author 庄立
     * @param str 字符串
     * @return boolean 为null返回true;否则返回false
     */
    public static boolean isEmpty(Map<?, ?> map)
    {
    	if(null == map || map.isEmpty())
    	{
    		return true;
    	}
        return  null == map || map.isEmpty();
    }
    
    /**
     * 
     * 〈数组对象是否为空〉
     * @author 庄立
     * @param obj 数组对象
     * @return boolean 为null返回true;否则返回false
     */
    public static boolean isEmpty(Object obj[])
    {
        if (null == obj)
        {
            return true;
        }
        return obj.length <= 0;
    }
    
    /**
     * 
     * 〈对字符串进行去掉空格〉
     * @author 庄立
     * @param str 字符串
     * @return boolean 返回trim的字符串
     */
    public static String trimString(String str)
    {
        return (null == str || "".equals(str)) ? "" : str.trim();
    }
    
    /**
     * 检查 字符串是否为空  
     * @author sen.ye
     * @param str
     * @return boolean 空：false, 不为空：true 
     */
    public static boolean checkEmpty(String str){
    	if(str==null||"".equals(str)||"null".equals(str)){
    		return false;
    	}
    	return true;
    }
    
    /**
     * 页面参数转Map
     * XRQ 2016年2月26日 上午11:38:03
     * @param params
     * @return
     */
	public static Map<String,Object> parameterToMap(Map<String,String[]> params){
		Map<String,Object> result=new HashMap<String,Object>();
		for(Entry<String,String[]> e:params.entrySet()){
			String value="";
			for(String v:e.getValue()){
				if(v!=null&&!v.trim().equals("")){
					value+=v+",";
				}
			}
			if(value!=null&&value.length()>1){
				value=value.substring(0,value.length()-1);
				result.put(e.getKey(), value);
			}
		}
		return result;
	}

	public static String sendPost(String url, String param) throws Exception {
		if(url!=null&&url.length()>1&&!url.startsWith("http://"))url="http://"+url;
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(),"utf-8"));
			// 发送请求参数
			out.print(param);
		
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"utf-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			throw e;
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
			}
		}
		return result;
	}
	public static String ClobToString(Clob clob) throws SQLException, IOException { 

		String reString = ""; 
		if(clob==null)return reString;
		Reader is = clob.getCharacterStream();// 得到流 
		BufferedReader br = new BufferedReader(is); 
		String s = br.readLine(); 
		StringBuffer sb = new StringBuffer(); 
		while (s != null) {// 执行循环将字符串全部取出付值给 StringBuffer由StringBuffer转成STRING 
		sb.append(s); 
		s = br.readLine(); 
		} 
		reString = sb.toString(); 
		return reString; 
		}
}
