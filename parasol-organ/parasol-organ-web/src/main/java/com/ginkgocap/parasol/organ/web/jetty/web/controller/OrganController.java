package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ginkgocap.parasol.organ.web.jetty.web.controller.BaseController;
import com.ginkgocap.parasol.organ.web.jetty.web.resource.ResourcePathExposer;
import com.ginkgocap.parasol.organ.web.jetty.web.service.organ.CusotmerCommonService;
import com.ginkgocap.parasol.organ.web.jetty.web.service.organ.DealCustomerConnectInfoService;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.CommonUtil;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.Constants;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.JsonReadUtil;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.ObjectUtils;
import com.ginkgocap.parasol.organ.web.jetty.web.vo.organ.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
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
import com.ginkgocap.ywxt.cache.Cache;
import com.ginkgocap.ywxt.cache.CacheModule;
import com.ginkgocap.ywxt.organ.model.Constants2;
import com.ginkgocap.ywxt.organ.model.Customer;
import com.ginkgocap.ywxt.organ.model.CustomerTag;
import com.ginkgocap.ywxt.organ.model.SimpleCustomer;
import com.ginkgocap.ywxt.organ.model.notice.CustomerNotice;
import com.ginkgocap.ywxt.organ.model.profile.CustomerPersonalPlate;
import com.ginkgocap.ywxt.organ.model.profile.CustomerProfile;
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
 * <p>
 * Title: OrgController.java<／p>
 * <p>
 * Description: 组织controller层<／p>
 * 
 * @author wfl
 * @date 2015-3-9
 * @version 1.0
 */
