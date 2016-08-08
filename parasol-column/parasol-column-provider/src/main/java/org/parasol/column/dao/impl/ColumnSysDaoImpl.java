package org.parasol.column.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.parasol.column.dao.ColumnSysDao;
import org.parasol.column.entity.ColumnSys;
import org.parasol.column.entity.ColumnSysExample;
import org.parasol.column.entity.ColumnSysExample.Criteria;
import org.parasol.column.mapper.gen.ColumnSysMapper;
import org.springframework.stereotype.Component;

@Component
public class ColumnSysDaoImpl implements ColumnSysDao {
	
	@Resource
	private ColumnSysMapper csm;

	@Override
	public int deleteByPrimaryKey(Long id) {
		return 0;
	}

	@Override
	public int insert(ColumnSys record) {
		return 0;
	}

	@Override
	public ColumnSys selectByPrimaryKey(Long id) {
		return csm.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(ColumnSys record) {
		return 0;
	}

	@Override
	public List<ColumnSys> selectAll() {
		ColumnSysExample example=new ColumnSysExample();
		Criteria c=example.createCriteria();
		c.andUserIdEqualTo(0l);
		List<ColumnSys> list=csm.selectByExample(example);
		return list;
	}

}
