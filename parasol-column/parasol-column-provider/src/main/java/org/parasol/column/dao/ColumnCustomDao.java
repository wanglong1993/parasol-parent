package org.parasol.column.dao;

import org.parasol.column.entity.ColumnCustom;

public interface ColumnCustomDao {

	int deleteByPrimaryKey(Long id);

    int insert(ColumnCustom record);
    
    ColumnCustom selectByPrimaryKey(Long id);
    
    int updateByPrimaryKey(ColumnCustom record);
}
