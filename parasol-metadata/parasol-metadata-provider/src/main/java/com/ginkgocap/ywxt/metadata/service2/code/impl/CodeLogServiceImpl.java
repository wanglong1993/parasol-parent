package com.ginkgocap.ywxt.metadata.service2.code.impl;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.stereotype.Service;

import com.ginkgocap.ywxt.metadata.model.CodeLog;
import com.ginkgocap.ywxt.metadata.service.code.CodeLogService;
import com.ginkgocap.ywxt.metadata.service2.BaseService;
import com.ginkgocap.ywxt.metadata.service2.exception.BaseServiceException;

@Deprecated
@Service("codeLogService")
public class CodeLogServiceImpl extends BaseService<CodeLog> implements CodeLogService {

	//select * from tb_code_log where sysItemId=#sysItemId:BIGINT# order by ctime desc,id desc
	private final static  String  CODELOG_SYSTEMID_ID = "CodeLog_SysItemId_Id";  
	@Override
	public CodeLog selectByPrimarKey(long id) {
		try {
			return this.getEntity(id);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
		}
		return null;
	}

	@Override
	public CodeLog insert(CodeLog codeLog) {
		try {
			Long id = (Long) this.saveEntity(codeLog);
			codeLog.setId(id);
			return codeLog;
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
		}
		return null;
	}

	@Override
	public int deleteByCodeId(long codeId) {
		try {
			return this.deleteList(CODELOG_SYSTEMID_ID, codeId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public long countByCodeId(long codeId) {
		try {
			return countEntitys(CODELOG_SYSTEMID_ID, codeId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public List<CodeLog> selectByCodeId(long codeId, long startRow, int pageSize) {
		try {
			return getSubEntitys(CODELOG_SYSTEMID_ID, NumberUtils.toInt(String.valueOf(startRow)), pageSize, codeId);
		} catch (BaseServiceException e) {
			e.printStackTrace(System.err);
			throw new RuntimeException(e.getMessage());
		}
	}

	@Override
	public Class<CodeLog> getEntityClass() {
		return CodeLog.class;
	}

}
