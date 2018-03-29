package com.ginkgocap.parasol.tags.mapper;


import com.ginkgocap.parasol.tags.model.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagDao {

    List<Tag> selectTagListByKeword(@Param("userId") long userId, @Param("keyword") String keyword, @Param("index")int index, @Param("size") int size);

    long  countTagListByKeword(@Param("userId") long userId, @Param("keyword") String keyword);

    List<Tag> selectTagBySourceId(@Param("userId") long userId,@Param("sourceId") long sourceId,@Param("sourceType") long sourceType);

    Tag getTagById(@Param("id") long id);
}
