package com.ginkgocap.ywxt.metadata.service.suggestion;

import java.util.List;
import java.util.Map;

import com.ginkgocap.ywxt.metadata.form.DataGridModel;
import com.ginkgocap.ywxt.metadata.model.Dic;
import com.ginkgocap.ywxt.metadata.model.Suggestion;

/**
 * 意见反馈
 * @author lk
 * 创建时间： 2013-03-14 13:55:00
 */
public interface SuggestionService {
    /**
     * 根据反馈记录ID取到反馈记录
	 * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/suggestion/controller/SuggestionController.java:
     * @param id 名称
     * @return
     */
	Suggestion selectByPrimarKey(long id);
    /**
     * 存入新的反馈记录
     * .//phoenix-business-web/src/main/java/com/ginkgocap/ywxt/controller/inc/home/HomeController.java
	 * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/controller/suggestion/SuggestionController.java
     * @param suggestion
     * @return
     */
	Suggestion insert(Suggestion suggestion);
	
	/**
	 * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/controller/suggestion/SuggestionController.java:101
	 * @param suggestion
	 * @return
	 */
	Suggestion add(Suggestion suggestion);
    /**
     * 修改反馈记录
     * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/suggestion/controller/SuggestionController.java
     * @param suggestion
     */
	void update(Suggestion suggestion);
    /**
     * 通过反馈记录ID删除记录
     * @param id
     */
	@Deprecated
	void delete(long id);
	/**
     * 根据条件查询
     * @param id 反馈编号
     * @param user_name 反馈人登录名
     * @param problemTitle反馈标题
     * @param feedbackType反馈类型
     * @param startRow 分页开始行
     * @param pageSize 行大小
     * @return 查询结果 size=0 if no result
     */
	@Deprecated
    List<Suggestion> selectByParams(long id,String user_name,String problemTitle,String feedbackType,long startRow,int pageSize);
    /**
     * 返回查询列表
     * 并带分页信息
     */
	@Deprecated
    Map<String,Object> selectByParam(long id,String user_name,String problemTitle,String cstart,String cend,String fstart,String fend,String feedbackType, DataGridModel dgm);

	/**
	 * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/controller/suggestion/SuggestionController.java:49:
	 * 
	 * @return
	 */
	List<Dic> selectDics();
}
