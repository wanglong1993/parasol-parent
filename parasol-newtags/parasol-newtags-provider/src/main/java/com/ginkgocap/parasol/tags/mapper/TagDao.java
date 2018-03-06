package com.ginkgocap.parasol.tags.mapper;


import com.ginkgocap.parasol.tags.model.Tag;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface TagDao {

    public List<Tag> selectTagListByKeword(@Param("userId") long userId, @Param("keyword") String keyWord, @Param("index")int index, @Param("size") int size);

    public long  countTagListByKeword(@Param("userId") long userId, @Param("keyword") String keyword);

    public List<Tag> selectTagBySourceId(@Param("userId") long userId,@Param("sourceId") long sourceId,@Param("sourceType") long sourceType);
}
