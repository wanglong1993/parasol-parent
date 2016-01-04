package com.ginkgocap.parasol.oauth2.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.oauth2.model.SecurityUserDetails;
import com.ginkgocap.parasol.oauth2.service.UserDetailService;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;

@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailService {

	@Autowired
	UserLoginRegisterService userLoginRegisterService;

	@Override
	public UserDetails loadUserByUsername(String username)throws UsernameNotFoundException {
		UserLoginRegister userLoginRegister = null;
		try {
			userLoginRegister = userLoginRegisterService.getUserLoginRegister(username);
		} catch (UserLoginRegisterServiceException e) {
			e.printStackTrace();
		}
		if (userLoginRegister == null) {
			throw new UsernameNotFoundException("Not found any user for username[" + username + "]");
		}

		return new SecurityUserDetails(userLoginRegister);
	}
}