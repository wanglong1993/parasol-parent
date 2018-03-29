package com.ginkgocap.parasol.tags.service.impl.newservice;

import com.ginkgocap.parasol.tags.mapper.TagDao;
import com.ginkgocap.parasol.tags.mapper.TagSourcesDao;
import com.ginkgocap.parasol.tags.model.*;
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
		List<Tag> tags = tagDao.selectTagListByKeword(userId, keyword, index*size, size);
		if(tags!=null && tags.size()>0){
			ListIterator<Tag> tagListIterator = tags.listIterator();
			while (tagListIterator.hasNext()){
				Tag next = tagListIterator.next();
				TagSearchVO tagSearchVO = new TagSearchVO();
				tagSearchVO.setId(next.getId());
				tagSearchVO.setUserId(next.getUserId());
				String firstIndex = next.getFirstIndex();
				if (StringUtils.isNotBlank(firstIndex)) {
					firstIndex = firstIndex.substring(0,1);
				}
				tagSearchVO.setFirstName(firstIndex);
				tagSearchVO.setTagName(next.getTagName());
				if(sourceType==0){
					tagSearchVO.setPersonSourceCount(tagSourcesDao.countSourceByTagId(userId,next.getId(),2,"",null));
					tagSearchVO.setCustomerSourceCount(tagSourcesDao.countSourceByTagId(userId,next.getId(),3,"",null));
					tagSearchVO.setKnowledgeSourceCount(tagSourcesDao.countSourceByTagId(userId,next.getId(),8,"",null));
					tagSearchVO.setDemandSourceCount(tagSourcesDao.countSourceByTagId(userId,next.getId(),7,"",null));
				}else if(sourceType==2){
					tagSearchVO.setPersonSourceCount(tagSourcesDao.countSourceByTagId(userId,next.getId(),2,"",null));
				}else if(sourceType==3){
					tagSearchVO.setCustomerSourceCount(tagSourcesDao.countSourceByTagId(userId,next.getId(),3,"",null));
				}else if(sourceType==7){
					tagSearchVO.setDemandSourceCount(tagSourcesDao.countSourceByTagId(userId,next.getId(),7,"",null));
				}else if(sourceType==8){
					tagSearchVO.setKnowledgeSourceCount(tagSourcesDao.countSourceByTagId(userId,next.getId(),8,"",null));
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
		return tagDao.selectTagBySourceId(userId,sourceId,sourceType);
	}

	@Override
	public List<TagSourceSearchVO> selectTagSourceList(long userId, String keyword, int sourceType, int index, int size) {
		List<TagSourceSearchVO> tagSourceSearchVOList = new ArrayList<TagSourceSearchVO>();
		List<Tag> tagList = tagDao.selectTagListByKeword(userId, keyword, index * size, size);
    	if(tagList!=null && tagList.size()>0){
			ListIterator<Tag> tagListIterator = tagList.listIterator();
			while (tagListIterator.hasNext()){
				Tag next = tagListIterator.next();
				TagSourceSearchVO tagSourceSearchVO = new TagSourceSearchVO();
				tagSourceSearchVO.setFirstName(next.getFirstIndex());
				tagSourceSearchVO.setId(next.getId());
				tagSourceSearchVO.setTagName(next.getTagName());
				tagSourceSearchVO.setUserId(next.getUserId());
				List<SourceSearchVO> sourceSearchVOList = new ArrayList<SourceSearchVO>();
				long personcount=0;
				long customercount=0;
				long demandcount=0;
				long knowledgecount=0;
				List<TagSource> tagSourceList= tagSourcesDao.selectSourceByTagId(userId,next.getId(),sourceType,"",0,3,null);
				if(tagSourceList!=null && tagSourceList.size()>0){
					ListIterator<TagSource> tagSourceListIterator = tagSourceList.listIterator();
					while (tagSourceListIterator.hasNext()){
						TagSource sourcenext = tagSourceListIterator.next();
						SourceSearchVO sourceSearchVO = new SourceSearchVO();
						sourceSearchVO.setCreateAt(sourcenext.getCreateAt());
						sourceSearchVO.setSourceExtra(sourcenext.getSourceExtra());
						sourceSearchVO.setSourceId(sourcenext.getSourceId());
						sourceSearchVO.setSourceTitle(sourcenext.getSourceTitle());
						sourceSearchVO.setSourceType(sourcenext.getSourceType());
						List<Tag> tags = tagDao.selectTagBySourceId(userId, sourcenext.getSourceId(), sourcenext.getSourceType());
						sourceSearchVO.setSourceTagList(tags);
						sourceSearchVO.setSourceColumnType(sourcenext.getSourceColumnType());
						sourceSearchVO.setSupDem(sourcenext.getSupDem());
						sourceSearchVO.setId(sourcenext.getId());
						if(sourcenext.getSourceType()==2){
							personcount++;
						}else if(sourcenext.getSourceType()==3){
							customercount++;
						}else if(sourcenext.getSourceType()==7){
							demandcount++;
						}else if(sourcenext.getSourceType()==8){
							knowledgecount++;
						}
						sourceSearchVOList.add(sourceSearchVO);
					}
				}
				tagSourceSearchVO.setSourceList(sourceSearchVOList);
				tagSourceSearchVO.setTotalcount(sourceSearchVOList.size());
				tagSourceSearchVO.setPersonSourceCount(personcount);
				tagSourceSearchVO.setCustomerSourceCount(customercount);
				tagSourceSearchVO.setDemandSourceCount(demandcount);
				tagSourceSearchVO.setKnowledgeSourceCount(knowledgecount);
				tagSourceSearchVOList.add(tagSourceSearchVO);
			}
		}
		return tagSourceSearchVOList;
	}
}
