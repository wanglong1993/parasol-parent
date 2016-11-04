package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
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

import com.alibaba.fastjson.JSON;
import com.ginkgocap.parasol.organ.web.jetty.web.resource.ResourcePathExposer;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.CommonUtil;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.Constants;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.Utils;
import com.ginkgocap.parasol.organ.web.jetty.web.vo.organ.OrganProfileVo;
import com.ginkgocap.parasol.organ.web.jetty.web.vo.organ.TemplateVo;
import com.ginkgocap.ywxt.organ.model.Area;
import com.ginkgocap.ywxt.organ.model.Customer;
import com.ginkgocap.ywxt.organ.model.SimpleCustomer;
import com.ginkgocap.ywxt.organ.model.template.Template;
import com.ginkgocap.ywxt.organ.service.CustomerCollectService;
import com.ginkgocap.ywxt.organ.service.CustomerCountService;
import com.ginkgocap.ywxt.organ.service.CustomerGroupService;
import com.ginkgocap.ywxt.organ.service.CustomerService;
import com.ginkgocap.ywxt.organ.service.SimpleCustomerService;
import com.ginkgocap.ywxt.organ.service.notice.CustomerNoticeService;
import com.ginkgocap.ywxt.organ.service.privilege.CustomerPermissionService;
import com.ginkgocap.ywxt.organ.service.profile.CustomerProfileService;
import com.ginkgocap.ywxt.organ.service.resource.CustomerResourceService;
import com.ginkgocap.ywxt.organ.service.tag.RCustomerTagService;
import com.ginkgocap.ywxt.organ.service.template.TemplateService;
import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.user.model.UserConfig;
import com.ginkgocap.ywxt.user.service.FriendsRelationService;
import com.ginkgocap.ywxt.user.service.UserBlackService;
import com.ginkgocap.ywxt.user.service.UserConfigService;
import com.ginkgocap.ywxt.user.service.UserService;
import com.ginkgocap.ywxt.util.DateFunc;
import com.ginkgocap.ywxt.util.JsonUtil;
import com.gintong.ywxt.organization.model.OrganRegister;
import com.gintong.ywxt.organization.service.OrganRegisterService;

