package com.ginkgocap.parasol.directory.dao.impl;

import com.ginkgocap.parasol.directory.dao.DirectoryDao;
import com.ginkgocap.parasol.directory.model.Directory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wang fei on 2017/8/29.
 */
@Service("myDirectoryDao")
public class DirectoryDaoImpl extends SqlSessionDaoSupport implements DirectoryDao{

    private final Logger logger = LoggerFactory.getLogger(DirectoryDaoImpl.class);

    private ApplicationContext applicationContext;

    //@Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.applicationContext=applicationContext;

    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return 0;
    }

    @Override
    public int insert(Directory record) {
        //SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) applicationContext.getBean("sqlSessionFactory");
        return 0;
    }

    @Override
    public int insertSelective(Directory record) {
        return 0;
    }

    @Override
    public Directory selectByPrimaryKey(Long id) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(Directory record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(Directory record) {
        return 0;
    }

    @Override
    public List<Directory> selectMyTreeDirectories(long appId, long userId, long typeId) {

        Map<String, Long> map = new HashMap<String, Long>(3);
        map.put("appId", appId);
        map.put("userId", userId);
        map.put("typeId", typeId);
        return getSqlSession().selectList("tb_directory.selectMyTreeDirectories", map);
    }
}
