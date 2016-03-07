package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.file.model.FileIndex;
import com.ginkgocap.parasol.file.service.FileIndexService;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.exception.UserOrganExtServiceException;
import com.ginkgocap.parasol.user.model.UserOrganExt;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.user.service.UserOrganExtService;
import com.ginkgocap.parasol.util.PinyinUtils;
@Service("userOrganExtService")
public class UserOrganExtServiceImpl extends BaseService<UserOrganExt> implements UserOrganExtService  {
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	@Resource
	private FileIndexService fileIndexService;
	private static Logger logger = Logger.getLogger(UserOrganExtServiceImpl.class);
	
	/**
	 * 检查数据
	 * @param userOrganExt
	 * @return
	 * @throws UserOrganExtServiceException
	 */
	private UserOrganExt checkValidity(UserOrganExt userOrganExt,int type)throws UserOrganExtServiceException,UserLoginRegisterServiceException {
		if(userOrganExt==null) throw new UserOrganExtServiceException("userOrganExt can not be null.");
		if(userOrganExt.getUserId()<=0l) throw new UserOrganExtServiceException("The value of userId is null or empty.");
		if(userLoginRegisterService.getUserLoginRegister(userOrganExt.getUserId())==null) throw new UserLoginRegisterServiceException("userId not exists in userLoginRegister.");
		if(type!=0)
		if(getUserOrganExt(userOrganExt.getUserId())==null)throw new UserOrganExtServiceException("userId not exists in UserOrganExt");
//		if(StringUtils.isEmpty(userOrganExt.getName()))throw new UserOrganExtServiceException("The value of  name is null or empty.");
		if(StringUtils.isEmpty(userOrganExt.getIp()))throw new UserOrganExtServiceException("The value of ip is null or empty.");
		if(userOrganExt.getCtime()==null) userOrganExt.setCtime(System.currentTimeMillis());
		if(userOrganExt.getUtime()==null) userOrganExt.setUtime(System.currentTimeMillis());
		if(!StringUtils.isEmpty(userOrganExt.getName()))userOrganExt.setNameFirst(StringUtils.substring(PinyinUtils.stringToHeads(userOrganExt.getName()), 0, 1));
		if(!StringUtils.isEmpty(userOrganExt.getName()))userOrganExt.setNameIndexAll(PinyinUtils.stringToQuanPin(userOrganExt.getName()));
		return userOrganExt;
	}
	
	@Override
	public Long createUserOrganExt(UserOrganExt userOrganExt)throws UserOrganExtServiceException,UserLoginRegisterServiceException{
		try {
			Long id=(Long) saveEntity(checkValidity(userOrganExt,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new UserOrganExtServiceException("createUserOrganExt failed.");
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrganExtServiceException(e);
		}
	}

	@Override
	public boolean updateUserOrganExt(UserOrganExt userOrganExt)throws UserOrganExtServiceException,UserLoginRegisterServiceException {
		try {
			return updateEntity(checkValidity(userOrganExt,1));
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrganExtServiceException(e);
		}
	}

	@Override
	public UserOrganExt getUserOrganExt(Long userId) throws UserOrganExtServiceException {
		try {
			if(userId==null || userId<=0l)throw new UserOrganExtServiceException("userId is null or empty.");
			UserOrganExt userOrganExt=getEntity(userId);
			if(userOrganExt==null)return null;
			Long fileIndexId=userOrganExt.getBusinessLicencePicId();
			FileIndex fileIndex=null;
			if(!ObjectUtils.isEmpty(fileIndexId)){
				fileIndex=getFileIndex(fileIndexId);
				if(fileIndex!=null)userOrganExt.setBusinessLicencePicPath(fileIndex.getServerHost()+"/"+fileIndex.getFilePath());
			}
			fileIndexId=userOrganExt.getIdcardFrontPicId();
			if(!ObjectUtils.isEmpty(fileIndexId)){
				fileIndex=getFileIndex(fileIndexId);
				if(fileIndex!=null)userOrganExt.setIdcardFrontPicPath(fileIndex.getServerHost()+"/"+fileIndex.getFilePath());
			}
			fileIndexId=userOrganExt.getIdcardBackPicId();
			if(!ObjectUtils.isEmpty(fileIndexId)){
				fileIndex=getFileIndex(fileIndexId);
				if(fileIndex!=null)userOrganExt.setIdcardBackPicPath(fileIndex.getServerHost()+"/"+fileIndex.getFilePath());
			}
			return userOrganExt;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrganExtServiceException(e);
		}
	}
	
	/**
	 * 根据FileIndexid获取组织用户的图片文件信息
	 * @param userId
	 * @return UserOrganExt
	 * @throws UserOrganExtServiceException 
	 */
	private FileIndex getFileIndex(Long fileIndexId) throws UserOrganExtServiceException {
		try {
			if(fileIndexId==null || fileIndexId<=0L)return null;
			FileIndex fileIndex=fileIndexService.getFileIndexById(fileIndexId.longValue());
			if(fileIndex==null)return null;
			return fileIndex;
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrganExtServiceException(e);
		}
	}
	
	@Override
	public List<UserOrganExt> getUserOrganExtList(List<Long> userIds)throws UserOrganExtServiceException {
		try {
			if(userIds==null || userIds.size()==0)throw new UserOrganExtServiceException("userIds is null or empty");
			return getEntityByIds(userIds);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrganExtServiceException(e);
		}
	}

	@Override
	public Boolean realDeleteUserOrganExt(Long id)throws UserOrganExtServiceException {
		try {
			if(id==null || id<=0l) throw new UserOrganExtServiceException("id is must grater than zero.");
			return deleteEntity(id);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserOrganExtServiceException(e);
		}
	}
}
