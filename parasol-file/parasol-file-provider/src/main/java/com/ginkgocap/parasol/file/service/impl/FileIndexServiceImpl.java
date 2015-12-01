package com.ginkgocap.parasol.file.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.file.model.FileIndex;
import com.ginkgocap.parasol.file.service.FileIndexService;

/**
 * 
* <p>Title: FileIndexServiceImpl.java<／p> 
* <p>Description: 文件上传service<／p> 

* @author fuliwen 
* @date 2015-11-30 
* @version 1.0
 */
@Service("fileIndexService")
public class FileIndexServiceImpl extends BaseService<FileIndex> implements FileIndexService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
    public FileIndex selectByPrimaryKey(long id) {
    	logger.info("进入根据文件主键id获取上传文件：参数id：{}", id);
    	FileIndex file = null;
    	try {
			file = getEntity(id);
		} catch (BaseServiceException e) {
	    	logger.error("根据文件主键id获取上传文件失败：参数id：{}", id);
			e.printStackTrace();
		}
    	return file;
    }

	@Override
    public FileIndex insertFileIndex(FileIndex fileIndex) {
    	logger.info("进入保存上传文件索引：参数fileTitle：{}", fileIndex.getFileTitle());
    	try {
			Long id = (Long)saveEntity(fileIndex);
			fileIndex.setId(id);
		} catch (BaseServiceException e) {
	    	logger.error("保存上传文件索引失败：参数fileTitle：{}", fileIndex.getFileTitle());
			e.printStackTrace();
		}
        return fileIndex;
    }

	@Override
    public boolean deleteFileIndexById(long id) {
    	logger.info("进入根据id删除上传文件索引：参数id：{}", id);
    	boolean flag = false;
    	try {
			flag = deleteEntity(id);
		} catch (BaseServiceException e) {
	    	logger.error("根据id删除上传文件索引失败：参数id：{}", id);
			e.printStackTrace();
		}
    	return flag;
    }

	@Override
    public List<FileIndex> selectFileIndexesByTaskId(String taskId) {
    	logger.info("进入根据taskid获取上传文件索引列表：参数taskid：{}", taskId);
    	List<FileIndex> files = new ArrayList<FileIndex>();
    	try {
			files = getEntitys("FileIndex_List_Id_TaskId",taskId);
		} catch (BaseServiceException e) {
	    	logger.info("根据taskid获取上传文件索引列表失败：参数taskid：{}", taskId);
			e.printStackTrace();
		}
        return files;
    }

	@Override
    public boolean updateFileIndexes(List<FileIndex> list) {
    	logger.info("进入批量保存上传文件索引：参数list.size()：{}", list.size());    	
    	boolean flag = false;
    	try {
			flag = updateEntitys(list);
		} catch (BaseServiceException e) {
	    	logger.error("批量保存上传文件索引失败：参数list.size()：{}", list.size());  
			e.printStackTrace();
		}
    	return flag;
    }

    @Override
    public List<FileIndex> selectFileIndexesByIds(List<Long> ids) {
    	logger.info("进入根据id列表获取上传文件索引列表：参数ids：{}", ids);       	
        List<FileIndex> list = null;
        try {
			list = getEntityByIds(ids);
		} catch (BaseServiceException e) {
	    	logger.error("根据id列表获取上传文件索引列表失败：参数ids：{}", ids);     
			e.printStackTrace();
		}
        return list;
    }

    @Override
    public boolean deleteFileIndexesByTaskId(String taskId) {
    	logger.info("进入根据taskid删除上传文件索引列表：参数taskid：{}", taskId);   
    	try {
			deleteList("");
		} catch (BaseServiceException e) {
	    	logger.info("根据taskid删除上传文件索引列表失败：参数taskid：{}", taskId);   
			e.printStackTrace();
		}
        return false;
    }

    @Override
    public int updateSetStatus(String ids, boolean status) {
    	
    	return 0;
    }

}
