package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ginkgocap.parasol.organ.web.jetty.web.resource.ResourcePathExposer;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.Constants;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.ObjectUtils;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.RedisKeyUtils;
import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.user.service.UserService;
import com.gintong.ywxt.organization.model.OrganRegister;
import com.gintong.ywxt.organization.model.OrganRelation;
import com.gintong.ywxt.organization.service.OrganRegisterService;
import com.gintong.ywxt.organization.service.OrganRelationService;

@Controller
@RequestMapping("/organ")
public class OrganRelationController extends BaseController {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 1: 图片类型 （个人用户）
	 * 2: 图片类型 （组织用户）
	 * */
	public static enum ImageType {
		Image_User_Type(1),Image_Organ_Type(2);
		public Integer type;

		ImageType(Integer type) {
			this.type = type;
		}
	}
	
	@Resource
	private OrganRelationService organRelationService;
	@Resource
	private OrganRegisterService organRegisterService;
	@Resource
	private ResourcePathExposer rpe;
	@Resource
	private UserService userService;

	@ResponseBody
	@RequestMapping(value = "/showAccounts.json", method = RequestMethod.POST)
	public Map<String, Object> showAccounts(HttpServletRequest request, HttpServletResponse response) {

		/** 获取json参数串 */
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		List<OrganRelation> organRelations = null;
		try {
			long organId = getUser(request).getId();
			if (organId != 0) {
				organRelations = organRelationService.findManagersByOrganId(organId);
			}
		} catch (Exception e) {
			logger.error("showAccounts.json 参数读取异常");
		}
		responseDataMap.put("accounts", organRelations);
		return genRespBody(responseDataMap, null);
	}
	
	@ResponseBody
	@RequestMapping(value = "/deleteManager.json", method = RequestMethod.POST)
	public Map<String, Object> deleteManager(HttpServletRequest request, HttpServletResponse response) {

		/** 获取json参数串 */
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		boolean result = false;
		JSONObject j = null;
		try {
			j = JSONObject.fromObject(getJsonParamStr(request));
			long organId = getUser(request).getId();
			long userId = j.optLong("userId");
			if (organId != 0 && userId != 0) {
				result = organRelationService.deleteMangerByIdAndOrganId(organId, userId);
			}
			
		} catch (Exception e) {
			logger.error("deleteManager.json 参数读取异常");
		}
		responseDataMap.put("result", result);
		return genRespBody(responseDataMap, null);
	}
	
	@ResponseBody
	@RequestMapping(value = "/showManagers.json", method = RequestMethod.POST)
	public Map<String, Object> showManagers(HttpServletRequest request, HttpServletResponse response) {

		/** 获取json参数串 */
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		List<User> managers = null;
		try {
			long organId = getUser(request).getId();
			if (organId != 0) {
				Map<String,Object> obj = new HashMap<String, Object>(1);
				List<Long> userIds = organRelationService.getManagersByOrganId(organId);
				obj.put("ids", userIds);
				managers = userService.selectSimpleUsers(obj);
				dealUserImage(managers);
			}
			
		} catch (Exception e) {
			logger.error("showManagers.json 参数读取异常");
		}
		responseDataMap.put("managers", managers);
		return genRespBody(responseDataMap, null);
	}
	
