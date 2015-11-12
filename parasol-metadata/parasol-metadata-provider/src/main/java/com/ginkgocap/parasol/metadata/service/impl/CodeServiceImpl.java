package com.ginkgocap.parasol.metadata.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.metadata.exception.CodeServiceException;
import com.ginkgocap.parasol.metadata.model.Code;
import com.ginkgocap.parasol.metadata.service.CodeService;
import com.mysql.fabric.xmlrpc.exceptions.MySQLFabricException;

public class CodeServiceImpl extends BaseService<Code> implements CodeService {

	private static int error_duplicate = 100; // 重名
	private static int error_name_blank = 101; // 名字是空的,名字必须有值
	private static int error_parentcode_null = 102; // 父对象不存在。

	private static Logger logger = Logger.getLogger(CodeServiceImpl.class);

	/**
	 * 创建根节点
	 * 
	 * @param code
	 * @return
	 * @throws CodeServiceException
	 *             不能重名
	 */
	@Override
	public Long createCodeForRoot(Code code) throws CodeServiceException {
		try {
			// 检查名字
			if (code == null || StringUtils.isBlank(code.getName())) {
				throw new CodeServiceException(error_name_blank, "Name property of code must have a value");
			}

			// 检查是否重名
			List<Code> codes = getCodesForRoot(true);
			if (CollectionUtils.isNotEmpty(codes)) {
				for (Code existCode : codes) {
					if (existCode != null && ObjectUtils.equals(existCode.getName(), code.getName())) {
						throw new CodeServiceException(error_duplicate, "Name is " + code.getName() + " already exists");
					}
				}
			}
			return (Long) saveEntity(code);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new CodeServiceException(e);
		}
	}

	@Override
	public Long createCodeForChildren(Long parentId, Code code) throws CodeServiceException {
		try {
			// 检查父对象情况
			if (parentId == null) {
				throw new CodeServiceException(error_parentcode_null, "The specified code by paraentId [" + ObjectUtils.toString(parentId, null)
						+ "] does not exist or parent code status disabled");
			}
			// 检查父对象的状态
			Code parentCode = getEntity(parentId);
			if (parentCode == null || parentCode.isDisabled()) {
				throw new CodeServiceException(error_parentcode_null, "The specified code by paraentId [" + ObjectUtils.toString(parentId, null)
						+ "] does not exist or parent code status disabled");
			}
			// 检查名字
			if (code == null || StringUtils.isBlank(code.getName())) {
				throw new CodeServiceException(error_name_blank, "Name property of code must have a value");
			}

			// 检查是否重名
			List<Code> codes = getCodesByParentId(parentId, true);
			if (CollectionUtils.isNotEmpty(codes)) {
				for (Code existCode : codes) {
					if (existCode != null && ObjectUtils.equals(existCode.getName(), code.getName())) {
						throw new CodeServiceException(error_duplicate, "Name is " + code.getName() + " already exists");
					}
				}
			}
			return (Long) saveEntity(code);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new CodeServiceException(e);
		}
	}

	@Override
	public boolean removeCode(Long codeId) throws CodeServiceException {
		try {
			if (codeId != null) {
				Code myself = getEntity(codeId);
				if (myself != null) {
					List<Code> codes = getCodesByParentId(codeId, true);
					if (CollectionUtils.isNotEmpty(codes)) {
						for (Code existCode : codes) {
							if (existCode != null) {
								removeCode(existCode.getId());
							}
						}
					}
					deleteEntity(codeId);
				} else {
					logger.warn("remove code don't exist by id [" + codeId + "]");
				}
				
			} else {
				logger.warn("codeId parameter is null");
			}
			return true;
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new CodeServiceException(e);
		}
	}

	@Override
	public boolean updateCode(Code code) throws CodeServiceException {
		try {
			return updateEntity(code);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new CodeServiceException(e);
		}
	}
	@Override
	public Code getCode(Long codeId) throws CodeServiceException {
		try {
			return getEntity(codeId);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new CodeServiceException(e);
		}
	}
	@Override
	public boolean disabledCode(Long codeId) throws CodeServiceException {
		if (codeId == null) {
			Code code = getCode(codeId);
			if (code != null) {
				code.setDisabled(true);
				return this.updateCode(code);
			}
		}
		return true;
	}
	
	
	@Override
	public boolean enabledCode(Long codeId) throws CodeServiceException {
		if (codeId == null) {
			Code code = getCode(codeId);
			if (code != null) {
				code.setDisabled(true);
				return this.updateCode(code);
			}
		}
		return true;
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
