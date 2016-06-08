package com.ginkgocap.parasol.organ.web.jetty.web.vo.organ;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class EventMeetList implements Serializable{

	/**
	 * 会面情况关联   事件
	 */
	private static final long serialVersionUID = 8979292310033267394L;
	
	
	private String tag;
	private List<EventMeet> conn;
	
	public String getTag() {
		return StringUtils.trimToEmpty(tag);
	}
	public void setTag(String tag) {
		this.tag = StringUtils.trimToEmpty(tag);
	}
	public List<EventMeet> getConn() {
		if(conn== null){
			return new ArrayList<EventMeet>();
		}else{
			return conn;
		}
	}
	public void setConn(List<EventMeet> conn) {
		this.conn= conn == null?new ArrayList<EventMeet>():conn;
	}
	
	
}
