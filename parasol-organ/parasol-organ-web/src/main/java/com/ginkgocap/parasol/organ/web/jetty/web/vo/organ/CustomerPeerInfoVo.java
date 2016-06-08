package com.ginkgocap.parasol.organ.web.jetty.web.vo.organ;

import java.io.Serializable;
import java.util.List;

import com.ginkgocap.ywxt.organ.model.Relation;

public class CustomerPeerInfoVo implements Serializable {

	private Relation inc;
	private List<String> tagNames;

	public Relation getInc() {
		return inc;
	}

	public void setInc(Relation inc) {
		this.inc = inc;
	}

	public List<String> getTagNames() {
		return tagNames;
	}

	public void setTagNames(List<String> tagNames) {
		this.tagNames = tagNames;
	}

}
