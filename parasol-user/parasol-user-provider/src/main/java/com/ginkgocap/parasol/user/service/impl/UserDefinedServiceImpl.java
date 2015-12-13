package com.ginkgocap.parasol.user.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserDefinedServiceException;
import com.ginkgocap.parasol.user.model.UserDefined;
import com.ginkgocap.parasol.user.service.UserDefinedService;
@Service("userDefinedService")
public class UserDefinedServiceImpl extends BaseService<UserDefined> implements UserDefinedService {
	private static final String USER_DEFINED_LIST_USERID = "UserDefined_List_UserId";
	private static Logger logger = Logger.getLogger(UserDefinedServiceImpl.class);
	
	/**
	 * 检查数据
	 * @param list
	 * @return
	 * @throws UserDefinedServiceException
	 */
	private List<UserDefined> checkValidity(List<UserDefined> list)throws UserDefinedServiceException{
		if(list==null || list.size()==0) throw new UserDefinedServiceException("UserDefined is null");
		for (UserDefined userDefined : list) {
			if(userDefined.getUserId()==null ||userDefined.getUserId()<=0l) throw new UserDefinedServiceException("userId must be a value");
			if(StringUtils.isEmpty(userDefined.getIp())) throw new UserDefinedServiceException("ip is null or empty");
			if(userDefined.getCtime()==null ||userDefined.getCtime()<=0l)userDefined.setCtime(System.currentTimeMillis());
			if(userDefined.getUtime()==null ||userDefined.getUtime()<=0l)userDefined.setUtime(System.currentTimeMillis());
		}
		return list;
	}
	 
	@Override
	public List<UserDefined> createUserDefinedByList(List<UserDefined> list,Long userId) throws UserDefinedServiceException {
		try {
			checkValidity(list);
			//删除以前的
			deleteEntityByIds(getIdList(userId));
			List<UserDefined> userDefineds=saveEntitys(list);
			if(userDefineds==null || userDefineds.size()==0) throw new UserDefinedServiceException("createUserDefinedByList failed.");
			return userDefineds;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserDefinedServiceException(e);
		}
	}
	@Override
	public List<Long> getIdList(Long userId) throws UserDefinedServiceException {
		try {
			if((userId==null || userId<=0l)) return ListUtils.EMPTY_LIST;
			return getIds(USER_DEFINED_LIST_USERID,userId);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserDefinedServiceException(e);
		}
	}
	@Override
	public boolean realDeleteUserDefinedList(List<Long> list)throws UserDefinedServiceException {
		try {
			if(list==null || list.size()==0) return false;
			return deleteEntityByIds(list);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserDefinedServiceException(e);
		}
	}

	@Override
	public List<UserDefined> getIdList(List<Long> ids)throws UserDefinedServiceException {
		try {
			if(ids==null || ids.size()==0) return Collections.EMPTY_LIST;
			return getEntityByIds(ids);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserDefinedServiceException(e);
		}
	}
}
