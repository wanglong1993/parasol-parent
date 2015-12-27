package com.ginkgocap.parasol.oauth2.service;

import com.ginkgocap.parasol.oauth2.domain.dto.OauthClientDetailsDto;
import com.ginkgocap.parasol.oauth2.domain.oauth.OauthClientDetails;

import java.util.List;

/**
 * @author Shengzhao Li
 */

public interface OauthService {

    OauthClientDetails loadOauthClientDetails(String clientId);

    List<OauthClientDetailsDto> loadAllOauthClientDetailsDtos();

    void archiveOauthClientDetails(String clientId);

    OauthClientDetailsDto loadOauthClientDetailsDto(String clientId);

    void registerClientDetails(OauthClientDetailsDto formDto);
}