package com.ginkgocap.parasol.user.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserEducationHistoryServiceException;
import com.ginkgocap.parasol.user.model.UserEducationHistory;
import com.ginkgocap.parasol.user.service.UserEducationHistoryService;
@Service("userEducationHistoryService")
public class UserEducationHistoryServiceImpl extends BaseService<UserEducationHistory> implements UserEducationHistoryService {
	private static final String USER_EDUCATION_HISTORY_LIST_USERID = "UserEducationHistory_List_UserId";
	private static Logger logger = Logger.getLogger(UserEducationHistoryServiceImpl.class);
	
	/**
	 * 检查数据
	 * @param list
	 * @return
	 * @throws UserEducationHistoryServiceException
	 */
	private List<UserEducationHistory> checkValidity(List<UserEducationHistory> list)throws UserEducationHistoryServiceException{
		if(list==null || list.size()==0) throw new UserEducationHistoryServiceException("UserEducationHistory is null");
		for (UserEducationHistory userDefined : list) {
			if(userDefined.getUserId()==null ||userDefined.getUserId()<=0l) throw new UserEducationHistoryServiceException("userId must be a value");
			if(StringUtils.isEmpty(userDefined.getIp())) throw new UserEducationHistoryServiceException("ip is null or empty");
			if(userDefined.getCtime()==null ||userDefined.getCtime()<=0l)userDefined.setCtime(System.currentTimeMillis());
			if(userDefined.getUtime()==null ||userDefined.getUtime()<=0l)userDefined.setUtime(System.currentTimeMillis());
		}
		return list;
	}
	 
	@Override
	public List<UserEducationHistory> createUserEducationHistoryByList(List<UserEducationHistory> list,Long userId) throws UserEducationHistoryServiceException {
		try {
			checkValidity(list);
			//删除以前的
			deleteEntityByIds(getIdList(userId));
			List<UserEducationHistory> userEducationHistorys=saveEntitys(list);
			if(userEducationHistorys==null || userEducationHistorys.size()==0) throw new UserEducationHistoryServiceException("createUserEducationHistoryByList failed.");
			return userEducationHistorys;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserEducationHistoryServiceException(e);
		}
	}
	@Override
	public List<UserEducationHistory> updateUserEducationHistoryByList(List<UserEducationHistory> list,Long userId) throws UserEducationHistoryServiceException {
		try {
			checkValidity(list);
			//删除以前的
			deleteEntityByIds(getIdList(userId));
			List<UserEducationHistory> userEducationHistorys=saveEntitys(list);
			if(userEducationHistorys==null || userEducationHistorys.size()==0) throw new UserEducationHistoryServiceException("updateUserEducationHistoryByList failed.");
			return userEducationHistorys;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserEducationHistoryServiceException(e);
		}
	}
	@Override
	public List<Long> getIdList(Long userId) throws UserEducationHistoryServiceException {
		try {
			if((userId==null || userId<=0l)) return ListUtils.EMPTY_LIST;
			return getIds(USER_EDUCATION_HISTORY_LIST_USERID,userId);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserEducationHistoryServiceException(e);
		}
	}
	@Override
	public boolean realDeleteUserEducationHistoryList(List<Long> list)throws UserEducationHistoryServiceException {
		try {
			if(list==null || list.size()==0) return false;
			return deleteEntityByIds(list);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserEducationHistoryServiceException(e);
		}
	}

	@Override
	public List<UserEducationHistory> getIdList(List<Long> ids)throws UserEducationHistoryServiceException {
		try {
			if(ids==null || ids.size()==0) return Collections.EMPTY_LIST;
			return getEntityByIds(ids);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserEducationHistoryServiceException(e);
		}
	}
}
