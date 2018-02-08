package com.ginkgocap.parasol.tags.service.impl.newservice;

import com.ginkgocap.parasol.tags.mapper.TagDao;
import com.ginkgocap.parasol.tags.mapper.TagSourcesDao;
import com.ginkgocap.parasol.tags.service.NewTagService;
import com.ginkgocap.parasol.tags.service.SearchService;
import com.ginkgocap.parasol.tags.service.TagService;
import com.ginkgocap.parasol.tags.service.TagSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service("searchService")
public class SearchServiceImpl implements SearchService{


    @Autowired
    private TagSourcesDao tagSourcesDao;

    @Autowired
    private TagDao tagDao;
    @Override
    public List searchTagsAndSource(long userId, String keyWord, int index, int size) {
        List list = new ArrayList();
        List list_1 = tagDao.searchTags(userId,keyWord,index,size);
        List list_2 = tagSourcesDao.searchTagSources(userId,keyWord,0,index,size);
        list.add(list_1);
        list.add(list_2);
        return list;
    }
}
