package com.ginkgocap.parasol.organ.web.jetty.web.vo.organ;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerColumnVo implements Serializable {

	private long id;
	private String name;
	private int type;// 类型 1:专业栏目 2:常用栏目 3:自定义栏目
	private int isSelect; // 是否选择 1:选择 0:未选择
	private List<CustomerColumnVo> child;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getIsSelect() {
		return isSelect;
	}

	public void setIsSelect(int isSelect) {
		this.isSelect = isSelect;
	}

	public List<CustomerColumnVo> getChild() {
		return child==null?new ArrayList<CustomerColumnVo>():child;
	}

	public void setChild(List<CustomerColumnVo> child) {
		this.child = child;
	}

}
