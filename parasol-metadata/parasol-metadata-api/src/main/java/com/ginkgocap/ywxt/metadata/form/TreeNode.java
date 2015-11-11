package com.ginkgocap.ywxt.metadata.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.ginkgocap.ywxt.metadata.model.Code;

public 	class TreeNode implements Serializable {
    
	private static final long serialVersionUID = -7084382746950521108L;
	private String nid;
	private String pid;
	private Code code;
	private List<TreeNode> children = new ArrayList<TreeNode>();
	
	public TreeNode(String nid,String pid){
		this.nid = nid;
		this.pid = pid;
	}
	
	public TreeNode(Code code){
		this.code = code;
	}
	
	public String getNid() {
		return nid;
	}

	public void setNid(String nid) {
		this.nid = nid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}

	public Code getCode() {
		return code;
	}

	public void setCode(Code code) {
		this.code = code;
	}
	
}
