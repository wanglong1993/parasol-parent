package com.ginkgocap.parasol.file.dataimporter.dao;

import java.util.List;

import com.ginkgocap.parasol.file.model.Index;

/**
 * 
* @Title: FileIndexDao.java
* @Package com.ginkgocap.parasol.file.dataimporter.dao
* @Description: TODO(用一句话描述该文件做什么)
* @author fuliwen@gintong.com  
* @date 2016年2月23日 上午11:46:59
* @version V1.0
 */
public interface FileIndexDao {
    /**
     * 通过主键获得文章记录
     * @param id
     * @return
     */
    List<Index> getAllFileIndexes(int start, int limit);

}
