package com.ginkgocap.parasol.sensitive.service;

import java.io.Serializable;
import java.util.List;

import com.ginkgocap.parasol.sensitive.model.SensitiveWord;
import com.ginkgocap.parasol.util.sw.format.AbstractFormat;

/**
 * 
* <p>Title: SensitiveWordService.java<／p> 
* <p>Description: <／p> 

* @author fuliwen 
* @date 2015-11-26 
* @version 1.0
 */
public interface SensitiveWordService {
	/**
	 * 按主键检索
	 * @param id
	 * @return
	 */
	public SensitiveWord findOne(long id);
	/**
	 * 保存或者更新
	 * @param sensitiveWord
	 * @return
	 */
	public SensitiveWord saveOrUpdate(SensitiveWord sensitiveWord);
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	public boolean deleteByIds(List<Serializable> ids);
	
	/**
	 * 更新敏感词
	 * @return 0 失败 1成功
	 */
	public int updateWord();
	/**
	 * 高亮文本中的关键词
	 * @param text 文本
	 * @param matchType 匹配方式，1为最小匹配（在文本中匹配到一个即可） 2为最大匹配（文本中所有关键词均匹配）
	 * @return
	 */
	public String highlight(String text, int matchType);
	
	/**
	 * 使用指定字符串高了文本中关键词
	 * @param text 文本
	 * @param matchType 匹配方式，1为最小匹配（在文本中匹配到一个即可） 2为最大匹配（文本中所有关键词均匹配）
	 * @param formate  例如：new HTMLFormat("<font color='red'>", "</font>")
	 * @return
	 */
	public String highlight(String text, int matchType, AbstractFormat formate);
	/**
	 * 返回敏感词集合
	 * @param text
	 * @return
	 */
	public List<String> sensitiveWord(String text);
	/**
	 * 返回过滤了敏感词的文本
	 * @param text
	 * @return
	 */
	public String replaceSensitive(String text);
	
	public boolean batchInsertSelective(List<SensitiveWord> sensitiveWords);
}
