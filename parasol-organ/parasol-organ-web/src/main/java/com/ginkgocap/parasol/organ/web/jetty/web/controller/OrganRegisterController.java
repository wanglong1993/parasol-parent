package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ginkgocap.parasol.organ.web.jetty.web.utils.EncodeUtil;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.cache.CacheManager;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.cache.CacheType;

import jersey.repackaged.com.google.common.collect.Maps;
import net.sf.json.JSONArray;
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

import com.alibaba.fastjson.JSON;
import com.ginkgocap.ywxt.cache.Cache;
import com.ginkgocap.ywxt.cache.CacheModule;
import com.ginkgocap.ywxt.model.MobileCode;
import com.ginkgocap.ywxt.organ.model.Customer;
import com.ginkgocap.ywxt.organ.service.CustomerService;
import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.user.service.UserService;
import com.gintong.ywxt.organization.model.OrganRegister;
import com.gintong.ywxt.organization.service.OrganRegisterService;
import com.gintong.ywxt.organization.service.OrganRelationService;
import com.gintong.ywxt.organization.util.Constants;

@Controller
@RequestMapping("/organ")
public class OrganRegisterController extends BaseController {

    public static final Logger logger = LoggerFactory.getLogger(OrganRegisterController.class);
    @Autowired
    public OrganRegisterService organRegisterService;
    @Resource
    private CacheManager cacheManager;
    @Resource
    private UserService userService;
    @Resource
    private Cache cache;
    @Resource
    private CustomerService customerService;
    @Resource
    private OrganRelationService organRelationService;

    private static final int hashIterations = 5000;