/**
 * Created by jbqiu on 2016/6/10. controller 组织controller
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
	private UserBlackService userBlackService;
	
	@Resource
	private UserService userService;

	@Resource
	private UserConfigService userConfigService;
	@Autowired
	private FriendsRelationService friendsRelationService;
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
	 * @author caizhigang
	 */
	@ResponseBody
	@RequestMapping(value = "/organ/saveOrganProfile.json", method = RequestMethod.POST)
	public Map<String, Object> saveOrganPro(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String requestJson = "";

		requestJson = getJsonParamStr(request);
		Map<String, Object> responseDataMap = new HashMap<String, Object>();

		User userBasic = null;
		if (requestJson != null && !"".equals(requestJson)) {
			try {
				JSONObject jo = JSONObject.fromObject(requestJson);
				userBasic = getUser(request);

				OrganRegister organRegister = organRegisterService
						.getOrganRegisterById(userBasic.getId());
				ObjectMapper objectMapper = new ObjectMapper();
				// Customer customer = objectMapper.readValue(jo.toString(),
				// Customer.class);

				Customer customer = JSON.parseObject(requestJson,
						Customer.class);
				customer.setBindUserId(organRegister.getUserId());
				
				Customer  oldCustomer=customerService.findOrganDataInTemplate(userBasic.getId(), customer.getTemplateId());
				if(oldCustomer!=null){
					customer.setId(oldCustomer.getId());
					System.out.println("old organ:"+oldCustomer.getId());
				}
				
				
				if(!OrganUtils.initCustomerOldField(customer)){
					
					return returnFailMSGNew("01", "保存失败,请检查传入参数");
				}
				
				if (isNullOrEmpty(customer.getName())) {
					setSessionAndErr(request, response, "-1", "组织简称必须填写");
					return returnFailMSGNew("01", "组织简称必须天填写");
				}

				System.out.println("organ templateId:"+customer.getTemplateId());
				
				
				if ("1".equals(customer.getIsListing())
						&& isNullOrEmpty(customer.getStockNum())) {
					setSessionAndErr(request, response, "-1", "上市公司必须填写证券号码！");
					return returnFailMSGNew("01", "上市公司必须填写证券号码！");
				}

				customer.setPicLogo("".equals(StringUtils.trimToEmpty(customer
						.getPicLogo())) ? Constants.ORGAN_DEFAULT_PIC_PATH
						: Utils.alterImageUrl(customer.getPicLogo()));
				customer.setCreateById(userBasic.getId());
				customer.setUserId(userBasic.getId());
				System.out.println("保存组织 userId:"+userBasic.getId());
				customer.setVirtual("1");
				customer.setUtime(DateFunc.getDate());
				// 数据类型 组织数据
				customerService.saveOrUpdateOrgData(customer);

				updateOrganRegister(customer);
				updateUser(customer);
				responseDataMap.put("success", true);
				responseDataMap.put("msg", "操作成功");

			} catch (Exception e) {
				setSessionAndErr(request, response, "-1", "系统异常,请稍后再试");
				logger.error("保存组织详情报错,请求参数:{}" + requestJson, e);
				return returnFailMSGNew("01", "系统异常,请稍后再试");
			}
		} else {
			setSessionAndErr(request, response, "-1", "非法操作！");
			return returnFailMSGNew("01", "非法操作！");
		}

		return responseDataMap;
	}

	public void updateOrganRegister(Customer customer) {
		OrganRegister organ = organRegisterService
				.getOrganRegisterById(customer.getCreateById());
		if (organ != null) {
			organ.setOrganName(customer.getName());
			organ.setOrgType(customer.getOrgType());
			organ.setIslisted("1".equals(customer.getIsListing()) ? true
					: false);
			organ.setAreaid(customer.getAreaid());
			organ.setArea(customer.getAreaString());
			organ.setOrganLogo(customer.getPicLogo());
			organ.setIndustry(customer.getIndustry());
			organ.setIndustryid(Long.valueOf(customer.getIndustryId()));
			organ.setOrganAllName(customer.getOrganAllName());
			organRegisterService.updateOrganData(organ);
		}

	}
	
	
	public void updateUser(Customer customer){
		
		User user=userService.selectByPrimaryKey(customer.getCreateById());
		if(user!=null){
			user.setName(customer.getName());
			user.setIndustry(customer.getIndustry());
			user.setPicPath(customer.getPicLogo());
			userService.updateUser(user);
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
	public ModelAndView getUserQRUrl(
			@RequestParam(defaultValue = "") String customerId,
			HttpServletRequest request, HttpServletResponse response,
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
	 * @author caizhigang 组织详情查询（新组织详情web端调用）
	 * 
	 * @param customerId
	 *            组织用户Id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/organ/getOrganProfile.json", method = RequestMethod.POST)
	public Map<String, Object> getOrganProfileNew(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String requestJson = Utils.getJsonIn(request);
		Map<String, Object> responseData = new HashMap<String, Object>();
		JSONObject j = JSONObject.fromObject(requestJson);

		User user = null;
		user = getUser(request);
		// 组织id 同时是用户id
		long organId = JsonUtil.getNodeToLong(j, "organId");

		Customer customer_temp = customerService.findOrganCurrentData(organId);// 组织详情基本资料

		
		  if(customer_temp==null){// 兼容关联老数据
			   customer_temp= customerService.findOne(organId);
			   if(!"1".equals(customer_temp.getVirtual())){
				   customer_temp=null;
			   }
		  }
		   
		   
       System.out.println("Is From web:"+CommonUtil.getRequestIsFromWebFlag());
		OrganRegister organRegister = organRegisterService
				.getOrganRegisterById(organId);
		if (customer_temp != null) {

			OrganProfileVo organProfileVo = createOrganProfileVo(customer_temp, user,organId);
			responseData.put("customer", organProfileVo);
			responseData.put("id", organProfileVo.getId());
			responseData.put("organNumber", organProfileVo.getOrganNumber());
			if(organRegister!=null){
				responseData.put("bindUserId", organRegister.getUserId());
			}else{
				responseData.put("bindUserId", 0);
			}
		

			try {
				customerCountService
						.updateCustomerCount(
								com.ginkgocap.ywxt.organ.model.Constants.customerCountType.read
										.getType(), organId);
			} catch (Exception e) {
				logger.error("插入组织数据统计功能报错,请求参数json: ", e);
			}

		} else {
			setSessionAndErr(request, response, "-1", "查询组织为空");
			return returnFailMSGNew("01", "查询组织为空");
		}

		return returnSuccessMSG(responseData);
	}
	
	
	
	
	public OrganProfileVo createOrganProfileVo(Customer customer_temp,User user,long organId) {
		

		if (customer_temp != null) {
			OrganProfileVo organProfileVo = new OrganProfileVo();
			
			
			
			boolean isblack = userBlackService.isBlackRelation(user.getId(), customer_temp.getUserId());
			organProfileVo.setBlack(isblack);// 黑名单关系
			// 查看组织对应的用户个人设置 看其主页是否允许查看
			UserConfig uc = userConfigService.getByUserId(organId);

			if (uc != null) {
				if (uc.getHomePageVisible() == null)
					uc.setHomePageVisible(2);
				organProfileVo.setUserConfig(uc.getHomePageVisible());
				organProfileVo.setWhetherShare(uc.getWhetherShare());
				organProfileVo.setWhetherDocking(uc.getWhetherDocking());
			}
			// 看是否是对方的黑名单
			/*
			 * boolean isblack = userBlackService.isBlackUser(loginUserId,
			 * customer_temp.getUserId(),0l);
			 * organProfileVo.setBlack(isblack);// 黑名单关系 if (isblack) { return
			 * returnFailMSGNew("01", "请查看其它组织"); }
			 */
			organProfileVo.setLoginUserId(user.getId());

			// 组织可以收藏吗？？？
			// 新增是否收藏
			// customer_new.setIsCollect(customerCollectService.findByUserIdAndCustomerId(loginUserId,
			// customer_temp.getId()) != null ? "1" : "0");
			Customer temps = customerService.getCustomerByComeidAndCreateId(
					customer_temp.getId(), user.getId());
			if (temps != null) {
				organProfileVo.setComeId(temps.getId());
			}
			organProfileVo.setFriends(isFriend(user.getId(),
					customer_temp.getUserId()));
			// cusotmerCommonService.findCustomerAuth(view, organProfileVo,
			// customer_temp, user);

			String sckNum = customer_temp.getStockNum();// 证券号码
			// organProfileVo.setCustomerId(customer_temp.getId());
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

			organProfileVo.setPersonalPlateList(customer_temp
					.getPersonalPlateList());
			organProfileVo.setVirtual(customer_temp.getVirtual());
			organProfileVo.setCreateById(customer_temp.getCreateById());
		
	        if(customer_temp.getPicLogo()==null||"".equals(customer_temp.getPicLogo())){
				
				organProfileVo.setPicLogo(rpe.getNginxRoot()
						+ Constants.ORGAN_DEFAULT_PIC_PATH);
			}else{
				organProfileVo.setPicLogo(rpe.getNginxRoot()
						+ Utils.alterImageUrl(customer_temp.getPicLogo()));
			}
	        
			organProfileVo.setOrgType(customer_temp.getOrgType());
			organProfileVo.setOrganAllName(customer_temp.getOrganAllName());
			organProfileVo.setAreaString(customer_temp.getAreaString());
			organProfileVo.setAreaid(customer_temp.getAreaid());
			organProfileVo.setAddress(customer_temp.getAddress());
			organProfileVo.setStatus(customer_temp.getStatus());
			organProfileVo.setLinkEmail(customer_temp.getLinkEmail());
			organProfileVo.setLinkManName(customer_temp.getLinkManName());

			if (customer_temp.getTemplateId() == 0) {
				if (customer_temp.getOrgType() == 1
						|| customer_temp.getOrgType() == 4) {
					organProfileVo.setTemplateId(1);
				} else if (customer_temp.getOrgType() == 2) {
					organProfileVo.setTemplateId(2);
				} else if (customer_temp.getOrgType() == 3) {
					organProfileVo.setTemplateId(5);
				}
			} else {
				organProfileVo.setTemplateId(customer_temp.getTemplateId());
			}

			if (customer_temp.getMoudles() != null
					&& customer_temp.getMoudles().size() > 0) {
				organProfileVo.setMoudles(customer_temp.getMoudles());
			} else {

				organProfileVo.setMoudles(OrganUtils
						.createMoudles(customer_temp));

			}
			
			return organProfileVo;
		}

		return null;
	}
	
	
	
	
	
	

	/**
	 * 返回组织详情和模板 数据 切换模板时使用
	 * 
	 * @author caizhigang
	 * @param customerId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/organ/getOrganProfileAndTemplate.json", method = RequestMethod.POST)
	public Map<String, Object> getOrganProfileAndTemplate(
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String requestJson = Utils.getJsonIn(request);
		Map<String, Object> responseData = new HashMap<String, Object>();
		JSONObject j = JSONObject.fromObject(requestJson);
		// 登录用户id
		User user = null;
		user = getUser(request);
		// 组织id 同时是用户id
		long organId = JsonUtil.getNodeToLong(j, "organId");
		long templateId = JsonUtil.getNodeToLong(j, "templateId");
		OrganRegister organ = organRegisterService
				.getOrganRegisterById(organId);
		// User organ=userService.selectByPrimaryKey(organId);
		
		if(organ==null){
			return returnFailMSGNew("-1", "请检查参数,组织未找到");
		}
	
		Customer customer_temp = customerService.findOrganDataInTemplate(
				organId, templateId);// 组织详情基本资料
		
		if(customer_temp==null){// 兼容老数据
			   customer_temp= customerService.findOne(organId);
			   if(customer_temp!=null&&customer_temp.getTemplateId()!=templateId){
				   customer_temp=null;
			   }
		 }
		   
		if (customer_temp != null) {
			
			OrganProfileVo organProfileVo=createOrganProfileVo(customer_temp, user, organId);
			responseData.put("customer", organProfileVo);
			responseData.put("bindUserId", customer_temp.getBindUserId());
			responseData.put("id", organProfileVo.getId());
			responseData.put("organNumber", customer_temp.getOrganNumber());
			try {
				customerCountService
						.updateCustomerCount(
								com.ginkgocap.ywxt.organ.model.Constants.customerCountType.read
										.getType(), organId);
			} catch (Exception e) {
				logger.error("插入组织数据统计功能报错,请求参数json: ", e);
			}

			responseData.put("hasData", true);
		}else{
			responseData.put("hasData", false);
		}

		Template template = templateService.findTemplateById(templateId);
		
		TemplateVo templateVo = new TemplateVo();
		templateVo.setTemplateName(template.getTemplateName());
		templateVo.setTemplateId(template.getTemplateId());
		templateVo.setTemplateType(template.getType());
		templateVo.setMoudles(template.getMoudles());
		templateVo.setVisibleMoudles(template.getVisibleMoudles());
		responseData.put("template", templateVo);

		return returnSuccessMSG(responseData);
	}

	public String isFriend(long userId, long toUserId) {
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
		int status = 3;

		try {
			status = friendsRelationService.getFriendsStatus(userId, toUserId);
			if (status == 0 || status == -1) {
				status = 3;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(status);
	}

	/**
	 * 
	 * 定义成功返回信息
	 * 
	 * @param successResult
	 * @return
	 * @author haiyan
	 */
	protected Map<String, Object> returnSuccessMSG(
			Map<String, Object> successResult) {

		successResult.put("success", true);
		return successResult;
	}

	/**
	 * 定义错误返回信息
	 * 
	 * @param result
	 * @param errRespCode
	 * @param errRespMsg
	 * @return
	 * @author wangfeiliang
	 */
	protected Map<String, Object> returnFailMSGNew(String errRespCode,
			String errRespMsg) {
		Map<String, Object> result = new HashMap<String, Object>();

		result.put("success", false);
		result.put("msg", errRespMsg);
		return result;
	}

	
	
	   
   
	
}
