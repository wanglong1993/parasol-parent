package com.ginkgocap.parasol.tags.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lenovo on 2018/2/28.
 */
public class TagSourceSearchVO implements Serializable {

    private long sourceId; // '资源ID',
    private String sourceTitle;// '资源标题'
    private long sourceType; // 资源类型 2人脉3客户7需求8知识,
    private long createAt; // '更新时间',
    private String sourceExtra;
    private List<Tag> sourceTagList;

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
        this.sourceTagList = sourceTagList;
    }
}
