package com.ginkgocap.parasol.oauth2.domain.oauth;

import com.ginkgocap.parasol.oauth2.domain.shared.Repository;

import java.util.List;

/**
 * @author Shengzhao Li
 */
public interface OauthRepository extends Repository {

    OauthClientDetails findOauthClientDetails(String clientId);

    List<OauthClientDetails> findAllOauthClientDetails();

    void updateOauthClientDetailsArchive(String clientId, boolean archive);

    void saveOauthClientDetails(OauthClientDetails clientDetails);
}