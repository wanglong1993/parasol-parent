package org.parasol.column.service.impl;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.parasol.column.dao.ColumnSelfDao;
import org.parasol.column.dao.ColumnSysDao;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.entity.ColumnSys;
import org.parasol.column.service.ColumnSysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("columnSysService")
public class ColumnSysServiceImpl implements ColumnSysService {
	
	@Resource 
	private ColumnSysDao sysDao;

	@Autowired
	private ColumnSelfDao selfDao;

	@Override
	public ColumnSys selectByPrimaryKey(Long id) {
		// TODO Auto-generated method stub
		return sysDao.selectByPrimaryKey(id);
	}

	@Override
	public List<ColumnSys> selectAll() {
		// TODO Auto-generated method stub
		return sysDao.selectAll();
	}

	@Override
	public List<ColumnSelf> queryDefaultSysColumn(Long pid, Long uid) {
		// TODO Auto-generated method stub
		return selfDao.queryListByPidAndUserId(pid, uid);

	}

}
