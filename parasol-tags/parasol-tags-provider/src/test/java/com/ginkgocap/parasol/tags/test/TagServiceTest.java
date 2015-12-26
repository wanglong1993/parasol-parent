package com.ginkgocap.parasol.tags.test;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;

import com.ginkgocap.parasol.tags.exception.TagServiceException;
import com.ginkgocap.parasol.tags.exception.TagSourceServiceException;
import com.ginkgocap.parasol.tags.model.Tag;
import com.ginkgocap.parasol.tags.model.TagSource;
import com.ginkgocap.parasol.tags.service.TagService;
import com.ginkgocap.parasol.tags.service.TagSourceService;

import junit.framework.Test;
import junit.framework.TestResult;

public class TagServiceTest extends TestBase implements Test {
	private static long System_AppId = 1;
	private static Long userId = 111l;
	private static int tagType = 1; //表示是什么类型，比如万能插座中的知识、事物等

	private static Long sourceId = 1l; //资源的Id
	private static int sourceType =1;  //资源的类型

	@Autowired
	private TagService tagService;


	@Autowired
	private TagSourceService tagSourceService;
	
	
	
	@Override
	public int countTestCases() {
		return 0;
	}

	@Override
	public void run(TestResult result) {

	}

	/**
	 * getDirectoryTypeByName getDirectorysForRoot getDirectorysByParentId
	 * createDirectoryForChildren createDirectoryForRoot
	 * @throws TagServiceException 
	 * @throws TagSourceServiceException 
	 * 
	 * @throws DirectoryTypeServiceException
	 */
	@org.junit.Test
	public void testTags() throws TagServiceException, TagSourceServiceException {
		//查询Tag列表
		List<Tag> tags = tagService.getTagsByUserIdAppidTagType(userId, System_AppId, tagType);
		//删除存在的
		if (CollectionUtils.isNotEmpty(tags)) {
			for (Tag tag : tags) {
				tagService.removeTag(userId, tag.getId());
			}
		}
		
		//检查是否删除成功
		tags = tagService.getTagsByUserIdAppidTagType(userId, System_AppId, tagType);
		Assert.assertTrue(CollectionUtils.isEmpty(tags));
		
		//创建新的Tag
		Tag tag = new Tag();
		tag.setAppId(System_AppId);
		tag.setUserId(userId);
		tag.setTagType(tagType);
		tag.setTagName("测试Tag");
		Long tagId = tagService.createTag(userId, tag);
		Assert.assertTrue(tagId != null && tagId > 0);
		
		//把新创建的Tag 添加到一个资源上
		TagSource source = new TagSource();
		source.setAppId(System_AppId);
		source.setSourceId(sourceId);
		source.setSourceType(sourceType);
		source.setTagId(tagId);
		source.setUserId(userId);
		source.setCreateAt(System.currentTimeMillis());

		Long tagSourceId = tagSourceService.createTagSource(source);
		Assert.assertTrue(tagId != null && tagId > 0);

		
		//添加到第二个资源上
		source = new TagSource();
		source.setAppId(System_AppId);
		source.setSourceId(sourceId+1);
		source.setSourceType(sourceType);
		source.setTagId(tagId);
		source.setUserId(userId);
		source.setCreateAt(System.currentTimeMillis());
		Long tagSourceId2 = tagSourceService.createTagSource(source);
		//删除第二个资源上的Tag
		tagSourceService.removeTagSource(System_AppId, tagSourceId2);
		//检查第二个资源是否存在
		source = tagSourceService.getTagSource(System_AppId, tagSourceId2);
		Assert.assertNull(source);

		
		
		//删除Tag
		tagService.removeTag(userId, tagId);
		//检查Tag是否存在。
		tag = tagService.getTag(userId, tagId);
		Assert.assertNull(tag);
		//检查资源下的Tag是否删除掉
		source = tagSourceService.getTagSource(System_AppId, tagSourceId);
		Assert.assertNull(tag);
		
	}
}
