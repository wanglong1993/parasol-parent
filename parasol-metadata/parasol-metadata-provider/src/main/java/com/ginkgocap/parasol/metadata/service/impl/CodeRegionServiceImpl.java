package com.ginkgocap.parasol.metadata.service.impl;

import java.util.List;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.metadata.exception.CodeRegionServiceException;
import com.ginkgocap.parasol.metadata.model.CodeRegion;
import com.ginkgocap.parasol.metadata.service.CodeRegionService;
import com.ginkgocap.parasol.metadata.type.CodeRegionType;

/**
 * 
 * @author allenshen
 * @date 2015年11月15日
 * @time 下午11:13:04
 * @Copyright Copyright©2015 www.gintong.com
 */
public class CodeRegionServiceImpl extends BaseService<CodeRegion> implements CodeRegionService {

	@Override
	public Long createCodeRegionForRoot(CodeRegion codeRegion, CodeRegionType type) throws CodeRegionServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long createCodeForChildren(Long parentId, CodeRegion codeRegion) throws CodeRegionServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeCodeRegion(Long codeRegionId) throws CodeRegionServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateCodeRegion(CodeRegion codeRegion) throws CodeRegionServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public CodeRegion getCodeRegionById(Long codeRegionId) throws CodeRegionServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CodeRegion> getCodeRegionsByParentId(Long parentId) throws CodeRegionServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countCodeRegionsByParentId(Long parentId) throws CodeRegionServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<CodeRegion> getCodeRegionsForRootByType(long parentId, CodeRegionType type) throws CodeRegionServiceException {
		// TODO Auto-generated method stub
		return null;
	}

}
