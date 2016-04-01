package com.ginkgocap.parasol.user.service.impl;

import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.file.exception.FileIndexServiceException;
import com.ginkgocap.parasol.file.model.FileIndex;
import com.ginkgocap.parasol.metadata.exception.CodeServiceException;
import com.ginkgocap.parasol.metadata.model.Code;
import com.ginkgocap.parasol.metadata.service.CodeService;
import com.ginkgocap.parasol.user.exception.UserInterestIndustryServiceException;
import com.ginkgocap.parasol.user.model.UserInterestIndustry;
import com.ginkgocap.parasol.user.service.UserInterestIndustryService;
@Service("userInterestIndustryService")
public class UserInterestIndustryServiceImpl extends BaseService<UserInterestIndustry> implements UserInterestIndustryService {
	private static final String USER_INTEREST_INDUSTRY_LIST_USERID = "UserInterestIndustry_List_UserId";
	private static final String UserInterestIndustry_List_Id_FirstIndustryId = "UserInterestIndustry_List_Id_FirstIndustryId";
	@Resource
	private CodeService codeService;
	private static Logger logger = Logger.getLogger(UserInterestIndustryServiceImpl.class);
	
	/**
	 * 检查数据
	 * @param list
	 * @return
	 * @throws UserInterestIndustryServiceException
	 */
	private List<UserInterestIndustry> checkValidity(List<UserInterestIndustry> list)throws UserInterestIndustryServiceException{
		if(list==null || list.size()==0) throw new UserInterestIndustryServiceException("UserInterestIndustry is null");
		for (UserInterestIndustry userInterestIndustry : list) {
			if(userInterestIndustry.getUserId()==null ||userInterestIndustry.getUserId()<=0l) throw new UserInterestIndustryServiceException("userId must be a value");
			if(StringUtils.isEmpty(userInterestIndustry.getIp())) throw new UserInterestIndustryServiceException("ip is null or empty");
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
			List<UserInterestIndustry> serInterestIndustrys=saveEntitys(list);
			if(serInterestIndustrys==null || serInterestIndustrys.size()==0) throw new UserInterestIndustryServiceException("createUserInterestIndustryByList failed.");
			return serInterestIndustrys;
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

	@Override
	public List<UserInterestIndustry> getInterestIndustryListBy(List<Long> ids)throws UserInterestIndustryServiceException {
		try {
			if(ids==null || ids.size()==0) return Collections.EMPTY_LIST;
			List<UserInterestIndustry> list=getEntityByIds(ids);
			if(list!=null &&  list.size()!=0){
				for (UserInterestIndustry userInterestIndustry : list) {
					Code code=null;
					try {
						code = codeService.getCode(userInterestIndustry.getId());
					} catch (CodeServiceException e) {
						e.printStackTrace();
					}
					if(!ObjectUtils.isEmpty(code)){
						userInterestIndustry.setFirstIndustryName(code.getName());
					}
				}
			}
			return list;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new UserInterestIndustryServiceException(e);
		}
	}
}
