package com.ginkgocap.parasol.knowledge.model.common;

/**
 * Created by Chen Peifeng on 2016/3/22.
 */
public class Knowledge {

    public static enum EType {
        EUnKnown((short)-1, "无效类型"),
        ESystemCreate((short)0, "为系统创建"),
        EUserCreate((short)1, "为用户创建");

        private short value;
        private String desc;
        private EType(short value,String desc){
            this.value = value;
            this.desc = desc;
        }
        public short getValue(){ return value; }
        public String getDesc() { return desc; }

        public static EType valueOf(short value)
        {
            switch (value) {
                case 0: return ESystemCreate;
                case 1: return EUserCreate;
                default: return EUnKnown;
            }
        }
    }

    public static enum EStatus {
        EUnKnown((short)-1, "无效类型"),
        EInvalidate((short)0, "为无效/删除"),
        EValidate((short)1, "为有效"),
        EDraft((short)2, "为草稿"),
        ERecovery((short)3,"回收站");

        private short value;
        private String desc;
        private EStatus(short value,String desc){
            this.value = value;
            this.desc = desc;
        }
        public short getValue(){ return value; }
        public String getDesc() { return desc; }

        public static EStatus valueOf(short value)
        {
            switch (value) {
                case 0: return EInvalidate;
                case 1: return EValidate;
                case 2: return EDraft;
                case 3: return ERecovery;
                default: return EUnKnown;
            }
        }
    }

    public static enum EAuditStatus {
        EUnKnown((short)-1, "无效类型"),
        EFailed((short)0, "未通过"),
        EAuditing((short)1, "审核中"),
        EPassed((short)2, "审核通过");

        private short value;
        private String desc;
        private EAuditStatus(short value,String desc){
            this.value = value;
            this.desc = desc;
        }

        public short getValue(){ return value; }
        public String getDesc() { return desc; }

        public static EAuditStatus valueOf(short value)
        {
            switch (value) {
                case 0: return EFailed;
                case 1: return EAuditing;
                case 2: return EPassed;
                default: return EUnKnown;
            }
        }
    }

    public static enum EReportStatus {
        EUnKnown((short)-1, "无效类型"),
        EReported((short)0, "已被举报"),
        ENotReport((short)1, "未被举报"),
        EReportAudited((short)2, "举报审核通过"),
        EReportFailed((short)3,"举报审核未通过，即无非法现象");

        private short value;
        private String desc;
        private EReportStatus(short value,String desc){
            this.value = value;
            this.desc = desc;
        }
        public short getValue(){ return value; }
        public String getDesc() { return desc; }

        public static EReportStatus valueOf(short value)
        {
            switch (value) {
                case 0: return EReported;
                case 1: return ENotReport;
                case 2: return EReportAudited;
                case 3: return EReportFailed;
                default: return EUnKnown;
            }
        }
    }
}
