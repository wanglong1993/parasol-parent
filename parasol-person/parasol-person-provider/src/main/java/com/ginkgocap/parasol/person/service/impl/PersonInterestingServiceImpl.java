package com.ginkgocap.parasol.person.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.person.model.PersonInteresting;
import com.ginkgocap.parasol.person.service.PersonInterestingService;
@Service("personInterestingService")
public class PersonInterestingServiceImpl extends BaseService<PersonInteresting> implements
			 PersonInterestingService {
	/**
	 * 检查数据
	 * @param PersonInteresting
	 * @return
	 * @throws Exception
	 */
	private PersonInteresting checkValidity(PersonInteresting PersonInteresting,int type)throws Exception {
		if(PersonInteresting==null) throw new Exception("PersonInteresting can not be null.");
		if(PersonInteresting.getPersonId()<0l) throw new Exception("The value of personId is null or empty.");
		if(type!=0)
		if(getObject(PersonInteresting.getPersonId())==null)throw new Exception("personId not exists in PersonInteresting");
//		if(StringUtils.isEmpty(PersonInteresting.getName()))throw new Exception("The value of  name is null or empty.");
		if(PersonInteresting.getCtime()==null) PersonInteresting.setCtime(System.currentTimeMillis());
		if(PersonInteresting.getUtime()==null) PersonInteresting.setUtime(System.currentTimeMillis());
		if(type==1)PersonInteresting.setUtime(System.currentTimeMillis());
		return PersonInteresting;
	}
	

	@Override
	public Long createObject(PersonInteresting object) throws Exception {
		try {
			Long id=(Long)saveEntity(checkValidity(object,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new Exception("创建失败！ ");
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public Boolean updateObject(PersonInteresting objcet) throws Exception {
		try {
			if(updateEntity(checkValidity(objcet,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public PersonInteresting getObject(Long id) throws Exception {
		try {
			if(id==null || id<=0l)throw new Exception("personId is null or empty");
			PersonInteresting PersonInteresting =getEntity(id);
			return PersonInteresting;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<PersonInteresting> getObjects(List<Long> ids) throws Exception {
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
