package com.ginkgocap.parasol.person.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.person.model.PersonFamilyMember;
import com.ginkgocap.parasol.person.service.PersonFamilyMemberService;
@Service("personFamilyMemberService")
public class PersonFamilyMemberServiceImpl extends BaseService<PersonFamilyMember> implements
			 PersonFamilyMemberService {
	private PersonFamilyMember checkValidity(PersonFamilyMember PersonFamilyMember,int type)throws Exception {
		if(PersonFamilyMember==null) throw new Exception("PersonFamilyMember can not be null.");
		if(PersonFamilyMember.getPersonId()<0l) throw new Exception("The value of personId is null or empty.");
		if(type!=0)
		if(getObject(PersonFamilyMember.getId())==null)throw new Exception("personId not exists in PersonFamilyMember");
		if(PersonFamilyMember.getCtime()==null) PersonFamilyMember.setCtime(System.currentTimeMillis());
		if(PersonFamilyMember.getUtime()==null) PersonFamilyMember.setUtime(System.currentTimeMillis());
		if(type==1)PersonFamilyMember.setUtime(System.currentTimeMillis());
		return PersonFamilyMember;
	}
	@Override
	public Long createObject(PersonFamilyMember object) throws Exception {
		Long id= (Long)this.saveEntity(this.checkValidity(object, 0));
		if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
		else throw new Exception("创建失败！ ");
	}

	@Override
	public Boolean updateObject(PersonFamilyMember objcet) throws Exception {
		if(updateEntity(checkValidity(objcet,1)))return true;
		else return false;
	}

	@Override
	public PersonFamilyMember getObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		PersonFamilyMember PersonFamilyMember =getEntity(id);
		return PersonFamilyMember;
	}

	@Override
	public List<PersonFamilyMember> getObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		List<PersonFamilyMember> PersonFamilyMembers =this.getEntityByIds(ids);
		return PersonFamilyMembers;
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		return this.deleteEntity(id);
	}
	@Override
	public List<PersonFamilyMember> createObjects(List<PersonFamilyMember> objects)
			throws Exception {
		if(objects==null || objects.size()<=0) return null;
		for(PersonFamilyMember PersonFamilyMember : objects){
			this.checkValidity(PersonFamilyMember, 0);
		}
		return this.saveEntitys(objects);
	}
	@Override
	public List<PersonFamilyMember> getObjectsByPersonId(Long personId) throws Exception {
		if(personId==null || personId<=0l)throw new Exception("id is null or empty");
		List<Long> ids = this.getIds("PersonFamilyMember_List_PersonId", personId);
		return this.getEntityByIds(ids);
	}
	@Override
	public Boolean deleteObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		return this.deleteEntityByIds(ids);
	}
	@Override
	public Boolean updateObjects(List<PersonFamilyMember> objects) throws Exception {
		if(objects==null || objects.size()<=0) return true;
		for(PersonFamilyMember PersonFamilyMember : objects){
			this.checkValidity(PersonFamilyMember, 1);
		}
		return this.updateEntitys(objects);
	}
	
	@Override
	public Boolean deleteObjectsByPersonId(Long personId) throws Exception {
		if(personId==null) throw new Exception("personId is null or empty");
		this.deleteList("PersonFamilyMember_List_PersonId", personId);
		return true;
	}

}
