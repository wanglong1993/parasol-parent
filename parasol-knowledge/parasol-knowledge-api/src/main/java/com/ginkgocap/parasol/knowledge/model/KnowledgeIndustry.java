package com.ginkgocap.parasol.knowledge.model;

import org.apache.commons.lang3.StringUtils;

/**
 * 知识javaBean （行业）
 * 
 * @author caihe
 * 
 */
public class KnowledgeIndustry extends Knowledge {

	// 老知识ID
	private long oid;

	public long getOid() {
		return oid;
	}

	public void setOid(long oid) {
		this.oid = oid;
	}
	
	@Override
	public <T> Knowledge setValue(KnowledgeNewsVO vo, Long userId) {
		this.setColumnId(vo.getColumnId());
		this.setUId(userId);
		//this.setUname(user.getName());
		this.setTags(vo.getTags());
		this.setId(vo.getkId());	
		this.setTitle(vo.getTitle());
		this.setCId(userId);
		//this.setCname(user.getName());
		this.setSource("");
		this.setS_addr("");
		this.setCpathid(vo.getColumnPath());
		this.setPic(vo.getPic());
		this.setDesc(vo.getDesc());
		this.setContent(vo.getContent());
		this.setEssence(Integer.parseInt(StringUtils.isBlank(vo.getEssence()) ? "0"
				: vo.getEssence()));
		this.setCreatetime(vo.getCreatetime());
		this.setStatus(vo.getSelectedIds().equals(Constants.Ids.platform.v()) ? Constants.Status.checking
				.v() : Constants.Status.checked.v());
		this.setReport_status(Constants.ReportStatus.unreport.v());
		this.setIsh(Constants.HighLight.unlight.v());
		this.setHcontent("");
		
		this.setOid(vo.getOid());
		this.setTaskid(vo.getTaskId());
		this.setSelectedIds(vo.getSelectedIds());
		this.setAsso(vo.getAsso());
		this.setKnowledgeMainId(vo.getKnowledgeMainId());
		this.setHideDesc(vo.getHideDesc());
		this.setFileType(vo.getFileType());
		return this;
	}

	@Override
	public <T> Knowledge setDraftValue(KnowledgeNewsVO vo, Long userId) {
		this.setColumnId(vo.getColumnId());
		this.setUId(userId);
		//this.setUname(user.getName());
		this.setTags(vo.getTags());
		this.setId(vo.getkId());
		this.setTitle(vo.getTitle());
		this.setCId(userId);
		//this.setCname(user.getName());
		this.setSource("");
		this.setS_addr("");
		this.setCpathid(vo.getColumnPath());
		this.setPic(vo.getPic());
		this.setDesc(vo.getDesc());
		this.setContent(vo.getContent());
		this.setEssence(Integer.parseInt(StringUtils.isBlank(vo.getEssence()) ? "0"
				: vo.getEssence()));
		this.setCreatetime(vo.getCreatetime());
		this.setStatus(Constants.Status.draft.v());
		this.setReport_status(Constants.ReportStatus.unreport.v());
		this.setIsh(Constants.HighLight.unlight.v());
		this.setHcontent("");
		this.setOid(vo.getOid());
		
		this.setSelectedIds(vo.getSelectedIds());
		this.setAsso(vo.getAsso());
		this.setKnowledgeMainId(vo.getKnowledgeMainId());
		this.setHideDesc(vo.getHideDesc());
		return this;
	}
	
}