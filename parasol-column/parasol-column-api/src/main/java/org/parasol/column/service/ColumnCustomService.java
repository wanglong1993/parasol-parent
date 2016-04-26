package org.parasol.column.service;

import java.util.List;

import org.parasol.column.entity.ColumnCustom;
import org.parasol.column.entity.ColumnSelf;

public interface ColumnCustomService {
	
	int deleteByPrimaryKey(Long id);

    int insert(ColumnCustom record);
    
    ColumnCustom selectByPrimaryKey(Long id);
    
    int updateByPrimaryKey(ColumnCustom record);
    
    List<ColumnSelf> queryListByPidAndUserId(Long pid,Long uid);
    
    ColumnCustom queryByCid(Long cid);
    
    int init(Long uid);
    
    int replace(Long uid,List<ColumnSelf> newList);

}
