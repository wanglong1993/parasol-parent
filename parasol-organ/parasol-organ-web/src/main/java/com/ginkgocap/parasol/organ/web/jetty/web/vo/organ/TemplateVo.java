package com.ginkgocap.parasol.organ.web.jetty.web.vo.organ;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import com.ginkgocap.ywxt.organ.model.template.Moudle;
import com.ginkgocap.ywxt.organ.model.template.TemplateMoudle;
/**
 * 返回前端的模板Template 对象
 * @author caizhigang
 *
 */
public class TemplateVo  implements Serializable{

	


	private long templateId;// id 模板ID
	private String templateName; // 模板名称
	private int templateType ; // 0 表示  公共模板  1 表示 用户模板
	private List<JSONObject> moudles; // 模板模块 内容
	


	
	public List<JSONObject> getMoudles() {
		return moudles==null?new ArrayList<JSONObject>():moudles;
	}
	public void setMoudles(List<JSONObject> moudles) {
		this.moudles = ( moudles==null?new ArrayList<JSONObject>():moudles);
	}
	public long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(long templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public int getTemplateType() {
		return templateType;
	}
	public void setTemplateType(int templateType) {
		this.templateType = templateType;
	}
	

	
	
}
