package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import com.alibaba.fastjson.JSON;
import com.ginkgocap.ywxt.organ.service.CustomerService;
import com.gintong.ywxt.organization.model.OrganRegister;
import com.gintong.ywxt.organization.service.OrganRegisterService;
import com.gintong.ywxt.organization.util.Constants;
import jersey.repackaged.com.google.common.collect.Maps;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
@Controller
@RequestMapping("/organ")
/**
 * Created by jbqiu on 2016/5/26.
 * 新版需求组织，客户controller
 */
public class OrganLoginController extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(OrganLoginController.class);

    @Autowired
    public OrganRegisterService organRegisterService;


    @Resource
    private CustomerService customerService;


    /**
     * 完善组织资料
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/completeInfo.json", method = RequestMethod.POST)
    public Map<String, Object> completeInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("完善组织信息");


        Map<String, Object> result = Maps.newHashMap();
        try {

            // Long loginAppId = LoginUserContextHolder.getAppKey();
            Long loginUserId = LoginUserContextHolder.getUserId();
            String requestJson = getJsonParamStr(request);
            if (StringUtils.isNotBlank(requestJson)) {
                JSONObject j = JSONObject.fromObject(requestJson);
                OrganRegister org = JSON.parseObject(requestJson, OrganRegister.class);

                //增加组织基本信息
                result = organRegisterService.updateOrganInformation(org);
                result.put("id", org.getId());
            }
        } catch (Exception e) {
            logger.error("系统异常,请稍后再试", e);
            return returnFailMSGNew("01", "系统异常,请稍后再试");
        }

        return genRespBody(result, null);
    }

    /**
     * 添加组织 在用户账号 注册成功后
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/registerOrgan.json", method = RequestMethod.POST)
    public Map<String, Object> registerOrgan(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("组织注册 ");
        long userId = 0;


        Map<String, Object> result = Maps.newHashMap();
        try {
            String requestJson = getJsonParamStr(request);

            OrganRegister org = new OrganRegister();
            org.setId(userId);
            //  org.setPassword(user.getPassword());
            //  org.setEmail(email);
            org.setStatus(Constants.OrganStatus.emailNoActive.v());
            org.setIsSwitch(0);
            result = organRegisterService.insertOrganRegister(org);
            // result.put("id", user.getId());


        } catch (Exception e) {
            logger.error("系统异常,请稍后再试", e);
            return returnFailMSGNew("01", "系统异常,请稍后再试");
        }

        return genRespBody(result, null);
    }

}
