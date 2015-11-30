package com.ginkgocap.parasol.sensitive.sw.process;

import java.util.List;
import java.util.Map;

import com.ginkgocap.parasol.sensitive.model.Word;
import com.ginkgocap.parasol.util.sw.format.AbstractFormat;


/**
 * 
* <p>Title: AbstractProcessor.java<／p> 
* <p>Description: <／p> 

* @author fuliwen 
* @date 2015-11-26 
* @version 1.0
 */
public abstract class AbstractProcessor {
	/**
	 * 返回高亮文本
	 * @param keysMap
	 * @param text
	 * @param format
	 * @param wordLeastLen
	 * @param matchType
	 * @return
	 */
	abstract Object process(Map keysMap, String text, AbstractFormat format, int wordLeastLen, int matchType);
	/**
	 * 返回敏感词集合
	 * @param keysMap
	 * @param text
	 * @param wordLeastLen
	 * @param matchType
	 * @return
	 */
	abstract List<String> processWord(Map keysMap, String text,  int wordLeastLen);
	/**
	 * 返回过滤后的文本
	 * @param keysMap
	 * @param text
	 * @param format
	 * @param wordLeastLen
	 * @param matchType
	 * @return
	 */
	abstract Object processReplaceWord(Map keysMap, String text, int wordLeastLen);
	/**
	 * 检查文本中从begin位置开始是否存在关键字，存在则返回其长度
	 * @param matchType 
	 * 		 	匹配模式
	 * @return
	 */
	public Word checkKeyWords(Map keysMap, String text, int begin, int matchType) {
		StringBuilder wordStr = new StringBuilder();
		Word word = new Word();
		Map checkHash = keysMap;
		int maxMatchNum = 0;
		int matchNum = 0;
		for(int i = begin; i<text.length(); i++){
			char w = text.charAt(i);
			Object keyWordHash = checkHash.get(w);
			if(keyWordHash != null){
				matchNum++;
				checkHash = (Map)keyWordHash;
				wordStr.append(w);
				if(((String)checkHash.get("isEnd")).equals("1")){//如果有isEnd并且是1表示这个字是本关键词的结尾
					if(matchType == 1){
						word.setwLength(matchNum);
						word.setWord(wordStr.toString());
						return word;
					}else{
						maxMatchNum = matchNum;
					}
				}
			}else{
				word.setwLength(maxMatchNum);
				word.setWord(wordStr.toString());
				return word;
			}
		}
		word.setwLength(maxMatchNum);
		word.setWord(wordStr.toString());
		return word;
	}
}
