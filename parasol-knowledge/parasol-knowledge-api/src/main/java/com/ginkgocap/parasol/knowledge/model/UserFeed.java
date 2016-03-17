package com.ginkgocap.parasol.knowledge.model;

import org.springframework.data.annotation.Transient;
import java.io.Serializable;
import java.util.List;
/**
 * 好友动态 mongo
 * @author 窦友
 *
 */
public class UserFeed implements Serializable {
    private static final long serialVersionUID = -26037617805798145L;

    private String id;//主键

    private String mongoId;

    private List<ReceiversInfo> receivers;//信息接收人
    private ResendInfo resend;//转发

    private long createdById;//信息发布人

    private String createdBy;//信息发布人

    private long oCreatedById;//信息首次发布人

    private String oCreatedBy;//信息首次发布人

    private String companyName;//信息首次发布人公司

    private String companyJob;//信息首次发布人职位

    private int scope;//扩散范围   默认0 所有人可见，1根据receiver检测 2所有好友查询所有好友id然后放入receivers然后scope改成1

    private int type;//1：发观点2：回复观点3：发布需求4：分享需求5：评论需求6：引荐好友7：添加了新好友/一度朋友添加好友成功   8：关注机构9：分享机构 10:回复我关注的需求 11 我关注的需求  12组织发布的新闻 13:转发xuqiu 14z
    private long targetId;//model对象在mysql中的id

    private String title;//信息标题
    private String content;//信息内容

    private List<String> industryArrs;//行业id集合方便查询
    private List<String> categoryArrs;//类型集合方便查询


    private String ctime;//发布时间
    private String parentId = "";//扩展字段，心情日记的回复功能，暂时不用(开启，表示被屏蔽)

    private List<Long> deletedById;//删除人从自己的列表中删除

    private int diaryType;//观点长短，0：短，1：长
    private String imgPath;//长观点的图片路径
    private int delstatus;//状态（0正常使用 -1 删除 ）

    private String clearContent;//清楚格式之后的content

    private String navTitle;//用来显示该人的行为 例如 发布需求、关注的需求有回复等

    private String reqType; //需求类型。 专门用来区分是投资需求还是融资需求
    private int resendNum;//转发次数
    private int favrateNum;//转发次数
    @Transient
    private int oneReplyCount;
    @Transient
    private List<EtUserInfo> etInfo;

    private String groupName;

    public int getFavrateNum() {
        return favrateNum;
    }
    public void setFavrateNum(int favrateNum) {
        this.favrateNum = favrateNum;
    }
    public ResendInfo getResend() {
        return resend;
    }
    public void setResend(ResendInfo resend) {
        this.resend = resend;
    }
    //是否可见
    private String isVisable;

    public int getResendNum() {
        return resendNum;
    }
    public void setResendNum(int resendNum) {
        this.resendNum = resendNum;
    }
    public String getMongoId() {
        return mongoId;
    }
    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<ReceiversInfo> getReceivers() {
        return receivers;
    }
    public void setReceivers(List<ReceiversInfo> receivers) {
        this.receivers = receivers;
    }
    public long getCreatedById() {
        return createdById;
    }
    public void setCreatedById(long createdById) {
        this.createdById = createdById;
    }
    public String getCreatedBy() {
        return createdBy;
    }
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }
    public int getScope() {
        return scope;
    }
    public void setScope(int scope) {
        this.scope = scope;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public long getTargetId() {
        return targetId;
    }
    public void setTargetId(long targetId) {
        this.targetId = targetId;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public List<String> getIndustryArrs() {
        return industryArrs;
    }
    public void setIndustryArrs(List<String> industryArrs) {
        this.industryArrs = industryArrs;
    }
    public List<String> getCategoryArrs() {
        return categoryArrs;
    }
    public void setCategoryArrs(List<String> categoryArrs) {
        this.categoryArrs = categoryArrs;
    }
    public String getCtime() {
        return ctime;
    }
    public void setCtime(String ctime) {
        this.ctime = ctime;
    }
    public String getParentId() {
        return parentId;
    }
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
    public List<Long> getDeletedById() {
        return deletedById;
    }
    public void setDeletedById(List<Long> deletedById) {
        this.deletedById = deletedById;
    }
    public int getDiaryType() {
        return diaryType;
    }
    public void setDiaryType(int diaryType) {
        this.diaryType = diaryType;
    }
    public String getImgPath() {
        return imgPath;
    }
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
    public int getDelstatus() {
        return delstatus;
    }
    public void setDelstatus(int delstatus) {
        this.delstatus = delstatus;
    }
    public int getOneReplyCount() {
        return oneReplyCount;
    }
    public void setOneReplyCount(int oneReplyCount) {
        this.oneReplyCount = oneReplyCount;
    }
    public String getGroupName() {
        return groupName;
    }
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
    public String getClearContent() {
        return clearContent;
    }
    public void setClearContent(String clearContent) {
        this.clearContent = clearContent;
    }
    public List<EtUserInfo> getEtInfo() {
        return etInfo;
    }
    public void setEtInfo(List<EtUserInfo> etInfo) {
        this.etInfo = etInfo;
    }
    public String getIsVisable() {
        return isVisable;
    }
    public void setIsVisable(String isVisable) {
        this.isVisable = isVisable;
    }
    public String getNavTitle() {
        return navTitle;
    }
    public void setNavTitle(String navTitle) {
        this.navTitle = navTitle;
    }
    public long getoCreatedById() {
        return oCreatedById;
    }
    public void setoCreatedById(long oCreatedById) {
        this.oCreatedById = oCreatedById;
    }
    public String getoCreatedBy() {
        return oCreatedBy;
    }
    public void setoCreatedBy(String oCreatedBy) {
        this.oCreatedBy = oCreatedBy;
    }
    public String getReqType() {
        return reqType;
    }
    public void setReqType(String reqType) {
        this.reqType = reqType;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getCompanyJob() {
        return companyJob;
    }
    public void setCompanyJob(String companyJob) {
        this.companyJob = companyJob;
    }
}