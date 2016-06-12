package com.ginkgocap.parasol.person.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.person.model.PersonRTemplate;
import com.ginkgocap.parasol.person.service.PersonRTemplateService;
@Service("personRTemplateService")
public class PersonRTemplateServiceImpl extends BaseService<PersonRTemplate> implements PersonRTemplateService {

	/**
	 * 检查数据
	 * @param PersonRTemplate
	 * @return
	 * @throws Exception
	 */
	private PersonRTemplate checkValidity(PersonRTemplate PersonRTemplate,int type)throws Exception {
		if(PersonRTemplate==null) throw new Exception("PersonRTemplate can not be null.");
		if(PersonRTemplate.getPersonId()<0l) throw new Exception("The value of personId is null or empty.");
		if(type!=0)
		if(getObject(PersonRTemplate.getPersonId())==null)throw new Exception("personId not exists in PersonRTemplate");
		return PersonRTemplate;
	}
	

	@Override
	public Long createObject(PersonRTemplate object) throws Exception {
		try {
			Long id=(Long)saveEntity(checkValidity(object,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new Exception("创建失败！ ");
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public Boolean updateObject(PersonRTemplate objcet) throws Exception {
		try {
			if(updateEntity(checkValidity(objcet,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public PersonRTemplate getObject(Long id) throws Exception {
		try {
			if(id==null || id<=0l)throw new Exception("personId is null or empty");
			PersonRTemplate PersonRTemplate =getEntity(id);
			return PersonRTemplate;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<PersonRTemplate> getObjects(List<Long> ids) throws Exception {
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
