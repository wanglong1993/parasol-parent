package com.ginkgocap.ywxt.metadata.service2.dic.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ginkgocap.ywxt.metadata.model.Dic;
import com.ginkgocap.ywxt.metadata.service.dic.DicService;
import com.ginkgocap.ywxt.metadata.service2.BaseService;
import com.ginkgocap.ywxt.metadata.service2.exception.BaseServiceException;
/**
 * 
 * @author allenshen
 * @date: 2015年10月22日
 * @time: 下午4:58:20
 * Copyright©2015 www.gintong.com
 */
@Service("dicService")
public class DicServiceImpl extends BaseService<Dic> implements DicService {
	private static final String DIC_LIST_ID_TYPE = "Dic_List_Id_Type";
	
	@Override
	public Class<Dic> getEntityClass() {
		return Dic.class;
	}

	@Override
	public List<Dic> getDicsByType(Integer type) {
		try {
			List<Dic> dics = getEntitys(DIC_LIST_ID_TYPE, 0);
			return dics;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
		
	}

}
