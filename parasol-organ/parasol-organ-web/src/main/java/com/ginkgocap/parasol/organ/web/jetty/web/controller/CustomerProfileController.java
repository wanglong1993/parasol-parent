package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ginkgocap.ywxt.user.service.UserService;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.ginkgocap.parasol.associate.model.Associate;
import com.ginkgocap.parasol.associate.service.AssociateService;
import com.ginkgocap.parasol.directory.model.Directory;
import com.ginkgocap.parasol.directory.model.DirectorySource;
import com.ginkgocap.parasol.directory.service.DirectoryService;
import com.ginkgocap.parasol.directory.service.DirectorySourceService;
import com.ginkgocap.parasol.organ.web.jetty.web.resource.ResourcePathExposer;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.Constants;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.Utils;
import com.ginkgocap.parasol.organ.web.jetty.web.vo.organ.BigDataModel;
import com.ginkgocap.parasol.organ.web.jetty.web.vo.organ.CustomerProfileVoNew;
import com.ginkgocap.parasol.tags.model.Tag;
import com.ginkgocap.parasol.tags.model.TagSource;
import com.ginkgocap.parasol.tags.service.TagService;
import com.ginkgocap.parasol.tags.service.TagSourceService;
import com.ginkgocap.ywxt.organ.model.Customer;
import com.ginkgocap.ywxt.organ.model.template.Template;
import com.ginkgocap.ywxt.organ.service.CustomerCollectService;
import com.ginkgocap.ywxt.organ.service.CustomerCountService;
import com.ginkgocap.ywxt.organ.service.CustomerService;
import com.ginkgocap.ywxt.organ.service.SimpleCustomerService;
import com.ginkgocap.ywxt.organ.service.template.TemplateService;
import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.util.DateFunc;
import com.ginkgocap.ywxt.util.JsonUtil;
import com.gintong.common.phoenix.permission.ResourceType;
import com.gintong.common.phoenix.permission.entity.Permission;
import com.gintong.common.phoenix.permission.entity.PermissionQuery;
import com.gintong.common.phoenix.permission.service.PermissionRepositoryService;
import com.gintong.frame.util.dto.InterfaceResult;
import com.gintong.frame.util.dto.Notification;

/**
 * Created by jbqiu on 2016/6/10. controller 组织点评controller
 */
@Controller
@RequestMapping("/organ")
public class CustomerProfileController extends BaseController {
	
    @Autowired
    private UserService userService;
	
	@Resource
	private CustomerService customerService;

	@Autowired
	private TemplateService templateService;


	@Autowired
	PermissionRepositoryService permissionRepositoryService;

	@Autowired
	DirectorySourceService directorySourceService;

	@Autowired
	DirectoryService directoryService;

	@Autowired
	TagSourceService tagSourceService;

	@Autowired
	TagService tagService;

	@Autowired
	AssociateService associateService;
	
	@Resource
	private CustomerCollectService  customerCollectService;
	@Resource 
	private ResourcePathExposer rpe;
	
	@Resource
    private CustomerCountService customerCountService;
	
	@Autowired
	private SimpleCustomerService simpleCustomerService;

	private final Logger logger=LoggerFactory.getLogger(getClass());
	

	long appId = 1;
	long sourceType = 3;

