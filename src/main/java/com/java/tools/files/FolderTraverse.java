package com.java.tools.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class FolderTraverse {
	
	public boolean readfile(String filepath) throws FileNotFoundException, IOException {
		try {

			File file = new File(filepath);
			if (!file.isDirectory()) {
				// System.out.println("文件");
				// System.out.println("inpath=" + filepath);
				// System.out.println("path=" + file.getPath());
				System.out.println(file.getAbsolutePath());
				System.out.println();
				// System.out.println("name=" + file.getName());
			} else if (file.isDirectory()) {
				// System.out.println("文件夹");
				// System.out.println("inpath=" + filepath);
				String[] filelist = file.list();
				for (int i = 0; i < filelist.length; i++) {
					File readfile = new File(filepath + "\\" + filelist[i]);
					if (!readfile.isDirectory()) {
						System.out.println(readfile.getAbsolutePath());
						System.out.println();
					} else if (readfile.isDirectory()) {
						readfile(filepath + "\\" + filelist[i]);
					}
				}

			}

		} catch (FileNotFoundException e) {
			System.out.println("readfile()   Exception:" + e.getMessage());
		}
		return true;
	}
}
