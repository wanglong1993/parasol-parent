package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ginkgocap.parasol.organ.web.jetty.web.model.mydata.ApproverMiNi;
import com.ginkgocap.parasol.organ.web.jetty.web.model.mydata.CommentApprover;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.CommonUtil;
import com.ginkgocap.ywxt.metadata.service.SensitiveWordService;
import com.ginkgocap.ywxt.organ.model.Customer;
import com.ginkgocap.ywxt.organ.model.evaluate.CustomerApprover;
import com.ginkgocap.ywxt.organ.model.evaluate.CustomerEvaluate;
import com.ginkgocap.ywxt.organ.service.CustomerService;
import com.ginkgocap.ywxt.organ.service.evaluate.CustomerApproveService;
import com.ginkgocap.ywxt.organ.service.evaluate.CustomerEvaluateService;
import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.user.model.UserConfig;
import com.ginkgocap.ywxt.user.service.FriendsRelationService;
import com.ginkgocap.ywxt.user.service.UserBlackService;
import com.ginkgocap.ywxt.user.service.UserConfigService;
import com.ginkgocap.ywxt.user.service.UserService;


/**
 * <p>
 * Title: OrganEvaluateController.java<／p>
 * <p>
 * Description:组织评价<／p>
 * 
 * @author wfl
 * @date 2015-3-16
 * @version 1.0
 */
@Controller
@RequestMapping("/customer")
public class OrganEvaluateController extends BaseController {

	// 日志记录对象
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserService userSerivce;
	@Autowired
	private CustomerEvaluateService customerEvaluateService;
	@Autowired
	private CustomerApproveService customerApproverService;
	@Autowired
	// 用户黑名单
	private UserBlackService userBlackService;

	@Autowired
	// 用户个人设置服务
	private UserConfigService userConfigService;
	@Autowired
	// 敏感词过滤
	private SensitiveWordService sensitiveWordService;
	@Autowired
	// 好友关系服务
	private FriendsRelationService friendsRelationService;
    @Autowired
    private CustomerService customerService;

	/** 可见权限open return 1 */
	private final int FRIEND_JURISDICTION_OPEN = 1;

	/**
	 * 获取该组织客户的评价
	 * 
	 * @param request
	 * @param response
	 * @return model
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/findEvaluate.json", method = RequestMethod.POST)
	public Map<String, Object> findEvaluate(HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("into /findEvaluate.json");

		User user = getUser(request);
		/** 获取json参数串 */
		String requestJson = "";
		try {
			requestJson = getJsonParamStr(request);
		} catch (IOException e) {
			logger.error("findEvaluate.json 参数读取异常");
		}
		/** 封装 response */
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		if (!isNullOrEmpty(requestJson)) {
			/** 登录判读 */
			if (user == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
			} else {
				JSONObject j = JSONObject.fromObject(requestJson);
				long userId = user.getId();
				long homeId = CommonUtil.optLongFromJSONObject(j, "userId");
				// 组织评价
				String type=j.getString("type");
				if (type != null) {
					responseDataMap.put("listUserComment",
							customerEvaluateService.selectCustomerEvaluate(homeId, j.optBoolean("isSelf"), userId,type));
				} 
				boolean isEvaluated = true;

				if ("1".equals(type)&&homeId != userId) {
					User homeUser = userSerivce.selectByPrimaryKey(homeId);
					if(homeUser!=null){
						if (userBlackService.isBlackRelation(userId, homeId)) {
							isEvaluated = false;
						}
						
						UserConfig uc = userConfigService.getByUserId(homeId);
						
						if (uc!=null&&uc.getEvaluateVisible()!=null &&1 ==uc.getEvaluateVisible() ) {
							
							if (!friendsRelationService.isExistFriends(userId,
									homeId)) {
								isEvaluated = false;
							}
						}
						
					}else{
						setSessionAndErr(request, response, "-1", "当前用户不存在！");
					}
				}else if("2".equals(type)){
					 Customer customer=customerService.findCustomerCurrentData(homeId, "1");
					 if(customer!=null){
						 long createHomeId=customer.getCreateById();
						 if (userBlackService.isBlackRelation(userId, createHomeId)) {
							 isEvaluated = false;
						 }
					 }else{
						 setSessionAndErr(request, response, "-1", "当前客户不存在！");
					 }
				}

				responseDataMap.put("isEvaluated", isEvaluated);
				responseDataMap.put("success", true);
			}
		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		return genRespBody(responseDataMap, null);
	}

