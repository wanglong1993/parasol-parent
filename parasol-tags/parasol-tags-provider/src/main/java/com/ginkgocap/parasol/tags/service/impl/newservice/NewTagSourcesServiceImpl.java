package com.ginkgocap.parasol.tags.service.impl.newservice;


import com.ginkgocap.parasol.tags.mapper.TagDao;
import com.ginkgocap.parasol.tags.mapper.TagSourcesDao;
import com.ginkgocap.parasol.tags.model.TagSource;
import com.ginkgocap.parasol.tags.service.NewTagSourceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("newTagSourceService")
public class NewTagSourcesServiceImpl implements NewTagSourceService {

	private static Logger logger = LoggerFactory.getLogger(NewTagSourcesServiceImpl.class);

	@Autowired
	private TagSourcesDao tagSourcesDao;

	@Autowired
	private TagDao tagDao;


	@Override
	public List searchTagSources(long userId, String keyWord, int sourceType, int index, int size) {
		List<TagSource> list = tagSourcesDao.searchTagSources(userId,keyWord,sourceType,index,size);
		return list;
	}
}
