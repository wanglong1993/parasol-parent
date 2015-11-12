package com.ginkgocap.parasol.metadata.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.metadata.exception.CodeServiceException;
import com.ginkgocap.parasol.metadata.model.Code;
import com.ginkgocap.parasol.metadata.service.CodeService;
/**
 * 
 * @author allenshen
 * @date 2015年11月12日
 * @time 下午1:35:05
 * @Copyright Copyright©2015 www.gintong.com
 */
@Service("codeService")
public class CodeServiceImpl extends BaseService<Code> implements CodeService {

	private static int error_duplicate = 100; // 重名
	private static int error_name_blank = 101; // 名字是空的,名字必须有值
	private static int error_parentcode_null = 102; // 父对象不存在。

	private static Logger logger = Logger.getLogger(CodeServiceImpl.class);

	// dao const;
	private static final String CODE_LIST_ID_ROOT = "Code_List_Id_Root"; // 父节点列表
	private static final String CODE_LIST_ID_ROOT_DISABLED = "Code_List_Id_Root_Disabled"; // 父节点能不能使用的节点列表

	private static final String CODE_LIST_ID_PID = "Code_List_Id_Pid"; // 父节点下边的子节点
	private static final String CODE_LIST_ID_PID_DISABLED = "Code_List_Id_Pid_Disabled"; // 父节点下边能不能是使用的节点

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
			// 设置为根节点
			code.setRoot(true);
			code.setPid(0l);
			Long id = (Long) saveEntity(code);
			if (id != null) {
				Code newCode = getCode(id);
				if (newCode != null) {
					newCode.setNumberCode(ObjectUtils.toString(id));
					this.updateCode(newCode);
				}
			}
			return id;

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

			code.setRoot(false); // 不是根节点
			code.setPid(parentId); //设置父节点ID
			Long id = (Long) saveEntity(code);
			if (id != null) {
				Code newCode = getCode(id);
				if (newCode != null) {
					newCode.setNumberCode(parentCode.getNumberCode() + "-" + id);
					updateCode(newCode);
				}
			}

			return id;
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
		if (codeId != null) {
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
		if (codeId != null) {
			Code code = getCode(codeId);
			if (code != null) {
				code.setDisabled(false);
				return this.updateCode(code);
			}
		}
		return true;
	}
	/**
	 * 查询一个父节点下边的一级子节点
	 * @param parentId 父节点ID
	 * @param displayDisabled 是否显示禁用的Code，默认为false，不显示
	 * @return
	 * @throws CodeServiceException
	 */
	@Override
	public List<Code> getCodesByParentId(Long parentId, boolean displayDisabled) throws CodeServiceException {

		try {
			return displayDisabled ? getEntitys(CodeServiceImpl.CODE_LIST_ID_PID, parentId) : getEntitys(CodeServiceImpl.CODE_LIST_ID_PID_DISABLED, parentId, false);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new CodeServiceException(e);
		}

	}

	@Override
	public int countCodesByParentId(Long parentId, boolean displayDisabled) throws CodeServiceException {
		try {
			return displayDisabled ? countEntitys(CODE_LIST_ID_PID, parentId) : countEntitys(CODE_LIST_ID_PID_DISABLED, parentId, false);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new CodeServiceException(e);
		}
	}

	@Override
	public List<Code> getCodesForRoot(boolean displayDisabled) throws CodeServiceException {
		try {
			return displayDisabled ? getEntitys(CODE_LIST_ID_ROOT, true) : getEntitys(CODE_LIST_ID_ROOT_DISABLED, true, false);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new CodeServiceException(e);
		}
	}

	@Override
	public int countCodesForRoot(boolean displayDisabled) throws CodeServiceException {
		try {
			return displayDisabled ? countEntitys(CODE_LIST_ID_ROOT, true) : countEntitys(CODE_LIST_ID_ROOT_DISABLED, true, false);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new CodeServiceException(e);
		}
	}
}
