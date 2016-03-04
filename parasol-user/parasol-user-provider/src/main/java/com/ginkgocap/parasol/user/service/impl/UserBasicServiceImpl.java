package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.file.exception.FileIndexServiceException;
import com.ginkgocap.parasol.file.model.FileIndex;
import com.ginkgocap.parasol.file.service.FileIndexService;
import com.ginkgocap.parasol.user.exception.UserBasicServiceException;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.service.UserBasicService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.util.PinyinUtils;
@Service("userBasicService")
public class UserBasicServiceImpl extends BaseService<UserBasic> implements UserBasicService  {
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	private FileIndexService fileIndexService;
	private static Logger logger = Logger.getLogger(UserBasicServiceImpl.class);
	/**
	 * 检查数据
	 * @param userBasic
	 * @return
	 * @throws UserBasicServiceException
	 */
	private UserBasic checkValidity(UserBasic userBasic,int type)throws UserBasicServiceException,UserLoginRegisterServiceException {
		if(userBasic==null) throw new UserBasicServiceException("userBasic can not be null.");
		if(userBasic.getUserId()<=0l) throw new UserBasicServiceException("The value of userId is null or empty.");
		if(userLoginRegisterService.getUserLoginRegister(userBasic.getUserId())==null) throw new UserLoginRegisterServiceException("userId not exists in userLoginRegister");
		if(type!=0)
		if(getUserBasic(userBasic.getUserId())==null)throw new UserBasicServiceException("userId not exists in userBasic");
//		if(StringUtils.isEmpty(userBasic.getName()))throw new UserBasicServiceException("The value of  name is null or empty.");
		if(userBasic.getSex().intValue()!=0 && userBasic.getSex().intValue()!=1 && userBasic.getSex().intValue()!=2)throw new UserBasicServiceException("The value of sex must be 0 or 1 or 2.");
		if(userBasic.getStatus().intValue() != 0 && userBasic.getStatus().intValue() != 1 && userBasic.getStatus().intValue() != -1 && userBasic.getStatus() !=2)throw new UserBasicServiceException("The value of status is null or empty.");
		if(userBasic.getCtime()==null) userBasic.setCtime(System.currentTimeMillis());
		if(userBasic.getUtime()==null) userBasic.setUtime(System.currentTimeMillis());
		if(type==1)userBasic.setUtime(System.currentTimeMillis());
		if(!StringUtils.isEmpty(userBasic.getName()))userBasic.setNameIndex(PinyinUtils.stringToHeads(userBasic.getName()));
		return userBasic;
	}
	
	@Override
	public Long createUserBasic(UserBasic userBasic)throws UserBasicServiceException,UserLoginRegisterServiceException{
		try {
			Long id=(Long)saveEntity(checkValidity(userBasic,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new UserBasicServiceException("createUserLoginRegister failed.");
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserBasicServiceException(e);
		}
	}

	@Override
	public boolean updateUserBasic(UserBasic userBasic)throws UserBasicServiceException,UserLoginRegisterServiceException {
		try {
			if(updateEntity(checkValidity(userBasic,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserBasicServiceException(e);
		}
	}

	@Override
	public UserBasic getUserBasic(Long userId) throws UserBasicServiceException {
		try {
			if(userId==null || userId<=0l)throw new UserBasicServiceException("userId is null or empty");
			UserBasic userBasic =getEntity(userId);
			if(!ObjectUtils.isEmpty(userBasic)){
				try {
					if(!ObjectUtils.isEmpty(userBasic.getPicId())){
						FileIndex fileIndex=fileIndexService.getFileIndexById(userBasic.getPicId());
						if(!ObjectUtils.isEmpty(fileIndex)){
							userBasic.setPicPath(fileIndex.getFilePath());
						}
					}
				} catch (FileIndexServiceException e) {
					e.printStackTrace();
				}
			}
			return userBasic;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserBasicServiceException(e);
		}
	}

	@Override
	public List<UserBasic> getUserBasecList(List<Long> userIds)throws UserBasicServiceException {
		try {
			if(userIds==null || userIds.size()==0)throw new UserBasicServiceException("userIds is null or empty");
			return getEntityByIds(userIds);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserBasicServiceException(e);
		}
	}

	@Override
	public Boolean realDeleteUserBasic(Long id)throws UserBasicServiceException {
		try {
			if(id==null || id<=0l) throw new UserBasicServiceException("id is must grater than zero.");
			return deleteEntity(id);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserBasicServiceException(e);
		}
	}
}
