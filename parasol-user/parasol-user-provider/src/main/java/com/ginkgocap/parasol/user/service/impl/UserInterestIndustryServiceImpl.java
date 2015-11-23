package com.ginkgocap.parasol.user.service.impl;

import java.util.Date;
import java.util.List;

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
	private static int error_userId_already_exists = 1002;
	private static final String USER_INTEREST_INDUSTRY_MAP_USERID = "UserInterestIndustry_Map_UserId";
	private static final String UserInterestIndustry_List_Id_FirstIndustryId = "UserInterestIndustry_List_Id_FirstIndustryId";
	private static final String UserInterestIndustry_List_Id_SecondIndustryId = "UserInterestIndustry_List_Id_SecondIndustryId";
	private static final String UserInterestIndustry_List_Id_ThirdIndustryId = "UserInterestIndustry_List_Id_ThirdIndustryId";
	private static Logger logger = Logger.getLogger(UserInterestIndustryServiceImpl.class);
	@Override
	public Long createUserInterestIndustry(UserInterestIndustry userInterestIndustry) throws UserInterestIndustryServiceException {
		try {
			if(userInterestIndustry==null) throw new UserInterestIndustryServiceException(error_userInterestIndustry_null,"UserInterestIndustry is null");
			if(userInterestIndustry!=null && userInterestIndustry.getUserId()==0) throw new UserInterestIndustryServiceException(error_userId_null,"userId must be a value");
			// 检查用户id是否存在
			if (userIdExists(userInterestIndustry.getUserId())) throw new UserInterestIndustryServiceException(error_userId_already_exists,"userId already exists");
			return (Long) saveEntity(userInterestIndustry);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserInterestIndustryServiceException(e);
		}
	}
	@Override
	public boolean updateUserInterestIndustry(Long id, Long firstIndustryId,Long secondIndustryId, Long thirdIndustryId, String ip)throws UserInterestIndustryServiceException {
		try {
			if((id==null || id<=0l)) return false;
			UserInterestIndustry userInterestIndustry=getEntity(id);
			if(userInterestIndustry!=null){
				if(firstIndustryId!=null && firstIndustryId > 0l)userInterestIndustry.setFirstIndustryId(firstIndustryId);
				if(secondIndustryId!=null && secondIndustryId > 0l)userInterestIndustry.setSecondIndustryId(secondIndustryId);
				if(thirdIndustryId!=null && thirdIndustryId >0l)userInterestIndustry.setThirdIndustryId(thirdIndustryId);
				userInterestIndustry.setIp(ip);
				userInterestIndustry.setUtime(new Date());
			}
			return updateEntity(userInterestIndustry);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserInterestIndustryServiceException(e);
		}
	}
	@Override
	public Long getId(Long userId) throws UserInterestIndustryServiceException {
		try {
			if((userId==null || userId<=0l)) return null;
			return (Long)getMapId(USER_INTEREST_INDUSTRY_MAP_USERID,userId);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserInterestIndustryServiceException(e);
		}
	}
	@Override
	public List<Long> getUserIdList(int start,int count,Long firstInterestId, Long secondInterestId,Long thireInterestId,int level) throws UserInterestIndustryServiceException {
		try {
			if(level==1)return (List<Long>)getIds(UserInterestIndustry_List_Id_FirstIndustryId, start, count, new Object[]{firstInterestId});
			if(level==2)return (List<Long>)getIds(UserInterestIndustry_List_Id_SecondIndustryId, start, count, new Object[]{secondInterestId});
			if(level==3)return (List<Long>)getIds(UserInterestIndustry_List_Id_ThirdIndustryId, start, count, new Object[]{thireInterestId});
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserInterestIndustryServiceException(e);
		}
		return null;
	}
	@Override
	public boolean userIdExists(Long userId) throws UserInterestIndustryServiceException {
		try {
			if((userId==null || userId<=0l)) return false;
			return (Long)getMapId(USER_INTEREST_INDUSTRY_MAP_USERID,userId)==null?false:true;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserInterestIndustryServiceException(e);
		}
	}
}
