package com.ginkgocap.parasol.tags.service.impl.newservice;


import com.ginkgocap.parasol.tags.mapper.TagDao;
import com.ginkgocap.parasol.tags.mapper.TagSourcesDao;
import com.ginkgocap.parasol.tags.model.Tag;
import com.ginkgocap.parasol.tags.model.TagSource;
import com.ginkgocap.parasol.tags.model.SourceSearchVO;
import com.ginkgocap.parasol.tags.service.NewTagSourceService;
import com.ginkgocap.parasol.tags.utils.GetId;
import net.sf.json.util.JSONUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("newTagSourceService")
public class NewTagSourcesServiceImpl implements NewTagSourceService {

	private static Logger logger = LoggerFactory.getLogger(NewTagSourcesServiceImpl.class);

	@Autowired
	private TagSourcesDao tagSourcesDao;

	@Autowired
	private TagDao tagDao;


	/**
	 * 查询符合条件的资源
	 * @param userId
	 * @param tagId
	 * @param keyWord
	 * @param sourceType
	 * @param index
	 * @param size
	 * @return
	 */
	@Override
	public List<SourceSearchVO> searchTagSources(long userId, long tagId, String keyWord, int sourceType, int index, int size) {
		logger.info("查询资源查询列表参数：userId="+ userId+"**tagId="+tagId+"**keyWord"+keyWord+"**sourceType="+sourceType+"**index="+index+"**size="+size);
		List<SourceSearchVO> sourceSearchVOList = new ArrayList<SourceSearchVO>();
		if(StringUtils.isEmpty(keyWord)){
			List<TagSource> tagSourceList= tagSourcesDao.selectSourceByTagId(userId,tagId,sourceType,keyWord,index*size,size,null);
			if(tagSourceList!=null && tagSourceList.size()>0){
				ListIterator<TagSource> tagSourceListIterator = tagSourceList.listIterator();
				while (tagSourceListIterator.hasNext()){
					TagSource next = tagSourceListIterator.next();
					SourceSearchVO tagSourceSearchVO = new SourceSearchVO();
					tagSourceSearchVO.setCreateAt(next.getCreateAt());
					tagSourceSearchVO.setSourceExtra(next.getSourceExtra());
					tagSourceSearchVO.setSourceId(next.getSourceId());
					tagSourceSearchVO.setSourceTitle(next.getSourceTitle());
					tagSourceSearchVO.setSourceType(next.getSourceType());
					List<Tag> tags = tagDao.selectTagBySourceId(userId, next.getSourceId(), next.getSourceType());
					tagSourceSearchVO.setSourceTagList(tags);
					tagSourceSearchVO.setCreateUserId(next.getUserId());
					tagSourceSearchVO.setSourceColumnType(next.getSourceColumnType());
					tagSourceSearchVO.setSupDem(next.getSupDem());
					tagSourceSearchVO.setId(next.getId());
					sourceSearchVOList.add(tagSourceSearchVO);
				}
			}
		}else{
			List<Long> sourceIdList = tagSourcesDao.searchSourceByTagIdAndtagName(userId, tagId, sourceType, keyWord);
			logger.info("符合标签的资源id列表：sourceIdList="+JSONUtils.valueToString(sourceIdList));
			logger.info("执行前：index="+index+"size="+size+"index * size="+ index * size);
			List<TagSource> tagSourceList = tagSourcesDao.selectSourceByTagId(userId, tagId, sourceType, keyWord, index * size, size , sourceIdList);
			logger.info("执行后：index="+index+"size="+size+"index * size="+ index * size);
			if(tagSourceList!=null && tagSourceList.size()>0){
				ListIterator<TagSource> tagSourceListIterator = tagSourceList.listIterator();
				while (tagSourceListIterator.hasNext()){
					TagSource next = tagSourceListIterator.next();
					logger.info("资源id="+next.getSourceId()+"****资源名称="+next.getSourceTitle());
					SourceSearchVO tagSourceSearchVO = new SourceSearchVO();
					tagSourceSearchVO.setCreateAt(next.getCreateAt());
					tagSourceSearchVO.setSourceExtra(next.getSourceExtra());
					tagSourceSearchVO.setSourceId(next.getSourceId());
					tagSourceSearchVO.setSourceTitle(next.getSourceTitle());
					tagSourceSearchVO.setSourceType(next.getSourceType());
					List<Tag> tags = tagDao.selectTagBySourceId(userId, next.getSourceId(), next.getSourceType());
					tagSourceSearchVO.setSourceTagList(tags);
					tagSourceSearchVO.setCreateUserId(next.getUserId());
					tagSourceSearchVO.setSourceColumnType(next.getSourceColumnType());
					tagSourceSearchVO.setSupDem(next.getSupDem());
					tagSourceSearchVO.setId(next.getId());
					sourceSearchVOList.add(tagSourceSearchVO);
				}
			}
		}
		return sourceSearchVOList;
	}

