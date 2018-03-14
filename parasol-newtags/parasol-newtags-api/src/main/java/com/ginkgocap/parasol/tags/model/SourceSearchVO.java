package com.ginkgocap.parasol.tags.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.ginkgocap.parasol.tags.util.MyCustomSerializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 2018/2/28.
 */
public class SourceSearchVO implements Serializable {

    private static final long serialVersionUID = 6606454865761504898L;

    private long sourceId; // '资源ID',
    private String sourceTitle;// '资源标题'
    private long sourceType; // 资源类型 2人脉3客户7需求8知识,
    private long createAt; // '更新时间',
    private String sourceExtra;
    //@JsonSerialize(using = MyCustomSerializer.class)
    private List<Tag> sourceTagList;
    private long createUserId; // 创建者 id
    private long sourceColumnType;

    public long getSourceColumnType() {
        return sourceColumnType;
    }

    public void setSourceColumnType(long sourceColumnType) {
        this.sourceColumnType = sourceColumnType;
    }

    public String getSourceExtra() {
        return sourceExtra;
    }

    public void setSourceExtra(String sourceExtra) {
        this.sourceExtra = sourceExtra;
    }

    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceTitle() {
        return sourceTitle;
    }

    public void setSourceTitle(String sourceTitle) {
        this.sourceTitle = sourceTitle;
    }

    public long getSourceType() {
        return sourceType;
    }

    public void setSourceType(long sourceType) {
        this.sourceType = sourceType;
    }

    public long getCreateAt() {
        return createAt;
    }

    public void setCreateAt(long createAt) {
        this.createAt = createAt;
    }

    public List<Tag> getSourceTagList() {
        return sourceTagList;
    }

    public void setSourceTagList(List<Tag> sourceTagList) {
        if(sourceTagList==null||sourceTagList.size()==0){
            this.sourceTagList=new ArrayList<Tag>();
        }else {
            this.sourceTagList=sourceTagList;
        }
    }

    public long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(long createUserId) {
        this.createUserId = createUserId;
    }
}
