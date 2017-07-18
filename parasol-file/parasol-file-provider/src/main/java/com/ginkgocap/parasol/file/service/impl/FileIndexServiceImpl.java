package com.ginkgocap.parasol.file.service.impl;

import com.ginkgocap.parasol.file.dao.FileIndexDao;
import com.ginkgocap.parasol.file.exception.FileIndexServiceException;
import com.ginkgocap.parasol.file.model.FileIndex;
import com.ginkgocap.parasol.file.service.FileIndexService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 
* <p>Title: FileIndexServiceImpl.java<／p> 
* <p>Description: 文件上传service<／p> 

* @author fuliwen 
* @date 2015-11-30 
* @version 1.0
 */
@Service("fileIndexService")
public class FileIndexServiceImpl  implements FileIndexService {

	@Autowired
	FileIndexDao fileIndexDao;

	private Logger logger = LoggerFactory.getLogger(getClass());
	private static int error_fileIndexId_blank = 100; // 索引文件id
	private static int error_fileIndex_blank = 101; // 索引文件
	private static int error_taskId_blank = 102; // taskId不存在	
	private static int error_userId_blank = 103;	//	用于id为空
	private static int error_idList_null = 104; //	用户id为空
	private static int error_fileIndexes_blank = 105; // fileIndex列表为空
	@Override
    public FileIndex getFileIndexById(long id) throws FileIndexServiceException {
		if(id <= 0) throw new FileIndexServiceException(error_fileIndexId_blank,"fileIndexId is null!");
    	logger.info("进入根据文件主键id获取上传文件：参数id：{}", id);
    	FileIndex file = null;
    	try {
			file = fileIndexDao.selectById(id);
		} catch (Exception e) {
	    	logger.error("根据文件主键id获取上传文件失败：参数id：{}", id);
	    	throw new FileIndexServiceException(e);
		}
    	return file;
    }

	@Override
    public FileIndex insertFileIndex(FileIndex fileIndex) throws FileIndexServiceException {
		if(fileIndex == null) throw new FileIndexServiceException(error_fileIndex_blank,"fileIndex is null!");
    	logger.info("进入保存上传文件索引：参数fileTitle：{}", fileIndex.getFileTitle());
    	try {
			Long id = fileIndexDao.insert(fileIndex);
			fileIndex.setId(id);
		} catch (Exception e) {
	    	logger.error("保存上传文件索引失败：参数fileTitle：{}", fileIndex.getFileTitle());
	    	throw new FileIndexServiceException(e);
		}
        return fileIndex;
    }


	@Override
	public boolean updateFileIndex(FileIndex fileIndex) throws FileIndexServiceException{
		if(fileIndex == null) throw new FileIndexServiceException(error_fileIndex_blank,"fileIndex is null!");
		logger.info("进入更新上传文件索引：参数fileTitle：{}", fileIndex.getFileTitle());
		boolean flag = false;
		try {
			int count = fileIndexDao.update(fileIndex);
			flag = count > 0 ?true:false;
		} catch (Exception e) {
	    	logger.error("保存上传文件索引失败：参数fileTitle：{}", fileIndex.getFileTitle());
	    	throw new FileIndexServiceException(e);
		}
        return flag;
	}	
	
	@Override
    public boolean deleteFileIndexById(long id) throws FileIndexServiceException {
		if(id <= 0) throw new FileIndexServiceException(error_fileIndexId_blank,"fileIndexId is null!");
    	logger.info("进入根据id删除上传文件索引：参数id：{}", id);
    	boolean flag = false;
    	try {
			flag = fileIndexDao.delete(id) > 0 ? true:false;
		} catch (Exception e) {
	    	logger.error("根据id删除上传文件索引失败：参数id：{}", id);
	    	throw new FileIndexServiceException(e);
		}
    	return flag;
    }

	@Override
    public List<FileIndex> getFileIndexesByTaskId(String taskId) throws FileIndexServiceException {
		if(StringUtils.isBlank(taskId)) throw new FileIndexServiceException(error_taskId_blank,"taskId is null!");
    	logger.info("进入根据taskid获取上传文件索引列表：参数taskid：{}", taskId);
    	List<FileIndex> files = new ArrayList<FileIndex>();
    	try {
			files = fileIndexDao.getFileIndexByTaskId(taskId);
		} catch (Exception e) {
	    	logger.error("根据taskid获取上传文件索引列表失败：参数taskid：{}", taskId);
	    	throw new FileIndexServiceException(e);
		}
        return files;
    }


