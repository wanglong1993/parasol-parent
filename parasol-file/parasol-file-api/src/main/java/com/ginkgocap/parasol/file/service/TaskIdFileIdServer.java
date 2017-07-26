package com.ginkgocap.parasol.file.service;

import com.ginkgocap.parasol.file.model.TaskIdFileId;

import java.util.List;

public interface TaskIdFileIdServer {

    int insert(TaskIdFileId pojo);

    int insertList(List< TaskIdFileId> pojos);

    List<TaskIdFileId> select(TaskIdFileId pojo);

    int update(TaskIdFileId pojo);

    void deleteByTaskId(String taskId);

    void deleteByTaskIdAndFileId(String taskId, String fileId);
}
