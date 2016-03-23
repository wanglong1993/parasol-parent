package com.ginkgocap.parasol.person.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.person.exception.PersonWorkHistoryServiceException;
import com.ginkgocap.parasol.person.model.PersonWorkHistory;
import com.ginkgocap.parasol.person.service.PersonWorkHistoryService;
@Service("personWorkHistoryService")
public class PersonWorkHistoryServiceImpl extends BaseService<PersonWorkHistory> implements PersonWorkHistoryService {
	private static final String PERSON_WORK_HISTORY_LIST_PERSONID = "PersonWorkHistory_List_PersonId";
	private static Logger logger = Logger.getLogger(PersonWorkHistoryServiceImpl.class);
	
	/**
	 * 检查数据
	 * @param list
	 * @return
	 * @throws PersonWorkHistoryServiceException
	 */
	private List<PersonWorkHistory> checkValidity(List<PersonWorkHistory> list)throws PersonWorkHistoryServiceException{
		if(list==null || list.size()==0) throw new PersonWorkHistoryServiceException("PersonWorkHistory is null");
		for (PersonWorkHistory userDefined : list) {
			if(userDefined.getPersonId()==null ||userDefined.getPersonId()<=0l) throw new PersonWorkHistoryServiceException("personId must be a value");
			if(StringUtils.isEmpty(userDefined.getIp())) throw new PersonWorkHistoryServiceException("ip is null or empty");
			if(userDefined.getCtime()==null ||userDefined.getCtime()<=0l)userDefined.setCtime(System.currentTimeMillis());
			if(userDefined.getUtime()==null ||userDefined.getUtime()<=0l)userDefined.setUtime(System.currentTimeMillis());
		}
		return list;
	}
	 
	@Override
	public List<PersonWorkHistory> createPersonWorkHistoryByList(List<PersonWorkHistory> list,Long personId) throws PersonWorkHistoryServiceException {
		try {
			checkValidity(list);
			//删除以前的
			deleteEntityByIds(getIdList(personId));
			List<PersonWorkHistory> personWorkHistorys=saveEntitys(list);
			if(personWorkHistorys==null || personWorkHistorys.size()==0) throw new PersonWorkHistoryServiceException("createPersonWorkHistoryByList failed.");
			return personWorkHistorys;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonWorkHistoryServiceException(e);
		}
	}
	@Override
	public List<PersonWorkHistory> updatePersonWorkHistoryByList(List<PersonWorkHistory> list,Long personId) throws PersonWorkHistoryServiceException {
		try {
			checkValidity(list);
			//删除以前的
			deleteEntityByIds(getIdList(personId));
			List<PersonWorkHistory> personWorkHistorys=saveEntitys(list);
			if(personWorkHistorys==null || personWorkHistorys.size()==0) throw new PersonWorkHistoryServiceException("updatePersonWorkHistoryByList failed.");
			return personWorkHistorys;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonWorkHistoryServiceException(e);
		}
	}
	@Override
	public List<Long> getIdList(Long personId) throws PersonWorkHistoryServiceException {
		try {
			if((personId==null || personId<=0l)) return ListUtils.EMPTY_LIST;
			return getIds(PERSON_WORK_HISTORY_LIST_PERSONID,personId);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonWorkHistoryServiceException(e);
		}
	}
	@Override
	public boolean realDeletePersonWorkHistoryList(List<Long> list)throws PersonWorkHistoryServiceException {
		try {
			if(list==null || list.size()==0) return false;
			return deleteEntityByIds(list);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonWorkHistoryServiceException(e);
		}
	}

	@Override
	public List<PersonWorkHistory> getIdList(List<Long> ids)throws PersonWorkHistoryServiceException {
		try {
			if(ids==null || ids.size()==0) return Collections.EMPTY_LIST;
			return getEntityByIds(ids);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonWorkHistoryServiceException(e);
		}
	}
}
