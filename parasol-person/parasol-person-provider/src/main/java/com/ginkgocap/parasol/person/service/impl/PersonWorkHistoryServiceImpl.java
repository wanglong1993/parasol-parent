package com.ginkgocap.parasol.person.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.person.model.PersonWorkHistory;
import com.ginkgocap.parasol.person.service.PersonWorkHistoryService;
@Service("personWorkHistoryService")
public class PersonWorkHistoryServiceImpl extends BaseService<PersonWorkHistory> implements PersonWorkHistoryService {
	private PersonWorkHistory checkValidity(PersonWorkHistory PersonWorkHistory,int type)throws Exception {
		if(PersonWorkHistory==null) throw new Exception("PersonWorkHistory can not be null.");
		if(PersonWorkHistory.getPersonId()<0l) throw new Exception("The value of personId is null or empty.");
		if(type!=0)
		if(getObject(PersonWorkHistory.getId())==null)throw new Exception("personId not exists in PersonWorkHistory");
		if(PersonWorkHistory.getCtime()==null) PersonWorkHistory.setCtime(System.currentTimeMillis());
		if(PersonWorkHistory.getUtime()==null) PersonWorkHistory.setUtime(System.currentTimeMillis());
		if(type==1)PersonWorkHistory.setUtime(System.currentTimeMillis());
		return PersonWorkHistory;
	}
	@Override
	public Long createObject(PersonWorkHistory object) throws Exception {
		Long id= (Long)this.saveEntity(this.checkValidity(object, 0));
		if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
		else throw new Exception("创建失败！ ");
	}
	
	@Override
	public Boolean deleteObjectsByPersonId(Long personId) throws Exception {
		if(personId==null) throw new Exception("personId is null or empty");
		this.deleteList("PersonWorkHistory_List_PersonId", personId);
		return true;
	}

	@Override
	public Boolean updateObject(PersonWorkHistory objcet) throws Exception {
		if(updateEntity(checkValidity(objcet,1)))return true;
		else return false;
	}

	@Override
	public PersonWorkHistory getObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		PersonWorkHistory PersonWorkHistory =getEntity(id);
		return PersonWorkHistory;
	}

	@Override
	public List<PersonWorkHistory> getObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		List<PersonWorkHistory> PersonWorkHistorys =this.getEntityByIds(ids);
		return PersonWorkHistorys;
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		return this.deleteEntity(id);
	}
	@Override
	public List<PersonWorkHistory> createObjects(List<PersonWorkHistory> objects)
			throws Exception {
		if(objects==null || objects.size()<=0) return null;
		for(PersonWorkHistory PersonWorkHistory : objects){
			this.checkValidity(PersonWorkHistory, 0);
		}
		return this.saveEntitys(objects);
	}
	@Override
	public List<PersonWorkHistory> getObjectsByPersonId(Long personId) throws Exception {
		if(personId==null || personId<=0l)throw new Exception("id is null or empty");
		List<Long> ids = this.getIds("PersonWorkHistory_List_PersonId", personId);
		return this.getEntityByIds(ids);
	}
	@Override
	public Boolean deleteObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		return this.deleteEntityByIds(ids);
	}
	@Override
	public Boolean updateObjects(List<PersonWorkHistory> objects) throws Exception {
		if(objects==null || objects.size()<=0) return true;
		for(PersonWorkHistory PersonWorkHistory : objects){
			this.checkValidity(PersonWorkHistory, 1);
		}
		return this.updateEntitys(objects);
	}
}
