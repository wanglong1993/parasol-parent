package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import com.ginkgocap.parasol.oauth2.web.jetty.LoginUserContextHolder;
import com.ginkgocap.parasol.organ.web.jetty.web.resource.ResourcePathExposer;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.Constants;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.Utils;
import com.ginkgocap.parasol.organ.web.jetty.web.vo.organ.OrganProfileVo;
import com.ginkgocap.parasol.user.model.UserBasic;
import com.ginkgocap.parasol.user.model.UserConfig;
import com.ginkgocap.parasol.user.model.UserFriendly;
//import com.ginkgocap.parasol.user.service.UserBlackListService;
import com.ginkgocap.parasol.user.service.UserConfigerService;
import com.ginkgocap.parasol.user.service.UserFriendlyService;
import com.ginkgocap.ywxt.organ.model.Customer;
import com.ginkgocap.ywxt.organ.model.SimpleCustomer;
import com.ginkgocap.ywxt.organ.model.template.Template;
import com.ginkgocap.ywxt.organ.service.*;
import com.ginkgocap.ywxt.organ.service.notice.CustomerNoticeService;
import com.ginkgocap.ywxt.organ.service.privilege.CustomerPermissionService;
import com.ginkgocap.ywxt.organ.service.profile.CustomerProfileService;
import com.ginkgocap.ywxt.organ.service.resource.CustomerResourceService;
import com.ginkgocap.ywxt.organ.service.tag.RCustomerTagService;
import com.ginkgocap.ywxt.organ.service.template.TemplateService;
import com.ginkgocap.ywxt.util.DateFunc;
import com.ginkgocap.ywxt.util.JsonUtil;
import com.gintong.ywxt.organization.model.OrganRegister;
import com.gintong.ywxt.organization.service.OrganRegisterService;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jbqiu on 2016/6/10.
 * controller 组织controller
 */
