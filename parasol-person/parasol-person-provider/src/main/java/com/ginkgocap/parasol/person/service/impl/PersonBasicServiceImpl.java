package com.ginkgocap.parasol.person.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.metadata.exception.CodeRegionServiceException;
import com.ginkgocap.parasol.metadata.model.CodeRegion;
import com.ginkgocap.parasol.metadata.service.CodeRegionService;
import com.ginkgocap.parasol.person.exception.PersonBasicServiceException;
import com.ginkgocap.parasol.person.model.PersonBasic;
import com.ginkgocap.parasol.person.service.PersonBasicService;
import com.ginkgocap.parasol.util.PinyinUtils;
@Service("personBasicService")
public class PersonBasicServiceImpl extends BaseService<PersonBasic> implements PersonBasicService  {
	private static Logger logger = Logger.getLogger(PersonBasicServiceImpl.class);
	@Resource
	private CodeRegionService codeRegionService;
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

	@Override
	public Map<String,Object> getPersonBasicListByCreateId(int start, int count,
			Long createId, String keyWord,String orderColumn) throws Exception {
		Map<String,Object> returnMap = new HashMap<String,Object>();
		if(createId==null||createId<=0l) throw new PersonBasicServiceException("createId is must grater than zero.");
		if(start<0) throw new PersonBasicServiceException("start is must grater than zero.");
		List<PersonBasic> personBasics = this.getEntitys("PersonBasic_List_By_CreateId", createId);
		if(personBasics==null){
			returnMap.put("count", 0);
			returnMap.put("persons", Collections.EMPTY_LIST);
			return returnMap;
		}
		//填充地区
		fill(personBasics);
		if(!StringUtils.isEmpty(keyWord)){
			filter(personBasics,keyWord);
		}
		if(!StringUtils.isEmpty(orderColumn)){
			if("NAME".endsWith(orderColumn)){
				Collections.sort(personBasics, new NameComparator());
			}
			if("TIME".endsWith(orderColumn)){
				Collections.sort(personBasics, new UtimeComparator());
			}
		}
		int startIndex = start*count;
		int endIndex = (start+1)*count;
		int size = personBasics.size();
		if(size>startIndex&&size<=endIndex){
			returnMap.put("count", size);
			returnMap.put("persons", personBasics.subList(startIndex, size));
		}
		if(size>startIndex&&size>endIndex){
			returnMap.put("count", size);
			returnMap.put("persons", personBasics.subList(startIndex, endIndex));
		}
		return returnMap;
	}

	private void fill(List<PersonBasic> personBasics) throws CodeRegionServiceException {
		if(personBasics!=null){
			Iterator<PersonBasic> it = personBasics.iterator();
			while(it.hasNext()){
					PersonBasic personBasic = it.next();
				
					Long provinceId = personBasic.getProvinceId();
					Long cityId = personBasic.getCityId();
					Long countyId = personBasic.getCountyId();
					CodeRegion codeRegion=new CodeRegion();
					if(provinceId!=null){
						codeRegion = codeRegionService.getCodeRegionById(provinceId);
						personBasic.setProvinceName(codeRegion.getCname());
					}
					if(cityId!=null){
						codeRegion = codeRegionService.getCodeRegionById(cityId);
						personBasic.setCityName(codeRegion.getCname());
					}
					if(countyId!=null){
						codeRegion = codeRegionService.getCodeRegionById(countyId);
						personBasic.setCountyName(codeRegion.getCname());
					}
				}
			}
	}


	/**
	 * 根据关键字过滤
	 * @param personBasics
	 * @param keyWord
	 * @throws CodeRegionServiceException
	 */
	private void filter(List<PersonBasic> personBasics, String keyWord) throws CodeRegionServiceException {
		if(personBasics!=null){
			Iterator<PersonBasic> it = personBasics.iterator();
			while(it.hasNext()){
				PersonBasic personBasic = it.next();
				String companyJob = personBasic.getCompanyJob();
				String name = personBasic.getName();
				String companyName = personBasic.getCompanyName();
				
				String provinceName = personBasic.getProvinceName();
				String cityName = personBasic.getCityName();
				String countyName = personBasic.getCountyName();
				
				StringBuilder sb = new StringBuilder();
				sb.append(name).append("|").append(companyJob)
				  .append("|").append(companyName).append("|")
				  .append(provinceName).append("|")
				  .append(cityName).append("|").append(countyName);
				if(sb.toString().indexOf(keyWord)<0){
					it.remove();
				}
				
			}
		}
	}
	/**
	 * 名称升序排列
	 * @author gintong
	 *
	 */
	public  class NameComparator implements Comparator<PersonBasic>{
		@Override
		public int compare(PersonBasic o1, PersonBasic o2) {
			String name1 = o1.getNameIndexAll();
			String name2 = o2.getNameIndexAll();
			if(!StringUtils.isEmpty(name1)&&!StringUtils.isEmpty(name2))
			return name1.compareTo(name2);
			return 0;
		}
	}
	/**
	 * 时间降序排列
	 * @author gintong
	 *
	 */
	public class UtimeComparator implements Comparator<PersonBasic>{

		@Override
		public int compare(PersonBasic o1, PersonBasic o2) {
			Long utime1= o1.getUtime();
			Long utime2 = o2.getUtime();
			if(utime1!=null&&utime2!=null){
			  if(utime1>utime2)
				  return -1;
			  return 1;
			}
			return 0;
		}
		
	}
	
}
