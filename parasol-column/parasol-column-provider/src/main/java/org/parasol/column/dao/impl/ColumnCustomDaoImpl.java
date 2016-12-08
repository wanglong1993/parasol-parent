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
	private ColumnCustomMapper columnCustomMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return columnCustomMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ColumnCustom record) {
		// TODO Auto-generated method stub
		return columnCustomMapper.insert(record);
	}

	@Override
	public ColumnCustom selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return columnCustomMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(ColumnCustom record) {
		// TODO Auto-generated method stub
		return columnCustomMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ColumnCustom> queryListByPidAndUserId(Long pid, Long uid) {
		// TODO Auto-generated method stub
		ColumnCustomExample example=new ColumnCustomExample();
		Criteria c=example.createCriteria();
		c.andPcidEqualTo(pid);
		c.andUserIdEqualTo(uid);
		List<ColumnCustom> list=columnCustomMapper.selectByExample(example);
		return list;
	}


	@Override
	public ColumnCustom queryByCid(Long cid) {
		// TODO Auto-generated method stub
		ColumnCustomExample example=new ColumnCustomExample();
		Criteria c=example.createCriteria();
		c.andCidEqualTo(cid);
		List<ColumnCustom> list=columnCustomMapper.selectByExample(example);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public int deleteByUserId(Long uid) {
		// TODO Auto-generated method stub
		ColumnCustomExample example=new ColumnCustomExample();
		Criteria c=example.createCriteria();
		c.andUserIdEqualTo(uid);
		int n=columnCustomMapper.deleteByExample(example);
		return n;
	}

	@Override
	public int insertBatch(List<ColumnCustom> list) {
		// TODO Auto-generated method stub
		return columnCustomMapper.insertBatch(list);
	}

}
