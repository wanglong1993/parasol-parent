package com.ginkgocap.parasol.tags.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * Created by Lenovo on 2018/3/7.
 */
public class MyCustomSerializer extends JsonSerializer<Object> {
    @Override
    public void serialize(Object o, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
        if (o != null) {
            jgen.writeObject(o);
        }
    }
}
