package com.ginkgocap.parasol.directory.dao;

import com.ginkgocap.parasol.directory.model.Directory;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

public interface DirectoryDao extends ApplicationContextAware {
    int deleteByPrimaryKey(Long id);

    int insert(Directory record);

    int insertSelective(Directory record);

    Directory selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Directory record);

    int updateByPrimaryKey(Directory record);

    List<Directory> selectMyTreeDirectories(long appId, long userId, long typeId);
}