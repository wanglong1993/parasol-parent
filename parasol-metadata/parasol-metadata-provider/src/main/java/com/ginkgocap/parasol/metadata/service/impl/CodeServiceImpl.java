package com.ginkgocap.parasol.metadata.service.impl;

import java.util.List;

import com.ginkgocap.parasol.metadata.exception.CodeServiceException;
import com.ginkgocap.parasol.metadata.model.Code;
import com.ginkgocap.parasol.metadata.service.CodeService;

public class CodeServiceImpl implements CodeService {

	@Override
	public Long createCodeForRoot(Code code) throws CodeServiceException {
		return null;
	}

	@Override
	public Long createCodeForChildren(Long parentId, Code code) throws CodeServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeCode(Code code) throws CodeServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateCode(Code code) throws CodeServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean disabledCode(Code code) throws CodeServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Code> getCodesByParentId(Long parentId, boolean displayDisabled) throws CodeServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countCodesByParentId(Long parentId, boolean displayDisabled) throws CodeServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Code> getCodesForRoot(boolean displayDisabled) throws CodeServiceException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int countCodesForRoot(boolean displayDisabled) throws CodeServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

}