@Controller
@RequestMapping("/organ")
public class OrganController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private CustomerService customerService;
	@Resource
	private CustomerProfileService customerProfileService;
	@Autowired
	SimpleCustomerService simpleCustomerService;
	@Resource
	private ResourcePathExposer rpe;
	@Resource
	private CustomerGroupService customerGroupService;
	@Resource
	private RCustomerTagService rCustomerTagService;
	@Resource
	private CustomerPermissionService customerPermissionService;

	@Autowired
	// 用户黑名单
	//private UserBlackListService userBlackService;
	@Resource
	private UserConfigerService userConfigService;
	@Autowired
	private UserFriendlyService userFriendlyService;
	@Resource
	private CustomerNoticeService customerNoticeService;
	@Resource
	private CustomerCollectService customerCollectService;
	@Resource
	private CustomerCountService customerCountService;
	@Resource
	private CustomerResourceService customerResourceService;

	@Autowired
	public OrganRegisterService organRegisterService;

	@Autowired
	TemplateService templateService;
	
	private static final String CLASS_NAME = OrganController.class.getName();





	
	/**
	 * 添加组织详情(新的web端调用)
	 * 
	 * @author cazhigang
	 */
	@ResponseBody
	@RequestMapping(value = "/organ/saveOrganProfile", method = RequestMethod.POST)
	public Map<String, Object> saveOrganPro(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String requestJson = "";
		requestJson = getJsonParamStr(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();

        UserBasic userBasic=null;
		if (requestJson != null && !"".equals(requestJson)) {
			try {
				JSONObject jo = JSONObject.fromObject(requestJson);
				userBasic=getUser(request);


				OrganRegister organRegister=organRegisterService.getOrganRegisterById(userBasic.getUserId());
				ObjectMapper objectMapper = new ObjectMapper();
				Customer customer = objectMapper.readValue(jo.toString(), Customer.class);

				if ("1".equals(customer.getIsListing()) && isNullOrEmpty(customer.getStockNum())) {
					setSessionAndErr(request, response, "-1", "上市公司必须填写证券号码！");
					return returnFailMSGNew("01", "上市公司必须填写证券号码！");
				}

				
				customer.setPicLogo("".equals(StringUtils.trimToEmpty(customer.getPicLogo())) ? Constants.ORGAN_DEFAULT_PIC_PATH :
                        Utils.alterImageUrl(customer.getPicLogo()));
				customer.setCreateById(userBasic.getUserId());
				customer.setUserId(userBasic.getUserId());
				customer.setVirtual("1");
				customer.setUtime(DateFunc.getDate());
				// 数据类型  组织数据
				customerService.saveOrUpdateOrgData(customer);


				updateOrganRegister(customer, customer.getCreateById());



		
				responseDataMap.put("success", true);
				responseDataMap.put("msg", "操作成功");
				notificationMap.put("notifCode", "0001");
				notificationMap.put("notifInfo", "hello mobile app!");
			} catch (Exception e) {
				setSessionAndErr(request, response, "-1", "系统异常,请稍后再试");
				logger.error("保存组织详情报错,请求参数:{}" + requestJson, e);
				return returnFailMSGNew("01", "系统异常,请稍后再试");
			}
		} else {
			setSessionAndErr(request, response, "-1", "非法操作！");
			return returnFailMSGNew("01", "非法操作！");
		}
		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);

		return model;
	}
	
	
	
	
	

	public void updateOrganRegister(Customer customer, long organId) {
		OrganRegister organ = organRegisterService.getOrganRegisterById(customer.getCreateById());
		if (organ != null) {
			organ.setOrganName(customer.getName());
			organ.setOrgType(customer.getOrgType());
			organ.setIslisted("1".equals(customer.getIsListing()) ? true : false);
			organ.setAreaid(customer.getAreaid());
			organ.setArea(customer.getAreaString());
			organ.setOrganLogo(customer.getPicLogo());
			organ.setIndustry(customer.getIndustry());
			organ.setIndustryid(Long.valueOf(customer.getIndustryId()));
			organ.setOrganAllName(customer.getOrganAllName());
			organRegisterService.updateOrganData(organ);
		}

	}

	/**
	 * 获取组织的二维码内容(组织详情页的二维码)
	 * http://local.gintong.com:5000/#/organization_detail?
	 * orgId=260301&d=3&customerId=260301 这是web自己拼接的地址
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 *             liubang
	 */
	@ResponseBody
	@RequestMapping(value = "/organ/qr", method = RequestMethod.GET)
	public ModelAndView getUserQRUrl(@RequestParam(defaultValue = "") String customerId, HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) throws Exception {
		if (StringUtils.isNotBlank(customerId)) {
			long userId = Long.parseLong(customerId);
			SimpleCustomer sc = simpleCustomerService.findByCustomerId(userId);
			if (sc != null) {
				model.put("customerId", customerId);
				model.put("type", sc.getVirtual());
			} else {
				model.put("customerId", "");
				model.put("type", "");
			}
		} else {
			model.put("customerId", "");
			model.put("type", "");
		}
		return new ModelAndView("/invitation", model);
	}

	
	/**
	 * @author caizhigang
	 * 组织详情查询（新组织详情web端调用）
	 * 
	 * @param customerId
	 *            组织用户Id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/organ/getOrganProfile", method = RequestMethod.POST)
	public Map<String, Object> getOrganProfileNew(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestJson = Utils.getJsonIn(request);
		Map<String, Object> responseData = new HashMap<String, Object>();
		JSONObject j = JSONObject.fromObject(requestJson);

        //登录用户id
        Long loginUserId = LoginUserContextHolder.getUserId();
        //组织id 同时是用户id
		long organId = JsonUtil.getNodeToLong(j, "organId");


		Customer customer_temp = customerService.findOrganCurrentData(organId);// 组织详情基本资料
		if (customer_temp != null) {
            OrganProfileVo organProfileVo = new OrganProfileVo();
			// 查看组织对应的用户个人设置 看其主页是否允许查看
			UserConfig uc = userConfigService.getUserConfig(organId);

			if (uc != null) {
				if (uc.getHomePageVisible() == null)
					uc.setHomePageVisible(new Byte("2"));
                organProfileVo.setUserConfig(uc.getHomePageVisible());
			}
            //看是否是对方的黑名单
			/*boolean isblack = userBlackService.isBlackUser(loginUserId, customer_temp.getUserId(),0l);
            organProfileVo.setBlack(isblack);// 黑名单关系
			if (isblack) {
				return returnFailMSGNew("01", "请查看其它组织");
			}*/
            organProfileVo.setLoginUserId(loginUserId);

            //组织可以收藏吗？？？
			// 新增是否收藏
		    //customer_new.setIsCollect(customerCollectService.findByUserIdAndCustomerId(loginUserId, customer_temp.getId()) != null ? "1" : "0");
			Customer temps = customerService.getCustomerByComeidAndCreateId(customer_temp.getId(), loginUserId);
			if (temps != null) {
                organProfileVo.setComeId(temps.getId());
			}
			organProfileVo.setFriends(isFriend(loginUserId, customer_temp.getUserId()));
			//cusotmerCommonService.findCustomerAuth(view, organProfileVo, customer_temp, user);

			String sckNum = customer_temp.getStockNum();// 证券号码
