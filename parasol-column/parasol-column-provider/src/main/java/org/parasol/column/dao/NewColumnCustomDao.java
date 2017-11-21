package org.parasol.column.dao;

import java.util.List;

import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.entity.NewColumnCustom;


public interface NewColumnCustomDao {

    int insert(NewColumnCustom record);
    
    NewColumnCustom queryBySid(Long sid);
    
    List<ColumnSelf> queryListByUid(Long uid);
    
    int deleteByUserId(Long uid);
    
    /*int insertBatch(List<NewColumnCustom> list);*/

    int updateByUid(NewColumnCustom newColumnCustom, Long uid);

}
