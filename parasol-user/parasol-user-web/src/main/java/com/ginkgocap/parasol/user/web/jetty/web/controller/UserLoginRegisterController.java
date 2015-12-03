package com.ginkgocap.parasol.user.web.jetty.web.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;
 
/**
 * 
 */
@RestController
public class UserLoginRegisterController extends BaseControl {
	private static Logger logger = Logger.getLogger(UserLoginRegisterController.class);
	@Autowired
	private UserLoginRegisterService userLoginRegisterService;

	/**
	 * 用户注册
	 * 
	 * @param request
	 * @return
	 * @throws DirectoryServiceException
	 * @throws CodeServiceException
	 */
	@RequestMapping(path = { "/directory/directory/createDirectoryRoot" }, method = { RequestMethod.GET })
	public MappingJacksonValue createDirectoryRoot(@RequestParam(name = UserLoginRegisterController.paramenterFields, defaultValue = "") String fileds,
			@RequestParam(name = UserLoginRegisterController.paramenterDebug, defaultValue = "") String debug,
			@RequestParam(name = UserLoginRegisterController.paramenterAppId, required = true) Long appId,
			@RequestParam(name = UserLoginRegisterController.paramenterUserId, required = true) Long userId,
			@RequestParam(name = UserLoginRegisterController.paramenterName, required = true) String name,
			@RequestParam(name = UserLoginRegisterController.paramenterRootType, required = true) long rootType) throws UserLoginRegisterServiceException {
		MappingJacksonValue mappingJacksonValue = null;
		try {
		}  catch (UserLoginRegisterServiceException e) {
			throw e;
		}
	}

}
