package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.model.UserWorkHistory;
import com.ginkgocap.parasol.user.service.UserWorkHistoryService;
@Service("userWorkHistoryService")
public class UserWorkHistoryServiceImpl extends BaseService<UserWorkHistory> implements UserWorkHistoryService {
	private UserWorkHistory checkValidity(UserWorkHistory UserWorkHistory,int type)throws Exception {
		if(UserWorkHistory==null) throw new Exception("UserWorkHistory can not be null.");
		if(UserWorkHistory.getUserId()<0l) throw new Exception("The value of userId is null or empty.");
		if(type!=0)
		if(getObject(UserWorkHistory.getId())==null)throw new Exception("userId not exists in UserWorkHistory");
		if(UserWorkHistory.getCtime()==null) UserWorkHistory.setCtime(System.currentTimeMillis());
		if(UserWorkHistory.getUtime()==null) UserWorkHistory.setUtime(System.currentTimeMillis());
		if(type==1)UserWorkHistory.setUtime(System.currentTimeMillis());
		return UserWorkHistory;
	}
	@Override
	public Long createObject(UserWorkHistory object) throws Exception {
		Long id= (Long)this.saveEntity(this.checkValidity(object, 0));
		if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
		else throw new Exception("创建失败！ ");
	}
	
	@Override
	public Boolean deleteObjectsByUserId(Long userId) throws Exception {
		if(userId==null) throw new Exception("userId is null or empty");
		this.deleteList("UserWorkHistory_List_UserId", userId);
		return true;
	}

	@Override
	public Boolean updateObject(UserWorkHistory objcet) throws Exception {
		if(updateEntity(checkValidity(objcet,1)))return true;
		else return false;
	}

	@Override
	public UserWorkHistory getObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		UserWorkHistory UserWorkHistory =getEntity(id);
		return UserWorkHistory;
	}

	@Override
	public List<UserWorkHistory> getObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		List<UserWorkHistory> UserWorkHistorys =this.getEntityByIds(ids);
		return UserWorkHistorys;
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		return this.deleteEntity(id);
	}
	@Override
	public List<UserWorkHistory> createObjects(List<UserWorkHistory> objects)
			throws Exception {
		if(objects==null || objects.size()<=0) return null;
		for(UserWorkHistory UserWorkHistory : objects){
			this.checkValidity(UserWorkHistory, 0);
		}
		return this.saveEntitys(objects);
	}
	@Override
	public List<UserWorkHistory> getObjectsByUserId(Long userId) throws Exception {
		if(userId==null || userId<=0l)throw new Exception("id is null or empty");
		List<Long> ids = this.getIds("UserWorkHistory_List_UserId", userId);
		return this.getEntityByIds(ids);
	}
	@Override
	public Boolean deleteObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		return this.deleteEntityByIds(ids);
	}
	@Override
	public Boolean updateObjects(List<UserWorkHistory> objects) throws Exception {
		if(objects==null || objects.size()<=0) return true;
		for(UserWorkHistory UserWorkHistory : objects){
			this.checkValidity(UserWorkHistory, 1);
		}
		return this.updateEntitys(objects);
	}
}
