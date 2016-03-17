package com.ginkgocap.parasol.knowledge.model;

import java.util.Date;
import org.apache.commons.lang3.StringUtils;


/**
 * 知识javaBean （资产管理）
 * 
 * @author caihe
 * 
 */
public class KnowledgeAsset extends Knowledge {

	private static final long serialVersionUID = 1L;

	@Override
	public <T> Knowledge setValue(KnowledgeNewsVO vo, Long userId) {
		this.setColumnid(vo.getColumnid() + "");
		this.setUid(userId);
		//this.setUname(user.getName());
		this.setTags(vo.getTags());
		this.setId(vo.getkId());
		this.setTitle(vo.getTitle());
		//this.setCid(userId);
		//this.setCname(user.getName());
		this.setSource(vo.getSource());
		this.setS_addr("");
		this.setCpathid(vo.getColumnPath());
		this.setPic(vo.getPic());
		this.setDesc(vo.getDesc());
		this.setContent(vo.getContent());
		this.setEssence(Integer.parseInt(vo.getEssence()));
		this.setCreatetime(vo.getCreatetime());
		this.setStatus(vo.getSelectedIds().equals(Constants.Ids.platform.v()) ? Constants.Status.checking
				.v() : Constants.Status.checked.v());
		this.setReport_status(Constants.ReportStatus.unreport.v());
		this.setIsh(Constants.HighLight.unlight.v());
		this.setHcontent("");
		this.setAsso(vo.getAsso());
		this.setSelectedIds(vo.getSelectedIds());
		this.setTaskid(vo.getTaskId());
		this.setKnowledgeMainId(vo.getKnowledgeMainId());
		this.setFileType(vo.getFileType());
		return this;
	}
	
	@Override
	public <T> Knowledge setDraftValue(KnowledgeNewsVO vo, Long userId) {
		this.setColumnid(vo.getColumnid() + "");
		this.setUid(userId);
		//this.setUname(user.getName());
		this.setTags(vo.getTags());
		this.setId(vo.getkId());
		this.setTitle(vo.getTitle());
		this.setCid(userId);
		//this.setCname(user.getName());
		this.setSource(vo.getSource());
		this.setS_addr("");
		this.setCpathid(vo.getColumnPath());
		this.setPic(vo.getPic());
		this.setDesc(vo.getDesc());
		this.setContent(vo.getContent());
		this.setEssence(Integer.parseInt(vo.getEssence()));
		this.setCreatetime(vo.getCreatetime());
		this.setStatus(Constants.Status.draft.v());
		this.setReport_status(Constants.ReportStatus.unreport.v());
		this.setIsh(Constants.HighLight.unlight.v());
		this.setHcontent("");
		this.setAsso(vo.getAsso());
		this.setSelectedIds(vo.getSelectedIds());
		this.setTaskid(vo.getTaskId());
		this.setKnowledgeMainId(vo.getKnowledgeMainId());
		return this;
	}

}