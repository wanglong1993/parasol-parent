package com.ginkgocap.ywxt.metadata.service;

import java.util.List;
import java.util.Map;

import com.ginkgocap.ywxt.metadata.form.DataGridModel;
import com.ginkgocap.ywxt.metadata.model.SensitiveWord;
import com.ginkgocap.ywxt.util.sw.format.AbstractFormat;

/**
 * 敏感词接口
 * @author liubang
 * 2014年4月30日 11:34:00
 */
public interface SensitiveWordService {
	/**
	 * 按主键检索
	 * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/code/controller/CodeController.java
	 * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/sensitivity/controller/SensitivityTypeController.java
	 * @param id
	 * @return
	 */
	public SensitiveWord findOne(long id);
	/**
	 * 保存或者更新
	 * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/code/controller/CodeController.java:602
	 * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/sensitivity/controller/SensitivityTypeController.java:414
	 * @param sensitiveWord
	 * @return
	 */
	public SensitiveWord saveOrUpdate(SensitiveWord sensitiveWord);
	/**
	 * 带分页的检索
	 * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/code/controller/CodeController.java:573
	 * @param param
	 * @param dgm
	 * @return
	 */
	public Map<String, Object> findByParams(Map<String, Object> param,DataGridModel dgm);
	/**
	 * 批量删除
	 * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/code/controller/CodeController.java:658
	 * @param ids
	 * @return
	 */
	public int deleteByIds(Long[] ids);
	/**
	 * 查询重复数据
	 * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/code/controller/CodeController.java:685
	 * @param word
	 * @param id
	 * @return
	 */
	public List<SensitiveWord> check(String word,long id);
	/**
	 * 更新敏感词
	 * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/code/controller/CodeController.java:604:
	 * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/code/controller/CodeController.java:639:
	 * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/code/controller/CodeController.java:660:
	 * @return 0 失败 1成功
	 */
	public int updateWord();

	
	/**
	 * 高亮文本中的关键词
	 * @param text 文本
	 * @param matchType 匹配方式，1为最小匹配（在文本中匹配到一个即可） 2为最大匹配（文本中所有关键词均匹配）
	 * @return
	 */
	@Deprecated
	public String highlight(String text, int matchType);
	
	/**
	 * 使用指定字符串高了文本中关键词
	 * @param text 文本
	 * @param matchType 匹配方式，1为最小匹配（在文本中匹配到一个即可） 2为最大匹配（文本中所有关键词均匹配）
	 * @param formate  例如：new HTMLFormat("<font color='red'>", "</font>")
	 * @return
	 */
	@Deprecated
	public String highlight(String text, int matchType, AbstractFormat formate);
	/**
	 * 返回敏感词集合
	 * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/controller/demand/DemandController.java
     * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/controller/person/PersonEvaluateController.java
     * .//phoenix-demand/phoenix-demand-provider/src/main/java/com/ginkgocap/ywxt/demand/service/impl/UserCategoryServiceImpl.java
     * .//phoenix-demand/phoenix-demand-provider/src/main/java/com/ginkgocap/ywxt/demand/service/impl/UserTagServiceImpl.java
     * .//phoenix-knowledge-web/src/main/java/com/ginkgocap/ywxt/controller/ColumnController.java
     * .//phoenix-knowledge-web/src/main/java/com/ginkgocap/ywxt/controller/knowledge/KnowledgeManagerController.java
     * .//phoenix-knowledge/phoenix-knowledge-provider/src/main/java/com/ginkgocap/ywxt/knowledge/service/impl/KnowledgeDraftServiceImpl.java
     * .//phoenix-knowledge/phoenix-knowledge-provider/src/main/java/com/ginkgocap/ywxt/knowledge/service/impl/KnowledgeServiceImpl.java
     * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/controller/SensitiveWordController.java
	 * @param text
	 * @return
	 */
	public List<String> sensitiveWord(String text);
	/**
	 * 返回过滤了敏感词的文本
	 * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/controller/requirement/RequirementController.java
	 * @param text
	 * @return
	 */
	@Deprecated
	public String replaceSensitive(String text);
	
	@Deprecated
	public boolean batchInsertSelective(List<SensitiveWord> sensitiveWords);
}
