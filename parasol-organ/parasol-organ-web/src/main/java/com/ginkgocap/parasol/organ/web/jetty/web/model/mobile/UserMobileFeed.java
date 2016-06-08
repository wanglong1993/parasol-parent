package com.ginkgocap.parasol.organ.web.jetty.web.model.mobile;



import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.springframework.data.annotation.Transient;

import com.alibaba.fastjson.JSON;
import com.ginkgocap.ywxt.user.form.EtUserInfo;
/**
 * 好友动态 mongo 
 * @author 窦友
 *
 */
/**
 * @author Administrator
 *
 */
public class UserMobileFeed implements Serializable {
    private static final long serialVersionUID = -26037617805798145L;
    
    private String id;//主键
    
    private List<LtypeUser> ltypeOneUser;//信息接收人  对应ltype = 1 时
    
    private List<LtypeUser> ltypeTwoUser;//信息接收人   对应ltype =2 时
    
    private List<LtypeUser> ltypeTreeUser;//信息接收人  对应 ltype =3 时
    
    private Set<Long> dynamicUser; //拥有动态的人的id集合
    
    private long createdById;//信息发布人
    
    private String createdBy;//信息发布人
    
    private long oCreatedById;//信息首次发布人
    
    private String oCreatedBy;//信息首次发布人
    
    private String companyName;//信息首次发布人公司
    
    private String companyJob;//信息首次发布人职位
    
    private int scope;//扩散范围   默认0 所有人可见，1根据receiver检测 2所有好友查询所有好友id然后放入receivers然后scope改成1
    
    private int type;//1、投资 2、融资  3、 业务需求 4、任务 5、项目  6、通知
    
    private int ltype;//和type配合 当type=1或2时 对应的 ltype 1为我发布 2为我关注 3为我回复 若type=3或4或5时 对应的 ltype 1我负责 2我参与 3我分配
    
    private long targetId;//model对象在mysql中的id
    
    private String title;//信息标题
    
    private String content;//信息内容
    
    private String ctime;//发布时间
    private String utime;//更新时间
    
    private int isUsed; //是否可用 是1 否0
    
    public String getUtime() {
		return utime;
	}
	public void setUtime(String utime) {
		this.utime = utime;
	}
	private String parentId = "";//扩展字段，心情日记的回复功能，暂时不用(开启，表示被屏蔽)
    
    private List<Long> deletedById;//删除人从自己的列表中删除
    
    private int delstatus;//状态（0正常使用 -1 删除 ）
    
    
    @Transient
    private int oneReplyCount;
    @Transient
    private List<EtUserInfo> etInfo;
    
    private String groupName;
    
    //是否可见
	private String isVisable;
	
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
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
	public List<LtypeUser> getLtypeOneUser() {
		return ltypeOneUser;
	}
	public void setLtypeOneUser(List<LtypeUser> ltypeOneUser) {
		this.ltypeOneUser = ltypeOneUser;
	}
	public List<LtypeUser> getLtypeTwoUser() {
		return ltypeTwoUser;
	}
	public void setLtypeTwoUser(List<LtypeUser> ltypeTwoUser) {
		this.ltypeTwoUser = ltypeTwoUser;
	}
	public List<LtypeUser> getLtypeTreeUser() {
		return ltypeTreeUser;
	}
	public void setLtypeTreeUser(List<LtypeUser> ltypeTreeUser) {
		this.ltypeTreeUser = ltypeTreeUser;
	}
	public int getLtype() {
		return ltype;
	}
	public void setLtype(int ltype) {
		this.ltype = ltype;
	}
	public int getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(int isUsed) {
		this.isUsed = isUsed;
	}
	public Set<Long> getDynamicUser() {
		return dynamicUser;
	}
	public void setDynamicUser(Set<Long> dynamicUser) {
		this.dynamicUser = dynamicUser;
	}
	
	/**
	 * @author zhangzhen
	 * 如果数据为空返回null
	 * 
	 * 指导使用方法
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * String jsonData = j.getString("Entity");
	 * */
	public static UserMobileFeed getByJsonString(String jsonEntity) {
		if(jsonEntity.equals("{}")) {
			return null; //无数据判断
		}
		return JSON.parseObject(jsonEntity, UserMobileFeed.class);
	}
	
	/**
	 * @author zhangzhen
	 * 如果数据为空返回null
	 * 
	 * 指导使用方法
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * Object jsonData = j.get("Entity");
	 * */
	public static UserMobileFeed getByJsonObject(Object jsonEntity) {
		return getByJsonString(jsonEntity.toString());
	}
	
	/**
	 * @author zhangzhen
	 * 如果没有数据，返回空数组
	 * 
	 * 指导使用方法
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * String jsonData = j.getString("Entity");
	 * */
	public static List<UserMobileFeed> getListByJsonString(String object) {
		return JSON.parseArray(object, UserMobileFeed.class);
	}
	
	/**
	 * @author zhangzhen
	 * @CreateTime 2014-11-11
	 * 如果没有数据，返回空数组
	 * 
	 * 指导使用方法 
	 * JSONObject j = JSONObject.fromObject(requestJson);
	 * Object jsonData = j.get("EntityList");
	 * */
	public static List<UserMobileFeed> getListByJsonObject(Object object) {
		return getListByJsonString(object.toString());
	}
}
