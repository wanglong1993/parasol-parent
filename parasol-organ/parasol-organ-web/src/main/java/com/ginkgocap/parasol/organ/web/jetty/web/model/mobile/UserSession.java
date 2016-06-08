package com.ginkgocap.parasol.organ.web.jetty.web.model.mobile;

import javax.annotation.Resource;

import com.ginkgocap.ywxt.user.model.User;
import com.ginkgocap.ywxt.util.sso.session.Session;
import com.ginkgocap.ywxt.util.sso.session.SessionManager;
import com.ginkgocap.ywxt.util.sso.util.Constants;

/**
 * session中获取用户信息
 * 
 * @author liuyang
 * 
 */
public class UserSession {
	@Resource
	private static SessionManager sessionManager;

	/**
	 * session中存放用户的信息
	 * 
	 * @param user
	 *            user对象
	 * @param request
	 * @param response
	 */
	public static void setSessionUser(User user) {
		Session session = SessionManager.getCurrentSession();
		session.setAttribute(Constants.LOGIN_USER, user);
	}
}
