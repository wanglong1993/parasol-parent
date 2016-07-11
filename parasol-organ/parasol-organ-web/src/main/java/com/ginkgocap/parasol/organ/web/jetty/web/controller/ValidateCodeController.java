package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ginkgocap.parasol.organ.web.jetty.web.utils.TokenUtil;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.cache.CacheManager;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.cache.CacheType;
import com.ginkgocap.ywxt.cache.Cache;
import com.ginkgocap.ywxt.cache.CacheModule;
import com.ginkgocap.ywxt.model.MobileCode;
import com.ginkgocap.ywxt.sms.model.SMSTypeEnum;
import com.ginkgocap.ywxt.sms.service.SendMessageService;
import com.ginkgocap.ywxt.util.CustomValidateUtils;
import com.ginkgocap.ywxt.util.Function;
import com.ginkgocap.ywxt.util.LoggerHelper;
import com.ginkgocap.ywxt.util.Module;

/**
 * @Title: ValidateCodeController.java
 * @Package com.ginkgocap.ywxt.controller
 * @Description:
 * @author haiyan
 * @date 2015-5-29 下午4:00:56
 */
@Controller
@RequestMapping("organ")
public class ValidateCodeController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ValidateCodeController.class);
	@Resource
	TokenUtil tokenUtil;
	@Resource
	private CacheManager cacheManager;
	@Autowired
	private Cache cache;
	@Autowired
	private SendMessageService sendMessageService;
	
	@RequestMapping(value="/getVcode.json",method = RequestMethod.GET)
	public void identifycode(HttpServletRequest request, HttpServletResponse response) {
		// 禁止缓存
		response.reset();
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "No-cache");
		response.setDateHeader("Expires", 0);
		// 指定生成的响应是图片
		response.setContentType("image/jpeg");
		try {
			Map<String, Object> map = tokenUtil.generateValidateCode();
			BufferedImage image = (BufferedImage) map.get("image");
			String code = (String) map.get("code");
			HttpSession hs = request.getSession();
			cacheManager.add(CacheType.IDENTIFY_CODE, hs.getId(), code);
			System.out.println("code:"+code);
			ImageIO.write(image, "JPEG", response.getOutputStream());
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (Exception e) {
			logger.error("生成验证码失败 " + e);
		}
	}
	
	/***
	 * 获取找回密码时用的验证码 getMobileVCode¶
	 * 
	 * @return model
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/getMobileVCode.json", method = RequestMethod.POST)
	public Map<String, Object> getVCodeForPassword(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String requestJson = "";
		requestJson = getJsonParamStr(request);
		Map<String, Object> model = new HashMap<String, Object>();
		Map<String, Object> responseDataMap = new HashMap<String, Object>();
		Map<String, Object> notificationMap = new HashMap<String, Object>();
		if (requestJson != null && !"".equals(requestJson)) {
			JSONObject j = JSONObject.fromObject(requestJson);
			String mobile = StringUtils.trimToEmpty(j.getString("mobile"));
			boolean isMobileOK = true;
			isMobileOK = CustomValidateUtils.isMobileNew(mobile);
			String sendMsgResult = "";
			// 记录日志
			Map<String, Object> message = new HashMap<String, Object>();
		    if (isMobileOK) {
				// 注册发送的验证码
				Random random = new Random();
				String sRand = "";
				int count = 1;// 获取短信次数
				MobileCode mobileCode;// 手机验证码对象
				String key = "";
				key = cache.getCacheHelper().buildKey(CacheModule.COMMON,
							mobile);
				MobileCode value = (MobileCode) cache.get(key);
				boolean isTimeOut = true;// 获取时间是否超时
				String msg = "";
				if (value == null) {// 第一次获取手机验证码
					for (int i = 0; i < 6; i++) {
						String rand = String.valueOf(random.nextInt(10));
						sRand += rand;
					}
					mobileCode = new MobileCode();
					mobileCode.setMobile(mobile);
					mobileCode.setCode(sRand);
					mobileCode.setCount(1);
					long ctime = System.currentTimeMillis();
					mobileCode.setCtime(ctime);
					mobileCode.setRtime(ctime);
					cache.set(key, 1 * 60 * 30, mobileCode);
					msg = "您的短信验证码为" + sRand + "，有效期30分钟，请及时验证";
					long sendStart = System.currentTimeMillis();
					sendMsgResult = sendMessageService.sendMessageForAuthCode(
							SMSTypeEnum.SMS_SINGLE.getName(), mobile, msg,
							"gb2312");
					logger.info(mobile + "===============" + msg);
					message.put("mobile", mobile);
					message.put("code", sRand);
					message.put("sendMsgResult", sendMsgResult);
					message.put("duration",
							sendStart + ":" + System.currentTimeMillis());
					responseDataMap.put("mobileCode", sRand);
				} else {
					mobileCode = value;
					long rtime = mobileCode.getRtime();
					long start = mobileCode.getCtime();
					long now = System.currentTimeMillis();
					// 判断是否是30秒内的请求
					isTimeOut = now - rtime > 30 * 1000;
					sRand = mobileCode.getCode();

					mobileCode.setRtime(now);
					count = mobileCode.getCount();
					if (isTimeOut && count < 5) {
						// mobileCodeSerice.update(mobileCode);
						sRand = "";
						for (int i = 0; i < 6; i++) {
							String rand = String.valueOf(random.nextInt(10));
							sRand += rand;
						}
						mobileCode.setCode(sRand);
						count = mobileCode.getCount() + 1;
						mobileCode.setCount(count);
						cache.set(
								key,
								Long.valueOf(
										(30 * 60 * 1000 - (now - start)) / 1000)
										.intValue(), mobileCode);
						msg = "您的短信验证码为" + sRand + "，有效期30分钟，请及时验证";
						long sendStart = System.currentTimeMillis();
						sendMsgResult = sendMessageService
								.sendMessageForAuthCode(
										SMSTypeEnum.SMS_SINGLE.getName(),
										mobile, msg, "gb2312");
						logger.info(mobile + "===============" + msg);
						message.put("mobile", mobile);
						message.put("code", sRand);
						message.put("sendMsgResult", sendMsgResult);
						message.put("duration",
								sendStart + ":" + System.currentTimeMillis());
						responseDataMap.put("mobileCode", sRand);
					} else {
						count = mobileCode.getCount() + 1;
						mobileCode.setCount(count);
						cache.set(
								key,
								Long.valueOf(
										(30 * 60 * 1000 - (now - start)) / 1000)
										.intValue(), mobileCode);
						message.put("mobile", mobile);
						message.put("code", mobileCode.getCode());
						message.put("sendMsgResult", "xxx");
						message.put("duration", "0");
						responseDataMap.put("mobileCode", mobileCode.getCode());
					}
				}
				message.put("msg", "get code");
				message.put("count", count);
				message.put("isTimeOut", isTimeOut);
				message.put("msg", msg);
				responseDataMap.put("success", true);
				logger.info(LoggerHelper.buildInfoMessage(Module.REGISTER,
						Function.DETAIL, message));
			}else if (!isMobileOK){
				setSessionAndErr(request, response, "-1", "手机号格式不正确");
				return returnFailMSGNew("01", "手机号格式不正确");
			} 
			notificationMap.put("notifCode", "0001");
			notificationMap.put("notifInfo", "hello mobile app!");
			model.put("responseData", responseDataMap);
			model.put("notification", notificationMap);
		} else {
			setSessionAndErr(request, response, "-1", "非法操作！");
			return returnFailMSGNew("01", "非法操作！");
		}
		return model;

	}

}
