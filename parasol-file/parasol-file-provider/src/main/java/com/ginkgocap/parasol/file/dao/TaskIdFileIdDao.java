package com.ginkgocap.parasol.file.dao;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.ginkgocap.parasol.file.model.TaskIdFileId;

public interface TaskIdFileIdDao {

    int insert(@Param("pojo") TaskIdFileId pojo);

    int insertList(@Param("pojos") List< TaskIdFileId> pojo);

    List<TaskIdFileId> select(@Param("pojo") TaskIdFileId pojo);

    int update(@Param("pojo") TaskIdFileId pojo);

    void deleteByTaskId(@Param("taskId") String taskId);

    void deleteByTaskIdAndFileId(@Param("taskId") String taskId,@Param("fileId") String fileId);

    List<Long> selectByTaskId(@Param("taskId") String taskId);
}
