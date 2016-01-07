package com.ginkgocap.parasol.oauth2.service.impl;

import org.apache.log4j.Logger;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.common.util.SerializationUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.oauth2.model.OauthCode;
import com.ginkgocap.parasol.oauth2.service.OauthCodeService;

@Service("oauthCodeService")
public class OauthCodeServiceImpl extends BaseService<OauthCode> implements OauthCodeService{
	private RandomValueStringGenerator generator = new RandomValueStringGenerator();
	private static Logger logger = Logger.getLogger(OauthCodeServiceImpl.class);
	private static final String OauthCode_List_By_Code = "OauthCode_List_By_Code"; 
	@Override
	public String createAuthorizationCode(OAuth2Authentication authentication) {
		String code = generator.generate();
		OauthCode oauthCode =new OauthCode();
		oauthCode.setCode(code);
		oauthCode.setAuthentication(SerializationUtils.serialize(authentication));
		try {
			Long id=(Long) saveEntity(oauthCode);
			if(id==null || id<=0l) return null;
			return code;
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public OAuth2Authentication consumeAuthorizationCode(String code)throws InvalidGrantException {
		try {
			Long id = (Long)getMapId(OauthCode_List_By_Code,code);
			if(id==null || id<=0l)return null;
			OauthCode oauthCode=getEntity(id);
			OAuth2Authentication authentication = null;  
			if(oauthCode!=null){
		        try {  
		            authentication = SerializationUtils.deserialize(oauthCode.getAuthentication());  
		        }  
		        catch (EmptyResultDataAccessException e) {  
		            if (logger.isDebugEnabled()) {  
		            	logger.info("Failed to find OAuth2Authentication for code " + code);  
		            }  
		        } 
			}
			if(!deleteEntity(id))return null;
			return authentication;
		} catch (BaseServiceException e) {
			e.printStackTrace();
		}
		return null;
	}

}
