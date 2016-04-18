package org.parasol.column.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.parasol.column.dao.ColumnCustomDao;
import org.parasol.column.entity.ColumnCustom;
import org.parasol.column.entity.ColumnCustomExample;
import org.parasol.column.entity.ColumnCustomExample.Criteria;
import org.parasol.column.mapper.gen.ColumnCustomMapper;
import org.springframework.stereotype.Component;

@Component
public class ColumnCustomDaoImpl implements ColumnCustomDao {
	
	@Resource
	private ColumnCustomMapper ccm;

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(ColumnCustom record) {
		// TODO Auto-generated method stub
		return ccm.insert(record);
	}

	@Override
	public ColumnCustom selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateByPrimaryKey(ColumnCustom record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<ColumnCustom> queryListByPidAndUserId(Long pid, Long uid) {
		// TODO Auto-generated method stub
		ColumnCustomExample example=new ColumnCustomExample();
		Criteria c=example.createCriteria();
		c.andPcidEqualTo(pid);
		c.andUserIdEqualTo(uid);
		List<ColumnCustom> list=ccm.selectByExample(example);
		return list;
	}

}
