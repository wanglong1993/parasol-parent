package com.ginkgocap.parasol.associate.model;

import com.fasterxml.jackson.annotation.JsonFilter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

/**
 * Created by xutlong on 2017/11/14.
 */
@JsonFilter("com.ginkgocap.parasol.associate.model.AssociateShare")
@Entity
@Table(name = "tb_associate_share")
public class AssociateShare {
    private long id;
    private String content;

    @Id
    @GeneratedValue(generator = "AssociateShareId")
    @GenericGenerator(name = "AssociateShareId", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator", parameters = { @Parameter(name = "sequence", value = "AssociateShareId") })
    @Column(name = "id")
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "content")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
