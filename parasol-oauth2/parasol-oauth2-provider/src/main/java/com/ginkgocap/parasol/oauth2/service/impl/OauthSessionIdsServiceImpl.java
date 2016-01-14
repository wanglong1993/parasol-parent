package com.ginkgocap.parasol.oauth2.service.impl;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.ginkgocap.parasol.oauth2.model.OauthSessionIds;
import com.ginkgocap.parasol.oauth2.service.OauthSessionIdsService;

@Service("oauthCodeService")
public class OauthSessionIdsServiceImpl extends BaseService<OauthSessionIds> implements OauthSessionIdsService{
	private RandomValueStringGenerator generator = new RandomValueStringGenerator();
	private static Logger logger = Logger.getLogger(OauthSessionIdsServiceImpl.class);
	private static final String OauthCode_List_By_Code = "OauthCode_List_By_Code";
	@Override
	public boolean idInUse(String id) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void addSession(HttpSession session) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeSession(HttpSession session) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void invalidateAll(String id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String newSessionId(HttpServletRequest request, long created) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getWorkerName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getClusterId(String nodeId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getNodeId(String clusterId, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isRunning() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isStarted() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isStarting() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isStopping() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isStopped() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isFailed() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void addLifeCycleListener(Listener listener) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeLifeCycleListener(Listener listener) {
		// TODO Auto-generated method stub
		
	} 

}
