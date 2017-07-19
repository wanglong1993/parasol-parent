package com.ginkgocap.parasol.file.web.jetty.web.controller;

import com.ginkgocap.parasol.file.exception.FileIndexServiceException;
import com.ginkgocap.parasol.file.model.FileIndex;
import com.ginkgocap.parasol.file.model.UserFileCategory;
import com.ginkgocap.parasol.file.model.UserFileCategoryExt;
import com.ginkgocap.parasol.file.service.FileIndexService;
import com.ginkgocap.parasol.file.service.UserFileCategoryServer;
import com.ginkgocap.parasol.file.web.jetty.web.ResponseError;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created by xutlong on 2017/7/7.
 */
@RestController
public class UserFileCategoryController extends BaseControl {

    @Value("${jtmobileserver.root}")
    private String nginxRoot;
    @Value("${nginxDFSRoot}")
    private String nginxDFSRoot;

    private static final String parameterId = "id";
    private static final String paramrterKeyWord = "keyword";
    private static final String parameterIds = "ids";
    private static final String parameterCategoryId = "categoryId"; // 查询的目录id
    private static final String parameterpage = "page";
    private static final String parametersize = "size";
    private static final String parameterfileType = "fileType";
    private static final String parameterParentId = "parentId";
    private static final String parameterIsDir = "isDir";
    private static final String parameterServerFileName = "serverFileName";

    @Autowired
    UserFileCategoryServer userFileCategoryServer;
    @Autowired
    FileIndexService fileIndexService;

