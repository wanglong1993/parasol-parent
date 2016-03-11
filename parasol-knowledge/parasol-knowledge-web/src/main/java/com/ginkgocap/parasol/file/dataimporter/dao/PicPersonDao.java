package com.ginkgocap.parasol.file.dataimporter.dao;

import java.util.List;

import com.ginkgocap.parasol.file.model.PicPerson;

/**
 * 
* @Title: FileIndexDao.java
* @Package com.ginkgocap.parasol.file.dataimporter.dao
* @Description: TODO(用一句话描述该文件做什么)
* @author fuliwen@gintong.com  
* @date 2016年2月23日 上午11:46:59
* @version V1.0
 */
public interface PicPersonDao {
	
  /**
   * sql过滤后，数据量不到500，不需要分批次处理
   * @return
   */
    List<PicPerson> getAllPersonPics();

}
