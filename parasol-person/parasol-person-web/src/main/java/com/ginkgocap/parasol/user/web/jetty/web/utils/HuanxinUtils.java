package com.ginkgocap.parasol.user.web.jetty.web.utils;

import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.gintong.easemob.server.comm.Constants;
import com.gintong.easemob.server.httpclient.api.EasemobUserHandler;
import com.gintong.easemob.server.httpclient.vo.ImUser;

public final class HuanxinUtils {

	static ResourceBundle resource = ResourceBundle.getBundle("imService");
	static String url = "";
	static String interfaceName = "";
	static {
		url = resource.getString("url");
		interfaceName = resource.getString("writeHuanErrorForAddUser");
	}

	static final Logger logger = LoggerFactory.getLogger(HuanxinUtils.class);

	public static void addUser(final String username, final String password, String className) {
		ImUser imuser = new ImUser();
		imuser.setUsername(username);
		imuser.setPassword(password);
		final ObjectNode objectNode = EasemobUserHandler.addSingleUser(imuser);
		ThreadPoolUtils.getExecutorService().execute(new Runnable() {
			@Override
			public void run() {
				writeHuanxinError(objectNode, username, password);
			}
		});
		logger.info("Huanxin==>>Invoke method addUser of class " + className + ",Response result: " + objectNode);
	}

	static void writeHuanxinError(ObjectNode objectNode, String username, String password) {
		if (null != objectNode && !objectNode.isNull()) {
			JsonNode jsonNode = objectNode.get("error");
			if (null != jsonNode && !jsonNode.isNull()) {
				objectNode.put("applicationName", Constants.APP_NAME_JTMOBILESERVER);
				objectNode.put("methodName", "addUser");
				objectNode.put("chatId", "");
				objectNode.put("reqJson", "{\"username\":username,\"password\":password}");
				try {
					HttpClientUtil.getGintongPost(url, interfaceName, objectNode.toString());
				} catch (Exception e) {
					logger.error("调用Imserver的服务方法异常,环信异常信息入库失败.", e);
				}
			}
		}
	}
}
