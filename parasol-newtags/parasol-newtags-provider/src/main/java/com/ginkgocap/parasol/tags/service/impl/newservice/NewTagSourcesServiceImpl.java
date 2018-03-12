package com.ginkgocap.parasol.tags.service.impl.newservice;


import com.ginkgocap.parasol.tags.mapper.TagDao;
import com.ginkgocap.parasol.tags.mapper.TagSourcesDao;
import com.ginkgocap.parasol.tags.model.Tag;
import com.ginkgocap.parasol.tags.model.TagSource;
import com.ginkgocap.parasol.tags.model.SourceSearchVO;
import com.ginkgocap.parasol.tags.service.NewTagSourceService;
import com.ginkgocap.parasol.tags.utils.GetId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;


@Service("newTagSourceService")
public class NewTagSourcesServiceImpl implements NewTagSourceService {

	private static Logger logger = LoggerFactory.getLogger(NewTagSourcesServiceImpl.class);

	@Autowired
	private TagSourcesDao tagSourcesDao;

	@Autowired
	private TagDao tagDao;


	@Override
	public List<SourceSearchVO> searchTagSources(long userId, long tagId, String keyWord, int sourceType, int index, int size) {
		List<SourceSearchVO> sourceSearchVOList = new ArrayList<SourceSearchVO>();
		List<TagSource> tagSourceList= tagSourcesDao.selectSourceByTagId(userId,tagId,sourceType,keyWord,index*size,size);
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
				sourceSearchVOList.add(tagSourceSearchVO);
			}
		}
		return sourceSearchVOList;
	}

	@Override
	public boolean deleteSourceByType(long userId, long tagId, int sourceType) {
		logger.info("删除标签下的资源：userId="+userId+"**tagId"+tagId+"**sourceType"+sourceType);
		boolean flag = true;
		try {
			tagSourcesDao.deleteSourceByType(userId, tagId, sourceType);
		}catch (Exception e){
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}

	@Override
	public long countSourceByTagId(long userId, long tagId, int sourceType, String keyword) {
		Long aLong = tagSourcesDao.countSourceByTagId(userId, tagId, sourceType, keyword);
		if(aLong==null){
			aLong=0l;
		}
		return aLong;
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
					next.setUserId(userId);
					next.setTagId(tagId);
					next.setAppId(1);
					next.setId(GetId.getId());
					next.setCreateAt(System.currentTimeMillis());
					tagSourcesDao.insertSource(next);
				}
			}
		}catch (Exception e){
			e.printStackTrace();
			flag=false;
		}
		return flag;
	}
}