	@ResponseBody
	@RequestMapping(value = "/addManager.json", method = RequestMethod.POST)
	public Map<String, Object> addManager(HttpServletRequest request, HttpServletResponse response) {

		/** 获取json参数串 */
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>(2);
		long id = 0l;
		JSONObject j = null;
		try {
			
			j = JSONObject.fromObject(getJsonParamStr(request));
			String userAccount = j.optString("userAccount");
//			String mobile = j.optString("mobile");
			long organId = getUser(request).getId();
			
			if(userAccount.length() > 0) {
				
				// FIXME 等待框架修改完善改掉 ↓
				User user = null;
				if (userAccount.indexOf("@") > 0) {
					user = userService.selectByEmail(userAccount);
				} else {
					List<User> listUser= userService.selectByMobile(userAccount);
					if(listUser!=null && listUser.size()>0){
						user = listUser.get(0);
					}else{
						user=null;
					}
					if (user == null) {
						user = userService.selectByUsername(userAccount);
					}
				}
				// FIXME ↑
				
				if (user != null
//						&& mobile.equals(user.getMobile())
						) {
					
					List<OrganRelation> organRelations = organRelationService.findManagersByOrganId(organId);

					boolean has = false;
					for (OrganRelation or : organRelations) {
						if (or.getUserId() == user.getId()) {
							has = true;
							break;
						}
					}

					if (!has) {
						Map<String, Object> params = new HashMap<String, Object>(4);
						params.put("type", "u");
						params.put("organId", organId);
						params.put("userId", user.getId());
						params.put("userAccount", userAccount);
						id = organRelationService.addManager(params);
						notificationMap.put("notifCode", 0);
						notificationMap.put("notifInfo", "添加成功!");
					} else {
						notificationMap.put("notifCode", -1);
						notificationMap.put("notifInfo", "添加失败，该管理员已经添加");
						setSessionAndErr(request, response, "-1", "添加失败，该管理员已经添加");
					}
				} else {
					notificationMap.put("notifCode", -1);
					notificationMap.put("notifInfo", "添加失败");
					setSessionAndErr(request, response, "-1", "添加失败");
				}
				
			}
			
		} catch (Exception e) {
			logger.error("addManager.json 参数读取异常");
		}
		responseDataMap.put("result", id > 0);
		return genRespBody(responseDataMap, notificationMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/authorization.json", method = RequestMethod.POST)
	public Map<String, Object> authorization(HttpServletRequest request, HttpServletResponse response) {

		/** 获取json参数串 */
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>(2);
		boolean result = false;
		JSONObject j = null;
		try {
			j = JSONObject.fromObject(getJsonParamStr(request));
			
			String authorizationAccount = j.optString("account");
			long organId = getUser(request).getId();
			String mobile = j.optString("mobile");
			long id = j.optLong("id");
			
			// FIXME 等待框架修改完善改掉 ↓
			User user = null;
			if (authorizationAccount.indexOf("@") > 0) {
				user = userService.selectByEmail(authorizationAccount);
			} else {
				List<User> listUser= userService.selectByMobile(authorizationAccount);
				if(listUser!=null && listUser.size()>0){
					user = listUser.get(0);
				}else{
					user=null;
				}
				if (user == null) {
					user = userService.selectByUsername(authorizationAccount);
				}
			}
			// FIXME ↑
			
			if (user != null
//					&& mobile.equals(user.getMobile())
					) {
					
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("id", id);
					params.put("userId", user.getId());
					params.put("authorizationAccount", authorizationAccount);
					
					result = organRelationService.authorizationManager(params);
					
					Map<String, Object> organRegisterUpdateResult = organRegisterService.updateOrganUser(organId, user.getId());
					if (organRegisterUpdateResult != null && organRegisterUpdateResult.containsKey("result")) {
						result = ((Boolean) (organRegisterUpdateResult.get("result"))) ? result : false;
						if(result) {
						notificationMap.put("notifCode", 0);
						notificationMap.put("notifInfo", "转让成功！");
						}
					} else {
						result = false;
						notificationMap.put("notifCode", -1);
						notificationMap.put("notifInfo", "转让失败,不能转让给自己！");
						setSessionAndErr(request, response, "-1", "不能转让给自己！");
					}
			} else {
				notificationMap.put("notifCode", -1);
				notificationMap.put("notifInfo", "转让失败！");
				setSessionAndErr(request, response, "-1", "转让失败！");
			}
		} catch (Exception e) {
			logger.error("authorization.json 参数读取异常");
		}
		responseDataMap.put("result", result);
		return genRespBody(responseDataMap, notificationMap);
	}
	
	@ResponseBody
	@RequestMapping(value = "/transitionUser.json", method = RequestMethod.POST)
	public Map<String, Object> transitionUser(HttpServletRequest request, HttpServletResponse response) {

		/** 获取json参数串 */
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		boolean result = false;
		try {
			User sessionUser = getUser(request);
			long sessionUserId = 0l;
			if(sessionUser != null) {
				sessionUserId = (Long)cache.getByRedis(RedisKeyUtils.getSessionIdKey(request.getHeader("sessionID"))+"_SwitchUserId");
				result = this.organSwitchToUser(sessionUserId, request);
			}
			if(result) {
				User baseUser = userService.selectByPrimaryKey(sessionUserId);
				if(baseUser != null)
				baseUser.setPicPath(getImage(baseUser.getPicPath(), ImageType.Image_User_Type.type));
				responseDataMap.put("baseUser", baseUser);
			}
			
		} catch (Exception e) {
			logger.error("transitionUser.json 参数读取异常");
		}
		responseDataMap.put("result", result);
		return genRespBody(responseDataMap, null);
	}
	
	@ResponseBody
	@RequestMapping(value = "/transitionOrganManager.json", method = RequestMethod.POST)
	public Map<String, Object> transitionOrganManager(
			HttpServletRequest request, HttpServletResponse response) {

		/** 获取json参数串 */
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		boolean result = false;
		JSONObject j = null;
		try {
			j = JSONObject.fromObject(getJsonParamStr(request));
		} catch (Exception e) {
			logger.error("transitionOrganManager.json 参数读取异常");
		}
		long organId = j.optLong("organId");
		if (organId > 0) {
			User currentUser = getUser(request);
			String sessionId = request.getHeader("sessionID");
			User user = userService.selectByPrimaryKey(organId);
			user.setSwitchUserId(currentUser.getId());
			user.setSwitchUserName(currentUser.getShortName());
			if (isWebRequest(request)) {
				if (StringUtils.isNotBlank(sessionId)) {
					cache.setByRedis(RedisKeyUtils.getSessionIdKey(sessionId), user, 60 * 60 * 24);
					cache.setByRedis(RedisKeyUtils.getSessionIdKey(sessionId)+"_SwitchUserId", currentUser.getId(),60 * 60 * 24);
				}
			} else {
				if (sessionId != null && !"null".equals(sessionId) && !"".equals(sessionId)) {
					String key = "user" + sessionId;
					cache.setByRedis(key, user, 60 * 60 * 24);
					cache.setByRedis(RedisKeyUtils.getSessionIdKey(sessionId)+"_SwitchUserId", currentUser.getId(),60 * 60 * 24);
				}
			}

				OrganRegister baseOrgan = organRegisterService
						.getOrganRegisterById(organId);
				if (baseOrgan != null) {
					if (baseOrgan.getIsSwitch() == 0) {
						baseOrgan.setIsSwitch(1); // 更新第一次切换状态
						organRegisterService.updateOrganEntity(baseOrgan);
					}
					baseOrgan.setOrganLogo(rpe.getNginxRoot()+ObjectUtils.alterImageUrl(baseOrgan.getOrganLogo()));
					
					baseOrgan.setCareIndustryIds(user.getCareIndustryIds());
					baseOrgan.setCareIndustryNames(user.getCareIndustryNames());
					
					responseDataMap.put("role",
							currentUser.getId() == baseOrgan.getUserId() ? "o" : "u");
				}
				responseDataMap.put("baseOrgan", baseOrgan);
				result = true;
		}

		responseDataMap.put("result", result);
		return genRespBody(responseDataMap, null);
	}
	
	@ResponseBody
	@RequestMapping(value = "/findMyOrgan.json", method = RequestMethod.POST)
	public Map<String, Object> findMyOrgan(HttpServletRequest request, HttpServletResponse response) {

		/** 获取json参数串 */
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		List<OrganRegister> organRegisterrs = null;
		
		try {
			long userId = this.getUser(request).getId();
			System.out.println("userId:"+userId);
			List<Long> ids = organRelationService.getOrganIdsByUserId(userId);
			System.out.println("ids:"+ids);
			organRegisterrs = organRegisterService.getOrganRegisterByIds(ids);
			System.out.println("organRegisterrs:"+organRegisterrs);
			dealOrganImageNew(organRegisterrs);
			responseDataMap.put("userId", userId);
			responseDataMap.put("ids", ids);
			
		} catch (Exception e) {
			logger.error("findMyOrgan.json 参数读取异常");
		}
		responseDataMap.put("organs", organRegisterrs);
		return genRespBody(responseDataMap, null);
	}
	
	@ResponseBody
	@RequestMapping(value = "/hasOrgans.json", method = RequestMethod.POST)
	public Map<String, Object> hasOrgans(HttpServletRequest request, HttpServletResponse response) {

		/** 获取json参数串 */
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		boolean hasOrgan = false;
		boolean hasSwitch = false;
		try {
			long userId = this.getUser(request).getId();
			List<Long> organIds = organRelationService.getOrganIdsByUserId(userId);
			hasOrgan = organIds.size() > 0;
			hasSwitch = organRegisterService.getSwitchEntityRecord(organIds);
		} catch (Exception e) {
			logger.error("findMyOrgan.json 参数读取异常");
		}
		responseDataMap.put("hasOrgan", hasOrgan);
		responseDataMap.put("hasSwitch", hasSwitch);
		return genRespBody(responseDataMap, null);
	}
	
	private String getImage(String image,int type) {
		
		if(image == null) {
			
			return rpe.getNginxRoot() + Constants.USER_DEFAULT_PIC_PATH_FAMALE;
			
		}
		
		if(image.startsWith("http") && (image.toUpperCase().endsWith("JPG") || image.toUpperCase().endsWith("JPEG"))) {
			return image;
		} else if(image.toUpperCase().endsWith("JPG")|| image.toUpperCase().endsWith("JPEG")) {
			return rpe.getNginxRoot() + image;
		} else {
			if(ImageType.Image_User_Type.type == type){
				return rpe.getNginxRoot() + Constants.USER_DEFAULT_PIC_PATH_FAMALE;
			}else{
				return rpe.getNginxRoot() + Constants.ORGAN_DEFAULT_PIC_PATH;
			}
			
		}
		
	}
	
	private void dealUserImage(List<User> user) {
		
		
		
		if(user != null &&  !user.isEmpty()) {
			for (User u : user) {
				u.setPicPath(getImage(u.getPicPath(),ImageType.Image_User_Type.type));
			}
		}
	}
	
	private void dealOrganImage(List<OrganRegister> organ) {
		if(organ != null && !organ.isEmpty()) {
			for (OrganRegister or : organ) {
				or.setOrganLogo(getImage(or.getOrganLogo(),ImageType.Image_Organ_Type.type));
			}
		}
	}
	private void dealOrganImageNew(List<OrganRegister> organ) {
		if(organ != null && !organ.isEmpty()) {
			for (OrganRegister or : organ) {
				or.setOrganLogo(rpe.getNginxRoot()+ObjectUtils.alterImageUrl(or.getOrganLogo()));
			}
		}
	}
	
}
