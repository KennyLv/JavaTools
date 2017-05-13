package com.java.tools.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class W3CDomXmlCreater {
	public W3CDomXmlCreater() {
	}

	public Element createRootElement(String rootTagName, Document doc) {
		if (doc.getDocumentElement() == null) {
			// logger.debug("create root element '" + rootTagName +
			// "' success.");
			Element root = doc.createElement(rootTagName);
			doc.appendChild(root);
			return root;
		}
		// logger.warn("this dom's root element is exist,create fail.");
		return doc.getDocumentElement();
	}

	public Element createElement(Element parent, String tagName) {
		Document doc = parent.getOwnerDocument();
		Element child = doc.createElement(tagName);
		parent.appendChild(child);
		return child;
	}

	public Element createElement(Element parent, String tagName, String value) {
		Document doc = parent.getOwnerDocument();
		Element child = doc.createElement(tagName);
		setElementValue(child, value);
		parent.appendChild(child);
		return child;
	}

	public void createAttribute(Element parent, String attrName,
			String attrValue) {
		setElementAttr(parent, attrName, attrValue);
	}

	public void buildXmlFile(String path) {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.newDocument();

			TransformerFactory tfactory = TransformerFactory.newInstance();
			Transformer transformer = tfactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			// logger.debug("New DOMSource success.");
			StreamResult result = new StreamResult(new File(path));
			// logger.debug("New StreamResult success.");
			transformer.setOutputProperty("encoding", "GBK");
			transformer.transform(source, result);
			// logger.debug("Build XML File '" + path + "' success.");
		} catch (TransformerConfigurationException e) {
			// logger.error("Create Transformer error:" + e);
		} catch (TransformerException e) {
			// logger.error("Transformer XML file error:" + e);
		} catch (ParserConfigurationException e) {
			// logger.error("Parse DOM builder error:" + e);
		}
	}

	public Document readDocumentFile(String path) {
		Document doc = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(path));
			// Element root = doc.getDocumentElement();
		} catch (ParserConfigurationException e) {
		} catch (SAXException e) {
		} catch (IOException e) {
		}
		return doc;
	}

	
	// while (it.hasNext()) {// �����ӽڵ�
	// Object o = it.next();
	// Element el_row = (Element) o;
	// String str = el_row.getText();
	// Iterator it_row = el_row.elementIterator();
	// while (it_row.hasNext()) {
	// Element el_ename = (Element) it_row.next();
	// System.out.println(el_ename.getName() + "="
	// + el_ename.getData());
	// }
	// }

	// if (n.hasChildNodes()) {
	// NamedNodeMap attributes = n.getAttributes(); // ����ڵ���������
	// // single="false"
	// // att="tta">
	// // System.out.println(attributes.getLength());
	// for (int j = 0; j < attributes.getLength(); j++) {
	// Node attribute = attributes.item(j);
	// // �õ�������
	// String attributeName = attribute.getNodeName();
	// System.out.println("������:" + attributeName);
	// // �õ�����ֵ
	// String attributeValue = attribute.getNodeValue();
	// System.out.println("����ֵ:" + attributeValue);
	// }// ��ӡ�����������:att����ֵ:tta������:single����ֵ:false
	//
	// }

	public static NodeList getNodeList(Element parent) {
		return parent.getChildNodes();
	}

	public static Element[] getElementsByName(Element parent, String name) {
		ArrayList<Node> resList = new ArrayList<Node>();
		NodeList nl = getNodeList(parent);
		for (int i = 0; i < nl.getLength(); i++) {
			Node nd = nl.item(i);
			if (nd.getNodeName().equals(name)) {
				resList.add(nd);
			}
		}
		Element[] res = new Element[resList.size()];
		for (int i = 0; i < resList.size(); i++) {
			res[0] = (Element) resList.get(i);
		}
		return res;
	}

	public static String getElementName(Element element) {
		return element.getNodeName();
	}

	public static String getElementValue(Element element) {
		NodeList nl = element.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			if (nl.item(i).getNodeType() == Node.TEXT_NODE)// ��һ��Text Node
			{
				return element.getFirstChild().getNodeValue();
			}
		}
		return null;
	}

	public static String getElementAttr(Element element, String attr) {
		return element.getAttribute(attr);
	}

	public static void setElementValue(Element element, String val) {
		Node node = element.getOwnerDocument().createTextNode(val);
		NodeList nl = element.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node nd = nl.item(i);
			if (nd.getNodeType() == Node.TEXT_NODE)// ��һ��Text Node
			{
				nd.setNodeValue(val);
				return;
			}
		}
		element.appendChild(node);
	}

	public static void setElementAttr(Element element, String attr,
			String attrVal) {
		element.setAttribute(attr, attrVal);
	}

	public static void addElement(Element parent, Element child) {
		parent.appendChild(child);
	}

	public static void addElement(Element parent, String tagName) {
		Document doc = parent.getOwnerDocument();
		Element child = doc.createElement(tagName);
		parent.appendChild(child);
	}

	public static void addElement(Element parent, String tagName, String text) {
		Document doc = parent.getOwnerDocument();
		Element child = doc.createElement(tagName);
		setElementValue(child, text);
		parent.appendChild(child);
	}

	public static void removeElement(Element parent, String tagName) {
		NodeList nl = parent.getChildNodes();
		for (int i = 0; i < nl.getLength(); i++) {
			Node nd = nl.item(i);
			if (nd.getNodeName().equals(tagName)) {
				parent.removeChild(nd);
			}
		}
	}

}
