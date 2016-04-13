package org.parasol.column.mapper.gen;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.entity.ColumnSelfExample;

public interface ColumnSelfMapper {
    int countByExample(ColumnSelfExample example);

    int deleteByExample(ColumnSelfExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ColumnSelf record);

    int insertSelective(ColumnSelf record);

    List<ColumnSelf> selectByExample(ColumnSelfExample example);

    ColumnSelf selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ColumnSelf record, @Param("example") ColumnSelfExample example);

    int updateByExample(@Param("record") ColumnSelf record, @Param("example") ColumnSelfExample example);

    int updateByPrimaryKeySelective(ColumnSelf record);

    int updateByPrimaryKey(ColumnSelf record);
}