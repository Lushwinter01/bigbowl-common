package com.bigbowl.common.utils;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class MapUtil {

    /**
     * 计算百度坐标两点之间的距离
     * @param x1 第一个点的 x坐标 或者纬度 latitude
     * @param y1 第一个点的 y坐标 或者经度 longitude
     * @param x2 第二个点的 x坐标 或者纬度 latitude
     * @param y2 第二个点的 y坐标 或者经度 longitude
     * @return 两点距离米
     */
    public static double getDistance(double x1, double y1, double x2, double y2) {

        return GetShortDistance(x1, y1, x2, y2);
    }
    static double DEF_PI = 3.14159265359; // PI
    static double DEF_2PI = 6.28318530712; // 2*PI
    static double DEF_PI180 = 0.01745329252; // PI/180.0
    static double DEF_R = 6370693.5; // radius of earth

    /**
     * 求两点之间的距离
     * */
    public static double GetShortDistance(double lon1, double lat1, double lon2, double lat2) {

        double ew1, ns1, ew2, ns2;
        double dx, dy, dew;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 经度差
        dew = ew1 - ew2;
        // 若跨东经和西经180 度，进行调整
        if (dew > DEF_PI)
            dew = DEF_2PI - dew;
        else if (dew < -DEF_PI)
            dew = DEF_2PI + dew;
        dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
        dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
        // 勾股定理求斜边长
        distance = Math.sqrt(dx * dx + dy * dy);
        DecimalFormat df = new DecimalFormat("#.##");
        Double distance2 = Double.parseDouble(df.format((distance + distance * 0.25) / 1000));
        return distance2;
    }

    /**
     * 运算与客户距离
     * */
    public static String operationDistance(String pointX,String pointY,String longtitudeX,String LatitudeY) {
		if (StringUtils.isNotBlank(pointX)
				&& StringUtils.isNotBlank(pointY)
				&& StringUtils.isNotBlank(longtitudeX)
				&& StringUtils.isNotBlank(LatitudeY)) {
			double newPointX = Double.valueOf(pointX);
			double newPointY = Double.valueOf(pointY);
			double newLatitudeX = Double.valueOf(longtitudeX);
			double newLongtitudeY = Double.valueOf(LatitudeY);
			double distance = getDistance(newPointX, newPointY, newLatitudeX, newLongtitudeY);
			DecimalFormat format = new DecimalFormat("#.##");
			return format.format(distance);
		}else{
			return null;
		}
    }
    
    /**
     * 排序 取出最近的距离数据
     * */
    public static Long distanceSort(Map<Long, Double> map) {
    	if(null == map || map.size()<1){
    		return null;
    	}
    	List<Map.Entry<Long, Double>> listData = new ArrayList<Map.Entry<Long, Double>>(map.entrySet());
        Collections.sort(listData, new Comparator<Map.Entry<Long, Double>>()  
                {    
    	          public int compare(Map.Entry<Long, Double> o1, Map.Entry<Long, Double> o2)  
    	          {  
    	            if ((o1.getValue() - o2.getValue())>0)  
    	              return 1;  
    	            else if((o1.getValue() - o2.getValue())==0)  
    	              return 0;  
    	            else   
    	              return -1;  
    	          }  
                } );
        return listData.get(0).getKey();
    }
    
    public static void main(String args[]) throws IOException {

        System.out.println(MapUtil.GetShortDistance(112.021121, 22.4633311, 114.3546436, 24.6354634));
        
        
        Map<Long, Double> map_Data = new HashMap<Long, Double>();  
        map_Data.put(1L, 0.98);  
        map_Data.put(2L, 0.50);  
        map_Data.put(3L, 0.76);  
        map_Data.put(4L, 0.23);  
        map_Data.put(5L, 0.85);  
       System.out.println(map_Data);  
       System.out.println(distanceSort(map_Data));
    }
}
