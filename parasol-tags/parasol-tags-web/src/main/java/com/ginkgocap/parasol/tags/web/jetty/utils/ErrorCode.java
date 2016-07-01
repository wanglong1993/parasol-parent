package com.ginkgocap.parasol.tags.web.jetty.utils;

public enum ErrorCode {
    success("hello app", "0001", 1), fail("error", "0002", 2), paramErr("参数错误", "0003", 3), commonErr("错误", "0004", 4);
    String des;
    String num;
    int state;

    ErrorCode(String des, String v, int state) {
        this.des = des;
        this.num = v;
        this.state = state;
    }

    public String num() {
        return num;
    }

    public String des() {
        return des;
    }

    public int state() {
        return state;
    }
}
