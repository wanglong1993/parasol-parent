package com.ginkgocap.parasol.file.dao;

import org.apache.ibatis.annotations.Param;
import java.util.List;
import com.ginkgocap.parasol.file.model.FileIndex;

public interface FileIndexDao {

    long insert(FileIndex pojo);

    int insertList(@Param("pojos") List< FileIndex> pojo);

    List<FileIndex> select(@Param("pojo") FileIndex pojo);

    int update(@Param("pojo") FileIndex pojo);

    FileIndex selectById(@Param("id") long id);

    int delete(@Param("userId") long userId, @Param("id") long id);

    List<FileIndex> getFileIndexByTaskId(@Param("taskId") String taskId);

    List<FileIndex> getFileIndexesByCreaterId(@Param("userId") long createrId);

    int deleteFileIndexesByTaskId(@Param("taskId") String taskId);

    List<FileIndex> selectFileIndexesByIds(@Param("ids") List<Long> ids);
}
