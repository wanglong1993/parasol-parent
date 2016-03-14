package com.ginkgocap.parasol.person.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.person.exception.PersonInfoServiceException;
import com.ginkgocap.parasol.person.model.PersonInfo;
import com.ginkgocap.parasol.person.service.PersonInfoService;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
@Service("personInfoService")
public class PersonInfoServiceImpl extends BaseService<PersonInfo> implements PersonInfoService  {
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	private static Logger logger = Logger.getLogger(PersonInfoServiceImpl.class);
	/**
	 * 检查数据
	 * @param personInfo
	 * @return
	 * @throws PersonInfoServiceException
	 */
	private PersonInfo checkValidity(PersonInfo personInfo,int type)throws PersonInfoServiceException,UserLoginRegisterServiceException {
		if(personInfo==null) throw new PersonInfoServiceException("personInfo can not be null.");
		if(personInfo.getPersonId()<=0l) throw new PersonInfoServiceException("The value of personId is null or empty.");
		if(type!=0)
		if(getPersonInfo(personInfo.getPersonId())==null)throw new PersonInfoServiceException("personId not exists in personInfo");
		if(personInfo.getCtime()==null) personInfo.setCtime(System.currentTimeMillis());
		if(personInfo.getUtime()==null) personInfo.setUtime(System.currentTimeMillis());
		if(type==1)personInfo.setUtime(System.currentTimeMillis());
		return personInfo;
	}
	
	@Override
	public Long createPersonInfo(PersonInfo personInfo)throws PersonInfoServiceException,UserLoginRegisterServiceException{
		try {
			Long id=(Long)saveEntity(checkValidity(personInfo,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new PersonInfoServiceException("createPersonInfo failed.");
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonInfoServiceException(e);
		}
	}

	@Override
	public boolean updatePersonInfo(PersonInfo personInfo)throws PersonInfoServiceException,UserLoginRegisterServiceException {
		try {
			if(updateEntity(checkValidity(personInfo,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonInfoServiceException(e);
		}
	}

	@Override
	public PersonInfo getPersonInfo(Long id) throws PersonInfoServiceException {
		try {
			if(id==null || id<=0l)throw new PersonInfoServiceException("id is null or empty");
			PersonInfo personInfo =getEntity(id);
			return personInfo;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonInfoServiceException(e);
		}
	}

	@Override
	public List<PersonInfo> getPersonInfo(List<Long> ids)throws PersonInfoServiceException {
		try {
			if(ids==null || ids.size()==0)throw new PersonInfoServiceException("userIds is null or empty");
			return getEntityByIds(ids);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonInfoServiceException(e);
		}
	}

	@Override
	public Boolean realDeletePersonInfo(Long id)throws PersonInfoServiceException {
		try {
			if(id==null || id<=0l) throw new PersonInfoServiceException("id is must grater than zero.");
			return deleteEntity(id);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonInfoServiceException(e);
		}
	}
}
