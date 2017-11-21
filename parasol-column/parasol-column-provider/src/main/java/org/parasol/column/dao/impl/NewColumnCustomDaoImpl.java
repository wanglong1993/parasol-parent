package org.parasol.column.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.parasol.column.dao.NewColumnCustomDao;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.entity.NewColumnCustom;
import org.parasol.column.entity.NewColumnCustomExample;
import org.parasol.column.entity.NewColumnCustomExample.Criteria;
import org.parasol.column.mapper.gen.NewColumnCustomMapper;
import org.springframework.stereotype.Component;

@Component
public class NewColumnCustomDaoImpl implements NewColumnCustomDao {
	
	@Resource
	private NewColumnCustomMapper newColumnCustomMapper;

	@Override
	public int insert(NewColumnCustom record) {
		// TODO Auto-generated method stub
		return newColumnCustomMapper.insert(record);
	}


	@Override
	public NewColumnCustom queryBySid(Long sid) {
		// TODO Auto-generated method stub
		NewColumnCustomExample example=new NewColumnCustomExample();
		Criteria c=example.createCriteria();
		c.andSidEqualTo(sid);
		List<NewColumnCustom> list=newColumnCustomMapper.selectByExample(example);
		if(list!=null&&list.size()>0){
			return list.get(0);
		}
		return null;
	}

	@Override
	public int deleteByUserId(Long uid) {
		// TODO Auto-generated method stub
		NewColumnCustomExample example=new NewColumnCustomExample();
		Criteria c=example.createCriteria();
		c.andUserIdEqualTo(uid);
		int n=newColumnCustomMapper.deleteByExample(example);
		return n;
	}

	@Override
	public int updateByUid(NewColumnCustom newColumnCustom, Long uid) {
		int n = newColumnCustomMapper.updateByUid(newColumnCustom,uid);
		return n;
	}

	/*@Override
	public int insertBatch(List<NewColumnCustom> list) {
		// TODO Auto-generated method stub
		return ccm.insertBatch(list);
	}*/
	
	@Override
	public List<ColumnSelf> queryListByUid(Long uid){
		// TODO Auto-generated method stub
		/*NewColumnCustomExample example=new NewColumnCustomExample();
		Criteria c=example.createCriteria();
		c.andUserIdEqualTo(uid);
		List<NewColumnCustom> list=ccm.selectByExample(example);*/
		List<ColumnSelf> list = newColumnCustomMapper.queryListByUid(uid);
		return list;
	}


}
