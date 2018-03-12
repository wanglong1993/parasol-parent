package com.ginkgocap.parasol.tags.service.impl.newservice;


import com.ginkgocap.parasol.tags.mapper.TagDao;
import com.ginkgocap.parasol.tags.mapper.TagSourcesDao;
import com.ginkgocap.parasol.tags.model.Tag;
import com.ginkgocap.parasol.tags.model.TagSource;
import com.ginkgocap.parasol.tags.model.TagSourceSearchVO;
import com.ginkgocap.parasol.tags.service.NewTagSourceService;
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
	public List<TagSourceSearchVO> searchTagSources(long userId, long tagId,String keyWord, int sourceType, int index, int size) {
		List<TagSourceSearchVO> tagSourceSearchVOList = new ArrayList<TagSourceSearchVO>();
		List<TagSource> tagSourceList= tagSourcesDao.selectSourceByTagId(userId,tagId,sourceType,keyWord,index*size,size);
		if(tagSourceList!=null && tagSourceList.size()>0){
			ListIterator<TagSource> tagSourceListIterator = tagSourceList.listIterator();
			while (tagSourceListIterator.hasNext()){
				TagSource next = tagSourceListIterator.next();
				TagSourceSearchVO tagSourceSearchVO = new TagSourceSearchVO();
				tagSourceSearchVO.setCreateAt(next.getCreateAt());
				tagSourceSearchVO.setSourceExtra(next.getSourceExtra());
				tagSourceSearchVO.setSourceId(next.getSourceId());
				tagSourceSearchVO.setSourceTitle(next.getSourceTitle());
				tagSourceSearchVO.setSourceType(next.getSourceType());
				List<Tag> tags = tagDao.selectTagBySourceId(userId, next.getSourceId(), next.getSourceType());
				List<Tag> tagList=new ArrayList<Tag>();
				if(tags!=null && tags.size()>0){
					ListIterator<Tag> tagListIterator = tags.listIterator();
					while (tagListIterator.hasNext()){
						Tag next1 = tagListIterator.next();
						tagList.add(next1);
					}
				}
				tagSourceSearchVO.setCreateUserId(next.getUserId());
				tagSourceSearchVO.setSourceTagList(tagList);
				tagSourceSearchVOList.add(tagSourceSearchVO);
			}
		}
		return tagSourceSearchVOList;
	}

	@Override
	public boolean deleteSourceByType(long userId, long tagId, int sourceType) {
		logger.info("删除标签下的资源：userId="+userId+"**tagId"+tagId+"**sourceType"+sourceType);
		boolean flag = false;
		int i = tagSourcesDao.deleteSourceByType(userId, tagId, sourceType);
		if(i>0){
			flag=true;
		}
		return flag;
	}

	@Override
	public long countSourceByTagId(long userId, long tagId, int sourceType, String keyword) {
		return tagSourcesDao.countSourceByTagId(userId,tagId,sourceType,keyword);
	}
}
