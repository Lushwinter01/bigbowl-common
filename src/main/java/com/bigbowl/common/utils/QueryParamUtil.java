package com.bigbowl.common.utils;

import java.util.Iterator;
import java.util.Map;

public class QueryParamUtil
{

	/**
	 * 参数匹配 删除查询条件中不需要的参数
	 * 
	 * @param query
	 * @param paramMap
	 */
	public static void matchParam(String query, Map<String, ?> paramMap)
	{
		if (paramMap != null) 
		{
			Iterator<String> iterator = paramMap.keySet().iterator();
			while(iterator.hasNext())
			{
				String key = iterator.next();
				
				if (query.indexOf(key) < 0)
				{
					iterator.remove();
				}
			}
		}
	}
}
