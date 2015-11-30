package com.ginkgocap.parasol.file.service;

import java.util.List;

import com.ginkgocap.parasol.file.model.FileIndex;

/**
 * 
* <p>Title: FileIndexService.java<／p> 
* <p>Description: <／p> 

* @author fuliwen 
* @date 2015-11-30 
* @version 1.0
 */
public interface FileIndexService {
	
    FileIndex selectByPrimaryKey(long id);

    FileIndex insert(FileIndex fileIndex);
    
    List<FileIndex> selectByTaskId(String taskId,String status);

    boolean delete(long id);
    
    /**
     * 根据taskid删除数据
     * @param taskId
     * @return 被删除的行数
     */
    int deleteByTaskId(String taskId);
    
    /**
     * 修改附件的状态为指定的状态
     * @param ids
     * @param status
     * @return
     */
    int updateSetStatus(String ids,boolean status);


    boolean update(List<FileIndex> list);

    List<FileIndex> selectByIds(List<Long> ids);
    
}
