package com.ginkgocap.parasol.sms.model;

import java.util.HashMap;
import java.util.Map;


/**
 * 
* <p>Title: SMSTypeEnum.java<／p> 
* <p>Description: 开放平台短信接口类<／p> 

* @author fuliwen 
* @date 2015-11-9 
* @version 1.0
 */
public enum SMSTypeEnum {
    //标准短信单个发送
    SMS_SINGLE {
        @Override
        public String getName() {
            return "1";
        }
    },
    //标准短信群发送
    SMS_GROUP {
        @Override
        public String getName() {
            return "2";
        }
    },
    //长短信单个发送
    SMS_LEN_SINGLE {
        @Override
        public String getName() {
            return "3";
        }
    },
    //长短信群发
    SMS_LEN_GROUP {
        @Override
        public String getName() {
            return "4";
        }
    };
    public static final Map<Integer, SMSTypeEnum> SMSTypeMAP = getSMSTypeMap();

    public abstract String getName();

    public static Map<Integer, SMSTypeEnum> getSMSTypeMap() {
        final Map<Integer, SMSTypeEnum> smsTypeMap = new HashMap<Integer, SMSTypeEnum>();
        smsTypeMap.put(SMSTypeEnum.SMS_SINGLE.ordinal(), SMSTypeEnum.SMS_SINGLE);
        smsTypeMap.put(SMSTypeEnum.SMS_GROUP.ordinal(), SMSTypeEnum.SMS_GROUP);
        smsTypeMap.put(SMSTypeEnum.SMS_LEN_SINGLE.ordinal(), SMSTypeEnum.SMS_LEN_SINGLE);
        smsTypeMap.put(SMSTypeEnum.SMS_LEN_GROUP.ordinal(), SMSTypeEnum.SMS_LEN_GROUP);
        return smsTypeMap;
    }


}