	/**
	 * 新的 添加客户详情
	 * 
	 * @author caizhigang
	 */
	@ResponseBody
	@RequestMapping(value = "/customer/saveCustomerProfile.json", method = RequestMethod.POST)
	public Map<String, Object> saveCustomerProfile(HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		String  requestJson = getJsonParamStr(request);;
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		User userBasic = null;
		if (requestJson != null && !"".equals(requestJson)) {
			try {
				JSONObject jo = JSONObject.fromObject(requestJson);
				// android端多传的属性
				jo.remove("relevance");
				jo.remove("directory");
				jo.remove("lableList");
				jo.remove("customerPermissions");
				jo.remove("friends");
				jo.remove("tagList");
				jo.remove("directory");
				jo.remove("associateList");
				userBasic = getUser(request);

				System.out.println("requestJson:" + requestJson);
				ObjectMapper objectMapper = new ObjectMapper();
				Customer customer = objectMapper.readValue(jo.toString(),
						Customer.class);
				Customer oldCustomer = customerService
						.findOne(customer.getId());
				if (oldCustomer != null
						&& userBasic.getId() != oldCustomer.getCreateById()) {// 修改
					setSessionAndErr(request, response, "-1", "您没有权限进行此操作");
					return returnFailMSGNew("01", "您没有权限进行此操作");
				}
				if (isNullOrEmpty(customer.getName())) {
					setSessionAndErr(request, response, "-1", "客户名称必须填写");
					return returnFailMSGNew("01", "客户名称必须填写");
				}
				if (isNullOrEmpty(customer.getPicLogo())) {
					customer.setPicLogo(Constants.ORGAN_DEFAULT_PIC_PATH);
				}
				customer.setCreateById(userBasic.getId());
				customer.setVirtual("0");
				customer.setAuth(-1);// 客户都是未进行认证的。
				customer.setUtime(DateFunc.getDate());
				// TODO
				// 关联json r:事件,p:人脉,o:组织,k:知识 type:1 需求 2 人脉 3 全平台普通用户 4
				// 组织(全平台组织用户) 5 客户 6 知识
				// {"r":[{"tag":"33","conn":[{"type":1,"id":2111,"title":"d d d d","ownerid":1,"ownername":"的的的的的的的","requirementtype":"tzxq"}]}],
				// "p":[{"tag":"111","conn":[{"type":2,"id":"141527672992500079","name":"五小六","ownerid":1,"ownername":"","caree":"","company":""}]}],
				// "o":[{"tag":"22","conn":[{"type":5,"id":618,"name":"我的测试客户","ownerid":"","ownername":"","address":"","hy":","},{"type":5,"id":617,"name":" 中国平安","ownerid":"","ownername":"","address":"","hy":",保险公司,"}]}],
				// "k":[{"tag":"44","conn":[{"type":5,"id":618,"title":"测试知识","ownerid":"","ownername":"","columntype":"","columnpath":""]}
				// String relevance=JsonUtil.getJsonNode(requestJson,
				// "relevance").toString();
				// if(StringUtils.isBlank(relevance) ||
				// StringUtils.equals(relevance, "{}")){
				// relevance="{\"r\":[],\"p\":[],\"o\":[],\"k\":[]}";
				// }
				// 目录
				// [2,3,4]
				// String directory=JsonUtil.getJsonNode(requestJson,
				// "directory").toString();
				// if(StringUtils.isBlank(directory)){
				// directory="[]";
				// }
				// 标签
				// [{"tagId":1,"tagName":"飞凤舞"}]
				// String tagJson=JsonUtil.getJsonNode(requestJson,
				// "lableList").toString();
				// if(StringUtils.isBlank(tagJson)){
				// tagJson="[]";
				// }

				// //标签转成CustomerTag对象
				// List<CustomerTag> tagList= new ArrayList<CustomerTag>();
				// if(StringUtils.isNotBlank(tagJson)){
				// tagList = objectMapper.readValue(tagJson,new
				// TypeReference<List<CustomerTag>>() {});
				// }

				// customer.setLableList(tagList);

				if (oldCustomer != null) {
					customer.setCustomerId(oldCustomer.getCustomerId());
				}

				boolean isAdd = false; // 是否增加
				if (customer.getCustomerId() == 0) {

					customer = customerService.addCustomerData(customer);
					responseDataMap.put("success", true);
					responseDataMap.put("msg", "操作成功");
					isAdd = true;
					System.out
							.println("增加客户" + customer.getCustomerId() + "成功");
				} else {

					PermissionQuery p = new PermissionQuery();
					p.setUserId(userBasic.getId());
					p.setResId(customer.getCustomerId());
					p.setResType((short) sourceType);
					InterfaceResult interfaceReslut = customerService
							.updateCustomerData(customer, p);
					System.out.println("interfaceReslut:" + interfaceReslut);
					System.out.println("responseData:"
							+ interfaceReslut.getResponseData());

					Notification notification = interfaceReslut
							.getNotification();
					if (notification.getNotifCode().equals("0")) {

						responseDataMap.put("success", true);
						responseDataMap.put("msg", "操作成功");
						System.out.println("修改客户:" + customer.getCustomerId()
								+ "成功");

					} else {

						responseDataMap.put("success", false);
						responseDataMap.put("msg", notification.getNotifInfo());
						return responseDataMap;
					}

				}

				System.out.println("客户e:" + customer.getCustomerId());
				jo = JSONObject.fromObject(requestJson);

				// 保存权限
				JSONObject customerPermissions = jo
						.getJSONObject("customerPermissions");
				if (customerPermissions != null) {

					System.out.println("customerPermissions:"
							+ customerPermissions);
					Permission per = new Permission();
					if (!isAdd) {
						System.out.println("查找权限：资源ID"
								+ customer.getCustomerId());
						per = permissionRepositoryService.selectByRes(
								customer.getCustomerId(), ResourceType.ORG)
								.getResponseData();
					}
					per.setResOwnerId(userBasic.getId());// 资源所有者id
					per.setPublicFlag(customerPermissions.getInt("pubicFlag"));// 公开-1，私密-0
					per.setShareFlag(customerPermissions.getInt("shareFlag"));// 可分享-1,不可分享-0
					per.setConnectFlag(customerPermissions
							.getInt("connectFlag"));// 可对接-1,不可对接-0
					per.setResType(ResourceType.ORG.getVal());// 资源类型
					per.setResId(customer.getCustomerId());// 资源id
					if (isAdd) {
						InterfaceResult<Boolean> result = permissionRepositoryService
								.insert(per);
					} else {
						permissionRepositoryService.update(per);
					}

				}
//
//				// // 保存目录
//				JSONArray directoryArry = jo.getJSONArray("directory");
//
//				Directory directory = new Directory();
//				directory.setAppId(appId);
//				directory.setName("蔡志刚" + System.currentTimeMillis());
//				directory.setUserId(userBasic.getId());
//				directory.setTypeId(3);
//
//				long directoryId = directoryService.createDirectoryForRoot(
//						(long) sourceType, directory);
//				System.out.println("创建目录Id为" + directoryId);
//
//				if (!isAdd) {// 如果不是新增资源先删除原来的目录
//					directorySourceService.removeDirectorySourcesBySourceId(
//							userBasic.getId(), (long) 1, (int) sourceType,
//							customer.getCustomerId());
//				}
//
//				for (int i = 0; i < directoryArry.size(); i++) {
//					DirectorySource directorySource = new DirectorySource();
//					directorySource = new DirectorySource();
//					directorySource.setDirectoryId(directoryArry.getLong(i));
//					directorySource.setAppId(appId);
//					directorySource.setUserId(userBasic.getId());
//					directorySource.setSourceId(customer.getCustomerId());
//					directorySource.setSourceType((int) sourceType);
//					directorySource.setCreateAt(System.currentTimeMillis());
//
//					directorySourceService
//							.createDirectorySources(directorySource);
//				}
//
//				// 保存标签
//				JSONArray tagArry = jo.getJSONArray("tagList");
//				Tag tag = new Tag();
//				tag.setAppId(appId);
//				tag.setTagName("蔡志刚:  " + System.currentTimeMillis());
//				tag.setTagType(sourceType);
//				long tagId = tagService.createTag(userBasic.getId(), tag);
//
//				System.out.println("创建标签为:  " + tagId);
//
//				if (!isAdd) {// 如果不是新增客户 先删除原来的标签
//					tagSourceService.removeTagSource((long) 1,
//							userBasic.getId(), customer.getId());
//				}
//
//				if (tagArry != null && tagArry.size() > 0) {// 添加标签
//					System.out.println("tagArry:" + tagArry);
//					for (int i = 0; i < tagArry.size(); i++) {
//						TagSource tagSource = new TagSource();
//						tagSource.setTagId(tagId);
//						tagSource.setAppId(appId);
//						tagSource.setUserId(userBasic.getId());
//						tagSource.setSourceId(customer.getCustomerId());
//						tagSource.setSourceType(sourceType);
//						tagSource.setCreateAt(System.currentTimeMillis());
//						tagSourceService.createTagSource(tagSource);
//					}
//				}
//
//				// 保存关联
//				if (!isAdd) {// 如果不是增加先删除原来关联关系
//
//					List<Associate> associateList = associateService
//							.getAssociatesBySourceId(appId,
//									userBasic.getId(),
//									customer.getCustomerId());
//					for (Associate associate : associateList) {
//						associateService.removeAssociate(appId,
//								userBasic.getId(), associate.getId());
//					}
//				}
//				JSONArray associateArray = jo.getJSONArray("associateList");
//				System.out.println("associateList:" + associateArray);
//				for (int i = 0; i < associateArray.size(); i++) {
//
//					JSONObject associateJsonObject = (JSONObject) associateArray
//							.opt(i);
//					Associate associate = new Associate();
//					associate.setUserId(userBasic.getId());
//					associate.setAppId(1);
//					associate.setSourceTypeId(sourceType);
//					associate.setSourceId(customer.getCustomerId());
//					associate.setAssocDesc(associateJsonObject
//							.has("assoc_desc") ? associateJsonObject
//							.getString("assoc_desc") : null);
//					associate.setAssocTypeId(associateJsonObject
//							.has("assoc_type_id") ? associateJsonObject
//							.getLong("assoc_type_id") : null);
//					associate
//							.setAssocId(associateJsonObject.has("associd") ? associateJsonObject
//									.getLong("associd") : null);
//					associate.setAssocTitle(associateJsonObject
//							.has("assoc_title") ? associateJsonObject
//							.getString("assoc_title") : null);
//					associate.setCreateAt(System.currentTimeMillis());
//					associateService.createAssociate(appId,
//							userBasic.getId(), associate);
//				}
				// 生成动态
				// saveCustomerDynamicNews(user,customer,customerPermissions.toString());

			} catch (Exception e) {
				setSessionAndErr(request, response, "-1", "系统异常,请稍后再试");
				e.printStackTrace();
				return returnFailMSGNew("01", "系统异常,请稍后再试");
			}
		} else {
			setSessionAndErr(request, response, "-1", "非法操作！");
			return returnFailMSGNew("01", "非法操作！");
		}

		return returnSuccessMSG(responseDataMap);
	}
	
	
	
