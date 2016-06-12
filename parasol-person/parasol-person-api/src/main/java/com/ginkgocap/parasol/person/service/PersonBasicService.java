package com.ginkgocap.parasol.person.service;

import java.util.List;

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
}
