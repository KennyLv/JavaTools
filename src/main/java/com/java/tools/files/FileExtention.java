package com.java.tools.files;

public class FileExtention {
	public static String getPostfix(String path) {
		if (path == null || "".equals(path.trim())) {
			return "";
		}
		if (path.contains(".")) {
			return path.substring(path.lastIndexOf(".") + 1, path.length());
		}
		return "";
	}
}
