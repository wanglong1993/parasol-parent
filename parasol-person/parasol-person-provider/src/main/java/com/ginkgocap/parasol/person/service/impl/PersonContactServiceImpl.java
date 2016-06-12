package com.ginkgocap.parasol.person.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.person.model.PersonContact;
import com.ginkgocap.parasol.person.service.PersonContactService;
@Service("personContactService")
public class PersonContactServiceImpl extends BaseService<PersonContact> implements
	PersonContactService {
	private PersonContact checkValidity(PersonContact personContact,int type)throws Exception {
		if(personContact==null) throw new Exception("personContact can not be null.");
		if(personContact.getPersonId()<0l) throw new Exception("The value of personId is null or empty.");
		if(type!=0)
		if(getObject(personContact.getId())==null)throw new Exception("id not exists in PersonContact");
//		if(StringUtils.isEmpty(personBasic.getName()))throw new PersonBasicServiceException("The value of  name is null or empty.");
		if(personContact.getCtime()==null) personContact.setCtime(System.currentTimeMillis());
		if(personContact.getUtime()==null) personContact.setUtime(System.currentTimeMillis());
		if(type==1)personContact.setUtime(System.currentTimeMillis());
		return personContact;
	}
	@Override
	public Long createObject(PersonContact object) throws Exception {
		Long id= (Long)this.saveEntity(this.checkValidity(object, 0));
		if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
		else throw new Exception("创建用户联系失败！ ");
	}

	@Override
	public Boolean updateObject(PersonContact objcet) throws Exception {
		if(updateEntity(checkValidity(objcet,1)))return true;
		else return false;
	}

	@Override
	public PersonContact getObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		PersonContact PersonContact =getEntity(id);
		return PersonContact;
	}

	@Override
	public List<PersonContact> getObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		List<PersonContact> PersonContacts =this.getEntityByIds(ids);
		return PersonContacts;
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		return this.deleteEntity(id);
	}
	@Override
	public List<PersonContact> createObjects(List<PersonContact> objects)
			throws Exception {
		if(objects==null || objects.size()<=0) return null;
		for(PersonContact personContact : objects){
			this.checkValidity(personContact, 0);
		}
		return this.saveEntitys(objects);
	}
	@Override
	public List<PersonContact> getObjectsByPersonId(Long personId) throws Exception {
		if(personId==null || personId<=0l)throw new Exception("id is null or empty");
		List<Long> ids = this.getIds("PersonContact_List_PersonId", personId);
		return this.getEntityByIds(ids);
	}
	@Override
	public Boolean deleteObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		return this.deleteEntityByIds(ids);
	}
	@Override
	public Boolean updateObjects(List<PersonContact> objects) throws Exception {
		if(objects==null || objects.size()<=0) return true;
		for(PersonContact personContact : objects){
			this.checkValidity(personContact, 1);
		}
		return this.updateEntitys(objects);
	}
	@Override
	public Boolean deleteObjectsByPersonId(Long personId) throws Exception {
		if(personId==null) throw new Exception("personId is null or empty");
		this.deleteList("PersonContact_List_PersonId", personId);
		return true;
	}


}
