package com.ginkgocap.parasol.organ.web.jetty.web.vo.organ;

import java.io.Serializable;

import org.apache.commons.lang.StringUtils;

import com.ginkgocap.ywxt.organ.model.Relation;

/**
 * <p>
 * Title: OrgInfoVo.java<／p>
 * <p>
 * Description:组织注册简写对象 <／p>
 * 
 * @author wfl
 * @date 2015-3-9
 * @version 1.0
 */
public class OrgInfoVo implements Serializable{

	private String orgType;// 组织类型 1.金融机构 2一般企业 3.政府组织 4.中介机构 5.专业媒体 6.期刊报纸
							// 7.研究机构 8.电视广播 9.互联网媒体
	private String isListing;// 是否上市 1:是 0:非上市
	private String stockNum;// 证券号
	private String name;//
	private String shotName;// 简称
	private String licensePic;// 营业执照副本扫描件
	private String linkIdPic;// 身份证图片(正面)
	private String linkIdPicReverse;//身份证反面
	private Relation linkName;//
	private String linkMobile;// 联系人手机
	private String mobileCode;//手机验证码
	private String picLogo;//组织logo

	public String getOrgType() {
		return StringUtils.trimToEmpty(orgType);
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getIsListing() {
		return StringUtils.trimToEmpty(isListing);
	}

	public void setIsListing(String isListing) {
		this.isListing = isListing;
	}

	public String getStockNum() {
		return StringUtils.trimToEmpty(stockNum);
	}

	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}

	public String getName() {
		return StringUtils.trimToEmpty(name);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShotName() {
		return StringUtils.trimToEmpty(shotName);
	}

	public void setShotName(String shotName) {
		this.shotName = shotName;
	}

	public String getLicensePic() {
		return StringUtils.trimToEmpty(licensePic);
	}

	public void setLicensePic(String licensePic) {
		this.licensePic = licensePic;
	}

	public String getLinkIdPic() {
		return StringUtils.trimToEmpty(linkIdPic);
	}

	public void setLinkIdPic(String linkIdPic) {
		this.linkIdPic = linkIdPic;
	}

	public Relation getLinkName() {
		return linkName==null?new Relation():linkName;
	}

	public void setLinkName(Relation linkName) {
		this.linkName = linkName;
	}

	public String getLinkMobile() {
		return StringUtils.trimToEmpty(linkMobile);
	}

	public void setLinkMobile(String linkMobile) {
		this.linkMobile = linkMobile;
	}

	public String getMobileCode() {
		return StringUtils.trimToEmpty(mobileCode);
	}

	public void setMobileCode(String mobileCode) {
		this.mobileCode = mobileCode;
	}

	public String getLinkIdPicReverse() {
		return StringUtils.trimToEmpty(linkIdPicReverse);
	}

	public void setLinkIdPicReverse(String linkIdPicReverse) {
		this.linkIdPicReverse = linkIdPicReverse;
	}

	public String getPicLogo() {
		return StringUtils.trimToEmpty(picLogo);
	}

	public void setPicLogo(String picLogo) {
		this.picLogo = picLogo;
	}
    
}
