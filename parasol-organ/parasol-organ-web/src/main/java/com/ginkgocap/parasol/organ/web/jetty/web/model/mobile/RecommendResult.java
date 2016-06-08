package com.ginkgocap.parasol.organ.web.jetty.web.model.mobile;

import java.io.Serializable;

public class RecommendResult extends SearchResult implements Serializable {

	private static final long serialVersionUID = 3799748746576697718L;

	private String knowledgeType; // 知识类型

	private String tagsScores;// 对接的人

	private String tags;// 标签

	public String getKnowledgeType() {
		return knowledgeType;
	}

	public void setKnowledgeType(String knowledgeType) {
		this.knowledgeType = knowledgeType;
	}

	public String getTagsScores() {
		return tagsScores;
	}

	public void setTagsScores(String tagsScores) {
		this.tagsScores = tagsScores;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

}
