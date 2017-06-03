package com.java.tools.img;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

public class ImageMerge {

	private static void mergeImage() throws IOException {

		int rows = 2;
		int cols = 2;
		int chunks = rows * cols;

		int chunkWidth, chunkHeight;
		int type;

		// 读入小图
		File[] imgFiles = new File[chunks];
		for (int i = 0; i < chunks; i++) {
			imgFiles[i] = new File("C:\\img\\merge\\img" + i + ".jpg");
		}

		// 创建BufferedImage
		BufferedImage[] buffImages = new BufferedImage[chunks];
		for (int i = 0; i < chunks; i++) {
			buffImages[i] = ImageIO.read(imgFiles[i]);
		}
		type = buffImages[0].getType();
		chunkWidth = buffImages[0].getWidth();
		chunkHeight = buffImages[0].getHeight();

		// 设置拼接后图的大小和类型
		BufferedImage finalImg = new BufferedImage(chunkWidth * cols, chunkHeight * rows, type);

		// 写入图像内容
		int num = 0;
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				finalImg.createGraphics().drawImage(buffImages[num], chunkWidth * j, chunkHeight * i, null);
				num++;
			}
		}

		// 输出拼接后的图像
		ImageIO.write(finalImg, "jpeg", new File("C:\\img\\merge\\finalImg.jpg"));

		System.out.println("完成拼接！");
	}

	/**
	 * Java拼接多张图片
	 * 
	 * @param pics
	 * @param type
	 * @param dst_pic
	 * @return
	 */
	public static boolean merge(String[] pics, String type, String dst_pic) {

		int len = pics.length;
		if (len < 1) {
			System.out.println("pics len < 1");
			return false;
		}
		File[] src = new File[len];
		BufferedImage[] images = new BufferedImage[len];
		int[][] ImageArrays = new int[len][];
		for (int i = 0; i < len; i++) {
			try {
				src[i] = new File(pics[i]);
				images[i] = ImageIO.read(src[i]);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
			int width = images[i].getWidth();
			int height = images[i].getHeight();
			ImageArrays[i] = new int[width * height];// 从图片中读取RGB
			ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
		}

		int dst_height = 0;
		int dst_width = images[0].getWidth();
		for (int i = 0; i < images.length; i++) {
			dst_width = dst_width > images[i].getWidth() ? dst_width : images[i].getWidth();

			dst_height += images[i].getHeight();
		}
		System.out.println(dst_width);
		System.out.println(dst_height);
		if (dst_height < 1) {
			System.out.println("dst_height < 1");
			return false;
		}

		// 生成新图片
		try {
			// dst_width = images[0].getWidth();
			BufferedImage ImageNew = new BufferedImage(dst_width, dst_height, BufferedImage.TYPE_INT_RGB);
			int height_i = 0;
			for (int i = 0; i < images.length; i++) {
				ImageNew.setRGB(0, height_i, dst_width, images[i].getHeight(), ImageArrays[i], 0, dst_width);
				height_i += images[i].getHeight();
			}

			File outFile = new File(dst_pic);
			ImageIO.write(ImageNew, type, outFile);// 写图片
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * 图片拼接
	 * 
	 * @param files
	 *            要拼接的文件列表
	 * @param type
	 *            1 横向拼接， 2 纵向拼接
	 * @return
	 */
	public static InputStream merge(String[] files, int type) {

		int len = files.length;
		if (len < 1) {
			System.out.print("图片数量小于1");
			return null;
		}

		File[] src = new File[len];
		BufferedImage[] images = new BufferedImage[len];
		int[][] ImageArrays = new int[len][];
		for (int i = 0; i < len; i++) {
			try {
				src[i] = new File(files[i]);
				images[i] = ImageIO.read(src[i]);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
			int width = images[i].getWidth();
			int height = images[i].getHeight();
			ImageArrays[i] = new int[width * height];// 从图片中读取RGB
			ImageArrays[i] = images[i].getRGB(0, 0, width, height, ImageArrays[i], 0, width);
		}

		int newHeight = 0;
		int newWidth = 0;
		for (int i = 0; i < images.length; i++) {
			// 横向
			if (type == 1) {
				newHeight = newHeight > images[i].getHeight() ? newHeight : images[i].getHeight();
				newWidth += images[i].getWidth();
			} else if (type == 2) {// 纵向
				newWidth = newWidth > images[i].getWidth() ? newWidth : images[i].getWidth();
				newHeight += images[i].getHeight();
			}
		}

		System.out.print("拼接后图像宽度：" + newWidth);
		System.out.print("拼接后图像高度：" + newHeight);
		if (type == 1 && newWidth < 1) {
			System.out.print("拼接后图像宽度小于1");
			return null;
		}
		if (type == 2 && newHeight < 1) {
			System.out.print("拼接后图像高度小于1");
			return null;
		}

		// 生成新图片
		try {
			BufferedImage ImageNew = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
			int height_i = 0;
			int width_i = 0;
			for (int i = 0; i < images.length; i++) {
				if (type == 1) {
					ImageNew.setRGB(width_i, 0, images[i].getWidth(), newHeight, ImageArrays[i], 0,
							images[i].getWidth());
					width_i += images[i].getWidth();
				} else if (type == 2) {
					ImageNew.setRGB(0, height_i, newWidth, images[i].getHeight(), ImageArrays[i], 0, newWidth);
					height_i += images[i].getHeight();
				}
			}
			// File outFile = new File("d://123.jpg");
			// ImageIO.write(ImageNew, "jpg", outFile);// 写图片

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(ImageNew, "jpg", out);// 图片写入到输出流中

			ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
			return in;

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
