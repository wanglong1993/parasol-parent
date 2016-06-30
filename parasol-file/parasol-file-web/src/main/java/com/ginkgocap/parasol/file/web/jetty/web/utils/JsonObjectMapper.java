package com.ginkgocap.parasol.file.web.jetty.web.utils;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by thinkpad on 2015/7/2.
 */
public class JsonObjectMapper extends ObjectMapper {
    public JsonObjectMapper() {
        super();

        this.configure(JsonGenerator.Feature.WRITE_NUMBERS_AS_STRINGS, true);// 数字也加引号
//        this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);// 允许单引号
//        this.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);// 字段和值都加引号
//        this.configure(JsonGenerator.Feature.QUOTE_NON_NUMERIC_NUMBERS, true);
//        this.getSerializerProvider().setNullValueSerializer(new JsonSerializer<Object>() {
//            @Override
//            public void serialize(
//                    Object value,
//                    JsonGenerator jg,
//                    SerializerProvider sp) throws IOException, JsonProcessingException {
//                jg.writeString("");
//            }
//        });// 空值处理为空串
    }

    public boolean canSerialize(Class<?> type) {
        if (CommonUtil.getRequestIsFromWebFlag())
            return true;
        return false;
    }
}
