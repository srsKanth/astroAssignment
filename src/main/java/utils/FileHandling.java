package utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class FileHandling {

	private static String rootpath = System.getProperty("user.dir");
	
	public static String getGlobalPropertyValue(String key) {
		return getPropertyValue(rootpath+"\\globalProperties.properties", key);
	}

	public static String getPropertyValue(String filePath, String key) {
		String rtnValue = "";
		try {
			File file = new File(filePath);
			FileInputStream fileInput = new FileInputStream(file);
			Properties properties = new Properties();
			properties.load(fileInput);
			fileInput.close();

			rtnValue = properties.getProperty(key);

		} catch (Exception e) {
			System.err.println("Exception thrown while reading property file :" + e.getMessage());
		}
		return rtnValue;

	}
}
