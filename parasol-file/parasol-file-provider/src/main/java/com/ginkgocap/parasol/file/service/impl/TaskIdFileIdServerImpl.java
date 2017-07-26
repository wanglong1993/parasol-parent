package com.ginkgocap.parasol.file.service.impl;

import com.ginkgocap.parasol.file.dao.TaskIdFileIdDao;
import com.ginkgocap.parasol.file.model.TaskIdFileId;
import com.ginkgocap.parasol.file.service.TaskIdFileIdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xutlong on 2017/7/25.
 */
@Service("taskIdFileIdServer")
public class TaskIdFileIdServerImpl implements TaskIdFileIdService {

    @Autowired
    TaskIdFileIdDao taskIdFileIdDao;

    @Override
    public int insert(TaskIdFileId pojo) {
        return taskIdFileIdDao.insert(pojo);
    }

    @Override
    public int insertList(List<TaskIdFileId> pojos) {
        return taskIdFileIdDao.insertList(pojos);
    }

    @Override
    public List<TaskIdFileId> select(TaskIdFileId pojo) {
        return taskIdFileIdDao.select(pojo);
    }

    @Override
    public int update(TaskIdFileId pojo) {
        return taskIdFileIdDao.update(pojo);
    }

    @Override
    public void deleteByTaskId(String taskId) {
        taskIdFileIdDao.deleteByTaskId(taskId);
    }

    @Override
    public void deleteByTaskIdAndFileId(String taskId, String fileId) {
        taskIdFileIdDao.deleteByTaskIdAndFileId(taskId,fileId);
    }
}
