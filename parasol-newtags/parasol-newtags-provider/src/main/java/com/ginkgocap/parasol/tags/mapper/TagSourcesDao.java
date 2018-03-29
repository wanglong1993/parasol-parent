package com.ginkgocap.parasol.tags.mapper;


import com.ginkgocap.parasol.tags.model.TagSource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagSourcesDao {

    int deleteSourceByType(@Param("userId")long userId,@Param("tagId")long tagId,@Param("sourceType")int sourceType,@Param("sourceId")long sourceId);

    /**
     * 符合条件的资源（资源为搜索主体）
     * @param userId
     * @param tagId
     * @param sourceType
     * @param keyword
     * @param index
     * @param size
     * @return
     */
    List<TagSource> selectSourceByTagId(@Param("userId")long userId,@Param("tagId")long tagId,@Param("sourceType")int sourceType,@Param("keyword")String keyword,
                                        @Param("start")int start,@Param("size")int size,@Param("sourceIdList")List sourceIdList);

    /**
     * 符合标签的资源（以标签为搜索主体）
     * @param userId
     * @param tagId
     * @param sourceType
     * @param keyword
     * @return
     */
    List<Long> searchSourceByTagIdAndtagName(@Param("userId")long userId, @Param("tagId")long tagId, @Param("sourceType")int sourceType, @Param("keyword")String keyword);

    long  countSourceByTagId(@Param("userId")long userId,@Param("tagId")long tagId,
                             @Param("sourceType")int sourceType,@Param("keyword")String keyword,
                             @Param("sourceIdList")List sourceIdList);

    List<TagSource> searchSourcesExctTag(@Param("userId")long userId,@Param("keyword")String keyword,@Param("sourceType")int sourceType);

    long insertSource(TagSource tagSource);

}
