package com.ginkgocap.parasol.oauth2.service.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.oauth2.domain.dto.UserJsonDto;
import com.ginkgocap.parasol.oauth2.domain.shared.security.WdcyUserDetails;
import com.ginkgocap.parasol.oauth2.domain.user.User;
import com.ginkgocap.parasol.oauth2.domain.user.UserRepository;
import com.ginkgocap.parasol.oauth2.service.UserService;
import com.ginkgocap.parasol.user.exception.UserLoginRegisterServiceException;
import com.ginkgocap.parasol.user.model.UserLoginRegister;
import com.ginkgocap.parasol.user.service.UserLoginRegisterService;

/**
 * @author Shengzhao Li
 */
@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired 
    UserLoginRegisterService userLoginRegisterService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        try {
			UserLoginRegister userLoginRegister=userLoginRegisterService.getUserLoginRegister(username);
//			userLoginRegister.getPassword();
		} catch (UserLoginRegisterServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        if (user == null || user.archived()) {
            throw new UsernameNotFoundException("Not found any user for username[" + username + "]");
        }

        return new WdcyUserDetails(user);
    }

    @Override
    public UserJsonDto loadCurrentUserJsonDto() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Object principal = authentication.getPrincipal();

        if (authentication instanceof OAuth2Authentication &&
                (principal instanceof String || principal instanceof org.springframework.security.core.userdetails.User)) {
            return loadOauthUserJsonDto((OAuth2Authentication) authentication);
        } else {
            final WdcyUserDetails userDetails = (WdcyUserDetails) principal;
            return new UserJsonDto(userRepository.findByGuid(userDetails.user().guid()));
        }
    }


    private UserJsonDto loadOauthUserJsonDto(OAuth2Authentication oAuth2Authentication) {
        UserJsonDto userJsonDto = new UserJsonDto();
        userJsonDto.setUsername(oAuth2Authentication.getName());

        final Collection<GrantedAuthority> authorities = oAuth2Authentication.getAuthorities();
        for (GrantedAuthority authority : authorities) {
            userJsonDto.getPrivileges().add(authority.getAuthority());
        }
        return userJsonDto;
    }
}