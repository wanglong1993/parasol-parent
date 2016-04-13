package org.parasol.column.mapper.gen;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.parasol.column.entity.ColumnSys;
import org.parasol.column.entity.ColumnSysExample;

public interface ColumnSysMapper {
    int countByExample(ColumnSysExample example);

    int deleteByExample(ColumnSysExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ColumnSys record);

    int insertSelective(ColumnSys record);

    List<ColumnSys> selectByExample(ColumnSysExample example);

    ColumnSys selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ColumnSys record, @Param("example") ColumnSysExample example);

    int updateByExample(@Param("record") ColumnSys record, @Param("example") ColumnSysExample example);

    int updateByPrimaryKeySelective(ColumnSys record);

    int updateByPrimaryKey(ColumnSys record);
}