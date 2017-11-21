package com.ginkgocap.parasol.directory.dao;

import com.ginkgocap.parasol.directory.model.DirectorySource;

public interface DirectorySourceDao {
    int deleteByPrimaryKey(Long id);

    int insert(DirectorySource record);

    int insertSelective(DirectorySource record);

    DirectorySource selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DirectorySource record);

    int updateByPrimaryKey(DirectorySource record);
}