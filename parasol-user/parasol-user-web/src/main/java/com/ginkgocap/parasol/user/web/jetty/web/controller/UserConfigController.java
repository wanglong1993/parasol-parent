package com.ginkgocap.parasol.user.web.jetty.web.controller;

import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;
import com.ginkgocap.parasol.user.model.UserConfig;
import com.ginkgocap.parasol.user.service.UserConfigerService;
import com.ginkgocap.parasol.user.web.jetty.web.utils.Prompt;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by xutlong on 2016/5/31.
 * 用户隐私设置ConfigController
 */
@RestController
public class UserConfigController extends BaseControl{

    private static Logger logger = Logger.getLogger(UserConfigController.class);

    @Autowired
    private UserConfigerService userConfigerService;


    /**
     * 设置用户浏览我的主页权限
     * @param homePageVisible 浏览我的主页
     * @return
     * @throws Exception
     */
    @RequestMapping(path = { "/user/user/userSetHomePageVisible" }, method = { RequestMethod.POST})
    public MappingJacksonValue userSetHomePageVisible(HttpServletRequest request, HttpServletResponse response
            , @RequestParam(name = "homePageVisible",required = true) String homePageVisible
            , @RequestParam(name = "ids", required = true) String ids
    )throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Long userId=null;
        try {
            userId = LoginUserContextHolder.getUserId();
            if(userId==null){
                resultMap.put("message", Prompt.userId_is_null_or_empty);
                resultMap.put("status",0);
                return new MappingJacksonValue(resultMap);
            }
            if(StringUtils.isEmpty(homePageVisible)){
                resultMap.put("message", "浏览我的主页设置不能为空");
                resultMap.put("status",0);
                return new MappingJacksonValue(resultMap);
            }
            UserConfig userConfig=userConfigerService.getUserConfig(userId);
            if(ObjectUtils.isEmpty(userConfig)){
                resultMap.put("message", "用户设置不存在");
                resultMap.put("status",0);
                return new MappingJacksonValue(resultMap);
            }
            userConfig.setHomePageVisible(new Byte(homePageVisible));
            userConfigerService.updateUserConfig(userConfig);
            // 存储可见的部分好友
            if (homePageVisible.equals("3")) {

            }
            resultMap.put( "status", 1);
            resultMap.put("message", "设置成功!");
            return new MappingJacksonValue(resultMap);
        }catch (Exception e ){
            logger.info(e.getStackTrace());
            throw e;
        }
    }
    /**
     * 设置用户对我评价权限
     *
     * @param evaluateVisible 对我评价
     * @return
     * @throws Exception
     */
    @RequestMapping(path = { "/user/user/userSetEvaluateVisible" }, method = { RequestMethod.POST})
    public MappingJacksonValue userSetEvaluateVisible(HttpServletRequest request,HttpServletResponse response
            ,@RequestParam(name = "evaluateVisible",required = true) String evaluateVisible
    )throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        Long userId=null;
        try {
            userId = LoginUserContextHolder.getUserId();
            if(userId==null){
                resultMap.put("message", Prompt.userId_is_null_or_empty);
                resultMap.put("status",0);
                return new MappingJacksonValue(resultMap);
            }
            if(StringUtils.isEmpty(evaluateVisible)){
                resultMap.put("message", "对我评价设置不能为空");
                resultMap.put("status",0);
                return new MappingJacksonValue(resultMap);
            }
            UserConfig userConfig=userConfigerService.getUserConfig(userId);
            if(ObjectUtils.isEmpty(userConfig)){
                resultMap.put("message", "用户设置不存在");
                resultMap.put("status",0);
                return new MappingJacksonValue(resultMap);
            }
            userConfig.setHomePageVisible(new Byte(evaluateVisible));
            userConfigerService.updateUserConfig(userConfig);
            resultMap.put( "status", 1);
            resultMap.put("message", "设置成功!");
            return new MappingJacksonValue(resultMap);
        }catch (Exception e ){
            logger.info(e.getStackTrace());
            throw e;
        }
    }









}
