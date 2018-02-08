package com.ginkgocap.parasol.tags.service;


import java.util.List;

public interface NewTagSourceService {


	public List searchTagSources(long userId, String keyWord,int sourceType, int index, int size);


}
