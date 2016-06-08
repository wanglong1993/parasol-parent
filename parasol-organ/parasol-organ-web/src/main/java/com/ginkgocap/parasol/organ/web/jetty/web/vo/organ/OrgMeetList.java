package com.ginkgocap.parasol.organ.web.jetty.web.vo.organ;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class OrgMeetList implements Serializable{

	/**
	 *会面记录关联   知识
	 */
	private static final long serialVersionUID = 8979292310033267394L;
	

	private String tag;
	private List<OrgMeet> conn;
	
	public String getTag() {
		return StringUtils.trimToEmpty(tag);
	}
	public void setTag(String tag) {
		this.tag = StringUtils.trimToEmpty(tag);
	}
	public List<OrgMeet> getConn() {
		if(conn == null){
			return new ArrayList<OrgMeet>();
		}else{
			return conn;
		}
	}
	public void setConn(List<OrgMeet> conn) {
		this.conn = conn == null?new ArrayList<OrgMeet>():conn;
	}
	
	
	
	

}
