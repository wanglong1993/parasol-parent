package com.ginkgocap.parasol.person.service;

import java.util.List;
import java.util.Map;

import com.ginkgocap.parasol.person.model.PersonBasic;

public interface PersonBasicService extends CommonService<PersonBasic> {
	/**
	 * 根据省id查询
	 * @param start
	 * @param count
	 * @param provinceId 
	 * @return
	 * @throws Exception
	 */
	public List<PersonBasic> getPersonBasicListByProvinceId(int start, int count, Long provinceId) throws Exception;
	/**
	 * 根据市id查询
	 * @param start
	 * @param count
	 * @param provinceId 
	 * @return
	 * @throws Exception
	 */
	public List<PersonBasic> getPersonBasicListByCityId(int start, int count, Long cityId) throws Exception;
	/**
	 * 根据县id查询
	 * @param start
	 * @param count
	 * @param provinceId 
	 * @return
	 * @throws Exception
	 */
	public List<PersonBasic> getPersonBasicListByCountyId(int start, int count, Long countyId) throws Exception;
	/**
	 * 根据用户名模糊查询
	 * @param start
	 * @param count
	 * @param provinceId 
	 * @return
	 * @throws Exception
	 */
	public List<PersonBasic> getPersonBasicListByPersonName(int start, int count, String userName) throws Exception;
	/**
	 * 根据用户id查询
	 * @param start
	 * @param count
	 * @param createId 创建者id 
	 * @param keyWord 关键字
	 * @param column 要过滤的字段
	 * @return
	 * @throws Exception
	 */
	public Map<String,Object> getPersonBasicListByCreateId(int start, int count, Long createId,String keyWord,String orderColumn) throws Exception;
}
