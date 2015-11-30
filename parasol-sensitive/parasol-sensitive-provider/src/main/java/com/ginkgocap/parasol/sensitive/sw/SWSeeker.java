package com.ginkgocap.parasol.sensitive.sw;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

import com.ginkgocap.parasol.sensitive.sw.process.DataInit;
import com.ginkgocap.parasol.sensitive.sw.process.Highlight;
import com.ginkgocap.parasol.util.sw.format.AbstractFormat;
import com.ginkgocap.parasol.util.sw.format.HTMLFormat;

/**
 * 
* <p>Title: SWSeeker.java<／p> 
* <p>Description: <／p> 

* @author fuliwen 
* @date 2015-11-26 
* @version 1.0
 */
@SuppressWarnings({ "rawtypes" })
@Component
public class SWSeeker {
	
	private Map<String, Map> keysMap = new ConcurrentHashMap<String, Map>();
	
	//需要过滤的词最短长度,默认0
	private int wordLeastLen = 0;
	
	//匹配方式，1为最小匹配（在文本中匹配到一个即可） 2为最大匹配（文本中所有关键词均匹配）
	private int matchType = 1;
	
	/**
	 * 先调用此方法判断是否可用
	 * @return 是否准备好
	 */
	public boolean isReady(){
		if(keysMap == null){
			keysMap = new ConcurrentHashMap<String, Map>();
		}
		if(keysMap.size() > 0){
			return true;
		}else{
			return false;
		}
	}
	
	public void initData(List<String> swwords) {
		this.addKeyWord(swwords.toArray(new String[]{}));
	}
	
	public void initData(String... swwords) {
		this.addKeyWord(swwords);
	}
	
	private void addKeyWord(String... swwords){
		if(swwords != null && swwords.length > 0){
			initWords(swwords);
		}
	}
	
	/**
	 * 初始化关键词hash tree
	 * @param swwords
	 */
	private void initWords(String... swwords){
		new DataInit().generalHash(swwords, keysMap);
	}
	
	/**
	 * 高亮文本中的关键词
	 * @param txt
	 * @return
	 */
	public String highlight(String text, int matchType){
		return highlight(text, matchType, new HTMLFormat("<font color='red'>", "</font>"));
	}
	
	/**
	 * 使用指定字符串高了文本中关键词
	 * @param text
	 * @param fragment
	 * @return
	 */
	public String highlight(String text, int matchType, AbstractFormat formate) {
		return new Highlight().process(keysMap, text, formate, wordLeastLen, matchType);
	}

	public List<String> sensitiveWord(String text){
		return new Highlight().processWord(keysMap, text, wordLeastLen);
	}
	
	public String replaceSensitive(String text){
		return new Highlight().processReplaceWord(keysMap, text, wordLeastLen);
	}
	
	public static void main(String[] args){
		String[] keyWords = new String[]{"关键字11","关键字1","过滤"};
		String test = "这是一段测试文本，在这个文本中含有的关键字文本关键字11关键字1没有其他关键字";
		SWSeeker sw = new SWSeeker();
		if(!sw.isReady()){
			sw.initData(keyWords);
			String sr = sw.highlight(test, 2);
			System.out.println(sr);
			List<String> list = sw.sensitiveWord(test);
			for(String s:list){
				System.out.println(s);
			}
		}else{
			String sr = sw.highlight(test, 2);
		}
	}

}
