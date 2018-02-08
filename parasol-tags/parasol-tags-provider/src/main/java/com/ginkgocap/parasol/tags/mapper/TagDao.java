package com.ginkgocap.parasol.tags.mapper;


import java.util.List;

public interface TagDao {

    public List searchTags(long userId, String keyWord, int index, int size);
}
