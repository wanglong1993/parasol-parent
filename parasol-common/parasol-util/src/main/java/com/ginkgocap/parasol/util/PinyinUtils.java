package com.ginkgocap.parasol.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 将单个字符转换成拼音
 * 
 * @author clive
 * @param src
 * @return
 * 
 * 
 */
public class PinyinUtils {

	public static String charToPinyin(final char src,
			final boolean isPolyphone, final String separator) {
		// 创建汉语拼音处理类
		final HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		// 输出设置，大小写，音标方式
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);

		final StringBuffer tempPinying = new StringBuffer();
		//

		// 如果是中文
		if (src > 128) {
			try {
				// 转换得出结果
				final String[] strs = PinyinHelper.toHanyuPinyinStringArray(
						src, defaultFormat);

				// 是否查出多音字，默认是查出多音字的第一个字符
				if (isPolyphone && null != separator) {
					for (int i = 0; i < strs.length; i++) {
						tempPinying.append(strs[i]);
						if (strs.length != i + 1) {
							// 多音字之间用特殊符号间隔起来
							tempPinying.append(separator);
						}
					}
				} else {
					tempPinying.append(strs!=null?strs[0]:"");
				}

			} catch (final BadHanyuPinyinOutputFormatCombination e) {
				e.printStackTrace();
			}
		} else {
			tempPinying.append(src);
		}

		return tempPinying.toString();

	}

	/**
	 * 取汉字的首字母
	 * 
	 * @param src
	 * @param isCapital
	 *            是否是大写
	 * @return
	 */
	public static char[] getHeadByChar(final char src, final boolean isCapital) {
		// 如果不是汉字直接返回
		if (src <= 128) {
			return new char[] { src };
		}
		// 获取所有的拼音
		final String[] pinyingStr = PinyinHelper.toHanyuPinyinStringArray(src);
		// 创建返回对象
		final int polyphoneSize =pinyingStr!=null?pinyingStr.length:0;
		final char[] headChars = new char[polyphoneSize];
		int i = 0;
		if(pinyingStr!=null && pinyingStr.length>0){
		// 截取首字符
		for (final String s : pinyingStr) {
			final char headChar = s.charAt(0);
			// 首字母是否大写，默认是小写
			if (isCapital) {
				headChars[i] = Character.toUpperCase(headChar);
			} else {
				headChars[i] = headChar;
			}
			i++;
		}
	   }
		return headChars;
	}

	/**
	 * 取汉字的首字母(默认是大写)
	 * 
	 * @param src
	 * @return
	 */
	public static char getHeadByChar(final char src) {
		return getHeadByChar(src, false)!=null&&getHeadByChar(src, false).length>0?getHeadByChar(src, false)[0]:0;
	}

	/**
	 * 取一个汉语字符串的全拼
	 * 
	 */
	public static String stringToQuanPin(String src) {
		char[] chars = src.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : chars) {
			sb.append(charToPinyin(c, false, null));
		}
		return sb.toString();
	}

	/**
	 * 取汉字字符串的每个汉字首字符
	 */
	public static String stringToHeads(String src) {
		char[] chars = src.toCharArray();
		StringBuilder sb = new StringBuilder();
		for (char c : chars) {
			sb.append(getHeadByChar(c));
		}
		return sb.toString();
	}
}
