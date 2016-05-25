package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.model.UserEducationHistory;
import com.ginkgocap.parasol.user.service.UserEducationHistoryService;
@Service("userEducationHistoryService")
public class UserEducationHistoryServiceImpl extends BaseService<UserEducationHistory> implements UserEducationHistoryService {
	private UserEducationHistory checkValidity(UserEducationHistory UserEducationHistory,int type)throws Exception {
		if(UserEducationHistory==null) throw new Exception("UserEducationHistory can not be null.");
		if(UserEducationHistory.getUserId()<0l) throw new Exception("The value of userId is null or empty.");
		if(type!=0)
		if(getObject(UserEducationHistory.getId())==null)throw new Exception("userId not exists in UserEducationHistory");
		if(UserEducationHistory.getCtime()==null) UserEducationHistory.setCtime(System.currentTimeMillis());
		if(UserEducationHistory.getUtime()==null) UserEducationHistory.setUtime(System.currentTimeMillis());
		if(type==1)UserEducationHistory.setUtime(System.currentTimeMillis());
		return UserEducationHistory;
	}
	@Override
	public Long createObject(UserEducationHistory object) throws Exception {
		Long id= (Long)this.saveEntity(this.checkValidity(object, 0));
		if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
		else throw new Exception("创建失败！ ");
	}

	@Override
	public Boolean updateObject(UserEducationHistory objcet) throws Exception {
		if(updateEntity(checkValidity(objcet,1)))return true;
		else return false;
	}

	@Override
	public UserEducationHistory getObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		UserEducationHistory UserEducationHistory =getEntity(id);
		return UserEducationHistory;
	}

	@Override
	public List<UserEducationHistory> getObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		List<UserEducationHistory> UserEducationHistorys =this.getEntityByIds(ids);
		return UserEducationHistorys;
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		return this.deleteEntity(id);
	}
	@Override
	public List<UserEducationHistory> createObjects(List<UserEducationHistory> objects)
			throws Exception {
		if(objects==null || objects.size()<=0) return null;
		for(UserEducationHistory UserEducationHistory : objects){
			this.checkValidity(UserEducationHistory, 0);
		}
		return this.saveEntitys(objects);
	}
	@Override
	public List<UserEducationHistory> getObjectsByUserId(Long userId) throws Exception {
		if(userId==null || userId<=0l)throw new Exception("id is null or empty");
		List<Long> ids = this.getIds("UserEducationHistory_List_UserId", userId);
		return this.getEntityByIds(ids);
	}
	@Override
	public Boolean deleteObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		return this.deleteEntityByIds(ids);
	}
	@Override
	public Boolean updateObjects(List<UserEducationHistory> objects) throws Exception {
		if(objects==null || objects.size()<=0) return true;
		for(UserEducationHistory UserEducationHistory : objects){
			this.checkValidity(UserEducationHistory, 1);
		}
		return this.updateEntitys(objects);
	}

}
