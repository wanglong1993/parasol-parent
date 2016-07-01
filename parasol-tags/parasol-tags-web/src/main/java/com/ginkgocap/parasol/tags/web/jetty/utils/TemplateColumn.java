package com.ginkgocap.parasol.tags.web.jetty.utils;

public enum TemplateColumn {
	ORG("组织详情",1,"/org/orgAndProInfo.json"), RESOURCES("资源需求",2,"/customer/resource/findResource.json"),FINANCE("财务分析",3,"/customer/finance/details.json"),HIGHT("高层治理",4,"/customer/hight/findHightOne.json"),STOCK("股东研究",5,"/customer/stock/findStockOne.json"),PEER("同业竞争",6,"/customer/peer/findPeer.json"),INDUSTRY("行业动态",7,"/customer/industry/findIndustry.json"),
	REPORT("研究报告",8,"/customer/findReport.json"),AREAINFO("地区概况",9,"/customer/areaInfo/findOne.json"),DEPARTMENT("主要职能部门",10,"/customer/departMents/findDepartOne.json"),PEOPLE("联系人资料",11,""),LISING("上市信息",12,""),XGDSR("相关当事人",13,"/customer/findRelevantParties.json"),
	ZYZZ("职业资质",14,""),FZJG("分支机构",15,""),YWFX("业务分析",16,""),GLQY("关联企业",17,""),ZYTT("专业团体",18,""),YBQY("一般企业",19,""),ZJJG("中介机构",20,""),
	QYKW("企业刊物",21,""),ZYKH("主要客户",22,""),HHR("合伙人",23,""),JGJJ("机构简介",24,""),BZQK("报纸期刊",25,""),YJJG("研究机构",26,""),DSGB("电视广播",27,""),
	JRJG("金融机构",28,""),JRCP("金融产品",29,""),HLWMT("互联网媒体",30,"");
	// 成员变量  
    private String name;  
    private int index;  
    private String url ;
    // 构造方法  
    private TemplateColumn(String name, int index,String url) {  
        this.name = name;  
        this.index = index;  
        this.url = url;
    }  
    // 普通方法  
    public static TemplateColumn getObject(int index) {  
        for (TemplateColumn c : TemplateColumn.values()) {  
            if (c.getIndex() == index) {
                return c;  
            }  
        }  
        return null;  
    }
   
    // get set 方法  
    public String getName() {  
        return name;  
    }  
    public void setName(String name) {  
        this.name = name;  
    }  
    public int getIndex() {  
        return index;  
    }  
    public void setIndex(int index) {  
        this.index = index;  
    }
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}  
    
    
}
