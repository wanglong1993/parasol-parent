package com.ginkgocap.parasol.file.service.impl;

import com.ginkgocap.parasol.file.dao.UserFileCategoryDao;
import com.ginkgocap.parasol.file.model.UserFileCategory;
import com.ginkgocap.parasol.file.model.UserFileCategoryExt;
import com.ginkgocap.parasol.file.service.UserFileCategoryServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xutlong on 2017/7/7.
 */
@Service("userFileCategoryServer")
public class UserFileCategoryServerImpl  implements UserFileCategoryServer{

    @Autowired
    UserFileCategoryDao userFileCategoryDao;

    @Override
    public List<UserFileCategory> getFileAndCategory(long userId, long category, int page, int size) {
        return null;
    }

    @Override
    public List<UserFileCategoryExt> getFileAndCategoryByFileType(String keyword, Long loginUserId, int fileType, Long category, int isDir, int page, int size) {
        Map<String,Object> pojo = new HashMap<String, Object>();
        pojo.put("userId",loginUserId);
        pojo.put("fileType",fileType);
        pojo.put("parentId",category);
        pojo.put("start",(page+1)*size);
        pojo.put("size",size);
        pojo.put("keyword",keyword);
        pojo.put("isDir",isDir);
        return userFileCategoryDao.getFileAndCategoryByFileType(pojo);
    }

    @Override
    public boolean existUserCategory(Long userId, Long parentId, String name, int isDir) {
        Map<String, Object> pojo = new HashMap<String, Object>();
        pojo.put("userId",userId);
        pojo.put("parentId",parentId);
        pojo.put("name", name);
        pojo.put("isDir",isDir);
        System.out.println(userFileCategoryDao.existUserCategory(pojo));
        return userFileCategoryDao.existUserCategory(pojo) > 0 ? false : true;
    }

    public int insert(UserFileCategory pojo){
        return userFileCategoryDao.insert(pojo);
    }

    public int insertList(List< UserFileCategory> pojos){
        return userFileCategoryDao.insertList(pojos);
    }

    public List<UserFileCategory> select(UserFileCategory pojo){
        return userFileCategoryDao.select(pojo);
    }

    @Override
    public UserFileCategory selectById(Long id) {
        return userFileCategoryDao.selectById(id);
    }

    @Override
    public int selectByIdAndCId(long userId, String fid, String cid) {
        List<UserFileCategory> ulist = userFileCategoryDao.selectByAndCId(userId,fid,cid);
        if (null != ulist) {
            return ulist.size();
        } else {
            return ulist.size();
        }
    }

    public int update(UserFileCategory pojo){
        return userFileCategoryDao.update(pojo);
    }

    @Override
    public void bathDelete(List<Long> idList) {
        userFileCategoryDao.bathDelete(idList);
    }
}
