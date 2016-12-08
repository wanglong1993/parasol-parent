package org.parasol.column.service;

import java.util.List;

import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.entity.ColumnSys;

public interface ColumnSysService {
	
	ColumnSys selectByPrimaryKey(Long id);
	
	List<ColumnSys> selectAll();
	
	//查询系统级的30条默认数据
	List<ColumnSelf> queryDefaultSysColumn(Long pid, Long uid);

}
