package com.ginkgocap.parasol.tags.mapper;


import com.ginkgocap.parasol.tags.model.TagSource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagSourcesDao {

    int deleteSourceByType(@Param("userId")long userId,@Param("tagId")long tagId,@Param("sourceType")int sourceType,@Param("sourceId")long sourceId);

    List<TagSource> selectSourceByTagId(@Param("userId")long userId,@Param("tagId")long tagId,@Param("sourceType")int sourceType,@Param("keyword")String keyword,
                                        @Param("index")int index,@Param("size")int size);

    long  countSourceByTagId(@Param("userId")long userId,@Param("tagId")long tagId,@Param("sourceType")int sourceType,@Param("keyword")String keyword);

    List<TagSource> searchSourcesExctTag(@Param("userId")long userId,@Param("keyword")String keyword,@Param("sourceType")int sourceType);

    long insertSource(TagSource tagSource);

}