	 /**
     * 查询客户详情(新客户详情)
     * @param customerId 查询考客户详情
     * @return
	 * @throws Exception 
	 * @author caizhigang
     */
	@ResponseBody
    @RequestMapping(value = "/customer/findCustomerByCustomerId.json", method = RequestMethod.POST)
	public Map<String, Object> findCustomerByCustomerId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestJson = getJsonParamStr(request);
    	Map<String, Object> responseData = new HashMap<String, Object>();
    	System.out.println("json:"+requestJson);
    	JSONObject j = JSONObject.fromObject(requestJson);
    	long customerId=JsonUtil.getNodeToLong(j, "customerId");
    	String view=JsonUtil.getNodeToString(j, "view");  //如果从转发中进入客户详情，前端app传入view=1   2:转发到第三方,不登录查看组织详情
		CustomerProfileVoNew customer_new = new CustomerProfileVoNew();
    	Customer customer_temp = customerService.findCustomerCurrentData(customerId,"0");//组织详情基本资料
    	String sckNum ="";
    	User userBasic = null;
    	if(customer_temp!= null){
    		sckNum = customer_temp.getStockNum();//证券号码
    		customer_new.setCustomerId(customer_temp.getCustomerId());
    		customer_new.setName(customer_temp.getName());
    		customer_new.setIndustry(customer_temp.getIndustry());
    		customer_new.setIndustryId(customer_temp.getIndustryId());
    		customer_new.setIsListing(customer_temp.getIsListing());
    		userBasic=getUser(request);
    		 customer_new.setLoginUserId(userBasic.getId());
    		 //新增是否收藏
    		 customer_new.setIsCollect(customerCollectService.findByUserIdAndCustomerId(userBasic.getId(), customer_temp.getCustomerId())!=null?"1":"0");
//    		 cusotmerCommonService.findCustomerAuth(view, customer_new, customer_temp,user);
    		 
    		customer_new.setStockNum(sckNum);
    		customer_new.setLinkMobile(customer_temp.getLinkMobile());
    		customer_new.setLinkEmail(customer_temp.getLinkEmail());
    		customer_new.setPicLogo("".equals(StringUtils.trimToEmpty(customer_temp.getPicLogo())) ? Constants.ORGAN_DEFAULT_PIC_PATH :
                Utils.alterImageUrl(customer_temp.getPicLogo()));
    		customer_new.setDiscribe(customer_temp.getDiscribe());
    		/*CustomerProfile cpr=customerProfileService.findOne(orgId);
    		if(cpr!=null) */
    			customer_new.setPersonalPlateList(customer_temp.getPersonalPlateList());
    		customer_new.setPropertyList(customer_temp.getPropertyList());
    		customer_new.setVirtual(customer_temp.getVirtual());
    		customer_new.setCreateById(customer_temp.getCreateById());
    		//修改组织来源 
    		customer_new.setComeId(customer_temp.getComeId());
    		customer_new.setCreateType(getUserType(customer_temp.getCreateById())==true?"2":"1");
    		
    		customer_new.setDirectory(customer_temp.getDirectory());
    		
//    		customer_new.setLableList(rCustomerTagService.getTagListByCustomerId(customerId));
//    		cusotmerCommonService.findFourModule(responseData, customer_temp,rpe.getNginxRoot());
    		customer_new.setIndustryObj(customer_temp.getIndustryObj());
    		
          
            customer_new.setOrgType(customer_temp.getOrgType());
            customer_new.setAreaid(customer_temp.getAreaid());
            customer_new.setAreaString(customer_temp.getAreaString());
            customer_new.setAddress(customer_temp.getAddress());
    		customer_new.setLinkManName(customer_temp.getLinkManName());
            
    		customer_new.setId(customer_temp.getId());
    		
    		// 设置模板ID
    		customer_new.setTemplateId(customer_temp.getTemplateId());
    		// 设置模块
    		
    		customer_new.setMoudles(customer_temp.getMoudles());
    		
//    		permissionRepositoryService.selectByRes(customer_temp.getCustomerId(), ResourceType.);
    		
//    		List<TagSource> tagSources=tagSourceService.getTagSourcesByAppIdSourceIdSourceType(appId, customer_temp.getCustomerId(), sourceType);
//    		List<Map<String,String>> tagList=new ArrayList<Map<String,String>>();
//    		
//    		for(TagSource tagSource:tagSources){
//    			Map map=new HashMap<String,String>();
//    			map.put("name", tagSource.getTagName());
//    			map.put("id", tagSource.getId());
//    		}
    		
//    		customer_new.setTagList(tagList); // 设置标签
//    		
//    		
//    		 List<DirectorySource>  directorySources=directorySourceService.getDirectorySourcesBySourceId(user.getId(), appId, (int)sourceType, customer_temp.getCustomerId());
//    		 
//    		 for(DirectorySource directorySource:directorySources){
//    			 
//    		 }
//    		 
    		 
//    		 //设置权限
//    		InterfaceResult<Permission> permissionInterfaceResult= permissionRepositoryService.selectByRes(customer_temp.getCustomerId(), ResourceType.ORG);
//    		Permission permission=permissionInterfaceResult.getResponseData();
//    		if(permissionInterfaceResult.getNotification().getNotifCode().equals("0")){
//    			
//    			Map permissionMap=new HashMap();
//    			permissionMap.put("publicFlag", permission.getPublicFlag());
//    			permissionMap.put("shareFlag", permission.getShareFlag());
//    			permissionMap.put("connectFlag", permission.getConnectFlag());
//    			customer_new.setCustomerPermissions(permissionMap);
//    		}
//    		
//    		 associateService.getAssociatesBy(appId, sourceType, customer_temp.getCustomerId());
//    		
    		
    		
    		 System.out.println(customer_temp);
	    	responseData.put("customer", customer_new);
	    	try{
    			customerCountService.updateCustomerCount(com.ginkgocap.ywxt.organ.model.Constants.customerCountType.read.getType(), customerId);
    		}catch(Exception e){
    			logger.error("插入组织数据统计功能报错,请求参数json: ",e);
    		}
	    	
    	}else{
    		setSessionAndErr(request, response, "-1", "客户不存在!");
    		return returnFailMSGNew("01", "客户不存在!");
    	}
		return returnSuccessMSG(responseData);
  }
	
	// 账号是否是组织 0:个人 1:组织
	public boolean getUserType(long userId) {
		
		/*UserLoginRegister userLoginRegister;
		try {
			userLoginRegister = userLoginRegisterService.getUserLoginRegister(userId);
			if (userLoginRegister!=null) {
				return userLoginRegister.getUsetType()==1;
			}
		} catch (UserLoginRegisterServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		return false;
	}
	
	
	
	 /**
     * 查询客户详情(大数据客户)
     * @param customerId 查询考客户详情
     * @return
	 * @throws Exception 
	 * @author zbb
     */
	@ResponseBody
    @RequestMapping(value = "/customer/findDigDataByCustomerId.json", method = RequestMethod.POST)
	public Map<String, Object> findDigDataByCustomerId(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestJson = getJsonParamStr(request);
    	Map<String, Object> responseData = new HashMap<String, Object>();
    	System.out.println("json:"+requestJson);
    	JSONObject j = JSONObject.fromObject(requestJson);
    	long customerId=JsonUtil.getNodeToLong(j, "customerId");
		BigDataModel bigDataCustomer = new BigDataModel();
    	Customer customer_temp = customerService.findCustomerCurrentData(customerId,"0");//组织详情基本资料
    	if(customer_temp!= null){
    		bigDataCustomer.setId(customer_temp.getId());
    		bigDataCustomer.setName(customer_temp.getName());
    		bigDataCustomer.setCreateById(customer_temp.getCreateById());
    		bigDataCustomer.setCustomerId(customer_temp.getCustomerId());
    		bigDataCustomer.setCtime(customer_temp.getCtime());
    		bigDataCustomer.setCurrent(customer_temp.isCurrent());
    		bigDataCustomer.setVirtual(customer_temp.getVirtual());
    		bigDataCustomer.setUrl(customer_temp.getUrl());
    		bigDataCustomer.setUtime(customer_temp.getUtime());
    		bigDataCustomer.setSource(customer_temp.getSource());
    		bigDataCustomer.setTaskid(customer_temp.getTaskid());
    		bigDataCustomer.setCrawl_datetime(customer_temp.getCrawl_datetime());
    		bigDataCustomer.setRegistration_number(customer_temp.getRegistration_number());
    		bigDataCustomer.setOrganization_code(customer_temp.getOrganization_code());
    		bigDataCustomer.setCredit_code(customer_temp.getCredit_code());
    		bigDataCustomer.setOperating_state(customer_temp.getOperating_state());
    		bigDataCustomer.setCtype(customer_temp.getCtype());
    		bigDataCustomer.setSet_up_time(customer_temp.getSet_up_time());
    		bigDataCustomer.setLegal_representative(customer_temp.getLegal_representative());
    		bigDataCustomer.setRegistered_capital(customer_temp.getRegistered_capital());
    		bigDataCustomer.setBusiness_term(customer_temp.getBusiness_term());
    		bigDataCustomer.setRegistration_authority(customer_temp.getRegistration_authority());
    		bigDataCustomer.setDate_issue(customer_temp.getDate_issue());
    		bigDataCustomer.setAddress(customer_temp.getAddress());
    		bigDataCustomer.setIndustry_involved(customer_temp.getIndustry_involved());
    		bigDataCustomer.setBusiness_scope(customer_temp.getBusiness_scope());
    		bigDataCustomer.setCompany_profile(customer_temp.getCompany_profile());
    		bigDataCustomer.setComment(customer_temp.getComment());
    		bigDataCustomer.setChange(customer_temp.getChange());
    		bigDataCustomer.setInvestment(customer_temp.getInvestment());
    		bigDataCustomer.setPeople(customer_temp.getPeople());
    		bigDataCustomer.setFinfo(customer_temp.getFinfo());
    		bigDataCustomer.setReport(customer_temp.getReport());
    		bigDataCustomer.setShareholders(customer_temp.getShareholders());
	    	responseData.put("customer", bigDataCustomer);
	    	try{
    			customerCountService.updateCustomerCount(com.ginkgocap.ywxt.organ.model.Constants.customerCountType.read.getType(), customerId);
    		}catch(Exception e){
    			logger.error("插入查询大数据客户功能报错,请求参数json: ",e);
    		}
	    	
    	}else{
    		setSessionAndErr(request, response, "-1", "客户不存在!");
    		return returnFailMSGNew("01", "客户不存在!");
    	}
		return returnSuccessMSG(responseData);
  }

	
	
	/**
     * 返回 客户 数据  和 模板  切换模板时 用
     * @param customerId 查询考客户详情
	 * @author caizhigang
     */
	@ResponseBody
    @RequestMapping(value = "/customer/findTemplateAndCustomerData.json", method = RequestMethod.POST)
	public Map<String, Object> findTemplateAndCustomerData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String requestJson = getJsonParamStr(request);
    	Map<String, Object> responseData = new HashMap<String, Object>();
    	System.out.println("json:"+requestJson);
    	JSONObject j = JSONObject.fromObject(requestJson);
    	long customerId=JsonUtil.getNodeToLong(j, "customerId");
    	long templateId=JsonUtil.getNodeToLong(j, "templateId");
    	String view=JsonUtil.getNodeToString(j, "view");  //如果从转发中进入客户详情，前端app传入view=1   2:转发到第三方,不登录查看组织详情
		CustomerProfileVoNew customer_new = new CustomerProfileVoNew();
    	Customer customer_temp = customerService.findCustomerDataInTemplate(customerId, templateId);//组织详情基本资料
    	String sckNum ="";
    	User userBasic = null;
    	if(customer_temp!= null){
    		userBasic=getUser(request);
    		sckNum = customer_temp.getStockNum();//证券号码
    		customer_new.setCustomerId(customer_temp.getCustomerId());
    		customer_new.setName(customer_temp.getName());
    		customer_new.setIndustry(customer_temp.getIndustry());
    		customer_new.setIndustryId(customer_temp.getIndustryId());
    		customer_new.setIsListing(customer_temp.getIsListing());
    		 customer_new.setLoginUserId(userBasic.getId());
    		 //新增是否收藏
    		 customer_new.setIsCollect(customerCollectService.findByUserIdAndCustomerId(userBasic.getId(), customer_temp.getCustomerId())!=null?"1":"0");
//    		 cusotmerCommonService.findCustomerAuth(view, customer_new, customer_temp,user);
    		 
    		customer_new.setStockNum(sckNum);
    		customer_new.setLinkMobile(customer_temp.getLinkMobile());
    		customer_new.setLinkEmail(customer_temp.getLinkEmail());
    		customer_new.setPicLogo("".equals(StringUtils.trimToEmpty(customer_temp.getPicLogo())) ? Constants.ORGAN_DEFAULT_PIC_PATH :
                Utils.alterImageUrl(customer_temp.getPicLogo()));
    		customer_new.setDiscribe(customer_temp.getDiscribe());
    		/*CustomerProfile cpr=customerProfileService.findOne(orgId);
    		if(cpr!=null) */
    			customer_new.setPersonalPlateList(customer_temp.getPersonalPlateList());
    		customer_new.setPropertyList(customer_temp.getPropertyList());
    		customer_new.setVirtual(customer_temp.getVirtual());
    		customer_new.setCreateById(customer_temp.getCreateById());
    		//修改组织来源 
    		customer_new.setComeId(customer_temp.getComeId());
    		customer_new.setCreateType(getUserType(customer_temp.getCreateById())==true?"2":"1");
    		
    		customer_new.setDirectory(customer_temp.getDirectory());
    		
//    		customer_new.setLableList(rCustomerTagService.getTagListByCustomerId(customerId));
//    		cusotmerCommonService.findFourModule(responseData, customer_temp,rpe.getNginxRoot());
    		customer_new.setIndustryObj(customer_temp.getIndustryObj());
    		
          
            customer_new.setOrgType(customer_temp.getOrgType());
            customer_new.setAreaid(customer_temp.getAreaid());
            customer_new.setAreaString(customer_temp.getAreaString());
            customer_new.setAddress(customer_temp.getAddress());
    		customer_new.setLinkManName(customer_temp.getLinkManName());
    		customer_new.setId(customer_temp.getId());
    		
    		
    		// 设置模板ID
    		customer_new.setTemplateId(customer_temp.getTemplateId());
    		// 设置模块
    		customer_new.setMoudles(customer_temp.getMoudles());
    		
    		System.out.println(customer_temp);
	    	responseData.put("customer", customer_new);
	    	responseData.put("hasData", true);
	    	try{
    			customerCountService.updateCustomerCount(com.ginkgocap.ywxt.organ.model.Constants.customerCountType.read.getType(), customerId);
    		}catch(Exception e){
    			logger.error("插入组织数据统计功能报错,请求参数json: ",e);
    		}
	    	
    	}else{
    		responseData.put("hasData", false);
    	}
    	
    	Template template=templateService.findTemplateById(templateId);
    	responseData.put("template", template);
    	
		return returnSuccessMSG(responseData);
  }
	
	
	
	 /**
	 * 删除客户
	 * @param request
	 * @param response
	 * @author cazhigang
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/customer/deleteCustomer.json", method = RequestMethod.POST)
	public Map<String, Object> delete(HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("/customer/deleteCustomer.json");
		User userBasic = getUser(request);
		// 获取json参数串
		String requestJson = "";
		try {
			requestJson = getJsonParamStr(request);
		} catch (IOException e) {
			logger.error("参数读取异常");
		}
		System.out.println("requestJson:"+requestJson);
		// 封装 response
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		if (!isNullOrEmpty(requestJson)) {
			JSONObject j = JSONObject.fromObject(requestJson);
			if (userBasic == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
				responseDataMap.put("successs", false);
		    	responseDataMap.put("msg", "无法获取用户");
		    	System.out.println("无法获取用户");
		    	return responseDataMap;
				
			} else {
			
			    long customerId = j.getLong("customerId");
			 
			    try{
			    	
			    	 boolean isDelCustomer= simpleCustomerService.deleteByIds(customerId+"");
					    
					  PermissionQuery p=new PermissionQuery();
					  p.setResId(customerId);
					  p.setUserId(userBasic.getId());
					  p.setResType((short)3);//  3组织

					  InterfaceResult<Boolean>  interfaceResult=customerService.deleteCustomerByCustomerId(customerId, p);
					  Notification notifacation=  interfaceResult.getNotification();
					  boolean isDelCustomerDataTag=interfaceResult.getResponseData();
					  
				    	   if(isDelCustomerDataTag&&isDelCustomer) {
								  responseDataMap.put("success", true);
								  responseDataMap.put("msg", "操作成功");
								  
						    } else {
						    	  customerService.deleteById(String.valueOf(customerId));
								  responseDataMap.put("success", false);
								  responseDataMap.put("msg", "删除失败");
								
						    }
					   
			    }catch(Exception e){
			    	e.printStackTrace();
			    	
			    	responseDataMap.put("successs", false);
			    	responseDataMap.put("msg", "系统异常");
			    	return responseDataMap;
			    }
			  
			  
			 
			}

		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
			System.out.println("没有参数");
		}
	
		return responseDataMap;
	}
    
	
	
	 /**
    *
    * 定义成功返回信息
    *
    * @param successResult
    * @return
    * @author haiyan
    */
   protected Map<String, Object> returnSuccessMSG(Map<String, Object> successResult) {
	   
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
   protected Map<String, Object> returnFailMSGNew(String errRespCode, String errRespMsg) {
       Map<String, Object> result = new HashMap<String, Object>();
      

       result.put("success", false);
       result.put("msg", errRespMsg);
       return result;
   }


}
