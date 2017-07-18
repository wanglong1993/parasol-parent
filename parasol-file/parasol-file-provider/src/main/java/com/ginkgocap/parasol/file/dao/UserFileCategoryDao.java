package com.ginkgocap.parasol.file.dao;

import com.ginkgocap.parasol.file.model.UserFileCategoryExt;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

import com.ginkgocap.parasol.file.model.UserFileCategory;
import org.springframework.stereotype.Component;

@Component
public interface UserFileCategoryDao {

    int insert(@Param("pojo") UserFileCategory pojo);

    int insertList(@Param("pojos") List< UserFileCategory> pojo);

    List<UserFileCategory> select(@Param("pojo") UserFileCategory pojo);

    int update(@Param("pojo") UserFileCategory pojo);

    List<UserFileCategoryExt> getFileAndCategoryByFileType(@Param("pojos") Map<String, Object> pojos);

    boolean existUserCategory(@Param("pojo") Map<String, Object> pojo);

    UserFileCategory selectById(@Param("id") Long id);

    List<UserFileCategory> selectByAndCId(@Param("userId") long userId, @Param("id") String fid,@Param("cid") String cid);

    void bathDelete(@Param("ids") List<Long> idList);
}
