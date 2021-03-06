package com.ginkgocap.parasol.knowledge.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.knowledge.dao.IColumnSysDao;
import com.ginkgocap.parasol.knowledge.model.ColumnSys;

@Repository("columnSysDao")
public class ColumnSysDao extends BaseService<ColumnSys> implements IColumnSysDao {

	@Override
	public List<ColumnSys> queryListByUserId(Long userId) throws Exception {
		// TODO Auto-generated method stub
		if(userId!=null){
			return this.getEntitys("ColumnSysDao_queryListByUserId", userId);
		}
		return null;
	}
	
}