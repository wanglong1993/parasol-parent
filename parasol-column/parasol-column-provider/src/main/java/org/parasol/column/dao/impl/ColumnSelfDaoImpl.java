package org.parasol.column.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.parasol.column.dao.ColumnSelfDao;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.entity.ColumnSelfExample;
import org.parasol.column.entity.ColumnSelfExample.Criteria;
import org.parasol.column.mapper.gen.ColumnSelfMapper;
import org.springframework.stereotype.Component;

@Component
public class ColumnSelfDaoImpl implements ColumnSelfDao {
	
	@Resource
	private ColumnSelfMapper selfMapper;

	@Override
	public int deleteByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return selfMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(ColumnSelf record) {
		// TODO Auto-generated method stub
		return selfMapper.insert(record);
	}

	@Override
	public ColumnSelf selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return selfMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKey(ColumnSelf record) {
		// TODO Auto-generated method stub
		return selfMapper.updateByPrimaryKey(record);
	}

	@Override
	public List<ColumnSelf> queryListByPidAndUserId(Long pid, Long uid) {
		// TODO Auto-generated method stub
		ColumnSelfExample example=new ColumnSelfExample();
		Criteria c=example.createCriteria();
		c.andParentIdEqualTo(pid);
		c.andUserIdEqualTo(uid);
		c.andDelStatusEqualTo((short)0);
		List<ColumnSelf> list= selfMapper.selectByExample(example);
		return list;
	}

	@Override
	public ColumnSelf selectMaxOrderColumn(Long pid, Long uid) {
		// TODO Auto-generated method stub
		ColumnSelfExample example=new ColumnSelfExample();
		Criteria c=example.createCriteria();
		c.andParentIdEqualTo(pid);
		c.andUserIdEqualTo(uid);
		example.setOrderByClause("order_num desc");
		example.setLimit(1);
		List<ColumnSelf> list= selfMapper.selectByExampleLimit(example);
		if(list!=null&&list.size()==1){
			return list.get(0);
		}
		return null;
	}

	@Override
	public List<ColumnSelf> queryListByPid(Long pid) {
		// TODO Auto-generated method stub
		ColumnSelfExample example=new ColumnSelfExample();
		Criteria c=example.createCriteria();
		c.andParentIdEqualTo(pid);
		c.andDelStatusEqualTo((short)0);
		List<ColumnSelf> list= selfMapper.selectByExample(example);
		return list;
	}

	@Override
	public List<ColumnSelf> queryDefaultList(Long startId,Long endId){
        // TODO Auto-generated method stub
		ColumnSelfExample example = new ColumnSelfExample();
		Criteria c = example.createCriteria();
		c.andIdBetween(startId,endId);
		c.andDelStatusEqualTo((short)0);
		List<ColumnSelf> list = selfMapper.selectByExample(example);
		return list;
	}

}
