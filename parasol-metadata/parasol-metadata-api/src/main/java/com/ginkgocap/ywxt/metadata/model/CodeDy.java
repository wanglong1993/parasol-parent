package com.ginkgocap.ywxt.metadata.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 地域
 * @author liu
 * 创建时间： 2011-11-18 11:47:09
 * @version1.1
 */

@Entity
@Table(name = "tb_code_dy")
public class CodeDy implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -7276340398139193957L;
    /**
     * 
     */

    private long id;//地域id
    private String name;//地域名称
    private String ctime;//创建时间
    @Id
	@Column(name = "Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ctime")
    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

	@Override
	public String toString() {
		return "CodeDy [id=" + id + ", name=" + name + ", ctime=" + ctime + "]";
	}

}
