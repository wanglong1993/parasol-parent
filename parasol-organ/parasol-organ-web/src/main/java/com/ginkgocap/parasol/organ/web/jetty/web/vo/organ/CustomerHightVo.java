package com.ginkgocap.parasol.organ.web.jetty.web.vo.organ;
import java.io.Serializable;

import com.ginkgocap.ywxt.organ.model.Relation;

public class CustomerHightVo implements Serializable{

	private Relation relation;
	private String job;
	private String birth;
	private String eduational;
	private String type;

	public Relation getRelation() {
		return relation;
	}

	public void setRelation(Relation relation) {
		this.relation = relation;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getEduational() {
		return eduational;
	}

	public void setEduational(String eduational) {
		this.eduational = eduational;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
