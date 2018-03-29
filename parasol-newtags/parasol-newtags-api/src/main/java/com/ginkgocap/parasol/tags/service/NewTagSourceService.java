package com.ginkgocap.parasol.tags.service;


import com.ginkgocap.parasol.tags.model.SourceSearchVO;
import com.ginkgocap.parasol.tags.model.TagSource;

import java.util.List;

public interface NewTagSourceService {


	public List<SourceSearchVO> searchTagSources(long userId, long tagId, String keyWord, int sourceType, int index, int size);

	public boolean deleteSourceByType(long userId,long tagId,int sourceType,long sourceId);

	public long  countSourceByTagId(long userId,long tagId,int sourceType,String keyword);

	public List<SourceSearchVO> searchSourcesExctTag(long userId,String keyWord,int sourceType);

	public boolean  updateSourceByTagId(long userId,long tagId,int sourceType,List<TagSource> tagSourceList);

	public boolean updateTagsources(Long appId, Long userId, Long sourceId,Long sourceType,List<Long> tagIds,String sourceTitle,long columnType,int supDem,String sourceExtra);

	List<TagSource> getTagSourcesBySourceId(Long userId,Long sourceId, Long sourceType);
}
