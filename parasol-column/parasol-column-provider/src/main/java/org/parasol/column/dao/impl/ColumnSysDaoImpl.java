package org.parasol.column.dao.impl;

import javax.annotation.Resource;

import org.parasol.column.dao.ColumnSysDao;
import org.parasol.column.entity.ColumnSys;
import org.parasol.column.mapper.gen.ColumnSysMapper;
import org.springframework.stereotype.Component;

@Component
public class ColumnSysDaoImpl implements ColumnSysDao {
	
	@Resource
	private ColumnSysMapper csm;

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(ColumnSys record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ColumnSys selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return csm.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(ColumnSys record) {
		// TODO Auto-generated method stub
		return 0;
	}

}