	/**
	 * 添加评价
	 * 
	 * @param request
	 * @param response
	 * @return model
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/addEvaluate.json", method = RequestMethod.POST)
	public Map<String, Object> addEvaluate(HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("into /addEvaluate.json");
		User user = getUser(request);

		/** 获取json参数串 */
		String requestJson = "";
		try {
			requestJson = getJsonParamStr(request);
		} catch (IOException e) {
			logger.error("参数读取异常");
		}
		/** 封装 response */
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		if (!isNullOrEmpty(requestJson)) {
			/** 登录判读 */
			if (user == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
			} else {
				JSONObject j = JSONObject.fromObject(requestJson);

				long homeUserId = CommonUtil.optLongFromJSONObject(j, "userId");
				long realHomeUserId=homeUserId;
				String type=j.getString("type");
				String comment = j.optString("comment");
				if(isNullOrEmpty(comment)){
					setSessionAndErr(request, response, "-1", "评价内容不能为空！");
					return genRespBody(responseDataMap, null);
				}

				if ("1".equals(type)&&homeUserId != user.getId()) {

				    String  feedback = userSerivce.selectByPrimaryKey(homeUserId).getName()+ "不允许别人对TA进行评价，试试其他功能吧！";
					if (userBlackService.isBlackRelation(realHomeUserId,
							user.getId())) {
						setSessionAndErr(request, response, "-1", feedback);
						responseDataMap.put("success", false);
						return genRespBody(responseDataMap, null);
					}

					if("1".equals(type)){
						// 个人权限判断
						UserConfig uc = userConfigService.getByUserId(homeUserId);
						// 判断当前被查看资料用户是否设置 仅对好友展示权限 (谨记，如果为null，则为人脉)
						if (uc != null&& uc.getEvaluateVisible() !=null&&FRIEND_JURISDICTION_OPEN ==uc.getEvaluateVisible() ) {
							// 判断好友关系
							if (!friendsRelationService.isExistFriends(homeUserId,
									user.getId())) {
								setSessionAndErr(request, response, "-1", feedback);
								responseDataMap.put("success", false);
								return genRespBody(responseDataMap, null);
							}
						}
						
					}
				}else if("2".equals(type)){
					 Customer customer=customerService.findCustomerCurrentData(homeUserId, "1");
             	     realHomeUserId=customer.getCreateById();
             	     if(realHomeUserId!=user.getId()){
             	    	  String feedback=userSerivce.selectByPrimaryKey(customer.getCreateById()).getName()+"不允许别人对TA的人脉进行评价，试试其他功能吧！";
             	    	  if (userBlackService.isBlackRelation(realHomeUserId,
      							user.getId())) {
      						setSessionAndErr(request, response, "-1", feedback);
      						responseDataMap.put("success", false);
      						return genRespBody(responseDataMap, null);
      					}
             	     }
				}

				// 敏感词判断
				List<String> listword = sensitiveWordService
						.sensitiveWord(comment);

				if (listword != null && listword.size() > 0) {
					setSessionAndErr(request, response, "-1", "您输入的内容含有敏感词");
					responseDataMap.put("success", false);
					return genRespBody(responseDataMap, null);
				}

				if(!customerEvaluateService.checkUserComment(comment,homeUserId,type)){
					setSessionAndErr(request, response, "-1", "评价内容已存在！");
					responseDataMap.put("success", false);
					return genRespBody(responseDataMap, null);
				}
				
				// 组织评价
				responseDataMap.putAll(customerEvaluateService.saveCustomerEvaluate(homeUserId, comment,user.getId(),type));
			}
		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		return genRespBody(responseDataMap, null);
	}

	/**
	 * 赞同与取消赞同
	 * 
	 * @param request
	 * @param response
	 * @return model
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/feedbackEvaluate.json", method = RequestMethod.POST)
	public Map<String, Object> feedbackEvaluate(HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("into /feedbackEvaluate.json");

		User user = getUser(request);
		/** 获取json参数串 */
		String requestJson = "";
		try {
			requestJson = getJsonParamStr(request);
		} catch (IOException e) {
			logger.error("参数读取异常");
		}
		/** 封装 response */
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		if (!isNullOrEmpty(requestJson)) {
			/** 登录判读 */
			if (user == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
			} else {
				JSONObject j = JSONObject.fromObject(requestJson);
				long homeUserId = CommonUtil.optLongFromJSONObject(j, "homeUserId");
				long realHomeUserId=homeUserId;
				String type=j.getString("type");
				if ("1".equals(type)&&homeUserId != user.getId()) {
					String feedback = "对方设置了评价权限";
					if (userBlackService.isBlackRelation(realHomeUserId,
							user.getId())) {
						setSessionAndErr(request, response, "-1", feedback);
						responseDataMap.put("success", false);
						return genRespBody(responseDataMap, null);
					}
					if("1".equals(type)){//组织
					// 个人权限判断
					UserConfig uc = userConfigService.getByUserId(homeUserId);
					// 判断当前被查看资料用户是否设置 仅对好友展示权限 (谨记，如果为null，则为人脉)
					if (uc != null&& uc.getEvaluateVisible()!=null && FRIEND_JURISDICTION_OPEN ==uc.getEvaluateVisible() ) {
						// 判断好友关系
						if (!friendsRelationService.isExistFriends(homeUserId,user.getId())) {
							setSessionAndErr(request, response, "-1", feedback);
							responseDataMap.put("success", false);
							return genRespBody(responseDataMap, null);
						}
					  }
					}
					
				}else if("2".equals(type)){
						realHomeUserId=customerService.findCustomerCurrentData(homeUserId, "1").getCreateById();
					    if(realHomeUserId!=user.getId()){
					    	String feedback = "对方设置了评价权限";
							if (userBlackService.isBlackRelation(realHomeUserId,
									user.getId())) {
								setSessionAndErr(request, response, "-1", feedback);
								responseDataMap.put("success", false);
								return genRespBody(responseDataMap, null);
							}
					    }
				}
					 responseDataMap.put("success",customerEvaluateService.feedbackEvaluate(CommonUtil.optLongFromJSONObject(j, "id"), user.getId(),j.optBoolean("feedback")));
			}
		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		return genRespBody(responseDataMap, null);
	}

