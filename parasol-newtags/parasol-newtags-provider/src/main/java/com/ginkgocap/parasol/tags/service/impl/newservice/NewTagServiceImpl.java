package com.ginkgocap.parasol.tags.service.impl.newservice;

import com.ginkgocap.parasol.tags.mapper.TagDao;
import com.ginkgocap.parasol.tags.mapper.TagSourcesDao;
import com.ginkgocap.parasol.tags.model.Tag;
import com.ginkgocap.parasol.tags.model.TagSearchVO;
import com.ginkgocap.parasol.tags.model.TagSource;
import com.ginkgocap.parasol.tags.service.NewTagService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("newTagService")
class NewTagServiceImpl implements NewTagService {
	private static Logger logger = LoggerFactory.getLogger(NewTagServiceImpl.class);

    @Autowired
	private TagDao tagDao;
	@Autowired
	private TagSourcesDao tagSourcesDao;

	@Override
	public List<TagSearchVO> selectTagListByKeword(long userId, String keyword, int sourceType, int index, int size) {
		List<TagSearchVO> tagSearchVOs = new ArrayList<TagSearchVO>();
		List<Tag> tags = tagDao.selectTagListByKeword(userId, keyword, index, size);
		if(tags!=null && tags.size()>0){
			ListIterator<Tag> tagListIterator = tags.listIterator();
			while (tagListIterator.hasNext()){
				Tag next = tagListIterator.next();
				TagSearchVO tagSearchVO = new TagSearchVO();
				tagSearchVO.setId(next.getId());
				tagSearchVO.setUserId(next.getUserId());
				tagSearchVO.setFirstName(next.getFirstIndex().substring(0,1));
				tagSearchVO.setTagName(next.getTagName());
				if(sourceType==0){
					tagSearchVO.setPersonSourceCount(tagSourcesDao.countSourceByTagId(userId,next.getId(),2,""));
					tagSearchVO.setCustomerSourceCount(tagSourcesDao.countSourceByTagId(userId,next.getId(),3,""));
					tagSearchVO.setKnowledgeSourceCount(tagSourcesDao.countSourceByTagId(userId,next.getId(),8,""));
					tagSearchVO.setDemandSourceCount(tagSourcesDao.countSourceByTagId(userId,next.getId(),7,""));
				}else if(sourceType==2){
					tagSearchVO.setPersonSourceCount(tagSourcesDao.countSourceByTagId(userId,next.getId(),2,""));
				}else if(sourceType==3){
					tagSearchVO.setCustomerSourceCount(tagSourcesDao.countSourceByTagId(userId,next.getId(),3,""));
				}else if(sourceType==7){
					tagSearchVO.setDemandSourceCount(tagSourcesDao.countSourceByTagId(userId,next.getId(),7,""));
				}else if(sourceType==8){
					tagSearchVO.setKnowledgeSourceCount(tagSourcesDao.countSourceByTagId(userId,next.getId(),8,""));
				}
				tagSearchVOs.add(tagSearchVO);
			}
		}
		return tagSearchVOs;
	}

	@Override
	public long countTagListByKeword(long userId, String keyword) {
		return tagDao.countTagListByKeword(userId,keyword);
	}

	@Override
	public List<Tag> selectTagBySourceId(long userId, long sourceId, long sourceType) {
		return null;
	}
}