//			organProfileVo.setCustomerId(customer_temp.getId());
            organProfileVo.setId(customer_temp.getId());
            organProfileVo.setName(customer_temp.getName());
            organProfileVo.setIndustry(customer_temp.getIndustry());
            organProfileVo.setIndustryId(customer_temp.getIndustryId());
            organProfileVo.setPropertyList(customer_temp.getPropertyList());
            organProfileVo.setUserId(customer_temp.getUserId());
            organProfileVo.setIsListing(customer_temp.getIsListing());
            organProfileVo.setLinkMobile(customer_temp.getLinkMobile());
            organProfileVo.setCreateType(new String("1"));
            organProfileVo.setStockNum(sckNum); // 改过的
            organProfileVo.setDiscribe(customer_temp.getDiscribe());
            organProfileVo.setOrganNumber(customer_temp.getOrganNumber());
			

			organProfileVo.setPersonalPlateList(customer_temp.getPersonalPlateList());
			organProfileVo.setVirtual(customer_temp.getVirtual());
			organProfileVo.setCreateById(customer_temp.getCreateById());
			organProfileVo.setPicLogo(rpe.getNginxRoot() + Utils.alterImageUrl(customer_temp.getPicLogo()));


			organProfileVo.setOrgType(customer_temp.getOrgType());
			organProfileVo.setOrganAllName(customer_temp.getOrganAllName());
			organProfileVo.setAreaString(customer_temp.getAreaString());
			organProfileVo.setAreaid(customer_temp.getAreaid());
			organProfileVo.setAddress(customer_temp.getAddress());
			organProfileVo.setStatus(customer_temp.getStatus());
			organProfileVo.setLinkEmail(customer_temp.getLinkEmail());
			organProfileVo.setLinkManName(customer_temp.getLinkManName());

			responseData.put("customer", organProfileVo);
			responseData.put("bindUserId", customer_temp.getBindUserId());
			responseData.put("id", organProfileVo.getId());
			responseData.put("organNumber", customer_temp.getOrganNumber());
			try {
				customerCountService.updateCustomerCount(com.ginkgocap.ywxt.organ.model.Constants.customerCountType.read.getType(), organId);
			} catch (Exception e) {
				logger.error("插入组织数据统计功能报错,请求参数json: ", e);
			}

		} else {
			setSessionAndErr(request, response, "-1", "查询组织为空");
			return returnFailMSGNew("01", "查询组织为空");
		}

		return returnSuccessMSG(responseData);
	}
	
	
	
	
	
	
	/**
	 * 返回组织详情和模板 数据    切换模板时使用
	 * @author caizhigang
	 * @param customerId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/organ/getOrganProfileAndTemplate.json", method = RequestMethod.POST)
	public Map<String, Object> getOrganProfileAndTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestJson = Utils.getJsonIn(request);
		Map<String, Object> responseData = new HashMap<String, Object>();
		JSONObject j = JSONObject.fromObject(requestJson);
        //登录用户id
        Long loginUserId = LoginUserContextHolder.getUserId();
        //组织id 同时是用户id
        long organId = JsonUtil.getNodeToLong(j, "organId");
		long templateId=JsonUtil.getNodeToLong(j, "templateId");
		OrganRegister organ = organRegisterService.getOrganRegisterById(organId);
//		User organ=userService.selectByPrimaryKey(organId);
		long orgId = organ.getId();
		Customer customer_temp = customerService.findOrganDataInTemplate(organId, templateId);// 组织详情基本资料
		if (customer_temp != null) {
            OrganProfileVo organProfileVo = new OrganProfileVo();
            // 查看组织对应的用户个人设置 看其主页是否允许查看
            UserConfig uc = userConfigService.getUserConfig(organId);
            if (uc != null) {
                if (uc.getHomePageVisible() == null)
                    uc.setHomePageVisible(new Byte("2"));
                organProfileVo.setUserConfig(uc.getHomePageVisible());
            }
            //看是否是对方的黑名单
          /*  boolean isblack = userBlackService.isBlackUser(loginUserId, customer_temp.getUserId(), 0l);
            organProfileVo.setBlack(isblack);// 黑名单关系
            if (isblack) {
                return returnFailMSGNew("01", "请查看其它组织");
            }*/
                organProfileVo.setLoginUserId(loginUserId);
                //组织可以收藏吗？？？
                // 新增是否收藏
                //customer_new.setIsCollect(customerCollectService.findByUserIdAndCustomerId(loginUserId, customer_temp.getId()) != null ? "1" : "0");
                Customer temps = customerService.getCustomerByComeidAndCreateId(customer_temp.getId(), loginUserId);
                if (temps != null) {
                    organProfileVo.setComeId(temps.getId());
                }
                organProfileVo.setFriends(isFriend(loginUserId, customer_temp.getUserId()));

                String sckNum = customer_temp.getStockNum();// 证券号码
