package com.ginkgocap.parasol.person.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.person.model.PersonDefined;
import com.ginkgocap.parasol.person.service.PersonDefinedService;
@Service("personDefinedService")
public class PersonDefinedServiceImpl extends BaseService<PersonDefined> implements PersonDefinedService {
	private PersonDefined checkValidity(PersonDefined personDefined,int type)throws Exception {
		if(personDefined==null) throw new Exception("PersonDefined can not be null.");
		if(personDefined.getPersonId()<0l) throw new Exception("The value of personId is null or empty.");
		if(type!=0)
		if(getObject(personDefined.getId())==null)throw new Exception("personId not exists in PersonDefined");
//		if(StringUtils.isEmpty(personBasic.getName()))throw new PersonBasicServiceException("The value of  name is null or empty.");
		if(personDefined.getCtime()==null) personDefined.setCtime(System.currentTimeMillis());
		if(personDefined.getUtime()==null) personDefined.setUtime(System.currentTimeMillis());
		if(type==1)personDefined.setUtime(System.currentTimeMillis());
		return personDefined;
	}
	@Override
	public Long createObject(PersonDefined object) throws Exception {
		Long id= (Long)this.saveEntity(this.checkValidity(object, 0));
		if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
		else throw new Exception("创建用户联系失败！ ");
	}

	@Override
	public Boolean updateObject(PersonDefined objcet) throws Exception {
		if(updateEntity(checkValidity(objcet,1)))return true;
		else return false;
	}

	@Override
	public PersonDefined getObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		PersonDefined PersonDefined =getEntity(id);
		return PersonDefined;
	}

	@Override
	public List<PersonDefined> getObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		List<PersonDefined> PersonDefineds =this.getEntityByIds(ids);
		return PersonDefineds;
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		return this.deleteEntity(id);
	}
	@Override
	public List<PersonDefined> createObjects(List<PersonDefined> objects)
			throws Exception {
		if(objects==null || objects.size()<=0) return null;
		for(PersonDefined PersonDefined : objects){
			this.checkValidity(PersonDefined, 0);
		}
		return this.saveEntitys(objects);
	}
	@Override
	public List<PersonDefined> getObjectsByPersonId(Long personId) throws Exception {
		if(personId==null || personId<=0l)throw new Exception("id is null or empty");
		List<Long> ids = this.getIds("PersonDefined_List_PersonId", personId);
		return this.getEntityByIds(ids);
	}
	@Override
	public Boolean deleteObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		return this.deleteEntityByIds(ids);
	}
	@Override
	public Boolean updateObjects(List<PersonDefined> objects) throws Exception {
		if(objects==null || objects.size()<=0) return true;
		for(PersonDefined PersonDefined : objects){
			this.checkValidity(PersonDefined, 1);
		}
		return this.updateEntitys(objects);
	}
	@Override
	public Boolean deleteObjectsByPersonId(Long personId) throws Exception {
		if(personId==null) throw new Exception("personId is null or empty");
		this.deleteList("PersonDefined_List_PersonId", personId);
		return true;
	}


}
