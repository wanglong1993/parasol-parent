package com.ginkgocap.parasol.metadata.service;

import java.util.List;

import com.ginkgocap.parasol.metadata.exception.CodeServiceException;
import com.ginkgocap.parasol.metadata.model.Code;

/**
 * 操作Code的接口定义
 * 
 * @author allenshen
 * @date 2015年11月11日
 * @time 下午5:46:44
 * @Copyright Copyright©2015 www.gintong.com
 */
public interface CodeService {

	/**
	 * 创建根节点
	 * @param code
	 * @return
	 * @throws CodeServiceException 不能重名
	 */
	public Long createCodeForRoot(Code code) throws CodeServiceException; 
	

	/**
	 * 创建父节点的子节点
	 * @param code
	 * @return
	 * @throws CodeServiceException 不能重名
	 */
	public Long createCodeForChildren(Long parentId, Code code) throws CodeServiceException; 
	
	
	/**
	 * 删除一个Code 会把Code下边的子Code也删除
	 * @param code
	 * @return
	 * @throws CodeServiceException
	 */
	public boolean removeCode(Code code) throws CodeServiceException;
	
	
	/**
	 * 删除一个Code 会把Code下边的子Code也删除
	 * @param code
	 * @return
	 * @throws CodeServiceException
	 */
	public boolean updateCode(Code code) throws CodeServiceException;
	
	
	
	/**
	 * 禁用一个Code，Code下边的子节点也不能使用
	 * @param code
	 * @return
	 * @throws CodeServiceException
	 */
	public boolean disabledCode(Code code) throws CodeServiceException;
	
	
	/**
	 * 查询一个父节点下边的一级子节点
	 * @param parentId 父节点ID
	 * @param displayDisabled 是否显示禁用的Code，默认为false，不显示
	 * @return
	 * @throws CodeServiceException
	 */
	public List<Code> getCodesByParentId(Long parentId, boolean displayDisabled) throws CodeServiceException;
	

	
	/**
	 * 查询一个父节点下边的一级子节点数量
	 * @param parentId 父节点ID
	 * @param displayDisabled 是否显示禁用的Code，默认为false，不显示
	 * @return
	 * @throws CodeServiceException
	 */
	public int countCodesByParentId(Long parentId, boolean displayDisabled) throws CodeServiceException;
	

	/**
	 * 查询父节点的列表
	 * @param parentId 父节点ID
	 * @param displayDisabled 是否显示禁用的Code，默认为false，不显示
	 * @return
	 * @throws CodeServiceException
	 */
	public List<Code> getCodesForRoot(boolean displayDisabled) throws CodeServiceException;
	
	
	
	/**
	 * 查询父节点的总数
	 * @param parentId 父节点ID
	 * @param displayDisabled 是否显示禁用的Code，默认为false，不显示
	 * @return
	 * @throws CodeServiceException
	 */
	public int countCodesForRoot(boolean displayDisabled) throws CodeServiceException;
	
	

}
