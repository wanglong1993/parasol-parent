package com.ginkgocap.parasol.user.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserWorkHistoryServiceException;
import com.ginkgocap.parasol.user.model.UserWorkHistory;
import com.ginkgocap.parasol.user.service.UserWorkHistoryService;
@Service("userWorkHistoryService")
public class UserWorkHistoryServiceImpl extends BaseService<UserWorkHistory> implements UserWorkHistoryService {
	private static final String USER_WORK_HISTORY_LIST_USERID = "UserWorkHistory_List_UserId";
	private static Logger logger = Logger.getLogger(UserWorkHistoryServiceImpl.class);
	
	/**
	 * 检查数据
	 * @param list
	 * @return
	 * @throws UserWorkHistoryServiceException
	 */
	private List<UserWorkHistory> checkValidity(List<UserWorkHistory> list)throws UserWorkHistoryServiceException{
		if(list==null || list.size()==0) throw new UserWorkHistoryServiceException("UserWorkHistory is null");
		for (UserWorkHistory userDefined : list) {
			if(userDefined.getUserId()==null ||userDefined.getUserId()<=0l) throw new UserWorkHistoryServiceException("userId must be a value");
			if(StringUtils.isEmpty(userDefined.getIp())) throw new UserWorkHistoryServiceException("ip is null or empty");
			if(userDefined.getCtime()==null ||userDefined.getCtime()<=0l)userDefined.setCtime(System.currentTimeMillis());
			if(userDefined.getUtime()==null ||userDefined.getUtime()<=0l)userDefined.setUtime(System.currentTimeMillis());
		}
		return list;
	}
	 
	@Override
	public List<UserWorkHistory> createUserWorkHistoryByList(List<UserWorkHistory> list,Long userId) throws UserWorkHistoryServiceException {
		try {
			checkValidity(list);
			//删除以前的
			deleteEntityByIds(getIdList(userId));
			List<UserWorkHistory> userWorkHistorys=saveEntitys(list);
			if(userWorkHistorys==null || userWorkHistorys.size()==0) throw new UserWorkHistoryServiceException("createUserWorkHistoryByList failed.");
			return userWorkHistorys;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserWorkHistoryServiceException(e);
		}
	}
	@Override
	public List<UserWorkHistory> updateUserWorkHistoryByList(List<UserWorkHistory> list,Long userId) throws UserWorkHistoryServiceException {
		try {
			checkValidity(list);
			//删除以前的
			deleteEntityByIds(getIdList(userId));
			List<UserWorkHistory> userWorkHistorys=saveEntitys(list);
			if(userWorkHistorys==null || userWorkHistorys.size()==0) throw new UserWorkHistoryServiceException("updateUserWorkHistoryByList failed.");
			return userWorkHistorys;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserWorkHistoryServiceException(e);
		}
	}
	@Override
	public List<Long> getIdList(Long userId) throws UserWorkHistoryServiceException {
		try {
			if((userId==null || userId<=0l)) return ListUtils.EMPTY_LIST;
			return getIds(USER_WORK_HISTORY_LIST_USERID,userId);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserWorkHistoryServiceException(e);
		}
	}
	@Override
	public boolean realDeleteUserWorkHistoryList(List<Long> list)throws UserWorkHistoryServiceException {
		try {
			if(list==null || list.size()==0) return false;
			return deleteEntityByIds(list);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserWorkHistoryServiceException(e);
		}
	}

	@Override
	public List<UserWorkHistory> getIdList(List<Long> ids)throws UserWorkHistoryServiceException {
		try {
			if(ids==null || ids.size()==0) return Collections.EMPTY_LIST;
			return getEntityByIds(ids);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserWorkHistoryServiceException(e);
		}
	}
}
