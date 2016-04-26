package org.parasol.column.dao;

import java.util.List;

import org.parasol.column.entity.ColumnSelf;

public interface ColumnSelfDao {

	int deleteByPrimaryKey(Long id);

    int insert(ColumnSelf record);
    
    ColumnSelf selectByPrimaryKey(Long id);
    
    int updateByPrimaryKey(ColumnSelf record);
    
    List<ColumnSelf> queryListByPidAndUserId(Long pid, Long uid);
    
    List<ColumnSelf> queryListByPid(Long pid);
    /**
     * 查询最大order_num栏目
     * @param pid
     * @param uid
     * @return
     */
    ColumnSelf selectMaxOrderColumn(Long pid, Long uid);
}
