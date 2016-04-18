package org.parasol.column.service.impl;

import java.util.List;

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

	@Override
	public List<ColumnSys> selectAll() {
		// TODO Auto-generated method stub
		return csd.selectAll();
	}

}
