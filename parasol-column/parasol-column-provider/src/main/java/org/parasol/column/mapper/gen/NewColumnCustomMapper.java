package org.parasol.column.mapper.gen;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.parasol.column.entity.ColumnSelf;
import org.parasol.column.entity.NewColumnCustom;
import org.parasol.column.entity.NewColumnCustomExample;

public interface NewColumnCustomMapper {
    int countByExample(NewColumnCustomExample example);

    int deleteByExample(NewColumnCustomExample example);

    int insert(NewColumnCustom record);

    int insertSelective(NewColumnCustom record);

    List<NewColumnCustom> selectByExample(NewColumnCustomExample example);

    int updateByExampleSelective(@Param("record") NewColumnCustom record, @Param("example") NewColumnCustomExample example);

    int updateByExample(@Param("record") NewColumnCustom record, @Param("example") NewColumnCustomExample example);

    List<ColumnSelf> queryListByUid(@Param("userId")Long userId);

    int updateByUid(@Param("record") NewColumnCustom record,@Param("userId")Long userId);
}