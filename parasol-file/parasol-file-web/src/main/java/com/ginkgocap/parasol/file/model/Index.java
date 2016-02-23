package com.ginkgocap.parasol.file.model;

import java.io.Serializable;

/**
 * 
* @Title: FileIndex.java
* @Package com.ginkgocap.parasol.file.model
* @Description: TODO(用一句话描述该文件做什么)
* @author fuliwen@gintong.com  
* @date 2016年2月23日 上午11:12:50
* @version V1.0
 */
public class Index implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 6755175107258258053L;
	private String id;// 主键
	private String file_path;// 文件存放的物理路径
	private String file_title;// 源文件的名称
	private long file_size; // 文件大小
	private boolean status; //文件状态
	private String md5; // 加密形式
	private String task_id; //taskId
	private String ctime; //创建时间
	private int module_type; //类型
	private int fileType; //类型
	private long author_id; //创建人姓名
	private String crc32;      //解压缩
	public String getId() {
		return id;
	}

	public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }

    public void setId(String id) {
		this.id = id;
	}

	public String getFile_path() {
		return file_path;
	}

	public void setFile_path(String file_path) {
		this.file_path = file_path;
	}

	public String getFile_title() {
		return file_title;
	}

	public void setFile_title(String file_title) {
		this.file_title = file_title;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMd5() {
		return md5;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getCrc32() {
		return crc32;
	}

	public void setCrc32(String crc32) {
		this.crc32 = crc32;
	}

	public long getFile_size() {
		return file_size;
	}

	public void setFile_size(long file_size) {
		this.file_size = file_size;
	}

	public String getTask_id() {
		return task_id;
	}

	public void setTask_id(String task_id) {
		this.task_id = task_id;
	}

	public int getModule_type() {
		return module_type;
	}

	public void setModule_type(int module_type) {
		this.module_type = module_type;
	}

	public long getAuthor_id() {
		return author_id;
	}

	public void setAuthor_id(long author_id) {
		this.author_id = author_id;
	}

}
