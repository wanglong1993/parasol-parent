package com.ginkgocap.parasol.sensitive.sw.process;

import java.util.HashMap;
import java.util.Map;

/**
 * 
* <p>Title: DataInit.java<／p> 
* <p>Description: <／p> 

* @author fuliwen 
* @date 2015-11-26 
* @version 1.0
 */
@SuppressWarnings({ "rawtypes" })
public class DataInit {
	
	/**
	 * 构造生成hash tree并返回敏感词中最短的词长度
	 * @param swwords 
	 *        关键字
	 * @param keysMap
	 * 		  聚合hash tree
	 * 
	 */
	public void generalHash(String[] swwords, Map<String, Map> keysMap){
		for(String word : swwords){
			Map hash = keysMap;
			for(int i = 0; i<word.length(); i++){
				char w = word.charAt(i);
				Object wordMap = hash.get(w);
				if(wordMap != null){
					hash = (Map)wordMap;
				}else{
					Map tempHash = new HashMap();
					tempHash.put("isEnd", "0");
					hash.put(w, tempHash);
					hash = tempHash;
				}
				if(i == word.length() - 1){
					hash.put("isEnd", "1");//1表示这个关键词tree结束
				}
			}
		}
	}
	
	/**
	 * 获取需要过滤的字符串最短长度
	 * @param swwords
	 * @return
	 */
	public int getWordLeastLen(String[] swwords){
		int len = 0;
		if(swwords == null || swwords.length == 0){
			return len;
		}
		for(String word : swwords){
			if(len == 0){
				len = word.length();
			}else if(word.length() < len){
				len = word.length();
			}
		}
		return len;
	}
	
}
