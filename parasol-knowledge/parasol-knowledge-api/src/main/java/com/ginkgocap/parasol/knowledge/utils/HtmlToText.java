package com.ginkgocap.parasol.knowledge.utils;

import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class HtmlToText {
	
	/**
	 * html转化为text
	 * @author 周仕奇
	 * @date 2016年1月14日 下午3:57:29
	 * @param inputString
	 * @return
	 */
	public static String html2Text(String inputString) {
		String htmlStr = inputString; // 含html标签的字符串
		String textStr = "";
		java.util.regex.Pattern p_script;
		java.util.regex.Matcher m_script;
		java.util.regex.Pattern p_style;
		java.util.regex.Matcher m_style;
		java.util.regex.Pattern p_html;
		java.util.regex.Matcher m_html;
		try {
			String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>"; // 定义script的正则表达式{或<script>]*?>[\s\S]*?<\/script>
			// }
			String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>"; // 定义style的正则表达式{或<style>]*?>[\s\S]*?<\/style>
			// }
			String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式

			htmlStr = htmlStr.replaceAll("&amp;", "&");
			htmlStr = htmlStr.replaceAll("&lt;", "<");
			htmlStr = htmlStr.replaceAll("&gt;", ">");
			htmlStr = htmlStr.replaceAll("&nbsp;", "");
			htmlStr = htmlStr.replaceAll("&quot;", "");
			htmlStr = htmlStr.replaceAll("&emsp;", "");
			htmlStr = htmlStr.replaceAll("&emsp;", "");
			htmlStr = StringUtils.replace(htmlStr, "\n", "");
			htmlStr = htmlStr.replaceAll("<span>", "")
					.replaceAll("</span>", "");

			p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
			m_script = p_script.matcher(htmlStr);
			htmlStr = m_script.replaceAll(""); // 过滤script标签

			p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
			m_style = p_style.matcher(htmlStr);
			htmlStr = m_style.replaceAll(""); // 过滤style标签

			p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
			m_html = p_html.matcher(htmlStr);
			htmlStr = m_html.replaceAll(""); // 过滤html标签
			htmlStr = htmlStr.replaceAll("&.[0,4];", "");
			textStr = htmlStr;

		} catch (Exception e) {
			System.err.println("Html2Text: " + e.getMessage());
		}

		return textStr;
	}

	/**
	 * 知识内容截取存入简介
	 * @author 周仕奇
	 * @date 2016年1月14日 下午3:57:47
	 * @param htmlStr
	 * @return
	 */
	public static String htmlTotest(String htmlStr) {
		String content = null;
		if (StringUtils.isNotBlank(htmlStr)) {
			content = htmlStr.replaceAll("(?isu)<[^>]+>", "");
		}

		if (StringUtils.isNotBlank(content)) {
			content = content.length() > 50 ? content.substring(0, 50) + "..."
					: content;

		}
		return content;
	}

	public static void main(String[] args) {
		String str = "<html>"
				+ "<head><meta http-equiv='content-type' content='text/html;charset=utf-8'></head> "
				+ "<body> " + "<p>ddddddddd</p> " + "</body> " + "</html>";
		System.out.println(htmlTotest(str));
		// String strs[] = str.split(",");
		// for (int i = 0; i < strs.length; i++) {
		// String s = "'" + strs[i].trim() + "',";
		// System.out.println(s.trim());
		// }
		// System.out.println(strs.length);

	}
}
