package com.ginkgocap.parasol.tags.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lenovo on 2018/2/28.
 */
public class TagSearchVO implements Serializable {

    private long tagId;// '标签ID'
    private long userId; // '用户ID',
    private String tagName; // '标签名称',
    private String firstName; //名字首字母
    private long personSourceCount=0;
    private long customerSourceCount=0;
    private long demandSourceCount=0;
    private long knowledgeSourceCount=0;

    public long getTagId() {
        return tagId;
    }

    public void setTagId(long tagId) {
        this.tagId = tagId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public long getPersonSourceCount() {
        return personSourceCount;
    }

    public void setPersonSourceCount(long personSourceCount) {
        this.personSourceCount = personSourceCount;
    }

    public long getCustomerSourceCount() {
        return customerSourceCount;
    }

    public void setCustomerSourceCount(long customerSourceCount) {
        this.customerSourceCount = customerSourceCount;
    }

    public long getDemandSourceCount() {
        return demandSourceCount;
    }

    public void setDemandSourceCount(long demandSourceCount) {
        this.demandSourceCount = demandSourceCount;
    }

    public long getKnowledgeSourceCount() {
        return knowledgeSourceCount;
    }

    public void setKnowledgeSourceCount(long knowledgeSourceCount) {
        this.knowledgeSourceCount = knowledgeSourceCount;
    }

}
