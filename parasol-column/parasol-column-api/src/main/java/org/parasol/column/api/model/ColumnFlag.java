package org.parasol.column.api.model;

public enum ColumnFlag {
	user(0),
	sys(1);
	
	private short flag;
	private ColumnFlag(int t){
		this.flag=(short)t;
	}
	public short getVal(){
		return flag;
	}
}
