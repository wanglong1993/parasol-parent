package com.ginkgocap.parasol.file.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

/**
 * 
* <p>Title: FileIndex.java<／p> 
* <p>Description: <／p> 

* @author fuliwen 
* @date 2015-11-30 
* @version 1.0
 */
@Entity
@Table(name = "tb_file_index")
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
	// 类型	1:用户头像、2：知识模块、3：人脉模块、4：需求模块、5：组织客户 、6：会议模块 、7：其他（可扩展）
	private int moduleType;
	// 类型	1-图片，2-视频，3-音频, 4-文档, 5-附件
	private int fileType;
    // 解压缩
	private String crc32;
	// 是否转码 1-是，0-否
	private int transcoding;
	// 缩略图地址
	private String thumbnailsPath;
	// appid
	private long appId;

	@Id
	@GeneratedValue(generator = "id")
	@GenericGenerator(name = "id", strategy = "com.ginkgocap.ywxt.framework.dal.dao.id.util.TimeIdGenerator", parameters = { @Parameter(name = "sequence", value = "tb_file_index") })
	@Column(name = "id")	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Column(name = "file_type")
	public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

	@Column(name = "file_path")
	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "file_title")
	public String getFileTitle() {
		return fileTitle;
	}

	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}

	@Column(name = "file_size")
	public long getFileSize() {
		return fileSize;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}
	
	@Column(name = "status")
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Column(name = "md5")
	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	@Column(name = "task_id")
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	@Column(name = "module_type")
	public int getModuleType() {
		return moduleType;
	}

    public void setModuleType(int moduleType) {
		this.moduleType = moduleType;
	}

	@Column(name = "crc32")
	public String getCrc32() {
		return crc32;
	}

	public void setCrc32(String crc32) {
		this.crc32 = crc32;
	}

	@Column(name = "server_host")
	public String getServerHost() {
		return serverHost;
	}

	public void setServerHost(String serverHost) {
		this.serverHost = serverHost;
	}

	@Column(name = "creater_id")
	public long getCreaterId() {
		return createrId;
	}

	public void setCreaterId(long createrId) {
		this.createrId = createrId;
	}

	@Column(name = "transcoding")	
	public int getTranscoding() {
		return transcoding;
	}

	public void setTranscoding(int transcoding) {
		this.transcoding = transcoding;
	}

	@Column(name = "thumbnails_path")
	public String getThumbnailsPath() {
		return thumbnailsPath;
	}

	public void setThumbnailsPath(String thumbnailsPath) {
		this.thumbnailsPath = thumbnailsPath;
	}

	@Column(name = "appId")	
	public long getAppId() {
		return appId;
	}

	public void setAppId(long appId) {
		this.appId = appId;
	}

}
