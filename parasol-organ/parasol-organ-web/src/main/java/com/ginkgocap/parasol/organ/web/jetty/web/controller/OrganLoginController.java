package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import com.alibaba.fastjson.JSON;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.Utils;
import com.ginkgocap.ywxt.organ.service.CustomerService;
import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.user.service.UserService;
import com.ginkgocap.ywxt.util.DateFunc;
import com.ginkgocap.ywxt.util.Encodes;
import com.gintong.ywxt.organization.model.OrganRegister;
import com.gintong.ywxt.organization.service.OrganRegisterService;
import com.gintong.ywxt.organization.util.Constants;
import jersey.repackaged.com.google.common.collect.Maps;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

    @Autowired
    private UserService userService;

    /**
     * 完善组织资料
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/organ/completeInfo", method = RequestMethod.POST)
    public Map<String, Object> completeInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("完善组织信息");


        Map<String, Object> result = Maps.newHashMap();
        try {


            String requestJson = getJsonParamStr(request);
            if (StringUtils.isNotBlank(requestJson)) {
                JSONObject j = JSONObject.fromObject(requestJson);
                OrganRegister org = JSON.parseObject(requestJson, OrganRegister.class);
                //增加


                org.setStatus(Constants.OrganStatus.emailNoActive.v());
                org.setIsSwitch(0);
                //organRegisterService.insertOrganRegister(org);
                //更新
                result = organRegisterService.saveOrUpdateOrganRegister(org);
                result.put("id", org.getId());
            }
        } catch (Exception e) {
            logger.error("系统异常,请稍后再试", e);
            return returnFailMSGNew("01", "系统异常,请稍后再试");
        }

        return genRespBody(result, null);
    }


}
