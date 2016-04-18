package org.parasol.column.service;

import java.util.List;

import org.parasol.column.entity.ColumnSys;

public interface ColumnSysService {
	
	ColumnSys selectByPrimaryKey(Long id);
	
	List<ColumnSys> selectAll();

}
