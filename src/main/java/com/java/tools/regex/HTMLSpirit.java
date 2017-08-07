package com.java.tools.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HTMLSpirit {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	}

	public static String delHTMLTag(String htmlStr) {
		String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
		String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
		String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
		String regEx_img = "<img[^>]+>";
		String regEx_class = "class=\"[\\w\\s-]*\"|id=\"[\\w\\s-]*\"|style=\"[\\w\\s-]*\"|";
		String regEx_quotes = "\"";
		String regEx_return = "\r|\n|\t";

		Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
		Matcher m_script = p_script.matcher(htmlStr);
		htmlStr = m_script.replaceAll(""); // 过滤script标签

		Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
		Matcher m_style = p_style.matcher(htmlStr);
		htmlStr = m_style.replaceAll(""); // 过滤style标签

		Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
		Matcher m_html = p_html.matcher(htmlStr);
		htmlStr = m_html.replaceAll(""); // 过滤html标签

		Pattern p_img = Pattern.compile(regEx_img, Pattern.CASE_INSENSITIVE);
		Matcher m_img = p_img.matcher(htmlStr);
		htmlStr = m_img.replaceAll("");

		Pattern p_class = Pattern.compile(regEx_class, Pattern.CASE_INSENSITIVE);
		Matcher m_class = p_class.matcher(htmlStr);
		htmlStr = m_class.replaceAll("");

		Pattern p_quotes = Pattern.compile(regEx_quotes, Pattern.CASE_INSENSITIVE);
		Matcher m_quotes = p_quotes.matcher(htmlStr);
		htmlStr = m_quotes.replaceAll("\\\\\"");

		Pattern p_return = Pattern.compile(regEx_return, Pattern.CASE_INSENSITIVE);
		Matcher m_return = p_return.matcher(htmlStr);
		htmlStr = m_return.replaceAll("");

		return htmlStr.trim(); // 返回文本字符串
	}

	/**
	 * 去掉字符串里面的html代码。<br>
	 * 要求数据要规范，比如大于小于号要配套,否则会被集体误杀。
	 * 
	 * @param content
	 *            内容
	 * @return 去掉后的内容
	 */
	public static String stripHtml(String content) {
		// <p>段落替换为换行
		content = content.replaceAll("<p .*?>", "\r\n");
		// <br><br/>替换为换行
		content = content.replaceAll("<br\\s*/?>", "\r\n");
		// 去掉其它的<>之间的东西
		content = content.replaceAll("\\<.*?>", "");
		// 还原HTML
		// content = HTMLDecoder.decode(content);
		return content;
	}

	// 笨方法：String s = "你要去除的字符串";
	// 1.去除空格：s = s.replace('\\s','');
	// 2.去除回车：s = s.replace('\n','');
	// 这样也可以把空格和回车去掉，其他也可以照这样做。
	//
	// 注：
	/*
	 * \n 回车(\u000a) \t 水平制表符(\u0009) \s 空格(\u0008) \r 换行(\u000d)
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}
}
