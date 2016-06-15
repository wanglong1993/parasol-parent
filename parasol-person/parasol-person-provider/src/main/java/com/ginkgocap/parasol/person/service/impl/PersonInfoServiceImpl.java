package com.ginkgocap.parasol.person.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.person.model.PersonInfo;
import com.ginkgocap.parasol.person.service.PersonInfoService;
@Service("personInfoService")
public class PersonInfoServiceImpl extends BaseService<PersonInfo> implements PersonInfoService  {
	/**
	 * 检查数据
	 * @param PersonInfo
	 * @return
	 * @throws Exception
	 */
	private PersonInfo checkValidity(PersonInfo PersonInfo,int type)throws Exception {
		if(PersonInfo==null) throw new Exception("PersonInfo can not be null.");
		if(PersonInfo.getPersonId()<0l) throw new Exception("The value of personId is null or empty.");
		if(type!=0)
		if(getObject(PersonInfo.getPersonId())==null)throw new Exception("personId not exists in PersonInfo");
//		if(StringUtils.isEmpty(PersonInfo.getName()))throw new Exception("The value of  name is null or empty.");
		if(PersonInfo.getCtime()==null) PersonInfo.setCtime(System.currentTimeMillis());
		if(PersonInfo.getUtime()==null) PersonInfo.setUtime(System.currentTimeMillis());
		if(type==1)PersonInfo.setUtime(System.currentTimeMillis());
		return PersonInfo;
	}
	

	@Override
	public Long createObject(PersonInfo object) throws Exception {
		try {
			Long id=(Long)saveEntity(checkValidity(object,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new Exception("创建失败！ ");
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public Boolean updateObject(PersonInfo objcet) throws Exception {
		try {
			if(updateEntity(checkValidity(objcet,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public PersonInfo getObject(Long id) throws Exception {
		try {
			if(id==null || id<=0l)throw new Exception("personId is null or empty");
			PersonInfo PersonInfo =getEntity(id);
			return PersonInfo;
		} catch (BaseServiceException e) {
			throw new Exception(e);
		}
	}

	@Override
	public List<PersonInfo> getObjects(List<Long> ids) throws Exception {
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
