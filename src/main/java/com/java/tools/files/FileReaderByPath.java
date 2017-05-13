package com.java.tools.files;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderByPath {

	private String ReadFile(String path) {
		File file = new File(path);
		BufferedReader reader = null;
		String laststr = "";
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			while ((tempString = reader.readLine()) != null) {
				// System.out.println("line " + line + ": " + tempString);
				laststr = laststr + tempString.trim();
				line++;
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		return laststr;
	}
}
