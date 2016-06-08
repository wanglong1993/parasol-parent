package com.ginkgocap.parasol.organ.web.jetty.web.controller;

import com.ginkgocap.parasol.organ.web.jetty.web.utils.CommonUtil;
import com.ginkgocap.parasol.organ.web.jetty.web.utils.JsonReadUtil;
import com.ginkgocap.ywxt.util.Encodes;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 
 */
public abstract class BaseController {
	private static Logger logger = Logger.getLogger(BaseController.class);

    /**
     * 获取head中的json参数串
     *
     * @param request
     * @return result
     * @throws java.io.IOException
     *
     */
    public String getJsonParamStr(HttpServletRequest request) throws IOException {
        String result = JsonReadUtil.getJsonIn(request);
        return result;
    }

    public JSONObject convertJson(HttpServletRequest request) throws IOException {
        String result = JsonReadUtil.getJsonIn(request);
        if (StringUtils.isEmpty(result))
            return null;
        JSONObject json = null;
        try {
            json = JSONObject.fromObject(result);
        } catch (net.sf.json.JSONException e) {
            logger.warn("convert json exception: " + e.getMessage());
            return null;
        }
        return json;
    }

    public void setSessionAndErr(HttpServletRequest request, HttpServletResponse response, String errCode, String errMessage) {

        response.setHeader("errorCode", errCode);
       // response.setHeader("errorMessage", Encodes.encodeBase64(errMessage.getBytes()));
    }

    /**
     * 如果为null，设置空值
     *
     * @param str
     *            待处理对象
     * @return str
     */
    public Object getJsonValueByKey(JSONObject j, String key) {
        // json中是否包含{Param:key}
        if (j.containsKey(key)) {
            return j.get(key);
        } else {
            throw new RuntimeException("JSONObject hasn't key :" + key);
        }
    }

    /**
     * 如果为null，设置空值
     *
     * @param str
     *            待处理对象
     * @return str
     */
    public Boolean getBooleanJsonValueByKey(JSONObject j, String key) {
        // json中是否包含{Param:key}
        Object obj = getJsonValueByKey(j, key);
        if (obj instanceof Boolean) {
            return j.getBoolean(key);
        } else {
            return Boolean.valueOf(String.valueOf(j.get(key)));
        }
    }

    /**
     * 如果为null，设置空值
     *
     * @param str
     *            待处理对象
     * @return str
     */
    public String getStringJsonValueByKey(JSONObject j, String key) {
        // json中是否包含{Param:key}
        Object obj = getJsonValueByKey(j, key);
        if (obj instanceof String) {
            return j.getString(key);
        } else {
            return String.valueOf(j.get(key));
        }
    }

    /**
     * 如果为null，设置空值
     *
     * @param str
     *            待处理对象
     * @return str
     */
    public Long geLongJsonValueByKey(JSONObject j, String key) {
        // json中是否包含{Param:key}
        Object obj = getJsonValueByKey(j, key);
        if (obj instanceof Long) {
            return CommonUtil.optLongFromJSONObject(j, key);
        } else {
            return Long.valueOf(String.valueOf(j.get(key)));
        }
    }
}
