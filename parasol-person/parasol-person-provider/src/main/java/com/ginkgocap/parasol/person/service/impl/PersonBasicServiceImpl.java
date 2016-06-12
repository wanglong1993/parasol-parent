package com.ginkgocap.parasol.person.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.person.exception.PersonBasicServiceException;
import com.ginkgocap.parasol.person.model.PersonBasic;
import com.ginkgocap.parasol.person.service.PersonBasicService;
import com.ginkgocap.parasol.util.PinyinUtils;
@Service("personBasicService")
public class PersonBasicServiceImpl extends BaseService<PersonBasic> implements PersonBasicService  {
	private static Logger logger = Logger.getLogger(PersonBasicServiceImpl.class);
	/**
	 * 检查数据
	 * @param personBasic
	 * @return
	 * @throws PersonBasicServiceException
	 */
	private PersonBasic checkValidity(PersonBasic personBasic,int type)throws Exception {
		if(personBasic==null) throw new PersonBasicServiceException("personBasic can not be null.");
		if(type!=0)
		if(getObject(personBasic.getId())==null)throw new PersonBasicServiceException("personId not exists in personBasic");
//		if(StringUtils.isEmpty(personBasic.getName()))throw new PersonBasicServiceException("The value of  name is null or empty.");
		if(personBasic.getSex().intValue()!=0 && personBasic.getSex().intValue()!=1 && personBasic.getSex().intValue()!=2)throw new PersonBasicServiceException("The value of sex must be 0 or 1 or 2.");
		if(personBasic.getCtime()==null) personBasic.setCtime(System.currentTimeMillis());
		if(personBasic.getUtime()==null) personBasic.setUtime(System.currentTimeMillis());
		if(type==1)personBasic.setUtime(System.currentTimeMillis());
		if(!StringUtils.isEmpty(personBasic.getName()))personBasic.setNameIndex(PinyinUtils.stringToHeads(personBasic.getName()));
		return personBasic;
	}
	

	@Override
	public Long createObject(PersonBasic object) throws Exception {
		try {
			Long id=(Long)saveEntity(checkValidity(object,0));
			if(!ObjectUtils.isEmpty(id) && id>0l)return  id;
			else throw new PersonBasicServiceException("创建用户基本信息失败！ ");
		} catch (BaseServiceException e) {
			logger.error("创建用户基本信息失败！", e);
			throw new PersonBasicServiceException(e);
		}
	}

	@Override
	public Boolean updateObject(PersonBasic objcet) throws Exception {
		try {
			if(updateEntity(checkValidity(objcet,1)))return true;
			else return false;
		} catch (BaseServiceException e) {
			logger.error("更新用户基本信息失败！", e);
			throw new PersonBasicServiceException(e);
		}
	}

	@Override
	public PersonBasic getObject(Long id) throws Exception {
		try {
			if(id==null || id<=0l)throw new PersonBasicServiceException("personId is null or empty");
			PersonBasic personBasic =getEntity(id);
			return personBasic;
		} catch (BaseServiceException e) {
			logger.error("获取用户基本信息失败！", e);
			throw new PersonBasicServiceException(e);
		}
	}

	@Override
	public List<PersonBasic> getObjects(List<Long> ids) throws Exception {
		try {
			if(ids==null || ids.size()==0)throw new PersonBasicServiceException("personIds is null or empty");
			return getEntityByIds(ids);
		} catch (BaseServiceException e) {
			logger.error("批量获取用户基本信息失败！", e);
			throw new PersonBasicServiceException(e);
		}
	}

	@Override
	public Boolean deleteObject(Long id) throws Exception {
		try {
			if(id==null || id<=0l) throw new PersonBasicServiceException("id is must grater than zero.");
			return deleteEntity(id);
		} catch (Exception e) {
			logger.error("删除用户基本信息失败！", e);
			throw new PersonBasicServiceException(e);
		}
	}


	@Override
	public List<PersonBasic> getPersonBasicListByProvinceId(int start, int count,
			Long provinceId) throws Exception {
		if(provinceId==null||provinceId<=0l) throw new PersonBasicServiceException("provinceId is must grater than zero.");
		if(start<0) throw new PersonBasicServiceException("start is must grater than zero.");
		return this.getSubEntitys("PersonBasic_List_By_ProvinceId", start, count, provinceId);
	}


	@Override
	public List<PersonBasic> getPersonBasicListByCityId(int start, int count,
			Long cityId) throws Exception {
		if(cityId==null||cityId<=0l) throw new PersonBasicServiceException("cityId is must grater than zero.");
		if(start<0) throw new PersonBasicServiceException("start is must grater than zero.");
		return this.getSubEntitys("PersonBasic_List_By_CityId", start, count, cityId);
	}


	@Override
	public List<PersonBasic> getPersonBasicListByCountyId(int start, int count,
			Long countyId) throws Exception {
		if(countyId==null||countyId<=0l) throw new PersonBasicServiceException("countyId is must grater than zero.");
		if(start<0) throw new PersonBasicServiceException("start is must grater than zero.");
		return this.getSubEntitys("PersonBasic_List_By_CountyId", start, count, countyId);
	}


	@Override
	public List<PersonBasic> getPersonBasicListByPersonName(int start, int count,
			String personName) throws Exception {
		if(StringUtils.isEmpty(personName)) throw new PersonBasicServiceException("personName is must grater than zero.");
		if(start<0) throw new PersonBasicServiceException("start is must grater than zero.");
		return this.getSubEntitys("PersonBasic_List_By_PersonName", start, count, personName+"%");
	}
	
}
