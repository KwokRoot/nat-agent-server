package org.devops.util;

import java.io.IOException;
import java.util.Properties;

public class SystemProperties {

	private static Properties properties;

	public static Properties getSystemProperties(){
		return getSystemProperties(null);
	}
	
	public static Properties getSystemProperties(String pathStr){
		
		if(pathStr == null || "".equals(pathStr)){
			pathStr = "system.properties";
		}
		
		if (properties == null) {
			properties = new Properties();
			try {
				properties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(pathStr));
			} catch (IOException e) {
				System.err.println("加载 system.properties 配置文件失败");
				e.printStackTrace();
			}
		}
		return properties;
	}
	
	
}
