package com.java.tools.img;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.ImageIcon;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * @Description:图片处理工具
 * @author:liuyc
 * @time:2016年5月27日 上午10:18:00
 */
public class ImageHandleHelper {

	/**
	 * @Description:截图
	 * @author:liuyc
	 * @time:2016年5月27日 上午10:18:23
	 * @param srcFile源图片、targetFile截好后图片全名、startAcross
	 *            开始截取位置横坐标、StartEndlong开始截图位置纵坐标、width截取的长，hight截取的高
	 */
	public static void cutImage(String srcFile, String targetFile, int startAcross, int StartEndlong, int width,
			int hight) throws Exception {
		// 取得图片读入器
		Iterator<ImageReader> readers = ImageIO.getImageReadersByFormatName("jpg");
		ImageReader reader = readers.next();
		// 取得图片读入流
		InputStream source = new FileInputStream(srcFile);
		ImageInputStream iis = ImageIO.createImageInputStream(source);
		reader.setInput(iis, true);
		// 图片参数对象
		ImageReadParam param = reader.getDefaultReadParam();
		Rectangle rect = new Rectangle(startAcross, StartEndlong, width, hight);
		param.setSourceRegion(rect);
		BufferedImage bi = reader.read(0, param);
		ImageIO.write(bi, targetFile.split("\\.")[1], new File(targetFile));
	}

	/**
	 * @Description:图片拼接 （注意：必须两张图片长宽一致哦）
	 * @author:liuyc
	 * @time:2016年5月27日 下午5:52:24
	 * @param files
	 *            要拼接的文件列表
	 * @param type1
	 *            横向拼接， 2 纵向拼接
	 */
	public static void mergeImage(String[] files, int type, String targetFile) {
		int len = files.length;
		if (len < 1) {
			throw new RuntimeException("图片数量小于1");
		}
		File[] src = new File[len];
		BufferedImage[] images = new BufferedImage[len];
		int[][] ImageArrays = new int[len][];
		for (int i = 0; i < len; i++) {
			try {
				src[i] = new File(files[i]);
				images[i] = ImageIO.read(src[i]);
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
			int width = images[i].getWidth();
			int height = images[i].getHeight();
			ImageArrays[i] = new int[width * height];
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
		if (type == 1 && newWidth < 1) {
			return;
		}
		if (type == 2 && newHeight < 1) {
			return;
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
			// 输出想要的图片
			ImageIO.write(ImageNew, targetFile.split("\\.")[1], new File(targetFile));

		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description:小图片贴到大图片形成一张图(合成)
	 * @author:liuyc
	 * @time:2016年5月27日 下午5:51:20
	 */
	public static final void overlapImage(String bigPath, String smallPath, String outFile) {
		try {
			BufferedImage big = ImageIO.read(new File(bigPath));
			BufferedImage small = ImageIO.read(new File(smallPath));
			Graphics2D g = big.createGraphics();
			int x = (big.getWidth() - small.getWidth()) / 2;
			int y = (big.getHeight() - small.getHeight()) / 2;
			g.drawImage(small, x, y, small.getWidth(), small.getHeight(), null);
			g.dispose();
			ImageIO.write(big, outFile.split("\\.")[1], new File(outFile));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * @Description:给图片添加文字信息
	 * @author:liuyc
	 * @time:2016年5月31日 上午10:23:36
	 */
	public static void drawStringForImage(String filePath, String content, Color contentColor, float qualNum,
			String targetFile) {
		ImageIcon imgIcon = new ImageIcon(filePath);
		Image theImg = imgIcon.getImage();
		int width = theImg.getWidth(null) == -1 ? 200 : theImg.getWidth(null);
		int height = theImg.getHeight(null) == -1 ? 200 : theImg.getHeight(null);
		BufferedImage bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bimage.createGraphics();
		g.setColor(contentColor);
		g.setBackground(Color.red);
		g.drawImage(theImg, 0, 0, null);
		// 设置字体、字型、字号
		g.setFont(new Font(null, Font.BOLD, 13));
		// 写入文字
		g.drawString(content, 10, height / 2);
		g.dispose();
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(targetFile);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(bimage);
			param.setQuality(qualNum, true);
			encoder.encode(bimage, param);
			out.flush();
		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					out = null;
					throw new RuntimeException(e);
				}
			}
		}
	}
}