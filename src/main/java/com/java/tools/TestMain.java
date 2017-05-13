package com.java.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
//import java.util.Map;
//import java.util.HashMap;
import com.java.tools.csv.CsvCreater;
import com.java.tools.csv.CsvReader;
import com.java.tools.excel.ExcelOperater;
import com.java.tools.files.GetURL;
import com.java.tools.files.ListFiles;
import com.java.tools.pdf.PdfScreenShoter;
import com.java.tools.random.Password;
import com.java.tools.regex.regexChecker;

public class TestMain {

	public static void main(String[] args) {
		pdfScreenShot("E:\\NEWQRG");
		// importCsv(new File("\\test\\20885210_DETAILS.csv"));
		// rsaTest();
		// mySignatureTest();
		// createRandom();
		// JSONObject jsKeys = getJsons();
		// outputUrl();
		// readExcel();
		// regexCheck();
		
		// List<String> processRecords = new ArrayList<String>();
		// processRecords.add("Begin upload file.");
		// processRecords.add("Begin upload file.");
		// processRecords.add("Begin upload file.");
		// List<String> xds = new ArrayList<String>();
		// xds.add("xds upload file.");
		// xds.add("xds upload file.");
		// xds.add("xds upload file.");
		// processRecords.addAll(xds);
		// System.out.println(processRecords);
		
		// String text = " 2017.5 Hyunda Secv G80 ";
		//
		// String realText = text.trim();
		// int splitPosition = realText.indexOf(" ");
		// System.out.println(realText.substring(0, splitPosition).trim());
		// System.out.println(realText.substring(splitPosition,
		// realText.length()).trim());

	}

	/**
	 * CSV导出
	 * 
	 * @throws Exception
	 */
	public void exportCsv() {
		List<String> dataList = new ArrayList<String>();
		dataList.add("1,张三,男");
		dataList.add("2,李四,男");
		dataList.add("3,小红,女");
		boolean isSuccess = CsvCreater.exportCsv(new File("D:/test/ljq.csv"), dataList);
		System.out.println(isSuccess);

	}

