package org.parasol.column.service;

import java.util.List;

import org.parasol.column.entity.ColumnSelf;

public interface ColumnSelfService {
	
	int deleteByPrimaryKey(Long id);

    int insert(ColumnSelf record);
    
    ColumnSelf selectByPrimaryKey(Long id);
    
    int updateByPrimaryKey(ColumnSelf record);
    
    List<ColumnSelf> queryListByPidAndUserId(Long pid, Long uid);
    
    List<ColumnSelf> queryListByPid(Long pid);
    
    ColumnSelf selectMaxOrderColumn(Long pid, Long uid);

}