//			organProfileVo.setCustomerId(customer_temp.getId());
                organProfileVo.setId(customer_temp.getId());
                organProfileVo.setName(customer_temp.getName());
                organProfileVo.setIndustry(customer_temp.getIndustry());
                organProfileVo.setIndustryId(customer_temp.getIndustryId());
                organProfileVo.setPropertyList(customer_temp.getPropertyList());
                organProfileVo.setUserId(customer_temp.getUserId());
                organProfileVo.setIsListing(customer_temp.getIsListing());
                organProfileVo.setLinkMobile(customer_temp.getLinkMobile());
                organProfileVo.setCreateType("1");
                organProfileVo.setStockNum(sckNum); // 改过的
                organProfileVo.setDiscribe(customer_temp.getDiscribe());
                organProfileVo.setOrganNumber(customer_temp.getOrganNumber());


                organProfileVo.setPersonalPlateList(customer_temp.getPersonalPlateList());
                organProfileVo.setVirtual(customer_temp.getVirtual());
                organProfileVo.setCreateById(customer_temp.getCreateById());
                organProfileVo.setPicLogo(rpe.getNginxRoot() + Utils.alterImageUrl(customer_temp.getPicLogo()));


                organProfileVo.setOrgType(customer_temp.getOrgType());
                organProfileVo.setOrganAllName(customer_temp.getOrganAllName());
                organProfileVo.setAreaString(customer_temp.getAreaString());
                organProfileVo.setAreaid(customer_temp.getAreaid());
                organProfileVo.setAddress(customer_temp.getAddress());
                organProfileVo.setStatus(customer_temp.getStatus());
                organProfileVo.setLinkEmail(customer_temp.getLinkEmail());
                organProfileVo.setLinkManName(customer_temp.getLinkManName());

                responseData.put("customer", organProfileVo);
                responseData.put("bindUserId", customer_temp.getBindUserId());
                responseData.put("id", organProfileVo.getId());
                responseData.put("organNumber", customer_temp.getOrganNumber());
                try {
                    customerCountService.updateCustomerCount(com.ginkgocap.ywxt.organ.model.Constants.customerCountType.read.getType(), orgId);
                } catch (Exception e) {
                    logger.error("插入组织数据统计功能报错,请求参数json: ", e);
                }


            }

		
		Template template=templateService.findTemplateById(templateId);
		responseData.put("template", template);
		
		return returnSuccessMSG(responseData);
	}
	
	
	
	
	
	
	
	
	public String isFriend(long userId, long toUserId){
		/*
		 * String isFriend="0"; //如果不是好友的话，查看是否已经申请加好友等待同意 boolean isFriends =
		 * friendsRelationService.isExistFriends(userId, toUserId);
		 * if(isFriends){ isFriend ="2"; }else{ //查询非好友的情况 1 等待同意 2 是好友 3不是好友
		 * RUser r =
		 * friendsRelationService.findByUserIdAndFriendIdAndStatus(userId,
		 * toUserId); isFriend=(r==null)?"3":"1";//如果没有查到，说明不是好友.如果查到，说明好友添加等待中
		 * }
		 */
        // 用户好友支持 默认为3
        int status=3;
        UserFriendly userFriendly=null;

        try {
            userFriendly = userFriendlyService.getFriendly(userId, toUserId);
            if(userFriendly!=null){
                //和vo定义的值匹配
                status=userFriendly.getStatus()+1;

            }

        }catch (Exception e){
        e.printStackTrace();
        }
		return String.valueOf(status);
	}



}