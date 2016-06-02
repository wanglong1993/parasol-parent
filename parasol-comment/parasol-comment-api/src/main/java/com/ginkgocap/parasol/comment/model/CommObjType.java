package com.ginkgocap.parasol.comment.model;

public enum CommObjType {
	res(1),
	review(2);
	
	private int type;
	private CommObjType(int t){
		this.type=t;
	}
	
	public int getVal(){
		return type;
	}
}
