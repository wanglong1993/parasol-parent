package com.ginkgocap.parasol.directory.dao;

import com.ginkgocap.parasol.directory.model.DirectoryType;

public interface DirectoryTypeDao {
    int deleteByPrimaryKey(Long id);

    int insert(DirectoryType record);

    int insertSelective(DirectoryType record);

    DirectoryType selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(DirectoryType record);

    int updateByPrimaryKey(DirectoryType record);
}