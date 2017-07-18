package com.ginkgocap.parasol.file.service;

import java.util.List;

import com.ginkgocap.parasol.file.exception.FileIndexServiceException;
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
	
	/**
	 * 根据id获取文件索引
	 * @param id
	 * @return 索引对象
	 */
    FileIndex getFileIndexById(long id) throws FileIndexServiceException;

    /**
     * 保存文件索引
     * @param fileIndex
     * @return 索引对象
     */
    FileIndex insertFileIndex(FileIndex fileIndex) throws FileIndexServiceException;
    
    /**
     * 更新索引文件
     * @param fileIndex
     * @return
     */
    boolean updateFileIndex(FileIndex fileIndex) throws FileIndexServiceException;
    
    /**
     * 根据taskId获取文件索引列表
     * @param taskId
     * @return 索引列表
     */
    List<FileIndex> getFileIndexesByTaskId(String taskId) throws FileIndexServiceException;
    
    /**
     * 根据用户id获取文件索引列表
     * @param userId
     * @return 索引列表
     */
    List<FileIndex> getFileIndexesByCreaterId(long userId) throws FileIndexServiceException;
    
    /**
     * 获取用户特定类型的文件索引列表
     * @param userId
     * @param type
     * @return 索引列表
     */
    List<FileIndex> getFileIndexesByCreaterIdAndType(long userId, int type) throws FileIndexServiceException;

    /**
     * 修改文件索引名称
     * @param id
     * @param fileIndex
     * @return 修改后的文件索引
     */
    FileIndex updateFileIndexByFileTitle(long id, String fileIndex) throws FileIndexServiceException;
    
    /**
     * 根据索引对象id删除索引对象
     * @param id
     * @return true or false
     */
    boolean deleteFileIndexById(long id) throws FileIndexServiceException;
    
    /**
     * 根据taskid删除数据
     * @param taskId
     * @return 是否删除
     */
    boolean deleteFileIndexesByTaskId(String taskId) throws FileIndexServiceException;
    
    /**
     * 修改附件的状态为指定的状态
     * @param ids
     * @param status
     * @return
     */
    int updateSetStatus(String ids,boolean status) throws FileIndexServiceException;


    /**
     * 批量保存文件索引列表
     * @param list
     * @return 是否保存成功
     */
    boolean updateFileIndexes(List<FileIndex> list) throws FileIndexServiceException;

    /**
     * 根据索引id列表获取文件索引列表
     * @param ids
     * @return 文件索引列表
     */
    List<FileIndex> selectFileIndexesByIds(List<Long> ids) throws FileIndexServiceException;

}
