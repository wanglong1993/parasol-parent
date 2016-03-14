package com.ginkgocap.parasol.person.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.person.exception.PersonEducationHistoryServiceException;
import com.ginkgocap.parasol.person.model.PersonEducationHistory;
import com.ginkgocap.parasol.person.service.PersonEducationHistoryService;
@Service("personEducationHistoryService")
public class PersonEducationHistoryServiceImpl extends BaseService<PersonEducationHistory> implements PersonEducationHistoryService {
	private static final String PERSON_EDUCATION_HISTORY_LIST_PERSONID = "PersonEducationHistory_List_PersonId";
	private static Logger logger = Logger.getLogger(PersonEducationHistoryServiceImpl.class);
	
	/**
	 * 检查数据
	 * @param list
	 * @return
	 * @throws PersonEducationHistoryServiceException
	 */
	private List<PersonEducationHistory> checkValidity(List<PersonEducationHistory> list)throws PersonEducationHistoryServiceException{
		if(list==null || list.size()==0) throw new PersonEducationHistoryServiceException("PersonEducationHistory is null");
		for (PersonEducationHistory userDefined : list) {
			if(userDefined.getPersonId()==null ||userDefined.getPersonId()<=0l) throw new PersonEducationHistoryServiceException("personId must be a value");
			if(StringUtils.isEmpty(userDefined.getIp())) throw new PersonEducationHistoryServiceException("ip is null or empty");
			if(userDefined.getCtime()==null ||userDefined.getCtime()<=0l)userDefined.setCtime(System.currentTimeMillis());
			if(userDefined.getUtime()==null ||userDefined.getUtime()<=0l)userDefined.setUtime(System.currentTimeMillis());
		}
		return list;
	}
	 
	@Override
	public List<PersonEducationHistory> createPersonEducationHistoryByList(List<PersonEducationHistory> list,Long personId) throws PersonEducationHistoryServiceException {
		try {
			checkValidity(list);
			//删除以前的
			deleteEntityByIds(getIdList(personId));
			List<PersonEducationHistory> userDefineds=saveEntitys(list);
			if(userDefineds==null || userDefineds.size()==0) throw new PersonEducationHistoryServiceException("createPersonEducationHistoryByList failed.");
			return userDefineds;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonEducationHistoryServiceException(e);
		}
	}
	@Override
	public List<Long> getIdList(Long personId) throws PersonEducationHistoryServiceException {
		try {
			if((personId==null || personId<=0l)) return ListUtils.EMPTY_LIST;
			return getIds(PERSON_EDUCATION_HISTORY_LIST_PERSONID,personId);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonEducationHistoryServiceException(e);
		}
	}
	@Override
	public boolean realDeletePersonEducationHistoryList(List<Long> list)throws PersonEducationHistoryServiceException {
		try {
			if(list==null || list.size()==0) return false;
			return deleteEntityByIds(list);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonEducationHistoryServiceException(e);
		}
	}

	@Override
	public List<PersonEducationHistory> getIdList(List<Long> ids)throws PersonEducationHistoryServiceException {
		try {
			if(ids==null || ids.size()==0) return Collections.EMPTY_LIST;
			return getEntityByIds(ids);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new PersonEducationHistoryServiceException(e);
		}
	}
}