	@Override
	public FileIndex updateFileIndexByFileTitle(long id, String fileTitle) throws FileIndexServiceException {
		if(id <= 0) throw new FileIndexServiceException(error_fileIndexId_blank,"fileIndexId is null!");
    	logger.info("进入根据文件索引id修改文件名：参数id：{}，fileTitle:{}", id, fileTitle);
    	FileIndex file = null;
//		try {
//			file = getEntity(id);
//			file.setFileTitle(fileTitle);
//			updateEntity(file);
//		} catch (BaseServiceException e) {
//	    	logger.error("根据文件索引id修改文件名失败：参数id：{}，fileTitle:{}", id, fileTitle);
//	    	throw new FileIndexServiceException(e);
//		}
		return file;
	}	
	
	@Override
	public List<FileIndex> getFileIndexesByCreaterId(long createrId) throws FileIndexServiceException {
		if(createrId <= 0) throw new FileIndexServiceException(error_userId_blank,"userId is null!");
    	logger.info("进入根据用户id获取上传文件索引：参数userId：{}", createrId);
    	List<FileIndex> files = new ArrayList<FileIndex>();
    	try {
			files = fileIndexDao.getFileIndexesByCreaterId(createrId);
		} catch (Exception e) {
	    	logger.error("根据用户id获取上传文件索引列表失败：参数userId：{}", createrId);
	    	throw new FileIndexServiceException(e);
		}
        return files;
	}
	
	@Override
	public List<FileIndex> getFileIndexesByCreaterIdAndType(long createrId, int type) throws FileIndexServiceException {
		if(createrId <= 0) throw new FileIndexServiceException(error_userId_blank,"userId is null!");
    	logger.info("进入根据用户id和type获取上传文件索引：参数userId：{},type:{}", createrId, type);
    	List<FileIndex> files = new ArrayList<FileIndex>();
//    	try {
//			files = getEntitys("FileIndex_List_Id_CreaterId_type",createrId,type);
//		} catch (BaseServiceException e) {
//	    	logger.error("根据用户id和type获取上传文件索引列表失败：参数userId：{}", createrId);
//	    	throw new FileIndexServiceException(e);
//		}
        return files;
	}		
	
	@Override
    public boolean updateFileIndexes(List<FileIndex> list) throws FileIndexServiceException {
		if( list==null || list.size()==0 ) throw new FileIndexServiceException(error_fileIndexes_blank,"fileIndexes list is null!");
    	logger.info("进入批量保存上传文件索引：参数list.size()：{}", list.size());    	
    	boolean flag = false;
//    	try {
//			flag = updateEntitys(list);
//		} catch (BaseServiceException e) {
//	    	logger.error("批量保存上传文件索引失败：参数list.size()：{}", list.size());
//	    	throw new FileIndexServiceException(e);
//		}
    	return flag;
    }

    @Override
    public List<FileIndex> selectFileIndexesByIds(List<Long> ids) throws FileIndexServiceException {
    	if( ids==null || ids.size()==0 ) throw new FileIndexServiceException(error_idList_null,"ids list is null!");
    	logger.info("进入根据id列表获取上传文件索引列表：参数ids：{}", ids);       	
        List<FileIndex> list = null;
//        try {
//			list = getEntityByIds(ids);
//		} catch (BaseServiceException e) {
//	    	logger.error("根据id列表获取上传文件索引列表失败：参数ids：{}", ids);
//	    	throw new FileIndexServiceException(e);
//		}
        return list;
    }

	@Override
    public boolean deleteFileIndexesByTaskId(String taskId) throws FileIndexServiceException {
		if(StringUtils.isBlank(taskId)) throw new FileIndexServiceException(error_taskId_blank,"taskId is null!");
    	logger.info("进入根据taskid删除上传文件索引列表：参数taskid：{}", taskId);   
    	boolean flag = false;
    	try {
			flag = fileIndexDao.deleteFileIndexesByTaskId(taskId) > 0 ? true:false;
		} catch (Exception e) {
	    	logger.info("根据taskid删除上传文件索引列表失败：参数taskid：{}", taskId);
	    	throw new FileIndexServiceException(e);
		}
        return false;
    }

    @Override
    public int updateSetStatus(String ids, boolean status) {
    	
    	return 0;
    }

}