	/**
	 * 删除评价
	 * 
	 * @param request
	 * @param response
	 * @return model
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/deleteEvaluate.json", method = RequestMethod.POST)
	public Map<String, Object> deleteEvaluate(HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("into /deleteEvaluate.json");

		User user = getUser(request);
		/** 获取json参数串 */
		String requestJson = "";
		try {
			requestJson = getJsonParamStr(request);
		} catch (IOException e) {
			logger.error("参数读取异常");
		}
		/** 封装 response */
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		if (!isNullOrEmpty(requestJson)) {
			/** 登录判读 */
			if (user == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
			} else {
				JSONObject j = JSONObject.fromObject(requestJson);
				
					responseDataMap.put("success", customerEvaluateService
							.deleteCustomerEvaluate(CommonUtil.optLongFromJSONObject(j, "id")));
				
			}
		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		return genRespBody(responseDataMap, null);
	}

	/**
	 * 更多评价(组织)
	 * 
	 * @author wfl
	 */
	@ResponseBody
	@RequestMapping(value = "/moreOrganEvaluate.json", method = RequestMethod.POST)
	public Map<String, Object> moreOrganEvaluate(HttpServletRequest request,
			HttpServletResponse response) {

		logger.info("into /moreEvaluate.json");

		User user = getUser(request);
		/** 获取json参数串 */
		String requestJson = "";
		try {
			requestJson = getJsonParamStr(request);
		} catch (IOException e) {
			logger.error("参数读取异常");
		}
		/** 封装 response */
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		if (!isNullOrEmpty(requestJson)) {
			/** 登录判读 */
			if (user == null) {
				setSessionAndErr(request, response, "-1", "请登录以后再操作");
			} else {
				JSONObject j = JSONObject.fromObject(requestJson);
				String type=j.getString("type");
				List<CustomerEvaluate> ces = customerEvaluateService
						.selectCustomerEvaluate(CommonUtil.optLongFromJSONObject(j, "userId"), false, user.getId(),type);
				List<CommentApprover> cas = new ArrayList<CommentApprover>();

				if (ces != null && ces.size() > 0) {
					cas = new ArrayList<CommentApprover>(ces.size());
					CommentApprover ca = null;

					for (int i = 0; i < ces.size(); i++) {

						CustomerEvaluate ce = ces.get(i);

						ca = new CommentApprover();

						ca.setUeid(ce.getId());
						ca.setFeedback(ce.isEvaluateStatus());
						ca.setComment(ce.getUserComment());
						ca.setListApproverMiNi(getOrganAmn(
								customerApproverService
										.selectCustomerApproverByUEId(ce
												.getId()), request));

						cas.add(ca);
					}

				}
				// 反馈
				responseDataMap.put("success", true);
				responseDataMap.put("listCommentApprover", cas);
			}
		} else {
			setSessionAndErr(request, response, "-1", "输入参数不合法");
		}
		return genRespBody(responseDataMap, null);
	}

	// 私有拼接转化方法 组织
	private List<ApproverMiNi> getOrganAmn(List<CustomerApprover> cas,
			HttpServletRequest request) {

		List<ApproverMiNi> amns = new ArrayList<ApproverMiNi>();
		if (cas != null && cas.size() > 0) {
			amns = new ArrayList<ApproverMiNi>(cas.size());
			ApproverMiNi amn = null;

			for (int i = 0; i < cas.size(); i++) {

				CustomerApprover ca = cas.get(i);
				amn = new ApproverMiNi();
				amn.setUserId(ca.getUserId());
				User u = userSerivce.selectByPrimaryKey(amn.getUserId());
				if (u != null)

				{
					amn.setImageUrl(getUserImg(request, u.getId(), 1));
					amn.setIsonline(u.isVirtual());
				}

				amns.add(amn);
			}

		}
		return amns;
	}

	
	
}
