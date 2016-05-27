package com.ginkgocap.parasol.user.web.jetty.web.controller;

import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;
import com.ginkgocap.parasol.user.exception.UserBlackListServiceException;
import com.ginkgocap.parasol.user.model.UserBlackList;
import com.ginkgocap.parasol.user.service.UserBlackListService;
import com.ginkgocap.parasol.user.web.jetty.web.utils.Prompt;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xutlong on 2016/5/27.
 * 用户黑名单
 */
@RestController
public class UserBlackListController extends BaseControl{

    private static Logger logger = Logger.getLogger(UserBlackListController.class);

    @Autowired
    private UserBlackListService userBlackListService;

    /**
     * 创建黑名单
     * @param  userBlackListId 需要添加到用户黑名单的用户id
     * @throws Exception
     * @return MappingJacksonValue
     */
    @RequestMapping(path = "/user/userblacklist/save", method = {RequestMethod.POST})
    public MappingJacksonValue saveUserBlackLsit(HttpServletRequest request, HttpServletResponse response
        ,@RequestParam(name = "userBlackListId",required = false) Long userBlackListId)  {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        UserBlackList userBlackListEntary = new UserBlackList();
        Long userId = null;
        Long appId = null;
        try {
            userId = LoginUserContextHolder.getUserId();
            if(userId==null){
                resultMap.put("message", Prompt.userId_is_null_or_empty);
                resultMap.put("status",0);
                return new MappingJacksonValue(resultMap);
            }
            appId = LoginUserContextHolder.getAppKey();
            if(ObjectUtils.isEmpty(appId)){
                resultMap.put( "message", "appId不能为空！");
                resultMap.put( "status", 0);
                return new MappingJacksonValue(resultMap);
            }
            userBlackListEntary.setUserId(userId);
            userBlackListEntary.setBlackUserId(userBlackListId);
            userBlackListEntary.setAppId(appId);
            userBlackListEntary.setCtime(System.currentTimeMillis());
            Long balcklistId = userBlackListService.save(userBlackListEntary);
            resultMap.put("message", "success");
            resultMap.put("status", 1);
            resultMap.put("balcklsitId",balcklistId);
            return new MappingJacksonValue(resultMap);
        } catch(Exception e) {
            resultMap.put("message", "创建黑名单失败！");
            resultMap.put("status", 0);
            return new MappingJacksonValue(resultMap);
        }
    }

    /**
     * 根据Id删除黑名单
     * @param  userblacklistId 需要添加到用户黑名单的用户id
     * @throws Exception
     * @return MappingJacksonValue
     */
    @RequestMapping(path = {"/user/userblacklist/delete"}, method = {RequestMethod.GET})
    public MappingJacksonValue deleteUserBlackList(HttpServletRequest request, HttpServletResponse response
            ,@RequestParam(name = "userblacklistId",required = false) Long userblacklistId) throws UserBlackListServiceException {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Long userId = null;
        Long appId = null;
        try {
            userId = LoginUserContextHolder.getUserId();
            if(userId==null){
                resultMap.put("message", Prompt.userId_is_null_or_empty);
                resultMap.put("status",0);
                return new MappingJacksonValue(resultMap);
            }
            appId = LoginUserContextHolder.getAppKey();
            if(ObjectUtils.isEmpty(appId)){
                resultMap.put( "message", "appId不能为空！");
                resultMap.put( "status", 0);
                return new MappingJacksonValue(resultMap);
            }
            Boolean sgin = userBlackListService.delete(userblacklistId);
            resultMap.put("message",sgin);
            resultMap.put("status", 1);
            return new MappingJacksonValue(resultMap);
        } catch (Exception e) {
            logger.error("删除黑名单失败！");
            throw new UserBlackListServiceException("删除黑名单失败！");
        }
    }
}
