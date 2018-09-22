/**
 * 
 */
package com.bigbowl.common.utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * @author chuck
 *
 */
public class ClassPathUtil {
	public static String findFile (String name) throws IOException
	{	
		String path = "";
		ClassLoader cl = Thread.currentThread().getContextClassLoader();
		Enumeration<URL> resourceUrls = (cl != null ? cl.getResources(path)
			: ClassLoader.getSystemResources(path));
		for (; resourceUrls.hasMoreElements();) {
			URL url = resourceUrls.nextElement();
			if (url.getFile().endsWith(".jar")) {

			} else {
				File f = new File (url.getFile());
				if (f.exists() && f.isDirectory())
				{
					f = new File (f.getPath() + File.separator + name);
					if (f.exists())
					{
						return f.getAbsolutePath();
					}
				}
			}
		}
		return null;
	}
}
