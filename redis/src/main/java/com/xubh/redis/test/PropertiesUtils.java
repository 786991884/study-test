package com.xubh.redis.test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.ResourceBundle;


public class PropertiesUtils {
	
	private static ResourceBundle rb;

	// 初始化资源文件
	static {
		rb = ResourceBundle.getBundle("violation");
	}

	private PropertiesUtils() {
	}
	
	
	public static String getProValue(String propKey) {
		// loading xmlProfileGen.properties from the classpath
		return rb.getString(propKey);

	}

}
