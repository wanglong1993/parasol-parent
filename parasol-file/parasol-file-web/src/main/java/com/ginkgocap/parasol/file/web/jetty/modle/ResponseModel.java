package com.ginkgocap.parasol.file.web.jetty.modle;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ResponseModel implements Serializable {
    private static final long serialVersionUID = -8109041280283019467L;
    private Map<String, Object> responseDataMap = new HashMap();
    private Map<String, Object> notificationMap = new HashMap();

    public ResponseModel() {
        this.notificationMap.put("notifCode", "0001");
        this.notificationMap.put("notifInfo", "hello mobile app!");
    }

    public Map<String, Object> toMap() {
        HashMap model = new HashMap();
        model.put("responseData", this.responseDataMap);
        model.put("notification", this.notificationMap);
        return model;
    }

    public void put(String key, Object value) {
        this.responseDataMap.put(key, value);
    }

    public void succeed(boolean bool) {
        this.put("succeed", Boolean.valueOf(bool));
    }

    public void errorNotify(String code, String info) {
        this.put("succeed", Boolean.valueOf(false));
        this.notificationMap.put("notifCode", code);
        this.notificationMap.put("notifInfo", info);
    }
}