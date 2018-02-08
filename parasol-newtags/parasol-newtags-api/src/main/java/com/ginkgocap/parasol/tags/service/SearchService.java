package com.ginkgocap.parasol.tags.service;

import java.util.List;

public interface SearchService {


    public List searchTagsAndSource(long userId, String keyWord, int index, int size);
}
