package com.ginkgocap.ywxt.metadata.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="tb_dic")
public class Dic implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1328788481921086194L;
    private long id;
    private String name;
    private int type;
    @Id
	@Column(name = "Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="type")
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

	@Override
	public String toString() {
		return "Dic [id=" + id + ", name=" + name + ", type=" + type + "]";
	}

    
    
}
