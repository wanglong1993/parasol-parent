package com.ginkgocap.ywxt.metadata.service;

import java.util.Map;

import com.ginkgocap.ywxt.metadata.model.CountryCode;
/**
 * 国家区号接口
 * <p>  </p>
 * @author liubang
 * @date 2015-1-14
 */
public interface CountryCodeService {
	/**
     * 通过主键获得国家区号
     * @param id
     * @return
     */
	@Deprecated
	CountryCode findOne(long id);
    /**
     * 查询所有的国家区号
     * @return
     */
    Map<String,Object> findAll();
    /**
     * 插入或者更新国家区号
     * @param code
     * @return
     */
    @Deprecated
    CountryCode saveOrUpdate(CountryCode countryCode);
    /**
     * 删除国家区号
     * @param id
     */
    @Deprecated
    void delete(long id);
}
