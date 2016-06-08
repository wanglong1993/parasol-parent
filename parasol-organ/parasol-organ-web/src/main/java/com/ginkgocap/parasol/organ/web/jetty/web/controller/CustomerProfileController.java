package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import com.ginkgocap.ywxt.organ.model.Constants2;
import com.ginkgocap.ywxt.organ.model.Customer;
import com.ginkgocap.ywxt.organ.model.CustomerTag;
import com.ginkgocap.ywxt.organ.model.template.Template;
import com.ginkgocap.ywxt.organ.service.*;
import com.ginkgocap.ywxt.organ.service.notice.CustomerNoticeService;
import com.ginkgocap.ywxt.organ.service.privilege.CustomerPermissionService;
import com.ginkgocap.ywxt.organ.service.profile.CustomerProfileService;
import com.ginkgocap.ywxt.organ.service.tag.RCustomerTagService;
import com.ginkgocap.ywxt.organ.service.template.CustomerColumnService;
import com.ginkgocap.ywxt.organ.service.template.TemplateService;
import com.ginkgocap.parasol.organ.web.jetty.web.resource.ResourcePathExposer;
import com.ginkgocap.parasol.organ.web.jetty.web.service.organ.CusotmerCommonService;
import com.ginkgocap.parasol.organ.web.jetty.web.service.organ.DealCustomerConnectInfoService;
import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.user.service.FriendsRelationService;
import com.ginkgocap.ywxt.user.service.UserService;
import com.ginkgocap.ywxt.util.DateFunc;
import com.ginkgocap.ywxt.util.JsonUtil;
import  com.ginkgocap.parasol.organ.web.jetty.web.utils.Constants;
import  com.ginkgocap.parasol.organ.web.jetty.web.utils.JsonReadUtil;
import  com.ginkgocap.parasol.organ.web.jetty.web.utils.ObjectUtils;
import com.ginkgocap.parasol.organ.web.jetty.web.vo.organ.CustomerProfileVoNew;
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
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import com.ginkgocap.ywxt.user.service.DynamicNewsService;
/**
* <p>Title: CustomerProfileController.java<／p> 
* <p>Description: 客户详情controller<／p> 
* @author wfl
* @date 2015-3-12 
* @version 1.0
 */
@Controller
@RequestMapping("/customer")
public class CustomerProfileController extends BaseController{

	 private final Logger logger=LoggerFactory.getLogger(getClass());
	
	 @Resource
     private UserService userService;
	 @Resource
	 private CustomerProfileService customerProfileService;
	 @Resource
	 private CustomerColumnService customerColumnService;
	 @Resource
	 private CustomerService customerService;
	 @Resource
	 private CustomerIdService customerIdService;
	@Resource
	private CustomerNoticeService customerNoticeService;
	@Resource
	private CustomerGroupService customerGroupService;
	@Resource
	private RCustomerTagService rCustomerTagService;
	@Resource
	private CustomerPermissionService customerPermissionService;
	@Resource
	private DealCustomerConnectInfoService dealCustomerConnectInfoService;

	@Resource 
	private ResourcePathExposer rpe;
	@Resource
	private FriendsRelationService friendsRelationService;
	@Resource
	private CustomerCollectService  customerCollectService;
	@Resource
    private CustomerCountService customerCountService;
	@Autowired
	private SimpleCustomerService simpleCustomerService;
	@Autowired
	private CusotmerCommonService cusotmerCommonService;
	
