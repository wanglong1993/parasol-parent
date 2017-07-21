package com.ginkgocap.parasol.file.service;

import com.ginkgocap.parasol.file.model.UserFileCategory;
import com.ginkgocap.parasol.file.model.UserFileCategoryExt;

import java.util.List;

/**
 * Created by xutlong on 2017/7/7.
 */
public interface UserFileCategoryServer {

    /**
     * 添加用户文件或者目录
     * @param pojo
     * @return
     */
    int insert(UserFileCategory pojo);

    /**
     * 集合方式添加用户文件或者目录
     * @param pojos
     * @return
     */
    int insertList(List< UserFileCategory> pojos);

    /**
     * 查询用户文件或者目录
     * @param pojo
     * @return
     */
    List<UserFileCategory> select(UserFileCategory pojo);

    /**
     * 根据ID查询目录信息
     * @param id
     * @return
     */
    UserFileCategory selectById(Long id);

    /**
     * 修改用户文件目录
     * @param pojo
     * @return
     */
    int update(UserFileCategory pojo);

    /**
     * 根据目录id获取用户云盘文件
     * @param category
     * @param page
     * @param size
     * @return
     */
    List<UserFileCategory> getFileAndCategory(long userId,long category,int page ,int size);


    /**
     * 根据目录Id和fileType获取云盘文件
     * @param loginUserId
     * @param fileType
     * @param category
     * @param page
     * @param size
     * @return
     */
    List<UserFileCategoryExt> getFileAndCategoryByFileType(String keyword,Long loginUserId, int fileType, Long category,
                                                        int isDir,int page, int size);

    /**
     * 根据目录Id和fileType获取云盘文件 文件所占容量
     * @param loginUserId
     * @param fileType
     * @param category
     * @param isDir
     * @return
     */
    long getFileSizeSumByFileType(Long loginUserId, int fileType, Long category,
                                                           int isDir);

    /**
     * 检查名称是否合法
     * @param userId
     * @param parentId
     * @param name
     * @return
     */
    boolean existUserCategory(Long userId, Long parentId, String name,int isDir);

    /**
     * 根据文件id和目录Id确定文件是否存在于目录下
     * @param userId
     * @param fid
     * @param cid
     * @return
     */
    int selectByIdAndCId(long userId, String fid, String cid);

    /**
     * 批量删除文件目录
     * @Param userId 用户Id
     * @param idList ids id的集合逗号分隔
     */
    void bathDelete(long userId,List<Long> idList);

}