    /**
     * 组织 注册第一步 用户名/密码
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/registerOne.json", method = RequestMethod.POST)
    public Map<String, Object> insertUserLog(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("组织 注册第一步 用户名/密码 ");
        logger.info("进入方法");
        
        Map<String, Object> result = Maps.newHashMap();
        try {
            String requestJson = getJsonParamStr(request);
            if (StringUtils.isNotBlank(requestJson)) {
                JSONObject j = JSONObject.fromObject(requestJson);
                String email = j.optString("email");
                String orgpwd = j.optString("password");
                String code = j.getString("code");
                if (!email.contains("@")) {
                    result.put("result", "邮箱地址格式不正确，请重新输入!");
                    return genRespBody(result, null);
                }
                if (StringUtils.isNotBlank(code)) { 
                	logger.info("插入用户前-----------2222222222");
                	
                    // 校验验证码
                    HttpSession hs = request.getSession();
                    String cacheIdentifyCode = cacheManager.get(CacheType.IDENTIFY_CODE, hs.getId());
                    logger.info("cacheIdentifyCode:"+cacheIdentifyCode);
                    if (!code.equals(cacheIdentifyCode)) {
                        result.put("result", "验证码不正确!");
                        return genRespBody(result, null);
                    }
                } else {
                    result.put("result", "验证码不为空!");
                    return genRespBody(result, null);
                }
                logger.info("插入用户前-----------333333333333");
                User user = insertUserOne(email, orgpwd);
                logger.info("插入用户后-------------4444444444");
                if (user != null) {
                    OrganRegister org = new OrganRegister();
                    org.setId(user.getId());
                    org.setPassword(user.getPassword());
                    org.setEmail(email);
                    org.setStatus(Constants.OrganStatus.emailNoActive.v());
                    org.setIsSwitch(0);
                    result = organRegisterService.insertOrganRegister(org);
                    result.put("id", user.getId());
                    // 发送邮箱验证
                    sendRegValidateEmail(user.getId(), email, 0, user.getId(), request, response);
                }
            }
        } catch (Exception e) {
            logger.error("系统异常,请稍后再试", e);
            return returnFailMSGNew("01", "系统异常,请稍后再试");
        }

        return genRespBody(result, null);
    }

    /**
     * 组织 注册第二步 去邮箱认证
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/regValidateEmail.json", method = RequestMethod.POST)
    public Map<String, Object> regValidateEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info(" 去邮箱认证");
        Map<String, Object> result = Maps.newHashMap();
        try {
            String requestJson = getJsonParamStr(request);
            if (StringUtils.isNotBlank(requestJson)) {
                JSONObject j = JSONObject.fromObject(requestJson);
                String email = j.optString("email");
                Long id = j.optLong("id");
                // 发送邮箱验证
                sendRegValidateEmail(null, email, 1, id, request, response);
                result.put("id", id);
            }

        } catch (Exception e) {
            logger.error("系统异常,请稍后再试", e);
            return returnFailMSGNew("01", "系统异常,请稍后再试");
        }

        return returnSuccessMSG(result);
    }

    /**
     * 组织 注册第二步 激活邮箱
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/registerSecond.json", method = RequestMethod.POST)
    public Map<String, Object> registerSecond(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("注册组织第二步 激活邮箱");
        Map<String, Object> result = Maps.newHashMap();
        try {
            String requestJson = getJsonParamStr(request);
            if (StringUtils.isNotBlank(requestJson)) {
                JSONObject j = JSONObject.fromObject(requestJson);
                long id = j.optLong("id");
                OrganRegister org = organRegisterService.getOrganRegisterById(id);
                if (org == null) {
                    result.put("result", "组织号不存在！");
                    return genRespBody(result, null);
                }
                if (org.getStatus() == Constants.OrganStatus.emailActivate.v()) {
                    setSessionAndErr(request, response, "-1", "链接失效！");
                    return returnFailMSGNew("01", "链接失效！");
                }
                updateUserStatus(id, Constants.OrganStatus.emailActivate.v());
                result = organRegisterService.updateOrganRegisterStatus(id, Constants.OrganStatus.emailActivate.v());
                result.put("id", id);
            }
        } catch (Exception e) {
            logger.error("系统异常,请稍后再试", e);
            return returnFailMSGNew("01", "系统异常,请稍后再试");
        }

        return genRespBody(result, null);
    }

    /**
     * 组织 注册第三步 添加登记信息
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/registerThird.json", method = RequestMethod.POST)
    public Map<String, Object> registerThird(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("注册组织第三步 添加登记信息");
        Map<String, Object> result = Maps.newHashMap();
        try {
            String requestJson = getJsonParamStr(request);
            if (StringUtils.isNotBlank(requestJson)) {
                JSONObject j = JSONObject.fromObject(requestJson);
                OrganRegister org = JSON.parseObject(requestJson, OrganRegister.class);
                // 验证手机验证码
//                String code = j.getString("code");
//                String key = cache.getCacheHelper().buildKey(CacheModule.COMMON, org.getContanctMobile());
//                MobileCode value = (MobileCode) cache.get(key);
//                if (value == null) {
//                    setSessionAndErr(request, response, "-1", "验证码已过期,请重新发送！");
//                    return returnFailMSGNew("01", "验证码已过期,请重新发送！");
//                } else {
//                    if (!value.getCode().equals(code)) {
//                        setSessionAndErr(request, response, "-1", "验证码不正确！");
//                        return returnFailMSGNew("01", "验证码不正确！");
//                    }
//                }

                updateUserValue(org);
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
     * 组织 注册第四步 绑定用户账号
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/registerFourth.json", method = RequestMethod.POST)
    public Map<String, Object> registerFourth(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("注册组织第四步 绑定用户账号");
        Map<String, Object> result = Maps.newHashMap();
        try {
            String requestJson = getJsonParamStr(request);
            if (StringUtils.isNotBlank(requestJson)) {
                JSONObject j = JSONObject.fromObject(requestJson);
                long id = j.optLong("id");
                String username = j.optString("username");
                String orgpwd = j.optString("password");
                // 用户合法性校验
                User user = getUserByAccount(username);
                if (user == null) {
                    logger.info("您输入的手机号/邮箱或密码有误");
                    result.put("result", "您输入的手机号/邮箱或密码有误");
                    return genRespBody(result, null);
                }
                if (user.isVirtual()) {
                    result.put("result", "绑定失败!");
                    return genRespBody(result, null);
                }
                boolean flag = organRegisterService.isUserBind(user.getId(), id);
                if (flag) {
                    result.put("result", "该用户已绑定此组织，请重新输入！");
                    return genRespBody(result, null);
                }
                String currPwd = EncodeUtil.encryptBySalt(orgpwd, user.getSalt());
                if (!currPwd.equals(user.getPassword())) {
                    logger.info("您输入的手机号/邮箱或密码有误");
                    result.put("result", "您输入的手机号/邮箱或密码有误");
                    return genRespBody(result, null);
                }
                long mongoId = updateCustomerValue(id, user.getId());
                bingUser(id, user.getId(), mongoId);
                addManager(id, user.getId(), username);
                result = organRegisterService.updateOrganUser(id, user.getId());
                result.put("id", id);
            }
        } catch (Exception e) {
            logger.error("系统异常,请稍后再试", e);
            return returnFailMSGNew("01", "系统异常,请稍后再试");
        }
        return genRespBody(result, null);
    }

    // 保存管理者账号
    public void addManager(long id, long userId, String username) {
        Map<String, Object> params = Maps.newHashMap();
        params.put("organId", id);
        params.put("userId", userId);
        params.put("userAccount", username);
        organRelationService.addManager(params);
    }

    /**
     * 组织 注册第四步 绑定用户账号 扫码
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/registerFourthAPP.json", method = RequestMethod.POST)
    public Map<String, Object> registerFourthAPP(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("注册组织第四步");
        Map<String, Object> result = Maps.newHashMap();
        try {
            String requestJson = getJsonParamStr(request);
            if (StringUtils.isNotBlank(requestJson)) {
                JSONObject j = JSONObject.fromObject(requestJson);
                long id = j.optLong("id");
                long userId = j.optLong("userid");
                User user = selectByPrimaryKey(userId);
                if (user == null) {
                    logger.info("用户不存在");
                    result.put("result", "用户不存在");
                    return genRespBody(result, null);
                }
                long monogId = updateCustomerValue(id, user.getId());
                bingUser(id, userId, monogId);
                result = organRegisterService.updateOrganUser(id, userId);

                result.put("id", id);
            }
        } catch (Exception e) {
            logger.error("系统异常,请稍后再试", e);
            return returnFailMSGNew("01", "系统异常,请稍后再试");
        }
        return genRespBody(result, null);
    }

    /**
     * 检测账号邮箱是否被注册
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/isExistEmail.json", method = RequestMethod.POST)
    public Map<String, Object> isExistEmail(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> result = Maps.newHashMap();
        try {
            String requestJson = getJsonParamStr(request);
            if (StringUtils.isNotBlank(requestJson)) {
                JSONObject j = JSONObject.fromObject(requestJson);
                String email = j.optString("email");
                boolean isEmailExist = userService.isExistByEmail(email);
               
                logger.info("isEmailExist:"+isEmailExist);
                if (isEmailExist) {

                    OrganRegister org = organRegisterService.isStatus(email);
                    
                    if (org != null) {
                        result.put("result", isEmailExist);
                        result.put("organid", org.getId());
                        result.put("status", org.getStatus());
                    }
                    return  genRespBody(result, null);
                }
            }
        } catch (Exception e) {
            logger.error("系统异常,请稍后再试", e);
            return returnFailMSGNew("01", "系统异常,请稍后再试");
        }
        return returnSuccessMSG(result);
    }

    /**
     * 检测组织号是否重重
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/isExistOrganNumber.json", method = RequestMethod.POST)
    public Map<String, Object> isExistOrganNumber(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> result = Maps.newHashMap();
        try {
            String requestJson = getJsonParamStr(request);
            if (StringUtils.isNotBlank(requestJson)) {
                JSONObject j = JSONObject.fromObject(requestJson);
                String organNumber = j.optString("organNumber");
                result = organRegisterService.isExistOrganNumber(organNumber);
            }
        } catch (Exception e) {
            logger.error("系统异常,请稍后再试", e);
            return returnFailMSGNew("01", "系统异常,请稍后再试");
        }
        return genRespBody(result, null);
    }

    /**
     * 11 检测组织全称是否重复
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/isExistOrganAllName.json", method = RequestMethod.POST)
    public Map<String, Object> isExistOrganAllName(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> result = Maps.newHashMap();
        try {
            String requestJson = getJsonParamStr(request);
            if (StringUtils.isNotBlank(requestJson)) {
                JSONObject j = JSONObject.fromObject(requestJson);
                String organAllName = j.optString("organAllName");
                result = organRegisterService.isExistName(organAllName);
            }
        } catch (Exception e) {
            logger.error("系统异常,请稍后再试", e);
            return returnFailMSGNew("01", "系统异常,请稍后再试");
        }
        return genRespBody(result, null);
    }

    /**
     * 编辑组织资料
     *
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/updateOrganData.json", method = RequestMethod.POST)
    public Map<String, Object> updateOrganData(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<String, Object> result = Maps.newHashMap();
        try {
            String requestJson = getJsonParamStr(request);
            if (StringUtils.isNotBlank(requestJson)) {
                OrganRegister register = JSON.parseObject(requestJson, OrganRegister.class);
                result = organRegisterService.updateOrganData(register);
            }
        } catch (Exception e) {
            logger.error("系统异常,请稍后再试", e);
            return returnFailMSGNew("01", "系统异常,请稍后再试");
        }
        return genRespBody(result, null);
    }

    private User getUserByAccount(String username) {
        User user = null;
        if (username.indexOf("@") > 0) {
            user = userService.selectByEmail(username);
        } else {
            List<User> listUser = userService.selectByMobile(username);
            if (listUser != null && listUser.size() > 0) {
                user = listUser.get(0);
            } else {
                user = null;
            }
            if (user == null) {
                user = userService.selectByUsername(username);
            }
        }
        return user;
    }

    /**
     * 修改用户表中组织状态
     *
     * @param userId
     */
    private Map<String, Object> updateUserStatus(long id, int status) {
        Map<String, Object> result = Maps.newHashMap();
        User user = selectByPrimaryKey(id);
        if (user == null) {
            result.put("result", "error");
            return result;
        }
        user.setOrganStatus(status);
        userService.updateUser(user);
        result.put("result", "success");
        return result;
    }

