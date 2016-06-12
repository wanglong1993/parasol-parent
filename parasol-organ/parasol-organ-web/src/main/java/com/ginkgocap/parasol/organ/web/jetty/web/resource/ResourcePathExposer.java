/**
 * Copyright (c) 2011 银杏资本.
 * All Rights Reserved. 保留所有权利.
 */
package com.ginkgocap.parasol.organ.web.jetty.web.resource;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;

/**
 * 通过Spring框架在ServletContext层面注入静态资源根路径信息
 * 
 * @author zhangle
 * 
 */
public class ResourcePathExposer implements ServletContextAware {

	private ServletContext application;
	private String imageRoot = "";
	private String resourceRoot = "";
	private String resourceVersion = "";
	private String imageVersion = "";

	private String imagesRoot;
	private String scriptsRoot;
	private String stylesRoot;
	private String nginxRoot;
	private String nfsHome;
	private String knowledgeHost;// 知识管理系统的连接地址
	private String cloudHost;// 云研究系统的连接地址
	private String webUrl;// 社区系统的连接地址
	private String bigdataQueryHost;// 大数据访问接口
	private String ecosphereQueryHost;// 关联访问接口
	private String newQueryHost;// 关联访问接口
	private String konwledgeQueryHotUrl; // 热点排行
	private String konwledgeQueryCommentUrl;// 评论排行
	private String knowledgeQueryTagUrl;// 热门排行

	/*
	 * 初始化方法
	 */
	public void init() {
		// resourceRoot = "/resources" + resourceVersion;
		// imageRoot = "/resources" + imageVersion;
		// getServletContext().setAttribute("resourcesRoot",
		// getServletContext().getContextPath() + resourceRoot);
		// getServletContext().setAttribute("imageRoot",
		// getServletContext().getContextPath() + imageRoot);
		//
		getServletContext().setAttribute("imagesRoot", imagesRoot);
		// getServletContext().setAttribute("scriptsRoot", scriptsRoot);
		// getServletContext().setAttribute("stylesRoot", stylesRoot);
		getServletContext().setAttribute("nginxRoot", nginxRoot);
		getServletContext().setAttribute("nfsHome", nfsHome);
		getServletContext().setAttribute("webUrl", webUrl);
		getServletContext().setAttribute("bigdataQueryHost", bigdataQueryHost);
		getServletContext().setAttribute("ecosphereQueryHost",
				ecosphereQueryHost);
		getServletContext().setAttribute("newQueryHost", newQueryHost);
		getServletContext().setAttribute("konwledgeQueryHotUrl",
				konwledgeQueryHotUrl);
		getServletContext().setAttribute("konwledgeQueryCommentUrl",
				konwledgeQueryCommentUrl);
		getServletContext().setAttribute("knowledgeQueryTagUrl",
				knowledgeQueryTagUrl);
		// getServletContext().setAttribute("knowledgeHost", knowledgeHost);
		// getServletContext().setAttribute("cloudHost", cloudHost);
		// add by douyou ,in jsp,use ${contextPath } to replace
		// request.getContextPath()

		getServletContext().setAttribute("contextPath",
				getServletContext().getContextPath());
	}

	public String getImageRoot() {
		return imageRoot;
	}

	public void setImageRoot(String imageRoot) {
		this.imageRoot = imageRoot;
	}

	public String getResourceRoot() {
		return resourceRoot;
	}

	public void setResourceRoot(String resourceRoot) {
		this.resourceRoot = resourceRoot;
	}

	public String getResourceVersion() {
		return resourceVersion;
	}

	public void setResourceVersion(String resourceVersion) {
		this.resourceVersion = resourceVersion;
	}

	public String getImageVersion() {
		return imageVersion;
	}

	public void setImageVersion(String imageVersion) {
		this.imageVersion = imageVersion;
	}

	@Override
	public void setServletContext(ServletContext servletContext) {
		application = servletContext;

	}

	public ServletContext getServletContext() {
		return this.application;
	}

	public String getImagesRoot() {
		return imagesRoot;
	}

	public void setImagesRoot(String imagesRoot) {
		this.imagesRoot = imagesRoot;
	}

	public String getScriptsRoot() {
		return scriptsRoot;
	}

	public void setScriptsRoot(String scriptsRoot) {
		this.scriptsRoot = scriptsRoot;
	}

	public String getStylesRoot() {
		return stylesRoot;
	}

	public void setStylesRoot(String stylesRoot) {
		this.stylesRoot = stylesRoot;
	}

	public String getNginxRoot() {
		return nginxRoot;
	}

	public void setNginxRoot(String nginxRoot) {
		this.nginxRoot = nginxRoot;
	}

	public String getNfsHome() {
		return nfsHome;
	}

	public void setNfsHome(String nfsHome) {
		this.nfsHome = nfsHome;
	}

	public void setKnowledgeHost(String knowledgeHost) {
		this.knowledgeHost = knowledgeHost;
	}

	public void setCloudHost(String cloudHost) {
		this.cloudHost = cloudHost;
	}

	public String getWebUrl() {
		return webUrl;
	}

	public void setWebUrl(String webUrl) {
		this.webUrl = webUrl;
	}

	public String getKnowledgeHost() {
		return knowledgeHost;
	}

	public String getCloudHost() {
		return cloudHost;
	}

	public void setBigdataQueryHost(String bigdataQueryHost) {
		this.bigdataQueryHost = bigdataQueryHost;
	}

	public void setEcosphereQueryHost(String ecosphereQueryHost) {
		this.ecosphereQueryHost = ecosphereQueryHost;
	}

	public String getBigdataQueryHost() {
		return bigdataQueryHost;
	}

	public String getEcosphereQueryHost() {
		return ecosphereQueryHost;
	}

	public String getNewQueryHost() {
		return newQueryHost;
	}

	public void setNewQueryHost(String newQueryHost) {
		this.newQueryHost = newQueryHost;
	}

	public String getKonwledgeQueryHotUrl() {
		return konwledgeQueryHotUrl;
	}

	public void setKonwledgeQueryHotUrl(String konwledgeQueryHotUrl) {
		this.konwledgeQueryHotUrl = konwledgeQueryHotUrl;
	}

	public String getKonwledgeQueryCommentUrl() {
		return konwledgeQueryCommentUrl;
	}

	public void setKonwledgeQueryCommentUrl(String konwledgeQueryCommentUrl) {
		this.konwledgeQueryCommentUrl = konwledgeQueryCommentUrl;
	}

	public String getKnowledgeQueryTagUrl() {
		return knowledgeQueryTagUrl;
	}

	public void setKnowledgeQueryTagUrl(String knowledgeQueryTagUrl) {
		this.knowledgeQueryTagUrl = knowledgeQueryTagUrl;
	}

}
