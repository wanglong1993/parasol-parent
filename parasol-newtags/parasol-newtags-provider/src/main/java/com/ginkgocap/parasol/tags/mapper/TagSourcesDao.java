package com.ginkgocap.parasol.tags.mapper;


import java.util.List;

public interface TagSourcesDao {


    public List searchTagSources(long userId, String keyWord, int sourceType,int index, int size);
}
