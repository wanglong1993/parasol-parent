package com.ginkgocap.parasol.knowledge.model;

import java.io.Serializable;

public class ResendInfo  implements Serializable{
    private static final long serialVersionUID = 4341320097772762608L;
    private long userId ;//被转发的用户id
    private String userName ;//被转发用户姓名
    private String content ;//被转发内容
    private String title ;//被转发内容
    private String mongoId ;//被转发mongoid
    private long mysqlId ;//被转发mysqlId
    private String time ;//被转发的发布时间
    private String reply ;//回复内容
    public long getUserId() {
        return userId;
    }

    public long getMysqlId() {
        return mysqlId;
    }

    public void setMysqlId(long mysqlId) {
        this.mysqlId = mysqlId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getMongoId() {
        return mongoId;
    }
    public void setMongoId(String mongoId) {
        this.mongoId = mongoId;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getReply() {
        return reply;
    }
    public void setReply(String reply) {
        this.reply = reply;
    }
}