    /**
     * 文件管理首页
     * @param categoryId
     * @param page
     * @param size
     * @return
     * @throws FileIndexServiceException
     * @throws IOException
     * @throws MyException
     */
    @ResponseBody
    @RequestMapping(path = { "/file/queryAllRCategory" }, method = { RequestMethod.GET })
    public Map<String,Object> queryAllRCategory(
            @RequestParam(name = UserFileCategoryController.parameterCategoryId ,defaultValue = "0") String categoryId,
            @RequestParam(name = UserFileCategoryController.parameterpage,defaultValue = "0") String page,
            @RequestParam(name = UserFileCategoryController.parametersize, defaultValue = "9999") String size,
            @RequestParam(name = UserFileCategoryController.parameterfileType, defaultValue = "0") String filetype,
            HttpServletRequest request) throws FileIndexServiceException, IOException, MyException {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            Long loginUserId=this.getUserId(request);
            // 0.校验输入参数（框架搞定，如果业务业务搞定）
            // 1.查询后台服务
            // 1.1 先获取用户的对应云盘记录（包括文件和目录）

            List<UserFileCategoryExt> userFileCategoryList = userFileCategoryServer.getFileAndCategoryByFileType("",loginUserId,
                        Integer.parseInt(filetype),Long.parseLong(categoryId),3,Integer.parseInt(page),Integer.parseInt(size));
            for (UserFileCategoryExt ufc : userFileCategoryList) {
                UserFileCategoryExt ue = new UserFileCategoryExt();
                ue.setCtime(ufc.getCtime());
                ue.setIsDir(ufc.getIsDir());
                ue.setServerFilename(ufc.getServerFilename());
                ue.setParentId(ufc.getParentId());
                if (ufc.getIsDir() == 0) {
                    if (ufc.getModuleType() == 100) {
                        ufc.setUrl(nginxRoot + "/mobile/download?id=" + ufc.getFileId());
                    } else {
                        ufc.setUrl(nginxDFSRoot + "/" + ufc.getServerHost() + "/" + ufc.getFilePath() + "?filename=" + ufc.getFileTitle());
                    }
                }
            }
            result.put("page",userFileCategoryList);
            result.put("success",true);
            return genRespBody(result,null);
        } catch (Exception e) {
            Map<String,Object> notificationMap = new HashMap<String,Object>();
            notificationMap.put("notifCode", "1010");
            notificationMap.put("notifInfo", e.getMessage());
            result.put("success",false);
            return genRespBody(result,notificationMap);
        }
    }

    /**
     * 搜索文件
     * @param keyword
     * @param fileType
     * @param page
     * @param size
     * @param parentId
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/file/search",method = RequestMethod.POST)
    public Map<String,Object> searchRCategory(
            @RequestParam(name = UserFileCategoryController.paramrterKeyWord, defaultValue = "") String keyword,
            @RequestParam(name = UserFileCategoryController.parameterfileType,defaultValue = "0") String fileType,
            @RequestParam(name = UserFileCategoryController.parameterpage,defaultValue = "0") String page,
            @RequestParam(name = UserFileCategoryController.parametersize,defaultValue = "9999") String size,
            @RequestParam(name = UserFileCategoryController.parameterParentId,defaultValue = "0") String parentId,
            HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String,Object>();
        long userId = getUserId(request);
        try {
            List<UserFileCategoryExt> ulist = userFileCategoryServer.getFileAndCategoryByFileType(keyword,userId,Integer.parseInt(fileType),
                    Long.parseLong(parentId),0,Integer.parseInt(page),Integer.parseInt(size));
            for (UserFileCategoryExt ufc : ulist) {
                UserFileCategoryExt ue = new UserFileCategoryExt();
                ue.setCtime(ufc.getCtime());
                ue.setIsDir(ufc.getIsDir());
                ue.setServerFilename(ufc.getServerFilename());
                ue.setParentId(ufc.getParentId());
                if (ufc.getIsDir() == 0) {
                    if (ufc.getModuleType() == 100) {
                        ufc.setUrl(nginxRoot + "/mobile/download?id=" + ufc.getFileId());
                    } else {
                        ufc.setUrl(nginxDFSRoot + "/" + ufc.getServerHost() + "/" + ufc.getFilePath() + "?filename=" + ufc.getFileTitle());
                    }
                }
            }
            result.put("page",ulist);
            result.put("success",true);
            return genRespBody(result,null);
        } catch (Exception e) {
            Map<String,Object> notificationMap = new HashMap();
            result.put("success",false);
            notificationMap.put("notifCode", "1010");
            notificationMap.put("notifInfo", "服务器错误");
            return genRespBody(result,notificationMap);
        }
    }

    /**
     * 新增或者修改用户目录
     */
    @ResponseBody
    @RequestMapping("/file/saveCategory")
    public Map<String,Object> saveOrUpdateCategory(
            @RequestParam(name = UserFileCategoryController.parameterId , defaultValue = "0") String _id,
            @RequestParam(name = UserFileCategoryController.parameterParentId, defaultValue = "0") String _parentId,
            @RequestParam(name = UserFileCategoryController.parameterServerFileName,defaultValue = "") String name,
            @RequestParam(name = UserFileCategoryController.parameterIsDir, defaultValue = "1") String isDir,
            HttpServletRequest request
    ) {
        Map<String, Object> result = new HashMap<String, Object>();
        Long userId=this.getUserId(request);
        try {
            Long id = Long.parseLong(_id);
            Long parentId = Long.parseLong(_parentId);
            name = name.trim();
            if (name.equals("") || null == name) {
                // 名称无效 直接返回
                Map<String,Object> notificationMap = new HashMap<String,Object>();
                result.put("success",false);
                notificationMap.put("notifCode", "1013");
                notificationMap.put("notifInfo", "无效的名称");
                return genRespBody(result,notificationMap);
            }
            // 检查文件名是否合法
            if (existUserCategory(userId,parentId,name)) {
                // 名称无效 直接返回
                Map<String,Object> notificationMap = new HashMap<String,Object>();
                result.put("success",false);
                notificationMap.put("notifCode", "1013");
                notificationMap.put("notifInfo", "目录重复");
                return genRespBody(result,notificationMap);
            }
            if (id == 0) {
                // 添加文件或目录
                UserFileCategory userFileCategory = new UserFileCategory();
                userFileCategory.setServerFilename(name);
                userFileCategory.setFielId(0l);
                userFileCategory.setSortId("");
                userFileCategory.setParentId(parentId);
                userFileCategory.setUserId(userId);
                userFileCategory.setIsDir(Integer.parseInt(isDir));
                userFileCategory.setCtime(new Date());
                int uid = userFileCategoryServer.insert(userFileCategory);
                result.put("id",uid);
            } else {
                // 修改名称操作
                UserFileCategory userFileCategory = userFileCategoryServer.selectById(id);
                if (name != null && !name.equals(""))
                    userFileCategory.setServerFilename(name);
                if (parentId != null)
                    userFileCategory.setParentId(parentId);
                userFileCategoryServer.update(userFileCategory);
            }
            result.put("success",true);
            return genRespBody(result,null);

        }catch (Exception e) {
            Map<String,Object> notificationMap = new HashMap<String,Object>();
            result.put("success",false);
            notificationMap.put("notifCode", "1013");
            notificationMap.put("notifInfo", e.getMessage());
            return genRespBody(result,notificationMap);
        }
    }

    /**
     * 建立文件与目录的关联关系
     * @param fid
     * @param cid
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/file/saveOrUpdateRCategory", method = RequestMethod.POST)
    public Map<String,Object> saveOrUpdateRCategory(
            @RequestParam(name = UserFileCategoryController.parameterId,defaultValue = "") String fid,
            @RequestParam(name = UserFileCategoryController.parameterCategoryId,defaultValue = "") String cid,
            HttpServletRequest request ){
        Map<String,Object> result = new HashMap<String, Object>();
        try {
            long userId = getUserId(request);
            // 先判断此文件是否已经存在于目录下
            int count = userFileCategoryServer.selectByIdAndCId(userId, fid, cid);
            if (count > 0) {
                Map<String,Object> notificationMap = new HashMap<String,Object>();
                result.put("success", false);
                notificationMap.put("notifCode", "1013");
                notificationMap.put("notifInfo", "文件已经存在目录下");
                return genRespBody(result,notificationMap);
            }
            UserFileCategory userFileCategory = new UserFileCategory();
            userFileCategory.setParentId(Long.parseLong(cid));
            userFileCategory.setCtime(new Date());
            userFileCategory.setIsDir(0);
            userFileCategory.setUserId(userId);
            userFileCategory.setFielId(Long.parseLong(fid));
            int id = userFileCategoryServer.insert(userFileCategory);
            if (id > 0) {
                result.put("success",true);
                result.put("id",id);
                return genRespBody(result,null);
            }
        } catch (Exception e) {
            Map<String,Object> notificationMap = new HashMap<String,Object>();
            result.put("success", false);
            notificationMap.put("notifCode", "1013");
            notificationMap.put("notifInfo", e.getMessage());
            return genRespBody(result,notificationMap);
        }
        return null;
    }

    /**
     * 批量删除文件或者目录
     * @param ids
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/file/deleteBoth.json",method = RequestMethod.GET)
    public Map<String,Object> delete(
            @RequestParam(name = UserFileCategoryController.parameterIds,defaultValue = "0") String ids,
            HttpServletRequest request ) {
        long userId = getUserId(request);
        Map<String,Object> result = new HashMap<String,Object>();
        try {
            if (ids.trim().equals("")) {
                Map<String,Object> notificationMap = new HashMap<String,Object>();
                result.put("success", false);
                notificationMap.put("notifCode", "1013");
                notificationMap.put("notifInfo", "目录不合法");
                return genRespBody(result,notificationMap);
            }
            List<Long> idList = covertIdsToLong(ids);
            userFileCategoryServer.bathDelete(idList);
            result.put("success",true);
            return genRespBody(result,null);
        } catch (Exception e) {
            Map<String,Object> notificationMap = new HashMap<String,Object>();
            result.put("success", false);
            notificationMap.put("notifCode", "1013");
            notificationMap.put("notifInfo", e.getMessage());
            return genRespBody(result,notificationMap);
        }
    }



    @Override
    protected <T> void processBusinessException(ResponseError error, Exception ex) {

    }

    private boolean existUserCategory(Long userId, Long parentId, String name) {
        return userFileCategoryServer.existUserCategory(userId,parentId,name);
    }

    private List<Long> covertIdsToLong(String fileIds) {
        List<Long> ids = new ArrayList<Long>();
        String[] fids = fileIds.split(",");
        for (String id : fids) {
            if (!id.trim().equals("")) {
                ids.add(Long.parseLong(id));
            }
        }
        return ids;
    }
}
