package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.exception.UserInterestIndustryServiceException;
import com.ginkgocap.parasol.user.model.UserInterestIndustry;
import com.ginkgocap.parasol.user.service.UserInterestIndustryService;
@Service("userInterestIndustryService")
public class UserInterestIndustryServiceImpl extends BaseService<UserInterestIndustry> implements UserInterestIndustryService {
	private static int error_userInterestIndustry_null = 1000;	
	private static int error_userId_null = 1001;	
	private static int error_Ip_null = 1004;	
	private static final String USER_INTEREST_INDUSTRY_LIST_USERID = "UserInterestIndustry_List_UserId";
	private static final String UserInterestIndustry_List_Id_FirstIndustryId = "UserInterestIndustry_List_Id_FirstIndustryId";
	private static Logger logger = Logger.getLogger(UserInterestIndustryServiceImpl.class);
	
	/**
	 * 检查数据
	 * @param list
	 * @param type
	 * @return
	 * @throws UserInterestIndustryServiceException
	 */
	private List<UserInterestIndustry> checkValidity(List<UserInterestIndustry> list)throws UserInterestIndustryServiceException{
		if(list==null || list.size()==0) throw new UserInterestIndustryServiceException(error_userInterestIndustry_null,"UserInterestIndustry is null");
		for (UserInterestIndustry userInterestIndustry : list) {
			if(userInterestIndustry.getUserId()==null ||userInterestIndustry.getUserId()<=0l) throw new UserInterestIndustryServiceException(error_userId_null,"userId must be a value");
			if(StringUtils.isEmpty(userInterestIndustry.getIp())) throw new UserInterestIndustryServiceException(error_Ip_null,"ip is null or empty");
			if(userInterestIndustry.getCtime()==null ||userInterestIndustry.getCtime()<=0l)userInterestIndustry.setCtime(System.currentTimeMillis());
			if(userInterestIndustry.getUtime()==null ||userInterestIndustry.getUtime()<=0l)userInterestIndustry.setUtime(System.currentTimeMillis());
		}
		return list;
	}
	 
	@Override
	public List<UserInterestIndustry> createUserInterestIndustryByList(List<UserInterestIndustry> list,Long userId) throws UserInterestIndustryServiceException {
		try {
			checkValidity(list);
			//删除以前的
			deleteEntityByIds(getIdList(userId));
			return (List<UserInterestIndustry>) saveEntitys(list);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserInterestIndustryServiceException(e);
		}
	}
	@Override
	public List<Long> getUserIdListByIndustryId(int start,int count,Long firstInterestId) throws UserInterestIndustryServiceException {
		try {
			return (List<Long>)getIds(UserInterestIndustry_List_Id_FirstIndustryId, start, count, new Object[]{firstInterestId});
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserInterestIndustryServiceException(e);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<Long> getIdList(Long userId) throws UserInterestIndustryServiceException {
		try {
			if((userId==null || userId<=0l)) return ListUtils.EMPTY_LIST;
			return getIds(USER_INTEREST_INDUSTRY_LIST_USERID,userId);
//			List<Serializable> ids = new ArrayList<Serializable>();
//			for(Long id:getIds(USER_INTEREST_INDUSTRY_LIST_USERID,userId)){
//				ids.add(id);
//			}
//			return ids;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserInterestIndustryServiceException(e);
		}
	}
	@Override
	public boolean realDeleteUserInterestIndustryList(List<Long> list)throws UserInterestIndustryServiceException {
		try {
			if(list==null || list.size()==0) return false;
			return deleteEntityByIds(list);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserInterestIndustryServiceException(e);
		}
	}
	@Override
	public boolean idExists(Long id)throws UserInterestIndustryServiceException {
		try {
			if(getEntity(id)!=null)return true;
			else return false;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserInterestIndustryServiceException(e);
		}
	}
}
