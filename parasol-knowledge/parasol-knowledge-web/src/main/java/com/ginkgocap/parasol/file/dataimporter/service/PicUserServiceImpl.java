/**
 * Copyright (c) 2016 北京金桐网投资有限公司.
 * All Rights Reserved. 保留所有权利.
 */
package com.ginkgocap.parasol.file.dataimporter.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.file.dataimporter.dao.PicUserDao;
import com.ginkgocap.parasol.file.model.PicUser;

/**
 * 
* @Title: FileServiceImpl.java
* @Package com.ginkgocap.parasol.file.dataimporter.service
* @Description: TODO(用一句话描述该文件做什么)
* @author fuliwen@gintong.com  
* @date 2016年2月22日 下午4:23:11
* @version V1.0
 */
@Service("picUserService")
public class PicUserServiceImpl implements PicUserService {

	@Autowired
	private PicUserDao picUserDao;

	public List<PicUser> getAllFileIndexes() {

		return picUserDao.getAllUserPics();
	}

	@Override
	public List<PicUser> getDefaultUserPics() {

		return picUserDao.getDefaultUserPics();
	}
	
}
