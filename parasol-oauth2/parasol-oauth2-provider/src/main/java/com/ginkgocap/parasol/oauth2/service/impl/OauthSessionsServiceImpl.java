package com.ginkgocap.parasol.oauth2.service.impl;

import java.util.EventListener;
import java.util.Set;

import javax.servlet.SessionCookieConfig;
import javax.servlet.SessionTrackingMode;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.eclipse.jetty.http.HttpCookie;
import org.eclipse.jetty.server.SessionIdManager;
import org.eclipse.jetty.server.session.SessionHandler;
import org.springframework.stereotype.Service;

import com.ginkgocap.parasol.common.service.impl.BaseService;
import com.ginkgocap.parasol.oauth2.model.OauthSessions;
import com.ginkgocap.parasol.oauth2.service.OauthSessionsService;

@Service("oauthSessionsService")
public class OauthSessionsServiceImpl extends BaseService<OauthSessions> implements OauthSessionsService{
	private static Logger logger = Logger.getLogger(OauthSessionsServiceImpl.class);
	private static final String OauthCode_List_By_Code = "OauthCode_List_By_Code";
	@Override
	public HttpSession getHttpSession(String id) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HttpSession newHttpSession(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean getHttpOnly() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public int getMaxInactiveInterval() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setMaxInactiveInterval(int seconds) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSessionHandler(SessionHandler handler) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void addEventListener(EventListener listener) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeEventListener(EventListener listener) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void clearEventListeners() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public HttpCookie getSessionCookie(HttpSession session, String contextPath,
			boolean requestIsSecure) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SessionIdManager getSessionIdManager() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public SessionIdManager getMetaManager() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setSessionIdManager(SessionIdManager idManager) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isValid(HttpSession session) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String getNodeId(HttpSession session) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getClusterId(HttpSession session) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public HttpCookie access(HttpSession session, boolean secure) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void complete(HttpSession session) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setSessionIdPathParameterName(String parameterName) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public String getSessionIdPathParameterName() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String getSessionIdPathParameterNamePrefix() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isUsingCookies() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isUsingURLs() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public Set<SessionTrackingMode> getDefaultSessionTrackingModes() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Set<SessionTrackingMode> getEffectiveSessionTrackingModes() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void setSessionTrackingModes(
			Set<SessionTrackingMode> sessionTrackingModes) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public SessionCookieConfig getSessionCookieConfig() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean isCheckingRemoteSessionIdEncoding() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setCheckingRemoteSessionIdEncoding(boolean remote) {
		// TODO Auto-generated method stub
		
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
