package org.parasol.column.mapper.gen;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.parasol.column.entity.ColumnCustom;
import org.parasol.column.entity.ColumnCustomExample;

public interface ColumnCustomMapper {
    int countByExample(ColumnCustomExample example);

    int deleteByExample(ColumnCustomExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ColumnCustom record);

    int insertSelective(ColumnCustom record);

    List<ColumnCustom> selectByExample(ColumnCustomExample example);

    ColumnCustom selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ColumnCustom record, @Param("example") ColumnCustomExample example);

    int updateByExample(@Param("record") ColumnCustom record, @Param("example") ColumnCustomExample example);

    int updateByPrimaryKeySelective(ColumnCustom record);

    int updateByPrimaryKey(ColumnCustom record);
    
    int insertBatch(List<ColumnCustom> list);
}