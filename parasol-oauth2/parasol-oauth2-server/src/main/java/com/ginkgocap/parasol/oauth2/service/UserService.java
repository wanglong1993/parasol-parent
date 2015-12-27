package com.ginkgocap.parasol.oauth2.service;

import com.ginkgocap.parasol.oauth2.domain.dto.UserJsonDto;

import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Shengzhao Li
 */
public interface UserService extends UserDetailsService {

    UserJsonDto loadCurrentUserJsonDto();
}