    /**
     * 修改用户信息
     *
     * @param org
     * @return
     */
    public Map<String, Object> updateUserValue(OrganRegister org) {
        Map<String, Object> result = Maps.newHashMap();
        User user = selectByPrimaryKey(org.getId());
        if (user == null) {
            result.put("result", "error");
            return result;
        }
        user.setOrganStatus(Constants.OrganStatus.informationRegistered.v());
        user.setName(org.getOrganName());
        user.setOrganType(org.getOrgType());
        user.setIsListing(org.getIslisted() ? "1" : "0");
        user.setStkcd(org.getStockNumber());
        user.setIndustry(org.getIndustry());
        user.setShortName(org.getOrganName());
        user.setPicPath(org.getOrganLogo());
        user.setOrganNumber(org.getOrganNumber());
        userService.updateUser(user);
        result.put("result", "success");
        return result;

    }

    /**
     * 绑定用户
     *
     * @param org
     * @return
     */
    public Map<String, Object> bingUser(long id, long userid, long monogId) {
        Map<String, Object> result = Maps.newHashMap();
        User user = selectByPrimaryKey(id);
        if (user == null) {
            result.put("result", "error");
            return result;
        }
        user.setOrganStatus(Constants.OrganStatus.binding.v());
        user.setUid(userid);
        user.setPeopleId(String.valueOf(monogId));
        userService.updateUser(user);
        result.put("result", "success");
        return result;

    }

