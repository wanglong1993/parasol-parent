package com.ginkgocap.parasol.file.model;

import java.io.Serializable;

import javax.persistence.Transient;

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
	private long id;// 主键
	private String filePath;// 文件存放的物理路径
	private String fileTitle;// 源文件的名称
	private long fileSize; // 文件大小
	private boolean status; //文件状态
	@Transient
	private long author; //创建人
	private String md5; // 加密形式
	private String taskId; //taskId
	private String ctime; //创建时间
	private int moduleType; //类型
	private int fileType; //类型
	private String authorName; //创建人姓名
	private String crc32;      //解压缩

	
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	

	public long getAuthor() {
		return author;
	}

	public void setAuthor(long author) {
		this.author = author;
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

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public int getModuleType() {
		return moduleType;
	}

	public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
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

}
