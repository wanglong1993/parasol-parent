package com.ginkgocap.parasol.user.web.jetty.web.controller;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.ginkgocap.parasol.user.service.UserInfoOperateService;

public class UserInfoController extends BaseControl {
	private static Logger logger = Logger.getLogger(UserInfoController.class);
	@Resource
	private UserInfoOperateService userInfoOperateService;
	
	
}
