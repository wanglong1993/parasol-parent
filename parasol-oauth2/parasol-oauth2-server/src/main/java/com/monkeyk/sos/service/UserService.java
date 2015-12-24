package com.monkeyk.sos.service;

import com.monkeyk.sos.domain.dto.UserJsonDto;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author Shengzhao Li
 */
public interface UserService extends UserDetailsService {

    UserJsonDto loadCurrentUserJsonDto();
}