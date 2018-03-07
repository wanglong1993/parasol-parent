package com.ginkgocap.parasol.tags.service;


import com.ginkgocap.parasol.tags.model.TagSource;
import com.ginkgocap.parasol.tags.model.TagSourceSearchVO;

import java.util.List;

public interface NewTagSourceService {


	public List<TagSourceSearchVO> searchTagSources(long userId,long tagId, String keyWord, int sourceType, int index, int size);

	public boolean deleteSourceByType(long userId,long tagId,int sourceType);

	public long  countSourceByTagId(long userId,long tagId,int sourceType,String keyword);
}
