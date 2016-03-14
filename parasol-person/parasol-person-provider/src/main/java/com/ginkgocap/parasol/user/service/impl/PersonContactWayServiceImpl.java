package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.file.service.FileIndexService;
import com.ginkgocap.parasol.person.exception.PersonContactWayServiceException;
import com.ginkgocap.parasol.person.model.PersonContactWay;
import com.ginkgocap.parasol.person.service.PersonContactWayService;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
@Service("personContactWayService")
public class PersonContactWayServiceImpl extends BaseService<PersonContactWay> implements PersonContactWayService  {
	@Resource
	private UserLoginRegisterService userLoginRegisterService;
	@Resource
	private FileIndexService fileIndexService;
	private static Logger logger = Logger.getLogger(PersonContactWayServiceImpl.class);
	/**
	 * 检查数据
	 * @param personContactWay
	 * @return
	 * @throws PersonContactWayServiceException
	 */
	private PersonContactWay checkValidity(PersonContactWay personContactWay,int type)throws PersonContactWayServiceException {
		if(personContactWay==null) throw new PersonContactWayServiceException("personContactWay can not be null.");
		if(personContactWay.getPersonId()<=0l) throw new PersonContactWayServiceException("The value of personId is null or empty.");
		if(type!=0)
		if(getPersonContactWay(personContactWay.getPersonId())==null)throw new PersonContactWayServiceException("userId not exists in personContactWay");
		if(personContactWay.getCtime()==null) personContactWay.setCtime(System.currentTimeMillis());
		if(personContactWay.getUtime()==null) personContactWay.setUtime(System.currentTimeMillis());
		if(type==1)personContactWay.setUtime(System.currentTimeMillis());
		return personContactWay;
	}
	
	@Override
	public Long createPersonContactWay(PersonContactWay personContactWay)throws PersonContactWayServiceException{
		try {
			Long id=(Long)saveEntity(checkValidity(personContactWay,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new PersonContactWayServiceException("createPersonContactWay failed.");
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonContactWayServiceException(e);
		}
	}

	@Override
	public boolean updatePersonContactWay(PersonContactWay personContactWay)throws PersonContactWayServiceException {
		try {
			if(updateEntity(checkValidity(personContactWay,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonContactWayServiceException(e);
		}
	}

	@Override
	public PersonContactWay getPersonContactWay(Long id) throws PersonContactWayServiceException {
		try {
			if(id==null || id<=0l)throw new PersonContactWayServiceException("id is null or empty");
			PersonContactWay personContactWay =getEntity(id);
			return personContactWay;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonContactWayServiceException(e);
		}
	}

	@Override
	public List<PersonContactWay> getPersonContactWayList(List<Long> ids)throws PersonContactWayServiceException {
		try {
			if(ids==null || ids.size()==0)throw new PersonContactWayServiceException("ids is null or empty");
			return getEntityByIds(ids);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonContactWayServiceException(e);
		}
	}

	@Override
	public Boolean realDeletePersonContactWay(Long id)throws PersonContactWayServiceException {
		try {
			if(id==null || id<=0l) throw new PersonContactWayServiceException("id is must grater than zero.");
			return deleteEntity(id);
		} catch (Exception e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonContactWayServiceException(e);
		}
	}
}
