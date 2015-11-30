package com.ginkgocap.parasol.file.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
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

    private final Logger logger = Logger.getLogger(getClass());
    /*
     * 文件保存记录的通过主键Id查询的方法
     * (non-Javadoc)
     * @see com.ginkgocap.ywxt.service.FileIndexService#selectByPrimaryKey(long)
     */
    public FileIndex selectByPrimaryKey(long id) {
    	FileIndex file = null;
    	try {
			file = getEntity(id);
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return file;
    }

    /*
     * 文件保存记录的插入方法
     * (non-Javadoc)
     * @see com.ginkgocap.ywxt.service.FileIndexService#insert(com.ginkgocap.ywxt.model.FileIndex)
     */
    public FileIndex insert(FileIndex fileIndex) {
    	try {
			Long id = (Long)saveEntity(fileIndex);
			fileIndex.setId(id);
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        return fileIndex;
    }

    /*
     * 文件保存记录的删除方法
     * (non-Javadoc)
     * @see com.ginkgocap.ywxt.service.FileIndexService#delete(long)
     */
    public boolean delete(long id) {
    	boolean flag = false;
    	try {
			flag = deleteEntity(id);
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return flag;
    }

    /*
     * 文件保存记录通过任务id获得
     * (non-Javadoc)
     * @see com.ginkgocap.ywxt.service.FileIndexService#selectByTaskId(java.lang.String)
     */
    
    public List<FileIndex> selectByTaskId(String taskId,String status) {
    	
    	List<FileIndex> files = new ArrayList<FileIndex>();
        return files;
    }


    
    public boolean update(List<FileIndex> list) {
    	
    	boolean flag = false;
    	try {
			flag = updateEntitys(list);
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return flag;
    }

    
    public List<FileIndex> selectByIds(List<Long> ids) {
        List<FileIndex> list = null;
        try {
			list = getEntityByIds(ids);
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return list;
    }

    
    public int deleteByTaskId(String taskId) {
    	
    	try {
			deleteList("");
		} catch (BaseServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return 0;
    }

    
    public int updateSetStatus(String ids, boolean status) {
//        return fileIndexDao.updateSetStatus(ids,status);
    	
    	return 0;
    }

}