	@Autowired
	private TemplateService templateService;
	


	    

	    
	    /**
	     * 新的 添加客户详情
	     * @author caizhigang
	     */
	    @ResponseBody
		@RequestMapping(value = "/saveCustomerProfile.json", method = RequestMethod.POST)
		public Map<String, Object> saveCustomerProfile(HttpServletRequest request,
				HttpServletResponse response) throws IOException {
	    	
			String requestJson = "";
			requestJson = getJsonParamStr(request);
			Map<String, Object> responseDataMap = new HashMap<String, Object>();
			if (requestJson != null && !"".equals(requestJson)) {
				try {
					JSONObject jo = JSONObject.fromObject(requestJson);
				    User user=getUser(request);
				    
				    //android端多传的属性
				    jo.remove("relevance");
				    jo.remove("directory");
				    jo.remove("lableList");
				    jo.remove("customerPermissions");
				    jo.remove("friends");
				    
				    ObjectMapper objectMapper=new ObjectMapper();
				    Customer customer=objectMapper.readValue(jo.toString(),Customer.class);
				    Customer oldCustomer = customerService.findOne(customer.getId());
				    if(oldCustomer!=null&&user.getId()!=oldCustomer.getCreateById()){//修改
				    	  setSessionAndErr(request, response, "-1", "您没有权限进行此操作");
			    	      return returnFailMSGNew("01","您没有权限进行此操作");
				    }
				    if(isNullOrEmpty(customer.getName())) {
				    	setSessionAndErr(request, response, "-1", "客户名称必须填写");
			    	    return returnFailMSGNew("01","客户名称必须填写");
				    }
				    if(isNullOrEmpty(customer.getPicLogo())){
				    	customer.setPicLogo(Constants.ORGAN_DEFAULT_PIC_PATH);
				    }
				    customer.setCreateById(user.getId());
				    customer.setVirtual("0");
				    customer.setAuth(-1);//客户都是未进行认证的。
				    customer.setUtime(DateFunc.getDate());
				    //TODO
				    //关联json r:事件,p:人脉,o:组织,k:知识 type:1 需求 2 人脉 3 全平台普通用户 4 组织(全平台组织用户) 5 客户 6 知识
				    //{"r":[{"tag":"33","conn":[{"type":1,"id":2111,"title":"d d d d","ownerid":1,"ownername":"的的的的的的的","requirementtype":"tzxq"}]}],
				    // "p":[{"tag":"111","conn":[{"type":2,"id":"141527672992500079","name":"五小六","ownerid":1,"ownername":"","caree":"","company":""}]}],
				    // "o":[{"tag":"22","conn":[{"type":5,"id":618,"name":"我的测试客户","ownerid":"","ownername":"","address":"","hy":","},{"type":5,"id":617,"name":" 中国平安","ownerid":"","ownername":"","address":"","hy":",保险公司,"}]}],
				    // "k":[{"tag":"44","conn":[{"type":5,"id":618,"title":"测试知识","ownerid":"","ownername":"","columntype":"","columnpath":""]}
				    String relevance=JsonUtil.getJsonNode(requestJson, "relevance").toString();
				    if(StringUtils.isBlank(relevance) || StringUtils.equals(relevance, "{}")){
				    	relevance="{\"r\":[],\"p\":[],\"o\":[],\"k\":[]}";
				    }
				    //目录
				    //[2,3,4]
				    String directory=JsonUtil.getJsonNode(requestJson, "directory").toString();
				    if(StringUtils.isBlank(directory)){
				    	directory="[]";
				    }
				    //标签
				    //[{"tagId":1,"tagName":"飞凤舞"}]
				    String tagJson=JsonUtil.getJsonNode(requestJson, "lableList").toString();
				    if(StringUtils.isBlank(tagJson)){
				    	tagJson="[]";
				    }
				    //权限
				    //{"dule":false,
				    //"xiaoles":[],"zhongles":[{"id":13556,"name":"张斌"},{"id":12454,"name":"林美霞"},{"id":13835,"name":"张桂珍"}],
				    //"dales":[{"id":13414,"name":"股市水晶球"},{"id":10089,"name":"杨楠"},{"id":13247,"name":"曾添_dataplayer"}]
				    //"modelType":[1,2,3],
				    //"mento":"哈哈"}
				    String customerPermissions=JsonUtil.getJsonNode(requestJson, "customerPermissions").toString();
				    if(StringUtils.isBlank(customerPermissions) || StringUtils.equals(customerPermissions, "{}")){
				    	customerPermissions="{\"dule\":true}";
				    }
				    customer.setDirectory(directory);
				    customer.setCustomerPermissions(customerPermissions);
				    customer.setRelevance(relevance);
				    //标签转成CustomerTag对象
				    List<CustomerTag> tagList= new ArrayList<CustomerTag>();
				    if(StringUtils.isNotBlank(tagJson)){
				    	tagList = objectMapper.readValue(tagJson,new TypeReference<List<CustomerTag>>() {});
				    }
				    customer.setLableList(tagList);
				    if(oldCustomer!=null){
				    	 customer.setCustomerId(oldCustomer.getCustomerId());
				    }
				   
				    //customerProfileService.saveOrUpdate(customer);
				    customerService.saveOrUpdateCustomerData(customer);
				    //生成动态
				  //  saveCustomerDynamicNews(user,customer,customerPermissions);
				    //删除冗余数据
				   //customerService.deletePartBycustomerType(customer.getId(),customer.getType());
					if(!isNullOrEmpty(relevance)){
						Map<String, Object> map =  dealCustomerConnectInfoService.insertCustomerConnectInfo(relevance, customer.getId(), user.getId());
						String result = String.valueOf(map.get(Constants2.status));//0 关联失败 1 关联成功
					}
					String groupIds="";
					if(StringUtils.isNotBlank(directory)&& !StringUtils.equals(directory, "[]")){
						JSONArray arr = JSONArray.fromObject(directory);
						groupIds = StringUtils.join(arr, ",");
					}
					customerGroupService.updateGroup(customer.getCustomerId()+"", groupIds);
					//if(!"[]".equals(tagJson)){
						rCustomerTagService.saveAll(customer.getCustomerId(), tagJson, user.getId());
					//}
					int isRecommend = 0;// 是否金桐脑推荐的 0 个人创建 1 金桐脑推荐
					int virtual = 0;//是否是组织，0客户，1组织
					if(!isNullOrEmpty(customerPermissions)){
						Map<String,Object>  permissonResult = customerPermissionService.saveAll(customerPermissions, customer.getCustomerId(), user.getId(), isRecommend, virtual);
					    logger.info("保存客户权限：客户id："+customer.getCustomerId()+",result"+JSONObject.fromObject(permissonResult).toString());
					}
				} catch (Exception e) {
					setSessionAndErr(request, response, "-1", "系统异常,请稍后再试");
					logger.error("添加客户详情报错,请求参数:{}"+requestJson,e);
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
	    @RequestMapping(value = "/findCustomerByCustomerId.json", method = RequestMethod.POST)
		public Map<String, Object> findCustomerByCustomerId(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String requestJson = JsonReadUtil.getJsonIn(request);
	    	Map<String, Object> responseData = new HashMap<String, Object>();
	    	System.out.println("json:"+requestJson);
	    	JSONObject j = JSONObject.fromObject(requestJson);
	    	long customerId=JsonUtil.getNodeToLong(j, "customerId");
	    	String view=JsonUtil.getNodeToString(j, "view");  //如果从转发中进入客户详情，前端app传入view=1   2:转发到第三方,不登录查看组织详情
			CustomerProfileVoNew customer_new = new CustomerProfileVoNew();
	    	Customer customer_temp = customerService.findCustomerCurrentData(customerId);//组织详情基本资料
	    	String sckNum ="";
	    	if(customer_temp!= null){
	    		sckNum = customer_temp.getStockNum();//证券号码
	    		customer_new.setCustomerId(customer_temp.getCustomerId());
	    		customer_new.setName(customer_temp.getName());
	    		customer_new.setIndustry(customer_temp.getIndustry());
	    		customer_new.setIndustryId(customer_temp.getIndustryId());
	    		customer_new.setIsListing(customer_temp.getIsListing());
	  			 User user =getJTNUser(request);
	    		 customer_new.setLoginUserId(user.getId());
	    		 //新增是否收藏
	    		 customer_new.setIsCollect(customerCollectService.findByUserIdAndCustomerId(user.getId(), customer_temp.getCustomerId())!=null?"1":"0");
	    		 cusotmerCommonService.findCustomerAuth(view, customer_new, customer_temp,user);
	    		 
	    		customer_new.setStockNum(sckNum);
	    		customer_new.setLinkMobile(customer_temp.getLinkMobile());
	    		customer_new.setLinkEmail(customer_temp.getLinkEmail());
	    		customer_new.setPicLogo(rpe.getNginxRoot()+ObjectUtils.alterImageUrl(customer_temp.getPicLogo()));
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
	    		
	    		customer_new.setLableList(rCustomerTagService.getTagListByCustomerId(customerId));
	    		cusotmerCommonService.findFourModule(responseData, customer_temp,rpe.getNginxRoot());
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
	    		customer_new.setMouduesPlateList(customer_temp.getMouduesPlateList());
	    		customer_new.setComplexmodule(customer_temp.getComplexmodule());
	    		
	    		// 数据类型
	    		customer_new.setDataType(customer_temp.getDataType());
	    		
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
		
		
		
		
		
		
		
		

	    /**
		 * 删除客户
		 * @param request
		 * @param response
		 * @author cazhigang
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value = "/deleteCustomer.json", method = RequestMethod.POST)
		public Map<String, Object> delete(HttpServletRequest request,
				HttpServletResponse response) {
			logger.info("/customer/deleteCustomer.json");
			User user = getUser(request);
			// 获取json参数串
			String requestJson = "";
			try {
				requestJson = getJsonParamStr(request);
			} catch (IOException e) {
				logger.error("参数读取异常");
			}
			// 封装 response
			Map<String, Object> model = new HashMap<String, Object>();
			Map<String, Object> responseDataMap = new HashMap<String, Object>();
			Map<String, Object> notificationMap = new HashMap<String, Object>();
			if (!isNullOrEmpty(requestJson)) {
				JSONObject j = JSONObject.fromObject(requestJson);
				if (user == null) {
					setSessionAndErr(request, response, "-1", "请登录以后再操作");
				} else {
				
				    long customerId = j.getLong("customerId");
				 
				   boolean isDelCustomer= simpleCustomerService.deleteByIds(customerId+"");
				    
				  boolean isDelCustomerDataTag=  customerService.deleteCustomerByCustomerId(customerId);
				    
				
				    	   if(isDelCustomerDataTag&&isDelCustomer) {
								  responseDataMap.put("success", true);
								  responseDataMap.put("msg", "操作成功");
								  notificationMap.put("notifCode", "0001");
								  notificationMap.put("notifInfo", "删除成功");
						    } else {
						    	  customerService.deleteById(String.valueOf(customerId));
								  responseDataMap.put("success", false);
								  responseDataMap.put("msg", "删除失败");
								  notificationMap.put("notifCode", "0001");
								 notificationMap.put("notifInfo", "删除失败");
						    }
				   
				}

			} else {
				setSessionAndErr(request, response, "-1", "输入参数不合法");
			}
			model.put("responseData", responseDataMap);
			model.put("notification", notificationMap);
			return model;
		}
	    
	    
	    
	    
		 /**
	     * 返回 客户 数据  和 模板  切换模板时 用
	     * @param customerId 查询考客户详情
		 * @author caizhigang
	     */
		@ResponseBody
	    @RequestMapping(value = "/findTemplateAndCustomerData.json", method = RequestMethod.POST)
		public Map<String, Object> findTemplateAndCustomerData(HttpServletRequest request, HttpServletResponse response) throws Exception {
			String requestJson = JsonReadUtil.getJsonIn(request);
	    	Map<String, Object> responseData = new HashMap<String, Object>();
	    	System.out.println("json:"+requestJson);
	    	JSONObject j = JSONObject.fromObject(requestJson);
	    	long customerId=JsonUtil.getNodeToLong(j, "customerId");
	    	long templateId=JsonUtil.getNodeToLong(j, "templateId");
	    	String view=JsonUtil.getNodeToString(j, "view");  //如果从转发中进入客户详情，前端app传入view=1   2:转发到第三方,不登录查看组织详情
			CustomerProfileVoNew customer_new = new CustomerProfileVoNew();
	    	Customer customer_temp = customerService.findCustomerDataInTemplate(customerId, templateId);//组织详情基本资料
	    	String sckNum ="";
	    	if(customer_temp!= null){
	    		sckNum = customer_temp.getStockNum();//证券号码
	    		customer_new.setCustomerId(customer_temp.getCustomerId());
	    		customer_new.setName(customer_temp.getName());
	    		customer_new.setIndustry(customer_temp.getIndustry());
	    		customer_new.setIndustryId(customer_temp.getIndustryId());
	    		customer_new.setIsListing(customer_temp.getIsListing());
	  			 User user =getJTNUser(request);
	    		 customer_new.setLoginUserId(user.getId());
	    		 //新增是否收藏
	    		 customer_new.setIsCollect(customerCollectService.findByUserIdAndCustomerId(user.getId(), customer_temp.getCustomerId())!=null?"1":"0");
	    		 cusotmerCommonService.findCustomerAuth(view, customer_new, customer_temp,user);
	    		 
	    		customer_new.setStockNum(sckNum);
	    		customer_new.setLinkMobile(customer_temp.getLinkMobile());
	    		customer_new.setLinkEmail(customer_temp.getLinkEmail());
	    		customer_new.setPicLogo(rpe.getNginxRoot()+ObjectUtils.alterImageUrl(customer_temp.getPicLogo()));
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
	    		
	    		customer_new.setLableList(rCustomerTagService.getTagListByCustomerId(customerId));
	    		cusotmerCommonService.findFourModule(responseData, customer_temp,rpe.getNginxRoot());
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
	    		customer_new.setMouduesPlateList(customer_temp.getMouduesPlateList());
	    		customer_new.setComplexmodule(customer_temp.getComplexmodule());
	    		
	    		// 数据类型
	    		customer_new.setDataType(customer_temp.getDataType());
	    		
	    		System.out.println(customer_temp);
		    	responseData.put("customer", customer_new);
		    	try{
	    			customerCountService.updateCustomerCount(com.ginkgocap.ywxt.organ.model.Constants.customerCountType.read.getType(), customerId);
	    		}catch(Exception e){
	    			logger.error("插入组织数据统计功能报错,请求参数json: ",e);
	    		}
		    	
	    	}
	    	
	    	Template template=templateService.findTemplateById(templateId);
	    	responseData.put("template", template);
	    	
			return returnSuccessMSG(responseData);
	  }
		
		
		
		
		
}
