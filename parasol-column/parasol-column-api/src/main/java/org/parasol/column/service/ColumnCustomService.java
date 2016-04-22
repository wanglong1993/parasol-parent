package org.parasol.column.service;

import java.util.List;

import org.parasol.column.entity.ColumnCustom;

public interface ColumnCustomService {
	
	int deleteByPrimaryKey(Long id);

    int insert(ColumnCustom record);
    
    ColumnCustom selectByPrimaryKey(Long id);
    
    int updateByPrimaryKey(ColumnCustom record);
    
    List<ColumnCustom> queryListByPidAndUserId(Long pid,Long uid);
    
    ColumnCustom queryByCid(Long cid);
    
    int init(Long uid);
    
    int replace(Long uid,List<ColumnCustom> newList);

}
