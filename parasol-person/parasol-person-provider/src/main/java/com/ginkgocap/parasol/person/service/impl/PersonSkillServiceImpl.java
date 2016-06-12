package com.ginkgocap.parasol.person.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.person.model.PersonSkill;
import com.ginkgocap.parasol.person.service.PersonSkillService;
@Service("personSkillService")
public class PersonSkillServiceImpl extends BaseService<PersonSkill> implements
		     PersonSkillService {
	/**
	 * 检查数据
	 * @param PersonSkill
	 * @return
	 * @throws Exception
	 */
	private PersonSkill checkValidity(PersonSkill PersonSkill,int type)throws Exception {
		if(PersonSkill==null) throw new Exception("PersonSkill can not be null.");
		if(PersonSkill.getPersonId()<0l) throw new Exception("The value of personId is null or empty.");
		if(type!=0)
		if(getObject(PersonSkill.getPersonId())==null)throw new Exception("personId not exists in PersonSkill");
//		if(StringUtils.isEmpty(PersonSkill.getName()))throw new Exception("The value of  name is null or empty.");
		if(PersonSkill.getCtime()==null) PersonSkill.setCtime(System.currentTimeMillis());
		if(PersonSkill.getUtime()==null) PersonSkill.setUtime(System.currentTimeMillis());
		if(type==1)PersonSkill.setUtime(System.currentTimeMillis());
		return PersonSkill;
	}
	

	@Override
	public Long createObject(PersonSkill object) throws Exception {
		try {
			Long id=(Long)saveEntity(checkValidity(object,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new Exception("创建失败！ ");
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public Boolean updateObject(PersonSkill objcet) throws Exception {
		try {
			if(updateEntity(checkValidity(objcet,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public PersonSkill getObject(Long id) throws Exception {
		try {
			if(id==null || id<=0l)throw new Exception("personId is null or empty");
			PersonSkill PersonSkill =getEntity(id);
			return PersonSkill;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<PersonSkill> getObjects(List<Long> ids) throws Exception {
		try {
			if(ids==null || ids.size()==0)throw new Exception("personIds is null or empty");
			return getEntityByIds(ids);
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		try {
			if(id==null || id<=0l) throw new Exception("id is must grater than zero.");
			return deleteEntity(id);
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
}
