package com.ginkgocap.parasol.file.constant;

/**
 * 文件类型 枚举.
 */
public enum FileType {

    PIC(1,"图片"),
    VIDEO(2,"视频"),
    AUDIO(3,"音频"),
    DOC(4,"文档"),
    OTHER(5,"其它");

    private int key;
    private String value;

    FileType(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
