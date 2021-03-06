package com.ginkgocap.parasol.knowledge.dao;

import java.util.List;

import com.ginkgocap.parasol.knowledge.model.ColumnSys;

/**
 * @Title: 知识系统栏目表
 * @Description: 存储系统栏目表，系统栏目为系统初始化栏目
 * @author 周仕奇
 * @date 2016年1月11日 下午2:31:19
 * @version V1.0.0
 */
public interface IColumnSysDao {
	
	List<ColumnSys> queryListByUserId(Long userId) throws Exception;
}