@Controller
@RequestMapping("/org")
public class OrganController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Resource
	private UserService userService;
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
	@Resource
	private DealCustomerConnectInfoService dealCustomerConnectInfoService;
	@Resource
	private Cache cache;
	@Autowired
	// 用户黑名单
	private UserBlackService userBlackService;
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
	private CusotmerCommonService cusotmerCommonService;
	@Autowired
	public OrganRegisterService organRegisterService;

	@Autowired
	TemplateService templateService;
	
	private static final String CLASS_NAME = OrganController.class.getName();


	/**
	 * 组织注册信息查询
	 * 
	 * @author wfl
	 */
	@ResponseBody
	@RequestMapping(value = "/save/find.json", method = RequestMethod.POST)
	public Map<String, Object> orgSaveFind(HttpServletRequest request, HttpServletResponse response) throws IOException {

		String requestJson = "";
		requestJson = getJsonParamStr(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		try {
			if (requestJson != null && !"".equals(requestJson)) {
				JSONObject jo = JSONObject.fromObject(requestJson);
				Long orgId = CommonUtil.getLongFromJSONObject(jo, "orgId");
				Customer customer = customerService.findOne(orgId);
				OrgInfoVo orgInfo = new OrgInfoVo();
				if (customer != null) {
					orgInfo.setIsListing(customer.getIsListing());
					orgInfo.setLicensePic("".equals(StringUtils.trimToEmpty(customer.getLicensePic())) ? "" : rpe.getNginxRoot()
							+ StringUtils.trimToEmpty(customer.getLicensePic()));
					orgInfo.setLinkIdPic("".equals(StringUtils.trimToEmpty(customer.getLinkIdPic())) ? "" : rpe.getNginxRoot()
							+ StringUtils.trimToEmpty(customer.getLinkIdPic()));
					orgInfo.setLinkIdPicReverse("".equals(StringUtils.trimToEmpty(customer.getLinkIdPicReverse())) ? "" : rpe.getNginxRoot()
							+ StringUtils.trimToEmpty(customer.getLinkIdPicReverse()));
					orgInfo.setLinkName(customer.getLinkName());
					orgInfo.setLinkMobile(customer.getLinkMobile());
					orgInfo.setName(customer.getName());
					orgInfo.setOrgType(customer.getType());
					orgInfo.setShotName(customer.getShotName());
					orgInfo.setStockNum(customer.getStockNum());
					orgInfo.setPicLogo("".equals(StringUtils.trimToEmpty(customer.getPicLogo())) ? "" : rpe.getNginxRoot()
							+ ObjectUtils.alterImageUrl(StringUtils.trimToEmpty(customer.getPicLogo())));
				}
				responseDataMap.put("success", true);
				responseDataMap.put("orgInfo", orgInfo);
			}
		} catch (Exception e) {
			setSessionAndErr(request, response, "-1", "系统异常，请稍后再试！");
			logger.error("组织注册查询失败，请求参数为:{}" + requestJson, e);
			return returnFailMSGNew("01", "系统异常，请稍后再试！");
		}

		model.put("responseData", responseDataMap);
		model.put("notification", notificationMap);

		return model;
	}

	/**
	 * 添加组织详情(老的)
	 * 
	 * @author wfl
	 */
	@ResponseBody
	@RequestMapping(value = "/saveCusProfile.json", method = RequestMethod.POST)
	public Map<String, Object> customerSaveCusProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String requestJson = "";
		requestJson = getJsonParamStr(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		if (requestJson != null && !"".equals(requestJson)) {
			try {
				JSONObject jo = JSONObject.fromObject(requestJson);
				User user = getUser(request);
				jo.remove("relevance");
				jo.remove("directory");
				jo.remove("lableList");
				jo.remove("customerPermissions");

				// android端多传的属性
				jo.remove("friends");

				ObjectMapper objectMapper = new ObjectMapper();
				Customer customer = objectMapper.readValue(jo.toString(), Customer.class);

				if ("1".equals(customer.getIsListing()) && isNullOrEmpty(customer.getStockNum())) {
					setSessionAndErr(request, response, "-1", "上市公司必须填写证券号码！");
					return returnFailMSGNew("01", "上市公司必须填写证券号码！");
				}

				Customer oldCustomer = customerService.findOne(customer.getId());
				// 原来的属性
				if (oldCustomer != null) {
					if (user.getId() != oldCustomer.getCreateById()) {// 修改
						setSessionAndErr(request, response, "-1", "您没有权限进行此操作");
						return returnFailMSGNew("01", "您没有权限进行此操作");
					}
					customer.setName(oldCustomer.getName());
					customer.setLinkIdPic(oldCustomer.getLinkIdPic());
					customer.setLinkName(oldCustomer.getLinkName());
					customer.setLicensePic(oldCustomer.getLicensePic());
					customer.setCreateById(oldCustomer.getCreateById());
					customer.setUserId(oldCustomer.getUserId());
					customer.setCtime(oldCustomer.getCtime());
				}
				customer.setPicLogo("".equals(StringUtils.trimToEmpty(customer.getPicLogo())) ? Constants.ORGAN_DEFAULT_PIC_PATH : ObjectUtils
						.alterImageUrl(customer.getPicLogo()));
				customer.setCreateById(user.getId());
				customer.setUserId(user.getId());
				customer.setVirtual("1");
				customer.setUtime(DateFunc.getDate());

				// TODO
				// 关联json r:事件,p:人脉,o:组织,k:知识 type:1 需求 2 人脉 3 全平台普通用户 4
				// 组织(全平台组织用户) 5 客户 6 知识
				// {"r":[{"tag":"33","conn":[{"type":1,"id":2111,"title":"d d d d","ownerid":1,"ownername":"的的的的的的的","requirementtype":"tzxq"}]}],
				// "p":[{"tag":"111","conn":[{"type":2,"id":"141527672992500079","name":"五小六","ownerid":1,"ownername":"","caree":"","company":""}]}],
				// "o":[{"tag":"22","conn":[{"type":5,"id":618,"name":"我的测试客户","ownerid":"","ownername":"","address":"","hy":","},{"type":5,"id":617,"name":" 中国平安","ownerid":"","ownername":"","address":"","hy":",保险公司,"}]}],
				// "k":[{"tag":"44","conn":[{"type":5,"id":618,"title":"测试知识","ownerid":"","ownername":"","columntype":"","columnpath":""]}
				String relevance = JsonUtil.getJsonNode(requestJson, "relevance").toString();
				if (StringUtils.isBlank(relevance) || StringUtils.equals(relevance, "{}")) {
					relevance = "{\"r\":[],\"p\":[],\"o\":[],\"k\":[]}";
				}
				// 目录
				// [2,3,4]
				String directory = JsonUtil.getJsonNode(requestJson, "directory").toString();
				if (StringUtils.isBlank(directory)) {
					directory = "[]";
				}
				// 标签
				// [{"tagId":1,"tagName":"飞凤舞"}]
				String tagJson = JsonUtil.getJsonNode(requestJson, "lableList").toString();
				if (StringUtils.isBlank(tagJson)) {
					tagJson = "[]";
				}
				// 权限
				// {"dule":false,
				// "xiaoles":[],"zhongles":[{"id":13556,"name":"张斌"},{"id":12454,"name":"林美霞"},{"id":13835,"name":"张桂珍"}],
				// "dales":[{"id":13414,"name":"股市水晶球"},{"id":10089,"name":"杨楠"},{"id":13247,"name":"曾添_dataplayer"}]
				// "modelType":[1,2,3],
				// "mento":"哈哈"}
				String customerPermissions = JsonUtil.getJsonNode(requestJson, "customerPermissions").toString();
				if (StringUtils.isBlank(customerPermissions) || StringUtils.equals(customerPermissions, "{}")) {
					// customerPermissions="{\"dule\":true}";
					customerPermissions = "{}";
				}
				customer.setDirectory(directory);
				customer.setCustomerPermissions(customerPermissions);
				customer.setRelevance(relevance);
				// 标签转成CustomerTag对象
				List<CustomerTag> tagList = new ArrayList<CustomerTag>();
				if (StringUtils.isNotBlank(tagJson)) {
					tagList = objectMapper.readValue(tagJson, new TypeReference<List<CustomerTag>>() {
					});
				}
				customer.setLableList(tagList);

				// customerProfileService.saveOrUpdate(customer);
				CustomerProfile cp = customer.getProfile();
				cp.setId(customer.getId());
				customerProfileService.saveOrUpdate(cp);
				customerService.saveOrUpdate(customer);
				// 删除冗余数据
				customerService.deletePartBycustomerType(customer.getId(), customer.getType());

				// 修改user对象属性
				User oldUser = userService.selectByPrimaryKey(user.getId());
				oldUser.setIsListing(customer.getIsListing());
				oldUser.setStkcd(customer.getStockNum());
				oldUser.setPicPath(ObjectUtils.alterImageUrl(customer.getPicLogo()));
				oldUser.setIndustry(StringUtils.join(customer.getIndustrys(), ","));
				oldUser.setIndustryIds(StringUtils.join(customer.getIndustryIds(), ","));
				oldUser.setShortName(customer.getShotName());
				userService.updateUserInfo(oldUser);

				if (!isNullOrEmpty(relevance)) {
					Map<String, Object> map = dealCustomerConnectInfoService.insertCustomerConnectInfo(relevance, customer.getId(), user.getId());
					String result = String.valueOf(map.get(Constants2.status));// 0
																				// 关联失败
																				// 1
																				// 关联成功
				}
				String groupIds = "";
				if (StringUtils.isNotBlank(directory) && !StringUtils.equals(directory, "[]")) {
					JSONArray arr = JSONArray.fromObject(directory);
					groupIds = StringUtils.join(arr, ",");
				}
				customerGroupService.updateGroup(customer.getId() + "", groupIds);
				if (!isNullOrEmpty(tagJson)) {
					rCustomerTagService.saveAll(customer.getId(), tagJson, user.getId());
				}

				int isRecommend = 0;// 是否金桐脑推荐的 0 个人创建 1 金桐脑推荐
				int virtual = 0;// 是否是组织，0客户，1组织
				/*
				 * if(!isNullOrEmpty(customerPermissions)){ Map<String,Object>
				 * permissonResult =
				 * customerPermissionService.saveAll(customerPermissions,
				 * customer.getId(), user.getId(), isRecommend, virtual);
				 * logger.
				 * info("保存客户权限：客户id："+customer.getId()+",result"+JSONObject
				 * .fromObject(permissonResult).toString()); }
				 */
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

	/**
	 * 添加组织详情(新的web端调用)
	 * 
	 * @author wfl
	 */
//	@ResponseBody
//	@RequestMapping(value = "/saveOrganProfile.json", method = RequestMethod.POST)
	public Map<String, Object> saveOrganProfile(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String requestJson = "";
		requestJson = getJsonParamStr(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		if (requestJson != null && !"".equals(requestJson)) {
			try {
				JSONObject jo = JSONObject.fromObject(requestJson);
				User user = getUser(request);
				jo.remove("relevance");
				jo.remove("directory");
				jo.remove("lableList");
				jo.remove("customerPermissions");

				// android端多传的属性
				jo.remove("friends");
				/*
				 * long organId=jo.optLong("id"); jo.remove("id");
				 */

				ObjectMapper objectMapper = new ObjectMapper();
				Customer customer = objectMapper.readValue(jo.toString(), Customer.class);

				if ("1".equals(customer.getIsListing()) && isNullOrEmpty(customer.getStockNum())) {
					setSessionAndErr(request, response, "-1", "上市公司必须填写证券号码！");
					return returnFailMSGNew("01", "上市公司必须填写证券号码！");
				}
				// OrganRegister
				// organ=organRegisterService.getOrganRegisterById(organId);
				Customer oldCustomer = customerService.findOne(customer.getId());
				// 原来的属性
				if (oldCustomer != null) {
					System.out.println("old:"+oldCustomer.getCreateById());
					System.out.println("user:"+user.getId() );
//					if (user.getId() != oldCustomer.getCreateById()) {// 修改
//						setSessionAndErr(request, response, "-1", "您没有权限进行此操作");
//						return returnFailMSGNew("01", "您没有权限进行此操作");
//					}
					oldCustomer.setName(customer.getName());
					// oldCustomer.setPhone(customer.getPhone());
					oldCustomer.setLinkManName(customer.getLinkManName());
					oldCustomer.setLinkMobile(customer.getLinkMobile());
					oldCustomer.setLinkEmail(customer.getLinkEmail());
					oldCustomer.setOrgType(customer.getOrgType());
					oldCustomer.setIsListing(customer.getIsListing());
					oldCustomer.setStockNum(customer.getStockNum());
					oldCustomer.setIndustry(customer.getIndustry());
					oldCustomer.setIndustryId(customer.getIndustryId());
					oldCustomer.setAreaString(customer.getAreaString());
					oldCustomer.setAddress(customer.getAddress());
					oldCustomer.setAreaid(customer.getAreaid());
					oldCustomer.setDiscribe(customer.getDiscribe());
					oldCustomer.setPropertyList(customer.getPropertyList());
					oldCustomer.setPersonalPlateList(customer.getPersonalPlateList());
					oldCustomer.setOrganNumber(customer.getOrganNumber());
					oldCustomer.setTemplateId(customer.getTemplateId());
					oldCustomer.setMouduesPlateList(customer.getMouduesPlateList());
					oldCustomer.setComplexmodule(customer.getComplexmodule());
				
				}
				oldCustomer.setPicLogo("".equals(StringUtils.trimToEmpty(customer.getPicLogo())) ? Constants.ORGAN_DEFAULT_PIC_PATH : ObjectUtils
						.alterImageUrl(customer.getPicLogo()));
				oldCustomer.setCreateById(user.getId());
				oldCustomer.setUserId(user.getId());
				oldCustomer.setVirtual("1");
				oldCustomer.setUtime(DateFunc.getDate());
				// TODO
				// 关联json r:事件,p:人脉,o:组织,k:知识 type:1 需求 2 人脉 3 全平台普通用户 4
				// 组织(全平台组织用户) 5 客户 6 知识
				// {"r":[{"tag":"33","conn":[{"type":1,"id":2111,"title":"d d d d","ownerid":1,"ownername":"的的的的的的的","requirementtype":"tzxq"}]}],
				// "p":[{"tag":"111","conn":[{"type":2,"id":"141527672992500079","name":"五小六","ownerid":1,"ownername":"","caree":"","company":""}]}],
				// "o":[{"tag":"22","conn":[{"type":5,"id":618,"name":"我的测试客户","ownerid":"","ownername":"","address":"","hy":","},{"type":5,"id":617,"name":" 中国平安","ownerid":"","ownername":"","address":"","hy":",保险公司,"}]}],
				// "k":[{"tag":"44","conn":[{"type":5,"id":618,"title":"测试知识","ownerid":"","ownername":"","columntype":"","columnpath":""]}
				String relevance = JsonUtil.getJsonNode(requestJson, "relevance").toString();
				if (StringUtils.isBlank(relevance) || StringUtils.equals(relevance, "{}")) {
					relevance = "{\"r\":[],\"p\":[],\"o\":[],\"k\":[]}";
				}
				// 目录
				// [2,3,4]
				String directory = JsonUtil.getJsonNode(requestJson, "directory").toString();
				if (StringUtils.isBlank(directory)) {
					directory = "[]";
				}
				// 标签
				// [{"tagId":1,"tagName":"飞凤舞"}]
				String tagJson = JsonUtil.getJsonNode(requestJson, "lableList").toString();
				if (StringUtils.isBlank(tagJson)) {
					tagJson = "[]";
				}
				// 权限
				// {"dule":false,
				// "xiaoles":[],"zhongles":[{"id":13556,"name":"张斌"},{"id":12454,"name":"林美霞"},{"id":13835,"name":"张桂珍"}],
				// "dales":[{"id":13414,"name":"股市水晶球"},{"id":10089,"name":"杨楠"},{"id":13247,"name":"曾添_dataplayer"}]
				// "modelType":[1,2,3],
				// "mento":"哈哈"}
				String customerPermissions = JsonUtil.getJsonNode(requestJson, "customerPermissions").toString();
				if (StringUtils.isBlank(customerPermissions) || StringUtils.equals(customerPermissions, "{}")) {
					// customerPermissions="{\"dule\":true}";
					customerPermissions = "{}";
				}
				oldCustomer.setDirectory(directory);
				oldCustomer.setCustomerPermissions(customerPermissions);
				oldCustomer.setRelevance(relevance);
				// 标签转成CustomerTag对象
				List<CustomerTag> tagList = new ArrayList<CustomerTag>();
				if (StringUtils.isNotBlank(tagJson)) {
					tagList = objectMapper.readValue(tagJson, new TypeReference<List<CustomerTag>>() {
					});
				}
				oldCustomer.setLableList(tagList);

				// customerProfileService.saveOrUpdate(customer);
				/*
				 * CustomerProfile cp=customer.getProfile();
				 * cp.setId(customer.getId());
				 * customerProfileService.saveOrUpdate(cp);
				 */
				customerService.saveOrUpdate(oldCustomer);
				// 删除冗余数据
				// customerService.deletePartBycustomerType(customer.getId(),customer.getType());

				updateOrganRegister(oldCustomer, customer.getCreateById());

				if (!isNullOrEmpty(relevance)) {
					Map<String, Object> map = dealCustomerConnectInfoService.insertCustomerConnectInfo(relevance, customer.getId(), user.getId());
					String result = String.valueOf(map.get(Constants2.status));// 0
																				// 关联失败
																				// 1
																				// 关联成功
				}
				String groupIds = "";
				if (StringUtils.isNotBlank(directory) && !StringUtils.equals(directory, "[]")) {
					JSONArray arr = JSONArray.fromObject(directory);
					groupIds = StringUtils.join(arr, ",");
				}
				customerGroupService.updateGroup(oldCustomer.getId() + "", groupIds);
				if (!isNullOrEmpty(tagJson)) {
					rCustomerTagService.saveAll(oldCustomer.getId(), tagJson, user.getId());
				}

				int isRecommend = 0;// 是否金桐脑推荐的 0 个人创建 1 金桐脑推荐
				int virtual = 0;// 是否是组织，0客户，1组织
				/*
				 * if(!isNullOrEmpty(customerPermissions)){ Map<String,Object>
				 * permissonResult =
				 * customerPermissionService.saveAll(customerPermissions,
				 * customer.getId(), user.getId(), isRecommend, virtual);
				 * logger.
				 * info("保存客户权限：客户id："+customer.getId()+",result"+JSONObject
				 * .fromObject(permissonResult).toString()); }
				 */
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
	
	
	
	/**
	 * 添加组织详情(新的web端调用)
	 * 
	 * @author cazhigang
	 */
	@ResponseBody
	@RequestMapping(value = "/saveOrganProfile.json", method = RequestMethod.POST)
	public Map<String, Object> saveOrganPro(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String requestJson = "";
		requestJson = getJsonParamStr(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
	
		
		if (requestJson != null && !"".equals(requestJson)) {
			try {
				JSONObject jo = JSONObject.fromObject(requestJson);
				User user = getUser(request);
				jo.remove("relevance");
				jo.remove("directory");
				jo.remove("lableList");
				jo.remove("customerPermissions");

				// android端多传的属性
				jo.remove("friends");
				/*
				 * long organId=jo.optLong("id"); jo.remove("id");
				 */

				OrganRegister organRegister=organRegisterService.getOrganRegisterById(user.getId());
				ObjectMapper objectMapper = new ObjectMapper();
				Customer customer = objectMapper.readValue(jo.toString(), Customer.class);

				if ("1".equals(customer.getIsListing()) && isNullOrEmpty(customer.getStockNum())) {
					setSessionAndErr(request, response, "-1", "上市公司必须填写证券号码！");
					return returnFailMSGNew("01", "上市公司必须填写证券号码！");
				}

				
				customer.setPicLogo("".equals(StringUtils.trimToEmpty(customer.getPicLogo())) ? Constants.ORGAN_DEFAULT_PIC_PATH : ObjectUtils
				.alterImageUrl(customer.getPicLogo()));
				customer.setCreateById(user.getId());
				customer.setUserId(user.getId());
				customer.setVirtual("1");
				customer.setUtime(DateFunc.getDate());
				// 数据类型  组织数据
				customerService.saveOrUpdateOrgData(customer);
				// 删除冗余数据
				// customerService.deletePartBycustomerType(customer.getId(),customer.getType());

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

		// 修改user对象属性
		User oldUser = userService.selectByPrimaryKey(customer.getCreateById());
		oldUser.setIsListing(customer.getIsListing());
		oldUser.setStkcd(customer.getStockNum());
		oldUser.setPicPath(ObjectUtils.alterImageUrl(customer.getPicLogo()));
		oldUser.setIndustry(StringUtils.join(customer.getIndustry(), ","));
		oldUser.setIndustryIds(StringUtils.join(customer.getIndustryId(), ","));
		oldUser.setShortName(customer.getName());
		userService.updateUserInfo(oldUser);
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
	@RequestMapping(value = "/qr", method = RequestMethod.GET)
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
	 * 组织详情查询(老组织详情)
	 * 
	 * @param customerId
	 *            组织用户Id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/orgAndProInfo.json", method = RequestMethod.POST)
	public Map<String, Object> orgAndProInfo(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestJson = JsonReadUtil.getJsonIn(request);
		Map<String, Object> responseData = new HashMap<String, Object>();
		JSONObject j = JSONObject.fromObject(requestJson);
		long orgId = JsonUtil.getNodeToLong(j, "orgId");
		long userId = JsonUtil.getNodeToLong(j, "userId");
		String view = JsonUtil.getNodeToString(j, "view"); // 1:转发进来都是大乐，2:转发到第三方,不登录查看组织详情
		orgId = replaceOrgId(orgId, userId);
		Customer customer_temp = customerService.findOne(orgId);// 组织详情基本资料
		if (customer_temp != null) {
			CustomerProfileVo customer_new = new CustomerProfileVo();
			// 查看用户个人设置
			UserConfig uc = userConfigService.getByUserId(customer_temp.getUserId());
			if (uc != null) {
				if (uc.getHomePageVisible() == null)
					uc.setHomePageVisible(2);
				customer_new.setUserConfig(uc.getHomePageVisible());
			}
			User user = getJTNUser(request);
			boolean isblack = userBlackService.isBlackRelation(user.getId(), customer_temp.getUserId());
			customer_new.setBlack(isblack);// 黑名单关系
			if (isblack) {
				return returnFailMSGNew("01", "请查看其它组织");
			}
			customer_new.setLoginUserId(user.getId());
			// 新增是否收藏
			customer_new.setIsCollect(customerCollectService.findByUserIdAndCustomerId(user.getId(), customer_temp.getId()) != null ? "1" : "0");
			Customer temps = customerService.getCustomerByComeidAndCreateId(customer_temp.getId(), user.getId());
			if (temps != null) {
				customer_new.setComeId(temps.getId());
			}
			customer_new.setFriends(isFriend(user.getId(), customer_temp.getUserId()));
			customer_new.setAuth(cusotmerCommonService.getCustomerAuth(view, customer_temp, user));

			String sckNum = customer_temp.getStockNum();// 证券号码
			customer_new.setCustomerId(customer_temp.getId());
			customer_new.setName(customer_temp.getName());
			customer_new.setShotName(customer_temp.getShotName());
			customer_new.setType(customer_temp.getType());
			customer_new.setIndustrys(customer_temp.getIndustrys());
			customer_new.setIndustryIds(customer_temp.getIndustryIds());
			customer_new.setPropertyList(customer_temp.getPropertyList());
			customer_new.setUserId(customer_temp.getUserId());
			customer_new.setIsListing(customer_temp.getIsListing());
			customer_new.setLinkMobile(customer_temp.getLinkMobile());
			customer_new.setCreateType(getUserType(customer_temp.getCreateById()) == true ? "2" : "1");
			customer_new.setStockNum(sckNum);
			customer_new.setDiscribe(customer_temp.getDiscribe());

			// 组织邮箱显示注册邮箱
			User emailUser = userService.selectByPrimaryKey(customer_temp.getUserId());
			if (emailUser != null)
				customer_new.setLinkEmail(emailUser.getEmail());
			CustomerProfile cpr = customerProfileService.findOne(orgId);
			if (cpr != null)
				customer_new.setPersonalPlateList(cpr.getPersonalPlateList());
			customer_new.setVirtual(customer_temp.getVirtual());
			customer_new.setCreateById(customer_temp.getCreateById());
			customer_new.setArea(customer_temp.getArea());
			customer_new.setPicLogo(rpe.getNginxRoot() + ObjectUtils.alterImageUrl(customer_temp.getPicLogo()));
			customer_new.setBanner(rpe.getNginxRoot() + customer_temp.getBanner());
			customer_new.setPhoneList(customer_temp.getPhoneList());
			customer_new.setDirectory(customer_temp.getDirectory());
			customer_new.setLableList(rCustomerTagService.getTagListByCustomerId(orgId));
			cusotmerCommonService.findFourModule(responseData, customer_temp, rpe.getNginxRoot());
			customer_new.setIndustryObj(customer_temp.getIndustryObj());
			List<PushKnowledge> pkList = new ArrayList<PushKnowledge>();
			// 获取最新公告
			List<CustomerNotice> list = new ArrayList<CustomerNotice>();// customerNoticeService.qryListByStkcd(sckNum,
																		// 1,5);
			// getOrgResource(responseData,orgId);
			List<ColumnVo> columnList = new ArrayList<ColumnVo>();// cusotmerCommonService.findSelectModel(orgId,
																	// customer_new);
			responseData.put("column", columnList);
			responseData.put("orgNewList", pkList);
			responseData.put("noticeList", list);
			responseData.put("customer", customer_new);
			try {
				customerCountService.updateCustomerCount(com.ginkgocap.ywxt.organ.model.Constants.customerCountType.read.getType(), orgId);
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
	 * 组织详情查询（新组织详情web端调用）
	 * 
	 * @param customerId
	 *            组织用户Id
	 * @return
	 * @throws Exception
	 */
//	@ResponseBody
//	@RequestMapping(value = "/getOrganProfile.json", method = RequestMethod.POST)
	public Map<String, Object> getOrganProfile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestJson = JsonReadUtil.getJsonIn(request);
		Map<String, Object> responseData = new HashMap<String, Object>();
		JSONObject j = JSONObject.fromObject(requestJson);
		long organId = JsonUtil.getNodeToLong(j, "organId");// 新组织表id
		OrganRegister organ = organRegisterService.getOrganRegisterById(organId);
//		User organ=userService.selectByPrimaryKey(organId);
		
//		long orgId = (organ == null||StringUtils.isEmpty(organ.getPeopleId())) ? 0 : Long.parseLong(organ.getPeopleId());
		
		long orgId = (organ == null) ? 0 : organ.getMongoId();

		String view = JsonUtil.getNodeToString(j, "view"); // 1:转发进来都是大乐，2:转发到第三方,不登录查看组织详情
		Customer customer_temp = customerService.findOne(orgId);// 组织详情基本资料
		if (customer_temp != null) {
			CustomerProfileVoNew customer_new = new CustomerProfileVoNew();
			// 查看用户个人设置
			UserConfig uc = userConfigService.getByUserId(customer_temp.getUserId());
			if (uc != null) {
				if (uc.getHomePageVisible() == null)
					uc.setHomePageVisible(2);
				customer_new.setUserConfig(uc.getHomePageVisible());
			}
			User user = getJTNUser(request);
			boolean isblack = userBlackService.isBlackRelation(user.getId(), customer_temp.getUserId());
			customer_new.setBlack(isblack);// 黑名单关系
			if (isblack) {
				return returnFailMSGNew("01", "请查看其它组织");
			}
			customer_new.setLoginUserId(user.getId());
			// 新增是否收藏
			customer_new.setIsCollect(customerCollectService.findByUserIdAndCustomerId(user.getId(), customer_temp.getId()) != null ? "1" : "0");
			Customer temps = customerService.getCustomerByComeidAndCreateId(customer_temp.getId(), user.getId());
			if (temps != null) {
				customer_new.setComeId(temps.getId());
			}
			customer_new.setFriends(isFriend(user.getId(), customer_temp.getUserId()));
			cusotmerCommonService.findCustomerAuth(view, customer_new, customer_temp, user);

			String sckNum = customer_temp.getStockNum();// 证券号码
			customer_new.setCustomerId(customer_temp.getId());
			customer_new.setName(customer_temp.getName());
			customer_new.setIndustry(customer_temp.getIndustry());
			customer_new.setIndustryId(customer_temp.getIndustryId());
			customer_new.setPropertyList(customer_temp.getPropertyList());
			customer_new.setUserId(customer_temp.getUserId());
			customer_new.setIsListing(customer_temp.getIsListing());
			customer_new.setLinkMobile(customer_temp.getLinkMobile());
			customer_new.setCreateType(getUserType(customer_temp.getCreateById()) == true ? "2" : "1");
			customer_new.setStockNum(sckNum); // 改过的
			customer_new.setDiscribe(customer_temp.getDiscribe());
			customer_new.setOrganNumber(customer_temp.getOrganNumber());

			customer_new.setTemplateId(customer_temp.getTemplateId());
			customer_new.setMouduesPlateList(customer_temp.getMouduesPlateList());
			customer_new.setComplexmodule(customer_temp.getComplexmodule());
			customer_new.setDataType(customer_temp.getDataType());
			// 组织邮箱显示注册邮箱
			/*
			 * User
			 * emailUser=userService.selectByPrimaryKey(customer_temp.getUserId
			 * ()); if(emailUser!=null)
			 * customer_new.setLinkEmail(emailUser.getEmail()); CustomerProfile
			 * cpr=customerProfileService.findOne(orgId); if(cpr!=null)
			 */
			customer_new.setPersonalPlateList(customer_temp.getPersonalPlateList());
			customer_new.setVirtual(customer_temp.getVirtual());
			customer_new.setCreateById(customer_temp.getCreateById());
			customer_new.setPicLogo(rpe.getNginxRoot() + ObjectUtils.alterImageUrl(customer_temp.getPicLogo()));
			// customer_new.setLableList(rCustomerTagService.getTagListByCustomerId(orgId));
			// cusotmerCommonService.findFourModule(responseData,
			// customer_temp,rpe.getNginxRoot());
			// customer_new.setIndustryObj(customer_temp.getIndustryObj());
			cusotmerCommonService.findCustomerAuth(view, customer_new, customer_temp, user);

			customer_new.setOrgType(customer_temp.getOrgType());
			customer_new.setOrganAllName(customer_temp.getOrganAllName());
			customer_new.setAreaString(customer_temp.getAreaString());
			customer_new.setAreaid(customer_temp.getAreaid());
			customer_new.setAddress(customer_temp.getAddress());
			customer_new.setStatus(customer_temp.getStatus());
			customer_new.setLinkEmail(customer_temp.getLinkEmail());
			customer_new.setLinkManName(customer_temp.getLinkManName());

			responseData.put("customer", customer_new);
			responseData.put("bindUserId", customer_temp.getBindUserId());
			responseData.put("id", customer_new.getCustomerId());
			responseData.put("organNumber", customer_temp.getOrganNumber());
			try {
				customerCountService.updateCustomerCount(com.ginkgocap.ywxt.organ.model.Constants.customerCountType.read.getType(), orgId);
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
	 * @author caizhigang
	 * 组织详情查询（新组织详情web端调用）
	 * 
	 * @param customerId
	 *            组织用户Id
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/getOrganProfile.json", method = RequestMethod.POST)
	public Map<String, Object> getOrganProfileNew(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestJson = JsonReadUtil.getJsonIn(request);
		Map<String, Object> responseData = new HashMap<String, Object>();
		JSONObject j = JSONObject.fromObject(requestJson);
		long organId = JsonUtil.getNodeToLong(j, "organId");// 新组织表id
		OrganRegister organ = organRegisterService.getOrganRegisterById(organId);
//		User organ=userService.selectByPrimaryKey(organId);
		long orgId = organ.getId();
		String view = JsonUtil.getNodeToString(j, "view"); // 1:转发进来都是大乐，2:转发到第三方,不登录查看组织详情
		Customer customer_temp = customerService.findOrganCurrentData(organId);// 组织详情基本资料
		if (customer_temp != null) {
			CustomerProfileVoNew customer_new = new CustomerProfileVoNew();
			// 查看用户个人设置
			UserConfig uc = userConfigService.getByUserId(customer_temp.getUserId());
			if (uc != null) {
				if (uc.getHomePageVisible() == null)
					uc.setHomePageVisible(2);
				customer_new.setUserConfig(uc.getHomePageVisible());
			}
			User user = getJTNUser(request);
			boolean isblack = userBlackService.isBlackRelation(user.getId(), customer_temp.getUserId());
			customer_new.setBlack(isblack);// 黑名单关系
			if (isblack) {
				return returnFailMSGNew("01", "请查看其它组织");
			}
			customer_new.setLoginUserId(user.getId());
			// 新增是否收藏
			customer_new.setIsCollect(customerCollectService.findByUserIdAndCustomerId(user.getId(), customer_temp.getId()) != null ? "1" : "0");
			Customer temps = customerService.getCustomerByComeidAndCreateId(customer_temp.getId(), user.getId());
			if (temps != null) {
				customer_new.setComeId(temps.getId());
			}
			customer_new.setFriends(isFriend(user.getId(), customer_temp.getUserId()));
			cusotmerCommonService.findCustomerAuth(view, customer_new, customer_temp, user);

			String sckNum = customer_temp.getStockNum();// 证券号码
//			customer_new.setCustomerId(customer_temp.getId());
			customer_new.setId(customer_temp.getId());
			customer_new.setName(customer_temp.getName());
			customer_new.setIndustry(customer_temp.getIndustry());
			customer_new.setIndustryId(customer_temp.getIndustryId());
			customer_new.setPropertyList(customer_temp.getPropertyList());
			customer_new.setUserId(customer_temp.getUserId());
			customer_new.setIsListing(customer_temp.getIsListing());
			customer_new.setLinkMobile(customer_temp.getLinkMobile());
			customer_new.setCreateType(getUserType(customer_temp.getCreateById()) == true ? "2" : "1");
			customer_new.setStockNum(sckNum); // 改过的
			customer_new.setDiscribe(customer_temp.getDiscribe());
			customer_new.setOrganNumber(customer_temp.getOrganNumber());
			customer_new.setDataType(customer_temp.getDataType());
			
			// 组织邮箱显示注册邮箱
			/*
			 * User
			 * emailUser=userService.selectByPrimaryKey(customer_temp.getUserId
			 * ()); if(emailUser!=null)
			 * customer_new.setLinkEmail(emailUser.getEmail()); CustomerProfile
			 * cpr=customerProfileService.findOne(orgId); if(cpr!=null)
			 */
			customer_new.setPersonalPlateList(customer_temp.getPersonalPlateList());
			customer_new.setVirtual(customer_temp.getVirtual());
			customer_new.setCreateById(customer_temp.getCreateById());
			customer_new.setPicLogo(rpe.getNginxRoot() + ObjectUtils.alterImageUrl(customer_temp.getPicLogo()));
			// customer_new.setLableList(rCustomerTagService.getTagListByCustomerId(orgId));
			// cusotmerCommonService.findFourModule(responseData,
			// customer_temp,rpe.getNginxRoot());
			// customer_new.setIndustryObj(customer_temp.getIndustryObj());
			cusotmerCommonService.findCustomerAuth(view, customer_new, customer_temp, user);

			customer_new.setOrgType(customer_temp.getOrgType());
			customer_new.setOrganAllName(customer_temp.getOrganAllName());
			customer_new.setAreaString(customer_temp.getAreaString());
			customer_new.setAreaid(customer_temp.getAreaid());
			customer_new.setAddress(customer_temp.getAddress());
			customer_new.setStatus(customer_temp.getStatus());
			customer_new.setLinkEmail(customer_temp.getLinkEmail());
			customer_new.setLinkManName(customer_temp.getLinkManName());

			responseData.put("customer", customer_new);
			responseData.put("bindUserId", customer_temp.getBindUserId());
			responseData.put("id", customer_new.getId());
			responseData.put("organNumber", customer_temp.getOrganNumber());
			try {
				customerCountService.updateCustomerCount(com.ginkgocap.ywxt.organ.model.Constants.customerCountType.read.getType(), orgId);
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
	@RequestMapping(value = "/getOrganProfileAndTemplate.json", method = RequestMethod.POST)
	public Map<String, Object> getOrganProfileAndTemplate(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestJson = JsonReadUtil.getJsonIn(request);
		Map<String, Object> responseData = new HashMap<String, Object>();
		JSONObject j = JSONObject.fromObject(requestJson);
		long organId = JsonUtil.getNodeToLong(j, "organId");// 新组织表id
		long templateId=JsonUtil.getNodeToLong(j, "templateId");
		OrganRegister organ = organRegisterService.getOrganRegisterById(organId);
//		User organ=userService.selectByPrimaryKey(organId);
		long orgId = organ.getId();
		String view = JsonUtil.getNodeToString(j, "view"); // 1:转发进来都是大乐，2:转发到第三方,不登录查看组织详情
		Customer customer_temp = customerService.findOrganDataInTemplate(organId, templateId);// 组织详情基本资料
		if (customer_temp != null) {
			CustomerProfileVoNew customer_new = new CustomerProfileVoNew();
			// 查看用户个人设置
			UserConfig uc = userConfigService.getByUserId(customer_temp.getUserId());
			if (uc != null) {
				if (uc.getHomePageVisible() == null)
					uc.setHomePageVisible(2);
				customer_new.setUserConfig(uc.getHomePageVisible());
			}
			User user = getJTNUser(request);
			boolean isblack = userBlackService.isBlackRelation(user.getId(), customer_temp.getUserId());
			customer_new.setBlack(isblack);// 黑名单关系
			if (isblack) {
				return returnFailMSGNew("01", "请查看其它组织");
			}
			customer_new.setLoginUserId(user.getId());
			// 新增是否收藏
			customer_new.setIsCollect(customerCollectService.findByUserIdAndCustomerId(user.getId(), customer_temp.getId()) != null ? "1" : "0");
			Customer temps = customerService.getCustomerByComeidAndCreateId(customer_temp.getId(), user.getId());
			if (temps != null) {
				customer_new.setComeId(temps.getId());
			}
			customer_new.setFriends(isFriend(user.getId(), customer_temp.getUserId()));
			cusotmerCommonService.findCustomerAuth(view, customer_new, customer_temp, user);

			String sckNum = customer_temp.getStockNum();// 证券号码
//			customer_new.setCustomerId(customer_temp.getId());
			customer_new.setId(customer_temp.getId());
			customer_new.setName(customer_temp.getName());
			customer_new.setIndustry(customer_temp.getIndustry());
			customer_new.setIndustryId(customer_temp.getIndustryId());
			customer_new.setPropertyList(customer_temp.getPropertyList());
			customer_new.setUserId(customer_temp.getUserId());
			customer_new.setIsListing(customer_temp.getIsListing());
			customer_new.setLinkMobile(customer_temp.getLinkMobile());
			customer_new.setCreateType(getUserType(customer_temp.getCreateById()) == true ? "2" : "1");
			customer_new.setStockNum(sckNum); // 改过的
			customer_new.setDiscribe(customer_temp.getDiscribe());
			customer_new.setOrganNumber(customer_temp.getOrganNumber());
			customer_new.setDataType(customer_temp.getDataType());
			
			// 组织邮箱显示注册邮箱
			/*
			 * User
			 * emailUser=userService.selectByPrimaryKey(customer_temp.getUserId
			 * ()); if(emailUser!=null)
			 * customer_new.setLinkEmail(emailUser.getEmail()); CustomerProfile
			 * cpr=customerProfileService.findOne(orgId); if(cpr!=null)
			 */
			customer_new.setPersonalPlateList(customer_temp.getPersonalPlateList());
			customer_new.setVirtual(customer_temp.getVirtual());
			customer_new.setCreateById(customer_temp.getCreateById());
			customer_new.setPicLogo(rpe.getNginxRoot() + ObjectUtils.alterImageUrl(customer_temp.getPicLogo()));
			// customer_new.setLableList(rCustomerTagService.getTagListByCustomerId(orgId));
			// cusotmerCommonService.findFourModule(responseData,
			// customer_temp,rpe.getNginxRoot());
			// customer_new.setIndustryObj(customer_temp.getIndustryObj());
			cusotmerCommonService.findCustomerAuth(view, customer_new, customer_temp, user);

			customer_new.setOrgType(customer_temp.getOrgType());
			customer_new.setOrganAllName(customer_temp.getOrganAllName());
			customer_new.setAreaString(customer_temp.getAreaString());
			customer_new.setAreaid(customer_temp.getAreaid());
			customer_new.setAddress(customer_temp.getAddress());
			customer_new.setStatus(customer_temp.getStatus());
			customer_new.setLinkEmail(customer_temp.getLinkEmail());
			customer_new.setLinkManName(customer_temp.getLinkManName());

			responseData.put("customer", customer_new);
			responseData.put("bindUserId", customer_temp.getBindUserId());
			responseData.put("id", customer_new.getId());
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
		int status = friendsRelationService.getFriendsStatus(userId, toUserId);
		if (status == 0 || status == -1) {
			status = 3;
		}
		return String.valueOf(status);
	}

	public long replaceOrgId(long orgId, long userId) {
		// 根据mysql中userID查询mongo中的组织
		if (userId != 0) {
			User user_temp = userService.selectByPrimaryKey(userId);
			if (user_temp != null && user_temp.isVirtual()) {
				String peopleId = user_temp.getPeopleId();
				if (!"".equals(StringUtils.trimToEmpty(peopleId))) {
					orgId = Long.valueOf(peopleId);
				}
			}
		}
		return orgId;
	}

}