	@Override
	public boolean deleteSourceByType(long userId, long tagId, int sourceType,long sourceId) {
		logger.info("删除标签下的资源：userId="+userId+"**tagId"+tagId+"**sourceType"+sourceType);
		boolean flag = true;
		try {
			tagSourcesDao.deleteSourceByType(userId, tagId, sourceType,sourceId);
		}catch (Exception e){
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public long countSourceByTagId(long userId, long tagId, int sourceType, String keyword) {
		if(StringUtils.isEmpty(keyword)){
			return tagSourcesDao.countSourceByTagId(userId, tagId, sourceType, keyword,null);
		}else{
			List<Long> sourceIdList = tagSourcesDao.searchSourceByTagIdAndtagName(userId, tagId, sourceType, keyword);
			return tagSourcesDao.countSourceByTagId(userId, tagId, sourceType, keyword,sourceIdList);
		}
	}

	@Override
	public List<SourceSearchVO> searchSourcesExctTag(long userId, String keyWord,int sourceType) {
		List<SourceSearchVO> sourceSearchVOList = new ArrayList<SourceSearchVO>();
		List<TagSource> tagSourceList= tagSourcesDao.searchSourcesExctTag(userId,keyWord,sourceType);
		if(tagSourceList!=null && tagSourceList.size()>0){
			ListIterator<TagSource> tagSourceListIterator = tagSourceList.listIterator();
			while (tagSourceListIterator.hasNext()){
				TagSource next = tagSourceListIterator.next();
				SourceSearchVO tagSourceSearchVO = new SourceSearchVO();
				tagSourceSearchVO.setCreateAt(next.getCreateAt());
				tagSourceSearchVO.setSourceExtra(next.getSourceExtra());
				tagSourceSearchVO.setSourceId(next.getSourceId());
				tagSourceSearchVO.setSourceTitle(next.getSourceTitle());
				tagSourceSearchVO.setSourceType(next.getSourceType());
				List<Tag> tags = tagDao.selectTagBySourceId(userId, next.getSourceId(), next.getSourceType());
				tagSourceSearchVO.setSourceTagList(tags);
				tagSourceSearchVO.setSourceColumnType(next.getSourceColumnType());
				tagSourceSearchVO.setSupDem(next.getSupDem());
				tagSourceSearchVO.setId(next.getId());
				sourceSearchVOList.add(tagSourceSearchVO);
			}
		}
		return sourceSearchVOList;
	}

	@Override
	public boolean updateSourceByTagId(long userId, long tagId, int sourceType, List<TagSource> tagSourceList) {
		boolean flag=true;
		try {
			if (tagSourceList!=null && tagSourceList.size()>0){
				ListIterator<TagSource> tagSourceListIterator = tagSourceList.listIterator();
				while (tagSourceListIterator.hasNext()){
					TagSource next = tagSourceListIterator.next();
					if(next.getChosenTag()==1){
						tagSourcesDao.deleteSourceByType(userId,tagId,(int)next.getSourceType(),next.getSourceId());
						next.setUserId(userId);
						next.setTagId(tagId);
						next.setAppId(1);
						next.setCreateAt(System.currentTimeMillis());
						tagSourcesDao.insertSource(next);
					}else {
						tagSourcesDao.deleteSourceByType(userId,tagId,(int)next.getSourceType(),next.getSourceId());
					}
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public boolean updateTagsources(Long appId, Long userId, Long sourceId, Long sourceType, List<Long> tagIds, String sourceTitle, long columnType, int supDem, String sourceExtra) {
		try {
//			tagSourcesDao.deleteSourceByType(userId,0,Integer.valueOf(sourceType+""),sourceId);
			if(tagIds!=null && tagIds.size()>0){
				Iterator<Long> iterator = tagIds.iterator();
				while (iterator.hasNext()){
					Long next = iterator.next();
					TagSource tagSource = new TagSource();
					tagSource.setAppId(appId);
					tagSource.setSourceExtra(sourceExtra);
					tagSource.setSupDem(supDem);
					tagSource.setSourceColumnType(columnType);
					tagSource.setSourceTitle(sourceTitle);
					tagSource.setCreateAt(System.currentTimeMillis());
					tagSource.setTagId(next);
					tagSource.setUserId(userId);
					tagSource.setSourceId(sourceId);
					tagSource.setSourceType(sourceType);
					tagSourcesDao.insertSource(tagSource);
				}
			}
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<TagSource> getTagSourcesBySourceId(Long userId, Long sourceId, Long sourceType) {
		List<TagSource> tagSourcesBySourceId = tagSourcesDao.getTagSourcesBySourceId(userId, sourceId, sourceType);
		if(tagSourcesBySourceId!=null && tagSourcesBySourceId.size()>0){
			for (TagSource tagSource: tagSourcesBySourceId) {
				long tagId = tagSource.getTagId();
				Tag tagById = tagDao.getTagById(tagId);
				if(tagById!=null){
					tagSource.setTagName(tagById.getTagName());
				}
			}
		}
		return tagSourcesBySourceId;
	}
}
