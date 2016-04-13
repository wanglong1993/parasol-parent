package org.parasol.column.service.impl;

import javax.annotation.Resource;

import org.parasol.column.dao.ColumnSysDao;
import org.parasol.column.entity.ColumnSys;
import org.parasol.column.service.ColumnSysService;
import org.springframework.stereotype.Component;

@Component
public class ColumnSysServiceImpl implements ColumnSysService {
	
	@Resource 
	private ColumnSysDao csd;

	@Override
	public ColumnSys selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return csd.selectByPrimaryKey(id);
	}

}
