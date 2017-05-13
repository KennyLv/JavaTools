package com.java.tools.files;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class GetURL {
	public void showURL() {
		try {
			// 第一种：获取类加载的根路径
			// xxx\JavaTools\target\test-classes
			File f = new File(this.getClass().getResource("/").getPath());
			System.out.println(f);

			// 获取当前类的所在工程路径; 如果不加“/” 获取当前类的加载目录
			// xxx\JavaTools\target\classes\com\java\tools\files
			File f2 = new File(this.getClass().getResource("").getPath());
			System.out.println(f2);

			// /xxx/JavaTools/target/classes/com/java/tools/files/%5c
			String f3 = GetURL.class.getResource("\\").getFile().toString();
			System.out.println(f3);

			// 第二种：获取项目路径
			// \JavaTools
			File directory = new File("");// 参数为空
			String courseFile = directory.getCanonicalPath();
			System.out.println(courseFile);
			// 第四种： \JavaTools
			System.out.println(System.getProperty("user.dir"));

			// 第三种：file:/xxx/JavaTools/target/test-classes/
			URL xmlpath = this.getClass().getClassLoader().getResource("");
			System.out.println(xmlpath);

			/*
			 * 结果： C:\Documents and Settings\Administrator\workspace\projectName
			 * 获取当前工程路径
			 */

			// 第五种： 获取所有的类路径 包括jar包的路径
			System.out.println(System.getProperty("java.class.path"));
		} catch (IOException ex) {

		}

	}
}
