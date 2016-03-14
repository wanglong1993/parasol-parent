package com.ginkgocap.parasol.user.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.person.exception.PersonDefinedServiceException;
import com.ginkgocap.parasol.person.model.PersonDefined;
import com.ginkgocap.parasol.person.service.PersonDefinedService;
@Service("personDefinedService")
public class PersonDefinedServiceImpl extends BaseService<PersonDefined> implements PersonDefinedService {
	private static final String PERSON_DEFINED_LIST_PERSONID = "PersonDefined_List_PersonId";
	private static Logger logger = Logger.getLogger(PersonDefinedServiceImpl.class);
	
	/**
	 * 检查数据
	 * @param list
	 * @return
	 * @throws PersonDefinedServiceException
	 */
	private List<PersonDefined> checkValidity(List<PersonDefined> list)throws PersonDefinedServiceException{
		if(list==null || list.size()==0) throw new PersonDefinedServiceException("PersonDefined is null");
		for (PersonDefined userDefined : list) {
			if(userDefined.getPersonId()==null ||userDefined.getPersonId()<=0l) throw new PersonDefinedServiceException("personId must be a value");
			if(StringUtils.isEmpty(userDefined.getIp())) throw new PersonDefinedServiceException("ip is null or empty");
			if(userDefined.getCtime()==null ||userDefined.getCtime()<=0l)userDefined.setCtime(System.currentTimeMillis());
			if(userDefined.getUtime()==null ||userDefined.getUtime()<=0l)userDefined.setUtime(System.currentTimeMillis());
		}
		return list;
	}
	 
	@Override
	public List<PersonDefined> createPersonDefinedByList(List<PersonDefined> list,Long personId) throws PersonDefinedServiceException {
		try {
			checkValidity(list);
			//删除以前的
			deleteEntityByIds(getIdList(personId));
			List<PersonDefined> userDefineds=saveEntitys(list);
			if(userDefineds==null || userDefineds.size()==0) throw new PersonDefinedServiceException("createPersonDefinedByList failed.");
			return userDefineds;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonDefinedServiceException(e);
		}
	}
	@Override
	public List<Long> getIdList(Long personId) throws PersonDefinedServiceException {
		try {
			if((personId==null || personId<=0l)) return ListUtils.EMPTY_LIST;
			return getIds(PERSON_DEFINED_LIST_PERSONID,personId);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonDefinedServiceException(e);
		}
	}
	@Override
	public boolean realDeletePersonDefinedList(List<Long> list)throws PersonDefinedServiceException {
		try {
			if(list==null || list.size()==0) return false;
			return deleteEntityByIds(list);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonDefinedServiceException(e);
		}
	}

	@Override
	public List<PersonDefined> getIdList(List<Long> ids)throws PersonDefinedServiceException {
		try {
			if(ids==null || ids.size()==0) return Collections.EMPTY_LIST;
			return getEntityByIds(ids);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonDefinedServiceException(e);
		}
	}
}
