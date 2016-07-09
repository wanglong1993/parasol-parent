package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jersey.repackaged.com.google.common.collect.Maps;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.ginkgocap.ywxt.knowledge.util.Constants;
import com.ginkgocap.ywxt.user.model.User;
import com.gintong.ywxt.organization.model.OrganUserLog;
import com.gintong.ywxt.organization.service.OrganUserLogService;

@Controller
@RequestMapping("/organ")
public class OrganUserLogController extends BaseController {

	public static final Logger logger = LoggerFactory.getLogger(OrganUserLogController.class);
	@Autowired
	public OrganUserLogService organUserLogService;

	/**
	 * 添加组织操作日志信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/saveUserLog.json", method = RequestMethod.POST)
	public Map<String, Object> saveUserLog(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("添加组织操作日志信息");
		Map<String, Object> result = Maps.newHashMap();
		try {
			String requestJson = getJsonParamStr(request);
			if (StringUtils.isNotBlank(requestJson)) {
				OrganUserLog userLog = JSON.parseObject(requestJson, OrganUserLog.class);
				result = organUserLogService.insertUserLog(userLog);
			}
		} catch (Exception e) {
			logger.error("系统异常,请稍后再试", e);
			return returnFailMSGNew("01", "系统异常,请稍后再试");
		}

		return genRespBody(result, null);
	}

	/**
	 * 查询组织操作日志信息
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value = "/queryUserLog.json", method = RequestMethod.POST)
	public Map<String, Object> queryUserLog(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("查询组织操作日志信息");
		Map<String, Object> result = Maps.newHashMap();
		User user = getUser(request);
		if (user == null) {
			result.put(Constants.status, Constants.ResultType.fail.v());
			result.put(Constants.errormessage, Constants.ErrorMessage.UserNotExisitInSession.c());
			result.put("result", "error");
			return genRespBody(result, null);
		}
		try {
			String requestJson = getJsonParamStr(request);
			if (StringUtils.isNotBlank(requestJson)) {
				JSONObject j = JSONObject.fromObject(requestJson);
				int index = j.optInt("index");
				int size = j.optInt("size");
				result = organUserLogService.queryUserLog(user.getId(), index, size < 500 ? size : 500);
			}
		} catch (Exception e) {
			logger.error("系统异常,请稍后再试", e);
			return returnFailMSGNew("01", "系统异常,请稍后再试");
		}

		return genRespBody(result, null);
	}
}
