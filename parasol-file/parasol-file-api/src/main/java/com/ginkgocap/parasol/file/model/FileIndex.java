package com.ginkgocap.parasol.file.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import com.fasterxml.jackson.annotation.JsonFilter;

/**
 * 
* <p>Title: FileIndex.java<／p> 
* <p>Description: <／p> 

* @author fuliwen 
* @date 2015-11-30 
* @version 1.0
 */
public class FileIndex implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 6755175107258258053L;
	
	// 主键
	private long id;
	// 文件存放的物理路径
	private String filePath;
	// 附件所在服务器地址
	private String serverHost;
	// 源文件的名称	
	private String fileTitle;
	// 文件大小	
	private long fileSize;
	// 文件状态	
	private int status;
	// 创建人
	private long createrId;
	// 加密形式
	private String md5;
	// taskId
	private String taskId;
	// 类型	1:用户头像、2：知识模块、3：人脉模块、4：需求模块、5：组织客户 、6：会议模块 、7：事务、8：动态、
	// 9：畅聊、10：社群、11：云盘文件、12：其他（可扩展）、100：老数据同步
	private int moduleType;
	// 类型	1-图片，2-视频，3-音频, 4-附件（包含文档和其他）
	private int fileType;
    // 解压缩
	private String crc32;
	// 是否转码 1-是，0-否
	private int transcoding;
	// 缩略图地址
	private String thumbnailsPath;
	// appid
	private long appId;
	// 创建时间
	private Date ctime;
	// 备注信息（拓展用）
	private String remark;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileTitle() {
		return fileTitle;
	}

	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}

	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public int getModuleType() {
		return moduleType;
	}

    public void setModuleType(int moduleType) {
		this.moduleType = moduleType;
	}

	public String getCrc32() {
		return crc32;
	}

	public void setCrc32(String crc32) {
		this.crc32 = crc32;
	}

	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	public long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(long createrId) {
		this.createrId = createrId;
	}

	public int getTranscoding() {
		return transcoding;
	}

	public void setTranscoding(int transcoding) {
		this.transcoding = transcoding;
	}

	public String getThumbnailsPath() {
		return thumbnailsPath;
	}

	public void setThumbnailsPath(String thumbnailsPath) {
		this.thumbnailsPath = thumbnailsPath;
	}

	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

	public Date getCtime() {
		return ctime;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
