package com.ginkgocap.parasol.person.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.person.model.PersonEducationHistory;
import com.ginkgocap.parasol.person.service.PersonEducationHistoryService;
@Service("personEducationHistoryService")
public class PersonEducationHistoryServiceImpl extends BaseService<PersonEducationHistory> implements PersonEducationHistoryService {
	private PersonEducationHistory checkValidity(PersonEducationHistory PersonEducationHistory,int type)throws Exception {
		if(PersonEducationHistory==null) throw new Exception("PersonEducationHistory can not be null.");
		if(PersonEducationHistory.getPersonId()<0l) throw new Exception("The value of userId is null or empty.");
		if(type!=0)
		if(getObject(PersonEducationHistory.getId())==null)throw new Exception("userId not exists in PersonEducationHistory");
		if(PersonEducationHistory.getCtime()==null) PersonEducationHistory.setCtime(System.currentTimeMillis());
		if(PersonEducationHistory.getUtime()==null) PersonEducationHistory.setUtime(System.currentTimeMillis());
		if(type==1)PersonEducationHistory.setUtime(System.currentTimeMillis());
		return PersonEducationHistory;
	}
	@Override
	public Long createObject(PersonEducationHistory object) throws Exception {
		Long id= (Long)this.saveEntity(this.checkValidity(object, 0));
		if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
		else throw new Exception("创建失败！ ");
	}

	@Override
	public Boolean updateObject(PersonEducationHistory objcet) throws Exception {
		if(updateEntity(checkValidity(objcet,1)))return true;
		else return false;
	}

	@Override
	public PersonEducationHistory getObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		PersonEducationHistory PersonEducationHistory =getEntity(id);
		return PersonEducationHistory;
	}

	@Override
	public List<PersonEducationHistory> getObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		List<PersonEducationHistory> PersonEducationHistorys =this.getEntityByIds(ids);
		return PersonEducationHistorys;
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		if(id==null || id<=0l)throw new Exception("id is null or empty");
		return this.deleteEntity(id);
	}
	@Override
	public List<PersonEducationHistory> createObjects(List<PersonEducationHistory> objects)
			throws Exception {
		if(objects==null || objects.size()<=0) return null;
		for(PersonEducationHistory PersonEducationHistory : objects){
			this.checkValidity(PersonEducationHistory, 0);
		}
		return this.saveEntitys(objects);
	}
	@Override
	public List<PersonEducationHistory> getObjectsByPersonId(Long userId) throws Exception {
		if(userId==null || userId<=0l)throw new Exception("id is null or empty");
		List<Long> ids = this.getIds("PersonEducationHistory_List_PersonId", userId);
		return this.getEntityByIds(ids);
	}
	@Override
	public Boolean deleteObjects(List<Long> ids) throws Exception {
		if(ids==null || ids.size()<=0)throw new Exception("ids is null or empty");
		return this.deleteEntityByIds(ids);
	}
	@Override
	public Boolean updateObjects(List<PersonEducationHistory> objects) throws Exception {
		if(objects==null || objects.size()<=0) return true;
		for(PersonEducationHistory PersonEducationHistory : objects){
			this.checkValidity(PersonEducationHistory, 1);
		}
		return this.updateEntitys(objects);
	}
	@Override
	public Boolean deleteObjectsByPersonId(Long userId) throws Exception {
		if(userId==null) throw new Exception("userId is null or empty");
		this.deleteList("PersonEducationHistory_List_PersonId", userId);
		return true;
	}

}
