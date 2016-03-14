package com.ginkgocap.parasol.person.service.impl;

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
import com.ginkgocap.parasol.person.exception.PersonBasicServiceException;
import com.ginkgocap.parasol.person.model.PersonBasic;
import com.ginkgocap.parasol.person.service.PersonBasicService;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
import com.ginkgocap.parasol.util.PinyinUtils;
@Service("personBasicService")
public class PersonBasicServiceImpl extends BaseService<PersonBasic> implements PersonBasicService  {
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	@Resource
	private FileIndexService fileIndexService;
	private static Logger logger = Logger.getLogger(PersonBasicServiceImpl.class);
	/**
	 * 检查数据
	 * @param personBasic
	 * @return
	 * @throws PersonBasicServiceException
	 */
	private PersonBasic checkValidity(PersonBasic personBasic,int type)throws PersonBasicServiceException,UserLoginRegisterServiceException {
		if(personBasic==null) throw new PersonBasicServiceException("personBasic can not be null.");
		if(personBasic.getUserId()<=0l) throw new PersonBasicServiceException("The value of userId is null or empty.");
		if(userLoginRegisterService.getUserLoginRegister(personBasic.getUserId())==null) throw new UserLoginRegisterServiceException("userId not exists in userLoginRegister");
		if(type!=0)
		if(getPersonBasic(personBasic.getId())==null)throw new PersonBasicServiceException("userId not exists in personBasic");
		if(StringUtils.isEmpty(personBasic.getName()))throw new PersonBasicServiceException("The name can't be null or empty.");
		if(personBasic.getCtime()==null) personBasic.setCtime(System.currentTimeMillis());
		if(personBasic.getUtime()==null) personBasic.setUtime(System.currentTimeMillis());
		if(type==1)personBasic.setUtime(System.currentTimeMillis());
		if(!StringUtils.isEmpty(personBasic.getName()))personBasic.setPinyin(PinyinUtils.stringToHeads(personBasic.getName()));
		return personBasic;
	}
	
	@Override
	public Long createPersonBasic(PersonBasic personBasic)throws PersonBasicServiceException,UserLoginRegisterServiceException{
		try {
			Long id=(Long)saveEntity(checkValidity(personBasic,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new PersonBasicServiceException("createPersonBasic failed.");
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonBasicServiceException(e);
		}
	}

	@Override
	public boolean updatePersonBasic(PersonBasic personBasic)throws PersonBasicServiceException,UserLoginRegisterServiceException {
		try {
			if(updateEntity(checkValidity(personBasic,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonBasicServiceException(e);
		}
	}

	@Override
	public PersonBasic getPersonBasic(Long id) throws PersonBasicServiceException {
		try {
			if(id==null || id<=0l)throw new PersonBasicServiceException("id is null or empty");
			PersonBasic personBasic =getEntity(id);
			if(!ObjectUtils.isEmpty(personBasic)){
				try {
					if(!ObjectUtils.isEmpty(personBasic.getPicId())){
						FileIndex fileIndex=fileIndexService.getFileIndexById(personBasic.getPicId());
						if(!ObjectUtils.isEmpty(fileIndex)){
							personBasic.setPicPath(fileIndex.getServerHost()+"/"+fileIndex.getFilePath());
						}
					}
				} catch (FileIndexServiceException e) {
					e.printStackTrace();
				}
			}
			return personBasic;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonBasicServiceException(e);
		}
	}

	@Override
	public List<PersonBasic> getPersonBasicList(List<Long> ids)throws PersonBasicServiceException {
		try {
			if(ids==null || ids.size()==0)throw new PersonBasicServiceException("userIds is null or empty");
			return getEntityByIds(ids);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonBasicServiceException(e);
		}
	}

	@Override
	public Boolean realDeletePersonBasic(Long id)throws PersonBasicServiceException {
		try {
			if(id==null || id<=0l) throw new PersonBasicServiceException("id is must grater than zero.");
			return deleteEntity(id);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonBasicServiceException(e);
		}
	}
}