    /**
     * 将第一步注册信息添加到用户表中
     *
     * @param email
     * @param password
     * @return
     */
    public User insertUserOne(String email, String orgpwd) {
        RandomNumberGenerator saltGenerator = new SecureRandomNumberGenerator();
        String salt = saltGenerator.nextBytes().toHex();
        String newPass = new Sha256Hash(orgpwd, salt, hashIterations).toHex();
        User user = new User();
        user.setEmail(email);
        user.setPassword(newPass);
        user.setSalt(salt);
        user.setVirtual(true);
        user.setType(2);
        user.setOrganStatus(Constants.OrganStatus.emailNoActive.v());
        User useresult = userService.insertUser(user);
        return useresult;
    }

    /**
     * 修改组织customer
     *
     * @param org
     * @return
     */
    public long updateCustomerValue(long orgId, long userId) {
        OrganRegister org = organRegisterService.getOrganRegisterById(orgId);
        Customer customer = customerService.findOne(org.getMongoId());
        if (customer == null)
            customer = new Customer();
        customer.setOrgType(org.getOrgType());
        customer.setOrganAllName(org.getOrganAllName());
        customer.setIndustryId(org.getIndustryid());
        customer.setIndustry(org.getIndustry());
        customer.setAreaString(org.getArea());
        customer.setAreaid(org.getAreaid());
        customer.setIsListing(org.getIslisted() ? "1" : "0");
        customer.setStockNum(org.getStockNumber());
        customer.setLinkManName(org.getContactName());
        customer.setLinkEmail(org.getContanctEmail());
        customer.setLinkMobile(org.getContanctMobile());
        customer.setOrganNumber(org.getOrganNumber());
        customer.setName(org.getOrganName());
        customer.setPicLogo(org.getOrganLogo());
        customer.setStatus(org.getStatus());
        customer.setBindUserId(userId);
        customer.setCreateById(org.getId());
        customer.setVirtual("1");
        customer.setUserId(org.getId());
        customer.setEmail(org.getEmail());
        customer.setCurrent(true);

        int templateType=  org.getTemplateType();
        
        if(templateType!=0){
        	
        	if(templateType==1){// 学校 模板
        		customer.setTemplateId(3);
        	}else if(templateType==2){
        		
        		customer.setTemplateId(4);
        	}else if(templateType==3){// 其它用 企业
        		
        		customer.setTemplateId(1);
        	}
        	
        	
        	
        }else{
        	 if(customer.getOrgType()==1||customer.getOrgType()==4){
              	customer.setTemplateId(1);
              }else if(customer.getOrgType()==2){
              	customer.setTemplateId(2);
              }else if(customer.getOrgType()==3){
              	customer.setTemplateId(5);
              }
        	
        }
       
        
        customer.setMoudles(OrganUtils.createMoudles(customer));
        Customer cus = customerService.saveOrUpdate(customer);
        org.setMongoId(cus.getId());
        organRegisterService.updateOrganData(org);

        return cus.getId();

    }
    
    

    
    
    
    
}
