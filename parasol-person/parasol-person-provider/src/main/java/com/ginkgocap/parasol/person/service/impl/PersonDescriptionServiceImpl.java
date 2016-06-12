package com.ginkgocap.parasol.person.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.person.model.PersonDescription;
import com.ginkgocap.parasol.person.service.PersonDescriptionService;
@Service("personDescriptionService")
public class PersonDescriptionServiceImpl extends BaseService<PersonDescription> implements
			 PersonDescriptionService {
	/**
	 * 检查数据
	 * @param PersonDescription
	 * @return
	 * @throws Exception
	 */
	private PersonDescription checkValidity(PersonDescription PersonDescription,int type)throws Exception {
		if(PersonDescription==null) throw new Exception("PersonDescription can not be null.");
		if(PersonDescription.getPersonId()<0l) throw new Exception("The value of userId is null or empty.");
		if(type!=0)
		if(getObject(PersonDescription.getPersonId())==null)throw new Exception("userId not exists in PersonDescription");
//		if(StringUtils.isEmpty(PersonDescription.getName()))throw new Exception("The value of  name is null or empty.");
		if(PersonDescription.getCtime()==null) PersonDescription.setCtime(System.currentTimeMillis());
		if(PersonDescription.getUtime()==null) PersonDescription.setUtime(System.currentTimeMillis());
		if(type==1)PersonDescription.setUtime(System.currentTimeMillis());
		return PersonDescription;
	}
	

	@Override
	public Long createObject(PersonDescription object) throws Exception {
		try {
			Long id=(Long)saveEntity(checkValidity(object,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new Exception("创建失败！ ");
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public Boolean updateObject(PersonDescription objcet) throws Exception {
		try {
			if(updateEntity(checkValidity(objcet,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public PersonDescription getObject(Long id) throws Exception {
		try {
			if(id==null || id<=0l)throw new Exception("userId is null or empty");
			PersonDescription PersonDescription =getEntity(id);
			return PersonDescription;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<PersonDescription> getObjects(List<Long> ids) throws Exception {
		try {
			if(ids==null || ids.size()==0)throw new Exception("userIds is null or empty");
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
