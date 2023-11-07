package com.bohuajia.o2o.util;

public class PathUtil {
	private static String seperator = System.getProperty("file.separator");
	
	public static String getImgBasePath() {
		String os = System.getProperty("os.name");
		String basePath = "";
		if (os.toLowerCase().startsWith("win")) {
			basePath = "D:/projectdev/image";
		} else {
			basePath = "/Users/projbh/Github_projects/image";
		}
		basePath = basePath.replace("/", seperator);
		return basePath;
	}
}
