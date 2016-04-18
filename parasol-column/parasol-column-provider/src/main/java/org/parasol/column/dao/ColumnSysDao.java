package org.parasol.column.dao;

import java.util.List;

import org.parasol.column.entity.ColumnSys;

public interface ColumnSysDao {

	int deleteByPrimaryKey(Long id);

    int insert(ColumnSys record);
    
    ColumnSys selectByPrimaryKey(Long id);
    
    int updateByPrimaryKey(ColumnSys record);
    
    List<ColumnSys> selectAll();
}
