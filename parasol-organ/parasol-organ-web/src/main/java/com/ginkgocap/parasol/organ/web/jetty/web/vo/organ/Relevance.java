package com.ginkgocap.parasol.organ.web.jetty.web.vo.organ;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Relevance implements Serializable{

	/**
	 * 会面情况关联   事件
	 */
	private static final long serialVersionUID = 8979292310033267394L;
	private List<EventMeetList> r;
	private List<KonwkedgeMeetList> k;
	private List<OrgMeetList> o;
	private List<PeopleMeetList> p;
	public List<EventMeetList> getR() {
		if(r== null){
			return new ArrayList<EventMeetList>();
		}else{
			return r;
		}
	}
	public void setR(List<EventMeetList> r) {
		this.r = r == null?new ArrayList<EventMeetList>():r;
	}
	public List<KonwkedgeMeetList> getK() {
		if(k== null){
			return new ArrayList<KonwkedgeMeetList>();
		}else{
			return k;
		}
	}
	public void setK(List<KonwkedgeMeetList> k) {
		this.k = k == null?new ArrayList<KonwkedgeMeetList>():k;
	}
	public List<OrgMeetList> getO() {
		if(o== null){
			return new ArrayList<OrgMeetList>();
		}else{
			return o;
		}
	}
	public void setO(List<OrgMeetList> o) {
		this.o = o == null?new ArrayList<OrgMeetList>():o;
	}
	public List<PeopleMeetList> getP() {
		if(p== null){
			return new ArrayList<PeopleMeetList>();
		}else{
			return p;
		}
	}
	public void setP(List<PeopleMeetList> p) {
		this.p = p == null?new ArrayList<PeopleMeetList>():p;
	}
	
	
}