	/**
	 * CSV导出
	 * 
	 * @throws Exception
	 */
	public static void importCsv(File f) {
		List<String> dataList = CsvCreater.importCsv(f);
		if (dataList != null && !dataList.isEmpty()) {
			for (int i = 5; i < dataList.size(); i++) {
				String data = dataList.get(i);
				// System.out.println(data);
				if (!data.startsWith("#")) {
					// System.out.println(!data.startsWith("#"));
					System.out.println(CsvReader.fromCSVLinetoArray(data));
				}
				System.out.println(" ");
			}
			/*
			 * for (String data : dataList) { if(!data.startsWith("#")){
			 * System.out.println(CsvReader.fromCSVLinetoArray(data));
			 * 
			 * } }
			 */
			/*
			 * try { CsvReader reader = new CsvReader(
			 * "D:\\Documents\\Downloads\\testData\\test\\20885210964847930156_20170102_DETAILS.csv"
			 * ); System.out.println(reader.readLine());// fromCSVLinetoArray
			 * 
			 * } catch (Exception e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
		}
	}

	private static void createRandom() {
		String pw = Password.getRandomString(16);
		String pw1 = Password.getRandomStringOne(16);
		String pw2 = Password.genRandomNum(16);
		System.out.println("getRandomString：" + pw);
		System.out.println("getRandomStringOne：" + pw1);
		System.out.println("genRandomNum：" + pw2);
	}

	private static void pdfScreenShot(String rootPath) {
		PdfScreenShoter pdfSs = new PdfScreenShoter();
		List<File> filelist = ListFiles.findFileByExten(rootPath, ".pdf");
		for (int i = 0; i < filelist.size(); i++) {
			String strFileName = filelist.get(i).getAbsolutePath();
			System.out.println(strFileName);
			pdfSs.generateBookIamge(strFileName, strFileName.replace(".pdf", ".jpg"));
		}
		System.out.println("DONE!");
	}

	private static void dom4jxml() {
		/*
		 * Document doc = readXmlFromFiles(); Element el_root =
		 * doc.getRootElement(); Iterator it = el_root.elementIterator(); while
		 * (it.hasNext()) { Element childNode = (Element) it.next();
		 * List<Attribute> listAttr = childNode.attributes(); //
		 * System.out.println("Node:" + childNode); for (Attribute attr :
		 * listAttr) { String name = attr.getName(); String value =
		 * attr.getValue();
		 * 
		 * if (rc.startCheck(reg, value)) { // System.out.println(name + " = " +
		 * value); // attr.setValue("no"); String oldResult =
		 * value.split("[(]")[0]; String oldIds =
		 * value.split("[(]")[1].replace(")", ""); String[] ids =
		 * oldIds.split(",");
		 * 
		 * String newIds = ""; for (String id : ids) { //
		 * System.out.println(id); newIds += getNewKeys(id) + ","; } String
		 * newValue = oldResult + "(" + newIds + ")";
		 * attr.setValue(newValue.replace(",)", ")")); } } }
		 */
	}

	private static void w3cxml() {
		// try {
		// Document doc = readXmlFromFiles();
		// Element el_root = doc.getRootElement();
		// Node it = el_root.getChildNodes();
		// for (int index = 1; index <= 5; index++) {
		// System.out.println("Node:" + index);
		// Node childNode = it.item(index);
		// NamedNodeMap attrs = childNode.getAttributes();
		// if (attrs != null) {
		// System.out.println("attr:" + attrs.getLength());
		// // for (int j = 0; j < attrs.getLength(); j++) {
		// // Node attribute = attrs.item(j);
		// // // String attributeName = attribute.getNodeName();
		// // // System.out.println("������:" + attributeName);
		// // // String attributeValue = attribute.getNodeValue();
		// // // System.out.println("����ֵ:" + attributeValue);
		// // }
		// }
		// }
		// } catch (DOMException e) {
		// }

		// saveNewXmlFiles(doc);
	}

	private static void regexCheck() {
		regexChecker rc= new regexChecker();
		String reg = "^[A-Z\\s]+$";//
		System.out.println(rc.startCheck(reg, "FEATURES AND CONTROLS"));//MULTIMEDIA //FEATURES AND CONTROLS
		// RegexChk rc = new RegexChk();
		// String reg = "\\w*[(][\\w , /]*[)]";
		// String[] strArray = { "YES(4718,4719)", "YES(4718)",
		// "NO(432421,435435)", "-", "YES(B,J)", "NO(J)",
		// "NO(87,1203,2278)", "YES", "YES " };
		// for (int i = 0; i < strArray.length; i++) {
		// String val = strArray[i];
		// if (rc.startCheck(reg, val)) {
		// System.out.println(val);
		// String oldResult = val.split("[(]")[0];
		// String oldIds = val.split("[(]")[1].replace(")", "");
		// String[] ids = oldIds.split(",");
		// String newIds = "";
		// for (String id : ids) {
		// newIds += getNewKeys(id) + ",";
		// }
		// String newValue = oldResult + "(" + newIds + ")";
		// System.out.println("=" + newValue.replace(",)", ")"));
		// }
		// }
	}

	private static void rsaTest() {
		//
		// String filepath = "F:/tmp/";
		// // // 生成 公钥/私钥 对
		// //RSAEncrypt.genKeyPair(filepath);
		// try {
		//
		// System.out.println("--------------公钥加密私钥解密过程-------------------");
		// String plainText = "ihep_公钥加密私钥解密";
		// // 公钥加密过程
		// byte[] cipherData = RSAEncrypt.encrypt(
		// RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)),
		// plainText.getBytes());
		//
		// String cipher = Base64.encode(cipherData);
		// // 私钥解密过程
		// byte[] res =
		// RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)),
		// Base64.decode(cipher));
		// String restr = new String(res);
		// System.out.println("原文：" + plainText);
		// System.out.println("加密：" + cipher);
		// System.out.println("解密：" + restr);
		// System.out.println();
		//
		// System.out.println("--------------私钥加密公钥解密过程-------------------");
		// plainText = "ihep_私钥加密公钥解密";
		// // 私钥加密过程
		// cipherData =
		// RSAEncrypt.encrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)),
		// plainText.getBytes());
		// cipher = Base64.encode(cipherData);
		// // 公钥解密过程
		// res =
		// RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)),
		// Base64.decode(cipher));
		// restr = new String(res);
		// System.out.println("原文：" + plainText);
		// System.out.println("加密：" + cipher);
		// System.out.println("解密：" + restr);
		// System.out.println();
		//
		// System.out.println("---------------私钥签名过程------------------");
		// String content = "ihep_这是用于签名的原始数据";
		// String signstr = RSASignature.sign(content,
		// RSAEncrypt.loadPrivateKeyByFile(filepath));
		// System.out.println("签名原串：" + content);
		// System.out.println("签名串：" + signstr);
		// System.out.println();
		//
		// System.out.println("---------------公钥校验签名------------------");
		// System.out.println("签名原串：" + content);
		// System.out.println("签名串：" + signstr);
		//
		// System.out.println(
		// "验签结果：" + RSASignature.doCheck(content, signstr,
		// RSAEncrypt.loadPublicKeyByFile(filepath)));
		// System.out.println();
		// } catch (Exception e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }

		// private static void mySignatureTest(){
		// String PLAIN_TEXT = "MANUTD is the greatest club in the world";
		//
		// MySignature myS = new MySignature();
		// // 建立两套公私钥对
		// Map<String, byte[]> keyMap1 = MyRSA.generateKeyBytes();
		// PublicKey publicKey1 =
		// MyRSA.restorePublicKey(keyMap1.get(MyRSA.PUBLIC_KEY));
		// PrivateKey privateKey1 =
		// MyRSA.restorePrivateKey(keyMap1.get(MyRSA.PRIVATE_KEY));
		//
		// Map<String, byte[]> keyMap2 = MyRSA.generateKeyBytes();
		// PublicKey publicKey2 =
		// MyRSA.restorePublicKey(keyMap2.get(MyRSA.PUBLIC_KEY));
		// PrivateKey privateKey2 =
		// MyRSA.restorePrivateKey(keyMap2.get(MyRSA.PRIVATE_KEY));
		//
		// /**
		// * 假设现在A签名后向B发送消息 A用B的公钥进行加密 用自己A的私钥进行签名
		// */
		// byte[] encodedText = MyRSA.RSAEncode(publicKey2,
		// PLAIN_TEXT.getBytes());
		// byte[] signature = myS.sign(privateKey1, PLAIN_TEXT.getBytes());
		//
		// /**
		// * 现在B收到了A的消息，进行两步操作 用B的私钥解密得到明文 将明文和A的公钥进行验签操作
		// */
		//
		// byte[] decodedText = MyRSA.RSADecode(privateKey2,
		// encodedText).getBytes();
		// System.out.println("Decoded Text: " + new String(decodedText));
		//
		// System.out.println("Signature is " + myS.verify(publicKey1,
		// signature,
		// decodedText));
		//
		//
	}

	public static void outputUrl() {
		GetURL gurl = new GetURL();
		gurl.showURL();
	}
	

	public static void readExcel() {
		String filePath = System.getProperty("user.dir");
		ExcelOperater eo = new ExcelOperater();
		eo.readExcel(filePath + "\\upload\\Delivery App Content Matrix 2.28.17.xlsx");
	}


	public static void getJsons() {
		// public static JSONObject getJsons() {
		/*
		 * ReadJSON rj = new ReadJSON(); JSONObject js = rj.getJsonFrom(
		 * "C:\\Users\\lv_qing\\Desktop\\keys.json") ; return js; }
		 * 
		 * public static String getNewKeys(String oldKeys) { String newKey = "";
		 * Iterator iter = jsKeys.keys(); while (iter.hasNext()) { String key =
		 * (String) iter.next(); JSONArray values = (JSONArray) jsKeys.get(key);
		 * if (values.contains(oldKeys)) { newKey = key; break; } } return
		 * newKey; }
		 * 
		 * // public static Document readXmlFromFiles() { // W3CDomXmlCreater
		 * xmlBuilder = new W3CDomXmlCreater(); // return xmlBuilder //
		 * .readDocumentFile( "C:\\Users\\lv_qing\\Desktop\\result.xml" ); // }
		 * 
		 * public static Document readXmlFromFiles() { // Need XML had xml
		 * namespace xmlns Dom4JXmlOper xmlBuilder = new Dom4JXmlOper(); return
		 * xmlBuilder .readDocumentFile(
		 * "C:\\Users\\lv_qing\\Desktop\\result.xml" ); }
		 * 
		 * public static void saveNewXmlFiles(Document doc) { try { OutputFormat
		 * format = OutputFormat.createPrettyPrint(); format.setEncoding("GBK");
		 * FileOutputStream output = new FileOutputStream(new File(
		 * "C:\\Users\\lv_qing\\Desktop\\result_1.xml" )); XMLWriter writer =
		 * new XMLWriter(output, format); writer.write(doc); writer.flush();
		 * writer.close(); } catch (UnsupportedEncodingException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch
		 * (FileNotFoundException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } catch (IOException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); }
		 */
	}
}
