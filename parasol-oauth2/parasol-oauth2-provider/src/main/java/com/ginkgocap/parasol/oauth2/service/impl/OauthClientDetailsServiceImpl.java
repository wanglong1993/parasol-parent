package com.ginkgocap.parasol.oauth2.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.ClientAlreadyExistsException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.ginkgocap.parasol.common.service.exception.BaseServiceException;
import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.oauth2.exception.OauthClientDetailsServiceException;
import com.ginkgocap.parasol.oauth2.model.OauthClientDetails;
import com.ginkgocap.parasol.oauth2.model.SimpleGrantedAuthority;
import com.ginkgocap.parasol.oauth2.service.OauthClientDetailsService;

@Service("oauthClientDetailsService")
public class OauthClientDetailsServiceImpl extends BaseService<OauthClientDetails> implements OauthClientDetailsService{

	private static Logger logger = Logger.getLogger(OauthClientDetailsServiceImpl.class);
	private static final String OauthClientDetails_Map_clientId = "OauthClientDetails_Map_clientId"; 
	private static final String OauthClientDetails_List_Id = "OauthClientDetails_List_Id"; 
	private static Random random;
	private static final String CLIENT_ID = "client_id";
	private static synchronized Random getRandom(){
		if (random==null)random=new Random();
			return random;
	}
	private  String generationClientId(){
		Random random = getRandom();
		StringBuffer sfb=new StringBuffer();
		for (int i = 0; i < 10; i++) {
			String rand = String.valueOf(random.nextInt(10));
			sfb.append(rand);
		}
		return sfb.toString();
	}
	public String generationClientSecret(ClientDetails clientDetails) {
		Map<String, String> values = new LinkedHashMap<String, String>();
		if (clientDetails!=null) {
			values.put(CLIENT_ID, clientDetails.getClientId());
		}
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		}
		catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).");
		}

		try {
			byte[] bytes = digest.digest(values.toString().getBytes("UTF-8"));
			return String.format("%032x", new BigInteger(1, bytes));
		}
		catch (UnsupportedEncodingException e) {
			throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).");
		}
	}
	@Override
	public ClientDetails loadClientByClientId(String clientId)throws ClientRegistrationException {
		try {
			if(StringUtils.isEmpty(clientId)) throw new ClientRegistrationException("clientId is null or empty.");
			Long id =(Long)getMapId(OauthClientDetails_Map_clientId,clientId);
			if(id!=null && id>0l){	
				OauthClientDetails oauthClientDetails=getEntity(id);
				if(oauthClientDetails!=null){
					oauthClientDetails.setScope(org.springframework.util.StringUtils.commaDelimitedListToSet(oauthClientDetails.getScope_()));
					oauthClientDetails.setResourceIds(org.springframework.util.StringUtils.commaDelimitedListToSet(oauthClientDetails.getResourceIds_()));
					oauthClientDetails.setAuthorizedGrantTypes(org.springframework.util.StringUtils.commaDelimitedListToSet(oauthClientDetails.getAuthorizedGrantTypes_()));
					oauthClientDetails.setRegisteredRedirectUri(org.springframework.util.StringUtils.commaDelimitedListToSet(oauthClientDetails.getRegisteredRedirectUris_()));
					Collection<GrantedAuthority> authorities =new ArrayList<GrantedAuthority>();
					Collection<String> list =org.springframework.util.StringUtils.commaDelimitedListToSet(oauthClientDetails.getAuthorities_());
					for (String authoritie : list) {
						authorities.add(new SimpleGrantedAuthority(authoritie));
					}
					oauthClientDetails.setAuthorities(authorities);
					oauthClientDetails.setAutoApproveScopes(org.springframework.util.StringUtils.commaDelimitedListToSet(oauthClientDetails.getAutoapprove()));
					return oauthClientDetails;
				} else { 
					throw new NoSuchClientException("No client with requested id: " + clientId);
				}
			} else {
				throw new NoSuchClientException("No client with requested id: " + clientId);
			}
		}catch (BaseServiceException e) {
			if (logger.isDebugEnabled()){
				e.printStackTrace(System.err);
			}
			throw new ClientRegistrationException(e.getMessage());
		}
	}

	public void addClientDetails(ClientDetails clientDetails)throws ClientAlreadyExistsException {
		try {
			if(clientDetails==null) throw new ClientAlreadyExistsException("clientDetails is null.");
			OauthClientDetails oauthClientDetails=(OauthClientDetails)clientDetails;
			oauthClientDetails.setClientId(generationClientId());
			oauthClientDetails.setClientSecret(generationClientSecret(clientDetails));
			if(StringUtils.isEmpty(oauthClientDetails.getResourceIds_())) throw new ClientAlreadyExistsException("resourceIds is null.");
			if(StringUtils.isEmpty(oauthClientDetails.getAuthorizedGrantTypes_())) throw new ClientAlreadyExistsException("authorizedGrantTypes is null.");
			if(StringUtils.isEmpty(oauthClientDetails.getRegisteredRedirectUris_())) throw new ClientAlreadyExistsException("registeredRedirectUris is null.");
			if(StringUtils.isEmpty(oauthClientDetails.getAuthorities_())) throw new ClientAlreadyExistsException("authorities is null.");
			if(StringUtils.isEmpty(oauthClientDetails.getScope_())) throw new ClientAlreadyExistsException("scope is null.");
			if(StringUtils.isEmpty(oauthClientDetails.getScope_())) throw new ClientAlreadyExistsException("scope is null.");
			if(oauthClientDetails.getUserId()<=0l) throw new ClientAlreadyExistsException("userId is null or empty.");
			if(oauthClientDetails.getStatus().intValue()!=1)oauthClientDetails.setStatus(new Byte("1"));
			if(oauthClientDetails.getType().intValue()!=1 && oauthClientDetails.getType().intValue()!=2 )throw new ClientAlreadyExistsException("type must be 1 or 2.");
			if(oauthClientDetails.getLogoId()<=0l)throw new ClientAlreadyExistsException("logoId is null or empty.");
			if(StringUtils.isEmpty(oauthClientDetails.getCompanyName())) throw new ClientAlreadyExistsException("companyName is null.");
			if(StringUtils.isEmpty(oauthClientDetails.getApplicationName())) throw new ClientAlreadyExistsException("applicationName is null.");
			if(oauthClientDetails.getCtime()==null || oauthClientDetails.getCtime()<=0l)oauthClientDetails.setCtime(System.currentTimeMillis());
			if(oauthClientDetails.getUtime()==null || oauthClientDetails.getUtime()<=0l)oauthClientDetails.setUtime(System.currentTimeMillis());
			Object obj=saveEntity(oauthClientDetails);
			if(ObjectUtils.isEmpty(obj))throw new ClientAlreadyExistsException("createOauthClientDetails failed.");
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new ClientAlreadyExistsException(e.getMessage());
		}
	}

	@Override
	public void updateClientDetails(ClientDetails clientDetails)throws NoSuchClientException {
		try {
			if(clientDetails==null) throw new NoSuchClientException("clientDetails is null.");
			OauthClientDetails oauthClientDetails=(OauthClientDetails)clientDetails;
			oauthClientDetails.setClientId(generationClientId());
			oauthClientDetails.setClientSecret(generationClientSecret(clientDetails));
			if(StringUtils.isEmpty(oauthClientDetails.getResourceIds_())) throw new NoSuchClientException("resourceIds is null.");
			if(StringUtils.isEmpty(oauthClientDetails.getAuthorizedGrantTypes_())) throw new NoSuchClientException("authorizedGrantTypes is null.");
			if(StringUtils.isEmpty(oauthClientDetails.getRegisteredRedirectUris_())) throw new NoSuchClientException("registeredRedirectUris is null.");
			if(StringUtils.isEmpty(oauthClientDetails.getAuthorities_())) throw new NoSuchClientException("authorities is null.");
			if(StringUtils.isEmpty(oauthClientDetails.getScope_())) throw new NoSuchClientException("scope is null.");
			if(StringUtils.isEmpty(oauthClientDetails.getScope_())) throw new NoSuchClientException("scope is null.");
			if(oauthClientDetails.getUserId()<=0l) throw new NoSuchClientException("userId is null or empty.");
			if(oauthClientDetails.getStatus().intValue()!=1)oauthClientDetails.setStatus(new Byte("1"));
			if(oauthClientDetails.getType().intValue()!=1 && oauthClientDetails.getType().intValue()!=2 )throw new NoSuchClientException("type must be 1 or 2.");
			if(oauthClientDetails.getLogoId()<=0l)throw new NoSuchClientException("logoId is null or empty.");
			if(StringUtils.isEmpty(oauthClientDetails.getCompanyName())) throw new NoSuchClientException("companyName is null.");
			if(StringUtils.isEmpty(oauthClientDetails.getApplicationName())) throw new NoSuchClientException("applicationName is null.");
			if(oauthClientDetails.getCtime()==null || oauthClientDetails.getCtime()<=0l)oauthClientDetails.setCtime(System.currentTimeMillis());
			if(oauthClientDetails.getUtime()==null || oauthClientDetails.getUtime()<=0l)oauthClientDetails.setUtime(System.currentTimeMillis());
			Object obj=updateEntity(oauthClientDetails);
			if(ObjectUtils.isEmpty(obj))throw new NoSuchClientException("updateOauthClientDetails failed.");
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new NoSuchClientException(e.getMessage());
		}
	}

	@Override
	public void updateClientSecret(String clientId, String secret)throws NoSuchClientException {
		try{
			if(StringUtils.isEmpty(clientId)) throw new NoSuchClientException("clientId is null or empty.");
			if(StringUtils.isBlank(secret))throw new NoSuchClientException("secret is null or empty.");
			Long id =(Long)getMapId(OauthClientDetails_Map_clientId,clientId);
			if(id==null || id<=0l)throw new NoSuchClientException("clientId is not exists.");
			OauthClientDetails oauthClientDetails=getEntity(id);
			if(oauthClientDetails==null)throw new NoSuchClientException("clientId is not exists.");
			oauthClientDetails.setClientSecret(secret);
			oauthClientDetails.setUtime(System.currentTimeMillis());
			Object obj=updateEntity(oauthClientDetails);
			if(ObjectUtils.isEmpty(obj))throw new NoSuchClientException("updateOauthClientDetails failed.");
		}catch(BaseServiceException e){
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new NoSuchClientException(e.getMessage());
		}
	}

	@Override
	public void removeClientDetails(String clientId)throws NoSuchClientException {
		try{
			if(StringUtils.isBlank(clientId))throw new NoSuchClientException("clientId is null or empty.");
			Long id =(Long)getMapId(OauthClientDetails_Map_clientId,clientId);
			deleteEntity(id);
		}catch(BaseServiceException e){
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new NoSuchClientException(e.getMessage());
		}
	}

	@Override
	public List<ClientDetails> listClientDetails(){
		try{
			List<Long> ids =getIds(OauthClientDetails_List_Id,new Object[]{1});
			List<ClientDetails> list2=new ArrayList<ClientDetails>();
			List<OauthClientDetails> list =getEntityByIds(ids);
			for (OauthClientDetails oauthClientDetails : list) {
				if(oauthClientDetails!=null){
					oauthClientDetails.setScope(org.springframework.util.StringUtils.commaDelimitedListToSet(oauthClientDetails.getScope_()));
					oauthClientDetails.setResourceIds(org.springframework.util.StringUtils.commaDelimitedListToSet(oauthClientDetails.getResourceIds_()));
					oauthClientDetails.setAuthorizedGrantTypes(org.springframework.util.StringUtils.commaDelimitedListToSet(oauthClientDetails.getAuthorizedGrantTypes_()));
					oauthClientDetails.setRegisteredRedirectUri(org.springframework.util.StringUtils.commaDelimitedListToSet(oauthClientDetails.getRegisteredRedirectUris_()));
					Collection<GrantedAuthority> authorities =new ArrayList<GrantedAuthority>();
					Collection<String> list3 =org.springframework.util.StringUtils.commaDelimitedListToSet(oauthClientDetails.getAuthorities_());
					for (String authoritie : list3) {
						authorities.add(new SimpleGrantedAuthority(authoritie));
					}
					oauthClientDetails.setAuthorities(authorities);
					oauthClientDetails.setAutoApproveScopes(org.springframework.util.StringUtils.commaDelimitedListToSet(oauthClientDetails.getAutoapprove()));
					list2.add(oauthClientDetails);
				}
			}
			return list2;
		}catch(BaseServiceException e){
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new NoSuchClientException(e.getMessage());
		}
	}
	@Override
	public List<OauthClientDetails> listClientDetails(int start, int count)throws OauthClientDetailsServiceException {
		try {
			List<Long> ids =getIds(OauthClientDetails_List_Id,start,count,new Object[]{1});
			return getEntityByIds(ids);
		} catch (BaseServiceException e) {
			if (logger.isDebugEnabled()) {
				e.printStackTrace(System.err);
			}
			throw new OauthClientDetailsServiceException(e);
		}
	}

}
