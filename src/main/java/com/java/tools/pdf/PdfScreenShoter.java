package com.java.tools.pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.Page;
import org.icepdf.core.util.GraphicsRenderingHints;


public class PdfScreenShoter {

	public void generateBookIamge(String inputFile, String outputFile) {
		Document document = null;

		try {
			//旋转 0～360
			float rotation = 0f;
			// 缩略图显示 1表示不缩放，0.5表示缩小50%
			float zoom = 1f;

			document = new Document();
			document.setFile(inputFile);
			// maxPages = document.getPageTree().getNumberOfPages();

			BufferedImage image = (BufferedImage) document.getPageImage(0, GraphicsRenderingHints.SCREEN,
					Page.BOUNDARY_CROPBOX, rotation, zoom);

			Iterator iter = ImageIO.getImageWritersBySuffix("jpg");
			ImageWriter writer = (ImageWriter) iter.next();

			FileOutputStream out = new FileOutputStream(new File(outputFile));
			ImageOutputStream outImage = ImageIO.createImageOutputStream(out);

			writer.setOutput(outImage);
			writer.write(new IIOImage(image, null, null));

		} catch (Exception e) {
			System.out.println("to generate thumbnail of a book fail : " + inputFile);
			System.out.println(e);
		}
	}
}
