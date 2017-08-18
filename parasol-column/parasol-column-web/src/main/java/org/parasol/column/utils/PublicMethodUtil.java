package org.parasol.column.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 公用方法
 * 
 * @author bianzhiwei
 * 
 */
public class PublicMethodUtil {
	/**
	 * 返回格式化后的时间
	 * 
	 * @param createDate
	 * @return
	 */
	public final static String getFormatDate(String createDate) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date begin;
		String retimr = "";
		try {
			begin = df.parse(createDate);
			Date end = new Date();
			long between = (end.getTime() - begin.getTime()) / 1000;
			int day = (int) between / (24 * 3600);
			int hour = (int) (between / 3600);
			int minute = (int) between / 60;
			if (day > 10) {
				retimr = createDate.substring(0, 10);
			} else if (day >= 1) {
				retimr = day + "天前";
			} else if (hour >= 1) {
				retimr = hour + "小时前";
			} else {
				retimr = minute + "分前";
			}
		} catch (Exception e) {
			System.err.print("格式化时间错误");
		}
		return retimr;
	}

	/**
	 * 截取字符串
	 * 
	 * @param cutStr
	 * @param length
	 * @return
	 */
	public static String cutString(String cutStr, int length) {
		int cutcount = 0;
		String achar = cutStr;
		char[] bchar = achar.toCharArray();
		StringBuffer cuttitle = new StringBuffer();
		if (achar.getBytes().length > length) {
			for (char c : bchar) {
				if (cutcount >= length - 2) {// 截取指定长度-2
					cuttitle.append("...");
					break;
				}
				if ((c + "").getBytes().length == 2) {
					cutcount++;
				}
				cutcount++;
				cuttitle.append(c);
			}
		} else {
			cuttitle = new StringBuffer(achar);// 不截取
		}
		return cuttitle.toString();
	}

	/**
	 * 获取当期时间
	 * 
	 * @return
	 */
	public final static String getNowDate() {
		String temp_str = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		temp_str = sdf.format(dt);
		return temp_str;
	}

	/**
	 * 获取当前日期
	 * 
	 * @return 当前日期字符串
	 *         <p>
	 *         格式<code>yyyy-MM-dd</code>
	 * @author libaofan 2013-9-3
	 */
	public final static String getCurrentDate() {
		String temp_str = "";
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		temp_str = sdf.format(dt);
		return temp_str;
	}
	/**
	 * 计算字符串长度，中文算2个，其它算1个
	 * @param value 目标字符串
	 * @return 字符串数量
	 * @author libaofan 2013-9-10
	 */
	public static int len(String value) {
		int valueLength = 0;
		String chinese = "[\u4e00-\u9fa5]";
		for (int i = 0; i < value.length(); i++) {
			String temp = value.substring(i, i + 1);
			if (temp.matches(chinese)) {
				valueLength += 2;
			} else {
				valueLength += 1;
			}
		}
		return valueLength;
	 }
}
