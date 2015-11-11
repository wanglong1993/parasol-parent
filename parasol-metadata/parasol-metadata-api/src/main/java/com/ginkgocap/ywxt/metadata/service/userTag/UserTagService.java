package com.ginkgocap.ywxt.metadata.service.userTag;

import java.util.List;
import java.util.Map;

import com.ginkgocap.ywxt.metadata.model.UserTag;

public interface UserTagService {
	/**
	 * 通过主键获得标签
	 * .//phoenix-demand/phoenix-demand-provider/src/main/java/com/ginkgocap/ywxt/demand/service/impl/DemandServiceImpl.java
	 * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/controller/tag/UserTagController.java
	 * @param id
	 * @return
	 */
	public UserTag selectById(long id);


	/**
	 * 插入标签
	 * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/controller/knowledge/TagKnowledgeController.java
     * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/controller/organ/OrganIndustryController.java
     * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/controller/organ/OrganPeerController.java
     * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/service/organ/impl/OrganTagServiceImpl.java
     * .//phoenix-demand/phoenix-demand-provider/src/main/java/com/ginkgocap/ywxt/demand/service/impl/UserTagServiceImpl.java
     * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/controller/tag/UserTagController.java
	 * @param UserTag
	 * @return
	 */
	public UserTag insert(UserTag UserTag);
	
	/**
	 * 插入多个标签
     * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/controller/tag/CommonTagController.java
	 * @param UserTag
	 * @return
	 */
	public boolean insert(List<UserTag> userTagList);

	/**
	 * 修改标签
	 * .//phoenix-demand/phoenix-demand-provider/src/main/java/com/ginkgocap/ywxt/demand/service/impl/UserTagServiceImpl.java
	 * @param UserTag
	 */
	public void update(UserTag UserTag);

	/**
	 * 删除标签
	 * .//phoenix-demand/phoenix-demand-provider/src/main/java/com/ginkgocap/ywxt/demand/service/impl/UserTagServiceImpl.java:145
	 * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/controller/tag/UserTagController.java:162
	 * @param id
	 */
	public void delete(long id);

	/**
	 * 根据标签名和用户Id删除
	 * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/controller/tag/UserTagController.java
	 * @param userId
	 * @param tagName
	 */
	public void deleteByTagNameAndUserId(long userId, String tagName);

	/**
	 * 根据名称和用户id查询
	 * 
	 * @param userId
	 *            用户id
	 * @param tagName
	 *            标签名称
	 * @return
	 */
	@Deprecated
	public List<UserTag> selectByNameAndUserId(long userId, String tagName);

	/**
	 * 根据用户id查询分页
	 * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/controller/knowledge/TagKnowledgeController.java
     * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/controller/tag/CommonTagController.java
     * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/cloud/controller/KnowledgeController.java
     * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/controller/tag/UserTagController.java
	 * @param userId
	 *            用户id
	 * @param tagName
	 *            标签名称
	 * @return
	 */
	
	public List<UserTag> selectPageByUserId(long userId, long startRow, int pageSize);
	
	/**
	 * 根据用户id查询总数
	 * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/controller/knowledge/TagKnowledgeController.java
     * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/controller/tag/CommonTagController.java
     * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/cloud/controller/KnowledgeController.java
     * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/controller/tag/UserTagController.java
	 * @param userId
	 *            用户id
	 * @return
	 */
	public long countByUserId(long userId);

	/**
	 * 根据用户id获得系统和用户的标签列表分页
	 * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/controller/tag/CommonTagController.java
	 * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/cloud/controller/KnowledgeController.java
	 * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/controller/tag/UserTagController.java
	 * @param userId
	 * @param startRow
	 * @param pageSize
	 * @return
	 */
	public List<UserTag> selectPageByUserIdAndSys(long userId, long startRow, int pageSize);
	
	/**
	 * 根据用户id获得系统标签总数
	 * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/controller/tag/CommonTagController.java
	 * @param userId
	 */
	public long countBySys();
	/**
	 * 获得系统标签列表分页
	 * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/controller/tag/CommonTagController.java
	 * @param userId
	 */
	public  List<UserTag> selectPageBySys(long startRow, int pageSize);
	
	/**
	 * 根据用户id获得系统和用户的标签总数
	 * .//jtmobileserver/src/main/java/com/ginkgocap/ywxt/controller/tag/CommonTagController.java
     * .//phoenix-admin/src/main/java/com/ginkgocap/ywxt/cloud/controller/KnowledgeController.java
     * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/controller/tag/UserTagController.java
	 * @param userId
	 */
	public long countByUserIdAndSys(long userId);
	/**
	 * 在用户标签和系统标签中，根据标签名查询
	 * @param userId
	 * @param tagName
	 * @return
	 */
	@Deprecated
	public List<UserTag> selectTagNameInUserIdAndSys(long userId,String tagName);
	/**
	 * 搜索前缀
	 * .//phoenix-web/src/main/java/com/ginkgocap/ywxt/controller/tag/UserTagController.java
	 * @param userId
	 * @param keyword
	 * @param startRow
	 * @param pageSize
	 * @return
	 */
	public List<UserTag> searchPrefixPageByUserIdAndSys(long userId,String[] keyword, long startRow, int pageSize);
	/**
	 * 搜索前后包含
	 * @param userId
	 * @param keyword
	 * @param startRow
	 * @param pageSize
	 * @return
	 * 
	 */
	@Deprecated
	public List<UserTag> searchContainPageByUserIdAndSys(long userId,String[] keyword, long startRow, int pageSize);

}
