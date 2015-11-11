package com.ginkgocap.ywxt.metadata.service.code;

import java.util.List;
import java.util.Map;

import com.ginkgocap.ywxt.metadata.form.DataGridModel;
import com.ginkgocap.ywxt.metadata.model.Code;
import com.ginkgocap.ywxt.metadata.model.CodeRegion;

public interface CodeService {
	/**
	 * 通过主键获得类型
	 * 
	 * @param id
	 * @return
	 */
	Code selectByPrimarKey(long id);

	/**
	 * 通过类型组合类型级别获取类型
	 * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/controller/LoginController.java
	 * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/init/InitDynamicInvoke.java
	 * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/metadata/controller/MetadataController.java
	 * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/service/login/impl/BasesLoginService.java
	 * 
	 * .//phoenix-knowledge-web/src/main/java/com/ginkgocap/ywxt/controller/knowledge/KnowledgeCaseController.java
	 * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/controller/IncTagController.java
	 * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/service/impl/HotProvinceImpl.java
	 * 
	 * @param group
	 * @param level
	 * @return
	 */
	List<Code> selectByGroupAndLevel(String group, int level);

	/**
	 * 插入类型
	 * 
	 * @param code
	 * @return
	 */
	Code insert(Code code);

	/**
	 * 修改类型
	 * 
	 * @param code
	 */
	void update(Code code);

	/**
	 * 删除类型
	 * 
	 * @param id
	 */
	void delete(long id);

	/**
	 * 根据条件查询
	 * 使用者：
	 * .//phoenix-knowledge-web/src/main/java/com/ginkgocap/ywxt/controller/knowledge/KnowledgeCaseController.java
	 * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/controller/requirement/RequirementController.java
	 * @param root
	 *            类型 区分1投资2融资3专家4行业
	 * @param keywords
	 *            关键字 可匹配全拼音简拼音汉字等
	 * @param useType
	 *            作用类型 0系统，1自定义,查询全部为-1
	 * @param startRow
	 *            分页开始行
	 * @param pageSize
	 *            行大小
	 * @return 查询结果 size=0 if no result
	 */
	List<Code> selectByParams(int root, String keywords, int useType,
			long startRow, int pageSize);

	/**
	 * 根据名称查询
	 * 
	 * @param name
	 *            名称
	 * @return 查询结果 size=0 if no result
	 */
	@Deprecated
	public List<Code> selectByName(String name);

	/**
	 * 根据名称查询
	 * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/controller/member/MemberController.java
	 * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/corporation/controller/CorporationController.java
	 * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/personalcustomer/controller/PersonalCustomerController.java
	 * @param name
	 *            名称
	 * @param root
	 *            1投资2融资3专家4行业
	 * @return 查询结果 size=0 if no result
	 */
	public List<Code> selectByName(String name, int root);

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	@Deprecated
	public List<Code> selectAll();

	/**
	 * 根据参数查询
	 * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/code/controller/CodeController.java
	 * @return
	 */
	public List<Map> findByMap(Map<String, Object> map);

	/***
     * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/code/controller/CodeController.java
 	 * @param name
	 *            名称
	 * @param createBy
	 *            创建人
	 * @param sysItem
	 *            自定义
	 * @param startTimer
	 *            开始时间
	 * @param endTimer
	 *            结束时间
	 * @param type
	 *            类型
	 * @param dgm
	 *            分页对象
	 * @return
	 */
	Map<String, Object> selectByParam(String name, String createBy,
			String sysItem, String startTimer, String endTimer, String type,
			DataGridModel dgm);

	/**
	 * 查询子节点的分词信息
	 * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/metadata/controller/MetadataController.java
	 * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/requirement/controller/CatalogController.java
	 * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/controller/IncTagController.java
	 * @param id
	 * @return List<Code>
	 */
	public List<Code> selectChildNodeById(long id);

	/**
	 * 查询兄弟节点的分词信息
	 * 
	 * @param id
	 * @return List<Code>
	 */
	@Deprecated
	public List<Code> selectBrotherNodeById(long id);

	/**
	 * 分页查询行业
	 * 
	 * @param useType
	 *            作用类型 0 系统类型，1 自定义类型 -1 全部
	 * @param industry
	 *            0 不是行业 1 是行业 -1 全部
	 * @param level
	 *            1 第一级 行业具有层级 -1 全部级别
	 * @param PageIndex
	 *            第几页
	 * @param pageSize
	 *            每页大小
	 * @return
	 */
	@Deprecated
	public Map<String, Object> selectCodeByPageUtil(int useType, int industry,
			int level, int PageIndex, int pageSize);

	/**
	 * 查询区域信息
	 * 
	 * @param pid
	 *            父ID
	 * @return
	 * @author haiyan
	 */
	@Deprecated
	public List<CodeRegion> selectAreaList(Long pid);
	
	/**
	 * 功能描述:查询目录树
	 * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/requirement/controller/CatalogController.java
	 * @return list
	 * @author yanweiqi
	 */
	public Map<String, Object> findMenuTree(int page,int pagesize);
	/**
	 * 获得最大id值
	 * @return long
	 * @author liny
	 */
	@Deprecated
	public Long getCataLogMaxId();
}
