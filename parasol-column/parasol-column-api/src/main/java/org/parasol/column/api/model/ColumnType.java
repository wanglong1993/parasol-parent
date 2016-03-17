package org.parasol.column.api.model;

public enum ColumnType {
	zixun(1),
	trgj(2),
	hangye(3),
	jdal(4),
	xcl(5),
	zcgl(6),
	hongguan(7),
	guandian(8),
	panlie(9),
	flfg(10),
	wenzhang(11);
	private short type;
	private ColumnType(int t){
		this.type=(short)t;
	}
	
	public short getVal(){
		return type;
	}

}
