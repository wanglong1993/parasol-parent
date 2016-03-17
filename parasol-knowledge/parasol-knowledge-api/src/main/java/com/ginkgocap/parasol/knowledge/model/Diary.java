package com.ginkgocap.parasol.knowledge.model;

import java.io.Serializable;

/**
 * Created by Admin on 2016/3/16.
 */
public class Diary implements Serializable {
    private static final long serialVersionUID = -1929086533334737984L;

    private Long id; //Id
    private Long fbrId; //发布人ID
    private String fname;//发布人的姓名
    private Long titleId;//标题ID
    private String titleName;//标题名称
    private Long requirementTypeId;//需求类型
    private String type;//操作的类型
    private String content; //日记内容
    private String ctime; //创建时间
    private int infoVisible;//信息可见； 0:自己 1:好友  2:所有
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getFbrId() {
        return fbrId;
    }
    public void setFbrId(Long fbrId) {
        this.fbrId = fbrId;
    }
    public String getFname() {
        return fname;
    }
    public void setFname(String fname) {
        this.fname = fname;
    }
    public Long getTitleId() {
        return titleId;
    }
    public void setTitleId(Long titleId) {
        this.titleId = titleId;
    }
    public String getTitleName() {
        return titleName;
    }
    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }
    public Long getRequirementTypeId() {
        return requirementTypeId;
    }
    public void setRequirementTypeId(Long requirementTypeId) {
        this.requirementTypeId = requirementTypeId;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getCtime() {
        return ctime;
    }
    public void setCtime(String ctime) {
        this.ctime = ctime;
    }
    public int getInfoVisible() {
        return infoVisible;
    }
    public void setInfoVisible(int infoVisible) {
        this.infoVisible = infoVisible;
    }


}
