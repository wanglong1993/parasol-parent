package com.ginkgocap.ywxt.metadata.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * 国家区号表
 * <p>  </p>
 * @author liubang
 * @date 2015-1-14
 */
@Entity
@Table(name="tb_country_code")
public class CountryCode implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5653753210649423415L;
	private long id;		//主键
	private String country;	//国家名称
	private String code;	//区号
	private String nameFirst;	//国家类型  ‘常用’ 和首字母作为类型
	@Id
	@Column(name = "Id")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	
	@Column(name="country")
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	@Column(name="code")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column(name="nameFirst")
	public String getNameFirst() {
		return nameFirst;
	}
	public void setNameFirst(String nameFirst) {
		this.nameFirst = nameFirst;
	}
	@Override
	public String toString() {
		return "CountryCode [id=" + id + ", country=" + country + ", code="
				+ code + ", nameFirst=" + nameFirst + "]";
	}
	
	
	
}	
