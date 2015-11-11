package com.ginkgocap.ywxt.metadata.service2.codedy.impl;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.ywxt.metadata.model.CodeDy;
import com.ginkgocap.ywxt.metadata.service.CodeDyService;
import com.ginkgocap.ywxt.metadata.service2.BaseService;
import com.ginkgocap.ywxt.metadata.service2.exception.BaseServiceException;
@Service("codeDyService")
public class CodeDyServiceImpl extends BaseService<CodeDy> implements CodeDyService {
	private static Logger logger = Logger.getLogger(CodeDyServiceImpl.class);
	private static final String selectByName = "CodeDy_Map_Id_Name"; // map
	private static final String selectAll = "CodeDy_List_Id_All";

	@Deprecated
	@Override
	public CodeDy insert(CodeDy codeDy) {
		try {
			Long id = (Long) saveEntity(codeDy);
			if (id != null) {
				codeDy.setId(id);
			}
			return codeDy;

		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Deprecated
	@Override
	public void update(CodeDy codeDy) {
		try {
			updateEntity(codeDy);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Deprecated
	@Override
	public CodeDy selectByPrimarKey(long id) {
		try {
			return getEntity(id);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Deprecated
	@Override
	public CodeDy selectByName(String name) {
		try {
			if (StringUtils.isNotBlank(name)) {
				Long id = (Long) getMapId(selectByName, name);
				if (id != null) {
					return getEntity(id);
				} else {
					logger.warn(selectByName + " don't find map by :  " + name);
				}
			} else {
				logger.error("enter name parameter is null or empty or blank");
			}
			return null;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<CodeDy> selectAll() {
		try {
			return getEntitys(selectAll, 1);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Deprecated
	@Override
	public void delete(long id) {
		try {
			deleteEntity(id);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e);
		}
	}

	@Override
	public Class getEntityClass() {
		// TODO Auto-generated method stub
		return CodeDy.class;
	}

}
