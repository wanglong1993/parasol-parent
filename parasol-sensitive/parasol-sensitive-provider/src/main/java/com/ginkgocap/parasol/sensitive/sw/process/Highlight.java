package com.ginkgocap.parasol.sensitive.sw.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.ginkgocap.parasol.sensitive.model.Word;
import com.ginkgocap.parasol.util.sw.format.AbstractFormat;
import com.ginkgocap.parasol.util.sw.format.HTMLFormat;

/**
 * 
* <p>Title: Highlight.java<／p> 
* <p>Description: <／p> 

* @author fuliwen 
* @date 2015-11-26 
* @version 1.0
 */
public class Highlight extends AbstractProcessor {

	/**
	 * 执行遍历匹配
	 * @param keysMap hash tree
	 * @param text 需要匹配的文本
	 * @param format 格式化风格
	 * @param wordLeastLen 文本最小长度
	 * @param matchType匹配类型 
	 * 			1：最小匹配 如果关键字有“中国”与“中国人” 那出现中国就匹配结束
	 * 			2：最大匹配如果关键字有“中国”与“中国人” 那匹配到中国人结束
	 */
	@Override
	public String process(Map keysMap, String text, AbstractFormat format, int wordLeastLen, int matchType) {
		StringBuffer result = new StringBuffer();
		if(keysMap == null || keysMap.isEmpty() || StringUtils.isEmpty(text)){
			return result.append(text).toString();
		}
		if(text.length() < wordLeastLen){
			return result.append(text).toString();
		}
		for(int i = 0; i<text.length();){
			Word word = checkKeyWords(keysMap, text, i, matchType);
			if(word.getwLength() > 0){
				result.append(format.format(word.getWord()));
				i += word.getwLength();
			}else{
				result.append(text.charAt(i));
				i++;
			}
		}
		return result.toString();
	}

	@Override
	public List<String> processWord(Map keysMap, String text, 
			int wordLeastLen) {
		List<String> list = new ArrayList<String>();
		if(keysMap == null || keysMap.isEmpty() || StringUtils.isEmpty(text)){
			return list;
		}
		if(text.length() < wordLeastLen){
			return list;
		}
		for(int i = 0; i<text.length();){
			Word word = checkKeyWords(keysMap, text, i, 2);
			if(word.getwLength() > 0){
				if(!list.contains(word.getWord())){
					list.add(word.getWord());
				}
				i += word.getwLength();
			}else{
				i++;
			}
		}
		return list;
	}

	@Override
	public String processReplaceWord(Map keysMap, String text, 
			int wordLeastLen) {
		AbstractFormat format = new HTMLFormat("<font color='red'>", "</font>");
		StringBuffer result = new StringBuffer();
		if(keysMap == null || keysMap.isEmpty() || StringUtils.isEmpty(text)){
			return result.append(text).toString();
		}
		if(text.length() < wordLeastLen){
			return result.append(text).toString();
		}
		for(int i = 0; i<text.length();){
			Word word = checkKeyWords(keysMap, text, i, 2);
			if(word.getwLength() > 0){
				result.append(format.format(word.getWord(),""));
				i += word.getwLength();
			}else{
				result.append(text.charAt(i));
				i++;
			}
		}
		return result.toString();
	}
}
