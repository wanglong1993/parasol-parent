package com.ginkgocap.parasol.user.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.user.model.UserFamilyMember;
import com.ginkgocap.parasol.user.service.UserFamilyMemberService;
@Service("userFamilyMemberService")
public class UserFamilyMemberServiceImpl extends BaseService<UserFamilyMember> implements
			 UserFamilyMemberService {
	private UserFamilyMember checkValidity(UserFamilyMember UserFamilyMember,int type)throws Exception {
		if(UserFamilyMember==null) throw new Exception("UserFamilyMember can not be null.");
		if(UserFamilyMember.getUserId()<0l) throw new Exception("The value of userId is null or empty.");
		if(type!=0)
		if(getObject(UserFamilyMember.getId())==null)throw new Exception("userId not exists in UserFamilyMember");
		if(UserFamilyMember.getCtime()==null) UserFamilyMember.setCtime(System.currentTimeMillis());
		if(UserFamilyMember.getUtime()==null) UserFamilyMember.setUtime(System.currentTimeMillis());
		if(type==1)UserFamilyMember.setUtime(System.currentTimeMillis());
		return UserFamilyMember;
	}
	@Override
	public Long createObject(UserFamilyMember object) throws Exception {
		Long id= (Long)this.saveEntity(this.checkValidity(object, 0));
		if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
		else throw new Exception("创建失败！ ");
	}

	@Override
	public Boolean updateObject(UserFamilyMember objcet) throws Exception {
		if(updateEntity(checkValidity(objcet,1)))return true;
		else return false;
	}

	@Override
	public UserFamilyMember getObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		UserFamilyMember UserFamilyMember =getEntity(id);
		return UserFamilyMember;
	}

	@Override
	public List<UserFamilyMember> getObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		List<UserFamilyMember> UserFamilyMembers =this.getEntityByIds(ids);
		return UserFamilyMembers;
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		return this.deleteEntity(id);
	}
	@Override
	public List<UserFamilyMember> createObjects(List<UserFamilyMember> objects)
			throws Exception {
		if(objects==null || objects.size()<=0) return null;
		for(UserFamilyMember UserFamilyMember : objects){
			this.checkValidity(UserFamilyMember, 0);
		}
		return this.saveEntitys(objects);
	}
	@Override
	public List<UserFamilyMember> getObjectsByUserId(Long userId) throws Exception {
		if(userId==null || userId<=0l)throw new Exception("id is null or empty");
		List<Long> ids = this.getIds("UserFamilyMember_List_UserId", userId);
		return this.getEntityByIds(ids);
	}
	@Override
	public Boolean deleteObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		return this.deleteEntityByIds(ids);
	}
	@Override
	public Boolean updateObjects(List<UserFamilyMember> objects) throws Exception {
		if(objects==null || objects.size()<=0) return true;
		for(UserFamilyMember UserFamilyMember : objects){
			this.checkValidity(UserFamilyMember, 1);
		}
		return this.updateEntitys(objects);
	}
	
	@Override
	public Boolean deleteObjectsByUserId(Long userId) throws Exception {
		if(userId==null) throw new Exception("userId is null or empty");
		this.deleteList("UserFamilyMember_List_UserId", userId);
		return true;
	}

}
