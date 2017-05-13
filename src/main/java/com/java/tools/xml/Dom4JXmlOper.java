package com.java.tools.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4JXmlOper {

	public Dom4JXmlOper() {

	}

	public Document readDocumentFile(String path) {
		SAXReader sr = new SAXReader();
		try {
			Document doc = sr.read(path);
			return doc;
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void updateXMLdocs() throws DocumentException, IOException {
		String path = Dom4JXmlOper.class.getResource(".").getFile().toString();

		SAXReader reader = new SAXReader();
		// 设置读取文件内容的编码
		reader.setEncoding("GBK");
		Document doc = reader.read(path + "books.xml");

		// 修改内容之一: 如果book节点中show属性的内容为yes,则修改成no
		// 先用xpath查找对象
		// 根据试用，根节点books的xpath路径要加/或不加都可以。
		List<Attribute> attrList = doc.selectNodes("books/book/@show");
		Iterator<Attribute> i = attrList.iterator();
		while (i.hasNext()) {
			Attribute attribute = i.next();
			if (attribute.getValue().equalsIgnoreCase("yes")) {
				attribute.setValue("no");
			}
		}

		// 修改内容之二: 把owner项内容改为Tshinghua
		// 并在owner节点中加入date节点,date节点的内容为2004-09-11,还为date节点添加一个属性type
		List<Element> eleList = doc.selectNodes("owner");
		Iterator<Element> eleIter = eleList.iterator();
		if (eleIter.hasNext()) {
			Element ownerElement = eleIter.next();
			ownerElement.setText("Tshinghua");
			Element dateElement = ownerElement.addElement("date");
			DateFormat df = SimpleDateFormat.getDateInstance();
			dateElement.setText(df.format(new Date()));
			ownerElement.addAttribute("type", "Gregorian calendar");
		}

		// 修改内容之三: 若title内容为Dom4j Tutorials,则删除该节点
		eleList = doc.selectNodes("books/book");
		eleIter = eleList.iterator();
		while (eleIter.hasNext()) {
			Element element = eleIter.next();
			Iterator<Element> titleIter = element.elementIterator("title");
			while (titleIter.hasNext()) {
				Element title = titleIter.next();
				if (title.getText().equalsIgnoreCase("Dom4j Tutorials")) {
					element.remove(title);
				}
			}
		}
		OutputFormat format = OutputFormat.createPrettyPrint();
		// 利用格式化类对编码进行设置
		format.setEncoding("GBK");
		FileOutputStream output = new FileOutputStream(new File(path + "books1.xml"));
		XMLWriter writer = new XMLWriter(output, format);
		writer.write(doc);
		writer.flush();
		writer.close();
	}
}
