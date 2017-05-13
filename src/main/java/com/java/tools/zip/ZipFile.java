package com.java.tools.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipFile {
	private static int k = 1; // 定义递归次数变量

	public static void main(String[] args) {

//		try {
//			compression("D:\\Documents\\Downloads\\20885210964847930156_20170102.csv.zip",
//					new File("D:\\Documents\\Downloads\\20885210964847930156_20170102.csv"));
//			decompression("D:\\Documents\\Downloads\\20885210964847930156_20170102.csv.zip",
//					"C:\\Users\\Administrator\\Desktop\\backup_down_invoice");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

	/**
	 * 
	 * @param outputZipFileName
	 * @param inputFile
	 * @throws Exception
	 */
	private static void compression(String outputZipFileName, File inputFile) throws Exception {
		System.out.println("压缩中...");
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outputZipFileName));
		BufferedOutputStream bo = new BufferedOutputStream(out);
		String baseFile = inputFile.getName();
		// 递归
		zip(out, inputFile, baseFile, bo);
		bo.close();
		out.close(); // 输出流关闭
		System.out.println("压缩完成");
	}

	private static void zip(ZipOutputStream out, File f, String base, BufferedOutputStream bo) throws Exception { // 方法重载
		if (f.isDirectory()) {
			File[] fl = f.listFiles();
			if (fl.length == 0) {
				out.putNextEntry(new ZipEntry(base + "/")); // 创建zip压缩进入点base
				System.out.println(base + "/");
			}
			for (int i = 0; i < fl.length; i++) {
				zip(out, fl[i], base + "/" + fl[i].getName(), bo); // 递归遍历子文件夹
			}
			System.out.println("第" + k + "次递归");
			k++;
		} else {
			out.putNextEntry(new ZipEntry(base)); // 创建zip压缩进入点base
			System.out.println(base);
			FileInputStream in = new FileInputStream(f);
			BufferedInputStream bi = new BufferedInputStream(in);
			int b;
			while ((b = bi.read()) != -1) {
				bo.write(b); // 将字节流写入当前zip目录
			}
			bi.close();
			in.close(); // 输入流关闭
		}
	}

	private static void decompression(String inputZipFileName, String outputFolder) throws Exception {

		// TODO Auto-generated method stub
		long startTime = System.currentTimeMillis();
		try {
			ZipInputStream Zin = new ZipInputStream(new FileInputStream(inputZipFileName));// 输入源zip路径
			BufferedInputStream Bin = new BufferedInputStream(Zin);
			File Fout = null;
			ZipEntry entry;
			try {
				while ((entry = Zin.getNextEntry()) != null && !entry.isDirectory()) {
					Fout = new File(outputFolder, entry.getName());
					if (!Fout.exists()) {
						(new File(Fout.getParent())).mkdirs();
					}
					FileOutputStream out = new FileOutputStream(Fout);
					BufferedOutputStream Bout = new BufferedOutputStream(out);
					int b;
					while ((b = Bin.read()) != -1) {
						Bout.write(b);
					}
					Bout.close();
					out.close();
					System.out.println(Fout + "解压成功");
				}
				Bin.close();
				Zin.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("耗费时间： " + (endTime - startTime) + " ms");

	}
}
