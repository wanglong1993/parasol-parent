package org.parasol.column.dao;

import org.parasol.column.entity.ColumnSelf;

public interface ColumnSelfDao {

	int deleteByPrimaryKey(Long id);

    int insert(ColumnSelf record);
    
    ColumnSelf selectByPrimaryKey(Long id);
    
    int updateByPrimaryKey(ColumnSelf record);
}
