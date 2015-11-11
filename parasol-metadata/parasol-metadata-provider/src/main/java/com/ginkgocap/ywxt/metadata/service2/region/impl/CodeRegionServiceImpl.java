/**
 * Copyright (c) 2011 银杏资本.
 * All Rights Reserved. 保留所有权利.
 */
package com.ginkgocap.ywxt.metadata.service2.region.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.ywxt.metadata.model.CodeRegion;
import com.ginkgocap.ywxt.metadata.service.region.CodeRegionService;
import com.ginkgocap.ywxt.metadata.service2.BaseService;
import com.ginkgocap.ywxt.metadata.service2.exception.BaseServiceException;

/**
 * @author douyou 
 * @version 创建时间：2011-11-18 下午3:54:41
 * 类说明
 */
@Service("codeRegionService")
public class CodeRegionServiceImpl extends BaseService<CodeRegion> implements CodeRegionService {
	private static final String CODEREGION_LIST_ID_PARENTID = "CodeRegion_List_Id_ParentId";
	private static final String CODEREGION_LIST_ID_ALL ="CodeRegion_List_Id_All";
	private static final long[] countryParentIds = {4696, 4697, 4698, 4699, 4700, 4701, 4702};
	private static final String selectByName = "selectByName";
	private static Logger logger = Logger.getLogger(CodeRegionServiceImpl.class);
	
	@Deprecated
	@Override
	public CodeRegion selectByPrimaryKey(long id) {
		try {
			return this.getEntity(id);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<CodeRegion> selectByParentId(long id) {
		Long parentId = id;
		try {
			return getEntitys(CODEREGION_LIST_ID_PARENTID, parentId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Deprecated
	@Override
	public CodeRegion selectParentByPrimaryKey(long id) {
		CodeRegion codeRegion = this.selectByPrimaryKey(id);
		if (codeRegion != null) {
			return this.selectByPrimaryKey(codeRegion.getParentId());
		} else {
			return null;
		}
	}

	@Override
	public List<CodeRegion> selectAllCountry() {
		List<CodeRegion> allCountTrys = new ArrayList<CodeRegion>();
		for (int i = 0; i < countryParentIds.length; i++) {
			List<CodeRegion> countrys = this.selectByParentId(countryParentIds[i]);
			allCountTrys.containsAll(countrys);
		}
		return allCountTrys;
	}

	@Override
	public List<CodeRegion> selectByName(String name) {
		if (StringUtils.isBlank(name)) {
			return ListUtils.EMPTY_LIST;
		}
		try {
			int take_number = 0;
			List<CodeRegion> resultCodeRegions = new ArrayList<CodeRegion>();
			while (true) {
				List<CodeRegion> codeRegions = getSubEntitys(CODEREGION_LIST_ID_ALL, take_number * TAKE_SIZE, TAKE_SIZE, 1);
				if (CollectionUtils.isNotEmpty(codeRegions)) {
					for (CodeRegion codeRegion : codeRegions) {
						if (codeRegion != null && (StringUtils.contains(codeRegion.getCname(), name) || StringUtils.contains(codeRegion.getEname(), name))) {
							resultCodeRegions.add(codeRegion);
						}
					}
				}

				// 退出循环（break loop）
				if (CollectionUtils.isEmpty(codeRegions)) {
					StringBuffer sb = new StringBuffer();
					sb.append(CollectionUtils.isEmpty(codeRegions) ? "code is empty or null; break loop" : "");
					logger.info(sb.toString());
					break;
				}
				take_number++;
			}
			return resultCodeRegions;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}	
	}

	@Override
	public Class<CodeRegion> getEntityClass() {
		return CodeRegion.class;
	}
    
    
}