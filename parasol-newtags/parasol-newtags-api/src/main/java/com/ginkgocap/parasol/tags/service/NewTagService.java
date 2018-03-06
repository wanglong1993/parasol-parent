package com.ginkgocap.parasol.tags.service;

import com.ginkgocap.parasol.tags.model.Tag;
import com.ginkgocap.parasol.tags.model.TagSearchVO;

import java.util.List;

public interface NewTagService {

    //0 全部聚合 2人脉 3 客户  7需求  8知识
    public List<TagSearchVO> selectTagListByKeword(long userId,String keyword,int sourceType,int index,int size);

    public long  countTagListByKeword(long userId,String keyword);

    public List<Tag> selectTagBySourceId(long userId,long sourceId,long sourceType);
}
