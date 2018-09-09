package com.lk.watch.configuration;

import java.io.InputStream;

/**
*
* @author lk
* @date 2018Äê9ÔÂ9ÈÕ
*/

public class LoadProperties {
	
	private static InputStream in;

	public static InputStream getInputStream(String configFile) {
		LoadProperties.class.getClassLoader()
		 .getResourceAsStream(configFile);
		return in;
	}
}
